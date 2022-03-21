package it.webred.ct.data.access.basic.concedilizie.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.concedilizie.ConcessioniEdilizieException;
import it.webred.ct.data.access.basic.concedilizie.ConcessioniEdilizieQueryBuilder;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.SoggettoConcessioneDTO;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisure;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisureDoc;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioni;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ConcessioniEdilizieJPAImpl extends CTServiceBaseDAO implements ConcessioniEdilizieDAO {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<String> getListaProgressivoAnno() {
		List<String> lista = new ArrayList<String>();

		try {
			Query q = manager_diogene.createNamedQuery("SitCConcessioni.getListaProgressivoAnno");
			lista = q.getResultList();

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}

		return lista;
	}
	
	@Override
	public List<String> getListaProtocolloAnno(){
		List<String> lista = new ArrayList<String>();
		
		try {
			Query q = manager_diogene.createNamedQuery("SitCConcessioni.getListaProtocolloAnno");
			lista = q.getResultList();

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		
		return lista;
		
	}
	
	@Override
	public List<String> getListaTitoliSoggetto(){
		List<String> lista = new ArrayList<String>();
		
		try {
			Query q = manager_diogene.createNamedQuery("SitCConcPersona.getListaTitoloSoggetto");
			lista = q.getResultList();

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		
		return lista;
	}
	
	@Override
	public List<String> getSuggestionVie(RicercaCivicoDTO rc){
		
		try {
				Date dtRif = rc.getDataRif();
				if(dtRif==null)
					dtRif = new Date();
				
				String descVia = rc.getDescrizioneVia().toString().toUpperCase();
				
				logger.debug("Ricerca Vie Conc.Edilizie " +
						"[descVia:"+descVia+"];" +
						"[dtRif:"+dtRif+"]");
				
				Query q = manager_diogene.createNamedQuery("SitCConcIndirizzi.getListaVieLike");
				q.setParameter("dtRif", dtRif);
				q.setParameter("via", descVia);
				q.setMaxResults(10);
				return  q.getResultList();
				
		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
	
	}
	
	@Override
	public List<String> getSuggestionCiviciByVia(RicercaCivicoDTO rc){
		List<String> civici = new ArrayList<String>();
		try {

			Date dtRif = rc.getDataRif();
			if(dtRif==null)
				dtRif = new Date();
			String descVia = rc.getDescrizioneVia().toString().toUpperCase();
			logger.debug("Ricerca Civici Conc.Edilizie " +
							"[descVia:"+descVia+"];" +
							"[dtRif:"+dtRif+"]");
			
			Query q = manager_diogene.createNamedQuery("SitCConcIndirizzi.getListaCiviciByVia");
			q.setParameter("dtRif", dtRif);
			q.setParameter("via", descVia);
			q.setMaxResults(10);
			List<Object> result =  q.getResultList();
			for(Object obj : result)
				civici.add(obj.toString());
			
		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		return civici;
	}
	
	@Override
	public List<String> getSuggestionSoggetti(String denominazione){
		List<String> soggetti = new ArrayList<String> ();
		try {
			
			logger.debug("Ricerca Soggetti Conc.Edilizie [param:"+denominazione+"]");
			Query q = manager_diogene.createNamedQuery("SitCConcPersona.getListaSoggettiByCognomeNomeDenom");
			q.setParameter("denominazione", denominazione.toUpperCase());
			q.setMaxResults(10);
			List<Object[]> result =  q.getResultList();
			logger.debug("Result Size ["+result.size()+"]");
			
			for(Object[] res : result){
				String denom = "";
				if(res[2]!= null)
					denom = res[2].toString().toUpperCase();
				else
					denom = res[0].toString().trim().toUpperCase() + " " + res[1].toString().trim().toUpperCase();
				soggetti.add(denom);
			}
			
		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		return soggetti;
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByUiu(RicercaConcEdilizieDTO rce) throws ConcessioniEdilizieException {
		List<SitCConcessioni>  lista = new ArrayList<SitCConcessioni> ();
		
		String foglio     = rce.getFoglio().trim();
		String particella = rce.getParticella().trim();
		String unimm      = "0";
		if(rce.getSub()!=null && rce.getSub().trim().length()>0 )
			unimm = rce.getSub().trim();
		
		logger.debug("ConcessioniEdilizieJPAImpl.getConcessioniByUiu() " +
						"[foglio: "+foglio+"];" +
						"[particella: "+particella+"];" +
						"[subalterno: "+unimm+"];");
		try {
			Query q= null;
			String sezione = rce.getSezione();
			if (sezione==null) {
				q=manager_diogene.createNamedQuery("Join_SitCConcessioni_SitCConcessioniCatasto.getConcessioneByFPS");
			}
			else {
				q=manager_diogene.createNamedQuery("Join_SitCConcessioni_SitCConcessioniCatasto.getConcessioneBySezFPS");
				q.setParameter("sezione",sezione.trim());
			}
			q.setParameter("foglio",foglio);
			q.setParameter("particella",particella);
			q.setParameter("subalterno",unimm);
			//q.setParameter("tipo", "URBANO");
			lista = (List<SitCConcessioni> ) q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return lista;
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByFabbricato(RicercaOggettoCatDTO ro) throws ConcessioniEdilizieException {
		List<SitCConcessioni>  lista = new ArrayList<SitCConcessioni> ();
		logger.debug("ConcessioniEdilizieJPAImpl.getConcessioniByFabbricato() [foglio: "+ro.getFoglio()+"];[particella: "+ro.getParticella()+"];");
		try {
			Query q= null;
			String sezione = ro.getSezione();
			if (sezione==null) {
				q=manager_diogene.createNamedQuery("Join_SitCConcessioni_SitCConcessioniCatasto.getConcessioneByFP");
			}
			else {
				q=manager_diogene.createNamedQuery("Join_SitCConcessioni_SitCConcessioniCatasto.getConcessioneBySezFP");
				q.setParameter("sezione",sezione.trim());
			}
			q.setParameter("foglio",ro.getFoglio().trim());
			q.setParameter("particella",ro.getParticella().trim());
			lista = (List<SitCConcessioni> ) q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return lista;
	}

	@Override
	public SitCConcessioni getConcessioneById(RicercaConcEdilizieDTO ro)	throws ConcessioniEdilizieException {
		SitCConcessioni conc = new SitCConcessioni();
		logger.debug("ConcessioniEdilizieJPAImpl.getConcessioneById() [id: "+ro.getIdConc()+"]");
		try {
			Query q= null;
			q=manager_diogene.createNamedQuery("SitCConcessioni.getConcessioneById");
			q.setParameter("id",ro.getIdConc());
			conc = (SitCConcessioni ) q.getSingleResult();
		}
		catch (NoResultException nre) {
			logger.debug("NON TROVATE CONCESSIONI PER ID: " + ro.getIdConc(), nre);
		}catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return conc;
	}

	@Override
	public SitCConcessioni getConcessioneByIdExt(RicercaConcEdilizieDTO ro)	throws ConcessioniEdilizieException {
		SitCConcessioni conc = new SitCConcessioni();
		logger.debug("ConcessioniEdilizieJPAImpl.getConcessioneByIdExt() [id: "+ro.getIdExtConc()+"]");
		try {
			Query q= null;
			q=manager_diogene.createNamedQuery("SitCConcessioni.getConcessioneByIdExt");
			q.setParameter("idExt",ro.getIdExtConc());
			conc = (SitCConcessioni ) q.getSingleResult();
		}
		catch (NoResultException nre) {
			logger.debug("NON TROVATE CONCESSIONI PER ID_EXT: " + ro.getIdExtConc(), nre);
			throw new ConcessioniEdilizieException (nre);
		}catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return conc;
	}

	
	@Override
	public List<SoggettoConcessioneDTO> getSoggettiByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException {
		List<SoggettoConcessioneDTO> lista = new ArrayList<SoggettoConcessioneDTO>();
		logger.debug("ConcessioniEdilizieJPAImpl.getSoggettiByConcessione()- [id: "+ro.getIdConc()+"];[idExt: "+ro.getIdExtConc());
		try {
			Query q =null;
			String idExtConc = ro.getIdExtConc();
			if (idExtConc==null || idExtConc.equals("")) {
				SitCConcessioni conc = getConcessioneById(ro);
				if (conc!=null)
					idExtConc = conc.getIdExt();
				else 
					return lista;
			}
			String sql=	(new ConcessioniEdilizieQueryBuilder()).getSQL_SOGGETTI_CONCESSIONE();
			q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,idExtConc);
			Date dtFinVal = new Date(); 
			q.setParameter(2,dtFinVal);
			q.setParameter(3,dtFinVal);
			List<Object[]> result= q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");
			for (Object[] rs : result) {
				SoggettoConcessioneDTO sogg = new SoggettoConcessioneDTO();
				sogg.setTitolo((String)rs[0]);
				String datiAna = "";
				String tipoPersona = (String)rs[1];
				String tipoSoggetto = (String)rs[5];
				String denom = (String)rs[4];
				String ragSoc = (String)rs[6];
				if (tipoPersona !=null && tipoPersona.equals("F")){
					String cognome = (String)rs[2];
					String nome = (String)rs[3];
					datiAna= (cognome!=null ? cognome.toUpperCase() : "") + " " + (nome!=null ? nome.toUpperCase() : "") ;
					if(datiAna.trim().equals(""))
						datiAna= (denom!=null ? denom.toUpperCase() : "");
				}else {
					datiAna= (denom!=null ? denom.toUpperCase() : "");
					if(tipoSoggetto!=null && tipoSoggetto.equals("A")||(tipoSoggetto==null && ragSoc!=null)) {
						if (ragSoc!=null)
							datiAna=ragSoc.toUpperCase();
					}
					
					datiAna = datiAna.trim();
				}
				sogg.setDatiAnag(datiAna);
				lista.add(sogg);
			}
		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return lista;
	}
	
	
	@Override
	public List<SitCConcIndirizzi> getIndirizziByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException {
		List<SitCConcIndirizzi> lista = new ArrayList<SitCConcIndirizzi>();
		logger.debug("ConcessioniEdilizieJPAImpl.getIndirizziByConcessione()- [id: "+ro.getIdConc()+"];[idExt: "+ro.getIdExtConc());
		try {
			Query q =null;
			String idExtConc = ro.getIdExtConc();
			if (idExtConc==null || idExtConc.equals("")) {
				SitCConcessioni conc = getConcessioneById(ro);
				if (conc!=null)
					idExtConc = conc.getIdExt();
				else 
					return lista;
			}
		
			q=manager_diogene.createNamedQuery("SitCConcIndirizzi.getListaIndirizziByIdExtConc");
			q.setParameter("idExtConcessione",idExtConc);
			Date dtFinVal = new Date(); 
			q.setParameter("dataRif",dtFinVal);
	
			lista = q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return lista;
	}

	@Override
	public String getStringaImmobiliByConcessione(RicercaConcEdilizieDTO ro)	throws ConcessioniEdilizieException {
		String strImm="";
		try {
			String sql=	(new ConcessioniEdilizieQueryBuilder()).getSQL_OGGETTI_CONCESSIONE();
			logger.debug("ConcessioniEdilizieJPAImpl.getStringaImmobiliByConcessione - [id: "+ro.getIdConc()+"];[idExt: "+ro.getIdExtConc());
			Query q =null;
			String idExtConc = ro.getIdExtConc();
			if (idExtConc==null || idExtConc.equals("")) {
				SitCConcessioni conc = getConcessioneById(ro);
				if (conc!=null)
					idExtConc = conc.getIdExt();
				else 
					return strImm;
			}
			q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,idExtConc);
			Date dtFinVal = new Date(); 
			q.setParameter(2,dtFinVal);
			q.setParameter(3,dtFinVal);
			List<Object[]> result= q.getResultList();
			for (Object[] rs : result) {
				if (!strImm.equals(""))
					strImm+="; ";
				String sez= (String)rs[0];
				String foglio= (String)rs[1];
				String part= (String)rs[2];
				String sub= (String)rs[3];
				strImm +=  sez!= null && !sez.equals("")? sez + "/" : "";
				strImm +=  foglio + "/";
				strImm +=  part;  
				strImm +=  sub!= null && !sub.equals("")? "/" + sub : ""; 
			}
			logger.debug("ConcessioniEdilizieJPAImpl.getStringaImmobiliByConcessione. Stringa Imm: "+ strImm);
		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return strImm;
	}
	
	@Override
	public List<SitCConcessioniCatasto> getListaImmobiliByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException {
		
		List<SitCConcessioniCatasto> listaImm = new ArrayList<SitCConcessioniCatasto>();
		
		try { 
			String sql=	(new ConcessioniEdilizieQueryBuilder()).getSQL_OGGETTI_CONCESSIONE();
			logger.debug("ConcessioniEdilizieJPAImpl.getListaImmobiliByConcessione - [id: "+ro.getIdConc()+"];[idExt: "+ro.getIdExtConc());
			Query q =null;
			String idExtConc = ro.getIdExtConc();
			if (idExtConc==null || idExtConc.equals("")) {
				SitCConcessioni conc = getConcessioneById(ro);
				if (conc!=null)
					idExtConc = conc.getIdExt();
				else 
					return listaImm;
			}
			
			q = manager_diogene.createNamedQuery("SitCConcessioniCatasto.getListaImmobiliByIdExtConc");
			q.setParameter("idExtConcessione",idExtConc);
			Date dtFinVal = new Date(); 
			q.setParameter("dataRif",dtFinVal);
			listaImm = q.getResultList();
			
			logger.debug("ConcessioniEdilizieJPAImpl.getListaImmobiliByConcessione. Result Size["+listaImm.size()+"]");
		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		return listaImm;
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniSuCiviciCatasto(RicercaCivicoDTO rc){
		List<SitCConcessioni> lista = new ArrayList<SitCConcessioni>();
		
		logger.debug("ConcessioniEdilizieJPAImpl.getConcessioniByCiviciCatasto() [listaCivici: "+rc.getListaIdCivici()+"]");
		try {
			Query q= null;
			
			q=manager_diogene.createNamedQuery("Join_SitCConcessioni_SitCivicoTotale.getConcessioniSuCiviciCatasto");
			q.setParameter("civiciCatasto",rc.getListaIdCivici());
			
			lista = (List<SitCConcessioni> ) q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new ConcessioniEdilizieException (t);
		}
		
		return lista;	
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByIndirizziByFoglioParticella(String foglio, String particella) {
		List<SitCConcessioni> lista = new ArrayList<SitCConcessioni>();

		try {
			Query q = manager_diogene.createNamedQuery("SitCConcessioni.getConcessioniByIndirizziFP");
			q.setParameter("foglio", new Long(foglio));
			q.setParameter("particella", particella);
			lista = q.getResultList();

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}

		return lista;
	}

	@Override
	public ConcEdilizieVisure getVisuraById(String id) {
		
		logger.debug("ConcessioniEdilizieJPAImpl.getVisuraById()  ["+id+"]");
		try {
			Query q = manager_diogene.createNamedQuery("ConcEdilizieVisure.getVisuraById");
			
			q.setParameter("id", id);
			
			List<ConcEdilizieVisure>  lista = q.getResultList();
			
			if(lista.size()>0)
				return (ConcEdilizieVisure)lista.get(0);
			
			logger.debug("ConcessioniEdilizieJPAImpl.getVisuraById() [result: "+lista.size()+"]");

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		 return null;
	}
	
	@Override
	public ConcEdilizieVisureDoc getDocVisuraById(BigDecimal id) {
		
		logger.debug("ConcessioniEdilizieJPAImpl.getDocVisuraById() [id:"+id+"]");
		try{
			Query q = manager_diogene.createNamedQuery("ConcEdilizieVisure.getDocVisuraById");
			q.setParameter("inxdoc", id.toString());
			return (ConcEdilizieVisureDoc) q.getSingleResult();
			
		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
	}
	
	@Override
	public List<String> getVisureTipiAtto(){
		
		List<String> lst = new ArrayList<String>();
		
		logger.debug("ConcessioniEdilizieJPAImpl.getVisureTipiAtto()");
		try{
			Query q = manager_diogene.createNamedQuery("ConcEdilizieVisure.getVisureTipiAtto");
			
			lst = (List<String>) q.getResultList();
			
		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		
		return lst;
	}
	
	@Override
	public List<ConcEdilizieVisure> getVisureByListaId(RicercaCivicoDTO rc) {
		List<ConcEdilizieVisure> lista = new ArrayList<ConcEdilizieVisure>();
		logger.debug("ConcessioniEdilizieJPAImpl.getVisureByListaId() [listaCivici: "+rc.getListaId()+"]");
		try {
			Query q = manager_diogene.createNamedQuery("ConcEdilizieVisure.getDatiByListaId");
			q.setParameter("listaId", rc.getListaId());
			lista = q.getResultList();
			
			logger.debug("ConcessioniEdilizieJPAImpl.getVisureByListaId() [result: "+lista.size()+"]");

		} catch (Throwable t) {
			throw new ConcessioniEdilizieException(t);
		}
		
		return lista;
	}
	
}
