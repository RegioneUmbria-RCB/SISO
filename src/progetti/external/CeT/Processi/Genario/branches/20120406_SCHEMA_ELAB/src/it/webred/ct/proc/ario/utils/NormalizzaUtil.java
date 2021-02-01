package it.webred.ct.proc.ario.utils;

import it.webred.ct.data.model.indice.SitFabbricatoTotale;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.caricatori.CaricatoriException;
//import it.webred.ct.proc.ario.bean.SitOggettoTotale;
//import it.webred.ct.proc.ario.bean.SitSoggettoTotale;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.utils.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @author Bravi Alessandro
 *
 */

public class NormalizzaUtil {

	public static Properties propsTitoli = null;
	public static Properties propsCarSporchi = null;
	
	protected static final Logger log = Logger.getLogger(NormalizzaUtil.class.getName());
	
	
	static{
		
		//Caricamento del file di properties dei titoli 
		propsTitoli = new Properties();
		propsCarSporchi = new Properties();
		
		try {			
			InputStream is = NormalizzaUtil.class.getResourceAsStream("../normalizzazione/titoli.properties");
		    propsTitoli.load(is);   		    		    
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}		
		
	}
	
	
	/*
	 * METODI CHE METTONO IN MAIUSCOLO I CAMPI DEI BEAN TOTALI
	 */		
	public static void getUpperSoggetti(SitSoggettoTotale sst) throws Exception{
						
		try{
			
			//Normalizzo gli accenti e metto in maiuscolo				
			sst.setCognome(normalizzaAccentiAndUpper(sst.getCognome()));
			sst.setNome(normalizzaAccentiAndUpper(sst.getNome()));						
			sst.setDenominazione(normalizzaAccentiAndUpper(sst.getDenominazione()));
			sst.setSesso(normalizzaAccentiAndUpper(sst.getSesso()));
			sst.setCodfisc(normalizzaAccentiAndUpper(sst.getCodfisc()));
			sst.setPi(normalizzaAccentiAndUpper(sst.getPi()));
			sst.setTipoPersona(normalizzaAccentiAndUpper(sst.getTipoPersona()));		
			sst.setDescProvinciaNascita(normalizzaAccentiAndUpper(sst.getDescProvinciaNascita()));
			sst.setDescComuneNascita(normalizzaAccentiAndUpper(sst.getDescComuneNascita()));			
			sst.setDescProvinciaRes(normalizzaAccentiAndUpper(sst.getDescProvinciaRes()));
			sst.setDescComuneRes(normalizzaAccentiAndUpper(sst.getDescComuneRes()));								
			sst.setRelDescr(normalizzaAccentiAndUpper(sst.getRelDescr()));
					
		}catch(Exception e){
			throw e;
		}
						
	}
	
	public static void getUpperOggetti(SitOggettoTotale sot) throws Exception{
				
		try{
			
			//Normalizzo gli accenti e metto in maiuscolo
			sot.setFoglioUrbano(normalizzaAccentiAndUpper(sot.getFoglioUrbano()));			
			sot.setPiano(normalizzaAccentiAndUpper(sot.getPiano()));
			sot.setCategoria(normalizzaAccentiAndUpper(sot.getCategoria()));
			sot.setZona(normalizzaAccentiAndUpper(sot.getZona()));
			sot.setInterno(normalizzaAccentiAndUpper(sot.getInterno()));
			sot.setRelDescr(normalizzaAccentiAndUpper(sot.getRelDescr()));
			sot.setNote(normalizzaAccentiAndUpper(sot.getNote()));
			sot.setSub(normalizzaAccentiAndUpper(sot.getSub()));
			sot.setClasse(normalizzaAccentiAndUpper(sot.getClasse()));
			sot.setScala(normalizzaAccentiAndUpper(sot.getScala()));
			sot.setRendita(normalizzaAccentiAndUpper(sot.getRendita()));
			sot.setFoglio(normalizzaAccentiAndUpper(sot.getFoglio()));					
			sot.setParticella(normalizzaAccentiAndUpper(sot.getParticella()));
			sot.setSuperficie(normalizzaAccentiAndUpper(sot.getSuperficie()));
			sot.setSezione(normalizzaAccentiAndUpper(sot.getSezione()));
			
			
		}catch(Exception e){
			throw e;
		}
			
	}
	
	
	public static void getUpperVie(SitViaTotale svt) throws Exception{				
				
		try{

			//Normalizzo gli accenti e metto in maiuscolo
			svt.setNote(normalizzaAccentiAndUpper(svt.getNote()));
			svt.setSedime(normalizzaAccentiAndUpper(svt.getSedime()));									
			svt.setIndirizzo(normalizzaAccentiAndUpper(svt.getIndirizzo()));					
			
		}catch(Exception e){
			throw e;
		}
				
	}
	
	
	
