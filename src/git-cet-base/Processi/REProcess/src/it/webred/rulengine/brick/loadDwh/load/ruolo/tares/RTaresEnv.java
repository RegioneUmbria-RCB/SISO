package it.webred.rulengine.brick.loadDwh.load.ruolo.tares;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class RTaresEnv extends EnvImport {

	public RTaresEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("40", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	protected String campiTableSupp = getProperty("tableS.campi");
	protected String campiTableAcc= getProperty("tableA.campi");
	protected ArrayList<int[]> shiftAtoS;
	protected final String SUPPLETIVO = "S";
	protected final String ACCONTO = "A";
	public String SQL_RUOLO = getProperty("sql.SQL_RUOLO");
	
	public String tableRE = getProperty("tableA.name");
	public String anno;
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}
	
	public void calcolaShift(){
		
		shiftAtoS = new ArrayList<int[]>();
		
		List<String> campiS = Arrays.asList(campiTableSupp.split("#"));
		List<String> campiA = Arrays.asList(campiTableAcc.split("#"));
		
		int j = 0;
		int numCampiAggiunti = 0;
		for(int i=0; i<campiA.size(); i++){
			String campoA = campiA.get(i);
			String campoS = campiS.get(j);
			
			if(campoA.equals(campoS)){
				//Corrispondono, non faccio niente
			}else{
				j = campiS.indexOf(campiA.get(i));
				int numCampi = j-(i+numCampiAggiunti);
				int[] ss = {i,numCampi};
				shiftAtoS.add(ss);
				
				numCampiAggiunti +=numCampi;
			}
			j++;
		}
		int numCampiRimasti = campiS.size()-(campiA.size()+numCampiAggiunti);
		if(numCampiRimasti>0){
			int[] ss = {campiA.size(),numCampiRimasti};
			shiftAtoS.add(ss);
		}
		
	}
	
	
}
