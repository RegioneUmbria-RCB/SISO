package it.webred.ct.data.access.basic.anagrafe;
 
import it.webred.ct.config.luoghi.LuoghiService;

import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.BaseGenericDTO;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.anagrafe.dao.AnagrafeDAO;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagrafeCoordCatDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.AttrPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComuneProvinciaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiCivicoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaIndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaLuogoDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.StatoCivile;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.CommonDataIn;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersCoordCatV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitDPersonaCivico;
import it.webred.ct.data.model.anagrafe.SitDStaciv;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.data.model.common.SitEnte;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * Session Bean implementation class AnagrafeServiceBean
 */ 
@Stateless 
public class AnagrafeServiceBean extends CTServiceBaseBean implements AnagrafeService {
	@Autowired
	private AnagrafeDAO anagrafeDAO;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<SitDPersona> getListaPersoneByCF(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> lista =null;
		if (rs.getDtRif()== null) 
			lista = anagrafeDAO.getListaPersoneByCF(rs);
		else
			lista = anagrafeDAO.getListaPersoneByCFAllaData(rs);
		return lista;
	}//-------------------------------------------------------------------------
	
/*	//SISO-1297
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<SitDPersona> getListaPersoneByCFAnonimo(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> lista =null;
		if (rs.getDtRif()== null) 
			lista = anagrafeDAO.getListaPersoneByCF(rs);
		else
			lista = anagrafeDAO.getListaPersoneByCFAllaData(rs);
		return lista;
	}*/
	
	@Override
	public List<AnagrafeCoordCatDTO> getListaPersoneByCoordCat( CatastoSearchCriteria rs ) {
		List<AnagrafeCoordCatDTO> listaDTO = new ArrayList<AnagrafeCoordCatDTO>();
		
		List<SitDPersCoordCatV> lista = anagrafeDAO.getListaPersoneByCoordCat(rs);
		if ( lista != null && lista.size()>0){
			
			Iterator<SitDPersCoordCatV> it = lista.iterator();
			while(it.hasNext()){
				SitDPersCoordCatV sorgente = it.next();
				AnagrafeCoordCatDTO destinazione = new AnagrafeCoordCatDTO();
				
				BeanUtils.copyProperties(sorgente, destinazione);
				
				listaDTO.add(destinazione);

			}
		}
		
		return listaDTO;
	}//-------------------------------------------------------------------------

	@Override
	public List<SitDPersona> getListaPersoneByDatiAna(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> lista =null;
		if (rs.getDtRif()== null) 
			lista = anagrafeDAO.getListaPersoneByDatiAna(rs);
		else
			lista = anagrafeDAO.getListaPersoneByDatiAnaAllaData(rs);
		return lista;
	}//-------------------------------------------------------------------------

	@Override
	public List<SitDPersona> getListaPersoneByDenominazione(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getListaPersoneByDenominazione(rs);
	}

	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<SitDPersona> searchSISOAnagrafiche(RicercaSoggettoAnagrafeDTO rs) { 
		return anagrafeDAO.getListaPersoneByDatiAnag(rs);
	}
	
	@Override
	public String getIdPersonaByCF(RicercaSoggettoAnagrafeDTO rs) {
		String idPersona=null;	
		logger.debug("AnagrafeServiceBean - getIdPersonaByCF(). " +	"[CF: "+rs.getCodFis()+"]");
		if (rs.getCodFis()==null || rs.getCodFis().equals("")) {
			return null;
		}
		List<SitDPersona> listaPersone = anagrafeDAO.getListaPersoneByCF(rs);
		//Controllo che siano la stessa persona
		if (listaPersone!=null  && !listaPersone.isEmpty()) {
			String idExt= listaPersone.get(0).getIdExt();
			boolean stessaPersona=true;
			for(SitDPersona pers: listaPersone) {
				stessaPersona=idExt.equals(pers.getIdExt());
				logger.debug("... in for: stessaPersona: " + stessaPersona);
				if (!stessaPersona)
					break;
			}
			if (stessaPersona) {
				idPersona=idExt;
			}
		}
		return idPersona;
	}
	
