package it.webred.rulengine.brick.F704;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcDatiTec;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcIndirizzi;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcPersona;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcessioni;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcessioniCatasto;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCPersona;
import it.webred.rulengine.brick.loadDwh.utils.DBFToCSVConverter;
import it.webred.rulengine.brick.loadDwh.utils.FileConverter;
import it.webred.rulengine.brick.loadDwh.utils.XLSToCSVConverter;
import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.superc.InsertDwh.InsertDwh;
import it.webred.rulengine.brick.superc.importDati.ImportFiles;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ImportConedil extends ImportFiles implements Rule {

	


	private String IMPORTCONEDIL_CONEDIL = getProperty("F704.table1.name");
	private String IMPORTCONEDIL_CONEDIL_IDX = getProperty("F704.table1.idx");
	
	private String createTableConedilSql = getProperty("F704.sql.CREATE_CONEDIL");
	private String selectTableConedilSql = getProperty("F704.sql.IMPORTCONEDIL");
	
	private static final boolean EX_NOVO = true;
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportConedil.class.getName());
	
	private static final int righePerTest = -1; //test fatto con 100; se = -1 non è preso in considerazione e il ciclo while non si ferma
	
	
	public ImportConedil(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	public CommandAck execute(Context ctx, Connection conn)
	throws CommandException {		
		
		String percorsoFilesConedil = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("F704.dir.fileConedil");

		String ret = null;
		try {			
			log.info("ELABORO " + percorsoFilesConedil);
			ret = this.elabora(conn, ctx, percorsoFilesConedil, IMPORTCONEDIL_CONEDIL, true,null);
			log.info(ret);
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		}		
		return new ApplicationAck("Elaborazione terminata correttamente");
		
	}


	@Override
	protected void sortFiles(List<String> files) {
		Collections.sort(files);
	}


	@Override
	protected void normalizza(String belfiore) throws RulEngineException {
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(selectTableConedilSql);
			rs = ps.executeQuery();
			
			// INSERIMENTO CONCESSIONI CONEDIL
			
			CommandLauncher launchSitConcessioni = new CommandLauncher(belfiore);
			Command cmdSitConcessioni = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class.getName(),true);
			
			BeanCommand bcSitConcessioni = new BeanCommand();
			bcSitConcessioni = cmdSitConcessioni.getBeanCommand();
			
			CommandLauncher launchSitCPersona = new CommandLauncher(belfiore);
			Command cmdSitCPersona = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class.getName(),true);
			
			BeanCommand bcSitCPersona = new BeanCommand();
			bcSitCPersona = cmdSitCPersona.getBeanCommand();
			
			CommandLauncher launchSitConcPersona = new CommandLauncher(belfiore);
			Command cmdSitConcPersona = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class.getName(),true);
			
			BeanCommand bcSitConcPersona = new BeanCommand();
			bcSitConcPersona = cmdSitConcPersona.getBeanCommand();
			
			CommandLauncher launchSitConcessioniCatasto = new CommandLauncher(belfiore);
			Command cmdSitConcessioniCatasto = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class.getName(),true);
			
			BeanCommand bcSitConcessioniCatasto = new BeanCommand();
			bcSitConcessioniCatasto = cmdSitConcessioniCatasto.getBeanCommand();

			CommandLauncher launchSitConcIndirizzi = new CommandLauncher(belfiore);
			Command cmdSitConcIndirizzi = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class.getName(),true);
			
			BeanCommand bcSitConcIndirizzi = new BeanCommand();
			bcSitConcIndirizzi = cmdSitConcIndirizzi.getBeanCommand();
			
			CommandLauncher launchSitCConcDatiTec = new CommandLauncher(belfiore);
			Command cmdSitCConcDatiTec = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcDatiTec.class.getName(),true);
			
			BeanCommand bcSitCConcDatiTec = new BeanCommand();
			bcSitCConcDatiTec = cmdSitCConcDatiTec.getBeanCommand();
			
			int conta = 0;
			
			while (rs.next()) {
				
				conta++;
				
				// INSERIMENTO SIT_C_CONCESSIONI
				EnvInsertDwh ec = new EnvSitCConcessioni(this.IMPORTCONEDIL_CONEDIL, "A");
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launchSitConcessioni, bcSitConcessioni, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				// INSERIMENTO SIT_C_PERSONA
				EnvInsertDwh ecp = new EnvSitCPersona(this.IMPORTCONEDIL_CONEDIL, "A");
				ecp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ecp, launchSitCPersona, bcSitCPersona, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				// INSERIMENTO SIT_C_CONC_PERSONA
				EnvInsertDwh eccp = new EnvSitCConcPersona(this.IMPORTCONEDIL_CONEDIL, "A");
				eccp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, eccp, launchSitConcPersona, bcSitConcPersona, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				// INSERIMENTO SIT_C_CONCESSIONI_CATASTO	
				EnvInsertDwh ecc = new EnvSitCConcessioniCatasto(this.IMPORTCONEDIL_CONEDIL, "A");
				ecc.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ecc, launchSitConcessioniCatasto, bcSitConcessioniCatasto, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				// INSERIMENTO SIT_C_CONC_INDIRIZZI	
				EnvInsertDwh eci = new EnvSitCConcIndirizzi(this.IMPORTCONEDIL_CONEDIL, "A");
				eci.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, eci, launchSitConcIndirizzi, bcSitConcIndirizzi, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				// INSERIMENTO SIT_C_CONC_DATI_TEC
				EnvInsertDwh ecdt = new EnvSitCConcDatiTec(this.IMPORTCONEDIL_CONEDIL, "A");
				ecdt.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ecdt, launchSitCConcDatiTec, bcSitCConcDatiTec, (String)ctx.get("connessione"), ctx, rs, abnormals);
				
				if (righePerTest > -1 && conta == righePerTest) {
					break;
				}
				
			}
			
		} catch (SQLException e) {
			throw new RulEngineException("Problemi durante la normalizzazione dei dati", e);
		} catch (CommandException e) {
			throw new RulEngineException("Errore durante la chiamata al comando di inserimento dati", e);
		} catch (Exception e) {
			throw new RulEngineException("Problemi durante la normalizzazione dei dati", e);
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (ps!=null)
					ps.close();
			} catch (SQLException e1) {
				
			}
		}		
	}
	
	
	@Override
	protected void preProcesing(Connection con) {
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(createTableConedilSql);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		try {
			st = con.createStatement();
			st.execute(this.IMPORTCONEDIL_CONEDIL_IDX);
		} catch (SQLException e1) {
			log.warn("Indice esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		if (EX_NOVO) {
			try {
				String[] tablesToDelete = new String[] {IMPORTCONEDIL_CONEDIL, "SIT_C_CONC_DATI_TEC",
														"SIT_C_CONC_INDIRIZZI", "SIT_C_CONCESSIONI_CATASTO",
														"SIT_C_CONC_PERSONA", "SIT_C_PERSONA", "SIT_C_CONCESSIONI"};
				for (String tableToDelete : tablesToDelete) {
					st = con.createStatement();
					st.execute("DELETE FROM " + tableToDelete);
				}				
			} catch (SQLException e1) {
				log.error("Errore nella cancellazione preliminare della tabella " + IMPORTCONEDIL_CONEDIL);
			}
		}
		
		//i file possono essere forniti in formato .dbf o .xls
		//in questo caso, devono essere convertiti in .csv
		final String prefissoFile = percorsoFiles.substring(percorsoFiles.lastIndexOf("/") + 1);
		String cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/"));

		File filesDati = new File(cartellaDati);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name)
			{
				return name.startsWith(prefissoFile);
			}
		};
		String[] fileList = filesDati.list(filter);
		boolean creataDir = false;
		for (String fileName : fileList) {			
			String fileCompleteName = cartellaDati + "/" + fileName;
			if (fileName.toUpperCase().endsWith(".DBF") || fileName.toUpperCase().endsWith(".XLS")) {
				String folderCompleteName = null;
				if (!creataDir) {
					String folderName = "FILE_ORIGINALI_";
					folderName += new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
					folderCompleteName = cartellaDati + "/" + folderName + "/";
					new File(folderCompleteName).mkdir();
					creataDir = true;
				}
				FileConverter conv = null;
				if (fileName.toUpperCase().endsWith(".DBF")) {
					conv = new DBFToCSVConverter();
				} else if (fileName.toUpperCase().endsWith(".XLS")) {
					conv = new XLSToCSVConverter();
				}
				String fileCompleteNameCSV = fileCompleteName.substring(0, fileCompleteName.length() - 4);
				if (fileName.toUpperCase().endsWith(".DBF")) {
					fileCompleteNameCSV += "DBF";
				} else if (fileName.toUpperCase().endsWith(".XLS")) {
					fileCompleteNameCSV += "XLS";
				}
				String completeDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
				fileCompleteNameCSV += "_" + completeDate + ".csv";
				try {
					conv.save(fileCompleteName, fileCompleteNameCSV, ctx.getBelfiore());
				} catch (Exception e) {
					log.error("Errore nella conversione del file " + fileName);
				}
				File fileToDelete = new File(fileCompleteName);
				if (!fileToDelete.renameTo(new File(folderCompleteName + fileName)))
					log.error("Errore nello spostamento del file " + fileName);
			}
		}
		
	}


	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		//TODO
		//verificare se il nome del file fornito contiene un riferimento alla data di export
		this.dataExport = new Timestamp(new Date().getTime());
	}
	
	

	

	
	
	
}

