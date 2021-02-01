package it.webred.rulengine.brick.idoneitaAlloggi;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class IdoneitaAlloggiEnv extends EnvImport {

	public IdoneitaAlloggiEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("24", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String folderNameFrom = getProperty("dir.files.from");
	public String folderNameTo = getProperty("dir.files.to");
	
	public String SQL_INDIRIZZO = getProperty("SQL_INDIRIZZO");
	public String SQL_DETT_RESIDENTI1 = getProperty("SQL_DETT_RESIDENTI1");
	public String SQL_DETT_UI = getProperty("SQL_DETT_UI");
	public String SQL_INDIRIZZO_CAT_UI = getProperty("SQL_INDIRIZZO_CAT_UI");
	public String SQL_TITOLARI = getProperty("SQL_TITOLARI");
	public String SQL_DOCFA_UIU = getProperty("SQL_DOCFA_UIU");
	public String SQL_DOCFA_ID_IMM = getProperty("SQL_DOCFA_ID_IMM");
	public String SQL_TARSU = getProperty("SQL_TARSU");
	public String NOME_FILE_ELENCO_VIE = getProperty("NOME_FILE_ELENCO_VIE");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
