package it.webred.rulengine.brick.superc.importDati;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author MARCO
 *
 */
@Deprecated
public abstract class ImportFiles extends Command {

	protected static final org.apache.log4j.Logger log = Logger.getLogger(ImportFiles.class.getName());
	protected static final String RETURN_NO_FILE = "Nessun File da Elaborare";
	protected static final String OK = "Elaborazione Terminata Correttamente";
	protected Connection con = null;
	protected String processId ; 
	protected String currentTable;
	
	protected Timestamp dataExport;
	protected Context ctx;
	protected List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
	protected String percorsoFiles = null;
	private HashMap<String, List<String>> filesElaborati  = new LinkedHashMap<String, List<String>>();
	
	public ImportFiles(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	protected abstract void sortFiles(List<String> files) throws RulEngineException;
	protected abstract void normalizza(String belfiore) throws RulEngineException;
	// opera su una connessione separata altrimenti una eccezzione in una DML potrebbe mndare in commit 
	// tutto !
	protected abstract void preProcesing(Connection con) throws RulEngineException;
	protected abstract void preProcesingFile(String file) throws RulEngineException;
	protected abstract CommandAck execute(Context ctx, Connection conn) throws CommandException;
	
	
	public CommandAck run(Context ctx) throws CommandException {
		
		Connection conn = ctx.getConnection((String)ctx.get("connessione"));
		CommandAck ok = execute(ctx, conn);
		
		boolean anomalievf = false;
		if (ok instanceof RejectAck || ok instanceof ErrorAck)
			return ok;
		if (ok instanceof ApplicationAck)
		{
			if (abnormals.size() > 0)
				anomalievf = true;
		}
		
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

		
		
		
		if (anomalievf)
		{
			CommandAck ritorno = new ApplicationAck("Presenti Anomalie di Elaborazione");
			((ApplicationAck) ritorno).setAbn(abnormals);
			return (ritorno);
		}
         
		
		return new ApplicationAck("Elaborazione terminata correttamente");
		
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
	protected String elabora(Connection conn, Context ctx , String percorsoFiles, String tempTable, boolean saltaIntestazione, String tipoRecord) throws Exception {
		try {
			this.processId = ctx.getProcessID();
			this.con = conn;
			this.dataExport = new Timestamp(new java.util.Date().getTime());
			this.ctx = ctx;
			this.percorsoFiles = percorsoFiles;
			this.currentTable = tempTable;

			//Connection conPre = RulesConnection.getConnection(this.getConnectionName());
			Connection conPre = RulesConnection.getConnection((String)ctx.get("connessione"));
			conPre.setAutoCommit(false);
			
			try {
				preProcesing( conPre);
				conPre.commit();
			} catch (Exception e) {
				conPre.rollback();
				log.error("Errore imprevisto su preProcessing",e);
				throw new RulEngineException("Errore imprevisto su preProcessing");
			}
			
			
			
			
			String[] files = this.cercaFileDaElaborare(percorsoFiles);
			

			
			List<String> fileDaElaborare = Arrays.asList(files);  
			if(fileDaElaborare.size() < 1)
				return RETURN_NO_FILE;
			
			// ordino in base ad un principio specifico
			sortFiles(fileDaElaborare);
			
			String cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/")+1);
	
			// creo cartella per mettere i files elaborati
			new File(cartellaDati+"ELABORATI/").mkdir();
			// LEGGO I FILES E LI METTO NELLA TABELLA TEMPORANEA
			for(int i=0;i< fileDaElaborare.size();i++)
			{
				String file = fileDaElaborare.get(i);
				log.info("ELABORO " + file + "TipoRecord:" + (tipoRecord!=null?tipoRecord:""));
				preProcesingFile(file);
				// METTO IL FILE NELLA TABELLA TEMPORANEA
				leggiFile(file, cartellaDati, tempTable, saltaIntestazione, tipoRecord);
				
			}
			
			normalizza(ctx.getBelfiore());
			
			// registro i files elaborati per spostarli alla fine dell'elaborazione della regola
			filesElaborati.put(cartellaDati, fileDaElaborare);
			
		} finally  {
			//truncateTable(tempTable);
		}

		return OK;
	}
	
	

	
	
	
	private void leggiFile(String file, String cartellaDati, String tempTable , boolean saltaIntestazione, String tipoRecord) throws Exception {
		
		BufferedReader fileIn = null;
		List<String> errori = new ArrayList<String>();
		try {
			if(new File(cartellaDati+"ELABORATI/"+file).exists())
			{
				log.debug("Scartato file perche già elaborato " + file);
				new File(cartellaDati+file).delete();
				return;
			}
			fileIn = new BufferedReader(new FileReader(cartellaDati+file));
			
			String currentLine=null;
			int riga = 1;
			boolean primaRiga=true;
			String insertSql = null;
			while ((currentLine = fileIn.readLine()) != null)
			{
				//List<String> campi = Arrays.asList(currentLine.split(("\\|")));
				//modificato Filippo Mazzini per leggere anche i valori = ""
				List<String> campi = Arrays.asList(currentLine.split("\\|", -1));
				
				if (saltaIntestazione && primaRiga) {
					primaRiga = false;
					continue;
				}
				// se devo leggere un particolare tipo record
				// allora se nmon è quello salto il record
				if (tipoRecord!=null && !tipoRecord.equalsIgnoreCase(campi.get(0))) 
					continue;

				if (tipoRecord!=null)
					campi = Arrays.asList(currentLine.substring(currentLine.indexOf("|")+1).split("\\|", -1));
				else
					campi = Arrays.asList(currentLine.split("\\|", -1));
						
				// nome campi sulla riga 1
				if (riga==1) {
					StringBuffer s = new StringBuffer();
					s.append("INSERT INTO ");
					s.append(tempTable);
					s.append(" VALUES(");
					for (int ii = 0; ii < campi.size(); ii++)
					{
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
				for (int ii = 0; ii < campi.size(); ii++)
				{
					ps.setString(ii+1, campi.get(ii));
				}
				ps.setString(campi.size()+1, processId);
				ps.setString(campi.size()+2, "0"); // re_flag_elaborato
				ps.setTimestamp(campi.size()+3, this.dataExport); // dt_exp_dato
				ps.executeUpdate();
				} catch (Exception e) {
					log.error(currentLine);
					log.error("Errore di inserimento record", e);
					errori.add(currentLine);

					//throw new RulEngineException("Errore di inserimento !", e);
				}  finally {
					if (ps!=null)
						ps.close();
				}
				primaRiga = false;

			}
	
			
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
			}
		}
		
	} 
	
	protected String[] cercaFileDaElaborare(String percorsoFiles)
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
	

}
