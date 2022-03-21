package it.webred.rulengine.brick.loadDwh.load.superc.concrete;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;

public interface PreNormalizzaFilter {

	
	public void filter(Connection conn ) throws RulEngineException ;

	
}
