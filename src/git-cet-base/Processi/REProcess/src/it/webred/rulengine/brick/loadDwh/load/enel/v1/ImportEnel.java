package it.webred.rulengine.brick.loadDwh.load.enel.v1;

import java.sql.Connection;

import org.apache.log4j.Logger;

import it.webred.rulengine.brick.loadDwh.load.enel.EnelEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportEnel<T extends  Env<?>> extends ConcreteImport<T> {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportEnel.class.getName());
	
	public ImportEnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.enel.v1.Env<EnelEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.enel.v1.Env<EnelEnv>((EnelEnv)ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
			
		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
