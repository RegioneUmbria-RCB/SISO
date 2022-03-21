package it.webred.rulengine.brick.loadDwh.load.demografia.milanoCoordCat;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.demografia.dto.MetricheTracciato;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DemogAnagrafeMilanoEnv extends EnvImport {
	
	private Logger log = Logger.getLogger(DemogAnagrafeMilanoEnv.class.getName());

	public DemogAnagrafeMilanoEnv(String connectionName, Context ctx)
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
		log.debug("Numero informazioni nella fornitura: " + nof);
		for(int i=1; i<=nof; i++) {
			log.debug("Informazione corrente nella fornitura: " + "field."+i+".range");
			String range = getProperty("field."+i+".range");
			String[] rangesplitted = range.split(":");
			MetricheTracciato mt = 
					new MetricheTracciato(i,new Integer(rangesplitted[0]),new Integer(rangesplitted[1]));
			mmtt.add(mt);
		}
		
		return mmtt;
	}
}
