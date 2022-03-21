package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


public class EnvSitCConcPersonaOnlyOne extends EnvInsertDwh {



	public EnvSitCConcPersonaOnlyOne(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

		List<String> nomi = new  ArrayList<String>();
		List<String> titoli = new  ArrayList<String>();
		String nominativi = rs.getString("D1");
		if (nominativi!=null)
			nomi = Arrays.asList( nominativi.split("\\+"));
		String tipotitoli = rs.getString("E1");
		if (tipotitoli!=null)
			titoli = Arrays.asList( tipotitoli.split("\\+"));

		
		Iterator<String> itrNomi = nomi.iterator();
		int curIndex = 0;
	    while (itrNomi.hasNext()) {
			LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
			ret.add(params);
	      String nome = (String) itrNomi.next();
	      String titolo = null;
	      try {
	    	  
		      titolo = titoli.get(curIndex);
	      } catch (Exception e) {
	    	  
	      }
	 		  params.put("ID_ORIG", null);
	 		  params.put("PROVENIENZA","OO");
	 		  params.put("FK_ENTE_SORGENTE", 3);
		      params.put("ID_ORIG_C_PERSONA",nome );
			  params.put("TITOLO", titolo);
		      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("A") );
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);
				
				curIndex++;
	    }
		
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return null;
		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}






}
