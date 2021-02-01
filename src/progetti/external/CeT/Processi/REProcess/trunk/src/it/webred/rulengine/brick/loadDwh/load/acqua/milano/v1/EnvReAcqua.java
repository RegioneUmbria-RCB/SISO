package it.webred.rulengine.brick.loadDwh.load.acqua.milano.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.exception.RulEngineException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvReAcqua extends EnvInsertDwh {
		
	public EnvReAcqua(String nomeTabellaOrigine, String provenienza,
			String[] nomeCampiChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampiChiave);
	}
	
	@Override
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
		
		return ret;
	}

	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		// TODO Auto-generated method stub
		return "";
	}
	
}
