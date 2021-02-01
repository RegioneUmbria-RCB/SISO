package it.webred.ct.data.access.basic.anagrafe.dao;
 
import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.anagrafe.AnagGenQueryBuilder;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeException;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.AttrPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComuneProvinciaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiCivicoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaIndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaLuogoDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.common.dto.StringheVie;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitDPersonaCivico;
import it.webred.ct.data.model.anagrafe.SitDStaciv;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.data.model.anagrafe.SitStato;
import it.webred.ct.data.model.common.SitEnte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
   
public class AnagrafeJPAImpl extends CTServiceBaseDAO implements AnagrafeDAO  {
	
	SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public SitDPersona getPersonaById(RicercaSoggettoAnagrafeDTO rs) {
		SitDPersona sogg = null;
		logger.debug("AnagrafeJPAImpl.getPersonaById: " + rs.getIdVarSogg());
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.getSoggById");
			q.setParameter("id", rs.getIdVarSogg());
			sogg = (SitDPersona)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("AnagrafeJPAImpl.getSoggettoById - No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return sogg;
	}
	
	@Override
	public List<SitDPersona> getListaPersoneByCF(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		String codFis = rs.getCodFis();
		logger.debug("AnagrafeJPAImpl.getListaPersoneByCF()" +	"[CODFISC: "+codFis+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersona.getListaSoggByCF");
			q.setParameter("codFisc", codFis);
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("AnagrafeJPAImpl.getListaPersoneByCF() - TROVATO. N.ELE: "+ listaPersone.size());
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}

	@Override
	public List<SitDPersona> getListaPersoneByCFAllaData(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getListaPersoneByCFAllaData()" +	"[CODFISC: "+rs.getCodFis()+"];[DATA:" + rs.getDtRif());
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersona.getListaSoggByCFAllaData");
			q.setParameter("codFisc", rs.getCodFis());
			q.setParameter("dtVal", rs.getDtRif());
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ listaPersone.size());
		
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}

	@Override
	public List<SitDPersona> getListaPersoneByDatiAna(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException {
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getListaPersoneByDatiAna " +"[COGNOME: "+rs.getCognome()+"];" +
				"[NOME: "+rs.getNome()+"];" +"[DATA_NASCITA: "+rs.getDtNas()+"];[ID_EXT_COMUNE_NASCITA: "+rs.getCodComNas()+"]");
		try {
			Query q =null;
			if (rs.getCodComNas() != null && !rs.getCodComNas().equals("")) {	
				q= manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDatiAnaCompleti");
				q.setParameter("dtNas", rs.getDtNas());
			} else if (rs.getDtNas() != null) {
				q=manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDatiAna");
				q.setParameter("dtNas", rs.getDtNas());
			} else {
				q=manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDatiAnaNoDtNas");
			}
			q.setParameter("cognome", rs.getCognome());
			q.setParameter("nome", rs.getNome());

			if (rs.getCodComNas() != null && !rs.getCodComNas().equals("")) 	 
				q.setParameter("idExtComNas", rs.getCodComNas());
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ listaPersone.size());
	
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}

	@Override
	public List<SitDPersona> getListaPersoneByDatiAnaAllaData(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getListaPersoneByDatiAnaAllaData " +
				"[COGNOME: "+rs.getCognome()+"];" +	"[NOME: "+rs.getNome()+"];" +"[DATA_NASCITA: "+rs.getDtNas()+
				"];[ID_EXT_COMUNE_NASCITA: "+rs.getCodComNas()+"];[DATA: "+rs.getDtRif()+"];");
		try {
			Query q =null;
			if (rs.getCodComNas() != null && rs.getCodComNas().equals("")) 	
				q= manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDatiAnaCompletiAllaData");
			else
				q=manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDatiAnaAllaData");
			q.setParameter("cognome", rs.getCognome());
			q.setParameter("nome", rs.getNome());
			q.setParameter("dtNas", rs.getDtNas());
			if (rs.getCodComNas() != null && rs.getCodComNas().equals("")) 	 
				q.setParameter("idExtComNas", rs.getCodComNas());
			q.setParameter("dtVal", rs.getDtRif());
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ listaPersone.size());
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}
	
	@Override
	public List<SitDPersona> getListaPersoneByDenominazione(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getListaPersoneByDenominazione " +
				"[DENOMINAZIONE: "+rs.getDenom()+"]");
		try {
			Query q=manager_diogene.createNamedQuery("SitDPersona.getListaSoggByDenominazione");
			q.setParameter("denominazione", rs.getDenom());
			if(rs.getMaxResult() != null)
				q.setMaxResults(rs.getMaxResult());
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ listaPersone.size());
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}
	
	@Override
	public List<SitDPersona> getVariazioniPersonaByIdExt(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersona> listaPersone= new ArrayList<SitDPersona>();
		String idExt = rs.getIdSogg();
		logger.debug("DATI SIT_D_PERSONA PER ID_EXT" +
				"[ID_EXT: "+idExt+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersona.getListaSoggByIdExt");
			q.setParameter("idExt", idExt);
			listaPersone =(List<SitDPersona>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ listaPersone.size());
	
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}	
		return listaPersone;
	}
	
