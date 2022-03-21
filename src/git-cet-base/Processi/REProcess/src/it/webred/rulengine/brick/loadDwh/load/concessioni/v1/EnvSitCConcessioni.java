package it.webred.rulengine.brick.loadDwh.load.concessioni.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCConcessioni extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitCConcessioni(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		if (rs.getObject("CHIAVE") != null && !rs.getString("CHIAVE").trim().equals("")) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
			if (rs.getString("CHIAVE").equals("10031"))
				params = new LinkedHashMap<String, Object>();
			
			params.put("ID_ORIG", rs.getString("CHIAVE"));
			params.put("FK_ENTE_SORGENTE", 3);
			params.put("CONCESSIONE_NUMERO", rs.getString("concessione_numero"));
			

			params.put("PROGRESSIVO_NUMERO",rs.getString("progressivo_numero") );
			params.put("PROGRESSIVO_ANNO", rs.getString("progressivo_anno") );

			if (rs.getObject("PROTOCOLLO_DATA") != null && !rs.getString("PROTOCOLLO_DATA").trim().equals("")) {
				try {
					params.put("PROTOCOLLO_DATA",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("PROTOCOLLO_DATA")).getTime())
					);
				} catch (Exception e) {}				
			}	
			
			params.put("PROTOCOLLO_NUMERO", rs.getString("protocollo_numero"));			
			params.put("TIPO_INTERVENTO", rs.getString("tipo_intervento"));			
			params.put("OGGETTO", rs.getString("oggetto"));			
			params.put("PROCEDIMENTO", rs.getString("procedimento"));			
			params.put("ZONA", rs.getString("zona"));

			
			if (rs.getObject("data_rilascio") != null && !rs.getString("data_rilascio").trim().equals("")) {
				try {
					params.put("DATA_RILASCIO",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("data_rilascio")).getTime())
					);
				} catch (Exception e) {}				
			}			
			params.put("DATA_RILASCIO", rs.getTimestamp("data_rilascio"));

			
			if (rs.getObject("DATA_INIZIO_LAVORI") != null && !rs.getString("DATA_INIZIO_LAVORI").trim().equals("")) {
				try {
					params.put("DATA_INIZIO_LAVORI",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("DATA_INIZIO_LAVORI")).getTime())
					);
				} catch (Exception e) {}				
			}				
			
			if (rs.getObject("DATA_FINE_LAVORI") != null && !rs.getString("DATA_FINE_LAVORI").trim().equals("")) {
				try {
					params.put("DATA_FINE_LAVORI",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("DATA_FINE_LAVORI")).getTime())
					);
				} catch (Exception e) {}				
			}				
			params.put("PROVENIENZA", this.getProvenienza());

			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);
			params.put("ESITO", null);

			ret.add(new RigaToInsert(params));
		}
		
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
