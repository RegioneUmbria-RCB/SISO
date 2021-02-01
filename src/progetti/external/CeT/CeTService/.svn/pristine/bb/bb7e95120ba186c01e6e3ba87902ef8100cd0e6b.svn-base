package it.webred.ct.data.access.basic.catasto;

import it.webred.ct.data.access.basic.CTServiceBaseBean;

import it.webred.ct.data.access.basic.catasto.dao.CatastoDAO;
import it.webred.ct.data.access.basic.catasto.dto.CatastoBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.CoordBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.DataVariazioneImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.DettaglioCatastaleImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.ImmobileBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.ImmobiliAccatastatiOutDTO;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.catasto.dto.PartDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParticellaInfoDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParticellaKeyDTO;
import it.webred.ct.data.access.basic.catasto.dto.PlanimetriaComma340DTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.SintesiImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.SoggettoCatastoDTO;
import it.webred.ct.data.access.basic.catasto.dto.SoggettoDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoDerivazioneDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoPerSoggDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.catasto.LoadCatUiuId;
import it.webred.ct.data.model.catasto.SitRepTarsu;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.catasto.Siticomu;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitideco;
import it.webred.ct.data.model.catasto.Sitidstr;
import it.webred.ct.data.model.catasto.SitiediVani;
import it.webred.ct.data.model.catasto.Sitipart;
import it.webred.ct.data.model.catasto.Sitisuolo;
import it.webred.ct.data.model.catasto.Sititrkc;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.support.audit.AuditStateless;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class CatastoImmobiliServiceBean
 */  
@Stateless 
public class CatastoServiceBean extends CTServiceBaseBean implements CatastoService {
  
	private static final long serialVersionUID = 1L;
	@Autowired 
	private CatastoDAO catastoDAO;
	
