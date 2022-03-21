package it.umbriadigitale.soclav.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.umbriadigitale.soclav.model.VArbiCasi;
import it.umbriadigitale.soclav.model.anpal.RdCAnpalBeneficiario;
import it.umbriadigitale.soclav.model.gepi.RdCGepiComponenteFamiglia;
import it.umbriadigitale.soclav.model.gepi.RdCGepiDomanda;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSoc;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSocPK;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLav;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLavPK;
import it.umbriadigitale.soclav.repository.AnpalRepository;
import it.umbriadigitale.soclav.repository.CartellaSocialeRepository;
import it.umbriadigitale.soclav.repository.ConsensoLavToSocRepository;
import it.umbriadigitale.soclav.repository.ConsensoSocToLavRepository;
import it.umbriadigitale.soclav.repository.GepiRepository;
import it.umbriadigitale.soclav.service.dto.CartellaSocialeDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiBeneficiarioDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiDomandaDTO;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.DatiPersonali;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Indirizzo;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Recapito;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;

@Service
public class AmbitiSocialeServiceImpl implements IAmbitiSocialeService {

	@Autowired
	@Qualifier("gepiRepository")
	private GepiRepository repo;
	
	@Autowired
	@Qualifier("anpalRepository")
	private AnpalRepository anpalRepo;
	
	@Autowired
	@Qualifier("consensoLavToSocRepository")
	private ConsensoLavToSocRepository consensoLavToSocRepo;
	
	@Autowired
	@Qualifier("consensoSocToLavRepository")
	private ConsensoSocToLavRepository consensoSocToLavRepo;
	
	@Autowired
	@Qualifier("cartellaSocialeRepository")
	private CartellaSocialeRepository carSocialeRepo;
	
	@Override
	public Integer count(List<String> entiAbilitati, int first, int size) {
		Page<RdCGepiDomanda> page = filtraEnti(entiAbilitati, first, size);
		return page.getNumberOfElements();
	}
	
	private Page<RdCGepiDomanda> filtraEnti(List<String> entiAbilitati, int first, int size) {
		Sort sort = new Sort(Sort.Direction.ASC, "nomeCompleto");
		Pageable pageable = new PageRequest(first, size, sort);
		Page<RdCGepiDomanda> page;
		if(entiAbilitati.size()==1 && entiAbilitati.get(0).equals("ALL"))
			page = repo.findAllRichiedenti(pageable);
		else	
			page = repo.findRichiedentiByEnte(entiAbilitati, pageable);
		return page;
	}
	
	@Override
	public List<GePiDomandaDTO> search(List<String> entiAbilitati, int first, int size) {
		Page<RdCGepiDomanda> page = filtraEnti(entiAbilitati, first, size);
		List<GePiDomandaDTO> lstDomande = new ArrayList<GePiDomandaDTO>();
		for(RdCGepiDomanda ben : page.getContent()) {
			List<GePiBeneficiarioDTO> lstFamiliari = new ArrayList<GePiBeneficiarioDTO>();
			GePiDomandaDTO domanda = new GePiDomandaDTO();
			domanda.setCfRichiedente(ben.getCf());
			domanda.setProtocolloINPS(ben.getCodIdentificativoDomanda());
			domanda.setDataDomanda(ben.getDataPresentazioneDomanda());
			domanda.setNomeCompletoRichiedente(ben.getNomeCompleto());
			
			domanda.setAssegnante(ben.getAssegnante());
			domanda.setAssegnatario(ben.getAssegnatario());
			domanda.setDataAssegnazione(ben.getDataAssegnazioneDomanda());
			
			Indirizzo residenza = new Indirizzo(ben.getResidenzaIndirizzo(), ben.getResidenzaCap(), ben.getResidenzaComuneCod(), ben.getResidenzaComuneDes());
			domanda.setResidenza(residenza);
			
			List<RdCGepiComponenteFamiglia> fami = ben.getComponenteFamiglia();
			for(RdCGepiComponenteFamiglia fam : fami) {
				GePiBeneficiarioDTO famBen = this.popolaAnagrafica(fam, residenza, domanda.getProtocolloINPS());
				famBen.setRichiedente(famBen.getDatipersonali().getCodicefiscale().equalsIgnoreCase(domanda.getCfRichiedente()));
				
				lstFamiliari.add(famBen);
			}
			domanda.setFamiliari(lstFamiliari);
			
			lstDomande.add(domanda);
		}
		
	      return lstDomande;
	}
	