	@Override
	public List<SitDPersona> getVariazioniPersonaByIdExt(RicercaSoggettoAnagrafeDTO rs) {
		List<SitDPersona> listaPersone=null; 
		logger.debug("AnagrafeServiceBean - getVariazioniPersonaByIdExt(). " +	"[ID-SOGG-ANA: "+rs.getIdSogg()+"]");
		if (rs.getIdSogg()!=null && !rs.getIdSogg().equals("")) {
			return null;
		}
		listaPersone = anagrafeDAO.getVariazioniPersonaByIdExt(rs);
		return listaPersone;
	}
	
	@Override
	public AnagraficaDTO getPersonaFamigliaByCF(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getPersonaFamigliaByCF(rs);
	}
	
	@Override
	public List<SitDPersona> getFamigliaByCF(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getFamigliaByCF(rs);
	}
	
	@Override
	public String getRelazioneParentelaByIdPersona(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getRelazioneParentelaByIdPersona(rs.getIdVarSogg());
	}
	
	@Override
	public List<SitDPersona> getFamigliaByCogNomDtNascita(RicercaSoggettoAnagrafeDTO rs){
		return anagrafeDAO.getFamigliaByCogNomDtNascita(rs);
	}
	
	@Override
	public IndirizzoAnagrafeDTO getIndirizzoPersona(RicercaSoggettoAnagrafeDTO rs) {
		IndirizzoAnagrafeDTO  indirizzo=null;
		logger.debug("AnagrafeServiceBean - getIndirizzoPersona(). " +	"[ID-SOGG-ANA: "+rs.getIdSogg()+"]");
		if (rs.getIdSogg()==null || rs.getIdSogg().equals("")) {
			return null;
		}
		//persone-civici
		List<SitDPersonaCivico> listaPersCiv = anagrafeDAO.getListaPersCivByIdExt(rs);
		if (listaPersCiv==null || listaPersCiv.size() == 0){
			return null;
		}
		//ora si cerca la riga valida alla data, se non c'è si prende quella attuale,  se non c'è si prende la più recente 
		SitDPersonaCivico persCivValidoAllaDtRif =null;
		SitDPersonaCivico persCivUltimo=null;	
		for (SitDPersonaCivico ele: listaPersCiv) {
			if (rs.getDtRif()!=null) {
				if (ele.getDtInizioVal().compareTo(rs.getDtRif())<= 0 &&
					(ele.getDtFineVal() == null || rs.getDtRif().compareTo(ele.getDtFineVal()) <= 0))	 {
					persCivValidoAllaDtRif = ele;
				}
			}	
			if (ele.getDtFineVal() == null)
				persCivUltimo=ele;
		}
		SitDPersonaCivico persCiv =persCivValidoAllaDtRif != null?persCivValidoAllaDtRif:persCivUltimo;
		if (persCiv == null){
			persCiv=listaPersCiv.get(0);
		}
		//civico
		String idExtDCivico=persCiv.getIdExtDCivico();	
		RicercaIndirizzoAnagrafeDTO ri = new RicercaIndirizzoAnagrafeDTO(idExtDCivico);
		List<SitDCivicoV> listaCiv=anagrafeDAO.getListaCiviciByIdExt(ri);
		if (listaCiv==null || listaCiv.size() == 0){
			return null;
		}
		//ora si cerca la riga valida alla data, come sopra 
		SitDCivicoV civValidoAllaDtRif =null;
		SitDCivicoV civUltimo=null;
		for (SitDCivicoV ele: listaCiv) {
			if (rs.getDtRif()!=null) {
				if (ele.getDtInizioVal().compareTo(rs.getDtRif())<= 0 &&
			       (ele.getDtFineVal() == null || 	rs.getDtRif().compareTo(ele.getDtFineVal()) <= 0))	 {
					civValidoAllaDtRif = ele;
				}
			}	
			if (ele.getDtFineVal() == null)
				civUltimo=ele;
		}
		SitDCivicoV civ =civValidoAllaDtRif != null?civValidoAllaDtRif:civUltimo;
		if (civ == null ){
			civ=listaCiv.get(0);
		}
		//via
		ri.setSitDViaIdExt(civ.getIdExtDVia());
		List<SitDVia> listaVie = anagrafeDAO.getListaVieByIdExt(ri);
		if (listaVie==null || listaVie.size() ==0){
			return null;
		}
		logger.debug("LISTA VIE - n.ELE: " + listaVie.size() );
		//ora si cerca la riga valida alla data, come sopra 
		SitDVia viaValidoAllaDtRif =null;
		SitDVia viaUltimo=null;
		for (SitDVia ele: listaVie) {
			if (rs.getDtRif()!=null) {
				if (ele.getDtInizioVal().compareTo(rs.getDtRif())<= 0 &&
				   (ele.getDtFineVal()==null || rs.getDtRif().compareTo(ele.getDtFineVal()) <= 0))	 {
					viaValidoAllaDtRif = ele;
				}
			}	
			if (ele.getDtFineVal() == null)
				viaUltimo=ele;
		}
		SitDVia via =viaValidoAllaDtRif != null?viaValidoAllaDtRif:viaUltimo;
		if (via == null){
			via=listaVie.get(0);
		}
		//dati
		indirizzo= new IndirizzoAnagrafeDTO();
		indirizzo.valDatiIndirizzo(via, civ);
		indirizzo.setDtIniVal(persCiv.getDtInizioVal());
		indirizzo.setDtFinVal(persCiv.getDtFineVal());
		logger.debug("via, civ: "+ via + ", " +civ);
		return indirizzo;
	}