	/**
	 * Metodo per la separazione dei titoli nella descrizione soggetti (es: sig. sig.ra ...)	 
	 */
	public static String separaTitoli(String dato) throws Exception{
		
		
		String val = null;	
		String titolo = null;
		boolean trovato = false;		
				
		
		if(dato != null && !dato.equals("")){
			
			val = dato.toUpperCase();			
			
			try{
				
							
				List<String> listaTitoli = new ArrayList<String>();
				
				//Recupero la lista dei titoli da properties
				for (int i = 0; i < propsTitoli.size(); i++) {					
					listaTitoli.add(propsTitoli.getProperty(Integer.toString(i)).toUpperCase());					
				}
				
				//Metto i titoli in ordine decrescente per lunghezza
				Object[] vett = ordinaVettore(listaTitoli.toArray(), propsTitoli.size());
								
				
				for (int i = 0; i < propsTitoli.size(); i++) { 
											
					titolo = vett[i].toString();
					
					if(titolo.equals(val)){						
						break;
					}else{
						int lungTitolo = titolo.length();
						int lungValore = val.length();
						
						boolean interrompi = false;					
						for(int j=0; j<=lungTitolo-1; j++){
							
							if( j>=lungValore){
								interrompi = true;
								break;
							}
							
							String car1 = titolo.subSequence(j,j+1).toString();
							String car2 = val.subSequence(j,j+1).toString();
							
							if(!car1.equals(car2)){
								interrompi = true;
								break;
							}					
						}
						
						//Elimino il titolo dal campo 
						if (!interrompi){			
							String[] splitString = val.split(titolo);																		
							val = splitString[1].trim();						
							break;
						}					
					}															
				}
														
			}catch(Exception e){
				throw e;
			}
		}
			
		return val;
						
	}
			
	
	/**
	 * Toglie gli accenti e li sostituisce con normali lettere ascii e mette tutto in Upper
	 * @param stringa
	 * @return
	 */
	public static String normalizzaAccentiAndUpper(String stringa) throws Exception{
		
		stringa = stringa==null?"":stringa.toUpperCase().trim();							
		
		//Converto gli accenti inversi e sequenze particolari
		stringa = stringa.replaceAll("`", "'");
		stringa = stringa.replaceFirst("\\?A'", "");
		stringa = stringa.replaceFirst("IA'", "I");
		//stringa = stringa.replaceAll("' ", "'");
		//stringa = stringa.replaceAll(". ", ".");
		stringa = stringa.replaceAll(String.valueOf((char)160),"");		//non-breaking space &nbsp;
		
		try {			   
		    String source = stringa;
		   
		    String UPPERCASE_UNICODE ="\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D0" +
									  "\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D7\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF\u00E0\u00E1" +
									  "\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2" +
									  "\u00F3\u00F4\u00F5\u00F6\u00F7\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\u00FF\u0021\u0023\u0024\u0025" +
									  "\u0026\u0028\u0029\u002A\u002B\u002C\u002D\u002E\u002F\u003A\u003B\u003C\u003D\u003E\u003F\u0040\u005D" +
									  "\u005B\u005E\u005F\u0060\u007B\u007C\u007D\u007E\u00A1\u00A2\u00A3\u00A4\u00A5\700A6\u00A7\u00A8\u00A9" +
									  "\u00AA\u00AB\u00AC\u00AE\u00AF\u00B0\u00B1\u00B2\u00B3\u00B5\u00B6\u00B7\u00B8\u00B9\u00BB\u00BC\u00BD" +
									  "\u00BE\u00BF\u02BA\u0150\u0170";
		    
		  
		    StringBuffer sb = new StringBuffer();
			int n = stringa.length();				      
			String result = stringa;
			for (int i = 0; i < n; i++) {
				char c = stringa.charAt(i);
				String g = String.valueOf(c);
				int pos = UPPERCASE_UNICODE.indexOf(c);
				if (pos > -1){
				       	  
					if (g.equals("\u00C0") || g.equals("\u00C1") || g.equals("/u00C2") || g.equals("/u00C3") || g.equals("/u00C4") || g.equals("/u00C5")) {							       				        		 
						result = result.replaceFirst(g, "A"); 
					}
            	    else if (g.equals("\u00C8") || g.equals("\u00C9") || g.equals("\u00CA") || g.equals("\u00CB")) {							      
            	    	result = result.replaceFirst(g, "E"); 
					}
					else if (g.equals("\u00CC") || g.equals("\u00CD") || g.equals("\u00CE") || g.equals("\u00CF")) {							       
						result = result.replaceFirst(g, "I"); 
					}
					else if (g.equals("\u00D0")) {
						result = result.replaceFirst(g, "D");   							       
					}
					else if (g.equals("\u00D1")) {							       
						result = result.replaceFirst(g, "N"); 
					}
					else if (g.equals("\u00D2") || g.equals("\u00D3") || g.equals("\u00D4") || g.equals("\u00D5") || g.equals("\u00D6")) {						     
						result = result.replaceFirst(g, "O"); 
					}
					else if (g.equals("\u00D9") || g.equals("\u00DA") || g.equals("\u00DB") || g.equals("\u00DC")) {					       
						result = result.replaceFirst(g, "U"); 
					}
					else if (g.equals("\u00DD")) {
						result = result.replaceFirst(g, "Y");  							       
					}
					else if (g.equals("\u00E0") || g.equals("\u00E1") || g.equals("\u00E2") || g.equals("\u00E3") || g.equals("\u00E4") || g.equals("\u00E5")) {							       
						result = result.replaceFirst(g, "a"); 
					}
					else if (g.equals("\u00E8") || g.equals("\u00E9") || g.equals("\u00EA") || g.equals("\u00EB")) {							       
				    	result = result.replaceFirst(g, "e"); 
					}
					else if (g.equals("\u00EC") || g.equals("\u00ED") || g.equals("\u00EE") || g.equals("\u00EF") ) {							      
						result = result.replaceFirst(g, "i"); 
					}
					else if (g.equals("\u00F1")) {							       
						result = result.replaceFirst(g, "n"); 
					}
					else if (g.equals("\u00F2") || g.equals("\u00F3") || g.equals("\u00F4") || g.equals("\u00F5") || g.equals("\u00F6")) {							     
						result = result.replaceFirst(g, "o"); 
					}
					else if (g.equals("\u00F9") || g.equals("\u00FA") || g.equals("\u00FB") || g.equals("\u00FC") ) {						        
						result = result.replaceFirst(g, "u"); 
					}
					else if (g.equals("\u00FD") || g.equals("\u00FF")) {						       
						result = result.replaceFirst(g, "y"); 
					}
					else if (g.equals("\u0060")) {						       
						result = result.replaceFirst(g, "'"); 
					}
					else if (g.equals("\u00FE") || g.equals("\u00C6") || g.equals("\u00C7") || g.equals("\u00D7") || g.equals("\u00D8") || g.equals("\u00DE") ||
							 g.equals("\u00DF") || g.equals("\u00E6") || g.equals("\u00E7") || g.equals("\u00F0") || g.equals("\u0021") || 
							 g.equals("\u0023") || g.equals("\u0024") || g.equals("\u0025") || g.equals("\u0026") || g.equals("\u0028") || g.equals("\u0029") ||
							 g.equals("\u002A") || g.equals("\u002B") || g.equals("\u002C") || g.equals("\u002D") /*PUNTO || g.equals("\u002E")*/|| g.equals("\u002F") ||
							 g.equals("\u003A") || g.equals("\u003B") || g.equals("\u003C") || g.equals("\u003D") || g.equals("\u003E") || g.equals("\u003F") ||
							 g.equals("\u0040") ||						 
							 g.equals("\u005D") ||
							 g.equals("\u005B") || g.equals("\u005E") || g.equals("\u005F") ||
							 g.equals("\u007B") || g.equals("\u007C") || g.equals("\u007D") || g.equals("\u007E") || g.equals("\u00A1") || g.equals("\u00A2") ||
							 g.equals("\u00A3") || g.equals("\u00A4") || g.equals("\u00A5") || g.equals("\700A6") || g.equals("\u00A7") || g.equals("\u00A8") ||
							 g.equals("\u00A9") || g.equals("\u00AA") || g.equals("\u00AB") || g.equals("\u00AC") || g.equals("\u00AE") || g.equals("\u00AF") ||
							 g.equals("\u00B0") || g.equals("\u00B1") || g.equals("\u00B2") || g.equals("\u00B3") || g.equals("\u00B4") || g.equals("\u00B5") ||
							 g.equals("\u00B6") || g.equals("\u00B7") || g.equals("\u00B8") || g.equals("\u00B9") || g.equals("\u00BA") || g.equals("\u00BB") ||
							 g.equals("\u00BC") || g.equals("\u00BD") || g.equals("\u00BE") || g.equals("\u00BF") || g.equals("\u02BA") || g.equals("\u0150") || g.equals("\u0170")) {
							       							
									 g="\\"+g; 
									 result = result.replaceFirst(g, ""); 
					}else {
						sb.append(c);
					}					        	  					     
				}else {
					sb.append(c);
				}
			}
		    
			return sb.toString();  	
			
		  }catch(Exception e){
			  throw new Exception("Errore in Gestione conversione accenti e caratteri: ", e);
		  }
			
		 	        	  			
		  //finally {		    
		  //}	
	       
	}
	