	private GePiBeneficiarioDTO popolaAnagrafica(RdCGepiComponenteFamiglia ben, Indirizzo residenza, String protocolloINPS) {
		
		GePiBeneficiarioDTO out = new GePiBeneficiarioDTO();
		
		DatiPersonali datipersonali = new DatiPersonali();
		datipersonali.setCognome(ben.getNomeCompleto());
		datipersonali.setNome(null);
		datipersonali.setCodicefiscale(ben.getId().getCf());
		datipersonali.setSesso(ben.getGenere());
		datipersonali.setDatanascita(ben.getDataNascita());
		
		datipersonali.setCodcomune(ben.getLuogoNascitaCod());
		datipersonali.setDesLuogoNascita(ben.getLuogoNascitaDes());
		
		//String cittadinanza = findDecodifica("CITTADINANZA", ben.getNazionalita());
		//datipersonali.setCodcittadinanza(ben.getCittadinanzaCod());
		datipersonali.setDesCittadinanza(ben.getNazionalita());
		out.setDatipersonali(datipersonali);
		
		Recapito recapito = new Recapito();
		recapito.setEmail(ben.getEmail());
		recapito.setTelefono(ben.getTelefono());
		recapito.setCellulare(ben.getCellulare());
		out.setRecapiti(recapito);
		
		//Indirizzo residenza = new Indirizzo(ben.getIndirizzo(),null , null, null);
		out.setResidenza(residenza);
		
		List<VArbiCasi> cartelle = carSocialeRepo.findCartelle(datipersonali.getCodicefiscale());
		out.setExistsCartellaSociale(cartelle!=null && !cartelle.isEmpty());
		
		Boolean canViewSAP = canViewDatiLavoro(datipersonali.getCodicefiscale(), residenza.getCodcomune());
		if(canViewSAP)
			out.setCodSAP(this.findCodSAP(datipersonali.getCodicefiscale(), protocolloINPS));
		
		RdcConsensiSocToLav consenso = this.findSocToLav(datipersonali.getCodicefiscale(), residenza.getCodcomune());
		if(consenso!=null) out.setConsensoRilasciato(consenso.getFlagConsenso());
		
		out.setCondOccupazionale(ben.getCondOccupazionale());
		out.setConvivente(ben.getConvivente());
		out.setDidFirmata(ben.getDidFirmata());
		out.setDisabilita(ben.getDisabilita());
		out.setFrequenzaCorsi(ben.getFrequenzaCorsi());
		out.setNaspi(ben.getNaspi());
		out.setPeriodoDisoccupazione(ben.getPeriodoDisoccupazione());
		out.setPrestazioniINPS(ben.getPrestazioniErogInps());
		out.setQualificaProfessionale(ben.getQualificaProfessionale());
		out.setRelazioneParentale(ben.getRelazioneParentale());
		out.setStatoBeneficio(ben.getStatoBeneficio());
		out.setStatoPattoLavoro(ben.getStatoPattoLavoro());
		out.setStudente(ben.getStudente());
		out.setTitoloSoggiorno(ben.getTitoloSoggiorno());
		out.setTitoloStudio(ben.getTitoloStudio());
		
		out.setUltimaModifica(ben.getDtMod()!=null ? ben.getDtMod() : ben.getDtIns());
		
		return out;
	}
	
	public RdcConsensiSocToLav findSocToLav(String cf, String ente) {
		RdcConsensiSocToLavPK pk = new RdcConsensiSocToLavPK();
		pk.setCf(cf.toUpperCase());
		pk.setCodEnteFrom(ente.toUpperCase());
		return consensoSocToLavRepo.findOne(pk);
	}
	
	@Override
	public boolean canViewDatiLavoro(String cf, String ente) {
		boolean val = false;
		if(!StringUtils.isBlank(cf) && !StringUtils.isBlank(ente)) {
			RdcConsensiLavToSocPK pk = new RdcConsensiLavToSocPK();
			pk.setCf(cf.toUpperCase());
			pk.setCodEnteTo(ente.toUpperCase());
			RdcConsensiLavToSoc consenso = consensoLavToSocRepo.findOne(pk);
			val = (consenso!=null && consenso.getFlagConsenso()!=null && consenso.getFlagConsenso().booleanValue());
		}
		return val;
	}
	
	
	
	@Override 
	public RdcConsensiSocToLav salvaConsenso(String cf, Boolean val, String ente) {
		RdcConsensiSocToLav consenso = null;
		if(val!=null && !StringUtils.isBlank(cf)) {
			RdcConsensiSocToLavPK pk = new RdcConsensiSocToLavPK();
			pk.setCf(cf.toUpperCase());
			pk.setCodEnteFrom(ente.toUpperCase());
			consenso = consensoSocToLavRepo.findOne(pk);
			if(consenso==null) {
				consenso = new RdcConsensiSocToLav();
				consenso.setId(pk);
				consenso.setDtIns(new Date());
			}else {
				consenso.setDtMod(new Date());
			}
			consenso.setFlagConsenso(val);
			consenso = consensoSocToLavRepo.save(consenso);
		}
		return consenso;
	}
	
	@Override
	public List<CartellaSocialeDTO> findCartelleSociali(String cf, String ente) {
		List<CartellaSocialeDTO> lstcs = new ArrayList<CartellaSocialeDTO>();
		List<VArbiCasi> lst = carSocialeRepo.findCartelle(cf);
		for(VArbiCasi jpa : lst) {
			CartellaSocialeDTO cs = new CartellaSocialeDTO();
			cs.setZonaSociale(jpa.getZonaSociale());
			cs.setOrganizzazione(jpa.getOrganizzazione());
			cs.setSettore(jpa.getSettore());
			
			cs.setCognome(jpa.getCognome());
			cs.setNome(jpa.getNome());
			cs.setCf(jpa.getCf());
			cs.setDataNascita(jpa.getDataNascita());
			
			cs.setStatoIter(jpa.getIter());
			cs.setDataIter(jpa.getDataIter());
			cs.setCategoriaSoc(jpa.getCategoria());
			
			lstcs.add(cs);
		}
		return lstcs;
	}
	
	private String findCodSAP(String cf, String protocolloINPS) {
		String codSAP = null;
		RdCAnpalBeneficiario anpal  = anpalRepo.findByCFAndNumProtINPS(cf, protocolloINPS);
		if(anpal!=null) codSAP = anpal.getCodSap();
		return codSAP;
	}

	@Override
	public List<String> loadListaProtocolli() {
		List<String> lstProtocolli = new ArrayList<String>();
		List<RdCGepiDomanda> result = repo.findAllRichiedenti();
		for(RdCGepiDomanda d : result) {
			if(!lstProtocolli.contains(d))
				lstProtocolli.add(d.getCodIdentificativoDomanda());
		}
		return lstProtocolli;	
	}

}
