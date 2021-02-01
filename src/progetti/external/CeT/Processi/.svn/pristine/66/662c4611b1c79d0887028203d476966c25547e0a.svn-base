package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.ImportFilesException;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.TroppiErroriImportFileException;
import it.webred.rulengine.brick.loadDwh.load.util.CharsetDetector;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.RulEngineException;

import it.webred.utils.FileUtils;
import it.webred.utils.GenericTuples;
import it.webred.utils.GenericTuples.T2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import java.sql.Types;


/**
 * @author MARCO
 *
 */
public abstract class ImportFiles<T extends EnvImport>  {

        protected static final org.apache.log4j.Logger log = Logger.getLogger(ImportFiles.class.getName());
        public static final String RETURN_NO_FILE = "Nessun File da Elaborare";
        public static final String OK = "Elaborazione Terminata Correttamente";
        protected Connection con = null;
        protected String processId ; 
        protected String currentTable;
        protected Context ctx;
        private String connectionName;
        protected String percorsoFiles;
        public T env;
        

        protected abstract void preProcesingFile(String file) throws RulEngineException;
        protected abstract void procesingFile(String file, String cartellaDati) throws RulEngineException;
        public abstract void sortFiles(List<String> files) throws RulEngineException;
        public abstract boolean isIntestazioneSuPrimaRiga() throws RulEngineException;
        public  abstract void preProcesing(Connection con) throws RulEngineException;
        public abstract  Timestamp getDataExport() throws RulEngineException;
        public abstract  List<String> getValoriFromLine(String tipoRecord,String currentLine) throws RulEngineException;
        public abstract  void tracciaFornitura(String file, String cartellaDati, String line) throws RulEngineException;

        /*
         * Restituisce il codice di provenienza di default nel caso che questa non sia indicata sul file di import standard
         */
        public  abstract String getProvenienzaDefault() throws RulEngineException;

        

        public ImportFiles(T env) {
                this.env = env;
        }

        public T getEnv() {
                return env;
        }
        
        
        
        /**
         * @return Messaggio sull'esito dell'elaborazione
         * @throws RulEngineException
         */
        public GenericTuples.T2<String,List<RAbNormal>>  avviaImportazioneFiles()
        throws RulEngineException {

                List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
                this.percorsoFiles = env.getPercorsoFiles();
                this.connectionName = env.getConnectionName();
                
                log.info("Avvio importazione file nel percorso:" + this.percorsoFiles + " - connessione = " + this.connectionName);
                
                Statement stmt= null;
                try {
                        this.con = env.getConn();
                        stmt =  this.con.createStatement();
                        //stmt.execute("ALTER SESSION ENABLE PARALLEL DML");
                } catch (Exception e1) {
                        try {
                                DbUtils.close(this.con);
                        } catch (SQLException e2) {
                        	log.error(e2);
                        }
                        throw new ImportFilesException("Problema di connessione!",e1);
                } finally {
                        try {
                                DbUtils.close(stmt);
                        } catch (SQLException e) {
                                // TODO Auto-generated catch block
                        	log.error(e);
                        }
                }
                
                this.ctx = env.getCtx();
                this.processId = ctx.getProcessID();


                String ret = null;
                try {
                        ret = this.elabora(this.con, ctx, percorsoFiles , true);
                } catch (it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.TroppiErroriImportFileException e) {
                        try {
                                DbUtils.close(this.con);
                        } catch (SQLException e1) {
                        	log.error(e1);
                        }
                        throw e;
                } catch (Exception e) {
                        try {
                                DbUtils.close(this.con);
                        } catch (SQLException e1) {
                        	log.error(e1);
                        }
                        log.error("Problema durante la procedura di elaborazione dei files", e);
                        throw new ImportFilesException("Problema durante la procedura di elaborazione dei files",e);
                }
                boolean anomalievf = false;

                if (RETURN_NO_FILE.equalsIgnoreCase(ret))
                        return new T2<String, List<RAbNormal>>(RETURN_NO_FILE,abnormals);

                
                if (OK.equalsIgnoreCase(ret))
                {
                        abnormals  = env.getAbnormals();
                        if (abnormals.size() > 0)
                                anomalievf = true;
                }
                
                
        
                // non li sposto piu tutti insieme ma uno per uno dentro il metodo fi ConcreteImport
                /*
                // sposto tutti i files sotto la cartella ELABORATI
                Iterator<Map.Entry<String,List<String>>> it = filesElaborati.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String,List<String>> pair = it.next();
                    String cartella =  pair.getKey();
                    List<String> filesToDelete =  (List<String>) pair.getValue();
                        for(int i=0;i< filesToDelete.size();i++)
                        {
                                String file = filesToDelete.get(i);
                                new File(cartella+file).renameTo(new File(cartella+"ELABORATI/"+file));
                        }
                }

                */
                
                return new T2<String, List<RAbNormal>>(OK,abnormals);
         
                
                
        }
        
        
        
