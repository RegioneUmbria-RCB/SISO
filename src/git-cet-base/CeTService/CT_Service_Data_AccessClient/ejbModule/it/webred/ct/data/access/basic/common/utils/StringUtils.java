package it.webred.ct.data.access.basic.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class StringUtils {
	
	public static long parsStringToLong(String s){
		long l = -1;
		if (s != null && !s.trim().equalsIgnoreCase("")){
			l = Long.parseLong(s.trim());
		}
		
		return l;
	}//-------------------------------------------------------------------------
	
	public static long parsStringToLongZero(String s){
		long l = 0;
		if (s != null && !s.trim().equalsIgnoreCase("")){
			l = Long.parseLong(s.trim());
		} 
		return l;
	}//-------------------------------------------------------------------------
	
	public static double parsStringToDouble(String s){
		double d = -1;

		if (s != null && !s.trim().equalsIgnoreCase("")){
			s = s.replace(',', '.');
			d = Double.parseDouble(s.trim());
		}
		return d;
	}//-------------------------------------------------------------------------
	
	public static double parsStringToDoubleZero(String s){
		double d = 0d;

		if (s != null && !s.trim().equalsIgnoreCase("")){
			s = s.replace(',', '.');
			d = Double.parseDouble(s.trim());
		}
		return d;
	}//-------------------------------------------------------------------------

	public static double parsBigDecimalToDoubleZero(BigDecimal s){
		double d = 0d;

		if (s != null){
			d = s.doubleValue();
		}
		return d;
	}//-------------------------------------------------------------------------

	public static byte parsStringToByte(String s){
		byte b = -1;

		if (s != null && !s.trim().equalsIgnoreCase("")){
			b = Byte.parseByte(s.trim());
		}

		return b;
	}//-------------------------------------------------------------------------
	
	public static int parsStringToInt(String s){
		int i = -1;

		if (s != null && !s.trim().equalsIgnoreCase("")){
			i = Integer.parseInt(s.trim());
		}
		return i;
	}//-------------------------------------------------------------------------
	
	public static String formatDouble(double d){
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		
		return decimalFormat.format(d);
	}//------------------------
	

	public static String checkNullString(String s){		
		if (s != null && !s.trim().equalsIgnoreCase("")){
			return s.trim();
		}else{
			return "";
		}
	}
	
	public static String cleanLeftPad(String s, char pad) {
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == pad)
				s = s.substring(1);
				
		}
		return s;
	}
	
	public static String removeLeadingZero(String str) {
		if (str==null || str.length() == 0)
			return str; 
		
		String retVal=new String(str);
		int i=0;
		while (i<str.length()) {
			if(str.charAt(i)=='0' && str.length() >i+1 ) {
				retVal=str.substring(i+1);
			}else
				break;
			i++;
		}
		return retVal;
	
	}	
	
	
	
	public static String pulisciVia(String via){
		String viaNew="";
		String viaNewMaiuscole="";
		
		if (via != null){
			char [] viaChars = via.toCharArray();
			int startVia=0;
			for (int i=0; i<viaChars.length; i++){
				char c= viaChars[i];
				
				if (c != '#'){
					try{
						java.lang.Character character = new java.lang.Character(c);
					      String s = character.toString();
					      int intChar = Integer.parseInt(s);
					}
					catch(Exception e){
						//non è un numerico
						startVia=i;
						break;
					}
				}
			}
			
			
			viaNew= via.substring(startVia);
			String[] viaNewArr = viaNew.trim().split(" ");
			if (viaNewArr != null && viaNewArr.length>0){
				String[] viaNewMaiuscoleArr=new String[viaNewArr.length];
				for (int i=0; i<viaNewArr.length; i++){
					String viaNewItem= viaNewArr[i];
					if (viaNewItem.length()>0){
					char viaNewItemIniziale= viaNewItem.charAt(0);
					String viaNewItemMaiuscole= (String.valueOf(viaNewItemIniziale)).toUpperCase() + viaNewItem.substring(1).toLowerCase();
					viaNewMaiuscoleArr[i]= viaNewItemMaiuscole;
					}
				}
				
				for (int i=0; i<viaNewMaiuscoleArr.length; i++){
					String viaNewItem= viaNewMaiuscoleArr[i];
					if (viaNewItem!= null)
					viaNewMaiuscole= viaNewMaiuscole+ viaNewItem + " ";
				}
				
			}else{
				if (viaNew.length()>0){
				char viaNewItemIniziale= viaNew.charAt(0);
				viaNewMaiuscole= (String.valueOf(viaNewItemIniziale)).toUpperCase() + viaNew.substring(1).toLowerCase();
				}
			}
			
			
		}
		return viaNewMaiuscole.trim();
	}
	public static String fill(char filler, String pos, String str, int len){
		String retVal = new String(str);
		while (retVal.length() < len) {
			if (pos.equals("sx"))
				retVal = filler + retVal;
			else if  (pos.equals("dx"))
				retVal = retVal+retVal;
			
		}
		return retVal;
	}
}
