package it.webred.rulengine.diagnostics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.bean.ParamInValue;
import it.webred.rulengine.diagnostics.superclass.ElaboraDiagnostics;

/**
 * Questa classe è usata per gestire le diagnostiche che hanno come risultato l'esecuzione
 * di una select che ritorna un ResultSet
 * @author Luca
 *
 */
public class ElaboraDiagnosticsSql extends ElaboraDiagnostics {
	
	public ElaboraDiagnosticsSql(Connection conn,Context ctxPar, List<RRuleParamIn> paramsPar) {
		super(conn,ctxPar,paramsPar);
		log = Logger.getLogger(ElaboraDiagnosticsSql.class.getName());
	}
	
	@Override
	protected Object getResult(DiagnosticConfigBean diaConf) throws Exception{
		log.info("[GETRESULT] - START Diagnostica " + diaConf.toString());
		String selectSql = "";
		PreparedStatement pstmt = getConn().prepareStatement("select c.sqlcommandprop from dia_catalogo c where c.idcatalogodia = ?");
		pstmt.setInt(1, diaConf.getIdCatalogoDia());
		log.debug("[GETRESULT] - select dia_catalogo id :" + diaConf.getIdCatalogoDia() );
		ResultSet rs_qry = pstmt.executeQuery();
		if (!rs_qry.next())
			throw new Exception("Errore nell'elaborazione della diagnostica con id: " + diaConf.getIdCatalogoDia());
		String sqlCommandProp = rs_qry.getString("sqlcommandprop").trim();
		log.debug("[GETRESULT] - sqlcommandprop recuperato :" + sqlCommandProp);
		rs_qry.close();
		if (sqlCommandProp != null && sqlCommandProp.trim().startsWith("PROPERTY@")) {
			String codEnte = "";
			String nomeProp = "" + diaConf.getIdCatalogoDia();
			if (sqlCommandProp.trim().length() > "PROPERTY@".length()) {
				nomeProp = sqlCommandProp.trim().substring(sqlCommandProp.trim().indexOf("PROPERTY@") + "PROPERTY@".length());
			}
			log.info("[GETRESULT] - Recupero codice belfiore ente dalla tabella sit_ente ");
			ResultSet rsEnte = getConn().prepareStatement("select codent from sit_ente").executeQuery();
			while (rsEnte.next()) {
				codEnte = rsEnte.getString("codent");
			}
			rsEnte.close();
			log.debug("[GETRESULT] - codice belfiore ente " + codEnte);	
			log.debug("[GETRESULT] - lettura chiave dia.properties : " + "dia." + nomeProp + "." + codEnte);		
			selectSql = getDiaProperty("dia." + nomeProp + "." + codEnte);
			if (selectSql == null) {
				selectSql = getDiaProperty("dia." + nomeProp);
			}
		} else {
			selectSql = sqlCommandProp;
		}
	
		if (selectSql == null || selectSql.trim().equals("")) {
			throw new Exception("Impossibile esportare la diagnostica con id: " + diaConf.getIdCatalogoDia() + "; query di esportazione non definita");
		} else {
			selectSql = selectSql.trim();
		}
		log.debug("[GETRESULT] - SQL DIA DA ESEGUIRE: " + selectSql);
		System.out.println("[GETRESULT] - SQL DIA DA ESEGUIRE: " + selectSql);
		// rimuovo eventuali commenti sql
		log.info("[GETRESULT] - Rimuovo commenti sql dalla select recuperata ");
		int fromindex = 0;
		while (selectSql.indexOf("/*",fromindex) > -1) {
			if(selectSql.indexOf("/*",fromindex) != selectSql.indexOf("/*+",fromindex)) {
				selectSql = selectSql.substring(0, selectSql.indexOf("/*",fromindex)) + selectSql.substring(selectSql.indexOf("*/",fromindex) + 2);
			    fromindex = selectSql.indexOf("*/",fromindex) +2;
			} else {
				fromindex = selectSql.indexOf("*/", selectSql.indexOf("/*+",fromindex)+3)+2;
			}
		}
		selectSql += "\n ";
		while (selectSql.indexOf("--") > -1) {
			selectSql = selectSql.substring(0, selectSql.indexOf("--")) + selectSql.substring(selectSql.indexOf("\n", selectSql.indexOf("--")));
		}
		
		// scorporo la group by 
		log.info("[GETRESULT] - Scorporo la group by ");
		String group = "";
		if (selectSql.toLowerCase().lastIndexOf("group by") > -1) {
			group = selectSql.substring(selectSql.toLowerCase().lastIndexOf("group by"));
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("group by"));
		}
		// scorporo eventuale order
		log.info("[GETRESULT] - Scorporo eventuale order ");
		String order = "";
		if (selectSql.toLowerCase().lastIndexOf("order") > -1) {
			order = selectSql.substring(selectSql.toLowerCase().lastIndexOf("order"));
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("order"));
		}	
					
		
		// Mapping eventuali parametri
		log.info("[GETRESULT] - Mapping eventuali parametri ");
		if (selectSql.toLowerCase().lastIndexOf("where") > -1 && getParamsInValue() != null && getParamsInValue().size() > 0){
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("where") + 5) + " 1=1 and " + selectSql.substring(selectSql.toLowerCase().lastIndexOf("where") + 5);
			
			int nParQuery = getNumParam(selectSql);			
			log.debug(" Né parametri trovati nella query :" + nParQuery);
			log.debug(" Né parametri passati dal ctx :" + getParamsInValue().size());
			
			if (nParQuery != getParamsInValue().size())
				throw new Exception("Impossibile esportare la diagnostica numero parametri diverso!");
			
			//Modifico la selectSql in base ai parametri passati dal ctx ed al loro valore. Se nel ctx il parametro
			//n-esimo non è valorizzato, nel prepared stmt il parametro n-esimo sarà commentato 
			
			Pattern p = Pattern.compile("\\s(and|or).*?(\\sand|\\sor|$)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);	
			int finposition = 0;
			if (getParamsInValue() != null) {
				datiDiaTestata4Upd.put(super.DES_PARAM, getParamsInValue());
				for (int i = 0; i < getParamsInValue().size(); i++) {
					Matcher m = p.matcher(selectSql);
					m.find(finposition);
					boolean trovato = true;
					while(m.group(0).indexOf("?") <0) {
						trovato =true;
						if (m.find(m.start(2)-1)) continue; else trovato=false; break;
					}
					if (getParamsInValue().get(i).getParamValue() == null) {
						selectSql = selectSql.substring(0, m.start(1)) + " /* " + selectSql.substring(m.start(1), m.start(2)) + " */ " + selectSql.substring(m.start(2));
					}
					if (trovato)
					finposition = m.start(2)-1;
				}
			}
		}

	
		selectSql += " " + order;
		selectSql += " " + group;
		
		//Eseguo la query
		datiDiaTestata4Upd.put(super.DES_SQL, selectSql);
		pstmt.cancel();
		pstmt = getConn().prepareStatement(selectSql);		
		int k = 0;
		if (getParamsInValue() != null) {
			for (ParamInValue parIn : getParamsInValue()) {
				log.debug("[GETRESULT] - Parametro da ctx : " + parIn.toString());
				if (parIn.getParamValue() != null) {					
					callPreparedStatementSetMethod(pstmt, parIn, ++k);
				}
			}			
		}
	
		log.info("[GETRESULT] - END ");
		
		return pstmt.executeQuery();
		
	}
}
