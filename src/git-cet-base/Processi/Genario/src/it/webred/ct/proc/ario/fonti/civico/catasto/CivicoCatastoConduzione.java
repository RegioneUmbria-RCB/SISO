package it.webred.ct.proc.ario.fonti.civico.catasto;

import java.sql.Connection;
import java.sql.ResultSet;

import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.civico.Civici;

public class CivicoCatastoConduzione extends DatoDwh implements Civici{

	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean codiceCivico(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean existProcessId() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDeleteSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 4;
	}

	@Override
	public String getInsertSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProgEs() {
		
		return 3;
	}

	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLNewProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLSaveProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLUpdateProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSearchSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSql(String pID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert,
			String saveTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareUpdateDato(DatoDwh classeFonte,
			Connection connForInsert, String saveTotale, String updateTotale,
			String searchTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean queryWithParamCodEnte() {
		// TODO Auto-generated method stub
		return false;
	}

}