        /**
         * il metodo viene chiamato dal metodo elabora , prima della esecuzione del processamento di tutti i files
         * Richiama il metodo preProcesing del singolo driver e scompatta eventuali file zip.
         * @throws RulEngineException 
         */
        private void preProcessingFiles() throws RulEngineException {
                try {
                        
                        // opera su una connessione separata altrimenti una eccezione in una DML potrebbe mandare in commit 
                        // tutto !
                        Connection conPre = RulesConnection.getConnection(this.connectionName);
                        conPre.setAutoCommit(false);
                        log.info("Creazione tabelle:" + conPre);
                        
                        
                        try {
                                preProcesing(conPre);
                                conPre.commit();
                        } catch (RulEngineException e) {
                                conPre.rollback();
                                log.error("Errore imprevisto su preProcessing",e);
                                throw e;
                        } catch (Exception e) {
                                conPre.rollback();
                                log.error("Errore imprevisto su preProcessing",e);
                                throw new RulEngineException("Errore imprevisto su preProcessing");
                        }
                                
                        // eventuale unzip dei file
                        log.info("Cerco i files da elaborare in " + this.percorsoFiles);
                        String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(this.percorsoFiles);
                        if (fs==null) {
                                log.warn("Lista dei files NULLA - probabile problema nei percorsi" );
                        } else {
                                log.info("Trovati " + fs.length + " in " + this.percorsoFiles );
                                for  (int i = 0; i < fs.length; i++) {
                                        File f = new File(this.percorsoFiles+fs[i]);
                                        log.debug("Percorso file da unzip:" + this.percorsoFiles+fs[i] );
                                        if (FileUtils.isZip(f))  {
                                                FileUtils.unzip(f);
                                                boolean del=false;
                                                int count = 1;
                                                while (!del) {
                                                        del = f.delete();
                                                        if (count>10000)
                                                                throw new RulEngineException("Impossibile cancellare file zip dopo scompattamento - elaborazione interrotta");
                                                        count+=1;
                                                                
                                                }
                                        }
                                }
                        }
                        
                } catch (Exception e) {
                        log.error("Errore nella ricerca dei files da elaborare!", e);
                        throw new RulEngineException("Errore imprevisto su preProcessing",e);

                }
        }
        
