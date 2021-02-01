package it.webred.rulengine.brick.pregeo;

import it.webred.rulengine.Context;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class PregeoEnv extends EnvImport {

	public PregeoEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("24", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	protected String dirFilesDatiDiogene = Utils.getConfigProperty("dir.files.datiDiogene") + "\\" + super.getCtx().getBelfiore() + "\\";
	
	/*public String folderNameFrom = dirFiles + getProperty("dir.files.from");
	public String folderNameTo = dirFilesDatiDiogene + getProperty("dir.files.to");*/
	//non si usa più il file properties: la cartella from è la dir.files dell'ente per la fonte Pregeo, la cartella to deve essere fissa "pregeo"
	public String folderNameFrom = dirFiles;
	public String folderNameTo = dirFilesDatiDiogene + "pregeo/";
	
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
