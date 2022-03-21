package it.webred.rulengine.brick.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class DocfaExtractRenamePlanimetrie extends Command implements Rule {

	private static final Logger log = Logger.getLogger(DocfaExtractRenamePlanimetrie.class.getName());


	public DocfaExtractRenamePlanimetrie(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		//String codEnte = (String)ctx.getDeclarativeType("RULENGINE.CODENTE").getValue();
		//String codEnte = "F205";
		
		CommandAck cmdAck = null;
		String nFile = "Planimetrie Docfa estratte e rinominate!"; 
		String nomeDirectoryStart = "";		// Load\\"+codEnte+"\\Docfa\\LETTI_DA_CARONTE 		
		String outputDir = "";				// Dati_Diogene\\"+codEnte+"\\planimetrie
		
		

		
		try {
			

				nomeDirectoryStart = Utils.getConfigProperty("dir.files",ctx.getBelfiore(), new Long(9)) + "LETTI_DA_CARONTE";
				outputDir = Utils.getConfigProperty("dir.files.datiDiogene") + "\\" + ctx.getBelfiore() + "\\planimetrie";
				
							 
			 
			File file = new File(nomeDirectoryStart);
			//test esistenza directory input
			if (!file.exists())
				return new ErrorAck("Docfa-Estrazione Planimetrie: Directory di input "+nomeDirectoryStart+" non trovata!");
			
			//test esistenza directori principale di output
			File out = new File(outputDir);
			if (!out.exists())
				return new ErrorAck("Docfa-Estrazione Planimetrie: Directory principale di output "+out+" non trovata!");
			
			//calcolo da quale fornitura caricare i file
			//non utilizzato per integrative
			String dataRif = this.controllaDataInizio(out); 
			
			if (file.isDirectory()){
				File[] files = file.listFiles();
				for (int i=0;i<files.length;i++){
					File f = files[i];
					if (f.exists() && !f.isDirectory()) {
						String nf = f.getName();
						try {							
							boolean isIntegr = nf.toUpperCase().contains("INT_");
							String nfToParse = isIntegr ? nf.replace("INT_", "") : nf;
							String outSD = nfToParse.substring(5, 11);
							if((nf.toUpperCase().endsWith("ZIP") && nf.toUpperCase().contains("PL") && (isIntegr || Integer.parseInt(nf.substring(5, 11))>=Integer.parseInt(dataRif))))
								unZipFile(nomeDirectoryStart+"\\"+nf, outputDir+"\\"+outSD, dataRif, isIntegr);
						} catch (Exception e) {
							//eccezione non bloccante
							log.debug("Errore in unZip delle Planimetrie Docfa, file " + nf, e);
						}						
					}					
				}
			}			
			
			//return
			cmdAck = new ApplicationAck(nFile );
				
		}
		catch (Exception e) {
			cmdAck = new ErrorAck(e);
			log.error("Errore in unZip delle Planimetrie Docfa", e);
		}

		
		
		return cmdAck;
	}
	
	private String controllaDataInizio(File out){
		String dataRiferimento = null;
		
		int appoggio = 190001;
		//verifico se ci sono gia' pdf caricati
		File[] files = out.listFiles();
		if (files != null && files.length > 0){
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				//test che sia directory e diversa da OUT(programma docfaPdf)
				if(f.isDirectory()){
					//memorizzo la directory con valore più alto 
					if (Integer.parseInt(f.getName()) > appoggio){
						dataRiferimento = f.getName();
						appoggio = Integer.parseInt(f.getName());
					}
				}
			}
		}
		
		if (dataRiferimento == null){
			dataRiferimento = Integer.toString(appoggio);
		}
		
		int somma = 1;
		//vedo se sono arrivata a caricare dicembre
		int rif = Integer.parseInt(dataRiferimento.substring(4));
		if (rif == 12) //devo mettere gennaio anno successivo
			somma = 89;
		//calcolo il nuovo punto di partenza
		dataRiferimento = Integer.toString(Integer.parseInt(dataRiferimento)+somma);
		
		
		return dataRiferimento;
	}
	
	private void unZipFile (String fileName,String outDir,String dataRif, boolean isIntegr) throws Exception {
		
		byte[] buf2 = new byte[1024];
        ZipInputStream zipinputstream2 = null;
        ZipEntry zipentry2;
        zipinputstream2 = new ZipInputStream(
            new FileInputStream(fileName));

        zipentry2 = zipinputstream2.getNextEntry();
        while (zipentry2 != null) 
        {
        	String entryName2 = zipentry2.getName();
        	System.out.println("entryname2 "+entryName2);
        	if ((entryName2.toUpperCase().endsWith("ZIP") && entryName2.toUpperCase().contains("PL") && (isIntegr || Integer.parseInt(entryName2.substring(5, 11))>=Integer.parseInt(dataRif)))
        		||( !entryName2.toUpperCase().endsWith("ZIP") && !entryName2.toUpperCase().endsWith("DAT") && !entryName2.toUpperCase().endsWith("LIS") )	){
	        	int n2;
	            FileOutputStream fileoutputstream2;
	            File newFile2 = new File(entryName2);
	            String directory2 = newFile2.getParent();
	            
	            if(directory2 == null)
	            {
	                if(newFile2.isDirectory())
	                    break;
	            }
	            
	            //test esistenza directory out
	            File fileo = new File(outDir);
	            if (!fileo.exists())
	            	fileo.mkdir();
	            
	            //vedo se e' una planimetria da rinominare 
	            if (!entryName2.toUpperCase().endsWith("ZIP") && !entryName2.toUpperCase().endsWith("DAT") && !entryName2.toUpperCase().endsWith("LIS")){
	            	entryName2 = entryName2.concat(".tif");
	            }
	            fileoutputstream2 = new FileOutputStream(
	            		outDir+"\\"+entryName2);             
	
	            while ((n2 = zipinputstream2.read(buf2, 0, 1024)) > -1)
	                fileoutputstream2.write(buf2, 0, n2);
	            
	            
	            fileoutputstream2.close(); 
	            zipinputstream2.closeEntry();
	            zipentry2 = zipinputstream2.getNextEntry();
	            
	            if (entryName2.toUpperCase().endsWith("ZIP")){
	            	//devo estrarre i file cntenuti
	            	unZipFile(outDir+"\\"+entryName2, outDir,dataRif,isIntegr);
	            }
	            
	            if (entryName2.toUpperCase().endsWith("ZIP") || entryName2.toUpperCase().endsWith("DAT") || entryName2.toUpperCase().endsWith("LIS")){
	            	//rimuovo file contenitore o file non significativo
	                File fileDel = new File(outDir+"\\"+entryName2);
	                fileDel.delete();
	            }
	            
        	}
        	else
        		zipentry2 = zipinputstream2.getNextEntry();
        }
        
        zipinputstream2.close();
        
	}

}
