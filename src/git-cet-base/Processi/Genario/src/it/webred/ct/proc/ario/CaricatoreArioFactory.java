package it.webred.ct.proc.ario;

import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.caricatori.CaricatoriException;
import it.webred.ct.proc.ario.caricatori.TipoCaricatore;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.rulengine.exception.RulEngineException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CaricatoreArioFactory {
	
	private static Properties prop_fonti = new Properties();	
	private static Properties prop_entiSorg = new Properties();	
	private static Hashtable<String, DatoDwh> clazz = new Hashtable<String, DatoDwh>();	
	private static final Logger log = Logger.getLogger(CaricatoreArioFactory.class.getName());
	
	
	public CaricatoreArioFactory(ArrayList<String> listaFonti){
		
		try{
		 
			InputStream is = getClass().getResourceAsStream("/fonti.properties");
			prop_fonti.load(is); 
			
			/*InputStream is_entiSorg = getClass().getResourceAsStream("/entiSorgente.properties");
			prop_entiSorg.load(is_entiSorg);*/ 
		
			istanziaFonti(listaFonti);
			
		}
		catch (Exception e1)
		{
			throw new RuntimeException(e1);
		}
		
	}

	/**
	 * 
	 * @param codEnte - sempre null, valorizzato soltanto in presenza di caricatori custom per l'ente sopecifico
	 * @return
	 */
	public void Execute(String codEnte, Connection connForInsert, ArrayList<String> listaFonti, HashParametriConfBean paramConfBean) throws RulEngineException
	{		
		log.info("---INIZIO METODO EXECUTE ---");
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = null;
		try{
			
			TipoCaricatore tc = new TipoCaricatore(codEnte);
			tc.setConnForInsert(connForInsert);
			
			String[] arrayTipi = prop_fonti.getProperty("listaTipi").split(",");

			for(int i=0; i < arrayTipi.length; i++){
				log.info("############ INFO: Inizio Caricamento/Instanziamento Tipo Fonte: "+arrayTipi[i]+" ###########");
				System.out.println("############ INFO: Inizio Caricamento/Instanziamento Tipo Fonte: "+arrayTipi[i]+" ###########");
			
					//if (!"soggetto".equals(arrayTipi[i]) && !"via".equals(arrayTipi[i]) && !"oggetto".equals(arrayTipi[i]) ) {
					for(String fonte:listaFonti){
						
						resultdate = new Date( System.currentTimeMillis());
						log.info("############ INFO: Inizio Caricamento Nome Fonte: "+fonte +" - "+sdf.format(resultdate));
						System.out.println("############ INFO: Inizio Caricamento Nome Fonte: "+fonte +" - "+sdf.format(resultdate));
						
					//	if ("civico".equals(arrayTipi[i]) && (fonte.equals("14")  || fonte.equals("4") || fonte.equals("7") || fonte.equals("3") || fonte.equals("5")
					//			|| fonte.equals("1") || fonte.equals("9")))
					//		continue;

						
								String chiave = arrayTipi[i]+fonte;
								int index= 0;
								while (clazz.containsKey(chiave+index)){
									log.info("Chiave: "+ chiave + "  N. fonte caricata: " + index);
									
									DatoDwh classe = clazz.get(chiave+index);
									
									boolean fkEs = tc.iSEnteSorgentePresente(classe);
									
									if(fkEs){
										tc.elaboraDatiToInsert(classe,codEnte,paramConfBean);								
									}	
															
									index++;															
								}	
								
								connForInsert.commit();
								resultdate = new Date( System.currentTimeMillis());
								log.info("############ INFO: Fine Caricamento Nome Fonte: "+fonte +" - "+sdf.format(resultdate));
								System.out.println("############ INFO: Fine Caricamento Nome Fonte: "+fonte +" - "+sdf.format(resultdate));
					//}
					}
	
				log.info("############ INFO: Fine Caricamento/Instanziamento Tipo Fonte: "+arrayTipi[i]+" ##########");
				System.out.println("############ INFO: Fine Caricamento/Instanziamento Tipo Fonte: "+arrayTipi[i]+" ##########");
				
			}			
			
		} catch (Exception e){
			try{
				connForInsert.rollback();	
			}catch(Exception ea){
			}
						
			log.error("Errore in Execute di CaricatoreArioFactory "+e.getMessage(),e);
			throw new CaricatoriException("Errore in Execute di CaricatoreArioFactory",e);

		}
	}

	
	//Metodo usato per instanziare le fonti da caricare
	public void istanziaFonti(ArrayList<String> lf) throws Exception{
	
		DatoDwh d = null;		
		
	    log.info("---INIZIO METODO ISTANZIA FONTI---");
		
	    try{
	    	
	    	//Vecchio Caricamento da properties
	    	/*if (lf.isEmpty()){
	  	    	
	    		//CARICO TUTTE LE FONTI DA PROPERTIES
	    		if(prop_entiSorg != null){
	    			
	    			Set<String> s = prop_entiSorg.stringPropertyNames();            	
	            	Iterator it = s.iterator();	            		            	
	            	
	            	while(it.hasNext()){	            		
	            		String chiave = it.next().toString();	
	            		log.info("INFO: Chiave da properties enti Sorgente: "+chiave);
	            		
	            		lf.add(prop_entiSorg.getProperty(chiave));
	            	}		    		
	    		}	    			  	    		    
	  	    }*/
	  	    		  	    	
		    String[] arrayTipi = prop_fonti.getProperty("listaTipi").split(",");
		    	
		    for (int i = 0; i < lf.size(); i++) { 
		      		
		    	String nomeFonte = lf.get(i);
		    	log.info("Nome Fonte:" + nomeFonte);
		      		
		    	for(int k=0; k<arrayTipi.length; k++){
		      			
		    		String tipoFonte = arrayTipi[k];
		    		log.info("Tipo Fonte:" + tipoFonte);
		      			
		      					      				
		    		String fonte = prop_fonti.getProperty(tipoFonte+"."+nomeFonte);
			 		log.info("classe fonte:"+ fonte);
				  		
			  		if(fonte!=null){
				  		
			  			String splitSogg[] = fonte.split(",");
				  			
			  			for(int m=0; m < splitSogg.length; m++){
	
			  				log.info("istanza classe fonte:"+ splitSogg[m]);
				  			d = istanziaClasse(splitSogg[m]);
				  				
				  			clazz.put(tipoFonte+nomeFonte+m, d);			  				
				  		}
		      		}	      						  			
		      	}
		      }		  	    		      		  				  		  			
	    	
	    }catch (Exception e){
			throw new RuntimeException(e);
		}
	
	}
	
	
	//Metodo che instanzia le classi 
	private DatoDwh istanziaClasse(String nome) throws Exception{
		
		DatoDwh dDwh = null;
		
		try{
			
			Class c = Class.forName(nome);
			dDwh = (DatoDwh) c.newInstance();
															
		}catch (Exception e) {
			throw e;
		}
		
		return dDwh;
	}
	
	
    public static void main(String[] args) 
    {
    	
    	//FORMATO parametro: user:pass@127.0.0.1:21
    		

    			/*FTPClient client = new FTPClient();
    			FileOutputStream fos = null;

    			try {
    				client.connect("172.16.2.62", 21);
    				client.login("ftpuser", "ftpuser");
   					boolean b = client.changeWorkingDirectory("import");

    				// controllo presenza fd
    				FTPFile[] ftpFiles = client.listFiles();
    				for (FTPFile ftpFile : ftpFiles) {
    					String filename = ftpFile.getName();
    						fos = new FileOutputStream("T:/temp/aree/"
    								+ ftpFile.getName());
    						//
    						// Download file from FTP server
    						//
    						 int bb = client.getReplyCode();
    						
    						client.retrieveFile("/" + filename, fos);
    						log.debug("File recuperato: "
    								+ filename);
    				}    			

    				client.logout();


    			} catch (IOException e) {
    				log.error(e.getMessage(),e);
    			} finally {
    				try {
    					client.disconnect();
    				} catch (IOException e) {
    					log.error(e.getMessage(),e);
    				}
    			}*/
    	
    }
	
}
