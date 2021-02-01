package it.webred.rulengine.brick.loadDwh.load.concessioni.v3;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCConcessioni extends it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCConcessioni {
	
	
	public EnvSitCConcessioni(String nomeTabellaOrigine, String provenienza, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		if (rs.getObject("CHIAVE") != null && !rs.getString("CHIAVE").trim().equals("")) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
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
			if (rs.getObject("DATA_PROROGA_LAVORI") != null && !rs.getString("DATA_PROROGA_LAVORI").trim().equals("")) {
				try {
					params.put("DATA_PROROGA_LAVORI",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("DATA_PROROGA_LAVORI")).getTime())
					);
				} catch (Exception e) {}				
			}	
			
			params.put("POSIZIONE_CODICE", rs.getString("POSIZIONE_CODICE"));
			params.put("POSIZIONE_DESCRIZIONE", rs.getString("POSIZIONE_DESCRIZIONE"));
			if (rs.getObject("POSIZIONE_DATA") != null && !rs.getString("POSIZIONE_DATA").trim().equals("")) {
				try {
					params.put("POSIZIONE_DATA",
							new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("POSIZIONE_DATA")).getTime())
					);
				} catch (Exception e) {}				
			}	
			
			
			params.put("PROVENIENZA", getProvenienza());
			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);
			params.put("ESITO", null);

			ret.add(new RigaToInsert(params));
		}
		
		return ret;
	}

	
}