	@Override
	public List<SoggettoAnagrafeDTO> searchSoggetto(RicercaSoggettoAnagrafeDTO rs) {
		List<SoggettoAnagrafeDTO> lista= anagrafeDAO.searchSoggetto(rs);
		return lista;
	}

	@Override
	public List<ComponenteFamigliaDTO> getListaCompFamiglia(RicercaSoggettoAnagrafeDTO rs) {
		
		List<ComponenteFamigliaDTO> listaComp =  anagrafeDAO.getCompFamigliaByIdExtDPersona(rs);
		
		//aggiungo i dati relativi a luogo di nascita
		if (listaComp !=null) {
			RicercaLuogoDTO rl = new RicercaLuogoDTO();
			rl.setEnteId(rs.getEnteId());
			rl.setUserId(rs.getUserId());
			if (rs.getDtRif()!=null)
				rl.setDtRif(rs.getDtRif());
			SitComune comune=null;
			SitProvincia provincia =null;
			for (ComponenteFamigliaDTO compFam: listaComp) {
				SitDPersona persona = compFam.getPersona();
				compFam.setDatiAnagComponente(compFam.getRelazPar() + " - " + persona.getCognome() + " " + persona.getNome());
				if (persona.getIdExtComuneNascita() !=null) {
					rl.setIdExtComune(persona.getIdExtComuneNascita());
					comune =anagrafeDAO.getComune(rl);
					if (comune!= null) {
						String desComune=comune.getDescrizione()==null? "" : comune.getDescrizione().trim() ;
						String cod = comune.getIdOrig();
						compFam.setDesComNas(desComune);
						compFam.setCodComNas(cod);
					}
				}
				if (persona.getIdExtComuneNascita() != null)  {
					rl.setIdExtProvincia(persona.getIdExtProvinciaNascita());
					provincia=anagrafeDAO.getProvincia(rl);
					if(provincia!=null) {
						String desProvincia=provincia.getDescrizione()==null? "" : provincia.getDescrizione().trim() ;
						compFam.setDesProvNas(desProvincia);
						String sigla = provincia.getSigla()==null? "" : provincia.getSigla().trim() ;
						compFam.setSiglaProvNas(sigla);
					}
						
				}
				
				//valorizzo cittadinanza
				if(compFam.getCittadinanza()==null)
					compFam.setCittadinanza(anagrafeDAO.findCittadinanzaByIdExtStato(persona.getIdExtStato()));
				
			}
		}
		return listaComp; 
	}
	
