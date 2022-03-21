package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DocfaAnomalie {
	
	private static String[] foglioNull = {"001", "Foglio Docfa NULL",null};
	private static String[] foglioInvalido =  {"002", "Foglio Docfa con formato non valido",null};
	private static String[] particellaNull = {"003", "Particella Docfa NULL",null};
	private static String[] subNull = {"004", "Subalterno Docfa NULL",null};
	private static String[] noDocfaDatiCensuari = {"005", "Dati censuari associati al DOCFA, assenti",null};
	private static String[] dataDocfaNull = {"006", "Data Docfa NULL",null};
	private static String[] noDocfaUiu = {"007", "Immobile presente solo nei dati censuari del docfa. Progressivo Docfa non disponibile(NR_PROG == NULL).",null};
	private static String[] datiCensuariRipetuti = {"008", "Presenza di dati censuari ripetuti per lo stesso docfa",null};
	private static String[] anomaliaConsistenza = {"009", "Consistenza dichiarata non coerente con valore atteso.",null};
	private static String[] altriDocfaBeforeIci = {"010", "Presenza di dichiarazioni docfa tra la data del DOCFA corrente e la prima dichiarazione ICI esaminata","I"};
	private static String[] anomaliaClassamentoDocfa = {"011", "Classe dichiarata non coerente con valore atteso ","I"};
	private static String[] dataDocfaDiversaFornitura = {"012", "Data del docfa non coerente con fornitura ",null};
	private static String[] docfaContemporanei = {"013", "Presenza di docfa contemporanei dello stesso tipo, per l'immobile",null};
	
	private static String[] categoriaDocfaErrata = {"014", "Categoria docfa non valida",null};
	private static String[] classeDocfaErrata = {"015", "Classe docfa non valida","I"};
	private static String[] renditaDocfaErrata = {"016", "Rendita docfa non valida","I"};
	private static String[] superficieDocfaErrata = {"017", "Superficie docfa non valida","T"};
	
	private static String[] categoriaIciAnteErrata = {"018", "Categoria ici ante docfa non valida","I"};
	private static String[] classeIciAnteErrata = {"019", "Classe ici ante docfa non valida","I"};
	private static String[] renditaIciAnteErrata = {"020", "Rendita ici ante non valida","I"};

	private static String[] categoriaIciPostErrata = {"021", "Categoria ici post non valida","I"};
	private static String[] classeIciPostErrata = {"022", "Classe ici post non valida","I"};
	private static String[] renditaIciPostErrata = {"023", "Rendita ici post non valida","I"};
	

	private static String tabAnomalie = "DOCFA_ANOMALIE";
	
	private static String SQL_CREATE = "CREATE TABLE DOCFA_ANOMALIE ( "
		+ "ID            VARCHAR2(3 BYTE) NOT NULL, "
		+ "TIPO          VARCHAR2(1 BYTE),  "
		+ "DESCRIZIONE   VARCHAR2(1000 BYTE)" +
	") ";
	
	public static String getTabAnomalie(){
		return tabAnomalie;
	}
	
	public static ArrayList<LinkedHashMap<String, Object>>  getDatiAnomalie(){
		
		ArrayList<LinkedHashMap<String, Object>> dati = new ArrayList<LinkedHashMap<String, Object>>();
		
		dati.add(getDatiAnomalia(foglioNull));
		dati.add(getDatiAnomalia(foglioInvalido));
		dati.add(getDatiAnomalia(particellaNull));
		dati.add(getDatiAnomalia(subNull));
		dati.add(getDatiAnomalia(noDocfaDatiCensuari));
		dati.add(getDatiAnomalia(dataDocfaNull));
		dati.add(getDatiAnomalia(noDocfaUiu));
		dati.add(getDatiAnomalia(datiCensuariRipetuti));
		dati.add(getDatiAnomalia(anomaliaConsistenza));
	
		dati.add(getDatiAnomalia(altriDocfaBeforeIci));
		dati.add(getDatiAnomalia(anomaliaClassamentoDocfa));
		dati.add(getDatiAnomalia(dataDocfaDiversaFornitura));
		dati.add(getDatiAnomalia(docfaContemporanei));
		
		dati.add(getDatiAnomalia(categoriaDocfaErrata));
		dati.add(getDatiAnomalia(classeDocfaErrata));
		dati.add(getDatiAnomalia(renditaDocfaErrata));
		//dati.add(getDatiAnomalia(superficieDocfaErrata));
		
		dati.add(getDatiAnomalia(categoriaIciAnteErrata));
		dati.add(getDatiAnomalia(classeIciAnteErrata));
		dati.add(getDatiAnomalia(renditaIciAnteErrata));
		
		dati.add(getDatiAnomalia(categoriaIciPostErrata));
		dati.add(getDatiAnomalia(classeIciPostErrata));
		dati.add(getDatiAnomalia(renditaIciPostErrata));
		
		return dati;
	}
	
	private static LinkedHashMap<String, Object> getDatiAnomalia(String[] a){
		LinkedHashMap<String, Object> anomalia = new LinkedHashMap<String, Object>();
		
		anomalia.put("ID", getCodiceAnomalia(a));
		anomalia.put("TIPO", getTipoAnomalia(a));
		anomalia.put("DESCRIZIONE", getDescrizioneAnomalia(a));
		
		return anomalia;
	}
	
	public static String getCodFoglioNull() {
		return foglioNull[0];
	}

	public static String getCodFoglioInvalido() {
		return foglioInvalido[0];
	}

	public static String getCodParticellaNull() {
		return particellaNull[0];
	}

	public static String getCodSubNull() {
		return subNull[0];
	}

	public static String getCodNoDocfaDatiCensuari() {
		return noDocfaDatiCensuari [0];
	}

	public static String getCodDataDocfaNull() {
		return dataDocfaNull[0];
	}

	public static String getCodNoDocfaUiu() {
		return noDocfaUiu[0];
	}

	public static String getCodDatiCensuariRipetuti() {
		return datiCensuariRipetuti[0];
	}
	
	public static String getCodAnomaliaConsistenza() {
		return anomaliaConsistenza[0];
	}


	public static String getCodAltriDocfaBeforeIci(){
		return altriDocfaBeforeIci[0];
	}
	
	public static String getCodAnomaliaClassamentoDocfa(){
		return anomaliaClassamentoDocfa[0];
	}
	
	public static String getCodDataDocfaDiversaFornitura(){
		return dataDocfaDiversaFornitura[0];
	}
	
	public static String getCodDocfaContemporanei(){
		return docfaContemporanei[0];
	}
	
	public static String getSQL_CREATE() {
		return SQL_CREATE;
	}

	public static String getCodiceAnomalia(String[] tipoAnomalia){
		return tipoAnomalia[0];
	}
	
	
	public static String getCodCategoriaDocfaErrata() {
		return categoriaDocfaErrata[0];
	}

	public static String getCodClasseDocfaErrata() {
		return classeDocfaErrata[0];
	}

	public static String getCodRenditaDocfaErrata() {
		return renditaDocfaErrata[0];
	}

	public static String getCodSuperficieDocfaErrata() {
		return superficieDocfaErrata[0];
	}

	public static String getCodCategoriaIciAnteErrata() {
		return categoriaIciAnteErrata[0];
	}

	public static String getCodClasseIciAnteErrata() {
		return classeIciAnteErrata[0];
	}

	public static String getCodRenditaIciAnteErrata() {
		return renditaIciAnteErrata[0] ;
	}

	public static String getCodCategoriaIciPostErrata() {
		return categoriaIciPostErrata[0];
	}

	public static String getCodClasseIciPostErrata() {
		return classeIciPostErrata[0];
	}

	public static String getCodRenditaIciPostErrata() {
		return renditaIciPostErrata[0];
	}

	public static String getDescrizioneAnomalia(String[] tipoAnomalia){
		return tipoAnomalia[1];
	}
	
	public static String getTipoAnomalia(String[] tipoAnomalia){
		return tipoAnomalia[2];
	}

	

	

	
	
	
	
	
}
