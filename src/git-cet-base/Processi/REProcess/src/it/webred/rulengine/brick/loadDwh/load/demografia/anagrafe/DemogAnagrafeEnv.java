package it.webred.rulengine.brick.loadDwh.load.demografia.anagrafe;

import java.util.ArrayList;
import java.util.List;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.demografia.dto.MetricheTracciato;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class DemogAnagrafeEnv extends EnvImport {

	public DemogAnagrafeEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("1", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRE_A = getProperty("tableA.name");
	public String filePrefix = getProperty("file.prefix");
	
	public List<MetricheTracciato> metricheTracciato = getMetricheTracciato();
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles+filePrefix;
	}

	private List<MetricheTracciato> getMetricheTracciato() {
		List<MetricheTracciato> mmtt = new ArrayList<MetricheTracciato>();
		
		int nof = Integer.parseInt(getProperty("nof.fields"));
		for(int i=1; i<=nof; i++) {
			String range = getProperty("field."+i+".range");
			String[] rangesplitted = range.split(":");
			MetricheTracciato mt = 
					new MetricheTracciato(i,new Integer(rangesplitted[0]),new Integer(rangesplitted[1]));
			mmtt.add(mt);
		}
		
		return mmtt;
	}
}