	/*public static String normalizzaAccentiAndUpper(String stringa) throws Exception{
		
		stringa = stringa==null?"":stringa.toUpperCase().trim();							
		
		//Elimino eventuali caratteri sporchi
    	stringa = stringa.replaceAll("`", "'");
		stringa = stringa.replaceFirst("\\?A'", "");
		stringa = stringa.replaceFirst("IA'", "I");
		stringa = stringa.replaceAll("°", "");
		stringa = stringa.replaceAll("©", "");
		
		//stringa = stringa.replaceAll("' ", "'");
		//stringa = stringa.replaceAll(". ", ".");
		stringa = stringa.replaceAll(String.valueOf((char)160),"");		//non-breaking space &nbsp;
		
		// List<String> lista = new ArrayList<String>();
                
        //Recupero la lista dei caratteri da properties
		//for (int i = 0; i < propsCarSporchi.size(); i++) {					
		//	lista.add(propsCarSporchi.getProperty(Integer.toString(i)).toUpperCase());					
		//}
        	       	        	        	        	    
        //Iterator<String> iterator = lista.iterator();
        //while(iterator.hasNext())
        //{
        //    String carSporco = iterator.next();	          
        //   	stringa = stringa.replaceAll(carSporco, "");		          	            
       // }				
		
		
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
	       
	}*/
	
	
	
