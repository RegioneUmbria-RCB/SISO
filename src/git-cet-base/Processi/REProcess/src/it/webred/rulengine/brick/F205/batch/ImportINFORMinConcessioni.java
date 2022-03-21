package it.webred.rulengine.brick.F205.batch;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcIndirizziZoneInform;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcPersonaZoneInform;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcessioniCatastoZoneInform;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCConcessioniZoneInform;
import it.webred.rulengine.brick.loadDwh.concessioni.F205.EnvSitCPersonaZoneInform;
import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.superc.InsertDwh.InsertDwh;
import it.webred.rulengine.brick.superc.importDati.CurrentItem;
import it.webred.rulengine.brick.superc.importDati.ImportDatiDb;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * La classe si occupa di trasferire i dati delle concessioni inform
 * che al comune di milano sono in uno schema oracle CONCESSIONI
 * all'interno della struttura unica delle concessioni
 * @author MarcoPort
 *
 */
public class ImportINFORMinConcessioni  extends ImportDatiDb  implements Rule {

	




	public ImportINFORMinConcessioni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportINFORMinConcessioni.class.getName());
	private Class clazzCommand = null; 
	CommandLauncher launchSit;// = new CommandLauncher();
	private String belfiore;
	BeanCommand bcSit = new BeanCommand();
	private String SIT_C_CONCESSIONI = it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class.getName();
	private String SIT_C_CONC_PERS = it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class.getName();
	private String SIT_C_PERSONA = it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class.getName();
	private String SIT_C_CONC_INDIRIZZI = it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class.getName();
	private String SIT_C_CONCESSIONI_CATASTO = it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class.getName();
	
	

	@Override
	protected CommandAck execute(Context ctx, Connection conn)
			throws CommandException {
		


		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.belfiore = ctx.getBelfiore();
			
			clazzCommand =  it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class;
			Command cmdSit = CommandFactory.getCommand(clazzCommand.getName(),true);
			bcSit = cmdSit.getBeanCommand();

			String sql  =  "select * from concessioni.mi_concessioni C , concessioni.mi_concessioni_INTERVENTO CI "
						+" WHERE C.PK_CONC = CI.FK_CONC";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			this.elabora(conn, ctx, rs,new CurrentItem( this.SIT_C_CONCESSIONI));
		
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
				if (rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			clazzCommand =  it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class;
			Command cmdSit = CommandFactory.getCommand(clazzCommand.getName(),true);
			bcSit = cmdSit.getBeanCommand();

			String sql  =  "SELECT * FROM CONCESSIONI.MI_CONCESSIONI_PERSONA P, "
							+" CONCESSIONI.MI_CONCESSIONI_PERS_RES PR "
							+" WHERE P.PK_PERSONA = PR.FK_PERSONA (+)"
							+" ORDER BY PK_PERSONA, PR.DATA_RES";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			this.elabora(conn, ctx, rs,new CurrentItem( this.SIT_C_PERSONA));
		
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
				if (rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			clazzCommand =  it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class;
			Command cmdSit = CommandFactory.getCommand(clazzCommand.getName(),true);
			bcSit = cmdSit.getBeanCommand();

			String sql  =  "SELECT PK_CONC_PER, FK_CONC, Fk_persona,  TIPO_SOGGETTO"
							+ " FROM CONCESSIONI.MI_CONCESSIONI_CONC_PER CP";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			this.elabora(conn, ctx, rs,new CurrentItem( this.SIT_C_CONC_PERS));
		
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
				if (rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			clazzCommand =  it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class;
			Command cmdSit = CommandFactory.getCommand(clazzCommand.getName(),true);
			bcSit = cmdSit.getBeanCommand();

			String sql  =  "SELECT PK_OGGETTO , FK_CONC, INDIRIZZO, CIVICO, SCALA, PIANO, CODICE_VIA"
							+  " FROM CONCESSIONI.MI_CONCESSIONI_OGGETTO CO" 
							+ " where INDIRIZZO IS NOT NULL";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			this.elabora(conn, ctx, rs,new CurrentItem( this.SIT_C_CONC_INDIRIZZI));
		
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
				if (rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			clazzCommand =  it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class;
			Command cmdSit = CommandFactory.getCommand(clazzCommand.getName(),true);
			bcSit = cmdSit.getBeanCommand();

			String sql  =  "SELECT pk_oggetto, FK_CONC, foglio, particella, subalterno, decode(DESTINAZIONE_USO,'SI','DESTINAZIONE USO', NULL) DESTINAZIONE_USO, "
						+"	decode(TUTELA,'SI','TUTELA', NULL) TUTELA "
						+"	FROM CONCESSIONI.MI_CONCESSIONI_OGGETTO CO";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			this.elabora(conn, ctx, rs,new CurrentItem( this.SIT_C_CONCESSIONI_CATASTO));
		
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
				if (rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		

		
		return new ApplicationAck("Elaborazione terminata correttamente");
	
	}




	protected void preProcesing() throws RulEngineException {
		PreparedStatement ps = null;
		String sql = null;
		try {
			String currentItem = getCurrentItem().getName();
			
			if (currentItem.equals(this.SIT_C_CONCESSIONI)) {
				sql  =  "DELETE FROM SIT_C_CONCESSIONI WHERE PROVENIENZA='I'";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
			}
			if (currentItem.equals(this.SIT_C_PERSONA)) {
				sql  =  "DELETE FROM SIT_C_PERSONA WHERE PROVENIENZA='I'";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
			}
			if (currentItem.equals(this.SIT_C_CONC_PERS)) {
				sql  =  "DELETE FROM SIT_C_CONC_PERSONA WHERE PROVENIENZA='I'";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();	
			}
			if (currentItem.equals(this.SIT_C_CONC_INDIRIZZI)) {
				sql  =  "DELETE FROM SIT_C_CONC_INDIRIZZI WHERE PROVENIENZA='I'";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();	
			}
			if (currentItem.equals(this.SIT_C_CONCESSIONI_CATASTO)) {
				sql  =  "DELETE FROM SIT_C_CONCESSIONI_CATASTO WHERE PROVENIENZA='I'";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();	
			}
			
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			throw new RulEngineException("Errore in delete delle tabelle Dwh",e);
		} finally {
			try {
				if (ps!=null)
						ps.close();
			} catch (SQLException e) {
				
			}
		}
		
	}



	@Override
	protected void preProcesingRow(ResultSet rs) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void processingRow(ResultSet rs) throws RulEngineException {
	try {
		String currentItem = getCurrentItem().getName();
		launchSit = new CommandLauncher(this.belfiore);
		
		if (currentItem.equals(this.SIT_C_CONCESSIONI)) {
			EnvInsertDwh ecp = new EnvSitCConcessioniZoneInform("CONCESSIONI.MI_CONCESSIONI","PK_CONC");
			ecp.setParametriPerGetRighe(this.dataExport);
			InsertDwh.launchInserimento(con, ecp,launchSit, bcSit,(String)ctx.get("connessione"),ctx, rs, abnormals);
		}
		if (currentItem.equals(this.SIT_C_PERSONA)) {
			EnvInsertDwh ecp = new EnvSitCPersonaZoneInform("CONCESSIONI.MI_CONCESSIONI_PERSONA","PK_PERSONA");
			ecp.setParametriPerGetRighe(this.dataExport);
			InsertDwh.launchInserimento(con, ecp,launchSit, bcSit,(String)ctx.get("connessione"),ctx, rs, abnormals);
		}
		if (currentItem.equals(this.SIT_C_CONC_PERS)) {
			EnvInsertDwh ecp = new EnvSitCConcPersonaZoneInform("CONCESSIONI.MI_CONCESSIONI_CONC_PER","PK_CONC_PER");
			ecp.setParametriPerGetRighe(this.dataExport);
			InsertDwh.launchInserimento(con, ecp,launchSit, bcSit,(String)ctx.get("connessione"),ctx, rs, abnormals);
		}
		if (currentItem.equals(this.SIT_C_CONC_INDIRIZZI)) {
			EnvInsertDwh ecp = new EnvSitCConcIndirizziZoneInform("CONCESSIONI.MI_CONCESSIONI_OGGETTO","PK_OGGETTO");
			ecp.setParametriPerGetRighe(this.dataExport);
			InsertDwh.launchInserimento(con, ecp,launchSit, bcSit,(String)ctx.get("connessione"),ctx, rs, abnormals);
		}
		if (currentItem.equals(this.SIT_C_CONCESSIONI_CATASTO)) {
			EnvInsertDwh ecp = new EnvSitCConcessioniCatastoZoneInform("CONCESSIONI.MI_CONCESSIONI_OGGETTO","PK_OGGETTO");
			ecp.setParametriPerGetRighe(this.dataExport);
			InsertDwh.launchInserimento(con, ecp,launchSit, bcSit,(String)ctx.get("connessione"),ctx, rs, abnormals);
		}
		

	} catch (CommandException e) {
		throw new RulEngineException("Errore durante la chiamata al comando di inserimento dati", e);
	} catch (Exception e) {
		throw new RulEngineException("Problemi durante la normalizzazione dei dati", e);

	}
		
	}






	
	
	
}
