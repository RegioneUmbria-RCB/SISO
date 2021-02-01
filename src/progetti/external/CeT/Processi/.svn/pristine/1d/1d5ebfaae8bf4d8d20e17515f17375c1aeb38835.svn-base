package it.webred.rulengine.brick.loadDwh.load.concessioni.v1;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v2.Env;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class ImportConcessioni<T extends  Env<?>>   extends  ConcreteImport<T> {





	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportConcessioni.class.getName());
	

	public boolean normalizza(String belfiore) throws RulEngineException {
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals =  concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();
		
		// INSERIMENTO CONCESSIONI 
		try {
			
			
			CommandLauncher launchSitConcPersona = new CommandLauncher(belfiore);
			Command cmdSitConcPersona = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcPersona.class.getName(),true);
			

			CommandLauncher launchSitConcessioni = new CommandLauncher(belfiore);
			Command cmd1 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioni.class.getName(),true);
			

			
			CommandLauncher launchSitCConcDatiTec = new CommandLauncher(belfiore);
			Command cmd5 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcDatiTec.class.getName(),true);
			


			

			CommandLauncher launchSitConcessioniCatasto = new CommandLauncher(belfiore);
			Command cmd2 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcessioniCatasto.class.getName(),true);
			

			CommandLauncher launchSitConcIndirizzi = new CommandLauncher(belfiore);
			Command cmd3 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCConcIndirizzi.class.getName(),true);
			

			CommandLauncher launchSitCPersona = new CommandLauncher(belfiore);
			Command cmd4 = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitCPersona.class.getName(),true);
			

				ps = con.prepareStatement(concreteImportEnv.getSQL_RE_CONCESSIONI_A());
				rs = ps.executeQuery();
				
				Long idFonte = ctx.getIdFonte();
				ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
				AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
				
				
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_CONCESSIONI	
					EnvInsertDwh ec = concreteImportEnv.getEnvSitCConcessioni();
					ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ec, launchSitConcessioni, cmd1,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
	
				}

				ps = con.prepareStatement(concreteImportEnv.getSQL_RE_CONCESSIONI_B());
				rs = ps.executeQuery();

				// per ogni record di B anche l'inserimento in A viene riproposto, ma è una cosa che posso anche accettare 
				// per una mole di dati non eccessiva !!!
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_PERSONA	
					EnvInsertDwh ep = concreteImportEnv.getEnvSitCPersona();
					ep.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ep, launchSitCPersona, cmd4,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
						
					// INSERIMENTO SIT_C_CONC_PERSONA	
					EnvInsertDwh ecp = concreteImportEnv.getEnvSitCConcPersona();
					ecp.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecp, launchSitConcPersona , cmdSitConcPersona,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
					
				}

				ps = con.prepareStatement(concreteImportEnv.getSQL_RE_CONCESSIONI_C());
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_CONC_DATI_TEC	
					EnvInsertDwh ecdt = concreteImportEnv.getEnvSitCConcDatiTec();
					ecdt.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecdt, launchSitCConcDatiTec, cmd5,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
					
				}
			
	
				ps = con.prepareStatement(concreteImportEnv.getSQL_RE_CONCESSIONI_D());
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_CONCESSIONI_CATASTO	
					EnvInsertDwh ecc = concreteImportEnv.getEnvSitCConcessioniCatasto();
					ecc.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ecc, launchSitConcessioniCatasto, cmd2,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
					
				}
			
				ps = con.prepareStatement(concreteImportEnv.getSQL_RE_CONCESSIONI_E());
				rs = ps.executeQuery();
	
				while (rs.next()) {
					
					// INSERIMENTO SIT_C_CONC_INDIRIZZI	
					EnvInsertDwh ei = concreteImportEnv.getEnvSitCConcIndirizzi();
					ei.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
					InsertDwh.launchInserimento(con,ei, launchSitConcIndirizzi, cmd3,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
					
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
		
		return true;
	}


	@Override
	public ConcreteImportEnv<ConcessioniStandardFilesEnv<TestataStandardFileBean>> getEnvSpec(EnvImport ei) {
		return new Env<ConcessioniStandardFilesEnv<TestataStandardFileBean>>((ConcessioniStandardFilesEnv) ei);
	}


	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}






}
