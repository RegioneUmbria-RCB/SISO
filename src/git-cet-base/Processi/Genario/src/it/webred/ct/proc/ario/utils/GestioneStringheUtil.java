package it.webred.ct.proc.ario.utils;

import it.webred.ct.proc.ario.bean.Oggetto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * @author Bravi Alessandro
 *
 */

public class GestioneStringheUtil {

	private static final org.apache.log4j.Logger log = Logger.getLogger(GestioneStringheUtil.class.getName());
	
	
	//Costruttore
	public GestioneStringheUtil() {}
	
	
	public static Collection<Oggetto> restituisciSub(String foglioInput, String partInput, String subInput) throws Exception { 
		
		Collection<Oggetto> oggetti =  new ArrayList<Oggetto>();
		
		try {
			
			LinkedList<String> numeriSub = new LinkedList<String>();			
						
			String strSplittata[] = null;			
			
			Pattern pattern = Pattern.compile("[^a-z^A-Z^0-9]");
			Matcher matcher = pattern.matcher( subInput );
			
			while ( matcher.find() ) {		    				
				String substr = matcher.group();				
				subInput=subInput.replace(substr, "-");				
			}	
			
			strSplittata = subInput.split("-");
			
			for(int i=0; i<strSplittata.length; i++){
								
				 String sub = strSplittata[i];
				 
				 matcher = pattern.matcher( sub );
						
				 if(!matcher.find() && sub!=null && !sub.equals("")){				   					
					 numeriSub.add(sub);				        					        					        																   						        																			
				 }	 
			}
				        
			// tolgo dalla stringa in input la parte riguardante il civico in modo da poter ridare indietro anche la via				
			if (numeriSub.size()>0) {
						    				        						   				        
				for(int i=0; i < numeriSub.size(); i++){
					Oggetto ogg = new Oggetto(foglioInput, partInput ,numeriSub.get(i));				
					oggetti.add(ogg);
				}										
			}		

		} catch (Exception e) {
			log.error("Errore trasformazione SUB" + subInput + " Errore: " + e);														
        	Oggetto oggettoErr= new Oggetto(foglioInput,partInput,subInput);
        	oggettoErr.setAnomalia("Errore trasformazione SUB "+subInput + ". Errore " +e);
        	oggetti.add(oggettoErr);        	
		}
		
		
		return oggetti;
		
	}
		
	
}