	@Override
	public String[] getLatitudineLongitudine(RicercaOggettoCatDTO rc){
		
		return catastoDAO.getLatitudineLongitudine(rc.getFoglio(), rc.getParticella(), rc.getCodEnte());
	}
	
	
	
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input){
		
		List<ImmobiliAccatastatiOutDTO> listaOut = catastoDAO.getImmobiliAccatastatiByDatiSoggetto(input);
		
		logger.debug("Trovati "+listaOut.size()+" immobili.");
		
		if(input.isRicercaRegime()){
			//Recupero regime di titolarità
			for(int i=0; i<listaOut.size(); i++){
				ImmobiliAccatastatiOutDTO dto = listaOut.get(i);
				String regime = catastoDAO.getRegimeImmobili(input.getEnteId(), dto.getFoglio(), dto.getNumero(), dto.getSub(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setRegime(regime);
				dto.setDescRegime(getDescRegime(regime));
				listaOut.set(i, dto);
			}
		}
		
		if(input.isRicercaSoggCollegato()){
			//Recupero soggetto collegato
			for(int i=0; i<listaOut.size(); i++){
				ImmobiliAccatastatiOutDTO dto = listaOut.get(i);
				String soggCollegato = catastoDAO.getSoggettoCollegatoImmobili(input.getEnteId(), dto.getFoglio(), dto.getNumero(), dto.getSub(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setSoggCollegato(soggCollegato);
				listaOut.set(i, dto);
			}
		}
		
		/*	for(ImmobiliAccatastatiOutputDTO dto : lista){
			
			RicercaOggettoCatDTO ro = new RicercaOggettoCatDTO();
			ro.setEnteId(input.getEnteId());
			
			ArrayList<IndirizzoDTO> locCat = catastoDAO.getLocalizzazioneCatastale(ro);
			
			if(locCat.size()>0){
				ImmobiliAccatastatiOutputDTO newDto = new ImmobiliAccatastatiOutputDTO();
				
				
				
			}else{
				listaOut.add(dto);
			}
			
			
		}*/
		
		return listaOut;
	}//-------------------------------------------------------------------------
	
	
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input){
	
		List<TerrenoPerSoggDTO> listaOut = catastoDAO.getTerreniAccatastatiByDatiSoggetto(input);
		
		if(input.isRicercaRegime()){
			//Recupero regime di titolarità
			for(int i=0; i<listaOut.size(); i++){
				TerrenoPerSoggDTO dto = listaOut.get(i);
				String regime = catastoDAO.getRegimeTerreni(input.getEnteId(), Long.toString(dto.getFoglio()), dto.getParticella(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setRegime(regime);
				dto.setDescRegime(getDescRegime(regime));
				listaOut.set(i, dto);
			}
		}
		
		if(input.isRicercaSoggCollegato()){
			//Recupero soggetto collegato
			for(int i=0; i<listaOut.size(); i++){
				TerrenoPerSoggDTO dto = listaOut.get(i);
				String soggCollegato = catastoDAO.getSoggettoCollegatoTerreni(input.getEnteId(), Long.toString(dto.getFoglio()), dto.getParticella(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setSoggCollegato(soggCollegato);
				listaOut.set(i, dto);
			}
		}
		
		return listaOut;
	}
	
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByPkCuaa(RicercaSoggettoCatDTO input){
		
		List<ImmobiliAccatastatiOutDTO> listaOut = catastoDAO.getImmobiliAccatastatiByPkCuaa(input);
		
		if(input.isRicercaRegime()){
			//Recupero regime di titolarità
			for(int i=0; i<listaOut.size(); i++){
				ImmobiliAccatastatiOutDTO dto = listaOut.get(i);
				String regime = catastoDAO.getRegimeImmobili(input.getCodEnte(), dto.getFoglio(), dto.getNumero(), dto.getSub(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setRegime(regime);
				dto.setDescRegime(getDescRegime(regime));
				listaOut.set(i, dto);
			}
		}
		
		if(input.isRicercaSoggCollegato()){
			//Recupero soggetto collegato
			for(int i=0; i<listaOut.size(); i++){
				ImmobiliAccatastatiOutDTO dto = listaOut.get(i);
				String soggCollegato = catastoDAO.getSoggettoCollegatoImmobili(input.getCodEnte(), dto.getFoglio(), dto.getNumero(), dto.getSub(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setSoggCollegato(soggCollegato);
				listaOut.set(i, dto);
			}
		}
		
		return listaOut;
		
	}
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByPkCuaa(RicercaSoggettoCatDTO input){
		
		List<TerrenoPerSoggDTO> listaOut = catastoDAO.getTerreniAccatastatiByPkCuaa(input);
		
		if(input.isRicercaRegime()){
			//Recupero regime di titolarità
			for(int i=0; i<listaOut.size(); i++){
				TerrenoPerSoggDTO dto = listaOut.get(i);
				String regime = catastoDAO.getRegimeTerreni(input.getCodEnte(), Long.toString(dto.getFoglio()), dto.getParticella(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setRegime(regime);
				dto.setDescRegime(getDescRegime(regime));
				listaOut.set(i, dto);
			}
		}
		
		if(input.isRicercaSoggCollegato()){
			//Recupero soggetto collegato
			for(int i=0; i<listaOut.size(); i++){
				TerrenoPerSoggDTO dto = listaOut.get(i);
				String soggCollegato = catastoDAO.getSoggettoCollegatoTerreni(input.getCodEnte(), Long.toString(dto.getFoglio()), dto.getParticella(), dto.getSubalterno(), dto.getPkCuaa());
				dto.setSoggCollegato(soggCollegato);
				listaOut.set(i, dto);
			}
		}
		
		return listaOut;
	}
	
	protected String getDescRegime(String regime) {
		String descRegime = "-";
		if (regime != null) {
			if (regime.equalsIgnoreCase("C")) {
				descRegime = "COMUNIONE";
			} else if (regime.equalsIgnoreCase("P")) {
				descRegime = "BENE PERSONALE";
			} else if (regime.equalsIgnoreCase("S")) {
				descRegime = "IN SEPARAZIONE";
			} else if (regime.equalsIgnoreCase("D")) {
				descRegime = "IN COMUNIONE DE RESIDUO";
			}
		}
		return descRegime;
	}
	
	public Sitidstr getViaByPrefissoDescr(RicercaCivicoDTO rc){
		return catastoDAO.getViaByPrefissoDescr(rc);
	}
	
	public Siticivi getCivico(RicercaCivicoDTO rc){
		return catastoDAO.getCivico(rc);
	}
	
	public List<ParticellaInfoDTO> getDatiCivicoCatasto(RicercaCivicoDTO rc){
		return catastoDAO.getDatiCivicoCatasto(rc);
	}
	
	@Override
	public List<KeyValueDTO> getListaQualitaTerreni(CeTBaseObject cet){
		return catastoDAO.getListaQualitaTerreni();
	}
	
	
	public List<Siticomu> getListaSiticomu(String descr){
		return catastoDAO.getListaSiticomu(descr);
	}
	
	public List<Sitideco> getListaCategorieImmobile(RicercaOggettoCatDTO ro){		
		return catastoDAO.getListaCategorieImmobile(ro);
	}

	public List<Sitidstr> getListaVie(RicercaOggettoCatDTO ro) {	
		return catastoDAO.getListaVie(ro);
	}
	
	public List<Sitidstr> getListaVieByCodiceVia(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaVieByCodiceVia(ro);
	}

	
	public List<Siticivi> getListaIndirizzi(RicercaOggettoCatDTO ro) {		
		return catastoDAO.getListaIndirizzi(ro);
	}
	
	public List<ConsSoggTab> getListaSoggettiF(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getListaSoggettiF(rs);
	}

	public List<ConsSoggTab> getListaSoggettiG(RicercaSoggettoCatDTO rs) {		
		return catastoDAO.getListaSoggettiG(rs);
	}

	public List<ConsSoggTab> getSoggettoByCF(RicercaSoggettoCatDTO rs) {		
		return catastoDAO.getSoggettoByCF(rs);
	}
	
	public List<ConsSoggTab> getSoggettiByPIVA(RicercaSoggettoCatDTO rs) {	
		return catastoDAO.getSoggettiByPIVA(rs);
	}

	public ConsSoggTab getSoggettoByPIVA(RicercaSoggettoCatDTO rs) {		
		return catastoDAO.getSoggettoByPIVA(rs);
	}

	public Long getCatastoRecordCount(RicercaOggettoCatDTO ro) {		
		return catastoDAO.getCatastoRecordCount(ro);
	}

	public List<Sitiuiu> getListaImmobiliByFPS(RicercaOggettoCatDTO ro){
		return catastoDAO.getListaImmobiliByFPS(ro);
	}
	
	public List<Sitiuiu> getListaImmobiliByFP(RicercaOggettoCatDTO ro){
		return catastoDAO.getListaImmobiliByFP(ro);
	}
	
	@Interceptors(AuditStateless.class)
	public List<SintesiImmobileDTO> getListaImmobili(RicercaOggettoCatDTO ro) {		
		return catastoDAO.getListaImmobili(ro);
	}
	
	public List<IndirizzoDTO> getListaIndirizziImmobile(RicercaOggettoCatDTO ro) {		
		return catastoDAO.getListaIndirizziImmobileByID(ro);
	}
	
	public List<SoggettoCatastoDTO> getListaSoggettiUiuByFPS(RicercaOggettoCatDTO ro){
		return catastoDAO.getListaSoggImmobileByFPS(ro);
	}
	
	public List<SoggettoCatastoDTO> getListaSoggettiUiuAllaDataByFPS(RicercaOggettoCatDTO ro){
		List<SoggettoCatastoDTO>  lista  = new  ArrayList<SoggettoCatastoDTO>();
		List<SoggettoCatastoDTO>  listaTemp = catastoDAO.getListaSoggImmobileByFPS(ro);
		for (SoggettoCatastoDTO sogg: listaTemp) {
			
			Date dtFinPoss = sogg.getDataFinePossesso();
			Date dtIniPoss = sogg.getDataInizioPossesso();
			
			Date dtRif = ro.getDtVal();
			
			if(dtIniPoss.compareTo(dtRif)<=0 && dtFinPoss.compareTo(dtRif)>=0)
              lista.add(sogg);
		}
		return lista;
	}
	
	
	
	public List<SoggettoCatastoDTO> getListaProprietariUiuByFPSDataRange(RicercaOggettoCatDTO ro){
		
		List<SoggettoCatastoDTO> soggetti = this.getListaSoggettiUiuByFPSDataRange(ro);
		List<SoggettoCatastoDTO> proprietari = new ArrayList<SoggettoCatastoDTO>();
		
		for(SoggettoCatastoDTO s : soggetti){
			if("1".equalsIgnoreCase(s.getTipoTitolo()))
					proprietari.add(s);	
		}
		logger.debug("getListaProprietariUiuByFPSDataRange - result:" + proprietari.size());
		return proprietari;
	}
	
	private boolean intersezioneDataRange(Date dtIni, Date dtFin, Date dtIniRif, Date dtFinRif){
		boolean interseca = false;
		
		if((dtIni==null || dtFinRif.compareTo(dtIni)>0) && (dtFin==null || dtIniRif.compareTo(dtFin)<0))
	           interseca = true;
		
		return interseca;
	}
	
	@Override
	public List<Date[]> getIntervalliProprietaTerrByFPSDataRange(RicercaOggettoCatDTO ro){
		List<Date[]>  lista  = new  ArrayList<Date[]>();
		List<Date[]>  listaTemp = catastoDAO.getIntervalliProprietaTerrByFPS(ro);
		for (Date[] intervallo : listaTemp) {
			Date dtIniPoss = intervallo[0];
			Date dtFinPoss = intervallo[1];
			
			Date dtIniRif = ro.getDtInizioRif();
			Date dtFinRif = ro.getDtFineRif();
			
			//if((dtIniPoss==null || dtFinRif.compareTo(dtIniPoss)>=0) && (dtFinPoss==null || dtIniRif.compareTo(dtFinPoss)<=0))
            if(intersezioneDataRange(dtIniPoss, dtFinPoss, dtIniRif, dtFinRif)) 
            	lista.add(intervallo);
		}
		return lista;
	}
	
	@Override
	public List<Date[]> getIntervalliProprietaUiuByFPSDataRange(RicercaOggettoCatDTO ro){
		List<Date[]>  lista  = new  ArrayList<Date[]>();
		
		List<Date[]>  listaTemp = catastoDAO.getIntervalliProprietaUiuByFPS(ro);
		for (Date[] intervallo : listaTemp) {
			Date dtIniPoss = intervallo[0];
			Date dtFinPoss = intervallo[1];
			
			Date dtIniRif = ro.getDtInizioRif();
			Date dtFinRif = ro.getDtFineRif();
			
			//if((dtIniPoss==null || dtFinRif.compareTo(dtIniPoss)>=0) && (dtFinPoss==null || dtIniRif.compareTo(dtFinPoss)<=0))
            if(intersezioneDataRange(dtIniPoss, dtFinPoss, dtIniRif, dtFinRif)) 
            	lista.add(intervallo);
		}
		logger.debug("getIntervalliProprietaUiuByFPSDataRange - num.result "+ lista.size());
		return lista;
	}

	
	public List<SoggettoCatastoDTO> getListaProprietariTerrByFPSDataRange(RicercaOggettoCatDTO ro){
		
		List<SoggettoCatastoDTO> soggetti = this.getListaSoggettiTerrByFPSDataRange(ro);
		List<SoggettoCatastoDTO> proprietari = new ArrayList<SoggettoCatastoDTO>();
		
		for(SoggettoCatastoDTO s : soggetti){
			if("1".equalsIgnoreCase(s.getTipoTitolo()))
					proprietari.add(s);	
		}
		
		return proprietari;
	}
	
	public List<SoggettoCatastoDTO>  getListaSoggettiUiuByFPSDataRange(RicercaOggettoCatDTO ro){
		List<SoggettoCatastoDTO>  lista  = new  ArrayList<SoggettoCatastoDTO>();
		List<SoggettoCatastoDTO>  listaTemp = catastoDAO.getListaSoggImmobileByFPS(ro);
		for (SoggettoCatastoDTO sogg: listaTemp) {
			sogg.setTipImm("F");
			
			Date dtFinPoss = sogg.getDataFinePossesso();
			Date dtIniPoss = sogg.getDataInizioPossesso();
			
			Date dtIniRif = ro.getDtInizioRif();
			Date dtFinRif = ro.getDtFineRif();
			
			//if((dtIniPoss==null || dtFinRif.compareTo(dtIniPoss)>=0) && (dtFinPoss==null || dtIniRif.compareTo(dtFinPoss)<=0))
            if(intersezioneDataRange(dtIniPoss, dtFinPoss, dtIniRif, dtFinRif)) 
            	lista.add(sogg);
		}
		return lista;
	}
	
	
	public List<SoggettoCatastoDTO>  getListaSoggettiTerrByFPSDataRange(RicercaOggettoCatDTO ro){
		List<SoggettoCatastoDTO>  lista  = new  ArrayList<SoggettoCatastoDTO>();
		List<SoggettoCatastoDTO>  listaTemp = catastoDAO.getListaSoggTerrenoByFPS(ro);
		for (SoggettoCatastoDTO sogg: listaTemp) {
			
			sogg.setTipImm("T");
			
			Date dtFinPoss = sogg.getDataFinePossesso();
			Date dtIniPoss = sogg.getDataInizioPossesso();
			
			Date dtIniRif = ro.getDtInizioRif();
			Date dtFinRif = ro.getDtFineRif();
			
			//if((dtIniPoss==null || dtFinRif.compareTo(dtIniPoss)>=0) && (dtFinPoss==null || dtIniRif.compareTo(dtFinPoss)<=0))
			if(intersezioneDataRange(dtIniPoss, dtFinPoss, dtIniRif, dtFinRif)) 
				lista.add(sogg);
		}
		return lista;
	}
	
	public List<SoggettoDTO> getListaSoggettiImmobile(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaSoggImmobileByID(ro);
	}
	
	public List<SoggettoDTO> getListaSoggettiAttualiImmobile(RicercaOggettoCatDTO ro) {
		List<SoggettoDTO> lista  = new  ArrayList<SoggettoDTO>();
		List<SoggettoDTO> listaTemp = getListaSoggettiImmobile(ro);
		for (SoggettoDTO sogg: listaTemp) {
			if (sogg.getDataFinePoss() == null || sogg.getDataFinePoss().compareTo(new Date()) > 0)
				lista.add(sogg);
		}
		return lista;
	}
	public List<SoggettoDTO> getListaSoggettiStoriciImmobile(RicercaOggettoCatDTO ro) {
		List<SoggettoDTO> lista  = new  ArrayList<SoggettoDTO>();
		List<SoggettoDTO> listaTemp = getListaSoggettiImmobile(ro);
		for (SoggettoDTO sogg: listaTemp) {
			if (sogg.getDataFinePoss() != null && sogg.getDataFinePoss().compareTo(new Date()) <= 0)
				lista.add(sogg);
		}
		return lista;
	}
	public DettaglioCatastaleImmobileDTO getDettaglioImmobile(RicercaOggettoCatDTO ro) {		
		return catastoDAO.getDettaglioImmobile(ro);
	}

	public Sitiuiu getDatiUiAllaData(RicercaOggettoCatDTO ro) {
		return catastoDAO.getDatiUiAllaData(ro);
	}

	public SiticonduzImmAll getDatiBySoggUiAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs) {
		return catastoDAO.getDatiBySoggUiAllaData(ro, rs);
	}

	@Override
	public List<SiticonduzImmAll> getDatiBySoggFabbricatoAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs) {
		return catastoDAO.getDatiBySoggFabbricatoAllaData(ro, rs);
	}
	public List<BigDecimal> getIdSoggByCF(RicercaSoggettoCatDTO rs)  {
		return catastoDAO.getIdSoggByCF(rs);		
	}
	public BigDecimal getIdSoggByCFAllaData(RicercaSoggettoCatDTO rs)  {
		return catastoDAO.getIdSoggByCFAllaData(rs);		
	}
	public List<BigDecimal> getIdSoggByDatiAnag(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getIdSoggByDatiAnag(rs);
	}
	public List<BigDecimal> getIdSoggByPI(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getIdSoggByPI(rs);
	}
	public BigDecimal getIdSoggByPIAllaData(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getIdSoggByPIAllaData(rs);
	}
	public List<BigDecimal> getIdSoggPGByDatiAnag(RicercaSoggettoCatDTO rs){
		return catastoDAO.getIdSoggPGByDatiAnag(rs);
	}
	public ConsSoggTab getSoggettoByID(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getSoggettoBYID(rs);
	}
	// Recupera gli immobili alla dtVal, se essa è valorizzata, 
	// gli immobile posseduti tra la dtRifDa e la dtRifA, se esse sono valorizzate 
	// tutti gli immobili posseduti nel corso del tempo, se nessuna data è valorizzata 
	public List<SiticonduzImmAll> getImmobiliByIdSogg(RicercaSoggettoCatDTO rs) {
		if (rs.getDtVal()==null)
			return catastoDAO.getImmobiliByIdSogg(rs);
		else
			return catastoDAO.getImmobiliByIdSoggAllaData(rs);
	}
	

	public List<TerrenoPerSoggDTO> getTerreniByIdSogg(RicercaSoggettoCatDTO rs)  {
		if (rs.getDtVal()==null)
			return catastoDAO.getTerreniByIdSogg(rs);
		else
			return catastoDAO.getTerreniByIdSoggAllaData(rs);
	}

	public List<SiticonduzImmAll> getImmobiliByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getImmobiliByIdSoggCedutiInRangeDate(rs);
	}

	public List<TerrenoPerSoggDTO> getTerreniByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs) {
		return catastoDAO.getTerreniByIdSoggCedutiInRangeDate(rs);
	}
	
	public List<DataVariazioneImmobileDTO> getDateVariazioniImmobile(RicercaOggettoCatDTO ro) {
		return catastoDAO.getDateVariazioniImmobile(ro);
	}
	
	public List<SitRepTarsu> getReportTarsuList(RicercaOggettoCatDTO ro) {
		return catastoDAO.getReportTarsuList(ro);
	}
	
	public List<SitiediVani> getDettaglioVaniC340(RicercaOggettoCatDTO ro){
		return catastoDAO.getDettaglioVaniC340(ro);
	}
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340(RicercaOggettoCatDTO ro) {
		return catastoDAO.getPlanimetrieComma340(ro);
	}
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340SezFglNum(RicercaOggettoCatDTO ro) {
		return catastoDAO.getPlanimetrieComma340SezFglNum(ro);
	}
	
	public List<IndirizzoDTO> getLocalizzazioneCatastale(RicercaOggettoCatDTO ro) {
		return catastoDAO.getLocalizzazioneCatastale(ro);
	}
	
	public List<IndirizzoDTO> getLocalizzazioneCatastaleDescr(RicercaOggettoCatDTO ro) {
		List<IndirizzoDTO> lst = new ArrayList<IndirizzoDTO>();
		HashMap map = new HashMap();
		
		for(IndirizzoDTO dto : this.getLocalizzazioneCatastale(ro)){
			String indirizzo = dto.getStrada()+", "+dto.getNumCivico();
			if(map.get(indirizzo)==null){
				IndirizzoDTO out = new IndirizzoDTO();
				out.setNumCivico(dto.getNumCivico());
				out.setStrada(dto.getStrada());
				lst.add(out);
				map.put(indirizzo, out);
			}
		}
		
		return lst;
	}

	@Override
	public BigDecimal calcolaSupUtileTarsuC30(RicercaOggettoCatDTO ro) {
		BigDecimal supTarsuC340=null;
		List<SitiediVani> listaVani  = catastoDAO.getDettaglioVaniC340AllaData(ro);
			
		if (listaVani != null ) {
			supTarsuC340=new BigDecimal(0);
			for(SitiediVani vano: listaVani) {
				if (vano.getAmbiente().equalsIgnoreCase("A") || vano.getAmbiente().equalsIgnoreCase("B") || vano.getAmbiente().equalsIgnoreCase("C")) 
					supTarsuC340 =	supTarsuC340.add(vano.getSupeAmbiente());
			}
			
		}
		float f = 0.8f;
		BigDecimal fatt = new BigDecimal(f);
		return supTarsuC340.multiply(fatt);
	}

	@Override
	public Siticomu getSitiComu(RicercaOggettoCatDTO ro) {
		return catastoDAO.getSitiComu(ro) ;
	}

	@Override
	public List<Siticomu> getSiticomuSezioni(RicercaOggettoCatDTO ro) {
		return catastoDAO.getSiticomuSezioni(ro);
	}

	@Override
	public List<ParticellaKeyDTO> getListaParticelle(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaParticelle(ro);
	}

	@Override
	public List<Sitiuiu> getListaUiAllaData(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaUiAllaData(ro) ;
	}

	@Override
	public List<IndirizzoDTO> getListaIndirizziPartAllaData(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaIndirizziPartAllaData(ro);
	}
	
	@Override
	public List<IndirizzoDTO> getListaIndirizziPart(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaIndirizziPart(ro);
	}

	/**
     * Restituisce la lista dei terreni per un certo foglio e particella
     * @return Lista dei terreni, ordinati per sub e data fine validità descrescente. 
     * Se specificato il parametro <CODE>dtVal</CODE>, solo quelli attivi alla data.
     * @param foglio, particella, data di riferimento
     */
	@Override
	public List<Sititrkc> getListaTerreniByFP(RicercaOggettoCatDTO ro) {
		return catastoDAO.getListaTerreniByFP(ro);
	}

	@Override
	public List<SoggettoDTO> getListaSoggettiAttualiTerreno(RicercaOggettoCatDTO ro) {
		List<SoggettoDTO> lista = new ArrayList<SoggettoDTO>();
		lista = catastoDAO.getListaTitolariAttualiTerreno(ro);
		if(ro.getAltriSoggetti()){
			lista.addAll(catastoDAO.getListaAltriSoggAttualiTerreno(ro));
		}
		
		return lista;
	}

	@Override
	public List<SoggettoDTO> getListaSoggettiStoriciTerreno(RicercaOggettoCatDTO ro) {
		List<SoggettoDTO> lista = new ArrayList<SoggettoDTO>();
		lista = catastoDAO.getListaTitolariStoriciTerreno(ro);
		if(ro.getAltriSoggetti()){
			lista.addAll(catastoDAO.getListaAltriSoggStoriciTerreno(ro));
		}
		return lista;
	}
	
	@Override
	public String getSoggettoCollegatoImmobili(RicercaOggettoCatDTO ro) {
		return catastoDAO.getSoggettoCollegatoImmobili(ro.getCodEnte(), ro.getFoglio(), ro.getParticella(), ro.getSub(), ro.getUnimm(), ro.getPkCuaa());		
	}
	
	@Override
	public String getSoggettoCollegatoTerreni(RicercaOggettoCatDTO ro) {
		return catastoDAO.getSoggettoCollegatoTerreni(ro.getCodEnte(), ro.getFoglio(), ro.getParticella(), ro.getSub(), ro.getPkCuaa());
	}

	@Override
	public Sitideco getSitideco(RicercaOggettoCatDTO ro) {
		return catastoDAO.getSitideco(ro);
	}

	@Override
	public ConsSoggTab getSoggettoByPkCuaa(RicercaSoggettoCatDTO rs) {
		List<ConsSoggTab> lista = catastoDAO.getSoggettoByPkCuaa(rs);
		if (lista !=null && lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

	@Override
	public List<LoadCatUiuId> getLoadCatUiuIdCollegati(RicercaOggettoCatDTO ro){
		return catastoDAO.getLoadCatUiuIdCollegati(ro);
	}

	@Override
	public boolean esisteParticellaCT(RicercaOggettoCatDTO ro) {
		List<Sititrkc>  lista = catastoDAO.getListaTerreniByFP(ro);
		return(lista.size()>0);
		
	}

	@Override
	public boolean esisteParticellaCU(RicercaOggettoCatDTO ro) {
		List<Sitiuiu>  listaUiu = catastoDAO.getListaUIByParticella(ro);
		return(listaUiu.size()>0);
	}
	
	@Override
	public List<ConsSoggTab> getTitolariSuCivicoByPkIdStraCivico(RicercaCivicoDTO rc){
		return catastoDAO.getTitolariSuCivicoByPkIdStraCivico(rc);
	}
	
	@Override
	public String getRegimeImmobili(RicercaOggettoCatDTO ro) {
		return catastoDAO.getRegimeImmobili(ro.getCodEnte(), ro.getFoglio(), ro.getParticella(), ro.getSub(), ro.getUnimm(), ro.getPkCuaa());
	}
	
	@Override
	public String getRegimeTerreni(RicercaOggettoCatDTO ro) {
		return catastoDAO.getRegimeTerreni(ro.getCodEnte(), ro.getFoglio(), ro.getParticella(), ro.getSub(), ro.getPkCuaa());
	}
	
	@Override
	public List<Sititrkc> getListaStoricoTerreniByFP(RicercaOggettoCatDTO roc){
		List<Sititrkc> listaStorico = new ArrayList<Sititrkc>();
		
		List<Sititrkc> listaTerr = catastoDAO.getListaTerreniByFP(roc);
		
		for(Sititrkc terreno : listaTerr){
			Date dataFine  =  terreno.getId().getDataFine();
			Date today = new Date();
			if(dataFine.before(today))
				listaStorico.add(terreno);
			
		}
		
		return listaStorico;
	}
	
	@Override
	public List<Sititrkc> getListaStoricoTerreniByFPS(RicercaOggettoCatDTO roc){
		
		return catastoDAO.getListaStoricoTerreniByFPS(roc);
	
	}

	@Override
	public List<Sitipart> getParticelleSitipart(PartDTO dto) {
		return catastoDAO.getParticelleSitipart(dto);
	}

	@Override
	public List<Sitisuolo> getParticelleSitisuolo(PartDTO dto) {
		return catastoDAO.getParticelleSitisuolo(dto);
	}
	
	@Override
	public List<Sitiuiu> getListaImmobiliByFPS_PostDtRif(RicercaOggettoCatDTO roc){
		return catastoDAO.getListaImmobiliByFPS_PostDtRif(roc);
	}
	
	@Override
	public List<TerrenoDerivazioneDTO> getTerreniGeneratori(RicercaOggettoCatDTO roc){
		
		List<TerrenoDerivazioneDTO> listaTerrGen = new ArrayList<TerrenoDerivazioneDTO>();
		if(roc.getIdeMuta()!=null){
		List<TerrenoDerivazioneDTO> lsttmp =  catastoDAO.getTerreniGeneratori(roc.getCodEnte(), roc.getIdeMuta());
		logger.debug("getTerreniGeneratori");
		for(TerrenoDerivazioneDTO tmp : lsttmp){
			String tmpFoglio = Long.toString(tmp.getFoglio());
			/*logger.debug("Confr Foglio: ["+tmpFoglio +"]["+roc.getFoglio()+"]");
			logger.debug("Confr Part: ["+tmp.getParticella() +"]["+roc.getParticella()+"]");
			logger.debug("Confr Sub: ["+tmp.getSubalterno() +"]["+roc.getSub()+"]");*/
			if (!(tmpFoglio.equals(roc.getFoglio()) && tmp.getParticella().equals(roc.getParticella()) && tmp.getSubalterno().equals(roc.getSub())))
				listaTerrGen.add(tmp);
		}
		}
		return listaTerrGen;
	}
	
	@Override
	public List<TerrenoDerivazioneDTO> getTerreniDerivati(RicercaOggettoCatDTO roc){
		
		List<TerrenoDerivazioneDTO> listaTerr = new ArrayList<TerrenoDerivazioneDTO>();
		
		if(roc.getIdeMuta()!=null){
		List<TerrenoDerivazioneDTO> lsttmp = catastoDAO.getTerreniDerivati(roc.getCodEnte(), roc.getIdeMuta());
		
		logger.debug("getTerreniDerivati");
		
		for(TerrenoDerivazioneDTO tmp : lsttmp){
			String tmpFoglio = Long.toString(tmp.getFoglio());
			
		/*	logger.debug("Confr Foglio: ["+tmpFoglio +"]["+roc.getFoglio()+"]");
			logger.debug("Confr Part: ["+tmp.getParticella() +"]["+roc.getParticella()+"]");
			logger.debug("Confr Sub: ["+tmp.getSubalterno() +"]["+roc.getSub()+"]");*/
			if (!(tmpFoglio.equals(roc.getFoglio()) && tmp.getParticella().equals(roc.getParticella()) && tmp.getSubalterno().equals(roc.getSub())))
				listaTerr.add(tmp);
		}
		}
		return listaTerr;
		
	}
	
	@Override
	public Date[] getMinMaxDateValTerreno(RicercaOggettoCatDTO rc){
		return catastoDAO.getMinMaxDateValTerreno(rc);
	}
	
	@Override
	public Date[] getMinMaxDateValUiu(RicercaOggettoCatDTO rc){
		return catastoDAO.getMinMaxDateValUiu(rc);
	}
	
	@Override
	public String getDescConduzTipoDocumento(RicercaOggettoCatDTO rc){
		return catastoDAO.getDescTipoDocumento(rc.getCodTipoDocumento());
	}
	
	@Override
	public List<CoordBaseDTO> getCoordUiByTopo(RicercaCivicoDTO rc){
		return catastoDAO.getCoordUiByTopo(rc);
	}
	
	@Override
	public List<Object[]> getListaImmobiliByParams(RicercaOggettoCatDTO roc){
		return catastoDAO.getListaImmobiliByParams(roc);
	}

	@Override
	public String[] getLatitudineLongitudineFromXY(CatastoBaseDTO rc) {
		String[] xy = (String[])rc.getObj1();
		String[] out = {"0","0"};
		if(xy!=null)
			out = catastoDAO.getLatitudineLongitudineFromXY(xy[0], xy[1]);
		return out;
	}
	
	@Override
	public List<ImmobileBaseDTO> getPrincipalieGraffatiByFP(RicercaOggettoCatDTO roc){
		List<ImmobileBaseDTO> lstTot =  catastoDAO.getPrincipalieGraffatiByFP(roc);
		List<ImmobileBaseDTO> lstOut = new ArrayList<ImmobileBaseDTO>();
		for(ImmobileBaseDTO i : lstTot){
			RicercaOggettoCatDTO rc = new RicercaOggettoCatDTO();
			rc.setCodEnte(i.getCodNazionale());
			rc.setFoglio(i.getFoglio());
			rc.setParticella(i.getParticella());
			rc.setSub(i.getSub());
			rc.setUnimm(i.getUnimm());
			if(!i.isGraffato()){
				Date[] minmax = catastoDAO.getMinMaxDateValUiu(rc);
				i.setDataInizioVal(minmax[0]);
				i.setDataFineVal(minmax[1]);
				if(roc.getDtVal()!=null && i.getDataFineVal()!=null && roc.getDtVal().before(i.getDataFineVal()))
					lstOut.add(i);
			}else{
				lstOut.add(i);
			}
			
			if(roc.getDtVal()==null)
				lstOut.add(i);
		}
		
		return lstOut;
	}
	
	@Override
	public List<ImmobileBaseDTO> getListaGraffatiPrincipale(RicercaOggettoCatDTO roc){
		return catastoDAO.getListaGraffatiPrincipale(roc);
	}//-------------------------------------------------------------------------
	
	@Override
	public ImmobileBaseDTO getPrincipaleDellaGraffata(RicercaOggettoCatDTO roc){
		ImmobileBaseDTO i =  catastoDAO.getPrincipaleDellaGraffata(roc);
		
		RicercaOggettoCatDTO rc = new RicercaOggettoCatDTO();
		rc.setCodEnte(i.getCodNazionale());
		rc.setFoglio(i.getFoglio());
		rc.setParticella(i.getParticella());
		rc.setSub(i.getSub());
		rc.setUnimm(i.getUnimm());
		
		Date[] minmax = catastoDAO.getMinMaxDateValUiu(rc);
		i.setDataInizioVal(minmax[0]);
		i.setDataFineVal(minmax[1]);
		
		return i;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Sititrkc> getListaTerreniByParams(RicercaOggettoCatDTO roc){
		return catastoDAO.getListaTerreniByParams(roc);
	}//-------------------------------------------------------------------------
	
	
	
}
