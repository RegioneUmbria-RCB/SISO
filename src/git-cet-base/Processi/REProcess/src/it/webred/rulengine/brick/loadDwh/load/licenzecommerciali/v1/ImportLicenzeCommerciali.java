package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.InsertDwh;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.LicenzeCommercialiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;

public class ImportLicenzeCommerciali<T extends Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<LicenzeCommercialiTipoRecordEnv<TestataStandardFileBean>>((LicenzeCommercialiTipoRecordEnv) ei);
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
			
			//SIT_LICENZE_COMMERCIO_VIE
			Command cmdSitLicenzeCommercioVie = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitLicenzeCommercioVie.class.getName(), true);
			
			String sql = concreteImportEnv.getSQL_RE_LICENZECOMMERCIALI_VIE();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
			
			while (rs.next()) {				
				// INSERIMENTO SIT_LICENZE_COMMERCIO_VIE
				EnvInsertDwh ec = concreteImportEnv.getEnvSitLicenzeCommercioVie();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitLicenzeCommercioVie, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//SIT_LICENZE_COMMERCIO
			Command cmdSitLicenzeCommercio = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitLicenzeCommercio.class.getName(), true);
			//BeanCommand bcSitLicenzeCommercio = new BeanCommand();
			//bcSitLicenzeCommercio = cmdSitLicenzeCommercio.getBeanCommand();
			
			sql = concreteImportEnv.getSQL_RE_LICENZECOMMERCIALI_A();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_LICENZE_COMMERCIO
				EnvInsertDwh ec = concreteImportEnv.getEnvSitLicenzeCommercio();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitLicenzeCommercio, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//SIT_LICENZE_COMMERCIO_ANAG
			Command cmdSitLicenzeCommercioAnag = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitLicenzeCommercioAnag.class.getName(), true);
			//BeanCommand bcSitLicenzeCommercioAnag = new BeanCommand();
			//bcSitLicenzeCommercioAnag = cmdSitLicenzeCommercioAnag.getBeanCommand();
			
			sql = concreteImportEnv.getSQL_RE_LICENZECOMMERCIALI_B();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_LICENZE_COMMERCIO_ANAG
				EnvInsertDwh ec = concreteImportEnv.getEnvSitLicenzeCommercioAnag();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitLicenzeCommercioAnag, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione LICENZE COMMERCIALI", e);
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
