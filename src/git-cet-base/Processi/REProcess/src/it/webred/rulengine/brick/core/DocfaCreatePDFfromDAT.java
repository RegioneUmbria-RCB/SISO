package it.webred.rulengine.brick.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
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
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.DateFormat;
import it.webred.utils.ShellCommand;

public class DocfaCreatePDFfromDAT extends Command implements Rule {

	private static final Logger log = Logger.getLogger(DocfaCreatePDFfromDAT.class.getName());
	
	private String docfaPdfWD = null; 
	

	public DocfaCreatePDFfromDAT(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		
		
		
		CommandAck cmdAck = null;
		String nFile = "Docfa - Conversione file DAT in PDF effettuata!"; 
		String nomeDirectoryStart = "";		// Load\\"+codEnte+"\\Docfa\\LETTI_DA_CARONTE 		
		String outputDir = "";				// Dati_Diogene\\"+codEnte+"\\docfa_pdf
		

		try {
			
			nomeDirectoryStart = Utils.getConfigProperty("dir.files",ctx.getBelfiore(), new Long(9)) + "LETTI_DA_CARONTE";
			outputDir = Utils.getConfigProperty("dir.files.datiDiogene") + "\\" + ctx.getBelfiore() + "\\docfa_pdf";
			
			
			File file = new File(nomeDirectoryStart);
			//test esistenza directory input
			if (!file.exists())
				return new ErrorAck("Docfa - conversione DAT-PDF: Directory di input "+nomeDirectoryStart+" non trovata!");
			
			//test esistenza directori principale di output
			File out = new File(outputDir);
			if (!out.exists())
				return new ErrorAck("Docfa - conversione DAT-PDF: Directory principale di output "+out+" non trovata!");
			
			docfaPdfWD = outputDir+"\\docfaPdfApp"; 
			
			//calcolo da quale fornitura caricare i file
			//non utilizzato per integrative
			String dataRif = this.controllaDataInizio(out); 
			
			
			
			if (file.isDirectory()){
				String dataEs = DateFormat.genDataAttuale();
				
				FileOutputStream fileoutLog = new FileOutputStream(outputDir+"\\DOCFA_"+dataEs+".log");
		        PrintStream output = new PrintStream(fileoutLog);
				File[] files = file.listFiles();
				for (int i=0;i<files.length;i++){
					File f = files[i];
					String nf = f.getName();
					boolean isIntegr = nf.toUpperCase().contains("INT_");
					String nfToParse = isIntegr ? nf.replace("INT_", "") : nf;
					if (nf.toUpperCase().endsWith("ZIP") 
					    && nf.toUpperCase().contains("DOC")
					    && (isIntegr || Integer.parseInt(nfToParse.substring(5, 11))>=Integer.parseInt(dataRif))){
						String outSD = nfToParse.substring(5, 11);
						
						//file di log
				        output.println("FORNITURA - "+outSD+" -");
				        
				        boolean hasNotIntegr = true;
				        for (int j=0;j<files.length;j++){
				        	File f1 = files[j];
							String nf1 = f1.getName();
							if (!nf1.equalsIgnoreCase(nf) && nf1.replace("INT_", "").equalsIgnoreCase(nf)) {
								hasNotIntegr = false;
								break;
							}
				        }
				        
				        boolean deleteTemp = isIntegr || hasNotIntegr;
				        unZipFile(nomeDirectoryStart,nomeDirectoryStart+"\\"+nf, outputDir+"\\"+outSD,output,deleteTemp);
						
					}
				}
				output.close();
				fileoutLog.close();
				
			}
		
			//nome Directory extract
			cmdAck = new ApplicationAck(nFile );
				
		}
		catch (Exception e) {
			cmdAck = new ErrorAck(e);
			log.error("Docfa: Errore in conversione DAT in PDF", e);		
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
			if(f.isDirectory() &&  !f.getName().toUpperCase().contains("DOCFAPDFAPP")){
				
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
	
private void unZipFile (String dirIn,String fileName,String outDir,PrintStream output, boolean deleteTemp) throws Exception {
		
		byte[] buf = new byte[1024];
        ZipInputStream zipinputstream = null;
        ZipEntry zipentry;
        zipinputstream = new ZipInputStream(
            new FileInputStream(fileName));

        zipentry = zipinputstream.getNextEntry();
        
      //directory out temp
        String nomeTemp = dirIn+"\\temp";
       
        File fileo = new File(nomeTemp);
        if (!fileo.exists())
        	fileo.mkdir();
        
        
        
         
        while (zipentry != null) 
        {
        	String entryName = zipentry.getName();
        	System.out.println("entryname "+entryName);
        	
        	//estraggo i file DAT e PDF nella directory di partenza
        	if ( !entryName.toUpperCase().contains("LIS") )
        	{
	        	
        		int n;
	            FileOutputStream fileoutputstream;
	            File newFile = new File(entryName);
	            String directory = newFile.getParent();
	            
	            if(directory == null)
	            {
	                if(newFile.isDirectory())
	                    break;
	            }
	            
	            fileoutputstream = new FileOutputStream(
	            		nomeTemp+"\\"+entryName);             
	
	            while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
	                fileoutputstream.write(buf, 0, n);
	            
	            
	            fileoutputstream.close(); 
	            zipinputstream.closeEntry();
        	}   
	        zipentry = zipinputstream.getNextEntry();
        }
        
        zipinputstream.close();
        
        //controllo se ci sono file da convertire o solo da spostare
        File dirTemp = new File(nomeTemp);
        File[]arrFile = dirTemp.listFiles();
        for (int i = 0; i < arrFile.length; i++) {
        	File tempF = arrFile[i];
			String nomeF = tempF.getName();
			boolean test = true;
			//test se DAT
			if (nomeF.toUpperCase().endsWith(".DAT")){
				/*
				 * Ora si comporta in questo modo: controlla se esiste il PDF nello
				 * zip e soltanto se non esiste lo genera dal DAT.
				 * Da oggi 07/03/2011 invece, visto che a volte il PDF presente nello 
				 * zip è sbagliato, generiamo sempre il PDF dal DAT e se da errore 
				 * prendiamo quello fornito ... sempre che esista.
				 */
				//verifico se c'e' PDF corrispondente
				String nomePdf = nomeTemp.concat("\\"+nomeF.substring(0,nomeF.length()-3).concat("PDF"));
				File fPdf = new File(nomePdf);
				//if (!fPdf.exists()){
					//genero il pdf in directory temp
					String command = "cmd start /C docfapdf "+nomeTemp+"\\"+nomeF;
	            	try{
		            	ShellCommand sc = new ShellCommand(command);
		                sc.setWorkDir(docfaPdfWD);
		                sc.run();
		                
		                //verifico che il PDF sia stato creato e che sia pieno!
		                File verPdf = new File(nomePdf);
		                if (!verPdf.exists()|| verPdf.length() == 0){
		                	log.error("ERRORE:File "+nomeF+" - non corretamente convertito!");
		                	output.println("File "+nomeF+" - NON CORRETTO!");
		                	test = false;
		                }	
	            	}
	            	catch (Throwable e) {
	        			log.error("Docfa: Creazione PDF per file "+nomeF+" NON RIUSCITA", e);
	        			if (!fPdf.exists()){
		        			output.println("File "+nomeF+" - NON GENERATO E PDF NON PRESENTE IN FORNITURA!");
		        			test = false;	            			
	            		}else{
	            			output.println("File "+nomeF+" - NON GENERATO MA PRESENTE IN FORNITURA!");
	            		}
	        		}
				//}
				
				File out = new File(outDir);
			    if (!out.exists())
			    	out.mkdir();
				
			    if (test){
			    	//output.println("File "+fPdf.getName()+" - OK!");
					//sposto il file PDF nella directory di output
					FileInputStream isPdf = new FileInputStream(fPdf);
					FileOutputStream fileoutputstream = new FileOutputStream(
							outDir+"\\"+fPdf.getName());             
					int n;
		            while ((n = isPdf.read(buf, 0, 1024)) > -1)
		                fileoutputstream.write(buf, 0, n);
					
		            fileoutputstream.close(); 
		            isPdf.close();
			    }
			}
			
		}
         
        
        //cancello la directory temp ed i file in essa contenuti
        if (deleteTemp) {
        	File[]delFile = dirTemp.listFiles();
            for (int i = 0; i < delFile.length; i++) {
            	File tempDel = delFile[i];
            	if (tempDel.exists())
            		tempDel.delete();
            }
            dirTemp.delete();
        }        
        
	}

}
