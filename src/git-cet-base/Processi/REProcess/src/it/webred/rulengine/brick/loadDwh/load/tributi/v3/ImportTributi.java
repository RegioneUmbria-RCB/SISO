package it.webred.rulengine.brick.loadDwh.load.tributi.v3;

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
import it.webred.rulengine.brick.loadDwh.load.tributi.TributiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.Env;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni.GestoreCorrelazioneVarTributi;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ImportTributi<T extends Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<TributiTipoRecordEnv>((TributiTipoRecordEnv) ei);
	}

	
	
	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {

		log.info("Avvio normalizzazione");
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals = concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();
		
		boolean inReplace = concreteImportEnv.getEnvImport().getEnteSorgente().isInReplace();
		try {
			CommandLauncher launch = new CommandLauncher(belfiore);
			
			//SIT_T_TAR_SOGG
			Command cmdSitTTarSogg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTTarSogg.class.getName(), true);
			
			String sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBTARSUSOGG();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_TAR_SOGG
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTTarSogg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTTarSogg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			//SIT_T_TAR_OGGETTO
			Command cmdSitTTarOggetto = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTTarOggetto.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBTARSUDICH();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_TAR_OGGETTO
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTTarOggetto();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTTarOggetto, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBTARSUDICH SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_TAR_ULT_SOGG
			Command cmdSitTTarUltSogg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTTarUltSogg.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_TAR_ULT_SOGG
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTTarUltSogg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTTarUltSogg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBTARSUSOGGULT SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_TAR_VIA
			Command cmdSitTTarVia = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTTarVia.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBTARSUVIA();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_TAR_VIA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTTarVia();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTTarVia, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//SIT_T_TAR_RIDUZIONI
			Command cmdSitTTarRiduzioni = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTTarRiduzioni.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBTARRIDUZ();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_TAR_RIDUZIONI
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTTarRiduzioni();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTTarRiduzioni, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBTARRIDUZ SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_ICI_SOGG
			Command cmdSitTIciSogg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciSogg.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBICISOGG();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_SOGG
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciSogg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciSogg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//SIT_T_ICI_OGGETTO
			Command cmdSitTIciOggetto = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciOggetto.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBICIDICH();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_OGGETTO
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciOggetto();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciOggetto, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBICIDICH SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_ICI_ULT_SOGG
			Command cmdSitTIciUltSogg = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciUltSogg.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBICISOGGULT();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_ULT_SOGG
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciUltSogg();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciUltSogg, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBICISOGGULT SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_ICI_VIA
			Command cmdSitTIciVia = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciVia.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBICIVIA();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_VIA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciVia();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciVia, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//SIT_T_ICI_RIDUZIONI
			Command cmdSitTIciRiduzioni = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciRiduzioni.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBICIRIDUZ();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_RIDUZIONI
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciRiduzioni();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciRiduzioni, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

			//tabella senza chiave in accodamento; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_TRIBUTI_TRTRIBICIRIDUZ SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			//SIT_T_ICI_VERSAMENTI
			Command cmdSitTIciVersamenti = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitTIciVersamenti.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_TRIBUTI_TRTRIBVICI();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {				
				// INSERIMENTO SIT_T_ICI_VERSAMENTI
				EnvInsertDwh ec = concreteImportEnv.getEnvSitTIciVersamenti();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitTIciVersamenti, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}

		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione TRIBUTI", e);
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
		return  new GestoreCorrelazioneVarTributi();
	}
	
}

