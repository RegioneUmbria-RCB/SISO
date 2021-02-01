package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;
import it.webred.rulengine.db.model.RAbNormal;

public class EnvSitCConcessioniCatastoOnlyOne extends EnvInsertDwh {



	public EnvSitCConcessioniCatastoOnlyOne(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		try {
			List<String> fogli = new  ArrayList<String>();
			List<String> particelle = new  ArrayList<String>();
			List<String> subalterni = new  ArrayList<String>();

			
			String f = rs.getString("Q");
			if (f!=null)
				fogli = Arrays.asList( f.split("\\+"));
			String p = rs.getString("S");
			if (p!=null)
				particelle = Arrays.asList( p.split("\\+"));
			String s = rs.getString("U");
			if (s!=null)
				subalterni = Arrays.asList( s.split("\\+"));


			
			Iterator<String> itrFogli = fogli.iterator();
			int indexCount = 0;
		    while (itrFogli.hasNext()) {
				LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
				ret.add(params);
		      String foglio = (String) itrFogli.next();
		      String particella = null;
		      String subalterno  = null;
		      try {
		    	  particella = particelle.get(indexCount);
		      } catch (Exception e) {
		      }
		      try {
		    	  subalterno = subalterni.get(indexCount);
		      } catch (Exception e) {
		      }
		 		  params.put("ID_ORIG", null);
		 		  params.put("PROVENIENZA","OO");
		 		  params.put("FK_ENTE_SORGENTE", 3);
			      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("A") );
				  params.put("FOGLIO", foglio);
				  params.put("PARTICELLA", particella);
				  params.put("SUBALTERNO", subalterno);
				  params.put("TIPO", "URBANO");
				  params.put("SEZIONE", null);
				  params.put("DESCRIZIONE", null);

				  params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
					params.put("DT_INI_VAL_DATO", null);
					params.put("DT_FINE_VAL_DATO", null);
					params.put("FLAG_DT_VAL_DATO", 0);
					
				indexCount++;
		    }
		} finally
		{
			
		}
		
		
		// RIPETO LA COSA PER GLI ALTRI 3 CAMPI
		try {
			List<String> fogli = new  ArrayList<String>();
			List<String> particelle = new  ArrayList<String>();
			List<String> subalterni = new  ArrayList<String>();

			
			String f = rs.getString("R");
			if (f!=null)
				fogli = Arrays.asList( f.split("\\+"));
			String p = rs.getString("T");
			if (p!=null)
				particelle = Arrays.asList( p.split("\\+"));
			String s = rs.getString("V");
			if (s!=null)
				subalterni = Arrays.asList( s.split("\\+"));


			
			Iterator<String> itrFogli = fogli.iterator();
		    while (itrFogli.hasNext()) {
				LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
				ret.add(params);
		      String foglio = (String) itrFogli.next();
		      String particella = null;
		      String subalterno  = null;
		      try {
		    	  particella = particelle.get(fogli.indexOf(foglio));
		      } catch (Exception e) {
		      }
		      try {
		    	  subalterno = subalterni.get(fogli.indexOf(foglio));
		      } catch (Exception e) {
		      }
		 		  params.put("ID_ORIG", null);
		 		  params.put("PROVENIENZA","OO");
		 		  params.put("FK_ENTE_SORGENTE", 3);
			      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("A") );
				  params.put("FOGLIO", foglio);
				  params.put("PARTICELLA", particella);
				  params.put("SUBALTERNO", subalterno);
				  params.put("TIPO", "TERRENI");
				  params.put("SEZIONE", null);

				  params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
					params.put("DT_INI_VAL_DATO", null);
					params.put("DT_FINE_VAL_DATO", null);
					params.put("FLAG_DT_VAL_DATO", 0);
		    }
		} finally
		{
			
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
