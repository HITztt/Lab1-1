package test;

import java.util.ArrayList;
import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.logging.Logger;

public class Test {
  static Logger  log = Logger.getLogger(Test.class.getName());
  static ArrayList<Double> al = new ArrayList<Double>();
  static double aDD =  Math.PI; 
  static double mUL =  Math.PI + 1;

  //化简函数
  /**

  * 依次扫描字符串，遇到+/*存储为一个变量，放入数组，将+存为PI  *存为PI+1,计算
  * 时依次扫描，先扫描*将两边数字相乘，再扫描加法，讲两边数字相加. 

  * expr

  * boolean

  * 异常捕捉

  */
  public static boolean simplify(String expr) {
    int exprStart = 0;
    int exprEnd = 0;

    char[] arr = expr.toCharArray();

    while (exprStart != arr.length) {
      while (arr[exprEnd] != '+' && arr[exprEnd] != '*') {
        exprEnd++;
        if (exprEnd == arr.length) {
          break;
        }
      }

      String temp = new String(arr,exprStart,exprEnd - exprStart);

      if (temp.equals("+")) {
        al.add(aDD);
      } else if (temp.equals("*")) {
        al.add(mUL);
      } else {
        try {
          Double.parseDouble(temp);
        } catch (Exception ee) {
          return false;
        }
        al.add(Double.parseDouble(temp));
      }

      if (exprEnd < arr.length) {
        exprStart = exprEnd;
        exprEnd++;
        String temp2 = new String(arr,exprStart,exprEnd - exprStart);
        if (temp2.equals("+")) {
          al.add(aDD);
        } else if (temp2.equals("*")) {
          al.add(mUL);
        }
      }
      exprStart = exprEnd;
    }

    int ii = 0;
    while (ii < al.size() - 1) {
      double element = al.get(ii);
      double result = 0;
      if (element == mUL) {
        result = al.get(ii - 1) * al.get(ii + 1);
        al.remove(ii + 1);    
        al.remove(ii);    
        al.set(ii - 1, result);
      } else {
        ii++;
      }
    }

    ii = 0;
    while (ii < al.size() - 1) {
      double element = al.get(ii);
      double result = 0;
      if (element == aDD) {
        result = al.get(ii - 1) + al.get(ii + 1);
        al.remove(ii + 1);    
        al.remove(ii);    
        al.set(ii - 1, result);
      } else {
        ii++;
      }
    }
    return true;
  }
  
  //求导函数
  /**

  * 以+将 字符串分割为多个多项式，计算每个多项式中求导变量出现的次数.

  * expr, var

  * void

  * 

  */
  public static void derivative(String expr,String var) {
    int ii = 0;
    int count;
    if (expr.indexOf(var) == -1) {
    	log.fine("Not find this variable");
      return;
    }
    String []ss = expr.split("[+]");
    ArrayList<String> result = new ArrayList<String>();
    count = findStr1(ss[0],var);


    for (ii = 0;ii <= ss.length - 1;ii++) {
      count = findStr1(ss[ii],var);
      if (count == 1) {
        if (ss[ii].equals(var)) {
          result.add(ss[ii].replace(var, "1"));
        } else {
          ss[ii] = ss[ii].replace(var + "*" , "");

          result.add(ss[ii].replace("*" + var , ""));
        }
      } else if (count > 1) {
        final String temp = (count) + "*" + ss[ii].replaceFirst(var + "[*]", "");
        result.add(temp);
      }
    }
    System.out.print(result.get(0));
    for (ii = 1;ii < result.size();ii++) {
      System.out.print("+" + result.get(ii));
    }
    System.out.println();
  }
  
  /**

  * 找字符串.

  * srcText, keyword

  * int

  * 

  */
  public static int findStr1(String srcText, String keyword) {
    int count = 0;
    int leng = srcText.length();
    int jlength = 0;
    for (int i = 0; i < leng; i++) {
      if (srcText.charAt(i) == keyword.charAt(jlength)) {
    	  jlength++;
        if (jlength == keyword.length()) {
          count++;
          jlength = 0;
        }
      } else {
        i = i - jlength;// should rollback when not match
        jlength = 0;
      }
    }
  
    return count;  
  }

  /**

  * 主函数.

  * [] arg

  * void

  * 

  */
  public static void main(String[] arg) {
    final int MAX_NUMBER = 1;
    int iilength = 1;
    Scanner in = new Scanner(System.in);

    //正则表达式匹配字符串
    String expr = in.nextLine();
    expr = expr.replace(" ", "");
    
    String pattern = "(([1-9][0-9]*\\*|\\p{Alpha}{1,}\\*)*([1-9][0-9]"
        + "*|\\p{Alpha}{1,})\\+)*([1-9][0-9]*\\*|\\p{Alpha}{1,}\\*)"
        + "*([1-9][0-9]*|\\p{Alpha}{1,})";
    // Create a Pattern object

    boolean ff = expr.matches(pattern);
    if (ff) {
      ;
    } else {
      System.out.println("Erorr");
      in.close();
      return;
    }
    //输入命令
    String command = in.nextLine();
    String []ss = command.split(" "); 
    //求值或化简
    if (ss[0].equals("!simplify")) {
      if (ss.length == 0) {
        System.out.println(expr);
      } else {
        int flag = 1;
        for (iilength = 1;iilength < ss.length;iilength++) {
          String []temp = ss[iilength].split("=");
          if (temp[1].matches("[1-9][0-9]{1,}\\.{0,1}[0-9]{0,}|[0-9]\\.{0,1}[0-9]{0,}")) {
            ;
          } else {
            System.out.println("wrong input");
            break;
          }

          if (expr.indexOf(temp[0]) == -1) {
            System.out.println("Not find " + temp[0] + " this variable");
            flag = 0;
            break;
          } else {
            expr = expr.replace(temp[0], temp[1]);
          }
        }
        if (flag == MAX_NUMBER) {
          long ai = System.currentTimeMillis();
          if (simplify(expr)) {
            System.out.print(al.get(0));
            System.out.println("\r求值执行耗时 : " + (System.currentTimeMillis() - ai) / 1000f + " 秒");
          } else {
            System.out.println(expr);

          }
        }
      }
    } else if (ss[0].equals("!d/d")) { //求导
      long ai = System.currentTimeMillis();
      final String expr1 = expr;
      derivative(expr1,ss[1]);
      System.out.println("\r求导执行耗时 : " + (System.currentTimeMillis() - ai) / 1000f + " 秒 ");
    } else {
      System.out.println("Command Erorr");
    }
    in.close();
  }
}

