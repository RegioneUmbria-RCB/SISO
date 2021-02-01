package it.webred.ct.data.access.basic.catasto;

import it.webred.ct.data.access.basic.catasto.dto.CatastoBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.CoordBaseDTO;
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
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.catasto.Siticomu;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitideco;
import it.webred.ct.data.model.catasto.Sitidstr;
import it.webred.ct.data.model.catasto.SitiediVani;
import it.webred.ct.data.model.catasto.Sitipart;
import it.webred.ct.data.model.catasto.Sitisuolo;
import it.webred.ct.data.model.catasto.Sititrkc;
import it.webred.ct.data.model.catasto.SititrkcPK;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CatastoService {
	
	public Siticomu getSitiComu(RicercaOggettoCatDTO ro) ;
	
	public List<Siticomu> getSiticomuSezioni(RicercaOggettoCatDTO ro) ;
	
	//Suggerimenti 
	public List<KeyValueDTO> getListaQualitaTerreni(CeTBaseObject cet);
	
	public List<Siticomu> getListaSiticomu(String descr);
	
	public List<Sitideco> getListaCategorieImmobile(RicercaOggettoCatDTO ro);
	
	public List<Sitidstr> getListaVie(RicercaOggettoCatDTO ro);
	
	public List<Sitidstr> getListaVieByCodiceVia(RicercaOggettoCatDTO ro);
	
	
	public List<Siticivi> getListaIndirizzi(RicercaOggettoCatDTO ro);
	
	public List<ConsSoggTab> getListaSoggettiF(RicercaSoggettoCatDTO rs);
	
	public List<ConsSoggTab> getListaSoggettiG(RicercaSoggettoCatDTO rs);
	
	public List<ConsSoggTab> getSoggettoByCF(RicercaSoggettoCatDTO rs);
	
	public ConsSoggTab getSoggettoByPIVA(RicercaSoggettoCatDTO rs);
	
	//Query di ricerca sul catasto
	
	public List<Sitiuiu> getListaImmobiliByFPS(RicercaOggettoCatDTO ro);
	
	public List<Sitiuiu> getListaImmobiliByFP(RicercaOggettoCatDTO ro);
	
	public List<SintesiImmobileDTO> getListaImmobili(RicercaOggettoCatDTO ro);
	
	public Long getCatastoRecordCount(RicercaOggettoCatDTO ro);
	
	public DettaglioCatastaleImmobileDTO getDettaglioImmobile(RicercaOggettoCatDTO ro);
	
	public List<IndirizzoDTO> getLocalizzazioneCatastale(RicercaOggettoCatDTO ro);
	
	public List<IndirizzoDTO> getLocalizzazioneCatastaleDescr(RicercaOggettoCatDTO ro);
	
	public List<ParticellaKeyDTO> getListaParticelle(RicercaOggettoCatDTO ro);
	
	public Sitidstr getViaByPrefissoDescr(RicercaCivicoDTO rc);
	
	public Siticivi getCivico(RicercaCivicoDTO rc);
	
	public List<ParticellaInfoDTO> getDatiCivicoCatasto(RicercaCivicoDTO rc);
	
	public List<ConsSoggTab> getTitolariSuCivicoByPkIdStraCivico(RicercaCivicoDTO rc);
	
	public String getRegimeImmobili(RicercaOggettoCatDTO ro);
	
	public String getRegimeTerreni(RicercaOggettoCatDTO ro);

	
	
	//decodifiche
	public Sitideco getSitideco(RicercaOggettoCatDTO ro);
	// ricerca soggetto
	public List<BigDecimal> getIdSoggByCF(RicercaSoggettoCatDTO rs);
	public BigDecimal getIdSoggByCFAllaData(RicercaSoggettoCatDTO rs);
	public List<BigDecimal> getIdSoggByDatiAnag(RicercaSoggettoCatDTO rs);//ricerca della persona fisica per dati anagrafici
	public List<BigDecimal> getIdSoggByPI(RicercaSoggettoCatDTO rs);
	public BigDecimal getIdSoggByPIAllaData(RicercaSoggettoCatDTO rs);
	public List<BigDecimal> getIdSoggPGByDatiAnag(RicercaSoggettoCatDTO rs);//ricerca della persona giuridica per dati anagrafici
	public ConsSoggTab getSoggettoByID(RicercaSoggettoCatDTO rs);
	public ConsSoggTab getSoggettoByPkCuaa(RicercaSoggettoCatDTO rs);
	
	public List<SoggettoDTO> getListaSoggettiAttualiTerreno(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaSoggettiStoriciTerreno(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaSoggettiImmobile(RicercaOggettoCatDTO ro);
	
	public List<SoggettoCatastoDTO> getListaSoggettiUiuByFPSDataRange(RicercaOggettoCatDTO ro);
	public List<SoggettoCatastoDTO> getListaSoggettiTerrByFPSDataRange(RicercaOggettoCatDTO ro);
	
	public List<SoggettoCatastoDTO> getListaProprietariUiuByFPSDataRange(RicercaOggettoCatDTO ro);
	public List<SoggettoCatastoDTO> getListaProprietariTerrByFPSDataRange(RicercaOggettoCatDTO ro);
	
	public List<SoggettoCatastoDTO> getListaSoggettiUiuByFPS(RicercaOggettoCatDTO ro);
	public List<SoggettoCatastoDTO> getListaSoggettiUiuAllaDataByFPS(RicercaOggettoCatDTO ro);
	
	public List<SoggettoDTO> getListaSoggettiAttualiImmobile(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaSoggettiStoriciImmobile(RicercaOggettoCatDTO ro);

	public String getSoggettoCollegatoImmobili(RicercaOggettoCatDTO ro);
	public String getSoggettoCollegatoTerreni(RicercaOggettoCatDTO ro);
	
	//dati PARTICELLA
	public List<IndirizzoDTO> getListaIndirizziPartAllaData(RicercaOggettoCatDTO ro) ;
	public List<IndirizzoDTO> getListaIndirizziPart(RicercaOggettoCatDTO ro) ;
	public List<Sitiuiu> getListaUiAllaData(RicercaOggettoCatDTO ro);
	public boolean esisteParticellaCU(RicercaOggettoCatDTO ro);
	public boolean esisteParticellaCT(RicercaOggettoCatDTO ro);
	//dati u.i e terreni
	public Sitiuiu getDatiUiAllaData(RicercaOggettoCatDTO ro);
	public SiticonduzImmAll getDatiBySoggUiAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getDatiBySoggFabbricatoAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getImmobiliByIdSogg(RicercaSoggettoCatDTO rs);
	public List<TerrenoPerSoggDTO> getTerreniByIdSogg(RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getImmobiliByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs);
	public List<TerrenoPerSoggDTO> getTerreniByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs);
	
	public List<Sititrkc> getListaTerreniByFP(RicercaOggettoCatDTO ro);
	public List<Sititrkc> getListaStoricoTerreniByFP(RicercaOggettoCatDTO roc);
	public List<Sititrkc> getListaStoricoTerreniByFPS(RicercaOggettoCatDTO roc);
	
	public List<TerrenoDerivazioneDTO> getTerreniGeneratori(RicercaOggettoCatDTO roc);
	public List<TerrenoDerivazioneDTO> getTerreniDerivati(RicercaOggettoCatDTO roc);
	
	public List<LoadCatUiuId> getLoadCatUiuIdCollegati(RicercaOggettoCatDTO ro);
	//SitiediVani
	public List<SitiediVani> getDettaglioVaniC340(RicercaOggettoCatDTO ro);
	public BigDecimal calcolaSupUtileTarsuC30(RicercaOggettoCatDTO ro);
	
	public List<IndirizzoDTO> getListaIndirizziImmobile(RicercaOggettoCatDTO ro);
	
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340(RicercaOggettoCatDTO ro);
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340SezFglNum(RicercaOggettoCatDTO ro);

	public List<Sitiuiu> getListaImmobiliByFPS_PostDtRif(RicercaOggettoCatDTO roc);

	
	//MATTEO
	public List<Sitipart> getParticelleSitipart(PartDTO dto);
	
	public List<Sitisuolo> getParticelleSitisuolo(PartDTO dto);
	
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input);
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByPkCuaa(RicercaSoggettoCatDTO input);
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input);
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByPkCuaa(RicercaSoggettoCatDTO input);

	public List<Date[]> getIntervalliProprietaTerrByFPSDataRange(RicercaOggettoCatDTO ro);

	public List<Date[]> getIntervalliProprietaUiuByFPSDataRange(RicercaOggettoCatDTO ro);

	public Date[] getMinMaxDateValTerreno(RicercaOggettoCatDTO rc);
	public Date[] getMinMaxDateValUiu(RicercaOggettoCatDTO rc);
	
	public String[] getLatitudineLongitudineFromXY(CatastoBaseDTO rc);
	public String[] getLatitudineLongitudine(RicercaOggettoCatDTO rc);
	
	public String getDescConduzTipoDocumento(RicercaOggettoCatDTO rc);
	
	//metodi GITOUT WS1
	public List<CoordBaseDTO> getCoordUiByTopo(RicercaCivicoDTO rc);

	public List<ImmobileBaseDTO> getPrincipalieGraffatiByFP(RicercaOggettoCatDTO roc);

	public List<ImmobileBaseDTO> getListaGraffatiPrincipale(RicercaOggettoCatDTO roc);

	public ImmobileBaseDTO getPrincipaleDellaGraffata(RicercaOggettoCatDTO roc);
	
	//metodi GITOUT WS2.1
	public List<Object[]> getListaImmobiliByParams(RicercaOggettoCatDTO rc);
	
	//metodi GITOUT WS2.2
	public List<ConsSoggTab> getSoggettiByPIVA(RicercaSoggettoCatDTO rs);
	
	//metodi GITOUT WS10
	public List<Sititrkc> getListaTerreniByParams(RicercaOggettoCatDTO rc);
		
	
}