	public static void paddingOggetto(SitOggettoTotale sot) throws Exception{
				
		try{
			
			//Foglio e Sub senza zeri iniziali
			//sot.setFoglio(StringUtils.padding(sot.getFoglio()!=null?sot.getFoglio().trim():null, 0, ' ', true));
			//sot.setSub(StringUtils.padding(sot.getSub()!=null?sot.getSub().trim():null, 0, ' ', true));
			
			
			//Particella Foglio e Sub con zeri iniziali
			sot.setFoglio(StringUtils.padding(sot.getFoglio()!=null?sot.getFoglio().trim():null, 4, '0', true));
			sot.setSub(StringUtils.padding(sot.getSub()!=null?sot.getSub().trim():null, 4, '0', true));
			sot.setParticella(StringUtils.padding(sot.getParticella()!=null?sot.getParticella().trim():null, 5, '0', true));
			
		}catch (Exception e) {
			throw e;
		}
	
	}
	
	
	
	/**
	 * Metodo che ordina il vettore con algoritmo BubbleSort2
	 */
	private static Object[] ordinaVettore(Object[] v, int numEle){
				
		int I,J;
		Object temp;
		int Scambio;
		Scambio =1; /* entra obbl. nel ciclo la prima volta */
		I=0;
		while (I<numEle-1 && Scambio==1) {
			Scambio =0;
			for (J=0;J<numEle-I-1;J++)	
				if (v[J+1].toString().length() > v[J].toString().length()){
					temp=v[J];
					v[J]=v[J+1];
					v[J+1]=temp;
					Scambio=1; 
				}
			I++;
		}
		
		return v;
	}					
		
	
	
