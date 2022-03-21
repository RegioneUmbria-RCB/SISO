package it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.v1;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.RTarsuEnv;
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

public class ImportRTarsu<T extends  Env<?>> extends ConcreteImport<T> {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportRTarsu.class.getName());
	
	public ImportRTarsu() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.v1.Env<RTarsuEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.v1.Env<RTarsuEnv>((RTarsuEnv)ei);
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
			
		
			//SIT_RUOLO_TARSU
			Command cmdSitRuoloTarsu = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTarsu.class.getName(), true);
			
			String sql = concreteImportEnv.getSQL_RUOLO();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_TARSU
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsu();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTarsu, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
		
			//SIT_RUOLO_TARSU_IMM
			Command cmdSitRuoloTarsuImm = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTarsuImm.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_IMMOBILE();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_TARSU_IMM
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsuImm();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTarsuImm, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			
			//SIT_RUOLO_TARSU_RATA
			Command cmdSitRuoloTarsuRata = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTarsuRata.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_RATA();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// INSERIMENTO SIT_RUOLO_TARSU_RATA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsuRata();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTarsuRata, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//SIT_RUOLO_TARSU_ST
			Command cmdSitRuoloTarsuSt = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTarsuSt.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_ST();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsuSt();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTarsuSt, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//SIT_RUOLO_TARSU_ST_SG
			Command cmdSitRuoloTarsuStSg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitRuoloTarsuStSg.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_ST_SG();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsuStSg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitRuoloTarsuStSg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
		
			/*Inserire altri gestori*/
			
			//tabella senza chiave; update RE_FLAG_ELABORATO
			
			EnvInsertDwh ec = concreteImportEnv.getEnvSitRuoloTarsuImm();
			sql = "UPDATE "+ec.getNomeTabellaOrigine()+" SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione RUOLO TARSU'", e);
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
