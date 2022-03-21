package it.webred.rulengine.brick.loadDwh.load.rette.dettaglio;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class BolletteDettConcreteImportEnv <T extends EnvImport> extends ConcreteImportEnv<T> {

	
	
	protected String[] CHIAVE ={"COD_BOLLETTA", "DES_VOCE"};
	
	public BolletteDettConcreteImportEnv(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}
	
}
