package it.webred.ct.data.access.basic.diagnostiche.tarsu.dao;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportSoggDTO;

import java.text.SimpleDateFormat;

public class DocfaTarReportQueryBuilder extends CTQueryBuilder{
	
	private RicercaDocfaReportDTO criteri ;
	private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private String SQL_DocfaTarReport = "FROM DocfaTarReport d WHERE 1 = 1 ";
	private String SQL_DocfaTarReport_SOGG = "FROM DocfaTarReport d, DocfaTarReportSogg s WHERE s.id.fkIdRep = d.idRep ";
	private final static String ORDER_BY  =" ORDER BY d.fornitura, d.foglio, d.particella, d.subalterno ";
	
	public DocfaTarReportQueryBuilder(RicercaDocfaReportDTO criteri) {
		this.criteri = criteri;
	}
	
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri != null){
	
			if (isCount)
				sql = "SELECT COUNT(DISTINCT d) ";
			else sql = "SELECT distinct d ";
			
			if(criteri.getTitolare().notEmpty())
				sql += SQL_DocfaTarReport_SOGG;
			else sql += SQL_DocfaTarReport;
			
			sql += getSQLCriteria();
			
			sql += ORDER_BY;
		
		}
		logger.info("SQL ["+sql+"]");
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";		
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		
		sqlCriteria = (criteri.getIdRepDaEscludere() == null  || criteri.getIdRepDaEscludere().equals("") ? sqlCriteria : sqlCriteria + " AND d.idRep != '" + criteri.getIdRepDaEscludere() + "'");
		
		//Parametri catastali immobile DOCFA
		sqlCriteria = (criteri.getFoglio() == null  || criteri.getFoglio().equals("") ? sqlCriteria : sqlCriteria + " AND d.foglio=LPAD('" + criteri.getFoglio() + "',4,'0')");
		sqlCriteria = (criteri.getParticella() == null  || criteri.getParticella().equals("") ? sqlCriteria : sqlCriteria + " AND d.particella=LPAD('" + criteri.getParticella()+ "',5,'0')");
		sqlCriteria = (criteri.getUnimm() == null  || criteri.getUnimm().equals("") ? sqlCriteria : (criteri.getUnimm().equals("null")? sqlCriteria + " AND d.subalterno IS NULL": sqlCriteria + " AND d.subalterno=LPAD('" + criteri.getUnimm()+ "',4,'0')"));
	
		//Parametri dichiarazione DOCFA
		sqlCriteria = (criteri.getFornitura() == null  ? sqlCriteria : sqlCriteria + " AND d.fornitura = TO_DATE('" + yyyyMMdd.format(criteri.getFornitura()) + "', 'yyyyMMdd')");
		sqlCriteria = (criteri.getFornituraDa() == null || criteri.getFornituraDa().equals("") ? sqlCriteria : sqlCriteria + " AND d.fornitura >= TO_DATE('" + criteri.getFornituraDa() + "', 'yyyyMMdd')");
		sqlCriteria = (criteri.getFornituraA() == null  || criteri.getFornituraA().equals("") ? sqlCriteria : sqlCriteria + " AND d.fornitura <= TO_DATE('" + criteri.getFornituraA() + "', 'yyyyMMdd')");
		
		sqlCriteria = (criteri.getProtocolloDocfa() == null  || criteri.getProtocolloDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(d.protocolloDocfa) ='" + criteri.getProtocolloDocfa().toUpperCase() + "'");
		sqlCriteria = (criteri.getDataRegistrazioneDocfa() == null ? sqlCriteria : sqlCriteria + " AND d.dataDocfa = '"+ f.format(criteri.getDataRegistrazioneDocfa())+ "'");
		sqlCriteria = (criteri.getTipoOperDocfa() == null  || criteri.getTipoOperDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND d.tipoOperDocfa ='" + criteri.getTipoOperDocfa() + "'");
		if(criteri.getCausaleDocfa() != null  && !criteri.getCausaleDocfa().equals("")){
			String inCau = "";
			for(String cau: criteri.getCausaliDocfa()){
				inCau += ",'" + cau + "'";
			}
			sqlCriteria = sqlCriteria + " AND UPPER(d.causaleDocfa) IN (" + inCau.replaceFirst(",", "") + ")";
		}		if(criteri.getCategoriaDocfa() != null  && !criteri.getCategoriaDocfa().equals("")){
			String inCat = "";
			for(String cat: criteri.getCategorieDocfa()){
				inCat += ",'" + cat + "'";
			}
			sqlCriteria = sqlCriteria + " AND UPPER(d.categoriaDocfa) IN (" + inCat.replaceFirst(",", "") + ")";
		}
		sqlCriteria = (criteri.getCivicoDocfa()== null  || criteri.getCivicoDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(d.civiciDocfa) ='" + criteri.getCivicoDocfa().toUpperCase() + "'");
		sqlCriteria = (criteri.getViaDocfa() == null  || criteri.getViaDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(d.viaDocfa) LIKE '%" + criteri.getViaDocfa().replace("'","''").toUpperCase() + "%'");
		
		//Parametri Flag Elaborazione
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgElaborati(),			    "d.flgElaborato");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgAnomali(), 				"d.flgAnomalie");
		sqlCriteria = (criteri.getTipoAnomalia() == null  || criteri.getTipoAnomalia().equals("")? sqlCriteria : sqlCriteria + " AND d.codAnomalie LIKE '%" + criteri.getTipoAnomalia() + "%'");
		
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgPresPreDocfa(),       	"d.flgTarAnteDocfa");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgPresPostDocfa(),			"d.flgTarPostDocfa");
		
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgSupTarsuPostMagPre(), 	"d.flgSupTarPostMagPre");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgSupTarsuPostMinPre(), 	"d.flgSupTarPostMinPre");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgSupTarsuPostMagC340(), 	"d.flgSupTarPostMagC340");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgSupTarsuPostMinC340(), 	"d.flgSupTarPostMinC340");
		
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgPresCatDataDocfa(), 		"d.flgUiuCatasto");
		sqlCriteria = addCondRicEq(sqlCriteria, criteri.getFlgDocfaSopprNoCostit(), 	"d.flgDocfaSNoC");
		
		//Ricerca per parametri del soggetto titolare
		RicercaDocfaReportSoggDTO titolare = criteri.getTitolare();
		if(titolare != null && criteri.getTitolare().notEmpty()){
			sqlCriteria = (titolare.getTipoSogg() == null  || titolare.getTipoSogg().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.flgPersFisica) = '" + titolare.getTipoSogg()+ "'");
			sqlCriteria = (titolare.getCognome() == null  || titolare.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.ragiSoci) LIKE '" + titolare.getCognome().replace("'","''").toUpperCase() + "%'");
			sqlCriteria = (titolare.getNome() == null  || titolare.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.nome) LIKE '" + titolare.getNome().replace("'","''").toUpperCase() + "%'");
			sqlCriteria = (titolare.getParIva() == null  || titolare.getParIva().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.codiPiva) = '" + titolare.getParIva().toUpperCase() + "'");
			sqlCriteria = (titolare.getCodFis() == null  || titolare.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.codiFisc) = '" + titolare.getCodFis().toUpperCase() + "'");
			
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgTitolareDataDocfa(),		"s.flgTitolareDataDocfa");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgResidIndCatAllaData(),	"s.flgResidIndCat3112");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgResidIndDcfAllaData(),	"s.flgResidIndDcf3112");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgAnomali(),				"s.flgAnomalia");
			
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresUiuPreDocfa(),		"s.flgTitTarUiuAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresUiuPostDocfa(),		"s.flgTitTarUiuPost");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresCivPreDocfa(),		"s.flgTitTarCivAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresCivPostDocfa(),		"s.flgTitTarCivPost");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresUndPreDocfa(),		"s.flgTitTarAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresUndPostDocfa(),		"s.flgTitTarPost");
			
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamUiuPreDocfa(),	"s.flgFamTarUiuAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamUiuPostDocfa(),	"s.flgFamTarUiuPost");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamCivPreDocfa(),	"s.flgFamTarCivAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamCivPostDocfa(),	"s.flgFamTarCivPost");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamUndPreDocfa(),	"s.flgFamTarAnte");
			sqlCriteria = addCondRicEq(sqlCriteria, titolare.getFlgPresFamUndPostDocfa(),	"s.flgFamTarPost");
			
		}
		
		return sqlCriteria;
	}
	
	private String addCondRicEq(String sqlCriteria, Object condizione, String colonna ){
		return sqlCriteria = (condizione == null || condizione.toString().equals("")? sqlCriteria : sqlCriteria + " AND "+ colonna + " ='" + condizione.toString() + "'");
	}
	
	
}