	@Override
	public List<ComponenteFamigliaDTO> getListaCompFamigliaInfoAggiuntiveByCf(RicercaSoggettoAnagrafeDTO rs) {
			
		List<ComponenteFamigliaDTO> listaComp =  anagrafeDAO.getCompFamigliaByCodFis(rs);
		
		for(ComponenteFamigliaDTO componente: listaComp)
			fillInfoAggiuntiveComponente(componente);
		
		return listaComp;
	}
	
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public ComponenteFamigliaDTO fillInfoAggiuntiveComponente(ComponenteFamigliaDTO componente) {
		
		if(componente.getInfoAggiuntive() == null) {
			componente.setInfoAggiuntive(new AnagraficaDTO());
			componente.getInfoAggiuntive().setPersona(componente.getPersona());
		}
		
		anagrafeDAO.fillInfoAggiuntive(componente.getInfoAggiuntive());
		
		//cittadinanza
		if(componente.getCittadinanza()==null)
			componente.setCittadinanza(anagrafeDAO.findCittadinanzaByIdExtStato(componente.getPersona().getIdExtStato()));
		
		
		//comune nascita
		AmTabComuni comuneNascitaTab = null;
		if(componente.getInfoAggiuntive().getComuneNascita() != null &&
				componente.getInfoAggiuntive().getComuneNascita().getIdOrig().startsWith("0")) {
			String codIstatComune = componente.getInfoAggiuntive().getComuneNascita().getIdOrig();
			comuneNascitaTab = luoghiService.getComuneItaByIstat(codIstatComune);
			if(comuneNascitaTab!=null) {
				componente.setCodComNas(comuneNascitaTab.getCodIstatComune()); //.replaceFirst("0", "")); 
				componente.setDesComNas(comuneNascitaTab.getDenominazione());
			}
		}
		
		//provincia e stato nascita
		if(componente.getInfoAggiuntive().getProvNascita() != null) {
			String siglaProv = componente.getInfoAggiuntive().getProvNascita().getSigla();
			boolean provinciaIta = comuneNascitaTab!=null && comuneNascitaTab.getSiglaProv().equals(siglaProv);
			if(provinciaIta) {
				componente.setSiglaProvNas(siglaProv);
				componente.setCodStatoNas("ITA");
				componente.setIstatStatoNas("1");
				componente.setDesStatoNas("ITALIA");
			} else {	
				AmTabNazioni nazione = luoghiService.getNazioneByCodiceGenerico(siglaProv);
				if(nazione != null) {
					componente.setCodStatoNas(nazione.getSigla());
					componente.setIstatStatoNas(nazione.getCodIstatNazione());
					componente.setDesStatoNas(nazione.getNazione());
				}
			}
		}else{ 
			//Tabella SIT_PROVINCIA è vuota o non contiene il valore - recupero da AM_TAB_COMUNI utilizzando idOrig
			//(Apparentemente sembra corrispondere al Cod.Istat (eliminando uno 0 iniziale)
			if(comuneNascitaTab!=null){
				componente.setSiglaProvNas(comuneNascitaTab.getSiglaProv());
				componente.setCodComNas(comuneNascitaTab.getCodIstatComune());
				componente.setDesComNas(comuneNascitaTab.getDenominazione());
				componente.setCodStatoNas("ITA");
				componente.setIstatStatoNas("1");
				componente.setDesStatoNas("ITALIA");
			}else{ 
				//Comune, stato estero (i primi 3 caratteri di idOrig sono il codIstat dello stato)
				if(componente.getInfoAggiuntive().getComuneNascita()!=null){
					String istatNaz = componente.getInfoAggiuntive().getComuneNascita().getIdOrig().substring(0,3);
					AmTabNazioni nazione = luoghiService.getNazioneByCodiceGenerico(istatNaz);
					if(nazione != null) {
						componente.setCodStatoNas(nazione.getSigla());
						componente.setIstatStatoNas(nazione.getCodIstatNazione());
						componente.setDesStatoNas(nazione.getNazione());
					}
				}
			}
			
		}
		
		// via residenza
		if(componente.getInfoAggiuntive().getVia() != null) {
			String sedime = componente.getInfoAggiuntive().getVia().getViasedime() != null? componente.getInfoAggiuntive().getVia().getViasedime():"";
			String viaResidenza = (sedime + " " + componente.getInfoAggiuntive().getVia().getDescrizione()).trim();
			componente.setIndirizzoResidenza(viaResidenza.trim());
		}
		if(componente.getInfoAggiuntive().getCivico() != null) {
			String civResidenza = ((componente.getInfoAggiuntive().getCivico().getCivLiv1()!= null? componente.getInfoAggiuntive().getCivico().getCivLiv1().replaceFirst ("^0*", ""): "") +
			" " + (componente.getInfoAggiuntive().getCivico().getCivLiv2() != null? componente.getInfoAggiuntive().getCivico().getCivLiv2().replaceFirst ("^0*", ""): "") +
			" " + (componente.getInfoAggiuntive().getCivico().getCivLiv3() != null? componente.getInfoAggiuntive().getCivico().getCivLiv3().replaceFirst ("^0*", ""): "")).trim();
			componente.setCivicoResidenza(civResidenza.isEmpty() ? null : civResidenza);
		}
		
		//comune e provincia residenza
		if(componente.getInfoAggiuntive().getComuneResidenza() != null) {
			componente.setCodComRes(componente.getInfoAggiuntive().getComuneResidenza().getCodIstat());
			componente.setDesComRes(componente.getInfoAggiuntive().getComuneResidenza().getDescrizione());
			
			AmTabComuni comumeRes = luoghiService.getComuneItaByIstat(componente.getCodComRes());
			if(comumeRes!=null){
				componente.setCodComRes(comumeRes.getCodIstatComune());
				componente.setDesComRes(comumeRes.getDenominazione());
				componente.setSiglaProvRes(comumeRes.getSiglaProv());
				componente.setCodStatoRes("ITA");
				componente.setIstatStatoRes("1");
				componente.setDesStatoRes("ITALIA");
			}
		}
		if(componente.getInfoAggiuntive().getComuneResidenzaEmig() != null) {
			if(componente.getInfoAggiuntive().getComuneResidenzaEmig().getIdOrig().startsWith("0")) {
				//italia
				String idOrigCom = componente.getInfoAggiuntive().getComuneResidenzaEmig().getIdOrig();
				idOrigCom = idOrigCom.length()<=6 ? idOrigCom : idOrigCom.substring(idOrigCom.length()-6, idOrigCom.length());
				componente.setCodComRes(idOrigCom);
				componente.setDesComRes(componente.getInfoAggiuntive().getComuneResidenzaEmig().getDescrizione());
				
				AmTabComuni comuneRes = luoghiService.getComuneItaByIstat(componente.getCodComRes());
				if(comuneRes!=null){
					componente.setCodComRes(comuneRes.getCodIstatComune());
					componente.setDesComRes(comuneRes.getDenominazione());
					componente.setSiglaProvRes(comuneRes.getSiglaProv());
					componente.setCodStatoRes("ITA");
					componente.setIstatStatoRes("1");
					componente.setDesStatoRes("ITALIA");
				}
			} else {
				//estero
				AmTabNazioni nazioneRes = luoghiService.getNazioneByCodiceGenerico(componente.getInfoAggiuntive().getComuneResidenzaEmig().getIdOrig().substring(0, 3));
				if(nazioneRes != null){
					componente.setIstatStatoRes(nazioneRes.getCodIstatNazione()); 
					componente.setCodStatoRes(nazioneRes.getSigla());
					componente.setDesStatoRes(nazioneRes.getNazione());
					//componente.setSiglaProvRes(nazioneRes.getCodNazioCie());
				}
			}
		}
		
		
		return componente;
	}

