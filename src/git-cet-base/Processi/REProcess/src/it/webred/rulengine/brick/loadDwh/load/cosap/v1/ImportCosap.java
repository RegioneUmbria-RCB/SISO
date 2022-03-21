package it.webred.rulengine.brick.loadDwh.load.cosap.v1;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.cosap.CosapTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.cosap.v1.Env;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

public class ImportCosap <T extends Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<CosapTipoRecordEnv<TestataStandardFileBean>>((CosapTipoRecordEnv) ei);
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
			
			//SIT_T_COSAP_CONTRIB
			Command cmdSitTCosapContrib = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTCosapContrib.class.getName(), true);
			
			String sql = concreteImportEnv.getSQL_RE_COSAP_A();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
			
			while (rs.next()) {
				// INSERIMENTO SIT_T_COSAP_CONTRIB
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTCosapContrib();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTCosapContrib, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//SIT_T_COSAP_TASSA
			Command cmdSitTCosapTassa = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTCosapTassa.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_COSAP_B();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {		
				// INSERIMENTO SIT_T_COSAP_TASSA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTCosapTassa();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTCosapTassa, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//tabella senza chiave; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_COSAP_B SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione COSAP", e);
		} finally {
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