        /**
         * 
         * 
         * @param conn
         * @param ctx
         * @param percorsoFiles
         * @param tempTable
         * @param saltaIntestazione - se true salta la prima riga del file 
         * @param tipoRecord - specifica il tiporecord da leggere sul file 
         * @return
         * @throws Exception
         */
        protected String elabora(Connection conn, Context ctx , String percorsoFiles, boolean saltaIntestazione) throws Exception {
                try {
                        
                        preProcessingFiles();
                        
                        log.info("Cerco file da elaorare in percorsoFiles=" + percorsoFiles); 
                        String[] files = this.cercaFileDaElaborare(percorsoFiles);
                        

                        
                        List<String> fileDaElaborare = Arrays.asList(files);  
                        if(fileDaElaborare.size() < 1) {
                                log.info("NO FILES!");
                                return RETURN_NO_FILE;
                        }
                        
                        // ordino in base ad un principio specifico
                        sortFiles(fileDaElaborare);
                        
                        String cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/")+1);
                        if (cartellaDati==null)
                                cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("\\")+1);
                        log.info("cartellaDati=" + cartellaDati);

                        // creo cartella per mettere i files elaborati
                        new File(cartellaDati+"ELABORATI/").mkdir();


                        // LEGGO I FILES E LI METTO NELLA TABELLA TEMPORANEA
                        for(int i=0;i< fileDaElaborare.size();i++)
                        {
                                String file = fileDaElaborare.get(i);
                                
                                //filtro file
                                boolean daElaborare = filtroFile(file, fileDaElaborare, cartellaDati);
                                if(daElaborare){
                                        log.debug("File corrente: "+file);
                                
                                        this.preProcesingFile(file);
        
                                        this.procesingFile(file, cartellaDati);
                                        
                                        postElaborazioneAction(file, fileDaElaborare, cartellaDati);
                                }
                        }
                        
                        
                } finally  {
                        //truncateTable(tempTable);
                }
                return OK;

        }
        
        /**
         * il metodo viene chiamato dal metodo elabora , prima della esecuzione del processamento di tutti i files
         * @throws RulEngineException 
         */
        public boolean filtroFile(String file, List<String> fileDaElaborare, String cartellaDati) throws RulEngineException {
                return true;
        }
        
        
        protected boolean leggiFile(String file, String cartellaDati, String tempTable , String tipoRecord,Timestamp dataExport) throws Exception {
                
                BufferedReader fileIn = null;
                List<String> errori = new ArrayList<String>();
                boolean lettoqualcosa = false;
                try {
                        if(new File(cartellaDati+"ELABORATI/"+file).exists())
                        {
                                log.debug("Scartato file perche già elaborato " + file);
                                RAbNormal abn = new RAbNormal();
                                abn.setMessage("Scartato file perche già elaborato " + file);
                                abn.setMessageDate(new Timestamp(new Date().getTime() ));
                                env.getAbnormals().add(abn);
                                log.info("APERTURA FILE" + cartellaDati + file);
                                new File(cartellaDati+file).delete();
                                return false;
                        }
                        //fileIn = new BufferedReader(new FileReader(cartellaDati+file));
                        
                        //problema file della fornitura non in codifica UTF8
                        //MODIFICATO
                        /*synchronized (this) {
                                CharsetDetector cd = new CharsetDetector();
                                String[] charsetsToBeTested = {"UTF-8", "windows-1252", "windows-1253", "ISO-8859-7"}; //inserire eventuali altri charset
                                Charset c = cd.detectCharset(new File(cartellaDati+file), charsetsToBeTested, true);
                                if (c == null) {
                                        c = Charset.forName("ISO-8859-7"); //default
                                }
                                String cn = c.name();
                                if (!cn.equalsIgnoreCase("UTF-8")) {
                                        BufferedReader fileInTest = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), c));
                        String currentLine = null;
                                        PrintWriter out = new PrintWriter(new File(cartellaDati+file+".new"), "UTF8");
                                        while ((currentLine = fileInTest.readLine()) != null) {
                                out.println(new String(currentLine.getBytes(cn)));
                        }
                        out.close();
                        fileInTest.close();
                        //new File(cartellaDati+file).delete();
                        //salva il file in ELABORATI come .old
                        new File(cartellaDati+file).renameTo(new File(cartellaDati+"ELABORATI/"+file+".old"));
                        new File(cartellaDati+file+".new").renameTo(new File(cartellaDati+file));
                                }
                        }
                        
                        fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), "UTF8"));*/
                        //CON:
                        String cn = getCharsetName(file);
                        if (cn == null) {
                        	CharsetDetector cd = new CharsetDetector();
                            String[] charsetsToBeTested = {"UTF-8", "windows-1252", "windows-1253", "ISO-8859-7"}; //inserire eventuali altri charset
                            Charset c = cd.detectCharset(new File(cartellaDati+file), charsetsToBeTested, true);
                            if (c == null) {
                                    c = Charset.forName("ISO-8859-7"); //default
                            }
                            cn = c.name();
                        }                        
                        fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), cn));
                        //FINE MODIFICATO

                        String currentLine=null;
                        int riga = 1;
                        boolean primaRiga=true;
                        String insertSql = null;
                        while ((currentLine = fileIn.readLine()) != null)
                        {
                                try {
                        
                                        String trFromLine = null;
                                        if (tipoRecord!=null && this instanceof ImportFilesWithTipoRecord) {
                                                trFromLine = ((ImportFilesWithTipoRecord)this).getTipoRecordFromLine(currentLine);
                                        } else if (tipoRecord!=null && !(this instanceof ImportFilesWithTipoRecord)) {
                                                throw new RulEngineException("Il Componente di Import deve essere di tipo  ImportFilesWithTipoRecord ");
                                        }
                                                
                                        boolean saltaIntestazione = isIntestazioneSuPrimaRiga();
                                        if (saltaIntestazione && primaRiga) {
                                                primaRiga = false;
                                                continue;
                                        }
                                        // se devo leggere un particolare tipo record
                                        // allora se non è quello salto il record
                                        if (tipoRecord!=null && !tipoRecord.equalsIgnoreCase(trFromLine)) 
                                                continue;
                                        
                                        lettoqualcosa = true;
                                        
                                        if(riga==1){
                                                // Traccia File Forniture (mantenere prima di getValoriFromLine!):
                                                // valorizza l'anno fornitura per elaborazione del caricamento di alcune fonti
                                                try {
                                                        tracciaFornitura(file, cartellaDati, currentLine);
                                                } catch (Exception e ) {
                                                        log.warn("Problemi nel tracciare la fornitura -  continuo ....", e);
                                                }
                                        }
                                        
                                        List<String> campi = getValoriFromLine(tipoRecord,currentLine);
                                        // nome campi sulla riga 1
                                        if (riga==1) {
                                                StringBuffer s = new StringBuffer();
                                                s.append("INSERT INTO ");
                                                s.append(tempTable);
                                                s.append(" VALUES(");
                                                for (int ii = 0; ii < campi.size(); ii++)       {
                                                                s.append("?,");
                                                }
                                                s.append("?,"); // processid
                                                s.append("?,"); // re_flag_elaborato
                                                s.append("?)"); // dt_exp_dato 
                                                insertSql = s.toString();
                                        }
                                        riga++;
                                        
                                        java.sql.PreparedStatement ps=null;
                                        try {
                                                ps = con.prepareStatement(insertSql);
                                                
                                                for (int ii = 0; ii < campi.size(); ii++)       {
                                                        String v = campi.get(ii)!=null ? campi.get(ii).trim() : null;
                                                        if(v != null){
                                                                ps.setString(ii+1, v);
                                                        }else{ 
                                                                ps.setNull(ii+1,Types.VARCHAR);
                                                        }
                                                }
                                                
                                                ps.setString(campi.size()+1, processId);
                                                ps.setString(campi.size()+2, "0"); // re_flag_elaborato
                                                ps.setTimestamp(campi.size()+3, dataExport); // dt_exp_dato
                                                
                                                
                                                ps.executeUpdate();
                                        } catch (Exception e) {
                                                log.error("Errore di inserimento record", e);
                                                log.error(insertSql);
                                                log.error(currentLine);
                                                errori.add(currentLine);
                                        }  finally {
                                                if (ps!=null)
                                                        ps.close();
                                        }

                                } catch (Exception e ) {
                                        log.error("ERRORE currentline=" + currentLine, e);
                                        errori.add(currentLine);
                                }                               
                                if (errori.size()>100) {
                                        try {
                                                con.rollback();
                                        } catch (SQLException e) {
                                        }
                                        String errmsg = "USCITA FORZATA NELLA LETTURA DEL FILE " + file+" : ci sono troppi ERRORI (>100)";
                                        errmsg += " - EFFETTUATO ROLLBACK DELLA CONNESSIONE (TABELLA " + tempTable + ")";
                                        errori.add(errmsg);
                                        log.error(errmsg);
                                        throw new RulEngineException(errmsg);
                                }
                                
                                
                                primaRiga = false;
                        }
        
                        return lettoqualcosa;
                } finally {
                        if (fileIn!=null)
                                fileIn.close();
                        if (errori.size()>0) {
                                PrintWriter  pw = new PrintWriter (cartellaDati+"ELABORATI/"+file+".err");
                                for (int ii = 0; ii < errori.size(); ii++)
                                {
                                        pw.println(errori.get(ii));
                                }
                                pw.close();
                                throw new TroppiErroriImportFileException("Errore di inserimento ! Prodotto file " + file+".err");
                                
                        }
                }
                
        } 
        
        
        private String getCartellaDati(String percorsoFiles){
                // se il percorso non è una directory allora vuol dire che ho indicato un prefisso
                File percorsoFilesFiles = new File(percorsoFiles);
                boolean isPrefix =false;
                if (percorsoFilesFiles!=null)
                        isPrefix = !percorsoFilesFiles.isDirectory();
                
                String cartellaDati = null;
                String prefixPossibile = null;
                if (isPrefix) {
                        // controllo la possibilità che nel property abbia indicato un prefisso e non una cartella
                        
                        if (percorsoFiles.lastIndexOf("/") ==-1) {
                                prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("\\") + 1);
                                cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("\\"));
                        } else {
                                prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("/") + 1);
                                cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/"));
                        }
                } else {
                        cartellaDati = percorsoFiles;
                }
                
                return cartellaDati;
                
        }
        
        
        public String[] cercaFileDaElaborare(String percorsoFiles)
        throws Exception
        {

        try
        {
                // se il percorso non è una directory allora vuol dire che ho indicato un prefisso
                File percorsoFilesFiles = new File(percorsoFiles);
                boolean isPrefix =false;
                if (percorsoFilesFiles!=null)
                        isPrefix = !percorsoFilesFiles.isDirectory();
                
                String cartellaDati = null;
                String prefixPossibile = null;
                if (isPrefix) {
                        // controllo la possibilità che nel property abbia indicato un prefisso e non una cartella
                        
                        if (percorsoFiles.lastIndexOf("/") ==-1) {
                                prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("\\") + 1);
                                cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("\\"));
                        } else {
                                prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("/") + 1);
                                cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/"));
                        }
                } else {
                        cartellaDati = percorsoFiles;
                }
                final String prefissoFile = prefixPossibile;

                File filesDati = new File(cartellaDati);
                

                FilenameFilter filter = new FilenameFilter() {
                        public boolean accept(File dir, String name)
                        {
                                if ("ELABORATI".equals(name))
                                        return false;
                                
                                if ((prefissoFile==null || prefissoFile.equals("")))
                                        return true;
                                
                                return name.startsWith(prefissoFile);
                        }
                };
                return  filesDati.list(filter);
        }
        catch (Exception e)
        {
                log.error("Errore nel metodo cercaFileDaElaborare: ", e);
                throw e;

        }               
        }

        public String getProperty(String propName) {
                
                String p =  DwhUtils.getProperty(this.getClass(), propName);
                return p;
        }

        
        protected void postElaborazioneAction(String file,
                        List<String> fileDaElaborare, String cartellaFiles) {
                // TODO Auto-generated method stub
                
        }
        
        /**
         * estrae la data di estrazioen del file come ultimo dato del nome del file NOMEFONTE_ENTE_DATA.txt
         * @param file
         * @param cartellaDati
         * @param line
         * @return
         * @throws RulEngineException
         */
        protected Date estraiDataFornitura(String file, String dateFormat)
                        throws RulEngineException{
                
                try {
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                        String fileSenzaExt = file.substring(0, file.lastIndexOf(".")); 
                        String[] arr = fileSenzaExt.split("_");
                        String data= null;
                        if(arr != null && arr.length > 1) 
                                data = arr[2];
                        else
                                return null;

                        return sdf.parse(data);
                } catch (Exception e) {
                        throw new RulEngineException("Impossibile estrarre la data fornitura dal file " + file,e);
                }
        }        
        
        protected String getCharsetName(String fileName) {
        	return null;
        }
        
}