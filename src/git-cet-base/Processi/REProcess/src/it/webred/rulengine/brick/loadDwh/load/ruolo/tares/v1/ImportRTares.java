package it.webred.rulengine.brick.loadDwh.load.ruolo.tares.v1;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.ruolo.tares.RTaresEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class ImportRTares<T extends  Env<?>> extends ConcreteImport<T> {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportRTares.class.getName());
	
	public ImportRTares() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.ruolo.tares.v1.Env<RTaresEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.ruolo.tares.v1.Env<RTaresEnv>((RTaresEnv)ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals = concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();
		try {
			CommandLauncher launch = new CommandLauncher(belfiore);
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
			
			//SIT_RUOLO_TARES
			Command cmdSitRuoloTares = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTares.class.getName(), true);
			
			String sql = concreteImportEnv.getSQL_RUOLO();
			log.debug("SQL_RUOLO["+sql+"]");
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_TARSU
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTares();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTares, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
		
		
			//SIT_RUOLO_TARSU_IMM
			Command cmdSitRuoloTaresImm = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTaresImm.class.getName(), true);
			
	 		 sql = concreteImportEnv.getSQL_RE_IMMOBILE();
	 		log.debug("SQL_RE_IMMOBILE["+sql+"]");
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_Tares_IMM
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTaresImm();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTaresImm, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
		
			//SIT_RUOLO_Tares_RATA
			Command cmdSitRuoloTaresRata = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTaresRata.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_RATA();
			log.debug("SQL_RE_RATA["+sql+"]");
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_Tares_RATA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTaresRata();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTaresRata, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
		
			//SIT_RUOLO_Tares_ST
			Command cmdSitRuoloTaresSt = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTaresSt.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_ST();
			log.debug("SQL_RE_ST["+sql+"]");
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTaresSt();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTaresSt, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
		
			//SIT_RUOLO_Tares_ST_SG
			Command cmdSitRuoloTaresStSg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTaresStSg.class.getName(), true);
			
		    sql = concreteImportEnv.getSQL_RE_ST_SG();
		    log.debug("SQL_RE_ST_SG["+sql+"]");
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTaresStSg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTaresStSg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
		
			
			//tabella senza chiave; update RE_FLAG_ELABORATO
			EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTaresImm();
			sql = "UPDATE "+ec.getNomeTabellaOrigine()+" SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione RUOLO TARES'", e);
		} 	finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.warn("Qualche problema nella chiusura dei cursori",e);
			}
		}
		
		return true;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