	@Override
	public List<SitDPersonaCivico> getListaPersCivByIdExt(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SitDPersonaCivico> listaPersCiv= new ArrayList<SitDPersonaCivico>();
		String idExt = rs.getIdSogg();
		logger.debug("getListaPersCivByIdExt [ID_EXT: "+idExt+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersonaCivico.getListaCivByIdPersona");
			q.setParameter("idExtDPersona", idExt);
			listaPersCiv =(List<SitDPersonaCivico>) q.getResultList();
			logger.debug("getListaPersCivByIdExt - TROVATO. N.ELE: "+ listaPersCiv.size());
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}	
		return listaPersCiv;
	}

	@Override
	public List<SitDCivicoV> getListaCiviciByIdExt(RicercaIndirizzoAnagrafeDTO ri) throws AnagrafeException {
		List<SitDCivicoV> listaCiv= new ArrayList<SitDCivicoV>();
		String idExt = ri.getSitDCivicoIdExt();
		logger.debug("getListaCiviciByIdExt [ID_EXT: "+idExt+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDCivicoV.getListaCivByIdExt");
			q.setParameter("idExt", idExt);
			listaCiv =(List<SitDCivicoV>) q.getResultList();
			logger.debug("getListaCiviciByIdExt TROVATO. N.ELE: "+ listaCiv.size());
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}	
		return listaCiv;

	}

	@Override
	public List<SitDVia> getListaVieByIdExt(RicercaIndirizzoAnagrafeDTO ri) throws AnagrafeException{
		List<SitDVia> listaVie= new ArrayList<SitDVia>();
		String idExt = ri.getSitDViaIdExt();
		logger.debug("getListaVieByIdExt [ID_EXT: "+idExt+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDVia.getListaVieByIdExt");
			q.setParameter("idExt", idExt);
			listaVie =(List<SitDVia>) q.getResultList();
			logger.debug("getListaVieByIdExt TROVATO. N.ELE: "+ listaVie.size());
	
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}	
		return listaVie;
	}

