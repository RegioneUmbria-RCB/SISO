package it.webred.rulengine.brick.loadDwh.concessioni.standard;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.concessioni.F704.EnvSitCConcDatiTec;
import it.webred.rulengine.brick.superc.InsertDwh.*;
import it.webred.rulengine.brick.superc.importDati.ImportFiles;
import it.webred.rulengine.brick.superc.importDati.ImportStandardFiles;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

@Deprecated
public class    ImportConcessioni extends ImportStandardFiles implements Rule   {



	private String RE_CONCESSIONI_A = getProperty("tableA.name");
	private String RE_CONCESSIONI_B = getProperty("tableB.name");
	private String RE_CONCESSIONI_C = getProperty("tableC.name");
	private String RE_CONCESSIONI_D = getProperty("tableD.name");
	private String RE_CONCESSIONI_E = getProperty("tableE.name");
	
	private String RE_CONCESSIONI_A_IDX = getProperty("tableA.idx");
	private String RE_CONCESSIONI_B_IDX = getProperty("tableB.idx");
	private String RE_CONCESSIONI_C_IDX = getProperty("tableC.idx");
	private String RE_CONCESSIONI_D_IDX = getProperty("tableD.idx");
	private String RE_CONCESSIONI_E_IDX = getProperty("tableE.idx");
	
	private String createTableA = getProperty("tableA.create_table");
	private String createTableB = getProperty("tableB.create_table");
	private String createTableC = getProperty("tableC.create_table");
	private String createTableD = getProperty("tableD.create_table");
	private String createTableE = getProperty("tableE.create_table");
	
	private String percorsoFiles = getProperty("dir.files");
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportConcessioni.class.getName());
	
	
	public ImportConcessioni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	public CommandAck execute(Context ctx, Connection conn)
	throws CommandException {
		

		String ret = null;

		try {
			log.info("ELABORO " + percorsoFiles);
			ret = this.elabora(conn, ctx, percorsoFiles , RE_CONCESSIONI_A , true,"A");
			log.info(ret);
			ret = this.elabora(conn, ctx, percorsoFiles , RE_CONCESSIONI_B , true,"B");
			log.info(ret);
			ret = this.elabora(conn, ctx, percorsoFiles , RE_CONCESSIONI_D , true,"D");
			log.info(ret);
			ret = this.elabora(conn, ctx, percorsoFiles , RE_CONCESSIONI_E , true,"E");
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
		
		// INSERIMENTO CONCESSIONI 
		try {
			
			
			CommandLauncher launchSitConcPersona = new CommandLauncher(belfiore);
			Command cmdSitConcPersona = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class.getName(),true);
			
			BeanCommand bcSitConcPersona = new BeanCommand();
			bcSitConcPersona = cmdSitConcPersona.getBeanCommand();

			CommandLauncher launchSitConcessioni = new CommandLauncher(belfiore);
			Command cmd1 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class.getName(),true);
			
			BeanCommand bcSitConcessioni = new BeanCommand();
			bcSitConcessioni = cmd1.getBeanCommand();
			
			CommandLauncher launchSitCConcDatiTec = new CommandLauncher(belfiore);
			Command cmd5 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcDatiTec.class.getName(),true);
			
			BeanCommand bcSitCConcDatiTec = new BeanCommand();
			bcSitCConcDatiTec = cmd5.getBeanCommand();

			
			
			CommandLauncher launchSitConcessioniCatasto = new CommandLauncher(belfiore);
			Command cmd2 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class.getName(),true);
			
			BeanCommand bcSitConcessioniCatasto = new BeanCommand();
			bcSitConcessioniCatasto = cmd2.getBeanCommand();

			CommandLauncher launchSitConcIndirizzi = new CommandLauncher(belfiore);
			Command cmd3 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class.getName(),true);
			
			BeanCommand bcSitConcIndirizzi = new BeanCommand();
			bcSitConcIndirizzi = cmd3.getBeanCommand();

			CommandLauncher launchSitCPersona = new CommandLauncher(belfiore);
			Command cmd4 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class.getName(),true);
			
			BeanCommand bcSitCPersona = new BeanCommand();
			bcSitCPersona = cmd4.getBeanCommand();

			if (RE_CONCESSIONI_A.equals(currentTable)) {
				ps = con.prepareStatement(getProperty("sql.RE_CONCESSIONI_A"));
				rs = ps.executeQuery();
				
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_CONCESSIONI	
					EnvInsertDwh ec = new EnvSitCConcessioni(this.RE_CONCESSIONI_A,"CHIAVE");
					ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ec, launchSitConcessioni, bcSitConcessioni,(String)ctx.get("connessione"),ctx, rs, abnormals);
	
				}
			}

			if (RE_CONCESSIONI_B.equals(currentTable)) {
				ps = con.prepareStatement(getProperty("sql.RE_CONCESSIONI_B"));
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_PERSONA	
					EnvInsertDwh ep = new EnvSitCPersona(this.RE_CONCESSIONI_B,"CHIAVE");
					ep.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ep, launchSitCPersona, bcSitCPersona,(String)ctx.get("connessione"),ctx, rs, abnormals);
	
					// INSERIMENTO SIT_C_CONC_PERSONA	
					EnvInsertDwh ecp = new EnvSitCConcPersona(this.RE_CONCESSIONI_B,"CHIAVE");
					ecp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecp, launchSitConcPersona , bcSitConcPersona,(String)ctx.get("connessione"),ctx, rs, abnormals);
					
					
				}
			}

			if (RE_CONCESSIONI_C.equals(currentTable)) {
				ps = con.prepareStatement(getProperty("sql.RE_CONCESSIONI_C"));
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_CONC_DATI_TEC	
					EnvInsertDwh ecdt = new EnvSitCConcDatiTec(this.RE_CONCESSIONI_B,"CHIAVE");
					ecdt.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecdt, launchSitCConcDatiTec, bcSitCConcDatiTec,(String)ctx.get("connessione"),ctx, rs, abnormals);
	
					
					
				}
			}
			
			if (RE_CONCESSIONI_D.equals(currentTable)) {
	
				ps = con.prepareStatement(getProperty("sql.RE_CONCESSIONI_D"));
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_CONCESSIONI_CATASTO	
					EnvInsertDwh ecc = new EnvSitCConcessioniCatasto(this.RE_CONCESSIONI_D,"CHIAVE");
					ecc.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecc, launchSitConcessioniCatasto, bcSitConcessioniCatasto,(String)ctx.get("connessione"),ctx, rs, abnormals);
					
				}
			}
			
			if (RE_CONCESSIONI_E.equals(currentTable)) {
				ps = con.prepareStatement(getProperty("sql.RE_CONCESSIONI_E"));
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_PERSONA	
					EnvInsertDwh ei = new EnvSitCConcIndirizzi(this.RE_CONCESSIONI_E,"CHIAVE");
					ei.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ei, launchSitConcIndirizzi, bcSitConcIndirizzi,(String)ctx.get("connessione"),ctx, rs, abnormals);
					
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
	protected void preProcesing(Connection con ) {
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(createTableA);
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
			st.execute(createTableB);
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
			st.execute(createTableC);
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
			st.execute(createTableD);
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
			st.execute(createTableE);
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
			st.execute(this.RE_CONCESSIONI_A_IDX);
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
			st.execute(this.RE_CONCESSIONI_B_IDX);
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
			st.execute(this.RE_CONCESSIONI_C_IDX);
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
			st.execute(this.RE_CONCESSIONI_D_IDX);
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
			st.execute(this.RE_CONCESSIONI_E_IDX);
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



	
	

	

	
	
	
}
