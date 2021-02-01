package it.webred.classfactory;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Alessandro Feriani
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WebredClassFactory {

	private static Object instanceLast(List<Class> list ) throws Exception {
		Class cls = Collections.max(list, new VersionClassComparator() );
		if( cls != null )
			return cls.newInstance();
		return null;
	}
	private static Object instanceVersion(List<Class> list, String ver ) throws Exception {
		ver = ver.toLowerCase().replace("ver", "");
		for (Class cls : list) {
			String clsVer = VersionClassComparator.getVersionLowerCase(cls);
			if( VersionClassComparator.compare(clsVer, ver) == 0 )
				return cls.newInstance();
		}

		return null;
	}
	
	/**
	 * @param 	className nome completo della classe da instanziare. 
	 * @param 	interface2implements interfaccia che la classi ricercate devono implementare
	 * @param 	versione di default se si vuole comunque instanziare una versione intermedia tra la prima e la max 
	 * 			( e.g "ver1", "ver10A")
	 * @return 	instanza della classe className. 
	 * 			Se className è empty, ricerca tutte le classi contenute nel package cui l'interfaccia appartiene.
	 * 			Tra tutte le classi prova ad instanziare la defaultVer ( se presente ) altrimenti instanzia l'utlima secondo
	 * 			l'algortimo di VersionClassComparator  @See VersionClassComparator
	 * 
	 * @throws Exception
	 */
	public static Object newInstance(String className, Class interface2implements, String defaultVer ) throws Exception {

		Object obj = null;
		
		if( StringUtils.isNotEmpty(className) ) {
			
			Class cls = Class.forName(className);
			
			//Check if implementes right interface
			if( interface2implements.isAssignableFrom(cls) ) {
				obj = cls.newInstance();
				if( obj != null )
					return obj;
			}
		}

		//cerca nel package (cui l'interfaccia appartiene) le classi che implementano interfaccia specificata
		List<Class> clsList = SpringClassFinder.allClassesThatImplementsInterface(interface2implements.getPackage(), interface2implements);
		
		//prova ad instanziare la versione di default se specificata
		if( StringUtils.isNotEmpty( defaultVer ) ){
			obj = instanceVersion( clsList, defaultVer );
			if( obj != null )
				return obj;
		}
		
		//se qui prova a instanziare l'utlima
		obj = instanceLast( clsList );
		if( obj != null )
			return obj;
		
		//Se qui alza errore
		throw new ClassCastException("Impossibile creare una nuova instanca di scheda di valutazione: " +
									"Nome Classe = \"" + StringUtils.trimToEmpty( className ) + "\"" +
									"Interfaccia da implementare:" + StringUtils.trimToEmpty(interface2implements.getName())); 
									 
	}
}
