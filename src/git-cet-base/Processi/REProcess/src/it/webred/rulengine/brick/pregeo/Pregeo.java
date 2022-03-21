package it.webred.rulengine.brick.pregeo;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.pregeo.bean.PregeoFornituraBean;
import it.webred.rulengine.brick.pregeo.bean.PregeoInfoBean;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.web.util.PdfExtracter;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

public class Pregeo extends Command implements Rule{

	protected static org.apache.log4j.Logger log = Logger.getLogger(Pregeo.class.getName());
	
	private static final String CORROTTI = "CORROTTI";
	private static final String REGOLARI = "REGOLARI";
	private static final String ZIP_PDF = "ZIP_PDF";
	
	private static final String PREGEO_CODICE = "PREGEO_CODICE";
	private static final String PREGEO_NOTE = "PREGEO_NOTE";
	private static final String PREGEO_PERSONE_COINVOLTE = "PREGEO_PERSONE_COINVOLTE";
	private static final String PREGEO_ATTRIBUTI = "PREGEO_ATTRIBUTI";
	private static final String PREGEO_RELAZIONE_TECNICA = "PREGEO_RELAZIONE_TECNICA";
	
	private static final String TAG_RELAZIONE_TECNICA = "Relazione Tecnica";
	private static final String TAG_TITOLARI = "Il/I sottoscritto/i dichiara/no di essere a conoscenza del contenuto del presente atto di aggiornamento";
	private static final String TAG_REDATTORE = "Tecnico redattore";
	private static final String TAG_CODICE = "Codice file PREGEO:";
	private static final String TAG_NOTE = "Nota:";
	
	private static final String NOME_TABELLA_FORNITURE = "PREGEO_FORNITURE";
	private static final String NOME_TABELLA_INFO = "PREGEO_INFO";
	
	private String folderNamePdf = "";
	
	private Date oggi = new Date(System.currentTimeMillis());
	
	public Pregeo(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}


