package it.webred.ct.data.access.basic.common.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringheVie {

	private static HashMap<String,String> mappaToponimi = new HashMap<String,String>();
	
	public static HashMap<String,String> getMappaToponimi(){
		mappaToponimi.put("ALZ", "ALZAGLIA");
		mappaToponimi.put("ALZAGLIA", "ALZAGLIA");
		mappaToponimi.put("BST", "BASTIONI");
		mappaToponimi.put("BASTIONI", "BASTIONI");
		mappaToponimi.put("BAST.NI", "BASTIONI");
		mappaToponimi.put("BST.NI", "BASTIONI");
		mappaToponimi.put("CAV", "CAVALCAVIA");
		mappaToponimi.put("CAVALCAVIA", "CAVALCAVIA");
		mappaToponimi.put("FORO", "FORO");
		mappaToponimi.put("FOR", "FORO");
		mappaToponimi.put("GAL", "GALLERIA");
		mappaToponimi.put("GALLERIA", "GALLERIA");
		mappaToponimi.put("GLL", "GALLERIA");
		mappaToponimi.put("GRD", "GIARDINO");
		mappaToponimi.put("GIARDINO", "GIARDINO");
		mappaToponimi.put("CORSO", "CORSO");
		mappaToponimi.put("C.SO", "CORSO");
		mappaToponimi.put("C SO", "CORSO");
		mappaToponimi.put("FRAZIONE", "FRAZIONE");
		mappaToponimi.put("FRAZ.", "FRAZIONE");
		mappaToponimi.put("FRAZ", "FRAZIONE");
		mappaToponimi.put("FR", "FRAZIONE");
		mappaToponimi.put("FRA", "FRAZIONE");
		mappaToponimi.put("FRAZ.", "FRAZIONE");
		mappaToponimi.put("FRAZ", "FRAZIONE");
		mappaToponimi.put("FRAZ.NE", "FRAZIONE");
		mappaToponimi.put("LARGO", "LARGO");
		mappaToponimi.put("L.GO", "LARGO");
		mappaToponimi.put("LGO", "LARGO");
		mappaToponimi.put("LAR", "LARGO");
		mappaToponimi.put("L.", "LARGO");
		mappaToponimi.put("L", "LARGO");
		mappaToponimi.put("PTA", "PORTA");
		mappaToponimi.put("P.TA", "PORTA");
		mappaToponimi.put("PORTA", "PORTA");
		mappaToponimi.put("PAS", "PASSAGGIO");
		mappaToponimi.put("PASS.GGIO", "PASSAGGIO");
		mappaToponimi.put("PASSAGGIO", "PASSAGGIO");
		mappaToponimi.put("LOCALITA", "LOCALITA'");
		mappaToponimi.put("LOCALITÀ", "LOCALITA'");
		mappaToponimi.put("LOCALITA''", "LOCALITA'");
		mappaToponimi.put("LOC TÀ", "LOCALITA'");
		mappaToponimi.put("LOC TA", "LOCALITA'");
		mappaToponimi.put("LTA", "LOCALITA'");
		mappaToponimi.put("LOC. TA", "LOCALITA'");
		mappaToponimi.put("LOC. TÀ", "LOCALITA'");
		mappaToponimi.put("LOC.TA", "LOCALITA'");
		mappaToponimi.put("LOC.TÀ", "LOCALITA'");
		mappaToponimi.put("LOC.", "LOCALITA'");
		mappaToponimi.put("LOC", "LOCALITA'");
		mappaToponimi.put("PIAZZOLA", "PIAZZOLA");
		mappaToponimi.put("P.LA", "PIAZZOLA");
		mappaToponimi.put("PIAZZALE", "PIAZZALE");
		mappaToponimi.put("P.LE", "PIAZZALE");
		mappaToponimi.put("PLE", "PIAZZALE");
		mappaToponimi.put("PIAZZETTA", "PIAZZA");
		mappaToponimi.put("PIAZZA", "PIAZZA");
		mappaToponimi.put("PIA", "PIAZZA");
		mappaToponimi.put("P ZZA", "PIAZZA");
		mappaToponimi.put("P.ZZA", "PIAZZA");
		mappaToponimi.put("P. ZZA", "PIAZZA");
		mappaToponimi.put("PZA", "PIAZZA");
		mappaToponimi.put("P.", "PIAZZA");
		mappaToponimi.put("P", "PIAZZA");
		mappaToponimi.put("PIAZZETTA", "PIAZZETTA");
		mappaToponimi.put("P.TTA", "PIAZZETTA");
		mappaToponimi.put("STRADA", "STRADA");
		mappaToponimi.put("SRADA", "STRADA");
		mappaToponimi.put("SDA", "STRADA");
		mappaToponimi.put("STR.", "STRADA");
		mappaToponimi.put("STR", "STRADA");
		mappaToponimi.put("SITO", "SITO");
		mappaToponimi.put("SIT", "SITO");
		mappaToponimi.put("S.TO", "SITO");
		mappaToponimi.put("SC", "STRADA COMUNALE");
		mappaToponimi.put("S.C.", "STRADA COMUNALE");
		mappaToponimi.put("SP", "STRADA PROVINCIALE");
		mappaToponimi.put("S.P.", "STRADA PROVINCIALE");
		mappaToponimi.put("SR", "STRADA REGIONALE");
		mappaToponimi.put("S.R.", "STRADA REGIONALE");
		mappaToponimi.put("SS", "STRADA STATALE");
		mappaToponimi.put("S.S.", "STRADA STATALE");
		mappaToponimi.put("STRADA PROVINCIALE", "STRADA PROVINCIALE");
		mappaToponimi.put("STRADA COMUNALE", "STRADA COMUNALE");
		mappaToponimi.put("STRADA REGIONALE", "STRADA REGIONALE");
		mappaToponimi.put("ZONA INDUSTRIALE", "ZONA INDUSTRIALE");
		mappaToponimi.put("ZI", "ZONA INDUSTRIALE");
		mappaToponimi.put("Z.I", "ZONA INDUSTRIALE");
		mappaToponimi.put("Z.I.", "ZONA INDUSTRIALE");
		mappaToponimi.put("VIA", "VIA");
		mappaToponimi.put("V.", "VIA");
		mappaToponimi.put("V", "VIA");
		mappaToponimi.put("VIE", "VIA");
		mappaToponimi.put("VICO", "VICO");
		mappaToponimi.put("VIALE", "VIALE");
		mappaToponimi.put("V.LE", "VIALE");
		mappaToponimi.put("VLE", "VIALE");
		mappaToponimi.put("VICOLO", "VICOLO");
		mappaToponimi.put("V.LO", "VICOLO");
		mappaToponimi.put("VLO", "VICOLO");
		mappaToponimi.put("VOCABOLO", "VOCABOLO");
		mappaToponimi.put("VICOLO", "VICOLO");
		mappaToponimi.put("VICOLETTO", "VICOLO");
		mappaToponimi.put("VIC.", "VICOLO");
		mappaToponimi.put("VOC.", "VOCABOLO");
		mappaToponimi.put("VOC", "VOCABOLO");
		mappaToponimi.put("COLLE", "COLLE");
		mappaToponimi.put("COL.", "COLLE");
		mappaToponimi.put("COLL.", "COLLE");
		mappaToponimi.put("C.DA", "CONTRADA");
		mappaToponimi.put("CONTRADA", "CONTRADA");
		mappaToponimi.put("C/DA", "CONTRADA");
		mappaToponimi.put("CON", "CONTRADA");
		mappaToponimi.put("CONTR", "CONTRADA");
		mappaToponimi.put("TRAVERSA", "TRAVERSA");
		mappaToponimi.put("TRAV.", "TRAVERSA");
		mappaToponimi.put("RIONE", "RIONE");
		mappaToponimi.put("R.NE", "RIONE");
		mappaToponimi.put("STRETTURA", "STRETTO");
		mappaToponimi.put("STRETTOIA", "STRETTO");
		mappaToponimi.put("STRETTA", "STRETTO");
		mappaToponimi.put("STRETTO", "STRETTO");
		mappaToponimi.put("STR.TTO", "STRETTO");
		mappaToponimi.put("-", "-");
	return mappaToponimi;
	};
	
	
	
	public static List<String> getToponimiDecoded(String param){
		ArrayList<String> toponimiDecoded = new ArrayList<String>();
		Iterator i = getMappaToponimi().entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String,String> entry = (Map.Entry<String,String>)i.next();
			if(entry.getValue().equalsIgnoreCase(param))
				toponimiDecoded.add(entry.getKey());
		}
		return toponimiDecoded;
	}
	
	public static String getElencoToponimiDecoded(String param){
		List<String> prefissi = getToponimiDecoded(param);
		String listaPrefissi = "";
		for(int i=0; i<prefissi.size(); i++){
			listaPrefissi += "'"+prefissi.get(i)+"'";
			if(i<prefissi.size()-1)
				listaPrefissi += ", ";
		}
		return listaPrefissi;
	}
	
}
