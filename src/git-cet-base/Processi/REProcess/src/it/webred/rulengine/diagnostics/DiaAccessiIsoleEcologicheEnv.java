package it.webred.rulengine.diagnostics;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class DiaAccessiIsoleEcologicheEnv extends EnvImport {
	
	protected String diaExpDirFiles = getConfigProperty("dia.exp.dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	protected String diaFtpUrl = getConfigProperty("dia.ftp.url",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
//	protected String diaFtpUsr = getConfigProperty("dia.ftp.usr",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
//	protected String diaFtpPwd = getConfigProperty("dia.ftp.pwd",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	public DiaAccessiIsoleEcologicheEnv(String connectionName, Context ctx) throws RulEngineException {
		//fonte dati associata alla diagnostica delle isole ecologiche è la 2: tributi
		super("2", connectionName, ctx);
	}


	@Override
	public String getPercorsoFiles() {
		return diaExpDirFiles;
	}

	public String getDiaFtpUrl() {
		return diaFtpUrl;
	}


}