	/**
	 * 
	 * Metodo che mette in UPPER i fabbricati 
	 */
	public static void getUpperFabbricati(SitFabbricatoTotale sft) throws Exception{
		
		try{
			
			//Normalizzo gli accenti e metto in maiuscolo
			sft.setFoglioUrbano(normalizzaAccentiAndUpper(sft.getFoglioUrbano()));			
			sft.setPiano(normalizzaAccentiAndUpper(sft.getPiano()));
			sft.setCategoria(normalizzaAccentiAndUpper(sft.getCategoria()));
			sft.setZona(normalizzaAccentiAndUpper(sft.getZona()));
			sft.setInterno(normalizzaAccentiAndUpper(sft.getInterno()));
			sft.setRelDescr(normalizzaAccentiAndUpper(sft.getRelDescr()));
			sft.setNote(normalizzaAccentiAndUpper(sft.getNote()));			
			sft.setClasse(normalizzaAccentiAndUpper(sft.getClasse()));
			sft.setScala(normalizzaAccentiAndUpper(sft.getScala()));
			sft.setRendita(normalizzaAccentiAndUpper(sft.getRendita()));
			sft.setFoglio(normalizzaAccentiAndUpper(sft.getFoglio()));					
			sft.setParticella(normalizzaAccentiAndUpper(sft.getParticella()));
			sft.setSuperficie(normalizzaAccentiAndUpper(sft.getSuperficie()));
			sft.setSezione(normalizzaAccentiAndUpper(sft.getSezione()));
			
			
		}catch(Exception e){
			throw e;
		}
			
	}
	
	/*
	 * Metodo che padda i fabbricati
	 */
	public static void paddingFabbricato(SitFabbricatoTotale sft) throws Exception{
		
		try{
			
			//Foglio senza zeri iniziali
			//sft.setFoglio(StringUtils.padding(sft.getFoglio()!=null?sft.getFoglio().trim():null, 0, ' ', true));
			
			//Particella e Foglio con zeri iniziali
			sft.setFoglio(StringUtils.padding(sft.getFoglio()!=null?sft.getFoglio().trim():null, 4, '0', true));
			sft.setParticella(StringUtils.padding(sft.getParticella()!=null?sft.getParticella().trim():null, 5, '0', true));
			
		}catch (Exception e) {
			throw e;
		}
	
	}
	
}
