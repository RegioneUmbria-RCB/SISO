package it.webred.rulengine.brick.cened;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class CenedEnv extends EnvImport {

	public CenedEnv(String connectionName, Context ctx) throws RulEngineException {
		super("45", connectionName, ctx);
	}

//	protected String dirFilesFrom = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
//	protected String dirFilesDatiDiogene = Utils.getConfigProperty("dir.files.datiDiogene") + "\\" + super.getCtx().getBelfiore() + "\\";
	
	public String folderNameFrom = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
//	public String folderNameTo = dirFilesDatiDiogene + "pregeo/";
	

	@Override
	public String getPercorsoFiles() {
		return folderNameFrom;
	}

}
