package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.v1;

import it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.AcquaTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import org.apache.log4j.Logger;

public class ImportAcqua<T extends  Env<?>> extends ConcreteImport<T> {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportAcqua.class.getName());
	
	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<AcquaTipoRecordEnv>((AcquaTipoRecordEnv) ei);
	}
	
	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {

		/*ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals =  concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();

		try {
			CommandLauncher launchUtente = new CommandLauncher(belfiore);
			Command cmdSitAcquaUtente = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitAcquaUtente.class.getName(),true);

			CommandLauncher launchUtenze = new CommandLauncher(belfiore);
			Command cmdSitAcquaUtenze = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitAcquaUtenze.class.getName(),true);
			
			CommandLauncher launchCatasto = new CommandLauncher(belfiore);
			Command cmdSitAcquaCatasto = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitAcquaCatasto.class.getName(),true);

			
			String sql = concreteImportEnv.getSQL_RE_ACQUA_A();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());

			while (rs.next()) {
				
				// INSERIMENTO SIT_ACQUA_UTENTE	
				EnvInsertDwh ec = concreteImportEnv.getEnvSitAcquaUtente();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launchUtente, cmdSitAcquaUtente,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
			}
			
			sql = concreteImportEnv.getSQL_RE_ACQUA_B();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				
				// INSERIMENTO SIT_ACQUA_UTENZE	
				EnvInsertDwh ec = concreteImportEnv.getEnvSitAcquaUtenze();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launchUtenze, cmdSitAcquaUtenze,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
			}
			
			sql = concreteImportEnv.getSQL_RE_ACQUA_C();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				// INSERIMENTO SIT_ACQUA_CATASTO	
				EnvInsertDwh ec = concreteImportEnv.getEnvSitAcquaCatasto();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con,ec, launchCatasto, cmdSitAcquaCatasto,concreteImportEnv.getEnvImport().getConnectionName(),ctx, rs, abnormals, amkvext);
			}
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione ACQUA", e);
		}
		*/

		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
