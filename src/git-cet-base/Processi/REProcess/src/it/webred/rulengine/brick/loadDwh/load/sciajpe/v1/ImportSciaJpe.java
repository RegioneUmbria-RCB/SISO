package it.webred.rulengine.brick.loadDwh.load.sciajpe.v1;

import it.webred.ct.config.model.AmKeyValueExt;




import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.SciaJpeTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;

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

public class ImportSciaJpe <T extends  Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<SciaJpeTipoRecordEnv<Testata>>((SciaJpeTipoRecordEnv) ei);
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
			String clazz = it.webred.rulengine.brick.loadDwh.base.LoadSitSciaJpe.class.getName();
			Command cmdSitSciaJpe = CommandFactory.getCommand(clazz ,true);

			String sql = concreteImportEnv.getSQL_RE_SCIA_JPE_UNO();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());

			while (rs.next()) {
				
				// INSERIMENTO SIT_SCIA_JPE
				EnvInsertDwh ec = concreteImportEnv.getEnvSitSciaJpe();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launch, cmdSitSciaJpe,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
			}
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione PRATICHE EDILI SCIA JPE ", e);
		} 	finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.warn("Qualche problema nella chiusura dei cursori per import  SCIA JPE ",e);
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
