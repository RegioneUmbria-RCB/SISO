package it.webred.cs.csa.utils;

public class StringUtils {

	public static String capitalize(String value) {
		if (value == null) {
			return value;
		}
		if (value.trim().equals("")) {
			return value.trim();
		}
		StringBuilder sb = new StringBuilder();
		String[] arr = value.split(" ");
		for (String s : arr) {
			if (s != null && !s.trim().equals("")) {
				sb.append(s.substring(0, 1).toUpperCase());
				sb.append(s.length() == 1 ? "" : s.substring(1));
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}
	
	public static String uppercase(String value) {
		if (value == null) {
			return value;
		}
		if (value.trim().equals("")) {
			return value.trim();
		}
		return value.trim().toUpperCase();
	}
	
}