	@Override
	public CommandAck run(Context ctx) throws CommandException {
		
		String nomeSchemaDiogene = RulesConnection.getConnections().get("DWH_" + ctx.getBelfiore());
		if (nomeSchemaDiogene == null || nomeSchemaDiogene.equals("")) {
			nomeSchemaDiogene = RulesConnection.getConnections().get("DWH");
		}
		Connection conn = null;
		
		List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
		/*
		 * RAbNormal rabn = new RAbNormal();
			rabn.setEntity("SIT_SOGGETTI_TO_CHECK");
			rabn.setMessage(e.getMessage());
			rabn.setKey(rs1.getString("FK_SOGGETTO"));
			rabn.setLivelloAnomalia(1);
			rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
			abnormals.add(rabn);
		 */
		String errorMsg = "";
		try{
		/*
		 * Determino il tipo di fornitura (ZIP o PDF) andando a verificare l'esistenza 
		 * o meno di files con estensione .pdf o .PDF 
		 */
			
			PregeoEnv env = new PregeoEnv((String)ctx.get("connessione"), ctx);
			String folderNameFrom = env.folderNameFrom;
			String folderNameTo = env.folderNameTo;	
		
			File f = new File (folderNameFrom);
			String[] files = f.list();
			boolean pdf = false;
			if (files != null){
				for (int index=0; index<files.length; index++){
					File file = new File(folderNameFrom + files[index]);
					if (!file.isDirectory()){
						if (files[index].endsWith(".zip") || files[index].endsWith(".ZIP")){
							pdf = false;
							folderNamePdf = folderNameFrom;
						}else if (files[index].endsWith(".pdf") || files[index].endsWith(".PDF")){
							pdf = true;
						}
					}
				}
			}
			/*
			 * Verifico l'esistenza della cartella ELABORATI che conterrà i ZIPs processati
			 * oppure uno zip dei PDFs processati a seconda della fornitura
			 */
			File fElaborati = new File(folderNameFrom + "ELABORATI");
			if (!fElaborati.exists()){
				boolean creataElaborati = fElaborati.mkdir();
				if (creataElaborati){
					log.info("Cartella ELABORATI creata con successo");				
				}else{
					log.info("Impossibile creare cartella ELABORATI");
				}
			}else{
				log.info("Cartella ELABORATI gia esistente");
			}
			/*
			 * Verifico l'esistenza della cartella folderNameTo (dove saranno salvati i PDF)
			 */
			File fTo = new File(folderNameTo);
			if (!fTo.exists()){
				boolean creataTo = fTo.mkdir();
				if (creataTo){
					log.info("Cartella di destinazione PDF creata con successo");				
				}else{
					log.info("Impossibile creare cartella di destinazione PDF");
				}
			}else{
				log.info("Cartella di destinazione PDF gia esistente");
			}
			/*
			 * Verifico e decomprimo i files zip cercando di legare il contenuto allo
			 * zip di origine
			 */
			ArrayList<File> alFilesCorrupted = null;
			ArrayList<File> alFilesRegular = null;
			Hashtable htZipPdf = null;
			if (!pdf){
				Hashtable<String, Object> htUnzip = this.verifyZipFiles(folderNameFrom, folderNameTo);
				alFilesCorrupted = (ArrayList<File>)htUnzip.get(CORROTTI);
				alFilesRegular = (ArrayList<File>)htUnzip.get(REGOLARI);
				htZipPdf = (Hashtable)htUnzip.get(ZIP_PDF);
				if (alFilesCorrupted != null && alFilesCorrupted.size()>0){
					errorMsg = "FILES CORROTTI: ";
					for (int i=0; i<alFilesCorrupted.size(); i++){
						errorMsg += (alFilesCorrupted.get(i)).getName() + "; ";
					}			
				}
				/*
				 * Determino la cartella in cui sono stati estratti i PDF
				 */
				folderNamePdf = this.setDestinationFolder(folderNameFrom);
			}else{
				folderNamePdf = folderNameFrom;
			}
			/*
			 * recupero i PDF ed il loro contenuto
			 */
			Hashtable<String, Hashtable<String, String>> htPdf = this.readPdf(folderNamePdf);
			/*
			 * Se la fornitura è zip la data della fornitura la recupero dal nome del 
			 * file zip mentre invece se la fornitura è in PDF la data della fornitura
			 * e il nome del file zip non saranno valorizzati 
			 */
			ArrayList<PregeoFornituraBean> alForniture = new ArrayList<PregeoFornituraBean>();
			ArrayList<PregeoInfoBean> alInfo = new ArrayList<PregeoInfoBean>();
			if (htZipPdf != null && htZipPdf.size()>0){
				log.info("La fornitura e' in formato ZIP!!! ");
				Enumeration en = htZipPdf.keys();
				PregeoFornituraBean pFornitura = null;
				while(en.hasMoreElements()){
					String fileZip = (String)en.nextElement();
					String filesPdf = (String)htZipPdf.get(fileZip);
					String[] aryFilePdf = filesPdf.split(";");
					if (aryFilePdf != null && aryFilePdf.length>0){
						/*
						 * ESTRAPOLO LA DATA DELLA FORNITURA DAL NOME DEL FILE ZIP:
						 * X000_AAAAMMGG_N.zip
						 * X000= codice belfiore
						 * AAAAMMGG= data fornitura
						 * N= progressivo
						 */
						String strDataFornitura = fileZip.substring(5, 13);
						for (int i=0; i<aryFilePdf.length; i++){
							pFornitura = new PregeoFornituraBean();
							pFornitura.setNomeFileZip(fileZip);
							pFornitura.setContenutoZip(aryFilePdf[i]);
							Date dataFornitura = Utils.fromAAAAMMGGToDate(strDataFornitura);
							pFornitura.setDataFornitura(  new java.sql.Date(dataFornitura.getTime()) ) ;
							pFornitura.setDataInserimento( new java.sql.Date(oggi.getTime()) );
							alForniture.add(pFornitura);
						}
					}
				}
			}else{
				log.info("La fornitura e' in formato PDF!!! ");	
				if (htPdf != null && htPdf.size()>0){
					/*
					 * LA DATA DELLA FORNITURA NON SI PUO ESTRAPOLARE DAL NOME DEL FILE
					 * PDF PERCHE' NON E' SEMPRE PRESENTE E NON HA UN NOME SISTEMATICO
					 */
					PregeoFornituraBean pFornitura = null;
					Enumeration<String> enPdfKeys = htPdf.keys();
					while(enPdfKeys.hasMoreElements()){
						String pdfKey = enPdfKeys.nextElement();
						Hashtable<String, String> htInfo = htPdf.get(pdfKey);
						//String pregeoCodice = htInfo.get(PREGEO_CODICE);
						String pregeoAttributi = htInfo.get(PREGEO_ATTRIBUTI);
						//String pregeoPersoneCoinvolte = htInfo.get(PREGEO_PERSONE_COINVOLTE);
						//String pregeoRelazioneTecnica = htInfo.get(PREGEO_RELAZIONE_TECNICA);
						String[] attributi = pregeoAttributi.split("\\|");
						/*
						 * pos. 1: data pregeo
						 * pos. 3: belfiore 
						 * pos. 4: foglio
						 * pos. 5: particelle divise da virgola
						 * pos. 6: nome tecnico
						 * pos. 7: tipo tecnico
						 */
						String strDataPregeo = (attributi[1]!=null)?attributi[1].trim():"";
						/*
						 * ESTRAPOLO LA DATA DELLA FORNITURA DAL PDF:
						 * puo essere in formato GGMMAAAA oppure GGMMAA
						 */
						Date dataFornitura = null;
						if (strDataPregeo.length() == 8)
							dataFornitura = Utils.fromGGMMAAAAToDate(strDataPregeo);
						else if (strDataPregeo.length() == 6)
							dataFornitura = Utils.fromGGMMAAToDate(strDataPregeo);
						else
							log.info("Data Fornitura da file pregeo  non comprensibile: " + strDataPregeo);
						pFornitura = new PregeoFornituraBean();
						pFornitura.setContenutoZip(pdfKey);
						pFornitura.setDataFornitura(new java.sql.Date(dataFornitura.getTime()) );
						pFornitura.setDataInserimento( new java.sql.Date(oggi.getTime()) );
						alForniture.add(pFornitura);
					}
				}else{
					log.info("La FORNITURA PREGEO in formato PDF presenta ANOMALIE!!!");	
					errorMsg += " La FORNITURA PREGEO in formato PDF presenta ANOMALIE!!! ";
				}
			}
			/*
			 * Effettuo l'insert delle forniture in PREGEO_FORNITURE e sposto i files
			 * nella cartella dati diogene
			 */
			if (alForniture != null && alForniture.size()>0){
				
				conn = env.getConn();
				/*
				 * Recupero il contenuto del campo CONTENUTO_ZIP per evitare di caricare
				 * doppioni 
				 */
				String sql1 = "select DISTINCT CONTENUTO_ZIP FROM " + nomeSchemaDiogene + "." + NOME_TABELLA_FORNITURE + " ";
				CallableStatement cs = conn.prepareCall(sql1, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				ResultSet rs1 = cs.executeQuery();
				Hashtable<String, String> htContenutoZip = new Hashtable<String, String>();
				while (rs1.next()) {
					String contenutoZip = rs1.getString("CONTENUTO_ZIP");
					htContenutoZip.put(contenutoZip, contenutoZip);
				}
				DbUtils.close(rs1);
				DbUtils.close(cs);
	
				String sql = "INSERT INTO " + nomeSchemaDiogene + "." + NOME_TABELLA_FORNITURE + " " +
				"(DATA_FORNITURA, NOME_FILE_ZIP, CONTENUTO_ZIP, DATA_INSERIMENTO) VALUES " +
				"(?,?,?,?)";
				log.info(sql);
				CallableStatement cstmt = conn.prepareCall(sql);
				for (int i=0; i<alForniture.size(); i++){
					PregeoFornituraBean pFornitura = alForniture.get(i);
					/*
					 * Prima di fare l'insert devo controllare se il PDF è stato già 
					 * preso incarico confrontando il campo CONTENUTO_ZIP
					 */
					if (!htContenutoZip.containsKey(pFornitura.getContenutoZip())){
						cstmt.setDate(1, pFornitura.getDataFornitura() );
						log.info("PAR. 1 (DATA FORNITURA): " + pFornitura.getDataFornitura());	
						cstmt.setString(2, pFornitura.getNomeFileZip());
						log.info("PAR. 2 (NOME FILE ZIP): " + pFornitura.getNomeFileZip());
						cstmt.setString(3, pFornitura.getContenutoZip());
						log.info("PAR. 3 (CONTENUTO ZIP): " + pFornitura.getContenutoZip());
						cstmt.setDate(4, pFornitura.getDataInserimento() );
						log.info("PAR. 4 (DATA_INSERIMENTO): " + pFornitura.getDataInserimento());

						boolean okInsert = cstmt.execute();
						log.info("File PDF inserito con successo nella tabella PREGEO_FORNITURE!!!: " + pFornitura.getContenutoZip());
					}else
						log.info("File PDF gia esistente nella tabella PREGEO_FORNITURE!!!: " + pFornitura.getContenutoZip());
				}
				DbUtils.close(cstmt);
			}
			/*
			 * Effettuo l'insert delle informazioni in PREGEO_INFO dopo aver controllato con il codice
			 * pregeo se le info sono state già caricate  
			 */
			int nPdf = 0;
			if (htPdf != null && htPdf.size()>0){
				PregeoInfoBean pInfo = null;
				nPdf = htPdf.size();
				Enumeration<String> enPdfKeys = htPdf.keys();
				while(enPdfKeys.hasMoreElements()){
					String pdfKey = enPdfKeys.nextElement();
					Hashtable<String, String> htInfo = htPdf.get(pdfKey);
					System.out.println("pdfKey: " + pdfKey);
					
					try{
					
						String pregeoCodice = htInfo.get(PREGEO_CODICE);
						String pregeoAttributi = htInfo.get(PREGEO_ATTRIBUTI);
						String pregeoPersoneCoinvolte = htInfo.get(PREGEO_PERSONE_COINVOLTE);
						String pregeoRelazioneTecnica = htInfo.get(PREGEO_RELAZIONE_TECNICA);
						String pregeoNote = htInfo.get(PREGEO_NOTE);
						String[] attributi = pregeoAttributi.split("\\|");
						/*
						 * pos. 1: data pregeo
						 * pos. 3: belfiore 
						 * pos. 4: foglio
						 * pos. 5: particelle divise da virgola
						 * pos. 6: nome tecnico
						 * pos. 7: tipo tecnico
						 */
						String strDataPregeo = (attributi[1]!=null)?attributi[1].trim():"";
						Date dataPregeo = null;
						if (strDataPregeo.length() == 8)
							dataPregeo = Utils.fromGGMMAAAAToDate(strDataPregeo);
						else if (strDataPregeo.length() == 6)
							dataPregeo = Utils.fromGGMMAAToDate(strDataPregeo);
						else
							log.info("Data Pregeo non comprensibile: " + strDataPregeo);
						String belfiore = attributi[3];
						String foglio = attributi[4];
						String[] particelle = attributi[5].split(",");
						String tecnico = attributi[6];
						String tipoTecnico = attributi[7];
						for (int ind=0; ind<particelle.length; ind++){
							try{
								pInfo = new PregeoInfoBean();
								pInfo.setCodicePregeo(pregeoCodice);
								pInfo.setDataPregeo( dataPregeo );
								if (pregeoPersoneCoinvolte != null && pregeoPersoneCoinvolte.length() > 4000) {
									pregeoPersoneCoinvolte = pregeoPersoneCoinvolte.substring(0, 3900) + "...";
								}
								pInfo.setDenominazione(pregeoPersoneCoinvolte);
								pInfo.setFoglio( !StringUtils.isEmpty(foglio)?foglio.trim():null );  
								pInfo.setNomeFilePdf(pdfKey);
								pInfo.setParticella(particelle[ind]);
								pInfo.setTecnico(tecnico);
								pInfo.setTipoTecnico(tipoTecnico);
								pInfo.setDataInserimento( new java.sql.Date(oggi.getTime()) );
								if (pregeoRelazioneTecnica != null && pregeoRelazioneTecnica.length() > 4000) {
									pregeoRelazioneTecnica = pregeoRelazioneTecnica.substring(0, 3900) + "...";
								}
								pInfo.setRelazioneTecnica(pregeoRelazioneTecnica);
								if (pregeoNote != null && pregeoNote.length() > 4000) {
									pregeoNote = pregeoNote.substring(0, 3900) + "...";
								}
								pInfo.setNota(pregeoNote);
								
								alInfo.add(pInfo);
								
							}catch(NumberFormatException nfe){
								errorMsg += " " + pdfKey + ": " + nfe.getMessage();
							}
						}
					
					}catch(Exception exc){
						log.error(exc);
						errorMsg += " " + pdfKey + ": " + exc.getMessage();
					}
					
				}
				errorMsg += " Num. Files PDF: " + nPdf;
				int nRec = 0;
				if (alInfo != null && alInfo.size()>0){
					nRec = alInfo.size();
					/*
					 * Per evitare di inserire i doppioni verifico se il codice pregeo 
					 * è già stato inserito. Recupero il contenuto del campo CODICE_PREGEO 
					 */
					String sql1 = "select DISTINCT CODICE_PREGEO FROM " + nomeSchemaDiogene + "." + NOME_TABELLA_INFO + " ";
					CallableStatement cs = conn.prepareCall(sql1, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
					ResultSet rs1 = cs.executeQuery();
					Hashtable<String, String> htCodiciPregeo = new Hashtable<String, String>();
					while (rs1.next()) {
						String codicePregeo = rs1.getString("CODICE_PREGEO");
						htCodiciPregeo.put(codicePregeo, codicePregeo);
					}
					DbUtils.close(rs1);
					DbUtils.close(cs);
					
					String sql = "INSERT INTO " + nomeSchemaDiogene + "." + NOME_TABELLA_INFO + " " +
					"(NOME_FILE_PDF, DATA_PREGEO, CODICE_PREGEO, DENOMINAZIONE, TECNICO, TIPO_TECNICO, FOGLIO, PARTICELLA, DATA_INSERIMENTO, RELAZIONE_TECNICA, NOTA) VALUES " +
					"(?,?,?,?,?,?,?,?,?,?,?)";
					log.info(sql);
					CallableStatement cstmt = conn.prepareCall(sql);
					for (int i=0; i<alInfo.size(); i++){
						/*
						 * Prima di fare l'insert devo controllare se il codice Pregeo 
						 * è stato già preso in carico
						 */
						PregeoInfoBean bInfo = alInfo.get(i);
						if (!htCodiciPregeo.containsKey(bInfo.getCodicePregeo())){
							cstmt.setString(1, bInfo.getNomeFilePdf() );
							log.info("PAR. 1 (NOME_FILE_PDF): " + bInfo.getNomeFilePdf());	
							cstmt.setDate(2, new java.sql.Date(bInfo.getDataPregeo().getTime()) );
							log.info("PAR. 2 (DATA_PREGEO): " + bInfo.getDataPregeo());
							cstmt.setString(3, bInfo.getCodicePregeo());
							log.info("PAR. 3 (CODICE_PREGEO): " + bInfo.getCodicePregeo());
							String denominazione = bInfo.getDenominazione();
							if (denominazione != null && denominazione.length() > 4000) {
								denominazione = denominazione.substring(0, 3900) + "...";
							}
							cstmt.setString(4, denominazione);
							log.info("PAR. 4 (DENOMINAZIONE): " + denominazione);
							cstmt.setString(5, bInfo.getTecnico());
							log.info("PAR. 5 (TECNICO): " + bInfo.getTecnico());
							cstmt.setString(6, bInfo.getTipoTecnico());
							log.info("PAR. 6 (TIPO_TECNICO): " + bInfo.getTipoTecnico());
							cstmt.setString(7, bInfo.getFoglio());
							log.info("PAR. 7 (FOGLIO): " + bInfo.getFoglio());
							cstmt.setString(8, bInfo.getParticella() );
							log.info("PAR. 8 (PARTICELLA): " + bInfo.getParticella());
							cstmt.setDate(9, new java.sql.Date(bInfo.getDataInserimento().getTime()) );
							log.info("PAR. 9 (DATA_INSERIMENTO): " + bInfo.getDataInserimento());
							String relazioneTecnica = bInfo.getRelazioneTecnica();
							if (relazioneTecnica != null && relazioneTecnica.length() > 4000) {
								relazioneTecnica = relazioneTecnica.substring(0, 3900) + "...";
							}
							cstmt.setString(10, relazioneTecnica);
							log.info("PAR. 10 (RELAZIONE_TECNICA): " + relazioneTecnica);
							String nota = bInfo.getNota();
							if (nota != null && nota.length() > 4000) {
								nota = nota.substring(0, 3900) + "...";
							}
							cstmt.setString(11, nota);
							log.info("PAR. 11 (NOTA): " + nota);
							
							boolean okInsert = cstmt.execute();
							log.info("Info Codice Pregeo " + bInfo.getCodicePregeo() + " inserite con successo nella tabella PREGEO_INFO!!!");
						}else
							log.info("Codice Pregeo gia esistente: " + bInfo.getCodicePregeo());
					}
					DbUtils.close(cstmt);
				}
				
				conn.commit();
				
				errorMsg += " Num. Records (=Particelle) inseriti : " + nRec;
				
			}else{
				log.info("PDF non trovati nella cartella " + folderNamePdf + " o assenti");
			}
			/*
			 * Nel caso di fornitura ZIP sposto tutti i files sotto ELABORATI, 
			 * nel caso di fornitura PDF comprimo tutti i files in uno ZIP e lo sposto 
			 * sotto ELABORATI
			 */
			if (!pdf){
				//Fornitura ZIP
				log.info("Inizio Spostamento Fornitura ZIP in ELABORATI");
				if ( alFilesCorrupted != null && alFilesCorrupted.size()>0 ){
					File currentFile = null;
					for (int cnt = 0; cnt<alFilesCorrupted.size(); cnt++){
						currentFile = alFilesCorrupted.get(cnt);
						if (currentFile != null){
							String currentFileName = currentFile.getName();
							currentFile.renameTo( new File(fElaborati.getPath() + "/" + currentFileName) );		
						}
					}
				}
				if ( alFilesRegular != null && alFilesRegular.size()>0 ){
					File currentFile = null;
					for (int cnt = 0; cnt<alFilesRegular.size(); cnt++){
						currentFile = alFilesRegular.get(cnt);
						if (currentFile != null){
							String currentFileName = currentFile.getName();
							currentFile.renameTo( new File(fElaborati.getPath() + "/" + currentFileName) );		
						}
					}
				}
				log.info("Fine Spostamento Fornitura ZIP in ELABORATI");
					
			}else{
				//Fornitura PDF
				log.info("Inizio Compressione Fornitura PDF");
				ArrayList<String> alFilesPath = new ArrayList<String>();
				Enumeration<String> enPdfs = htPdf.keys();
				while(enPdfs.hasMoreElements()){
					String currentFileName = enPdfs.nextElement();
					alFilesPath.add(folderNamePdf + currentFileName);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String today = sdf.format(oggi);
				Utils.doZip(fElaborati.getPath() + "/" + today + "_PREGEO.zip", alFilesPath);
				log.info("Fine Compressione Fornitura PDF");
			}
			log.info("Inizio Spostamento Fornitura PDF in Dati_Diogene");
			Enumeration<String> enPdfKeys = htPdf.keys();
			while(enPdfKeys.hasMoreElements()){
				String currentFileName = enPdfKeys.nextElement();
				File currentFile = new File(folderNamePdf + currentFileName);
				currentFile.renameTo( new File(folderNameTo + currentFileName) );
			}		
			log.info("Fine Spostamento Fornitura PDF in Dati_Diogene");
			/*
			 * Nel caso di fornitura in ZIP rimane da eliminare la cartella chiamata 
			 * con il codice belfiore dove sono stati estratti i ZIPs
			 */
			if (!pdf){
				log.info("Eliminazione della cartella chiamata con il codice belfiore dove sono stati estratti i ZIPs ");
				File cartellaEstrazioneZip = new File(folderNamePdf);
				if (cartellaEstrazioneZip.isDirectory()){
					Utils.deleteDirTree(cartellaEstrazioneZip);
					cartellaEstrazioneZip.delete();
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		} finally {
			try {
				DbUtils.close(conn);
			} catch(Exception e){
				log.error(e.getMessage());
			}
		}
		
		return new ApplicationAck(abnormals, "Fine Elaborazione - " + errorMsg);
	}//-------------------------------------------------------------------------
	
	private Hashtable<String, Object> verifyZipFiles(String folderNameFrom, String folderNameTo){
		ArrayList<File> alFilesCorrupted = new ArrayList<File>();
		ArrayList<File> alFilesRegular = new ArrayList<File>();
		Hashtable<String, Object> htUnzip = new Hashtable<String, Object>();
		/*
		 * Recupero il contenuto della cartella Load che poi passo al metodo che 
		 * decomprime ogni singolo file
		 */
		String zipFileName = "";
        try{
         /*
          *  unzip dei file pregeo (= i files PDF dovrebbero dovrebbe essere all'interno 
          *  di una cartella chiamata con il codice belfiore del comune) 
          */
			String[] lstDirZip = it.webred.utils.FileUtils.cercaFileDaElaborare(folderNameFrom);
			File file = null;
			boolean cartellaDestinazioneDeterminata = false;
			String extractionFolderName = "";
			Hashtable<String, String> htZipPdf = new Hashtable<String, String>();
			log.info("Inizio decompressione fornitura");
			for (int i = 0; i < lstDirZip.length; i++) {
				zipFileName = lstDirZip[i];
				String ext = zipFileName.substring( zipFileName.lastIndexOf("."), zipFileName.length() );
				if (zipFileName != null && !zipFileName.trim().equalsIgnoreCase("") && ext.trim().equalsIgnoreCase(".zip")){
					file = new File(folderNameFrom + zipFileName);
					log.info("Decompressione del file: " + folderNameFrom + zipFileName);
					if (FileUtils.isZip(file))  {
						boolean decompresso = FileUtils.unzip(file);
						if (decompresso){
							alFilesRegular.add(file);
							/*
							 * Per capire qual'è il contenuto di ogni zip decompresso
							 * devo interrogare ogni volta la cartella di destinazione
							 * per cercare di capire quale file è stato aggiunto
							 */
							if (!cartellaDestinazioneDeterminata){
								/*
								 * Determino la cartella in cui sono stati estratti i PDF
								 */
								extractionFolderName = this.setDestinationFolder(folderNameFrom);
								cartellaDestinazioneDeterminata = true;
							}
							/*
							 * Per ogni file zip decompresso vado a recuperare l'elenco 
							 * dei files della cartella di destinazione per poter 
							 * collegare ogni zip con il suo contenuto  
							 */
							String[] lstDirPdf = it.webred.utils.FileUtils.cercaFileDaElaborare(extractionFolderName);
							for (int ind=0; ind<lstDirPdf.length; ind++){
								String pdfFileName = lstDirPdf[ind].trim();
								boolean giaEsistente = false;
								String nuovoFile = extractionFolderName + pdfFileName;
								/*
								 * Confronto il file pdf corrente con il contenuto della
								 * cartella in cui sono stati estratti i files zip
								 * per capire qual'è il contenuto dello zip corrente
								 */
								Enumeration<String> enKeys = htZipPdf.keys();
								while(enKeys.hasMoreElements()){
									String currentKey = enKeys.nextElement();
									String currentElement = htZipPdf.get(currentKey);
									if ( currentElement != null && !currentElement.trim().equalsIgnoreCase("") && nuovoFile != null && !nuovoFile.trim().equalsIgnoreCase("") ){
										if (currentElement.indexOf( nuovoFile ) != -1 ){
											giaEsistente = true;
										}										
									}
								}//while end
								if (!giaEsistente){
									String filesContenutiNelloZipCorrente = "";
									if (htZipPdf.containsKey(file.getName())){
										filesContenutiNelloZipCorrente = htZipPdf.get( file.getName() );
										filesContenutiNelloZipCorrente += "; " + nuovoFile;
									}else{
										filesContenutiNelloZipCorrente = nuovoFile;
									}
									htZipPdf.put(zipFileName, filesContenutiNelloZipCorrente);									
								}
							}//for end
						}
						else
							alFilesCorrupted.add(file);
					}
				}
			}
			log.info("Fine decompressione fornitura");
			htUnzip.put(CORROTTI, alFilesCorrupted);
			htUnzip.put(REGOLARI, alFilesRegular);
			htUnzip.put(ZIP_PDF, htZipPdf);
        }catch(Exception e){
        	log.error(e.getMessage());
        }
        return htUnzip;
	}//-------------------------------------------------------------------------

	private Hashtable<String, Hashtable<String, String>> readPdf(String folderNamePdf){
		Hashtable<String, Hashtable<String, String>> htPdf = new Hashtable<String, Hashtable<String, String>>();
		String fileInElaborazione = "";
		try{
			/*
			 * Cerco la nuova cartella creata se i PDF non sono sulla cartella principale
			 */
			File folderFrom = new File (folderNamePdf);
			String[] filesList = folderFrom.list();
			if (filesList != null){
				Hashtable<String, String> htPregeoInfo = null;

				for (int index=0; index<filesList.length; index++){
					htPregeoInfo = new Hashtable<String, String>();
					File currentFile = new File(folderNamePdf + filesList[index]);
					if ( currentFile != null && !currentFile.isDirectory() && (currentFile.getPath().endsWith(".pdf") || currentFile.getPath().endsWith(".PDF")) ){
						fileInElaborazione = currentFile.getPath();
						log.info("Inizio estrazione da PDF: " + fileInElaborazione);
						String testo  = PdfExtracter.getTextFromPdf( fileInElaborazione );
						/*
						 * Ogni riga un elemento dell'array
						 */
						boolean trovatoCodicePregeo = false;
						boolean trovatiTitolari = false;
						boolean trovateInfo = false;
						boolean trovataRelazioneTecnica = false;
						boolean trovateNote = false;
						String[] aryTesto = testo.split("\r\n");
						if (aryTesto != null && aryTesto.length>0){
							for (int i=0; i<aryTesto.length; i++){
								if (aryTesto[i] != null){
									String riga = aryTesto[i];
									if (!trovatoCodicePregeo && riga.indexOf(TAG_CODICE)!=-1){
										String codicePregeo = riga.substring( riga.lastIndexOf(":")+2, riga.length() );
										boolean valido = true;
										StringBuffer sb = new StringBuffer(codicePregeo);
										String caratteriConsentiti = "0123456789.";
										for (int z=0; z<sb.length(); z++){
											char currentChar = sb.charAt(z);
											if (caratteriConsentiti.indexOf(currentChar) == -1 ){
												valido = false;
											}
										}
										if (valido){
											htPregeoInfo.put(PREGEO_CODICE, codicePregeo);
											log.info("PREGEO_CODICE " + fileInElaborazione + ": " + codicePregeo);
											trovatoCodicePregeo = true;
										}else{
											log.info("PREGEO_CODICE " + fileInElaborazione + ": NON VALIDO!!!");
										}
									}
									if (!trovatiTitolari && riga.indexOf(TAG_TITOLARI)!=-1){
										String temp = TAG_TITOLARI;
										String titolari = testo.substring( testo.indexOf( temp )+temp.length()+2, testo.indexOf( TAG_REDATTORE ) );
										String tit = titolari.replaceAll("(?i)Firma", " ");
										htPregeoInfo.put(PREGEO_PERSONE_COINVOLTE, tit!=null?tit:"" );
										log.info("PREGEO_PERSONE_COINVOLTE " + fileInElaborazione + ": " + tit!=null?tit:"" );
										trovatiTitolari = true;
									}
									if (!trovateInfo && riga.startsWith("0|")){
										/*
										 * Attenzione alle particelle che possono 
										 * essere anche piu di una ed in tal caso 
										 * sono separate da una virgola
										 */
										//String info = riga.substring(riga.indexOf("0|")+1, riga.length() );
										String info = riga.substring(0, riga.length() );
										if (info != null && !info.trim().equalsIgnoreCase("") && info.endsWith("|")){
											System.out.println("PREGEO_ATTRIBUTI in una sola riga");
										}else{
											System.out.println("PREGEO_ATTRIBUTI su piu righe: verifico le righe successive fino a quando non trovo il pipe finale");
											for (int inizio = i+1; inizio<aryTesto.length; inizio++){
												String row = aryTesto[inizio];
												info += row;
												if (row != null && !row.trim().equalsIgnoreCase("") && row.trim().endsWith("|")){
													break;
												}
											}
										}
										htPregeoInfo.put(PREGEO_ATTRIBUTI, info!=null?info:"" );
										log.info("PREGEO_ATTRIBUTI " + fileInElaborazione + ": " + info!=null?info:"" );
										trovateInfo = true;
									}
									if (!trovateNote && riga.indexOf(TAG_NOTE)!=-1){
										/*
										 * Le note iniziano nella riga 9|... ma il testo
										 * effettivo è contenuto - quando presente - nella riga immediatamente
										 * successiva che inizia per 6|....| 
										 */
										String note = "";
										if (aryTesto.length>=i+1){
											String row = aryTesto[i+1];
											if (row != null && row.startsWith("6|")){
												int primoPipe = row.indexOf('|');
												int ultimoPipe = row.lastIndexOf('|');
												int startSubStr = row.indexOf("6|");
												if (ultimoPipe > primoPipe){
													note += row.substring( startSubStr+2, ultimoPipe );													
												}else{
													note += row.substring( startSubStr+2, row.length()-1 );
												}
											}
										}
										htPregeoInfo.put(PREGEO_NOTE, note!=null?note:"" );
										log.info("PREGEO_NOTE " + fileInElaborazione + ": " + note!=null?note:"" );
										trovateNote = true;
									}
									if (!trovataRelazioneTecnica && riga.indexOf(TAG_RELAZIONE_TECNICA)!=-1){
										/*
										 * Nel PDF abbiamo identificato questa punto di inizio (Relazione Tecnica) 
										 * anceh se poi include anche altre informazioni di testa del
										 * documento e poi non esiste una fine che sia sempre la stessa 
										 * ... quindi per il momento prendiamo fino alla fine del PDF e
										 * consideriamo solo i primi 3900 caratteri (VARCHAR2 max 4000 caratteri)  
										 */
										String inizio = TAG_RELAZIONE_TECNICA;
										String fine = "Pag.";
										
										String relazioneTecnica = testo.substring( testo.indexOf( inizio )+17 );
										if (relazioneTecnica.indexOf(fine)!=-1)
											/*
											 * Se dopo la relazione tecnica esistono altre pagine
											 * si prende fino all'inizio della pagina
											 * successiva 
											 */
											htPregeoInfo.put(PREGEO_RELAZIONE_TECNICA, relazioneTecnica!=null?relazioneTecnica.substring( 0, relazioneTecnica.indexOf(fine)):"");
										else
											/*
											 * Se dopo la relazione tecnica non esistono altre pagine
											 * si prende fino alla fine del file PDF
											 */
											htPregeoInfo.put(PREGEO_RELAZIONE_TECNICA, relazioneTecnica!=null?relazioneTecnica:"" );
										
										log.info("PREGEO_RELAZIONE_TECNICA " + fileInElaborazione + ": " + relazioneTecnica!=null?relazioneTecnica:"" );
										trovataRelazioneTecnica = true;
									}
								}
							}
						}
						if (htPregeoInfo != null && htPregeoInfo.size() > 0 && htPregeoInfo.size() <= 5)
							htPdf.put(currentFile.getName(), htPregeoInfo);
						
						log.info("Fine estrazione da PDF: " + fileInElaborazione);
						
					}
				}
				
			}
		}catch(Exception e){
			log.error(e);
			log.error("ERRORE FILE: " + fileInElaborazione + " " + e.getMessage());
		}
		return htPdf;
	}//-------------------------------------------------------------------------
	
	private String setDestinationFolder(String folderNameFrom){
		/*
		 * Ultima directory trovata nel percorso diversa da elaborati
		 */
		String extractionFolderName = "";
		File folderFrom = new File (folderNameFrom);
		String[] filesList = folderFrom.list();
		if (filesList != null){
			for (int index=0; index<filesList.length; index++){
				File currentFile = new File(folderNameFrom + filesList[index]);
				boolean isDir = currentFile.isDirectory(); 
				if ( new Boolean(isDir) && !filesList[index].equalsIgnoreCase("ELABORATI") )
					extractionFolderName = folderNameFrom + filesList[index]  + "/";					
			}
		}
		return extractionFolderName;
	}//-------------------------------------------------------------------------
	
}