	@Override
	public List<SoggettoAnagrafeDTO> searchSoggetto(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException{
		List<SoggettoAnagrafeDTO> lista= new ArrayList<SoggettoAnagrafeDTO>();
		try {
			String sql = (new AnagGenQueryBuilder(rs)).createQuery(false);
			logger.debug("searchSoggetto. SQL: " + sql);
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object[]> result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			lista =new ArrayList<SoggettoAnagrafeDTO>();
			for (Object[] ele : result) {
				SoggettoAnagrafeDTO sogg = new SoggettoAnagrafeDTO(); 
				if (ele[0] != null)
					sogg.setCognome(ele[0].toString().trim());
				if (ele[1] != null)
					sogg.setNome(ele[1].toString().trim());
				if (ele[2] != null)
				sogg.setDtNas((Date)ele[2]);
				if (ele[3] != null)
					sogg.setCodFis(ele[3].toString().trim());
				if (ele[4] != null)
					sogg.setIdExtComNas(ele[4].toString().trim());
				if (ele[5] != null)
					sogg.setDesComNas(ele[5].toString().trim());
				lista.add(sogg);
			}
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return lista;
	}

	@Override
	public List<ComponenteFamigliaDTO> getCompFamigliaByIdExtDPersona(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException {
		List<ComponenteFamigliaDTO> listaPersone=new ArrayList<ComponenteFamigliaDTO>();
		
		String param = rs.getIdSogg();
		
		logger.debug("AnagrafeJPAImpl.getListaCompFamiglia [ID_EXT: " + rs.getIdSogg() + "][dt_rif: " + rs.getDtRif()+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersona_SitDPersFam.getPersonaRelFamByIdExtDPersonaAllaData");
			q.setParameter("idExtDPersona", param);
			java.util.Date dtRif =rs.getDtRif();
			// se la data è null, si cercano le righe valide alla data di sistema
			if (dtRif==null)
				dtRif = new java.util.Date();
			q.setParameter("dtRif", dtRif);
			List<Object[]> result = q.getResultList();
			logger.warn("getCompFamigliaByIdExtDPersona - TROVATO. N.ELE: "+ result.size());
			for (Object[] ele : result) {
				ComponenteFamigliaDTO comp= new ComponenteFamigliaDTO(); 
				if (ele[0] != null)
					comp.setPersona((SitDPersona)ele[0]);
				if (ele[1] != null)
					comp.setRelazPar(ele[1].toString().trim());
				listaPersone.add(comp);
			}
	
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}
	
	@Override
	public List<ComponenteFamigliaDTO> getCompFamigliaByCodFis(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException {
		List<ComponenteFamigliaDTO> listaPersone=new ArrayList<ComponenteFamigliaDTO>();
		
		String param = rs.getCodFis();
		
		logger.debug("AnagrafeJPAImpl.getListaCompFamiglia [CODFISC: " + rs.getCodFis() + "][dt_rif: " + rs.getDtRif()+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SitDPersona_SitDPersFam.getPersonaRelFamByCodFisAllaData");
			q.setParameter("codfisc", param);
			java.util.Date dtRif =rs.getDtRif();
			// se la data è null, si cercano le righe valide alla data di sistema
			if (dtRif==null)
				dtRif = new java.util.Date();
			q.setParameter("dtRif", dtRif);
			List<Object[]> result = q.getResultList();
			logger.warn("getCompFamigliaByCodFis - TROVATO. N.ELE: "+ result.size());
			for (Object[] ele : result) {
				ComponenteFamigliaDTO comp= new ComponenteFamigliaDTO(); 
				if (ele[0] != null)
					comp.setPersona((SitDPersona)ele[0]);
				if (ele[1] != null)
					comp.setRelazPar(ele[1].toString().trim());
				listaPersone.add(comp);
			}
	
		} catch (Throwable t) {
			logger.error("ERRORE getCompFamigliaByCodFis", t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}

	@Override
	public List<SitComune> getComuni(RicercaLuogoDTO rl) throws AnagrafeException {
		List<SitComune> comuni = new ArrayList<SitComune>();
		logger.debug("AnagrafeJPAImpl.getComuni");
		try{
				Query q= manager_diogene.createNamedQuery("SitComune.getComuni");
				comuni = (List<SitComune>) q.getResultList();
				
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return comuni;
	}
	
	@Override
	public SitComune belfioreToComune(RicercaLuogoDTO rl) throws AnagrafeException {
		SitComune comune = null;
		logger.debug("AnagrafeJPAImpl.getComuni");
		try{
				Query q= manager_diogene.createNamedQuery("SitComune.getComuneByBelfiore");
				q.setParameter("belfiore", rl.getBelfiore());
				List<SitComune> lista = (List<SitComune>) q.getResultList();
				if(lista.size() > 0)
					comune = lista.get(0);
		
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return comune;
	}
	
	@Override
	public SitComune descrizioneToComune(RicercaLuogoDTO rl) throws AnagrafeException {
		SitComune comune = null;
		logger.debug("AnagrafeJPAImpl.getComuneByDescr");
		try{
				Query q= manager_diogene.createNamedQuery("SitComune.getComuneByDescrizione");
				q.setParameter("descrizione", rl.getDesComune().toUpperCase());
				List<SitComune> lista = (List<SitComune>) q.getResultList();
				if(lista.size() > 0)
					comune = lista.get(0);
		
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return comune;
	}
	
	@Override
	public SitComune getComune(RicercaLuogoDTO rl) throws AnagrafeException {
		SitComune comune = null;
		logger.debug("AnagrafeJPAImpl.getComune: ID_EXT_COMUNE: " + rl.getIdExtComune()+ " ; dt_rif: " + rl.getDtRif());
		if(rl.getIdExtComune()!=null){
			try{
				Query q = null;
				if (rl.getDtRif()!=null) {
					q= manager_diogene.createNamedQuery("SitComune.getComuneAllaData");
					q.setParameter("dtRif", rl.getDtRif());
				}else
					q= manager_diogene.createNamedQuery("SitComune.getComuneAttuale");
				q.setParameter("idExt", rl.getIdExtComune());
				comune = (SitComune)q.getSingleResult();
		
			}catch (NoResultException nre) {
				logger.warn("AnagrafeJPAImpl.getComune [ID_EXT_COMUNE: " + rl.getIdExtComune()+"] - No Result " + nre.getMessage());
			}catch (Throwable t) {
				logger.error("", t);
				throw new AnagrafeException(t);
			}
		}
		return comune;
	}
	
	@Override
	public List<SitProvincia> getProvincie(RicercaLuogoDTO rl) throws AnagrafeException {
		List<SitProvincia> provincie = new ArrayList<SitProvincia>();
		logger.debug("AnagrafeJPAImpl.getProvincie");
		try{
				Query q= manager_diogene.createNamedQuery("SitProvincia.getProvincie");
				provincie = (List<SitProvincia>) q.getResultList();
				
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return provincie;
	}

	@Override
	public SitProvincia getProvincia(RicercaLuogoDTO rl)			throws AnagrafeException {
		SitProvincia provincia= null;
		logger.debug("AnagrafeJPAImpl.getProvincia: ID_EXT_PROVINCIA: " + rl.getIdExtProvincia()+ " ; dt_rif: " + rl.getDtRif());
		if(rl.getIdExtProvincia()!=null){
			try{
				Query q = null;
				if (rl.getDtRif()!=null) {
					q= manager_diogene.createNamedQuery("SitProvincia.getProvinciaAllaData");
					q.setParameter("dtRif", rl.getDtRif());
				}else
					q= manager_diogene.createNamedQuery("SitProvincia.getProvinciaAttuale");
				q.setParameter("idExt", rl.getIdExtProvincia());
				provincia = (SitProvincia)q.getSingleResult();
			}catch (NoResultException nre) {
				logger.warn("AnagrafeJPAImpl.getProvincia [ID_EXT_PROVINCIA: " + rl.getIdExtProvincia()+"] - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new AnagrafeException(t);
			}
		}
		return provincia;
	}
	@Override
	public List<ComponenteFamigliaDTO> getResidentiAlCivico(RicercaIndirizzoAnagrafeDTO ri) throws AnagrafeException {
		List<ComponenteFamigliaDTO> listaPersone= new ArrayList<ComponenteFamigliaDTO>();
		logger.debug("AnagrafeJPAImpl.getResidentiAlCivico" +	"[SIT_D_CIVICO.ID "+ ri.getSitDCivicoId());
		try {
			Query q = manager_diogene.createNamedQuery("Demografia.getResidentiAlCivico");
			
			q.setParameter("idCivico", ri.getSitDCivicoId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dtRif = sdf.parse(sdf.format(new java.util.Date()));
			q.setParameter("dtRif", dtRif);
			List<Object[]> result = q.getResultList();
			logger.warn("AnagrafeJPAImpl.getResidentiAlCivico TROVATO. N.ELE: "+ result.size());
			for (Object[] ele : result) {
				ComponenteFamigliaDTO comp= new ComponenteFamigliaDTO(); 
				if (ele[0] != null)
					comp.setRelazPar(ele[0].toString().trim());
				else
					comp.setRelazPar("");
				if (ele[1] != null)
					comp.setIdExtDFamiglia(ele[1].toString().trim());
				else
					comp.setIdExtDFamiglia("");
				if (ele[2] != null)
					comp.setIdOrigFamiglia(ele[2].toString().trim());
				else
					comp.setIdOrigFamiglia("");
				if (ele[3] != null)
					comp.setPersona((SitDPersona)ele[3]);
				listaPersone.add(comp);
			}
		
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return listaPersone;
	}

	@Override
	public IndirizzoAnagrafeDTO getIndirizzo(RicercaIndirizzoAnagrafeDTO ri)	throws AnagrafeException {
		IndirizzoAnagrafeDTO ind = new IndirizzoAnagrafeDTO();
		
		logger.debug("AnagrafeJPAImpl.getIndirizzo" +	"[SIT_D_CIVICO.ID "+ ri.getSitDCivicoId());
		try {
			Query q = manager_diogene.createNamedQuery("SitDVia_SitDCivico.getIndirizzoByIdCiv");
			q.setParameter("idCivico", ri.getSitDCivicoId());
			Date dtRif = new Date();
			q.setParameter("dtRif", dtRif);
			List<Object[]> result = q.getResultList();
			logger.warn("AnagrafeJPAImpl.getIndirizzo - TROVATO. N.ELE: "+ result.size());
			
			String civLiv1=""; String civLiv2=""; String civLiv3="";
			if(result.size()>0){
				
				Object[] ele = result.get(0);
				SitDVia via =(SitDVia) ele[0];
				civLiv1 = ele[1]!=null ? ele[1].toString().trim() : "";
				civLiv2 = ele[2]!=null ? ele[2].toString().trim() : "";
				civLiv3 = ele[3]!=null ? ele[3].toString().trim(): "";
				
				ind.valIndirizzo(via);
			}
			
			
			ind.setCivico(civLiv1);
			ind.setCivicoLiv2(civLiv2);
			ind.setCivicoLiv3(civLiv3);
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return ind;
		
	}
	
	@Override
	public List<AnagraficaDTO> getAnagrafeByCF(RicercaSoggettoDTO rs) throws AnagrafeException{
		List<AnagraficaDTO> anagraficaList = new ArrayList<AnagraficaDTO>();
		logger.debug("AnagrafeJPAImpl.getAnagrafeByCF" +	"[CODFISC: "+rs.getCodFis());
		try {
			
			Query q = manager_diogene.createNamedQuery("SitDPersona.getAnagrafeByCF");
			q.setParameter("codFisc", rs.getCodFis());
			List<Object> lista = q.getResultList();
			for(Object ana: lista){
				
				AnagraficaDTO anagrafica = new AnagraficaDTO();
				anagrafica.setPersona( (SitDPersona) ana);
				
				if(ana != null){
					fillInfoAggiuntive(anagrafica);
				}
				
				anagraficaList.add(anagrafica);
			}

		
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return anagraficaList;
	}
	
	public void fillInfoAggiuntive(AnagraficaDTO anagrafica) {
		
		RicercaLuogoDTO rlDto = new RicercaLuogoDTO();
		
		//Dati Comune di Nascita
		rlDto.setIdExtComune(anagrafica.getPersona().getIdExtComuneNascita());
		anagrafica.setComuneNascita(getComune(rlDto));
		
		//Dati Provincia di Nascita
		rlDto.setIdExtProvincia(anagrafica.getPersona().getIdExtProvinciaNascita());
		anagrafica.setProvNascita(getProvincia(rlDto));
		
		Query q  = manager_diogene.createNamedQuery("SitDPersona.getInfoResidenza");
		q.setParameter("id", anagrafica.getPersona().getId());
		List<Object[]> listaInfoAgg = q.getResultList();
		if(listaInfoAgg.size() > 0){
			Object[] infoAgg = listaInfoAgg.get(0);
			anagrafica.setCivico( (SitDCivicoV) (infoAgg[0] != null? infoAgg[0]: new SitDCivicoV()));
			anagrafica.setVia( (SitDVia) (infoAgg[1] != null? infoAgg[1]: new SitDVia()));
			
			//Verifico se è emigrato (data non nulla, e maggiore di dataImm)
			Date dataEmi = anagrafica.getPersona().getDataEmi();
			Date dataImm = anagrafica.getPersona().getDataImm();
			boolean emigrato = dataEmi!=null && (dataImm==null || dataImm.before(dataEmi)); //emigrato e non rientrato nel comune
			
			//Dati Residenza
			if(emigrato){
				anagrafica.setComuneResidenza(null);
				rlDto.setIdExtComune(anagrafica.getPersona().getIdExtComuneEmi());
				anagrafica.setComuneResidenzaEmig(getComune(rlDto));
				rlDto.setIdExtProvincia(anagrafica.getPersona().getIdExtProvinciaEmi());
				anagrafica.setProvResidenzaEmig(getProvincia(rlDto));
			}
			else
				anagrafica.setComuneResidenza((SitEnte) (infoAgg[2] != null? infoAgg[2]: new SitEnte())); 
		}
		
	}
	
	@Override
	public AnagraficaDTO getPersonaFamigliaByCF(RicercaSoggettoAnagrafeDTO rs) {
		AnagraficaDTO anagrafica = new AnagraficaDTO();
		logger.debug("AnagrafeJPAImpl.getPersonaFamigliaByCF: " + rs.getCodFis());
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.getPersonaFamigliaByCF");
			q.setParameter("idPersona", rs.getIdVarSogg());
			q.setParameter("codFisc", rs.getCodFis().toUpperCase());
			List<Object> lista = q.getResultList();
			if(lista.size() > 0){
				Object ana = lista.get(0);
				anagrafica.setPersona( (SitDPersona) ana);
				RicercaLuogoDTO rlDto = new RicercaLuogoDTO();

				if(ana != null){
					fillInfoAggiuntive(anagrafica);
				}
			
				return anagrafica;
			}
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return anagrafica;
	}

	@Override
	public List<SitDPersona> getFamigliaByCogNomDtNascita(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> lista = new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getFamigliaByCogNomDtNascita: " + rs.getCognome()+" "+ rs.getNome()+" "+ sdf.format(rs.getDtNas()));
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.getFamigliaByCogNomDtNascita");
			q.setParameter("cognome", rs.getCognome().toUpperCase());
			q.setParameter("nome", rs.getNome().toUpperCase());
			q.setParameter("dtNascita", rs.getDtNas());
			lista = q.getResultList();
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return lista;
	}
	
	
	@Override
	public List<SitDPersona> getFamigliaByCF(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> lista = new ArrayList<SitDPersona>();
		logger.debug("AnagrafeJPAImpl.getFamigliaByCF: " + rs.getCodFis());
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.getFamigliaByCF");
			q.setParameter("codFisc", rs.getCodFis().toUpperCase());
			lista = q.getResultList();
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return lista;
	}
	
	@Override
	public String getRelazioneParentelaByIdPersona(String id) {
		
		logger.debug("AnagrafeJPAImpl.getRelazioneParentela id: " + id);
		try{
			Query q = manager_diogene.createNamedQuery("SitDCodParentela.getParentelaByIdPersona");
			q.setParameter("id", id);
			List<String> result = q.getResultList();
			if(result.size()> 0){
				return result.get(0);
			}
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return null;
	}
	
	public boolean verificaNazionalitaByCF(String descr, RicercaSoggettoAnagrafeDTO rs){
		boolean nazionalita = false;
		
		logger.debug("SitStato.verificaNazionalitaByCF: " + descr);
		try{
			Query q = manager_diogene.createNamedQuery("SitStato.verificaNazionalitaByCFAllaData");
			q.setParameter("descr", descr);
			q.setParameter("codFisc", rs.getCodFis());
			q.setParameter("dtRif", rs.getDtRif());
			List<SitStato> result = q.getResultList();
			if(result.size()> 0)
				nazionalita = true;
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return nazionalita;
		
	}
	
	
	
	@Override
	public boolean verificaResidenzaByCogNomDtNascAllaData(RicercaSoggettoAnagrafeDTO rs){
		boolean residenza = false;
		if(rs.getDtRif()== null)
			rs.setDtRif(new Date());
		
		logger.debug("SitDPersona.verificaResidenzaByCogNomDtNascAllaData: " + rs.getCognome()+" "+ rs.getNome()+" "+ sdf.format(rs.getDtNas()));
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.verificaResidenzaByCogNomDtNascAllaData");
			q.setParameter("cognome", rs.getCognome());
			q.setParameter("nome", rs.getNome());
			q.setParameter("dtNascita", rs.getDtNas());
			q.setParameter("dtRif", rs.getDtRif());
			List<Object> result = q.getResultList();
			if(result.size()> 0){
				residenza = true;
			}
		 logger.debug("Il soggetto " + rs.getCognome()+" "+ rs.getNome()+" "+ sdf.format(rs.getDtNas())+(residenza? "":" non")+" è residente nel comune alla data " + sdf.format(rs.getDtRif()));
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return residenza;
		
	}
	
	
	
	@Override
	public boolean verificaResidenzaByCFAllaData(RicercaSoggettoAnagrafeDTO rs){
		boolean residenza = false;
		if(rs.getDtRif()== null)
			rs.setDtRif(new Date());
		
		logger.debug("SitDPersona.verificaResidenzaByCFAllaData: " + rs.getCodFis()+", "+rs.getDtRif());
		try{
			Query q = manager_diogene.createNamedQuery("SitDPersona.verificaResidenzaByCFAllaData");
			q.setParameter("codFisc", rs.getCodFis());
			q.setParameter("dtRif", rs.getDtRif());
			List<Object> result = q.getResultList();
			if(result.size()> 0){
				residenza = true;
			}
		 logger.debug("Il soggetto "+rs.getCodFis()+(residenza? "":" non")+" è residente nel comune alla data " + sdf.format(rs.getDtRif()));
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
		return residenza;
		
	}
	
	@Override
	public AttrPersonaDTO getAttributiPersonaByCF(RicercaSoggettoAnagrafeDTO rs){
		AttrPersonaDTO tipo= new AttrPersonaDTO();
		
		java.util.Date dtRef = rs.getDtRif();
		if(dtRef == null){
			dtRef = new java.util.Date();
			rs.setDtRif(dtRef);
		}
		
		//Verifica Nazionalità Italiana ("I")
		tipo.setItaliano(this.verificaNazionalitaByCF("I", rs));
		
		//Verifica Residenza nel Comune alla Data
		tipo.setResidente(this.verificaResidenzaByCFAllaData(rs));
		
		List<SitDPersona> persone = this.getListaPersoneByCFAllaData(rs);
		
		Boolean defunto = false;
		for(SitDPersona p: persone){
			//Verifica Defunto
			defunto = defunto || (p.getDataMor()!=null);
			
		}
		tipo.setDefunto(defunto);
		
		return tipo;
	}
	
	
	public DatiCivicoAnagrafeDTO getDatiCivicoAnagrafe(RicercaCivicoDTO rc){
		DatiCivicoAnagrafeDTO dati = new DatiCivicoAnagrafeDTO();
		
		RicercaIndirizzoAnagrafeDTO ria = new RicercaIndirizzoAnagrafeDTO();
		ArrayList<String> famiglie = new ArrayList<String>();
		ArrayList<String> famiglieResPropr = new ArrayList<String>();
		
		String idViaUnico = rc.getIdVia();
		String[] listaIdCivico = rc.getListaIdCivici();
		
		//Scorre gli id ricavati dall'indice e per ciascuno estrae i residenti
		List<ComponenteFamigliaDTO> residenti = new ArrayList<ComponenteFamigliaDTO>();
		for(String idCivico : listaIdCivico){
			ria.setSitDCivicoId(idCivico);
			List<ComponenteFamigliaDTO> residentiIdCivico = this.getResidentiAlCivico(ria);
			residenti.addAll(residentiIdCivico);
		}
			
		//Per ciascun residente esamina i dati
		for(ComponenteFamigliaDTO r : residenti){
			//Numero di abitanti distinti per fasce di età (<18anni, tra 18 e 65 anni, > 65 anni)
			SitDPersona persona = r.getPersona();
			logger.info(persona.getId()+" "+persona.getCodfisc());
			
			int eta = calcolaEta((Date)persona.getDataNascita());
			if(eta<18)
				dati.setCountUnder18(dati.getCountUnder18()+1);
			else if(eta>65)
				dati.setCountOver65(dati.getCountOver65()+1);
			else
				dati.setCount18_65(dati.getCount18_65()+1);
			
			//Numero di famiglie residenti al civico
			String idExtFamiglia = r.getIdExtDFamiglia();
			
			if(!famiglie.contains(idExtFamiglia))
				famiglie.add(idExtFamiglia);
			
		}
			
		try{
		
			String[] ids = rc.getListaId();
			if(ids!=null && ids.length >0){
				List<String> idsTitolari = Arrays.asList(ids); 
				//Estrarre le famiglie dei titolari a catasto
				logger.debug("RICERCA Famiglie Titolari a Catasto [listaId: "+idsTitolari+"]");
				
				Query qfc = manager_diogene.createNamedQuery("SitDPersFam.getFamiglieTitolariByIdConsSoggTab");
				qfc.setParameter("listaIdConsSoggTab", idsTitolari);
				List<String> famTitolari = qfc.getResultList();
				logger.debug("Result Size ["+famTitolari+"]");
				
				for(String fam : famiglie){
					if(famTitolari.contains(fam)&& famiglieResPropr.contains(fam))
						famiglieResPropr.add(fam);
				}
			}
			
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new AnagrafeException(t);
		}
			
		//dati.setResidenti(residenti);
		dati.setCountFamResidenti(famiglie.size());
		dati.setCountFamResidentiProprietarie(famiglieResPropr.size());
		
		return dati;
	}
	
	private int calcolaEta(Date dataNasc){
		int eta = 0;
		
		String sDataNasc = ddMMyyyy.format(dataNasc);
		String oggi = ddMMyyyy.format(super.getCurrentDate());
		
		int annoNasc = Integer.parseInt(sDataNasc.substring(4));
		int anno = Integer.parseInt(oggi.substring(4));
		
		eta = anno - annoNasc;
		
		return eta;
	}
	
	public SitDVia getViaByPrefissoDescr(RicercaCivicoDTO rc){
		SitDVia strada = null;
		
		List<String> listaPrefissi = StringheVie.getToponimiDecoded(rc.getToponimoVia());
		if(listaPrefissi.size()==0)
			listaPrefissi.add(rc.getToponimoVia());
		
		//Estrae la strada dal catasto partendo da: toponimo, descrizione
		try {
			
			logger.debug("RICERCA VIA Anagrafe [toponimo: "+listaPrefissi +"," +
									  "descrizione: "+rc.getDescrizioneVia()+ "]");
			
			Query qs = manager_diogene.createNamedQuery("SitDVia.getViaByPrefissoDescr");
			qs.setParameter("listaPrefissi", listaPrefissi);
			qs.setParameter("descrizione", rc.getDescrizioneVia());
			java.util.Date dtRif = rc.getDataRif();
			if(dtRif == null)
				dtRif = new java.util.Date();
			qs.setParameter("dtRif", dtRif);
			
			List<SitDVia> result =  qs.getResultList();
			logger.debug("RICERCA VIA Anagrafe ["+result.size()+"]");
			if(result.size()>0)
				strada = result.get(0);
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new AnagrafeException(t);
		}
		return strada;
	}
	
	public String getIdCivicoByIndirizzo(RicercaCivicoDTO rc){
		String idCivico = null;
		
		SitDVia via = this.getViaByPrefissoDescr(rc);
		if(via!=null){
			String idExtDVia = via.getIdExt();
			try {
				logger.info("RICERCA ID CIVICO Anagrafe [ID_VIA: "+ idExtDVia +"," +
														"civico: "+rc.getCivico()+ "]");
				
				Query qs = manager_diogene.createNamedQuery("SitDCivicoV.getIdCivByIdExtViaCivico");
				qs.setParameter("idExtDVia" , idExtDVia);
				qs.setParameter("civico", rc.getCivico());
				java.util.Date dtRif = rc.getDataRif();
				if(dtRif == null)
					dtRif = new java.util.Date();
				qs.setParameter("dtRif", dtRif);
				
				List<SitDCivicoV> result = qs.getResultList();
				if(result.size()>0)
					idCivico = result.get(0).getId();
				else
					logger.info("RICERCA ID CIVICO Anagrafe [Nessun risultato]");
				
			} catch (Throwable t) {
				logger.error("",t);
				throw new AnagrafeException(t);
			}
		}
		return idCivico;
	}

	@Override
	public Long getNumeroFamiglieResidentiAlCivico(String idCivico, Date dtRif) {
		Long numFamRes=null;
		logger.debug("getNumeroFamiglieResidentiAlCivico() - idCivivco: " + idCivico);
		try{
			Query q = manager_diogene.createNamedQuery("Demografia.getNumFamiglieResidentiAlCivico");
			q.setParameter("idCivico", idCivico);
			q.setParameter("dtRif", dtRif);
			Object result = q.getSingleResult();
			numFamRes = (Long) result;		
		}catch (NoResultException nre) {
			logger.debug("Nessuna famiglia residente al civico");
		}
		catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return numFamRes;
	}
		
	@Override
	public ComuneProvinciaDTO getDescrizioneComuneProvByIdExt(String idExtComune) {
		ComuneProvinciaDTO cp=null;
		logger.debug("getDescrizioneComuneProvByIdExt() - idExtComune: " + idExtComune);
		try{
			Query q = manager_diogene.createNamedQuery("SitComune.getDescrizioneComuneProvByIdExt");
			q.setParameter("idExtComune", idExtComune);
			List<Object[]> result = q.getResultList();
			
			if(result.size()>0){
				Object[] obj = result.get(0);
				cp = new ComuneProvinciaDTO();
				cp.setBelfiore(obj[0]!=null ? obj[0].toString():"");
				cp.setDescComune(obj[1]!=null ? obj[1].toString():"");
				cp.setSiglaProv(obj[2]!=null ? obj[2].toString():"");
				cp.setDescProv(obj[3]!=null ? obj[3].toString():"");
			}
			
		}catch (NoResultException nre) {
			logger.debug("Comune non identificato");
		}
		catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return cp;
	}
	
	@Override
	public SitEnte getEnteByDescrizione(RicercaLuogoDTO rl) throws AnagrafeException {
		SitEnte comune = null;
		logger.debug("AnagrafeJPAImpl.getEnte");
		try{
				Query q= manager_diogene.createNamedQuery("SitEnte.getEnteByDescrizione");
				q.setParameter("descrizione", rl.getDesComune().toUpperCase());
				List<SitEnte> lista = (List<SitEnte>) q.getResultList();
				if(lista.size() > 0)
					comune = lista.get(0);
		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return comune;
	}
	
	public List<Object[]> getIndirizzoResidenzaByCodFisc(RicercaSoggettoAnagrafeDTO rs) {
		List<Object[]> result = new ArrayList<Object[]>();
		String codFis = rs.getCodFis();
		if (codFis == null) {
			return null;
		}
		//il codice fiscale può non essere stato ancora validato, quindi ci possono essere gli apici, ma basta toglierli
		codFis = codFis.replace("'", "");
		String sql = "select distinct id_orig_via as codice_via, "
				+ "decode(viasedime, null, '', viasedime || ' ' ) || descrizione_via as indirizzo, "
				+ "trim(leading 0 from civ_liv1) as civico_numero, "
				+ "case "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv3) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then null "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) || '/' || trim(leading 0 from civ_liv3) "
				+ "end as civico_altro, "
				+ "c.sigla_prov as prov, "
				+ "c.cod_istat_comune as com_cod, "
				+ "c.denominazione as com_des, "
				+ "(select cod_istat_nazione from am_tab_nazioni where nazione = 'ITALIA') as stato_cod, "
				+ "'ITALIA' as stato_des, "
				+ "case "
				+ "when dt_inizio_indirizzo is not null then dt_inizio_indirizzo "
				+ "else dt_inizio_val_persona end as data_inizio_app "
				+ "from persona_civici_v a, am_tab_comuni c "
				+ "where a.codfisc = '" + codFis.toUpperCase().trim() + "' "
				+ "and c.cod_nazionale = a.codent "
				+ "and a.dt_fine_val_persona is null "
				+ "and a.data_emi_persona is null "
				+ "and a.data_mor_persona is null "
				+ "and (a.id_orig_via is not null and a.id_orig_via <> 0) "
				+ "and a.dt_inizio_val_persona = (select max (b.dt_inizio_val_persona) "
				+ "from persona_civici_v b "
				+ "where a.codfisc = b.codfisc "
				+ "and b.dt_fine_val_persona is null "
				+ "and b.data_emi_persona is null "
				+ "and b.data_mor_persona is null "
				+ "and (b.id_orig_via is not null and b.id_orig_via <> 0)) "
				+ "and (case "
				+ "WHEN (SELECT COUNT (1) FROM am_tab_comuni cc WHERE cc.cod_nazionale = c.cod_nazionale) = 1 "
				+ "then 1 "
				+ "WHEN (SELECT COUNT (1) FROM am_tab_comuni cc WHERE cc.cod_nazionale = c.cod_nazionale) > 1 "
				+ "then c.attivo "
				+ "end) = 1";
		
		try{
			Query q = manager_diogene.createNativeQuery(sql);
			result = q.getResultList();
		}catch (Throwable t) {
			logger.warn("getIndirizzoResidenzaByCodFisc SQL["+sql+"],CF["+codFis+"]");
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return result;
	}
	
	public List<Object[]> getIndirizzoResidenzaByNomeCiv(RicercaCivicoDTO rc) {
		List<Object[]> result = new ArrayList<Object[]>();
		String indirizzo = rc.getDescrizioneVia();
		String civico = rc.getCivico();
		if (indirizzo == null || civico == null) {
			return null;
		}
		indirizzo = indirizzo.replace("'", "''");
		String sql = "select distinct id_orig_via as codice_via, "
				+ "decode(viasedime, null, '', viasedime || ' ' ) || a.descrizione as indirizzo, "
				+ "trim(leading 0 from civ_liv1) as civico_numero, "
				+ "case "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv3) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then null "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) || '/' || trim(leading 0 from civ_liv3) "
				+ "end as civico_altro, "
				+ "c.sigla_prov as prov, "
				+ "c.cod_istat_comune as com_cod, "
				+ "c.denominazione as com_des, "
				+ "(select cod_istat_nazione from am_tab_nazioni where nazione = 'ITALIA') as stato_cod, "
				+ "'ITALIA' as stato_des, "
				+ "a.dt_inizio_val as data_inizio_app "
				+ "from sit_d_civico_via_v a, am_tab_comuni c, sit_ente s "
				+ "where UPPER(a.descrizione) = '" + indirizzo.toUpperCase().trim() + "' "
				+ "and trim(leading 0 from a.civ_liv1) = '"+ civico + "' "
				+ "and c.cod_nazionale = s.codent "
				+ "and a.dt_fine_val is null "
				+ "and (a.id_orig_via is not null and a.id_orig_via <> 0) "
				+ "and (case "
				+ "WHEN (SELECT COUNT (1) FROM am_tab_comuni cc WHERE cc.cod_nazionale = c.cod_nazionale) = 1 "
				+ "then 1 "
				+ "WHEN (SELECT COUNT (1) FROM am_tab_comuni cc WHERE cc.cod_nazionale = c.cod_nazionale) > 1 "
				+ "then c.attivo "
				+ "end) = 1";
	
		try{
			Query q = manager_diogene.createNativeQuery(sql);
			result = q.getResultList();
		}catch (Throwable t) {
			logger.warn("getIndirizzoResidenzaByNomeCiv SQL["+sql+"],indirizzo["+indirizzo+"],civico["+civico+"]");
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return result;
	}
	
	public List<SitDVia> getIndirizziLike(RicercaIndirizzoAnagrafeDTO ri) {
		String like = ri.getSitDCivicoViaDescrizione();
		if (like == null) {
			return null;
		}
		like = like.replace("'", "''");
		List<SitDVia> result = new ArrayList<SitDVia>();
		logger.debug("AnagrafeJPAImpl.getEnte");
		try{
				Query q= manager_diogene.createNamedQuery("SitDVia.getListaVieByDescrizione");
				q.setParameter("descrizione", like);
				result = (List<SitDVia>) q.getResultList();
		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return result;
	}
	
	
	public List<SitDStaciv> getListaStatoCivile(){
		try{
			Query q= manager_diogene.createNamedQuery("SitDStaciv.getListaValidi");
			return q.getResultList();
		}catch(Throwable t){
			logger.error("", t);
			throw new AnagrafeException(t);
		}
	}
	
	public List<SitDCivicoV> getCivicoByIdExtDVia(RicercaIndirizzoAnagrafeDTO ri) {
		String id = ri.getSitDViaIdExt();
		if (id == null) {
			return null;
		}
		List<SitDCivicoV> result = new ArrayList<SitDCivicoV>();
		logger.debug("AnagrafeJPAImpl.getEnte");
		try{
				Query q= manager_diogene.createNamedQuery("SitDCivicoV.getListaCivByIdExtDVia");
				q.setParameter("idExtDVia", id);
				result = (List<SitDCivicoV>) q.getResultList();
		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		}catch (Throwable t) {
			logger.error("", t);
			throw new AnagrafeException(t);
		}
		return result;
	}
		/*String sql = "select distinct id_orig_via as codice_via, "
				+ "decode(viasedime, null, '', viasedime || ' ' ) || descrizione as indirizzo, "
				+ "trim(leading 0 from civ_liv1) as civico_numero, "
				+ "case "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv3) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then null "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) || '/' || trim(leading 0 from civ_liv3) "
				+ "end as civico_altro "
				+ "from sit_d_civico_via_v "
				+ "where (viasedime is not null or descrizione is not null) "
				+ "and (decode(viasedime, null, '', viasedime || ' ' ) || descrizione) "
				+ "|| ' ' || "
				+ "trim(leading 0 from civ_liv1) "
				+ "|| '/' || "
				+ "case "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv3) "
				+ "when trim(leading 0 from civ_liv2) is null "
				+ "and trim(leading 0 from civ_liv3) is null "
				+ "then null "
				+ "when trim(leading 0 from civ_liv2) is not null "
				+ "and trim(leading 0 from civ_liv3) is not null "
				+ "then "
				+ "trim(leading 0 from civ_liv2) || '/' || trim(leading 0 from civ_liv3) "
				+ "end "
				+ "like " + like.toUpperCase().trim() + " || '%' "
				+ "order by 2, 3, 4";
		Query q = manager_diogene.createNativeQuery(sql);
		List<Object[]> result = q.getResultList();*/
	
}