	@Override
	public SitDPersona getPersonaById(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getPersonaById(rs);
	}

	@Override
	public DatiPersonaDTO getDatiPersonaById(RicercaSoggettoAnagrafeDTO rs) {
		SitDPersona pers = anagrafeDAO.getPersonaById(rs);
		if (pers==null)
			return null;
		DatiPersonaDTO datiPers=new DatiPersonaDTO();
		RicercaLuogoDTO rl = new RicercaLuogoDTO();
		rl.setEnteId(rs.getEnteId());
		rl.setUserId(rs.getUserId());
		if (rs.getDtRif()!=null)
			rl.setDtRif(rs.getDtRif());
		SitComune comune=null;
		SitProvincia provincia =null;
		
		rl.setIdExtComune(pers.getIdExtComuneNascita());
		comune =anagrafeDAO.getComune(rl);
		
		if (comune== null)
			comune=new SitComune();
		
		rl.setIdExtProvincia(pers.getIdExtProvinciaNascita());
		provincia=anagrafeDAO.getProvincia(rl);
		
		if(provincia==null)
			provincia=new SitProvincia();
		datiPers.setPersona(pers);
		String desComune=comune.getDescrizione()==null? "" : comune.getDescrizione().trim() ;
		datiPers.setDesComNas(desComune);
		String desProvincia=provincia.getDescrizione()==null? "" : provincia.getDescrizione().trim() ;
		datiPers.setDesProvNas(desProvincia);
		String sigla = provincia.getSigla()==null? "" : provincia.getSigla().trim() ;
		datiPers.setSiglaProvNas(sigla);
		rs.setIdSogg(pers.getIdExt());
		IndirizzoAnagrafeDTO indir = getIndirizzoPersona(rs);
		String indStr = "";
		if (indir !=null) {
			indStr += (indir.getSedimeVia()== null )? "": indir.getSedimeVia().trim();
			indStr += (indir.getDesVia() == null )? "": " " + indir.getDesVia().trim();
			if (indir.getDesVia() != null && ! indir.getDesVia().equals("")){
				indStr += (indir.getCivico() == null )? "": ", " + StringUtils.removeLeadingZero(indir.getCivico().trim());
				indStr += (indir.getCivicoLiv2() == null )? "": " " + StringUtils.removeLeadingZero(indir.getCivicoLiv2().trim());
				indStr += (indir.getCivicoLiv3() == null )? "": " " + StringUtils.removeLeadingZero(indir.getCivicoLiv3().trim());	
			}
			
		}
		datiPers.setIndirizzoResidenza(indStr);
		datiPers.setCittadinanza(anagrafeDAO.findCittadinanzaByIdExtStato(pers.getIdExtStato()));
		return datiPers;
	}

