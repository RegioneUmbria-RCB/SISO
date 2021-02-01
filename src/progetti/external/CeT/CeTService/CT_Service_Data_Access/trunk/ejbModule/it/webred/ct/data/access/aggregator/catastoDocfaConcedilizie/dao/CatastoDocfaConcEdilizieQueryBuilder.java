package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dao;

import org.apache.log4j.Logger;

import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto.SearchCriteriaDTO;
import it.webred.ct.data.access.basic.c336.C336PraticaQueryBuilder;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.SearchC336PraticaDTO;
import it.webred.ct.data.access.basic.catasto.dao.CatastoQueryBuilder;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.concedilizie.ConcessioniEdilizieQueryBuilder;
import it.webred.ct.data.access.basic.concedilizie.dto.ConcEdiSearchCriteria;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.docfa.DocfaQueryBuilder;
import it.webred.ct.data.access.basic.docfa.dto.DocfaSearchCriteria;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.fabbricato.FabbricatoQueryBuilder;
import it.webred.ct.data.access.basic.fabbricato.dto.SearchFabbricatoDTO;

public class CatastoDocfaConcEdilizieQueryBuilder {
	
	protected Logger logger = Logger.getLogger("ctservice.log");
	
	private SearchCriteriaDTO criteria;
	
	public CatastoDocfaConcEdilizieQueryBuilder(SearchCriteriaDTO criteria){
		this.criteria = criteria;
		this.criteria.getCatOggetto().setCodNazionale(criteria.getEnteId());
		
	}
	public String createQueryGettingFPS(Boolean isCount){
		logger.info("AMBITI DI RICERCA-CTT: " + criteria.getCatOggetto().getFaiRicercaInCatastoTerreni());
		logger.info("AMBITI DI RICERCA-CTU: " + criteria.getCatOggetto().getFaiRicercaInCatastoUrbano());
		logger.info("AMBITI DI RICERCA-DOCFA: " + criteria.getFaiRicercaInDocfa());
		logger.info("AMBITI DI RICERCA-CE: " + criteria.getFaiRicercaInConEdi());
		logger.info("AMBITI DI RICERCA-FABB-MAIDIC: " + criteria.getFaiRicercaInFabbrMaiDich());
		logger.info("AMBITI DI RICERCA-FABB-EX-RURALI: " + criteria.getFaiRicercaInFabbrExRurali());
		logger.info("AMBITI DI RICERCA-C336 PRATICA: " + criteria.getFaiRicercaInC336Pratica());
		String sql = "";
		String innerSql = "";
		//Estrae la query di ricerca in catasto urbano
		String sqlCatasto = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoUrbano() ) {
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatasto = new CatastoQueryBuilder(csc).createQueryCongiuntaGettingFPS(false);
		}
		//Estrae la query di ricerca in catasto terreni
		String sqlCatastoTerreni = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoTerreni()) {
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatastoTerreni = new CatastoQueryBuilder(csc).createInnerQueryTerreni(true);
		}	
		String sqlDocfa="";
		if (criteria.getFaiRicercaInDocfa()) {
			DocfaSearchCriteria dsc = new DocfaSearchCriteria();
			dsc.setRicercaOggetto(criteria.getDcfOggetto());
			dsc.setRicercaDichiarante(criteria.getDcfDichiarante());
			sqlDocfa = new DocfaQueryBuilder(dsc).createQueryGettingFPS(false);
		}
		//Estrae la query di ricerca nelle concessioni edilizie
		String sqlConcEdi="";
		if (criteria.getFaiRicercaInConEdi() ) {
			ConcEdiSearchCriteria cesc = new ConcEdiSearchCriteria();
			cesc.setRicercaOggetto(criteria.getConcEdiOggetto());
			cesc.setRicercaSoggetto(criteria.getConcEdiSoggetto());
			sqlConcEdi = new ConcessioniEdilizieQueryBuilder(cesc).createQueryGettingFPS(false);	
		}
		//Query di ricerca nei fabbricati ex-rurali
		String sqlFabbrExRurale ="";
		if (criteria.getFaiRicercaInFabbrExRurali() ) {
			SearchFabbricatoDTO crt = new SearchFabbricatoDTO();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			crt.setExRurale(true);
			FabbricatoQueryBuilder fq= new FabbricatoQueryBuilder(crt);
			sqlFabbrExRurale = fq.createQuery(false);
		}
		String sqlPratica="";
		String sqlPraticaE="";
		String sqlPraticaNE="";
		if (criteria.getFaiRicercaInC336Pratica() ) {
			logger.info("criteria.getC336Pratica().getCodStato(): " + criteria.getC336Pratica().getCodStato());
			SearchC336PraticaDTO crt = criteria.getC336Pratica();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			C336PraticaQueryBuilder pq= new C336PraticaQueryBuilder(crt);
			if (!criteria.getC336Pratica().getCodStato().equals(C336PraticaDTO.COD_STATO_NON_ESAMINATA)) {
				//sqlPratica = pq.createQuerySelectFP(false);
				sqlPraticaE=pq.createQueryPerExistsSub("X.SEZIONE", "X.FOGLIO", "X.PARTICELLA", "X.SUBALTERNO");
			}
			else
				sqlPraticaNE = pq.createQueryPerExistsSub("X.SEZIONE", "X.FOGLIO", "X.PARTICELLA", "X.SUBALTERNO");
		}
		
		innerSql = sqlCatasto!=null && sqlCatasto.length()> 0 ? innerSql + "(" + sqlCatasto +")" : innerSql;
		
		if(innerSql.length()>0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlCatastoTerreni +")";
		else if(innerSql.length()==0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + "(" + sqlCatastoTerreni +")";
		
		if(innerSql.length()>0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlDocfa +")";
		else if(innerSql.length()==0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + "(" + sqlDocfa +")";
		
		if(innerSql.length()>0 && (sqlConcEdi!=null && sqlConcEdi.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlConcEdi +")";
		else if(innerSql.length()==0 && (sqlConcEdi!=null && sqlConcEdi.length()> 0))
			innerSql =  innerSql + "(" + sqlConcEdi +")";
		
		if(innerSql.length()>0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlFabbrExRurale +")";
		else if(innerSql.length()==0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + "(" + sqlFabbrExRurale +")";
		
		if(innerSql.length()>0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlPratica +")";
		else if(innerSql.length()==0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + "(" + sqlPratica +")";
	
		if(innerSql.length()>0){
			if(!isCount)
				sql = "SELECT DISTINCT sezione, foglio, particella, subalterno FROM ( ";
			else
				sql = "SELECT DISTINCT COUNT(*) FROM ( ";
			
			sql += innerSql + ")X WHERE 1=1 ";
			
			/*if(criteria.getTipoRicerca() ==null || criteria.getTipoRicerca().equals(""))
				sql += "AND X.subalterno <> '0000' ";
				*/
			
		}
		
		if(sql.length()>0 && (sqlPraticaE!=null && sqlPraticaE.length()> 0))
			sql = sql + " AND EXISTS " + "(" + sqlPraticaE +")";
		if(sql.length()>0 && (sqlPraticaNE!=null && sqlPraticaNE.length()> 0))
			sql = sql + " AND NOT EXISTS " + "(" + sqlPraticaNE +")";
		
	
		String orderBy ="ORDER BY sezione, foglio, particella, subalterno";
		if(sql.length()>0 && !isCount)
			sql += orderBy;
		
		return sql;
	}
	public String createQueryGettingFPS_OLD(Boolean isCount){
		String sql = null;
		String innerSql = "";
		
		//Estrae la query di ricerca in catasto
		CatastoSearchCriteria csc = criteria.getCatOggetto();
		String sqlCatasto = new CatastoQueryBuilder(csc).createQueryCongiuntaGettingFPS(false);
		
		//Estrae la query di ricerca nei docfa
		DocfaSearchCriteria dsc = new DocfaSearchCriteria();
		dsc.setRicercaOggetto(criteria.getDcfOggetto());
		dsc.setRicercaDichiarante(criteria.getDcfDichiarante());
		String sqlDocfa = new DocfaQueryBuilder(dsc).createQueryGettingFPS(false);
		
		//Estrae la query di ricerca nelle concessioni edilizie
		ConcEdiSearchCriteria cesc = new ConcEdiSearchCriteria();
		cesc.setRicercaOggetto(criteria.getConcEdiOggetto());
		cesc.setRicercaSoggetto(criteria.getConcEdiSoggetto());
		String sqlConcEdi = new ConcessioniEdilizieQueryBuilder(cesc).createQueryGettingFPS(false);
		
		innerSql = sqlCatasto!=null && sqlCatasto.length()> 0 ? innerSql + "(" + sqlCatasto +")" : innerSql;
		
		if(innerSql.length()>0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlDocfa +")";
		else if(innerSql.length()==0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + "(" + sqlDocfa +")";
		
		if(innerSql.length()>0 && (sqlConcEdi!=null && sqlConcEdi.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlConcEdi +")";
		else if(innerSql.length()==0 && (sqlConcEdi!=null && sqlConcEdi.length()> 0))
			innerSql =  innerSql + "(" + sqlConcEdi +")";
		
		if(innerSql.length()>0 && !isCount){
			sql = "SELECT DISTINCT sezione, foglio, particella, subalterno FROM ( ";
			sql += innerSql + ") ORDER BY sezione, foglio, particella, subalterno";
		}
		
		if(innerSql.length()>0 && isCount){
			sql = "SELECT DISTINCT COUNT(*) FROM ( ";
			sql += innerSql + ")";
		}
		
		return sql;
	}
	public String createQueryGettingParticelle(Boolean isCount){
		logger.info("AMBITI DI RICERCA-CTT: " + criteria.getCatOggetto().getFaiRicercaInCatastoTerreni());
		logger.info("AMBITI DI RICERCA-CTU: " + criteria.getCatOggetto().getFaiRicercaInCatastoUrbano());
		logger.info("AMBITI DI RICERCA-DOCFA: " + criteria.getFaiRicercaInDocfa());
		logger.info("AMBITI DI RICERCA-CE: " + criteria.getFaiRicercaInConEdi());
		logger.info("AMBITI DI RICERCA-FABB-MAIDIC: " + criteria.getFaiRicercaInFabbrMaiDich());
		logger.info("AMBITI DI RICERCA-FABB-EX-RURALI: " + criteria.getFaiRicercaInFabbrExRurali());
		logger.info("AMBITI DI RICERCA-C336 PRATICA: " + criteria.getFaiRicercaInC336Pratica());
		String sql = null;
		String innerSql = "";
		//Estrae la query di ricerca in catasto urbano
		String sqlCatasto = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoUrbano() ) {
			logger.info("ricerca in catasto urbano");
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatasto = new CatastoQueryBuilder(csc).createQueryParticelleGenerale(false);
		}
		logger.info("sqlCatasto " + sqlCatasto);
		//Estrae la query di ricerca in catasto terreni
		String sqlCatastoTerreni = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoTerreni()) {
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatastoTerreni = new CatastoQueryBuilder(csc).createInnerQueryTerreni(false);
		}	
		
		//Estrae la query di ricerca nei docfa 
		String sqlDocfa ="";
		if (criteria.getFaiRicercaInDocfa() ) {
			DocfaSearchCriteria dsc = new DocfaSearchCriteria();
			dsc.setRicercaOggetto(criteria.getDcfOggetto());
			sqlDocfa = new DocfaQueryBuilder(dsc).createQueryGettingParticella(false);
		}
		
		//Query di ricerca nei fabbricati ex-rurali
		String sqlFabbrExRurale ="";
		if (criteria.getFaiRicercaInFabbrExRurali() ) {
			SearchFabbricatoDTO crt = new SearchFabbricatoDTO();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			crt.setExRurale(true);
			FabbricatoQueryBuilder fq= new FabbricatoQueryBuilder(crt);
			sqlFabbrExRurale = fq.createQuery(false);
		}
				
		String sqlFabbrMaiDic="";
		if (criteria.getFaiRicercaInFabbrMaiDich()) {
			SearchFabbricatoDTO crt = new SearchFabbricatoDTO();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			crt.setMaiDichiarato(true);
			FabbricatoQueryBuilder fq= new FabbricatoQueryBuilder(crt);
			sqlFabbrMaiDic = fq.createQuery(false);
		}
		
		String sqlPratica="";
		String sqlPraticaE="";
		String sqlPraticaNE="";
		if (criteria.getFaiRicercaInC336Pratica() ) {
			logger.info("criteria.getC336Pratica().getCodStato(): " + criteria.getC336Pratica().getCodStato());
			SearchC336PraticaDTO crt = criteria.getC336Pratica();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			C336PraticaQueryBuilder pq= new C336PraticaQueryBuilder(crt);
			if (!criteria.getC336Pratica().getCodStato().equals(C336PraticaDTO.COD_STATO_NON_ESAMINATA)) {
				//sqlPratica = pq.createQuerySelectFP(false);
				sqlPraticaE = pq.createQueryPerExistsParticella("X.SEZIONE", "X.FOGLIO", "X.PARTICELLA");
			}
			else
				sqlPraticaNE = pq.createQueryPerExistsParticella("X.SEZIONE", "X.FOGLIO", "X.PARTICELLA");
		}
		
		innerSql = sqlCatasto!=null && sqlCatasto.length()> 0 ? innerSql + "(" + sqlCatasto +")" : innerSql;
		logger.info("innerSql " + innerSql);
		if(innerSql.length()>0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlCatastoTerreni +")";
		else if(innerSql.length()==0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + "(" + sqlCatastoTerreni +")";
		
		if(innerSql.length()>0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlDocfa +")";
		else if(innerSql.length()==0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + "(" + sqlDocfa +")";
		
		if(innerSql.length()>0 && (sqlFabbrMaiDic!=null && sqlFabbrMaiDic.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlFabbrMaiDic +")";
		else if(innerSql.length()==0 && (sqlFabbrMaiDic!=null && sqlFabbrMaiDic.length()> 0))
			innerSql =  innerSql + "(" + sqlFabbrMaiDic +")";
		
		if(innerSql.length()>0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlFabbrExRurale +")";
		else if(innerSql.length()==0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + "(" + sqlFabbrExRurale +")";
		
		if(innerSql.length()>0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlPratica +")";
		else if(innerSql.length()==0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + "(" + sqlPratica +")";
	
		if(innerSql.length()>0 && !isCount){
			sql = "SELECT DISTINCT sezione, foglio, particella FROM ( ";
			sql += innerSql + ")";
		}
		if(innerSql.length()>0 && isCount){
			sql = "SELECT DISTINCT COUNT(*) FROM ( ";
			sql += innerSql + ")";
		}
		String orderBy ="ORDER BY sezione, foglio, particella";
		logger.info("--->sql " + sql);
		if(sql.length()>0 && (sqlPraticaE!=null && sqlPraticaE.length()> 0))
			sql = sql + " X WHERE EXISTS " + "(" + sqlPraticaE +")";
		if(sql.length()>0 && (sqlPraticaNE!=null && sqlPraticaNE.length()> 0))
			sql = sql + " X WHERE NOT EXISTS " + "(" + sqlPraticaNE +")";
		
		sql += orderBy;
		
		return sql;
	}
	/*
	public String createQueryGettingUIU(Boolean isCount){
		String sql = null;
		String innerSql = "";
		//Estrae la query di ricerca in catasto urbano
		String sqlCatasto = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoUrbano() ) {
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatasto = new CatastoQueryBuilder(csc).createQueryCongiuntaGettingFPS(false);
		}
		//Estrae la query di ricerca in catasto terreni
		String sqlCatastoTerreni = "";
		if (criteria.getCatOggetto().getFaiRicercaInCatastoTerreni()) {
			CatastoSearchCriteria csc = criteria.getCatOggetto();
			sqlCatastoTerreni = new CatastoQueryBuilder(csc).createInnerQueryTerreni(true);
		}	
		
		//Estrae la query di ricerca nei docfa 
		String sqlDocfa ="";
		if (criteria.getDcfOggetto() != null &&
			(criteria.getDcfOggetto().getDataRegistrazioneDa()!= null && !criteria.getDcfOggetto().getDataRegistrazioneA().equals("")) ||
			(criteria.getDcfOggetto().getDataRegistrazioneA() !=null && !criteria.getDcfOggetto().getDataRegistrazioneA().equals("") )||
   			(criteria.getDcfOggetto().getFornituraDa() != null && !criteria.getDcfOggetto().getFornituraDa().equals("") )|| 
   			(criteria.getDcfOggetto().getFornituraA() !=null && !criteria.getDcfOggetto().getFornituraA().equals("") ) ||
			(criteria.getDcfOggetto().getProtocollo() != null && !criteria.getDcfOggetto().getProtocollo().equals("")) ) {
			DocfaSearchCriteria dsc = new DocfaSearchCriteria();
			dsc.setRicercaOggetto(criteria.getDcfOggetto());
			sqlDocfa = new DocfaQueryBuilder(dsc).createQueryGettingFPS(false);
		}
				
		String sqlFabbrExRurale ="";
		if (criteria.getFaiRicercaInFabbrExRurali() ) {
			SearchFabbricatoDTO crt = new SearchFabbricatoDTO();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			crt.setExRurale(true);
			FabbricatoQueryBuilder fq= new FabbricatoQueryBuilder(crt);
			sqlFabbrExRurale = fq.createQuery(false);
		}
		
		String sqlPratica="";
		String sqlPraticaNE="";
		if (criteria.getC336Pratica() != null &&
			(criteria.getC336Pratica().getCodStato() != null && ! criteria.getC336Pratica().getCodStato().equals(""))) {
			logger.info("criteria.getC336Pratica().getCodStato(): " + criteria.getC336Pratica().getCodStato());
			SearchC336PraticaDTO crt = criteria.getC336Pratica();
			crt.setFoglio(criteria.getCatOggetto().getFoglio());
			crt.setParticella(criteria.getCatOggetto().getParticella());
			C336PraticaQueryBuilder pq= new C336PraticaQueryBuilder(crt);
			if (!criteria.getC336Pratica().getCodStato().equals(C336PraticaDTO.COD_STATO_NON_ESAMINATA)) {
				sqlPratica = pq.createQuerySelectFPS(false);
			}
			else
				sqlPraticaNE = pq.createQueryPerExistsSub("SEZIONE", "FOGLIO", "PARTICELLA", "SUB");
		}
		
		innerSql = sqlCatasto!=null && sqlCatasto.length()> 0 ? innerSql + "(" + sqlCatasto +")" : innerSql;
		if(innerSql.length()>0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlCatastoTerreni +")";
		else if(innerSql.length()==0 && (sqlCatastoTerreni!=null && sqlCatastoTerreni.length()> 0))
			innerSql =  innerSql + "(" + sqlCatastoTerreni +")";
		
		if(innerSql.length()>0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlDocfa +")";
		else if(innerSql.length()==0 && (sqlDocfa!=null && sqlDocfa.length()> 0))
			innerSql =  innerSql + "(" + sqlDocfa +")";
		
		if(innerSql.length()>0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlFabbrExRurale +")";
		else if(innerSql.length()==0 && (sqlFabbrExRurale!=null && sqlFabbrExRurale.length()> 0))
			innerSql =  innerSql + "(" + sqlFabbrExRurale +")";
		
		if(innerSql.length()>0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + " UNION " + "(" + sqlPratica +")";
		else if(innerSql.length()==0 && (sqlPratica!=null && sqlPratica.length()> 0))
			innerSql =  innerSql + "(" + sqlPratica +")";
	
		if(innerSql.length()>0 && !isCount){
			sql = "SELECT DISTINCT sezione, foglio, to_char(particella) as particella, sub FROM ( ";
			sql += innerSql + ")";
		}
		if(innerSql.length()>0 && isCount){
			sql = "SELECT DISTINCT COUNT(*) FROM ( ";
			sql += innerSql + ")";
		}
		String orderBy ="ORDER BY sezione, foglio, particella, SUB";
		
		if(sql.length()>0 && (sqlPraticaNE!=null && sqlPraticaNE.length()> 0))
			sql = sql + " WHERE NOT EXISTS " + "(" + sqlPraticaNE +")";
		
		sql += orderBy;
		
		return sql;
	}
	*/
}
