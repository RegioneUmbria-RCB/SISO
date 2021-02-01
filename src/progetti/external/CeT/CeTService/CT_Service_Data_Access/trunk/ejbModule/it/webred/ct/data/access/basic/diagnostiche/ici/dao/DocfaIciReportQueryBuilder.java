package it.webred.ct.data.access.basic.diagnostiche.ici.dao;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportSoggDTO;
import it.webred.ct.data.model.diagnostiche.DocfaIciReport;
import it.webred.ct.data.model.diagnostiche.DocfaIciReportSogg;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocfaIciReportQueryBuilder extends CTQueryBuilder{
	
	private RicercaDocfaReportDTO criteri ;
	private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private String SQL_DocfaIciReport = "FROM DocfaIciReport d WHERE 1 = 1 ";
	private String SQL_DocfaIciReport_SOGG = "FROM DocfaIciReport d, DocfaIciReportSogg s WHERE s.id.fkIdRep = d.idRep ";
	private final static String ORDER_BY  =" ORDER BY d.fornitura, d.foglio, d.particella, d.subalterno ";
	
	public DocfaIciReportQueryBuilder(RicercaDocfaReportDTO criteri) {
		this.criteri = criteri;
	}
	
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri != null){
	
			if (isCount)
				sql = "SELECT COUNT(DISTINCT d) ";
			else sql = "SELECT distinct d ";
			
			if(criteri.getTitolare().notEmpty())
				sql += SQL_DocfaIciReport_SOGG;
			else sql += SQL_DocfaIciReport;
			
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
		sqlCriteria = (criteri.getFornituraDa() == null || criteri.getFornituraDa().equals("")  ? sqlCriteria : sqlCriteria + " AND d.fornitura >= TO_DATE('" + criteri.getFornituraDa() + "', 'yyyyMMdd')");
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
		}
		if(criteri.getCategoriaDocfa() != null  && !criteri.getCategoriaDocfa().equals("")){
			String inCat = "";
			for(String cat: criteri.getCategorieDocfa()){
				inCat += ",'" + cat + "'";
			}
			sqlCriteria = sqlCriteria + " AND UPPER(d.categoriaDocfa) IN (" + inCat.replaceFirst(",", "") + ")";
		}
		sqlCriteria = (criteri.getCivicoDocfa()== null  || criteri.getCivicoDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(d.civiciDocfa) ='" + criteri.getCivicoDocfa().toUpperCase() + "'");
		sqlCriteria = (criteri.getViaDocfa() == null  || criteri.getViaDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(d.viaDocfa) LIKE '%" + criteri.getViaDocfa().replace("'","''").toUpperCase() + "%'");
		sqlCriteria = (criteri.getRenditaDa() == null  || BigDecimal.ZERO.compareTo(criteri.getRenditaDa())>= 0 ? sqlCriteria : sqlCriteria + " AND d.renditaDocfa >= "+ criteri.getRenditaDa());
		sqlCriteria = (criteri.getRenditaA()  == null  || BigDecimal.ZERO.compareTo(criteri.getRenditaA())>= 0  ? sqlCriteria : sqlCriteria + " AND d.renditaDocfa <= "+ criteri.getRenditaA());

		
		//Parametri Flag Elaborazione
		sqlCriteria = (criteri.getFlgElaborati()== null  || criteri.getFlgElaborati().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgElaborato ='" + criteri.getFlgElaborati() + "'");
		sqlCriteria = (criteri.getFlgAnomali()== null  || criteri.getFlgAnomali().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgAnomalie ='" + criteri.getFlgAnomali() + "'");
		sqlCriteria = (criteri.getFlgAnomaliClasse()== null  || criteri.getFlgAnomaliClasse().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgAnomaliaClasse ='" + criteri.getFlgAnomaliClasse() + "'");
		sqlCriteria = (criteri.getFlgPresPreDocfa()== null  || criteri.getFlgPresPreDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgIciAnteDocfa ='" + criteri.getFlgPresPreDocfa() + "'");
		sqlCriteria = (criteri.getFlgPresPostDocfa()== null  || criteri.getFlgPresPostDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgIciPostDocfa ='" + criteri.getFlgPresPostDocfa() + "'");
		sqlCriteria = (criteri.getFlgPresCatDataDocfa()== null  || criteri.getFlgPresCatDataDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgUiuCatasto ='" + criteri.getFlgPresCatDataDocfa() + "'");
		sqlCriteria = (criteri.getFlgDocfaSopprNoCostit()== null  || criteri.getFlgDocfaSopprNoCostit().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgDocfaSNoC ='" + criteri.getFlgDocfaSopprNoCostit() + "'");
		sqlCriteria = (criteri.getFlgVarCategoria()== null  || criteri.getFlgVarCategoria().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgVarAnteCategoria ='" + criteri.getFlgVarCategoria() + "'");
		sqlCriteria = (criteri.getFlgVarClasse()== null  || criteri.getFlgVarClasse().equals("") ? sqlCriteria : sqlCriteria + " AND d.flgVarAnteClasse ='" + criteri.getFlgVarClasse() + "'");
		
		if(criteri.getFlgVarRendita()!= null  && !criteri.getFlgVarRendita().equals("")){
			
			logger.info("Minore di: "+criteri.getVarRenditaMinDi() +", Maggiore di: "+ criteri.getVarRenditaMagDi());
			
			if("0".equalsIgnoreCase(criteri.getFlgVarRendita()))
				sqlCriteria += " AND d.flgVarAnteRendita ='0'";
			
			else{
				sqlCriteria += " AND d.flgVarAnteRendita ='1'";
			
				if("-".equalsIgnoreCase(criteri.getFlgVarRendita())){
					sqlCriteria += " AND d.maxDimPercRenIci2Dcf > 0";
					sqlCriteria += " AND d.maxDimRenIci2Dcf > 0";
					
					if(criteri.getFlgVarRenditaPerc()!=null && criteri.getFlgVarRenditaPerc()){
						sqlCriteria = (criteri.getVarRenditaMinDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMinDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxDimPercRenIci2Dcf <= "+ criteri.getVarRenditaMinDi());
						sqlCriteria = (criteri.getVarRenditaMagDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMagDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxDimPercRenIci2Dcf >= "+ criteri.getVarRenditaMagDi());
					}else{
						sqlCriteria = (criteri.getVarRenditaMinDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMinDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxDimRenIci2Dcf <= "+ criteri.getVarRenditaMinDi());
					    sqlCriteria = (criteri.getVarRenditaMagDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMagDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxDimRenIci2Dcf >= "+ criteri.getVarRenditaMagDi());
					}
				}else if("+".equalsIgnoreCase(criteri.getFlgVarRendita())){
					sqlCriteria += " AND d.maxAugPercRenIci2Dcf > 0";
					sqlCriteria += " AND d.maxAugRenIci2Dcf > 0";
					
					if(criteri.getFlgVarRenditaPerc()!=null && criteri.getFlgVarRenditaPerc()){
						sqlCriteria = (criteri.getVarRenditaMinDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMinDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxAugPercRenIci2Dcf <= "+ criteri.getVarRenditaMinDi());
						sqlCriteria = (criteri.getVarRenditaMagDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMagDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxAugPercRenIci2Dcf >= "+ criteri.getVarRenditaMagDi());
					
					}else{
						sqlCriteria = (criteri.getVarRenditaMinDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMinDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxAugRenIci2Dcf <= "+ criteri.getVarRenditaMinDi());
						sqlCriteria = (criteri.getVarRenditaMagDi()== null  || BigDecimal.ZERO.compareTo(criteri.getVarRenditaMagDi())>= 0 ? sqlCriteria : sqlCriteria + " AND d.maxAugRenIci2Dcf >= "+ criteri.getVarRenditaMagDi());
					}
				}
			}
		}
		
		//Variazione % tra la somma dei val.imponibili per uiu cessate/variate (nel docfa) e la somma dei val.imponibili delle uiu costutuite/variate (nel docfa)
		if(criteri.getFlgVarPercSum()!= null  && !criteri.getFlgVarPercSum().equals("")){
			if("-".equalsIgnoreCase(criteri.getFlgVarPercSum())){
				sqlCriteria += " AND d.varPercSumImponibile < 0";
				sqlCriteria = (criteri.getVarPercSumPrePostDa()== null || BigDecimal.ZERO.compareTo(criteri.getVarPercSumPrePostDa())>= 0 ? sqlCriteria : sqlCriteria + " AND d.varPercSumImponibile <= -"+ criteri.getVarPercSumPrePostDa());
				sqlCriteria = (criteri.getVarPercSumPrePostA()== null  || BigDecimal.ZERO.compareTo(criteri.getVarPercSumPrePostA())>= 0 ? sqlCriteria : sqlCriteria + " AND d.varPercSumImponibile >= -"+ criteri.getVarPercSumPrePostA());
			}else if("+".equalsIgnoreCase(criteri.getFlgVarPercSum())){
				sqlCriteria += " AND (d.varPercSumImponibile > 0 OR d.varPercSumImponibile IS NULL)";
				sqlCriteria = (criteri.getVarPercSumPrePostDa()== null || BigDecimal.ZERO.compareTo(criteri.getVarPercSumPrePostDa())>= 0 ? sqlCriteria : sqlCriteria + " AND NVL(d.varPercSumImponibile,99999999) >= "+ criteri.getVarPercSumPrePostDa());
				sqlCriteria = (criteri.getVarPercSumPrePostA()== null  || BigDecimal.ZERO.compareTo(criteri.getVarPercSumPrePostA())>= 0 ?  sqlCriteria : sqlCriteria + " AND d.varPercSumImponibile <= "+ criteri.getVarPercSumPrePostA());
			}else
				sqlCriteria += " AND d.varPercSumImponibile = 0";
		}	
				
		sqlCriteria = (criteri.getTipoAnomalia() == null  || criteri.getTipoAnomalia().equals("")? sqlCriteria : sqlCriteria + " AND d.codAnomalie LIKE '%" + criteri.getTipoAnomalia() + "%'");

		//Ricerca per parametri del soggetto titolare
		RicercaDocfaReportSoggDTO titolare = criteri.getTitolare();
		if(titolare != null && criteri.getTitolare().notEmpty()){
			sqlCriteria = (titolare.getTipoSogg() == null  || titolare.getTipoSogg().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.flgPersFisica) = '" + titolare.getTipoSogg()+ "'");
			sqlCriteria = (titolare.getCognome() == null  || titolare.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.ragiSoci) LIKE '" + titolare.getCognome().replace("'","''").toUpperCase() + "%'");
			sqlCriteria = (titolare.getNome() == null  || titolare.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.nome) LIKE '" + titolare.getNome().replace("'","''").toUpperCase() + "%'");
			sqlCriteria = (titolare.getParIva() == null  || titolare.getParIva().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.codiPiva) = '" + titolare.getParIva().toUpperCase() + "'");
			sqlCriteria = (titolare.getCodFis() == null  || titolare.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND UPPER(s.codiFisc) = '" + titolare.getCodFis().toUpperCase() + "'");
			sqlCriteria = (titolare.getFlgTitolareDataDocfa() == null  || titolare.getFlgTitolareDataDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitolareDataDocfa = '" + titolare.getFlgTitolareDataDocfa() + "'");
			sqlCriteria = (titolare.getFlgResidIndCatAllaData() == null  || titolare.getFlgResidIndCatAllaData().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgResidIndCat3112 = '" + titolare.getFlgResidIndCatAllaData() + "'");
			sqlCriteria = (titolare.getFlgResidIndDcfAllaData() == null  || titolare.getFlgResidIndDcfAllaData().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgResidIndDcf3112 = '" + titolare.getFlgResidIndDcfAllaData() + "'");
			sqlCriteria = (titolare.getFlgAnomali() == null  || titolare.getFlgAnomali().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgAnomalia = '" + titolare.getFlgAnomali() + "'");
			sqlCriteria = (titolare.getFlgPresUiuPreDocfa() == null  || titolare.getFlgPresUiuPreDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciUiuAnte = '" + titolare.getFlgPresUiuPreDocfa() + "'");
			sqlCriteria = (titolare.getFlgPresUiuPostDocfa() == null  || titolare.getFlgPresUiuPostDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciUiuPost = '" + titolare.getFlgPresUiuPostDocfa() + "'");
			sqlCriteria = (titolare.getFlgPresCivPreDocfa() == null  || titolare.getFlgPresCivPreDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciCivAnte = '" + titolare.getFlgPresCivPreDocfa() + "'");
			sqlCriteria = (titolare.getFlgPresCivPostDocfa() == null  || titolare.getFlgPresCivPostDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciCivPost = '" + titolare.getFlgPresCivPostDocfa() + "'");
			sqlCriteria = (titolare.getFlgPresUndPreDocfa() == null  || titolare.getFlgPresUndPreDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciAnte = '" + titolare.getFlgPresUndPreDocfa() + "'");
			sqlCriteria = (titolare.getFlgPresUndPostDocfa() == null  || titolare.getFlgPresUndPostDocfa().equals("") ? sqlCriteria : sqlCriteria + " AND s.flgTitIciPost = '" + titolare.getFlgPresUndPostDocfa() + "'");
		}
		
		return sqlCriteria;
	}
	
	
}