	@Override
	public List<SitComune> getComuni(RicercaLuogoDTO rl) {
		return anagrafeDAO.getComuni(rl);
	}
	
	@Override
	public SitComune belfioreToComune(RicercaLuogoDTO rl) {
		return anagrafeDAO.belfioreToComune(rl);
	}
	
	@Override
	public SitComune getComune(RicercaLuogoDTO rl) {
		return anagrafeDAO.getComune(rl);
	}

	@Override
	public List<SitProvincia> getProvincie(RicercaLuogoDTO rl) {
		return anagrafeDAO.getProvincie(rl);
	}
	
	@Override
	public SitProvincia getProvincia(RicercaLuogoDTO rl) {
		return anagrafeDAO.getProvincia(rl);
	}

	@Override 
	public List<ComponenteFamigliaDTO> getResidentiAlCivico(RicercaIndirizzoAnagrafeDTO ri) {
		List<ComponenteFamigliaDTO> listaComp = anagrafeDAO.getResidentiAlCivico(ri);
		for (ComponenteFamigliaDTO comp: listaComp) {
			SitDPersona p = comp.getPersona();
			comp.setDatiAnagComponente(comp.getIdExtDFamiglia() + " " + comp.getRelazPar() + " - " + p.getCognome() + " " + p.getNome() + " " + p.getCodfisc());
		}
		return listaComp;
	}

	@Override
	public IndirizzoAnagrafeDTO getIndirizzo(RicercaIndirizzoAnagrafeDTO ri) {
		return anagrafeDAO.getIndirizzo(ri);
	}

	@Override
	public List<AnagraficaDTO> getAnagrafeByCF(RicercaSoggettoDTO rs) throws AnagrafeException{
		return anagrafeDAO.getAnagrafeByCF(rs);
	}
	
