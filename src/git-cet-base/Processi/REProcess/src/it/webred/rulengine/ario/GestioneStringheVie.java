package it.webred.rulengine.ario;

import it.webred.rulengine.ario.bean.Civico;
import it.webred.rulengine.ario.bean.Indirizzo;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.dwh.DwhUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.log4j.Logger;

import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWatermanGotohWindowedAffine;
import uk.ac.shef.wit.simmetrics.similaritymetrics.TagLink;

/**
 * @author MARCO
 *
 */
/**
 * @author MARCO
 *
 */
@Deprecated
public class GestioneStringheVie {

	private static HashMap<String, String> sedimario = new HashMap<String, String>();
	private static HashMap<String, String> romanToLetters = new HashMap<String, String>();
	private static Collection<String> ctrSedime = new ArrayList<String>();
	private static final org.apache.log4j.Logger log = Logger.getLogger(GestioneStringheVie.class.getName());
	public static final float SMITHWATERMAN_PERC_LIMITE_SUP = (float) 0.93;
	public static final float TAGLINK_PERC_LIMITE_SUP = (float) 0.93;
	public static final float SMITHWATERMAN_PERC_LIMITE_INF = (float) 0.88;
	public static final float TAGLINK_PERC_LIMITE_INF = (float) 0.88;
	public static final float SMITHWATERMAN_PERC_LIMITE_FIX = (float) 0.90;
	public static final float TAGLINK_PERC_LIMITE_FIX = (float) 0.85;
	
	private static HashMap<String, Indirizzo<String, String>> cacheIndirizziRiconosciuti = new HashMap<String, Indirizzo<String, String>>();
	
	

	
	public GestioneStringheVie() {
		
	}
	
