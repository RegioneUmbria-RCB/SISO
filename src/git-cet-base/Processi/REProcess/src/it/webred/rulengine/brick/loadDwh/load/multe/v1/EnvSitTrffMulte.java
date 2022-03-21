package it.webred.rulengine.brick.loadDwh.load.multe.v1;

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


public class EnvSitTrffMulte extends EnvInsertDwh {
		
	public EnvSitTrffMulte(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"TIPO_VERBALE", "NR_VERBALE", "DATA_MULTA"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("TIPO_VERBALE");
		chiave+= "" + rs.getString("NR_VERBALE");
		chiave+= " " + rs.getString("DATA_MULTA");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 17);
		
		params.put("STATO_VERBALE",rs.getString("STATO_VERBALE") );
		params.put("TIPO_VERBALE",rs.getString("TIPO_VERBALE") );
		params.put("NR_VERBALE", rs.getString("NR_VERBALE"));
		params.put("SERIE_VERBALE", rs.getString("SERIE_VERBALE"));
		params.put("ANNO_VERBALE", rs.getString("ANNO_VERBALE"));
		params.put("DATA_MULTA",rs.getString("DATA_MULTA") );
		params.put("IMPORTO_MULTA", rs.getString("IMPORTO_MULTA"));
		params.put("IMPORTO_DOVUTO",rs.getString("IMPORTO_DOVUTO") );
		params.put("DT_SCADENZA_PAGAM",rs.getString("DT_SCADENZA_PAGAM") );
		params.put("LUOGO_INFRAZIONE",rs.getString("LUOGO_INFRAZIONE") );
		params.put("NOTE",rs.getString("NOTE") );
		params.put("TIPO_ENTE",rs.getString("TIPO_ENTE") );
		params.put("COMUNE_ENTE",rs.getString("COMUNE_ENTE") );
		params.put("TARGA",rs.getString("TARGA") );
		params.put("MARCA",rs.getString("MARCA") );
		params.put("MODELLO",rs.getString("MODELLO") );
		params.put("CODICE_PERSONA",rs.getString("CODICE_PERSONA") );
		params.put("COGNOME",rs.getString("COGNOME") );
		params.put("NOME",rs.getString("NOME") );
		params.put("LUOGO_NASCITA",rs.getString("LUOGO_NASCITA") );
		params.put("DT_NASCITA",rs.getString("DT_NASCITA") );
		params.put("LUOGO_RESIDENZA",rs.getString("LUOGO_RESIDENZA") );
		params.put("INDIRIZZO_RESIDENZA",rs.getString("INDIRIZZO_RESIDENZA") );
		params.put("NR_PATENTE",rs.getString("NR_PATENTE") );
		params.put("DT_RILASCIO_PATENTE",rs.getString("DT_RILASCIO_PATENTE") );
		params.put("PROV_RILASCIO_PATENTE",rs.getString("PROV_RILASCIO_PATENTE") );
		params.put("FLAG_PAGAMENTO",rs.getString("FLAG_PAGAMENTO") );
		params.put("ESTREMI_PAGAMENTO", rs.getString("ESTREMI_PAGAMENTO"));
		params.put("SISTEMA_PAGAMENTO", rs.getString("SISTEMA_PAGAMENTO"));
		params.put("DT_PAGAMENTO", rs.getString("DT_PAGAMENTO"));
		params.put("IMPORTO_PAGATO", rs.getString("IMPORTO_PAGATO"));
		params.put("COD_FISC", rs.getString("COD_FISC"));
		params.put("IMPORTO_SCONTATO", rs.getString("IMPORTO_SCONTATO"));
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE TIPO_VERBALE=? AND NR_VERBALE=? AND DATA_MULTA = ? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
