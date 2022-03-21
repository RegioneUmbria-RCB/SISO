package it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.v1;

import it.webred.rulengine.brick.loadDwh.load.concessioni.v2.Env;
import it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.PlanimetrieComma340Env;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import org.apache.log4j.Logger;

public class ImportPlanimetrieComma340<T extends  Env<?>>   extends  ConcreteImport<T> {





	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPlanimetrieComma340.class.getName());
	

	public boolean normalizza(String belfiore) throws RulEngineException {
		
		return true;
	}


	@Override
	public it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.v1.Env<PlanimetrieComma340Env> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.v1.Env<PlanimetrieComma340Env>((PlanimetrieComma340Env)ei);
	}


	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}






}
