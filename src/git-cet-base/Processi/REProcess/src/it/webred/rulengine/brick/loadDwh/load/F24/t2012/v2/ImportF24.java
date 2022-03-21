package it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportF24<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportF24() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.Env<F24Env> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.Env<F24Env>((F24Env)ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		/*	
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<RAbNormal> abnormals = concreteImportEnv.getEnvImport().getAbnormals();
		Connection con = concreteImportEnv.getEnvImport().getConn();
		Context ctx = concreteImportEnv.getEnvImport().getCtx();
		try {
			CommandLauncher launch = new CommandLauncher(belfiore);
			
			// INSERIMENTO SIT_F24_TESTATA
			Command cmdF24Testata = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitF24Testata.class.getName(), true);
			
			String sql = concreteImportEnv.
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExtByKeyFonteComune("flag.data.validita.dato", belfiore, idFonte.toString());
			
			while (rs.next()) {
				// INSERIMENTO SIT_F24_TESTATA
				EnvInsertDwh ec = concreteImportEnv.getEnvSitF24Testata();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdF24Testata, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
		
			//SIT_T_PUBBLICITA_PRAT_DETTAGLIO
			Command cmdSitPubblicitaPratDettaglio = CommandFactory.getCommand(it.webred.rulengine.brick.loadDwh.base.LoadSitPubblicitaPratDettaglio.class.getName(), true);
			
			sql = concreteImportEnv.getSQL_RE_PUBBLICITA_B();
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {		
				// INSERIMENTO SIT_T_PUBBLICITA_PRAT_DETTAGLIO
				EnvInsertDwh ec = concreteImportEnv.getEnvSitPubblicitaPratDettaglio();
				ec.setParametriPerGetRighe(rs.getTimestamp("DT_EXP_DATO"));
				InsertDwh.launchInserimento(con, ec, launch, cmdSitPubblicitaPratDettaglio, concreteImportEnv.getEnvImport().getConnectionName(), ctx, rs, abnormals, amkvext);
			}
			
			//tabella senza chiave; update RE_FLAG_ELABORATO
			sql = "UPDATE RE_PUBBLICITA_B SET RE_FLAG_ELABORATO='1' WHERE RE_FLAG_ELABORATO='0'";
			con.prepareStatement(sql).executeUpdate();
			
			
		} catch (Exception e) {
			throw new RulEngineException("Errore durante normalizzazione PUBBLIICTA'", e);
		}
		
		return true;
		*/
		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}

}
