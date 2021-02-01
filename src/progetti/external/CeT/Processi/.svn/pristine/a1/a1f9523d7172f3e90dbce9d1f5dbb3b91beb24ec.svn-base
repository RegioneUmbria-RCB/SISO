package it.webred.rulengine.brick.F205;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcIndirizziOnlyOne;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcPersonaOnlyOne;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcessioniCatastoOnlyOne;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcessioniOnlyOne;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCPersonaOnlyOne;

import it.webred.rulengine.brick.superc.InsertDwh.*;
import it.webred.rulengine.brick.superc.importDati.ImportFiles;

import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.def.TypeFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ImportConedilOnlyOne extends ImportFiles implements Rule   {


	private String IMPORTCONEDILONLYONE_ANAGRAFE = getProperty("table1.name");
	private String IMPORTCONEDILONLYONE_CONEDIL = getProperty("table2.name");
	private String IMPORTCONEDILONLYONE_ANAGRAFE_IDX = getProperty("table1.idx");
	private String IMPORTCONEDILONLYONE_CONEDIL_IDX = getProperty("table2.idx");
	
	private String createTableAnaSql = getProperty("sql.CREATE_IMPORTCONEDILONLYONE_ANAGRAFE");
	private String createTableConedilSql = getProperty("sql.CREATE_IMPORTCONEDILONLYONE_CONEDIL");
		
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportConedilOnlyOne.class.getName());
	
	
	public ImportConedilOnlyOne(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	public CommandAck execute(Context ctx, Connection conn) throws CommandException {
		

		
		String percorsoFilesAna = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileAnagrafica");
		String percorsoFilesConedil = Utils. getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedil");
		String percorsoFilesConedi2 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi2");
		String percorsoFilesConedi3 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi3");
		String percorsoFilesConedi4 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi4");
		String percorsoFilesConedi5 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi5");
		String percorsoFilesConedi9 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi9");
		String percorsoFilesConedi10 = Utils.getConfigProperty("dir.files",ctx.getBelfiore(),ctx.getIdFonte())+getProperty("dir.fileConedi10");

		String ret = null;

		try {
			log.info("ELABORO " + percorsoFilesAna);
			ret = this.elabora(conn, ctx, percorsoFilesAna , IMPORTCONEDILONLYONE_ANAGRAFE , false,null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedil);
			ret = this.elabora(conn, ctx, percorsoFilesConedil, IMPORTCONEDILONLYONE_CONEDIL, true ,null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi2);
			ret = this.elabora(conn, ctx, percorsoFilesConedi2, IMPORTCONEDILONLYONE_CONEDIL , true,null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi3);
			ret = this.elabora(conn, ctx, percorsoFilesConedi3, IMPORTCONEDILONLYONE_CONEDIL , true,null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi4);
			ret = this.elabora(conn, ctx, percorsoFilesConedi4, IMPORTCONEDILONLYONE_CONEDIL, true, null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi5);
			ret = this.elabora(conn, ctx, percorsoFilesConedi5, IMPORTCONEDILONLYONE_CONEDIL, true, null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi9);
			ret = this.elabora(conn, ctx, percorsoFilesConedi9, IMPORTCONEDILONLYONE_CONEDIL, true, null);
			log.info(ret);
			log.info("ELABORO " + percorsoFilesConedi10);
			ret = this.elabora(conn, ctx, percorsoFilesConedi10, IMPORTCONEDILONLYONE_CONEDIL, true, null);
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
			ps = con.prepareStatement(getProperty("sql.IMPORTCONEDILONLYONE_ANAGRAFE"));
			rs = ps.executeQuery();
			
			CommandLauncher launch = new CommandLauncher(belfiore);
			Command cmd = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class.getName(),true);
			
			BeanCommand bc = new BeanCommand();
			bc = cmd.getBeanCommand();
			
			

			while (rs.next()) {
				
				// INSERIMENTO SIT_C_PERSONA	
				EnvInsertDwh ecp = new EnvSitCPersonaOnlyOne (this.IMPORTCONEDILONLYONE_ANAGRAFE,"A");
				ecp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ecp, launch, bc,(String)ctx.get("connessione"),ctx, rs, abnormals);

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
		
		// INSERIMENTO CONCESSIONI CONEDIL
		try {
			ps = con.prepareStatement(getProperty("sql.IMPORTCONEDILONLYONE_CONEDIL"));
			rs = ps.executeQuery();
			
			CommandLauncher launchSitConcPersona = new CommandLauncher(belfiore);
			Command cmdSitConcPersona = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class.getName(),true);
			
			BeanCommand bcSitConcPersona = new BeanCommand();
			bcSitConcPersona = cmdSitConcPersona.getBeanCommand();

			CommandLauncher launchSitConcessioni = new CommandLauncher(belfiore);
			Command cmd1 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class.getName(),true);
			
			BeanCommand bcSitConcessioni = new BeanCommand();
			bcSitConcessioni = cmd1.getBeanCommand();
			
			CommandLauncher launchSitConcessioniCatasto = new CommandLauncher(belfiore);
			Command cmd2 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class.getName(),true);
			
			BeanCommand bcSitConcessioniCatasto = new BeanCommand();
			bcSitConcessioniCatasto = cmd2.getBeanCommand();

			CommandLauncher launchSitConcIndirizzi = new CommandLauncher(belfiore);
			Command cmd3 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class.getName(),true);
			
			BeanCommand bcSitConcIndirizzi = new BeanCommand();
			bcSitConcIndirizzi = cmd3.getBeanCommand();

			while (rs.next()) {

				// INSERIMENTO SIT_C_CONCESSIONI	
				EnvInsertDwh ec = new EnvSitCConcessioniOnlyOne  (this.IMPORTCONEDILONLYONE_CONEDIL,"A");
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launchSitConcessioni, bcSitConcessioni,(String)ctx.get("connessione"),ctx, rs, abnormals);

				// INSERIMENTO SIT_C_CONC_PERSONA	
				EnvInsertDwh ecp = new EnvSitCConcPersonaOnlyOne(this.IMPORTCONEDILONLYONE_CONEDIL,"A");
				ecp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ecp,launchSitConcPersona, bcSitConcPersona,(String)ctx.get("connessione"),ctx, rs, abnormals);


				// INSERIMENTO SIT_C_CONC_CONCESSIONI_CATASTO	
				EnvInsertDwh ecc = new EnvSitCConcessioniCatastoOnlyOne (this.IMPORTCONEDILONLYONE_CONEDIL,"A");
				ecc.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ecc, launchSitConcessioniCatasto, bcSitConcessioniCatasto,(String)ctx.get("connessione"),ctx, rs, abnormals);

				// INSERIMENTO SIT_C_CONC_INDIRIZZI	
				EnvInsertDwh eci = new EnvSitCConcIndirizziOnlyOne (this.IMPORTCONEDILONLYONE_CONEDIL,"A");
				eci.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,eci, launchSitConcIndirizzi, bcSitConcIndirizzi,(String)ctx.get("connessione"),ctx, rs, abnormals);
				
				
			}
			
			
			
			
			// SE ONLYONE HA INTRODOTTO CONCESSIONI CHE ERANO GIA' IN INFORM 
			// RIPORTO LE IMMAGINE CHE ERANO DI INFORM SU ONLYONE FACENDO UN UPDATE DELLA
			// TABELLA DELLE IMMAGINI
			// E CANCELLO QUELLE DI INFORM E TENGO QUELLE DI ONLYONE
			ps = con.prepareStatement(getProperty("sql.UPDATE_IMG_TO_OO"));
			ps.executeUpdate();
			
			ps = con.prepareStatement(getProperty("sql.DELETE_INFORM_IN_OO"));
			ps.executeUpdate();
			
			
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
			st.execute(createTableAnaSql);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
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
			st.execute(createTableConedilSql);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
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
			st.execute(this.IMPORTCONEDILONLYONE_ANAGRAFE_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
			st.execute(this.IMPORTCONEDILONLYONE_CONEDIL_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}

		
		
	}


	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		// il metodo vale per la lettura delle date di anagrafe a oncessioni
		int nom = file.lastIndexOf("ExpNom");
		if (nom!=-1) {
			String data = file.substring(nom+6,20);
			this.dataExport = (Timestamp)TypeFactory.getType(data, "java.sql.Timestamp");
		} else {
			nom = file.lastIndexOf("ExpPra");
			if (nom!=-1) {
				String data = file.substring(nom+6,20);
				this.dataExport = (Timestamp)TypeFactory.getType(data, "java.sql.Timestamp");
			}
		}
	}
	
	

	

	
	
	
}
