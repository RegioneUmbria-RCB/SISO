package it.webred.classfactory;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alessandro Feriani
 * Confronta versione due classi
 * la versione e' contenuta nell'ultimo nome del package con prefisso "ver". Se non presente è sottointesa "ver0"
 * e.g. it.webred.classfactory.VersionClassComparator
 * e.g. it.webred.classfactory.ver1.VersionClassComparator
 * e.g. it.webred.classfactory.ver1A.VersionClassComparator
 * e.g. it.webred.classfactory.ver10A.VersionClassComparator
 */
@SuppressWarnings({ "rawtypes" })
public class VersionClassComparator implements Comparator<Class>, Serializable {

	private static final long serialVersionUID = 1L;

	private static class VersionInfo {
		public Integer 	major = 0;
		public String 	minor = "";
		public VersionInfo(){
			
		}
	}
	
	public static String getVersionLowerCase( Class cls ){
		String ret = "ver0";

		String packageName = cls.getPackage().getName(); 
		String[] arr = packageName.split("\\.");
		if( arr != null && arr.length > 0 )
		{
			String lastPackageName = arr[arr.length - 1].toLowerCase();
			if( lastPackageName.startsWith("ver") )
				ret = lastPackageName;
		}

		return ret.replace("ver", "");
	}
	
	private static String VALID_PATTERN = "[0-9]+|[A-Z]+";
	private static VersionInfo parse(String toParse ) {
	    List<String> chunks = new LinkedList<String>();
	    toParse = toParse + "$"; //Added invalid character to force the last chunk to be chopped off
	    int beginIndex = 0;
	    int endIndex = 0;
	    while(endIndex < toParse.length()){         
	        while(toParse.substring(beginIndex, endIndex + 1).matches(VALID_PATTERN)){
	            endIndex++;
	        }
	        if(beginIndex != endIndex){
	            chunks.add(toParse.substring(beginIndex, endIndex));    
	        } else {
	            endIndex++;
	        }  
	        beginIndex = endIndex;
	    }               
	    
	    VersionInfo ver = new VersionInfo();

	    if( chunks.size() > 0 )
	    	ver.major = Integer.parseInt(chunks.get(0));
	    if( chunks.size() > 1 )
	    	ver.minor = chunks.get(1);
	    
	    return ver;
	}
	
	public static int compare(String ver1, String ver2) {

		// e.g. "1" < "1A" < "2"
		VersionInfo v1= parse(ver1);
		VersionInfo v2= parse(ver2);
		
		if( v1.major < v2.major )
			return -1;
		if( v1.major > v2.major )
			return 1;

		//Se qui le versioni major so uguali, confornta le minor
		return v1.minor.compareTo(v2.minor);
	}
	
	@Override
	public int compare(Class o1, Class o2) {
		String ver1 = getVersionLowerCase(o1);
		String ver2 = getVersionLowerCase(o2);
		
		return compare( ver1, ver2 );
	}

}
