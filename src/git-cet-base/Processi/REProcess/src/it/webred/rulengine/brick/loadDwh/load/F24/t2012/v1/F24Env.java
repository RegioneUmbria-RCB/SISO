package it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1;

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
	
	//esempio nome file F24: F704.I108033.D2012147.P01.T00   
	//                       F704_I108033_D2012147_P01_T00   
	
	private String getFullPrefix() {
		
		String belfiore = super.getCtx().getBelfiore();
		CommonService cdm = (CommonService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "CommonServiceBean");
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(belfiore);
		SitEnte ente = cdm.getEnte(cet);

		String fullprefix = "";
		fullprefix += super.getCtx().getBelfiore();
		//fullprefix += ".I"+ente.getCodIstat() ;   
		//fullprefix += ".D";
		//fullprefix += anno;
		return fullprefix;
	}
}

