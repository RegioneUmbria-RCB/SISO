package it.webred.rulengine.brick.loadDwh.load.locazioni.loca;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class LocazioniConcreteImportEnv <T extends EnvImport> extends ConcreteImportEnv<T> {

	public LocazioniConcreteImportEnv(T ei) {
		super(ei);
	}

}