	static
	{
		try
		{


			romanToLetters.put("I","PRIMO");
			romanToLetters.put("II","DUE");
			romanToLetters.put("III","TRE");
			romanToLetters.put("IV","QUATTRO");
			romanToLetters.put("V","CINQUE");
			romanToLetters.put("VI","SEI");
			romanToLetters.put("VII","SETTE");
			romanToLetters.put("VIII","OTTO");
			romanToLetters.put("IX","NOVE");
			romanToLetters.put("X","DIECI");
			romanToLetters.put("XI","UNDICI");
			romanToLetters.put("XII","DODICI");
			romanToLetters.put("XIII","TREDICI");
			romanToLetters.put("XIV","QUATTORDICI");
			romanToLetters.put("XV","QUINDICI");
			romanToLetters.put("XVI","SEDICI");
			romanToLetters.put("XVII","DICIASSETTE");
			romanToLetters.put("XVIII","DICIOTTO");
			romanToLetters.put("XIX","DICIANNOVE");
			romanToLetters.put("XX","VENTI");
			romanToLetters.put("XXI","VENTUNO");
			romanToLetters.put("XXII","VENTIDUE");
			romanToLetters.put("XXIII","VENTITRE");
			romanToLetters.put("XXIV","VENTIQUATTRO");
			romanToLetters.put("XXV","VENTICINQUE");
			romanToLetters.put("XXVI","VENTISEI");
			romanToLetters.put("XXVII","VENTISETTE");
			romanToLetters.put("XXVIII","VENTOTTO");
			romanToLetters.put("XXIX","VENTINOVE");
			romanToLetters.put("XXX","TRENTA");
			romanToLetters.put("XXXI","TRENTUNO");
			
			sedimario.put("ALZ", "ALZAGLIA");
			sedimario.put("ALZAGLIA", "ALZAGLIA");
			sedimario.put("BST", "BASTIONI");
			sedimario.put("BASTIONI", "BASTIONI");
			sedimario.put("BAST.NI", "BASTIONI");
			sedimario.put("BST.NI", "BASTIONI");
			sedimario.put("CAV", "CAVALCAVIA");
			sedimario.put("CAVALCAVIA", "CAVALCAVIA");
			sedimario.put("FORO", "FORO");
			sedimario.put("FOR", "FORO");
			sedimario.put("GAL", "GALLERIA");
			sedimario.put("GALLERIA", "GALLERIA");
			sedimario.put("GLL", "GALLERIA");
			sedimario.put("GRD", "GIARDINO");
			sedimario.put("GIARDINO", "GIARDINO");
			sedimario.put("CORSO", "CORSO");
			sedimario.put("C.SO", "CORSO");
			sedimario.put("C SO", "CORSO");
			sedimario.put("FRAZIONE", "FRAZIONE");
			sedimario.put("FRAZ.", "FRAZIONE");
			sedimario.put("FRAZ", "FRAZIONE");
			sedimario.put("FR", "FRAZIONE");
			sedimario.put("FRA", "FRAZIONE");
			sedimario.put("FRAZ.", "FRAZIONE");
			sedimario.put("FRAZ", "FRAZIONE");
			sedimario.put("FRAZ.NE", "FRAZIONE");
			sedimario.put("LARGO", "LARGO");
			sedimario.put("L.GO", "LARGO");
			sedimario.put("LGO", "LARGO");
			sedimario.put("LAR", "LARGO");
			sedimario.put("L.", "LARGO");
			sedimario.put("L", "LARGO");
			sedimario.put("PTA", "PORTA");
			sedimario.put("P.TA", "PORTA");
			sedimario.put("PORTA", "PORTA");
			sedimario.put("PAS", "PASSAGGIO");
			sedimario.put("PASS.GGIO", "PASSAGGIO");
			sedimario.put("PASSAGGIO", "PASSAGGIO");
			sedimario.put("LOCALITA", "LOCALITA'");
			sedimario.put("LOCALITÀ", "LOCALITA'");
			sedimario.put("LOCALITA''", "LOCALITA'");
			sedimario.put("LOC TÀ", "LOCALITA'");
			sedimario.put("LOC TA", "LOCALITA'");
			sedimario.put("LTA", "LOCALITA'");
			sedimario.put("LOC. TA", "LOCALITA'");
			sedimario.put("LOC. TÀ", "LOCALITA'");
			sedimario.put("LOC.TA", "LOCALITA'");
			sedimario.put("LOC.TÀ", "LOCALITA'");
			sedimario.put("LOC.", "LOCALITA'");
			sedimario.put("LOC", "LOCALITA'");
			sedimario.put("PIAZZOLA", "PIAZZOLA");
			sedimario.put("P.LA", "PIAZZOLA");
			sedimario.put("PIAZZALE", "PIAZZALE");
			sedimario.put("P.LE", "PIAZZALE");
			sedimario.put("PLE", "PIAZZALE");
			sedimario.put("PIAZZETTA", "PIAZZA");
			sedimario.put("PIAZZA", "PIAZZA");
			sedimario.put("PIA", "PIAZZA");
			sedimario.put("P ZZA", "PIAZZA");
			sedimario.put("P.ZZA", "PIAZZA");
			sedimario.put("P. ZZA", "PIAZZA");
			sedimario.put("PZA", "PIAZZA");
			sedimario.put("P.", "PIAZZA");
			sedimario.put("P", "PIAZZA");
			sedimario.put("PIAZZETTA", "PIAZZETTA");
			sedimario.put("P.TTA", "PIAZZETTA");
			sedimario.put("STRADA", "STRADA");
			sedimario.put("SRADA", "STRADA");
			sedimario.put("SDA", "STRADA");
			sedimario.put("STR.", "STRADA");
			sedimario.put("STR", "STRADA");
			sedimario.put("SITO", "SITO");
			sedimario.put("SIT", "SITO");
			sedimario.put("S.TO", "SITO");
			sedimario.put("SC", "STRADA COMUNALE");
			sedimario.put("S.C.", "STRADA COMUNALE");
			sedimario.put("SP", "STRADA PROVINCIALE");
			sedimario.put("S.P.", "STRADA PROVINCIALE");
			sedimario.put("SR", "STRADA REGIONALE");
			sedimario.put("S.R.", "STRADA REGIONALE");
			sedimario.put("SS", "STRADA STATALE");
			sedimario.put("S.S.", "STRADA STATALE");
			sedimario.put("STRADA PROVINCIALE", "STRADA PROVINCIALE");
			sedimario.put("STRADA COMUNALE", "STRADA COMUNALE");
			sedimario.put("STRADA REGIONALE", "STRADA REGIONALE");
			sedimario.put("ZONA INDUSTRIALE", "ZONA INDUSTRIALE");
			sedimario.put("ZI", "ZONA INDUSTRIALE");
			sedimario.put("Z.I", "ZONA INDUSTRIALE");
			sedimario.put("Z.I.", "ZONA INDUSTRIALE");
			sedimario.put("VIA", "VIA");
			sedimario.put("V.", "VIA");
			sedimario.put("V", "VIA");
			sedimario.put("VIE", "VIA");
			sedimario.put("VICO", "VICO");
			sedimario.put("VIALE", "VIALE");
			sedimario.put("V.LE", "VIALE");
			sedimario.put("VLE", "VIALE");
			sedimario.put("VICOLO", "VICOLO");
			sedimario.put("V.LO", "VICOLO");
			sedimario.put("VLO", "VICOLO");
			sedimario.put("VOCABOLO", "VOCABOLO");
			sedimario.put("VICOLO", "VICOLO");
			sedimario.put("VICOLETTO", "VICOLO");
			sedimario.put("VIC.", "VICOLO");
			sedimario.put("VOC.", "VOCABOLO");
			sedimario.put("VOC", "VOCABOLO");
			sedimario.put("COLLE", "COLLE");
			sedimario.put("COL.", "COLLE");
			sedimario.put("COLL.", "COLLE");
			sedimario.put("C.DA", "CONTRADA");
			sedimario.put("CONTRADA", "CONTRADA");
			sedimario.put("C/DA", "CONTRADA");
			sedimario.put("CON", "CONTRADA");
			sedimario.put("CONTR", "CONTRADA");
			sedimario.put("TRAVERSA", "TRAVERSA");
			sedimario.put("TRAV.", "TRAVERSA");
			sedimario.put("RIONE", "RIONE");
			sedimario.put("R.NE", "RIONE");
			sedimario.put("STRETTURA", "STRETTO");
			sedimario.put("STRETTOIA", "STRETTO");
			sedimario.put("STRETTA", "STRETTO");
			sedimario.put("STRETTO", "STRETTO");
			sedimario.put("STR.TTO", "STRETTO");
			sedimario.put("-", "-");

			
			ctrSedime.addAll(sedimario.values());
		    //Aggiungo anche le chiavi di sedimario 
			Set es = sedimario.entrySet();
			Iterator it = es.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = ((String)entry.getKey()).toUpperCase();
				if (!ctrSedime.contains(key)) 
					ctrSedime.add(key);
			}
			// prendo alcuni sedie dalle tabelle sul db
			Connection conn = RulesConnection.getConnection("DWH");
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ArrayListHandler();
			String sqlPrefissoVia  = DwhUtils.getProperty(GestioneStringheVie.class, "sql.prefisso_via");
			
			List<Object[]> results = (List<Object[]>) run.query(conn, sqlPrefissoVia, h);
			for (int i = 0; i < results.size() ; i++) {
				String sedime = ((String) ((Object[])results.get(i))[0]).toUpperCase();
				String sedimeNonTratato=sedime;
				// aggiungo il sedime anche se ha gli spazi (non aggiungo a sedimario i sedimi con gli spazi)
				if (!ctrSedime.contains(sedime)) 
					ctrSedime.add(sedime);

				// aggiungo il sedime a ctrSedime senza spazi
				int posSpazio = sedime.indexOf(" ");
				if (posSpazio!=-1) {
						sedime = sedime.substring(0, posSpazio);
					if (!ctrSedime.contains(sedime)) 
						ctrSedime.add(sedime);
					if (!sedimario.containsKey(sedime))
						sedimario.put(sedime, sedime);
					if (!sedimario.containsKey(sedimeNonTratato))
						sedimario.put(sedimeNonTratato, sedime);
					
				}
					
				
			}


		
		}
		catch (Exception e1)
		{
			log.error("Errore nella costruzione delle tabelle di riferimento sedime vie",e1);
			throw new RuntimeException(e1);
		}
		finally
		{
		}

	}
	
	/**
	 * EFFETTUA IL RESET DELLA CACHE DEGLI INDIRIZZI RICONIOSCIUTI 
	 */
	public static void resetCacheIndirizziRiconosciuti() {
		cacheIndirizziRiconosciuti = new HashMap<String, Indirizzo<String,String>>();
	}
	
	public static String trovaSedimeUnivoco(String sedime){
		
		if (sedime != null)
			return sedimario.get(sedime.toUpperCase());
		else
			return "-";
		
	}
	
	
	public static float similarityStrings(String s1, String s2, boolean consideraPuntato) {
		try {
			if (s1==null || s2==null)
				return 0;
			s1 = s1.toUpperCase();
			s2 = s2.toUpperCase();
			
			float smith = new SmithWatermanGotohWindowedAffine().getSimilarity(s1, s2);
			float tagLink = new TagLink().getSimilarity(s1, s2);
			float perc = (smith + tagLink) / 2;
			if  (smith>= SMITHWATERMAN_PERC_LIMITE_INF  &&
			    tagLink >= TAGLINK_PERC_LIMITE_INF)
				return  perc;
			else if (consideraPuntato) {
				if (ugualiSenzaPuntato(s1, s2))
						return 1;
				else
					return 0;
			} else 
				return 0;
			
		} catch (Exception e) {
			log.warn("Erroe valutando le strighe"+":" + s1 +  ":" + s2  );
			return 0;
		}
			
	}
	
	public static Collection<Civico> restituisciCivico(String input) throws Exception { 
		try {
			
		       Pattern pattern = Pattern.compile("(\\D*)?(\\d+)");
		       
	        
	        Matcher matcher = pattern.matcher( input );
	        LinkedList<String> numeriCivici = new LinkedList<String>();
	        String note=null;
	        boolean contieneKm=false;
	        int numeroFind =1;
	        int endOld=0;
	        String indirizzoComposto = "";
	        while ( matcher.find() ) 
	        {
	        	String substr = matcher.group();
	        	System.out.println(substr);
	        	
	            int start = matcher.start();
	            int end = matcher.end();
	            
	            // controllo sul contenuto di stringhe non valide per il numero civico
	            boolean contieneStrighe = false;
				String[] c = {"INTERNO","SCALA","PIANO","ANTRONE","CORTILE"};
				// SE IL NUMERO È RELATIVO A UNA SCALA, PIANO ECC ECC ALLORA NON LO SCELGO COME CIVICO
				for(int l=0; l < c.length; l++){
					if (substr.toUpperCase().indexOf(c[l])!=-1) {
						contieneStrighe = true;
						l=c.length;
					}
			    }
				
				boolean giornodelMese = false;
				String[] m = {"GENNAIO","FEBBRAIO","MARZO","APRILE","MAGGIO","GIUGNO","LUGLIO","AGOSTO","SETTEMBRE","OTTOBRE","NOVEMBRE","DICEMBRE"};
				// SE IL NUMERO È RELATIVO A UN GIORNO DEL MESE ALLORA NON E' VALIDO COME CIVICO
				for(int l=0; l < m.length; l++){
					if (input.toUpperCase().indexOf(m[l])> input.indexOf(substr)+ substr.length()) {
						giornodelMese = true;
						l=m.length;
					}
			    }
					
				if (!contieneStrighe && !giornodelMese) {
		            String civico = null;
		            int charat = substr.length();
		            while (charat>1) {
		            	String taglia = substr.substring(charat-1).toUpperCase();
		            	try {
		            		new Integer(taglia);
		            		charat = charat-1;
		            		civico = taglia;
		            	} catch (Exception e) {
		            		charat = 0;
		            	}
		            }

						// controllo che nel caso che sia il secondo numero trovato nell'indirizzo, il primo trovato non sia più di quattro caratteri indietro
						// altrimenti vorrebbe dire che il primo appartiene al nome della via ( es. via 20 settembre 14)
						if (numeroFind==2) {
							// elimino a prescindere nel caso i due numeri sono distanti più di 4 caratteri
							if (end-endOld-civico.length()>4)  {
								numeriCivici.remove(0);
							} else {
								String[] s = {"KM","KILOMETRO","K.M.","K M","KILOMETRI"};
								// SE LA SECONDA SOTTOSTRINGA CONTIENE km è probable che il primo civico non sia un civico ma un numero strada
								for(int l=0; l < s.length; l++){
									if (substr.toUpperCase().indexOf(s[l])!=-1) {
										note = substr;
										contieneKm = true;
										numeriCivici.remove(0);
										l=c.length;
									}
							    }
							}

						}
						if (contieneKm==false)	{
							numeriCivici.add(civico);
							indirizzoComposto = indirizzoComposto + substr;
			    			numeroFind = numeroFind +1;
						}
		    			contieneKm = false;

		    			System.out.println(civico + ":" + start + "-" + end);
		        }
				endOld = end;
	        }
	        
	        // tolgo dalla stringa in input la parte riguardante il civico in modo da poter ridare indietro anche la via
	        Indirizzo<String, String> ind =  null;
	        if (numeriCivici.size()>0) {
				// PREVENGO IL CASO DI + CIVICI UGUALI UNO VICINO ALL'ALTRO
	        	String indirizzo = indirizzoComposto;
	        	String civOld = numeriCivici.get(numeriCivici.size()-1);
	        	for(int l=numeriCivici.size()-1 ; l > -1 ; l--){
//	        		if (numeriCivici.get(l).equals(civOld)) {
	    	        	int lastpos = indirizzo.lastIndexOf(numeriCivici.get(l));
	    	        	indirizzo=indirizzo.substring(0, lastpos).trim();
		        		civOld = numeriCivici.get(l);
//	        		} else
//	        			l=-1; // esco
	        			
			    }
	        	ind = restituisciIndirizzo(indirizzo, null);

	        } else  {
	        	ind = restituisciIndirizzo(input, null);
	        	numeriCivici.add("0");
	        }
	        
	        if (ind==null){
	        	//throw new RulEngineException("Errore trasformazione civico:" + input);
	        	log.error("Errore trasformazione civico:" + input);
	        	Collection civiciErr =  new ArrayList<Civico>();
	        	Civico civicoErr= new Civico(input, null,null);
	        	civicoErr.setNote("Errore trasformazione civico");
	        	civiciErr.add(civicoErr);
	        	return civiciErr;
	        	
	        }
			String[] nc = {" NR","NR.","NUMERO","N."," N","SNC","SENZA NUMERO","SENZA NUMERO CIVICO","S.N.C.","KM","KILOMETRO","KILOMETRI","K.M.","K M"};
			// SE IL NUMERO È RELATIVO A UNA SCALA, PIANO ECC ECC ALLORA NON LO SCELGO COME CIVICO
			for(int l=0; l < nc.length; l++){
				String indx =  ind.getInd();
				if (indx.toUpperCase().trim().endsWith((nc[l]))) {
					indx = indx.substring(0,indx.lastIndexOf(nc[l]));
					ind.setInd(indx);
					l=nc.length;
				}
		    }

			
	        Collection civici =  new ArrayList<Civico>();
			for(int i=0; i < numeriCivici.size(); i++){
				Civico c = new Civico(ind.getSed(), ind.getInd(),numeriCivici.get(i));
				c.setNote(note);
				civici.add(c);
		    }
			
			return civici;
	        
		} catch (Exception e) {
			log.error("Errore trasformazione civico" + input,e);
			//throw new Exception(e);
			Collection civiciErr =  new ArrayList<Civico>();
        	Civico civicoErr= new Civico(input, null,null);
        	civicoErr.setNote("Errore trasformazione civico:"+e);
        	civiciErr.add(civicoErr);
        	return civiciErr;
		}
	}

	
	/**
	 * Toglie gli accenti e li sostituisce con normali lettere ascii
	 * @param stringa
	 * @return
	 */
	private static String normalizzoAccentiAndUpper(String stringa) {
		stringa = stringa==null?"-":stringa.toUpperCase().trim();
		
		
		String UPPERCASE_ASCII =
		    "AEIOU" // grave
		    + "AEIOUY" // acute
		    + "AEIOUY" // circumflex
		    + "AON" // tilde
		    + "AEIOUY" // umlaut
		    + "A" // ring
		    + "C" // cedilla
		    + "OU" // double acute
		    ;

		// stesso numero di caratteri di quella sopra
		  String UPPERCASE_UNICODE =
		    "\u00C0\u00C8\u00CC\u00D2\u00D9"
		    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
		    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
		    + "\u00C3\u00D5\u00D1"
		    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
		    + "\u00C5"
		    + "\u00C7"
		    + "\u0150\u0170"
		    ;

		  StringBuilder sb = new StringBuilder();
	       int n = stringa.length();
	       for (int i = 0; i < n; i++) {
	          char c = stringa.charAt(i);
	          int pos = UPPERCASE_UNICODE.indexOf(c);
	          if (pos > -1){
	            sb.append(UPPERCASE_ASCII.charAt(pos));
	            sb.append("'");
	          }
	          else {
	            sb.append(c);
	          }
	       }
	       return sb.toString();

		

	}
	
	/**
	 * Dato un indirizzo : "via degli sciri" , il metodo restituisce il sedime (univoco da hash)
	 * e il nome via separatamente
	 * Se vengono forniti uno o più sedime di riferimento il metodo semplicemente non ricerca il sedime ma toglie quello di riferimento dalla stringa
	 * @param descrInd
	 * @param sedimeRif -  un eventuale sedime già attribuito come valore separato all'indirizzo (viene preso in considerazione in modo subordinato rispetto a quello contenuto nell'indirizzo)
	 * @return
	 * @throws Exception 
	 */
	public static Indirizzo<String,String> restituisciIndirizzo(String ind, String sedimeRif) throws Exception {
		
		String descrInd = ind;
		// controllo se indirizzo presente in cache
		if (cacheIndirizziRiconosciuti.containsKey(ind))
			return cacheIndirizziRiconosciuti.get(ind);
		

		String sedimeOk = null;
		String sedime = null;
		
		descrInd = normalizzoAccentiAndUpper(descrInd);
		// normalizzo mese
		descrInd= normalizzaGiorniMese(descrInd);
		
		String indirizzo = descrInd;
		
			
		try {
			
			if (sedimeRif!=null)
				sedimeRif = sedimeRif.toUpperCase();
			
			
			String[] predicati ={"DEI", "DEGLI", "DEL", "DELLO", "DELLA" ,"DELLE", "AL", "AGLI", "ALLA","ALLE","FRA","TRA","SU","PER","DI","A", "DA","IN" , "CON", "NEL","NELL'","NELLO","NELLA","NEI","NEGLI","NELLE", "DAL","DALL'","DALLA","DAI","DAGLI","DALLE" };
			descrInd = descrInd.trim();

			int posSpazio = descrInd.lastIndexOf(" ");
			int posSpazio2 = descrInd.lastIndexOf(".");
			if (posSpazio2>posSpazio)
				posSpazio = posSpazio2;
			
			while (posSpazio>0) {
				String sottoS = descrInd.substring(0, posSpazio).toUpperCase();
				if (ctrSedime.contains(sottoS)) {
					sedime = descrInd.substring(0, posSpazio);
					String articoloInSedime=null;
					for (int ii=0 ; ii<predicati.length;ii++) {
						if (sedime.endsWith(predicati[ii])) {
							articoloInSedime = predicati[ii];
							ii=predicati.length;
						}
					}
					String sedime1 = trovaSedimeUnivoco(sedime);
					sedimeOk = null;
					//cerco descrizione univoca per il sedime
					if (sedime1 != null)
						sedimeOk = sedime1;
					if (sedimeOk!=null && descrInd.startsWith(sedimeOk)) {
						if (articoloInSedime!=null)
							indirizzo = descrInd.substring(sedimeOk.length()).trim();
						else
							indirizzo = descrInd.substring(sedime.length()).trim();
					}
					else {
						indirizzo = descrInd.substring(posSpazio).trim();
					}
					if (indirizzo.startsWith("."))
						indirizzo = indirizzo.substring(1);
							
					posSpazio=-1;
				} else {
					posSpazio = sottoS.lastIndexOf(" ");
					posSpazio2 = sottoS.lastIndexOf(".");
					if (posSpazio2>posSpazio)
						posSpazio = posSpazio2;			
				}
			}
					
		} catch (Exception e) {
			log.error("Errore trasformazione indirizzo" + descrInd,e);
			throw new Exception(e);
		}
		Indirizzo<String, String> ret = null;
		if (sedimeOk==null && sedimeRif!=null) {
			ret =  restituisciIndirizzo(sedimeRif + " " + ind,null);
			cacheIndirizziRiconosciuti.put(ind, ret);
			return ret;
		}
		sedimeOk = sedimeOk==null?"-":sedimeOk.toUpperCase();
		// TOLGO UNA EVENTUALE PUNTEGGIATURA PRESENTE ALLA FINE DELLA STRINGA INDIRIZZO
		String[] punt = {",",".",";","-",":",",,"};
		for(int l=0; l < punt.length; l++){
			String indx =  indirizzo;
			if (indx.toUpperCase().trim().endsWith((punt[l]))) {
				indx = indx.substring(0,indx.lastIndexOf(punt[l]));
				indirizzo = indx;
				l=punt.length;
			}
	    }

		ret =  new Indirizzo<String, String>(sedimeOk,indirizzo);
		cacheIndirizziRiconosciuti.put(ind, ret);
		
		return ret;

	}
	
	/**
	 * Converts decimal numer to roman's format
	 * @param decimal
	 * @return
	 */
	public static String decimalToRoman(int decimal) {
		 int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		 String[] numeral = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		 String result ="";
			// Loop through each of the values to diminish the number
			for ( int i = 0; i < 13; i++ ) {
				 while ( decimal >= value[i]) {
				        decimal -= value[i];
				        result += numeral[i];
			    }
			}
		return result;
	}
	
	
	

	
	/*
	 * PRENDE IN UNPUT UN INDIRIZZO E, EVENTUALMENTE ABBIA ALL'INTERNO UN GIORNO DEL MESE CON INDICATO IL MESE
	 * L'indirizzo viene normalizzato in lettere. XX SETTEMBRE --> VENTI SETTEMBRE , 20 OTTOBRE --> VENTI OTTOBRE ECC. ECC.
	 * @param indirizzo
	 * @return
	 */
	public static String normalizzaGiorniMese(String indirizzo) {
		String ret = indirizzo;
		try {
			// Create a pattern to match breaks
	        Pattern p = Pattern.compile("(\\b(?:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\s(?:GENNAIO|FEBBRAIO|MARZO|APRILE|MAGGIO|GIUGNO|LUGLIO|AGOSTO|SETTEMBRE|OTTOBRE|NOVEMBRE|DICEMBRE))");
	        // Create a matcher with an input string
	        Matcher m = p.matcher(indirizzo);
	        StringBuffer sb = new StringBuffer();
	        boolean result = m.find();
	        // Loop through and create a new String 
	        // with the replacements
	        while(result) {
	        	String substr = m.group();
		        	// prendo la sottostringa e sostituisco al numero le lettere
		        	Pattern p1 = Pattern.compile("\\b(?:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\s");
			        Matcher m1 = p1.matcher(substr);
			        while (m1.find()) {
				        String numero = m1.group();
				        numero = numero.trim();
				        String roman = decimalToRoman(Integer.parseInt(numero));
				        String lettere = romanToLetters.get(roman);
				        ret = indirizzo.replaceFirst(substr,substr.replaceFirst(numero,lettere));
			        }
	        	result = m.find();
	        }        
		} catch (Exception e) {
			ret = indirizzo;
		}

		try {
			// Create a pattern to match breaks
	        Pattern p = Pattern.compile("(\\b(?:I|II|III|IV|V|VI|VII|VIII|IX|X|XI|XII|XIII|XIV|XV|XVI|XVIII|XIX|XX|XXI|XXII|XXIII|XXIV|XXV|XXVI|XXVII|XXVIII|XXIX|XXX|XXXI)\\s(?:GENNAIO|FEBBRAIO|MARZO|APRILE|MAGGIO|GIUGNO|LUGLIO|AGOSTO|SETTEMBRE|OTTOBRE|NOVEMBRE|DICEMBRE))");	
	        // Create a matcher with an input string
	        Matcher m = p.matcher(indirizzo);
	        StringBuffer sb = new StringBuffer();
	        boolean result = m.find();
	        // Loop through and create a new String 
	        // with the replacements
	        while(result) {
	        	String substr = m.group();
	        	// prendo la sottostringa e sostituisco al numero le lettere
	        	Pattern p1 = Pattern.compile("\\b(?:I|II|III|IV|V|VI|VII|VIII|IX|X|XI|XII|XIII|XIV|XV|XVI|XVIII|XIX|XX|XXI|XXII|XXIII|XXIV|XXV|XXVI|XXVII|XXVIII|XXIX|XXX|XXXI)\\s");
		        Matcher m1 = p1.matcher(substr);
		        while (m1.find()) {
			        String roman = m1.group();
			        roman = roman.trim();
			        String lettere = romanToLetters.get(roman);
			        ret = indirizzo.replaceFirst(substr,substr.replaceFirst(roman,lettere));
		        }

	        	result = m.find();
	        }        
		} catch (Exception e) {
			ret = indirizzo;
		}
		return ret;
	}
	


	
	

	public static boolean ugualiSenzaPuntato(String indirizzo, String indirizzo2) {
		// metto gli spazi davanti ai punti e agli apostrofi, per normalizzare
		// poi splitto
		String ind1 = indirizzo;
		String ind2 = indirizzo2;
		ind1 = ind1.replaceAll("[\\.]", ". ");
		ind1 = ind1.replaceAll("[\\']", "' ");
		ind2 = ind2.replaceAll("[\\.]", ". ");
		ind2 = ind2.replaceAll("[\\']", "' ");
		
		String u[] = ind1.split("[\\s]+");
		String v[] = ind2.split("[\\s]+");
	

		
		if (u.length!=v.length)
			return false;
		boolean ok = confrontaVettoriStringhe(u, v);
		if (ok)
			return ok;
		try {
				// inverto l'ultima parola di un vettore con la prima, magari il puntato sta in fondo
				
				String[] vInvertito = giraElmetiVettore(v);
			    
				ok = confrontaVettoriStringhe(u, vInvertito);
				if (ok)
					return ok;
		} catch (Exception e) {
			return false;
		}
		
		try {
			// inverto anche l'altro vettore , ci provo !
			String[] uInvertito = giraElmetiVettore(u);
			ok = confrontaVettoriStringhe(uInvertito,v);
			if (ok)
				return ok;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	private static String[] giraElmetiVettore(String[] v) {
		String[] vInvertito = new String[v.length];
		vInvertito = v.clone();
		String ultima = v[v.length-1]; 
		for (int i = vInvertito.length-1; i > 0 ; i--) {
			vInvertito[i] = vInvertito[i-1];
		 }
		vInvertito[0] = ultima;
		return vInvertito;
	}
	
	
	/**
	 * verifica che il primo elemento di un vettore abbia il puntato e l'altro abbia come iniziale delle prima parola
	 * la lettera puntata sul primo vettore (u)
	 * @param u
	 * @param v
	 * @return
	 */
	private static boolean confrontaVettoriStringhe(String[] u, String[] v) {
		// la prima parola di uno puntata è uguale alla prima parola dell'altro indirizzo
		String vPuntato = v[0].substring(0,1) + ".";
		String uPuntato = u[0].substring(0,1) + ".";
		if (!u[0].equals(vPuntato)
	    		&& !v[0].equals(uPuntato))
			return false;
		
		// le altre parole devono essere uguali
	    for (int i = 1; i < u.length ; i++) {
	    	try {
	        	if (!u[i].equals(v[i]))
	        		return false;
	    	} catch (Exception e) {
	    		return false;
	    	}
	    }
	    return true;
	}

	
	public static void main(String[] sS)
	throws Exception
	{

		String a= null;
		try 	{
			throw new Exception();
		} catch (Exception e) {
			a = "aaaaa";
		} finally {
			System.out.print("sssss"+a);
		}

			
	/*	Collection<Civico> numeroCivico = restituisciCivico("VIA S. PAOL0NO 28");
		 for (Iterator iterator1 = numeroCivico.iterator(); iterator1.hasNext();) {
      	    Civico c = (Civico) iterator1.next();
			float perc = similarityStrings( (String)c.getInd(), "SAN PAOLINO", true);
			System.out.println(perc);
			
		 }
*/
	}
	
}
