package it.webred.verificaCoordinate.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class GestioneStringheVie {
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(GestioneStringheVie.class.getName());
	
	private static final String SQL_PREFISSO_VIA = "SELECT prefisso FROM SITIDSTR where prefisso is not null " +
             										"union " +
             										"select prefisso from load_strade  where prefisso is not null " +
             										"union " +
             										"select VIASEDIME as prefisso FROM SIT_D_VIA where viasedime is not null";

	private static HashMap<String, String> prefissario = new HashMap<String, String>();
	
	public GestioneStringheVie() {
		
	}
	
	static {
		prefissario.put("ALZ", "ALZAIA");
		prefissario.put("ALZAIA", "ALZAIA");
		prefissario.put("BST", "BASTIONI");
		prefissario.put("BASTIONI", "BASTIONI");
		prefissario.put("BAST.NI", "BASTIONI");
		prefissario.put("BST.NI", "BASTIONI");
		prefissario.put("CAV", "CAVALCAVIA");
		prefissario.put("CAVALCAVIA", "CAVALCAVIA");
		prefissario.put("FORO", "FORO");
		prefissario.put("FOR", "FORO");
		prefissario.put("GAL", "GALLERIA");
		prefissario.put("GALLERIA", "GALLERIA");
		prefissario.put("GLL", "GALLERIA");
		prefissario.put("GRD", "GIARDINO");
		prefissario.put("GIARDINO", "GIARDINO");
		prefissario.put("CORSO", "CORSO");
		prefissario.put("C.SO", "CORSO");
		prefissario.put("C SO", "CORSO");
		prefissario.put("FRAZIONE", "FRAZIONE");
		prefissario.put("FRAZ.", "FRAZIONE");
		prefissario.put("FRAZ", "FRAZIONE");
		prefissario.put("FR", "FRAZIONE");
		prefissario.put("FRA", "FRAZIONE");
		prefissario.put("FRAZ.", "FRAZIONE");
		prefissario.put("FRAZ", "FRAZIONE");
		prefissario.put("FRAZ.NE", "FRAZIONE");
		prefissario.put("LARGO", "LARGO");
		prefissario.put("L.GO", "LARGO");
		prefissario.put("LGO", "LARGO");
		prefissario.put("LAR", "LARGO");
		prefissario.put("L.", "LARGO");
		prefissario.put("L", "LARGO");
		prefissario.put("PTA", "PORTA");
		prefissario.put("P.TA", "PORTA");
		prefissario.put("PORTA", "PORTA");
		prefissario.put("PAS", "PASSAGGIO");
		prefissario.put("PASS.GGIO", "PASSAGGIO");
		prefissario.put("PASSAGGIO", "PASSAGGIO");
		prefissario.put("LOCALITA", "LOCALITA'");
		prefissario.put("LOCALITÁ", "LOCALITA'");
		prefissario.put("LOCALITA''", "LOCALITA'");
		prefissario.put("LOC TÁ", "LOCALITA'");
		prefissario.put("LOC TA", "LOCALITA'");
		prefissario.put("LTA", "LOCALITA'");
		prefissario.put("LOC. TA", "LOCALITA'");
		prefissario.put("LOC. TÁ", "LOCALITA'");
		prefissario.put("LOC.TA", "LOCALITA'");
		prefissario.put("LOC.TÁ", "LOCALITA'");
		prefissario.put("LOC.", "LOCALITA'");
		prefissario.put("LOC", "LOCALITA'");
		prefissario.put("PIAZZOLA", "PIAZZOLA");
		prefissario.put("P.LA", "PIAZZOLA");
		prefissario.put("PIAZZALE", "PIAZZALE");
		prefissario.put("P.LE", "PIAZZALE");
		prefissario.put("PLE", "PIAZZALE");
		prefissario.put("PIAZZETTA", "PIAZZA");
		prefissario.put("PIAZZA", "PIAZZA");
		prefissario.put("PIA", "PIAZZA");
		prefissario.put("P ZZA", "PIAZZA");
		prefissario.put("P.ZZA", "PIAZZA");
		prefissario.put("P. ZZA", "PIAZZA");
		prefissario.put("PZA", "PIAZZA");
		prefissario.put("P.", "PIAZZA");
		prefissario.put("P", "PIAZZA");
		prefissario.put("PIAZZETTA", "PIAZZETTA");
		prefissario.put("P.TTA", "PIAZZETTA");
		prefissario.put("STRADA", "STRADA");
		prefissario.put("SRADA", "STRADA");
		prefissario.put("SDA", "STRADA");
		prefissario.put("STR.", "STRADA");
		prefissario.put("STR", "STRADA");
		prefissario.put("SITO", "SITO");
		prefissario.put("SIT", "SITO");
		prefissario.put("S.TO", "SITO");
		prefissario.put("SC", "STRADA COMUNALE");
		prefissario.put("S.C.", "STRADA COMUNALE");
		prefissario.put("SP", "STRADA PROVINCIALE");
		prefissario.put("S.P.", "STRADA PROVINCIALE");
		prefissario.put("SR", "STRADA REGIONALE");
		prefissario.put("S.R.", "STRADA REGIONALE");
		prefissario.put("SS", "STRADA STATALE");
		prefissario.put("S.S.", "STRADA STATALE");
		prefissario.put("STRADA PROVINCIALE", "STRADA PROVINCIALE");
		prefissario.put("STRADA COMUNALE", "STRADA COMUNALE");
		prefissario.put("STRADA REGIONALE", "STRADA REGIONALE");
		prefissario.put("ZONA INDUSTRIALE", "ZONA INDUSTRIALE");
		prefissario.put("ZI", "ZONA INDUSTRIALE");
		prefissario.put("Z.I", "ZONA INDUSTRIALE");
		prefissario.put("Z.I.", "ZONA INDUSTRIALE");
		prefissario.put("VIA", "VIA");
		prefissario.put("V.", "VIA");
		prefissario.put("V", "VIA");
		prefissario.put("VIE", "VIA");
		prefissario.put("VICO", "VICO");
		prefissario.put("VIALE", "VIALE");
		prefissario.put("V.LE", "VIALE");
		prefissario.put("VLE", "VIALE");
		prefissario.put("VICOLO", "VICOLO");
		prefissario.put("V.LO", "VICOLO");
		prefissario.put("VLO", "VICOLO");
		prefissario.put("VOCABOLO", "VOCABOLO");
		prefissario.put("VICOLO", "VICOLO");
		prefissario.put("VICOLETTO", "VICOLO");
		prefissario.put("VIC.", "VICOLO");
		prefissario.put("VOC.", "VOCABOLO");
		prefissario.put("VOC", "VOCABOLO");
		prefissario.put("COLLE", "COLLE");
		prefissario.put("COL.", "COLLE");
		prefissario.put("COLL.", "COLLE");
		prefissario.put("C.DA", "CONTRADA");
		prefissario.put("CONTRADA", "CONTRADA");
		prefissario.put("C/DA", "CONTRADA");
		prefissario.put("CON", "CONTRADA");
		prefissario.put("CONTR", "CONTRADA");
		prefissario.put("TRAVERSA", "TRAVERSA");
		prefissario.put("TRAV.", "TRAVERSA");
		prefissario.put("RIONE", "RIONE");
		prefissario.put("R.NE", "RIONE");
		prefissario.put("STRETTURA", "STRETTO");
		prefissario.put("STRETTOIA", "STRETTO");
		prefissario.put("STRETTA", "STRETTO");
		prefissario.put("STRETTO", "STRETTO");
		prefissario.put("STR.TTO", "STRETTO");
		prefissario.put("-", "-");
	}
	
	private static HashMap<String, String> leggiPrefissi(Connection conn) {
		HashMap<String, String> myPrefissario = new HashMap<String, String>();
		//leggo i prefissi presenti nell'HashMap di base
		Iterator<String> it = prefissario.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			myPrefissario.put(key, prefissario.get(key));
		}
		//cerco altri prefissi nel DB
		try {
			PreparedStatement ps = conn.prepareStatement(SQL_PREFISSO_VIA);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String prefisso = rs.getString("prefisso");
				if (!myPrefissario.containsKey(prefisso)) {
					myPrefissario.put(prefisso, prefisso);
				}
			}
		} catch (Exception e) {
			log.error("Errore nel recupero dei prefissi dal DB", e);
		}
		return myPrefissario;
	}
	
	public static String trovaPrefissoUnivoco(Connection conn, String prefisso) {
		HashMap<String, String> myPrefissario = leggiPrefissi(conn);
		if (prefisso != null)
			return myPrefissario.get(prefisso.toUpperCase());
		else
			return "-";		
	}
	
}
