package it.webred.ct.data.access.basic.concedilizie;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.concedilizie.dao.ConcessioniEdilizieDAO;
import it.webred.ct.data.access.basic.concedilizie.dto.ConcessioneDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.IndirizzoConcessioneDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.SoggettoConcessioneDTO;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisure;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisureDoc;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioni;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless 
public class ConcessioniEdilizieServiceBean extends CTServiceBaseBean implements ConcessioniEdilizieService {
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/IndiceCorrelazioneServiceBean")
	private IndiceCorrelazioneService indiceService;	
	
	@Autowired
	private ConcessioniEdilizieDAO concEdilizieDAO;
	private static final long serialVersionUID = 1L;
	@Override
	public SitCConcessioni getConcessioneById(RicercaConcEdilizieDTO ro) {
		return concEdilizieDAO.getConcessioneById(ro);
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByUiu(RicercaConcEdilizieDTO rce){
		return concEdilizieDAO.getConcessioniByUiu(rce);
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByFabbricato(RicercaOggettoCatDTO ro) {
		return concEdilizieDAO.getConcessioniByFabbricato(ro);
	}
	
	@Override
	public List<SoggettoConcessioneDTO> getSoggettiByConcessione(RicercaConcEdilizieDTO ro) {
		return concEdilizieDAO.getSoggettiByConcessione(ro);
	}
	
	@Override
	public List<IndirizzoConcessioneDTO> getIndirizziByConcessione(RicercaConcEdilizieDTO ro) {
		List<SitCConcIndirizzi> lista = concEdilizieDAO.getIndirizziByConcessione(ro);
		List<IndirizzoConcessioneDTO> listaInd = new ArrayList<IndirizzoConcessioneDTO>();
		
		for(SitCConcIndirizzi ind : lista){
			IndirizzoConcessioneDTO indirizzo = new IndirizzoConcessioneDTO();
			indirizzo.setId(ind.getId());
			indirizzo.setIdExtConc(ind.getIdExtCConcessioni());
			String sedime = ind.getSedime()!=null ? ind.getSedime().trim() : "";
			String via = ind.getIndirizzo()!=null ? ind.getIndirizzo().trim() : "";
			indirizzo.setIndirizzo((sedime+" "+via).trim());
			indirizzo.setCivico(ind.getCivLiv1()!=null ? ind.getCivLiv1().trim() : "");
			listaInd.add(indirizzo);
		}
		
		return listaInd;
	}
	
	@Override
	public String getStringaImmobiliByConcessione(RicercaConcEdilizieDTO ro) {
		return concEdilizieDAO.getStringaImmobiliByConcessione(ro);
	}
	@Override
	public List<ConcessioneDTO> getDatiConcessioniByFabbricato(RicercaOggettoCatDTO ro) {
		List<ConcessioneDTO> lista=new ArrayList<ConcessioneDTO>();
		
		List<SitCConcessioni> listaConc =  getConcessioniByFabbricato(ro);
		if(listaConc==null)
			return lista;
		
		ConcessioneDTO datiConc =null;
		RicercaConcEdilizieDTO rc = new RicercaConcEdilizieDTO();
		rc.setUserId(ro.getUserId());
		rc.setEnteId(ro.getEnteId());
		for (SitCConcessioni conc:listaConc) {
			if (ro.getDtVal()!= null && conc.getProtocolloData() !=null) {
				if (conc.getProtocolloData().after(ro.getDtVal()) )
					continue;
			}
			datiConc = new ConcessioneDTO();
			rc.setIdExtConc(conc.getIdExt());
			datiConc.valorizzaDatiConc(conc, getSoggettiByConcessione(rc), getStringaImmobiliByConcessione(rc),this.getIndirizziByConcessione(rc));
			lista.add(datiConc);
		}
		return lista;
	}
	
	@Override
	public List<ConcessioneDTO> getDatiConcessioniByImmobile(RicercaConcEdilizieDTO ro) {
		List<ConcessioneDTO> lista=new ArrayList<ConcessioneDTO>();
		
		List<SitCConcessioni> listaConc =  getConcessioniByUiu(ro);
		if(listaConc==null)
			return lista;
		
		ConcessioneDTO datiConc =null;
		RicercaConcEdilizieDTO rc = new RicercaConcEdilizieDTO();
		rc.setUserId(ro.getUserId());
		rc.setEnteId(ro.getEnteId());
		for (SitCConcessioni conc:listaConc) {
			if (ro.getDtRif()!= null && conc.getProtocolloData() !=null) {
				if (conc.getProtocolloData().after(ro.getDtRif()))
					continue;
			}
			datiConc = new ConcessioneDTO();
			rc.setIdExtConc(conc.getIdExt());
			datiConc.valorizzaDatiConc(conc, getSoggettiByConcessione(rc), getStringaImmobiliByConcessione(rc),this.getIndirizziByConcessione(rc));
			lista.add(datiConc);
		}
		return lista;
	}
	
	@Override
	public List<String> getListaProgressivoAnno(RicercaConcEdilizieDTO ro){
		return concEdilizieDAO.getListaProgressivoAnno();
	}
	@Override
	public List<String> getListaProtocolloAnno(RicercaConcEdilizieDTO ro){
		return concEdilizieDAO.getListaProtocolloAnno();
	}
	@Override
	public List<String> getListaTitoliSoggetto(RicercaConcEdilizieDTO ro){
		return concEdilizieDAO.getListaTitoliSoggetto();
	}
	@Override
	public List<String> getSuggestionVie(RicercaCivicoDTO rc){
		return concEdilizieDAO.getSuggestionVie(rc);
	}
	@Override
	public List<String> getSuggestionCiviciByVia(RicercaCivicoDTO rc){
		return concEdilizieDAO.getSuggestionCiviciByVia(rc);
	}
	@Override
	public List<String> getSuggestionSoggetti(RicercaSoggettoDTO rs){
		return concEdilizieDAO.getSuggestionSoggetti(rs.getDenom());
	}

	@Override
	public List<SitCConcessioni> getConcessioniSuCiviciCatasto(RicercaCivicoDTO rc) {
		
		return concEdilizieDAO.getConcessioniSuCiviciCatasto(rc);
	}
	
	@Override
	public List<SitCConcessioni> getConcessioniByIndirizziByFoglioParticella(RicercaOggettoCatDTO dto) {		
		return concEdilizieDAO.getConcessioniByIndirizziByFoglioParticella(dto.getFoglio(), dto.getParticella());
	}


	public List<ConcVisuraDTO> getVisureCiviciDelFabbricato(RicercaOggettoCatDTO ro) {
		List<ConcVisuraDTO> listaVis=new ArrayList<ConcVisuraDTO>() ;
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		cet.setSessionId(ro.getSessionId());
		
		KeyFabbricatoDTO keyFabbr = new KeyFabbricatoDTO(cet, ro.getEnteId(), ro.getSezione(), ro.getFoglio(), ro.getParticella(), new Date());
		
		String ENTE_SORGENTE = "35";
		String TIPO_INFO_OGG= "1";
		List<Object> lista = this.getCiviciCorrelatiFromFabbricato(keyFabbr, null, ENTE_SORGENTE, TIPO_INFO_OGG);
		
		for(Object o : lista){
			ConcEdilizieVisure cc = (ConcEdilizieVisure)o;
			
			
			ConcVisuraDTO dto = getVisuraById(cc.getStringId());	
			
			Date dtRif = ro.getDtVal()!=null ? ro.getDtVal() : new Date();
			
			Date data = dto.getDataDoc();
			if(data!=null && data.compareTo(dtRif)<=0)
				listaVis.add(dto);
			
			if(data==null)
				listaVis.add(dto);
			
		}
		return listaVis;
			
	}
	
	@Override
	public List<ConcessioneDTO> getDatiConcCiviciDelFabbricato(RicercaOggettoCatDTO ro){
		List<ConcessioneDTO> listaConc=new ArrayList<ConcessioneDTO>() ;
		
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(ro.getEnteId());
		cet.setUserId(ro.getUserId());
		cet.setSessionId(ro.getSessionId());
		
		KeyFabbricatoDTO keyFabbr = new KeyFabbricatoDTO(cet, ro.getEnteId(), ro.getSezione(), ro.getFoglio(), ro.getParticella(), new Date());
		
		String ENTE_SORGENTE = "3";
		String TIPO_INFO_OGG= "2";
		List<Object> lista = this.getCiviciCorrelatiFromFabbricato(keyFabbr, null, ENTE_SORGENTE, TIPO_INFO_OGG);
		
		if(lista==null  || lista.size()==0)
			return listaConc;
		
		ConcessioneDTO dto =null;
		RicercaConcEdilizieDTO rc = new RicercaConcEdilizieDTO();
		rc.setUserId(ro.getUserId());
		rc.setEnteId(ro.getEnteId());
		Date dtRif = ro.getDtVal()!=null ? ro.getDtVal() : new Date();
		
		logger.debug("Trovati in Concessioni ["+lista.size()+"] civici correlati al fabbricato ["+keyFabbr+"]");
		String key= "";
		if (ro.getSezione()!=null )  
			key =StringUtils.removeLeadingZero(ro.getSezione().trim()) + "|";
		key  += StringUtils.removeLeadingZero(ro.getFoglio().trim()) + "|" + StringUtils.removeLeadingZero(ro.getParticella().trim());
		
		for (Object o:lista) {
				
			SitCConcIndirizzi conInd = (SitCConcIndirizzi)o; 
			
			dto = new ConcessioneDTO();
			rc.setIdConc(conInd.getId());
			SitCConcessioni conc = concEdilizieDAO.getConcessioneById(rc);
			
			if(conc!=null){
				//Ricerco con idExtConcessione
				rc.setIdExtConc(conc.getIdExt());
				
				//VOGLIO AVERE GLI OGGETTI AI CIVICI DEL FABBRICATO NON ATTRIBUITI ALLA CHIAVE FABBRICATO, 
				//QUINDI SCARTO LE CONCESSIONI CHE HANNO ALMENO UN IMMOBILE RIFERITO AL FOGLIO,PARTICELLA CORRISPONDENTE AL FABBRICATO
				List<SitCConcessioniCatasto> listaImm = concEdilizieDAO.getListaImmobiliByConcessione(rc);
				boolean trovato = false; 
				int i=0;
				String currKey="";String sezione=""; String foglio=""; String numero=""; 
				while(i<listaImm.size() && !trovato){
					SitCConcessioniCatasto cc = listaImm.get(i);
					
					currKey="";
					sezione=cc.getSezione() !=null ? StringUtils.removeLeadingZero(cc.getSezione().trim()) : "" ;
					foglio=cc.getFoglio() !=null ? StringUtils.removeLeadingZero(cc.getFoglio().trim()) : "" ;
					numero=cc.getParticella()!=null ? StringUtils.removeLeadingZero(cc.getParticella().trim()) : "" ;
					if (ro.getSezione()!= null && !sezione.equals("")) 
						currKey=sezione +  "|" ;
					currKey += foglio+ "|" + numero;
					
					if (currKey.equals(key))
						trovato = true;
				
					i++;
				}
				
				if(!trovato){
					dto.valorizzaDatiConc(conc, getSoggettiByConcessione(rc), getStringaImmobiliByConcessione(rc), this.getIndirizziByConcessione(rc) );
				
					Date data = dto.getDataProt();
					if(data!=null && data.compareTo(dtRif)<=0)
						listaConc.add(dto);
					
					if(data==null)
						listaConc.add(dto);
				}
			
			}
		}
		return listaConc;
		
	}
	
	
	
	private ConcVisuraDTO getVisuraById(String id){
		
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		ConcVisuraDTO dto = new ConcVisuraDTO();
			
		ConcEdilizieVisure c = concEdilizieDAO.getVisuraById(id);
		if(c!=null){
			dto.setId(c.getStringId());
			dto.setNumProtGen(c.getNumProtGen());
			dto.setNumProtSett(c.getNumProtSett());
			dto.setAnnoProtGen(c.getAnnoProtGen());
		    dto.setTipoAtto(c.getTipoAtto());
		    dto.setNumeroAtto(c.getNumeroAtto());
		   
		    Date dataDoc = null;
		    try {
				dataDoc = yyyyMMdd.parse(c.getDataDoc());
			} catch (Exception e) {
				logger.error("Errore non bloccante parsing dataDoc "+e.getMessage(),e);
			}
		    dto.setDataDoc(dataDoc);
			
			dto.setPrefisso(c.getTpv());
			dto.setNomeVia(c.getNomeVia()!=null ? c.getNomeVia().trim() : null);
			dto.setCivico(c.getCivico());
			dto.setCivicoSub(c.getCivicoSub()!=null ? c.getCivicoSub().trim() : null);
			
			dto.setAltriciv(c.getAltriciv()!=null ? c.getAltriciv().trim() : null);
			dto.setAltravia(c.getAltravia()!=null ? c.getAltravia().trim() : null);
			
			dto.setNomeIntestatario(c.getNomeIntestatario()!=null ? c.getNomeIntestatario().trim() : null);
			
			dto.setDestinazione(c.getDestinazione());
			dto.setPrivata(BigDecimal.ZERO.compareTo(c.getPrivata())==0 ? false : true);
			dto.setRiparto(c.getRiparto());
		    dto.setTipologia(c.getTipologia());
		    dto.setIdFile(c.getInxdoc());
		    dto.setVincoloAmbientale(c.getVincoloAmbientale());
		    
		    ConcEdilizieVisureDoc file  = concEdilizieDAO.getDocVisuraById(c.getInxdoc());
		    if(file!=null){
		    	dto.setPathFile(file.getFilee());
		    }
		}
		
		return dto;
	}
	
	
	@Override
	public ConcVisuraDTO getVisuraById(RicercaConcEdilizieDTO rc) {
		ConcVisuraDTO dto = null;
		
		String id = rc.getIdConc();
		
		if(id!=null && id.length()>0)
			dto = getVisuraById(id);
		
		return dto;
		
	}
	
	

	public List<Object> getCiviciCorrelatiFromFabbricato(KeyFabbricatoDTO keyFabbr,String progEs, String destFonte, String destProgEs) {
		RicercaIndiceDTO ri= new RicercaIndiceDTO();
		ri.setEnteId(keyFabbr.getEnteId());
		ri.setUserId(keyFabbr.getUserId());
		ri.setObj(keyFabbr);
		ri.setProgressivoEs(progEs);
		ri.setDestFonte(destFonte); //
		ri.setDestProgressivoEs(destProgEs);
		List<Object> lista = indiceService.getCiviciCorrelatiFromFabbricato(ri);
		return lista;
	}
	
	@Override
	public List<String> getVisureTipiAtto(RicercaConcEdilizieDTO rc){
		return concEdilizieDAO.getVisureTipiAtto();
	}

	
	
}