	@Override
	public AttrPersonaDTO getAttributiPersonaByCF(RicercaSoggettoAnagrafeDTO rs)throws AnagrafeException{
		return anagrafeDAO.getAttributiPersonaByCF(rs);
	}
	
	@Override
	public String getIdCivicoByIndirizzo(RicercaCivicoDTO rc){
		return anagrafeDAO.getIdCivicoByIndirizzo(rc);
	} 
	
	@Override
	public SitDVia getViaByPrefissoDescr(RicercaCivicoDTO rc){
		return anagrafeDAO.getViaByPrefissoDescr(rc);
	}
	
	 @Override
	public DatiCivicoAnagrafeDTO getDatiCivicoAnagrafe(RicercaCivicoDTO rc){
		return anagrafeDAO.getDatiCivicoAnagrafe(rc);
	}

	@Override
	public Long getNumeroFamiglieResidentiAlCivico(RicercaCivicoDTO rc) {
		return anagrafeDAO.getNumeroFamiglieResidentiAlCivico(rc.getIdCivico(),rc.getDataRif()); 
	}
	
	@Override
	public boolean checkResidenza(RicercaLuogoDTO rs){
		
		boolean ret = false;
		SitEnte c = anagrafeDAO.getEnteByDescrizione(rs);
		if (c==null)
			return false;
		
		if(c.getCodent()== null)
			logger.error(" metodo CheckResidenza per il comune: " + rs.getDesComune() + ". IL BELFIORE E' NULL");
		
		if(c.getCodent()!= null && c.getCodent().equals(rs.getEnteId()))
			ret=true;
		return ret; 
	}
	
	@Override
	public boolean verificaResidenzaByCFAllaData(RicercaSoggettoAnagrafeDTO rs){
		return anagrafeDAO.verificaResidenzaByCFAllaData(rs); 
	}
	
	@Override
	public boolean verificaResidenzaByCogNomDtNascAllaData(RicercaSoggettoAnagrafeDTO rs){
		return anagrafeDAO.verificaResidenzaByCogNomDtNascAllaData(rs);
	}

	
	@Override
	public ComuneProvinciaDTO getDescrizioneComuneProvByIdExt(CommonDataIn dataIn){
		return anagrafeDAO.getDescrizioneComuneProvByIdExt((String)dataIn.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<IndirizzoAnagDTO> getIndirizzoResidenzaByCodFisc(RicercaSoggettoAnagrafeDTO rs) {
		return anagrafeDAO.getIndirizzoResidenzaByCodFisc(rs);
	}
	
	@Override
	public List<IndirizzoAnagDTO> getIndirizzoResidenzaByNomeCiv(RicercaCivicoDTO rc) {
		return anagrafeDAO.getIndirizzoResidenzaByNomeCiv(rc);
	}
	
	@Override
	public List<SitDVia> getIndirizziLike(RicercaIndirizzoAnagrafeDTO ri) {
		return anagrafeDAO.getIndirizziLike(ri);
	}
	
	@Override
	public List<SitDCivicoV> getCivicoByIdExtDVia(RicercaIndirizzoAnagrafeDTO ri) {
		return anagrafeDAO.getCivicoByIdExtDVia(ri);
	}
	
	@Override
	public List<StatoCivile> getListaStatoCivile(CeTBaseObject cet){
		
		List<StatoCivile> lstdto = new ArrayList<StatoCivile>();
		List<SitDStaciv> lstjpa = anagrafeDAO.getListaStatoCivile();
		
		for(SitDStaciv jpa : lstjpa){
			StatoCivile sc = new StatoCivile();
			sc.setCodice(jpa.getIdOrig());
			sc.setDescrizione(jpa.getDescrizione());
			sc.setIdExt(jpa.getIdExt());
			lstdto.add(sc);
		}
		return lstdto;
	}

	@Override
	public String getCittadinanzaByIdExtStato(BaseGenericDTO dto) {
		return anagrafeDAO.findCittadinanzaByIdExtStato((String)dto.getObj());
	}

}
