package it.webred.rulengine.brick.loadDwh.load.schedemartelli.v1;

import it.webred.ct.config.model.AmKeyValueExt;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.base.LoadSitSchedeMartelli;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.schedemartelli.SchedeMartelliTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.schedemartelli.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
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

public class ImportSchedeMartelli <T extends  Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<SchedeMartelliTipoRecordEnv<Testata>>((SchedeMartelliTipoRecordEnv) ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {

		ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals =  concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();

		try {
			CommandLauncher launch = new CommandLauncher(belfiore);
			String clazz = LoadSitSchedeMartelli.class.getName();
			Command cmdSitSchedeMartelli = CommandFactory.getCommand(clazz ,true);

			String sql = concreteImportEnv.getSQL_RE_SCHEDE_MARTELLI_UNO();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());

			while (rs.next()) {
				
				// INSERIMENTO SIT_SCHEDE_MARTELLI
				EnvInsertDwh ec = concreteImportEnv.getEnvSitSchedeMartelli();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launch, cmdSitSchedeMartelli,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
			}
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione SCHEDE MARTELLI ", e);
		} 	finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.warn("Qualche problema nella chiusura dei cursori per import SCHEDE MARTELLI ", e);
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
