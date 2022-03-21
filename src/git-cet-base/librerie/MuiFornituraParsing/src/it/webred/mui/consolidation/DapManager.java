package it.webred.mui.consolidation;

import java.util.HashSet;

public class DapManager {
	
	public static HashSet<String> tipologieAbitazione = null;
	static {
		tipologieAbitazione = new HashSet<String>();
		tipologieAbitazione.add("A01");
		tipologieAbitazione.add("A02");
		tipologieAbitazione.add("A03");
		tipologieAbitazione.add("A04");
		tipologieAbitazione.add("A05");
		tipologieAbitazione.add("A06");
		tipologieAbitazione.add("A07");
		tipologieAbitazione.add("A08");
		tipologieAbitazione.add("A09");
	}

	public static boolean isTipologiaImmobileAbitativo(String tipologiaImmobile) {
		return (tipologiaImmobile != null ? tipologieAbitazione
				.contains(tipologiaImmobile.toUpperCase()) : false);

	}
	
}
