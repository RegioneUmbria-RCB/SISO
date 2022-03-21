package it.webred.rulengine.brick.loadDwh.load.concessioni.v1;


import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCConcDatiTec extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitCConcDatiTec(String nomeTabellaOrigine, String provenienza,  String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
		
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
	 		  params.put("ID_ORIG", rs.getString("CHIAVE"));
	 		  params.put("PROVENIENZA",this.getProvenienza());
	 		  params.put("FK_ENTE_SORGENTE", 3);
		      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("CHIAVE_RELAZIONE") );
			  params.put("dest_uso", rs.getString("dest_uso"));
			  params.put("zone_omogenee", rs.getString("zone_omogenee"));
			  params.put("zone_funzionali",rs.getString("zone_funzionali") );
			  params.put("vincoli",rs.getString("vincoli") );
			  
			  params.put("sup_eff_lotto",rs.getString("sup_eff_lotto") );
			  params.put("sup_edificabile",rs.getString("sup_edificabile") );
			  params.put("sup_lorda_pav",rs.getString("sup_lorda_pav") );
			  
			  params.put("sup_occupata",rs.getString("sup_occupata") );
			  params.put("sup_coperta",rs.getString("sup_coperta") );
			  params.put("sup_filtrante",rs.getString("sup_filtrante") );
			  params.put("vol_virtuale",rs.getString("vol_virtuale") );

			  params.put("vol_fisico_istat",rs.getString("vol_fisico_istat") );
			  params.put("vol_totale",rs.getString("vol_totale") );
			  params.put("parcheggio_n_posti",rs.getString("parcheggio_n_posti") );
			  params.put("parcheggio_sup",rs.getString("parcheggio_sup") );
			  params.put("vani",rs.getString("vani") );
			  params.put("num_abitazioni",rs.getString("num_abitazioni") );
				if (rs.getObject("data_agibilita") != null && !rs.getString("data_agibilita").trim().equals("")) {
					try {
						params.put("data_agibilita",
								new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("data_agibilita")).getTime())
						);
					} catch (Exception e) {}				
				}
			  
				if (rs.getObject("data_abitabilita") != null && !rs.getString("data_abitabilita").trim().equals("")) {
					try {
						params.put("data_abitabilita",
								new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("data_abitabilita")).getTime())
						);
					} catch (Exception e) {}				
				}
			  
			  params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);
			ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE CHIAVE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
