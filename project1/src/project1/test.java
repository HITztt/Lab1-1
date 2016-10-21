package project1;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*change for lab3!*/
/* 2nd change for lab3*/

public class test {
	
    static ArrayList<Double> al = new ArrayList<Double>();
    static double ADD =  Math.PI; 
    static double MUL =  Math.PI + 1;
	
	

	public static boolean simplify(String expr){
		int expr_start = 0;
		int expr_end = 0;
		
		char[] arr = expr.toCharArray();
		
		while(expr_start!=arr.length){
			while(arr[expr_end] != '+' && arr[expr_end] != '*'){
				expr_end++;
				if(expr_end == arr.length){
					break;
				}
			}
			
			String temp = new String(arr,expr_start,expr_end - expr_start);
			
			if(temp.equals("+")){
				al.add(ADD);
			}else if(temp.equals("*")){
				al.add(MUL);
			}else{
				try{
					Double.parseDouble(temp);
				}catch (Exception e) {
					return false;
				}
				al.add(Double.parseDouble(temp));
			}
			
			if(expr_end<arr.length){
				expr_start = expr_end;
				expr_end++;
				String temp2 = new String(arr,expr_start,expr_end - expr_start);
				if(temp2.equals("+")){
					al.add(ADD);
				}else if(temp2.equals("*")){
					al.add(MUL);
				}
			}
			expr_start = expr_end;		
		}
		
		int i=0;
		while(i<al.size()-1){
			double element = al.get(i);
			double result = 0;
			if(element == MUL){
				result = al.get(i-1)*al.get(i+1);
				al.remove(i + 1);    
                al.remove(i);    
                al.set(i - 1, result);
			}else{
				i++;
			}
		}
		
		i=0;
		while(i<al.size()-1){
			double element = al.get(i);
			double result = 0;
			if(element == ADD){
				result = al.get(i-1)+al.get(i+1);
				al.remove(i + 1);    
                al.remove(i);    
                al.set(i - 1, result);
			}else{
				i++;
			}
		}
		return true;
	}
	
	public static void derivative(String expr,String var){
		int i,count;
		if(expr.indexOf(var) == -1){
			System.out.println("Not find this variable");
			return;
		}
		String s[] = expr.split("[+]");
		ArrayList<String> result = new ArrayList<String>();
		count = findStr1(s[0],var);
		
		/*
		if(count == 1){
			if(s[0].equals(var)){
				System.out.print(s[0].replace(var, "1"));
				result.add(s[0].replace(var, "1"));
			}else{
			s[0] = s[0].replace(var+"*", "");
			System.out.print(s[0].replace("*"+var, ""));
			
			}
		}else if(count > 1){
			System.out.print(  (count) + "*" + s[0].replaceFirst(var + "[*]", "") );
		}
		*/
		
		for(i = 0;i <= s.length - 1;i++){
			count = findStr1(s[i],var);
			if(count == 1){
				if(s[i].equals(var)){
					//System.out.print("+"+ s[i].replace(var, "1"));
					result.add(s[i].replace(var, "1"));
				}
				else
				{
				s[i] = s[i].replace(var+"*", "");
				//System.out.print( "+" + s[i].replace("*"+var, ""));
				result.add(s[i].replace("*"+var, ""));
				}
			}else if(count > 1){
				//System.out.print( "+"+(count) + "*" + s[i].replaceFirst(var + "[*]", "")  );
				String temp = (count) + "*" + s[i].replaceFirst(var + "[*]", "");
				result.add(temp);
			}
		}
		System.out.print(result.get(0));
		for(i=1;i<result.size();i++)
		{
			System.out.print("+"+result.get(i));
		}
		System.out.println();	
		
		/*
		count = findStr1(s[s.length-1],var);
		
		if(count == 1){
			if(s[i].equals(var)){
				System.out.print(s[i].replace(var, "1"));
			}else{
			s[i] = s[i].replace(var+"*", "");
			System.out.print(s[i].replace("*"+var, ""));
			}
		}else if(count > 1){
			System.out.print(  (count) + "*" + s[i].replaceFirst(var + "[*]", "") );
		}*/
	}
	
	public static int findStr1(String srcText, String keyword) {  
        int count = 0;  
        int leng = srcText.length();  
        int j = 0;  
        for (int i = 0; i < leng; i++) {  
            if (srcText.charAt(i) == keyword.charAt(j)) {  
                j++;  
                if (j == keyword.length()) {  
                    count++;  
                    j = 0;  
                }  
            } else {  
                i = i - j;// should rollback when not match  
                j = 0;  
            }  
        }  
  
        return count;  
    } 
	
	public static void main(String[] arg){
		int i=1;
		Scanner in = new Scanner(System.in);
		while(true)
		{
		String expr = in.nextLine();
		expr = expr.replace(" ", "");
		String expr1 = expr;
		String pattern = "(([1-9][0-9]*\\*|\\p{Alpha}{1,}\\*)*([1-9][0-9]*|\\p{Alpha}{1,})\\+)*([1-9][0-9]*\\*|\\p{Alpha}{1,}\\*)*([1-9][0-9]*|\\p{Alpha}{1,})";
	      // Create a Pattern object
	    
	    boolean f = expr.matches(pattern);
	    if (f){
	    	System.out.println(expr+ " right");
	    }
	    else{
			System.out.println("Erorr");
		}
		while (true){
			
			String Command = in.nextLine();
			String s[] = Command.split(" "); 
			
			if(s[0].equals("!simplify")){
				if(s.length == 0){
					System.out.println(expr);
				}
				else
				{
					int flag = 1;
					for(i = 1;i < s.length;i++){
						String temp[] = s[i].split("=");
						if (temp[1].matches("[1-9][0-9]{1,}\\.{0,1}[0-9]{0,}|[0-9]\\.{0,1}[0-9]{0,}"))
						{
						
						}
						else
						{
							System.out.println("wrong input");
							break;
						}
						
						if(expr.indexOf(temp[0]) == -1){
							System.out.println("Not find " + temp[0] +" this variable");
							flag = 0;
							break;
						}else{
							expr = expr.replace(temp[0], temp[1]);
						}
					}
					if (flag==1){
						long ai=System.currentTimeMillis();
						if(simplify(expr)){
							System.out.print(al.get(0));
						System.out.println("\r求值执行耗时 : "+(System.currentTimeMillis()-ai)/1000f+" 秒 ");
						}else{
							System.out.println(expr);
							
						}
					}				
				}				
			}else if(s[0].equals("!d/d")){
				long ai=System.currentTimeMillis();	
				derivative(expr1,s[1]);
				System.out.println("\r求导执行耗时 : "+(System.currentTimeMillis()-ai)/1000f+" 秒 ");
			}else{
				System.out.println("Command Erorr");
			}
		
		}
		}
		
	}
}

