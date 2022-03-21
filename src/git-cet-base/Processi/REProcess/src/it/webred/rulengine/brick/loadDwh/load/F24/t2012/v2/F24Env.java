package it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2;

import org.apache.log4j.Logger;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.data.access.basic.common.CommonService;
import it.webred.ct.data.model.common.SitEnte;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class F24Env extends EnvImport {

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRE_A = getProperty("tableA.name");
	//public String anno = getProperty("file.year");
	
	public F24Env(String connectionName, Context ctx) throws RulEngineException {
			super("33", connectionName, ctx);
	}

	
	@Override
	public String getPercorsoFiles() {
		return dirFiles + getFullPrefix();
	}
	
	//esempio nome file F24: IMU.F704.D2012154.P01.T00
	
	private String getFullPrefix() {
		
		String fullprefix = "IMU.";
		fullprefix += super.getCtx().getBelfiore();
		fullprefix += ".D";
		//fullprefix += anno;
		return fullprefix;
	}
}

