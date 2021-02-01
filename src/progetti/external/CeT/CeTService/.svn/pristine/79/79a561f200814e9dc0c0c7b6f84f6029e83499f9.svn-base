package it.webred.ct.data.access.basic.catasto.dao;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
public interface CatastoDAO {
	public Siticomu getSitiComu(RicercaOggettoCatDTO ro) ;
	
	public List<Siticomu> getSiticomuSezioni(RicercaOggettoCatDTO ro) ;
	
	//Suggerimenti 
	public List<KeyValueDTO> getListaQualitaTerreni();
	
	public List<Siticomu> getListaSiticomu(String descr) ;
	
	public List<Sitideco> getListaCategorieImmobile(RicercaOggettoCatDTO ro);
	
	public List<Sitidstr> getListaVie(RicercaOggettoCatDTO ro);
	
	public List<Siticivi> getListaIndirizzi(RicercaOggettoCatDTO ro);
	
	public List<ConsSoggTab> getListaSoggettiF(RicercaSoggettoCatDTO rs);
	
	public List<ConsSoggTab> getListaSoggettiG(RicercaSoggettoCatDTO rs);
	
	public List<ConsSoggTab> getSoggettoByCF(RicercaSoggettoCatDTO rs);
	
	public ConsSoggTab getSoggettoByPIVA(RicercaSoggettoCatDTO rs);
	
	//Query di ricerca sul catasto
	public Long getCatastoRecordCount(RicercaOggettoCatDTO ro);
	
	public List<Sitiuiu> getListaImmobiliByFPS(RicercaOggettoCatDTO ro);
	
	public List<Sitiuiu> getListaImmobiliByFP(RicercaOggettoCatDTO ro);
	
	public List<SintesiImmobileDTO> getListaImmobili(RicercaOggettoCatDTO ro);
	
	public List<IndirizzoDTO> getListaIndirizziImmobileByID(RicercaOggettoCatDTO ro);

	public List<SoggettoDTO> getListaSoggImmobileByID(RicercaOggettoCatDTO ro);
	
	public List<SoggettoCatastoDTO> getListaSoggImmobileByFPS(RicercaOggettoCatDTO ro);
	
	public List<ConsSoggTab> getTitolariSuCivicoByPkIdStraCivico(RicercaCivicoDTO rc);
	
	public DettaglioCatastaleImmobileDTO getDettaglioImmobile(RicercaOggettoCatDTO ro);
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340(RicercaOggettoCatDTO ro);
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340SezFglNum(RicercaOggettoCatDTO ro);

	public ArrayList<IndirizzoDTO> getLocalizzazioneCatastale(RicercaOggettoCatDTO ro);
	
	public List<SitiediVani> getDettaglioVaniC340(RicercaOggettoCatDTO ro);
	
	public List<SitiediVani> getDettaglioVaniC340AllaData(RicercaOggettoCatDTO ro);
	
	public List<DataVariazioneImmobileDTO> getDateVariazioniImmobile(RicercaOggettoCatDTO ro);
	
	public List<SitRepTarsu> getReportTarsuList(RicercaOggettoCatDTO ro);
	
	public List<ParticellaKeyDTO> getListaParticelle(RicercaOggettoCatDTO ro);
	
	public Sitidstr getViaByPrefissoDescr(RicercaCivicoDTO rc);
	
	public Siticivi getCivico(RicercaCivicoDTO rc);
	
	public List<ParticellaInfoDTO> getDatiCivicoCatasto(RicercaCivicoDTO rc);
	
	public List<Sitiuiu> getListaUIByParticella(RicercaOggettoCatDTO ro);
	
	//decodifiche
	public Sitideco getSitideco(RicercaOggettoCatDTO ro);
	//ricerca del soggetto in anagrafe
	public List<BigDecimal> getIdSoggByCF(RicercaSoggettoCatDTO rs);
	public BigDecimal getIdSoggByCFAllaData(RicercaSoggettoCatDTO rs);
	public List<BigDecimal> getIdSoggByDatiAnag(RicercaSoggettoCatDTO rs);//ricerca della persona fisica per dati anagrafici
	public List<BigDecimal> getIdSoggByPI(RicercaSoggettoCatDTO rs);
	public BigDecimal getIdSoggByPIAllaData(RicercaSoggettoCatDTO rs);
	public List<BigDecimal> getIdSoggPGByDatiAnag(RicercaSoggettoCatDTO rs);//ricerca della persona giuridica per dati anagrafici
	public ConsSoggTab getSoggettoBYID(RicercaSoggettoCatDTO rs);
	public List<ConsSoggTab> getSoggettoByPkCuaa(RicercaSoggettoCatDTO rs);
	
	public List<SoggettoCatastoDTO> getListaSoggTerrenoByFPS(RicercaOggettoCatDTO ro);
	
	public List<SoggettoDTO> getListaTitolariAttualiTerreno(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaTitolariStoriciTerreno(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaAltriSoggAttualiTerreno(RicercaOggettoCatDTO ro);
	public List<SoggettoDTO> getListaAltriSoggStoriciTerreno(RicercaOggettoCatDTO ro);
	
	
	//dati particella
	public List<Sitiuiu> getListaUiAllaData(RicercaOggettoCatDTO ro) ;
	public List<IndirizzoDTO> getListaIndirizziPartAllaData(RicercaOggettoCatDTO ro);
	public List<IndirizzoDTO> getListaIndirizziPart(RicercaOggettoCatDTO ro);
	
	//ricerca u.i./terreni e dati relativi
	public Sitiuiu getDatiUiAllaData(RicercaOggettoCatDTO ro);
	public SiticonduzImmAll getDatiBySoggUiAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getDatiBySoggFabbricatoAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs); 
	public List<SiticonduzImmAll> getImmobiliByIdSogg(RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getImmobiliByIdSoggAllaData(RicercaSoggettoCatDTO rs);
	public List<TerrenoPerSoggDTO> getTerreniByIdSogg(RicercaSoggettoCatDTO rs);
	public List<TerrenoPerSoggDTO> getTerreniByIdSoggAllaData(RicercaSoggettoCatDTO rs);
	public List<SiticonduzImmAll> getImmobiliByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs);
	public List<TerrenoPerSoggDTO> getTerreniByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs);
	
	public List<Sititrkc> getListaTerreniByFP(RicercaOggettoCatDTO ro);
	public List<Sititrkc> getListaStoricoTerreniByFPS(RicercaOggettoCatDTO ro);
	
	public List<LoadCatUiuId> getLoadCatUiuIdCollegati(RicercaOggettoCatDTO ro);

	public List<Sitiuiu> getListaImmobiliByFPS_PostDtRif(RicercaOggettoCatDTO roc);

	//MATTEO
	public List<Sitipart> getParticelleSitipart(PartDTO dto);
	
	public List<Sitisuolo> getParticelleSitisuolo(PartDTO dto);

	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input);
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByPkCuaa(RicercaSoggettoCatDTO input);
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input);
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByPkCuaa(RicercaSoggettoCatDTO input);

	public String getRegimeImmobili(String codEnte, String foglio, String particella, String sub, String unimm, BigDecimal pkCuaa);
	public String getRegimeTerreni(String codEnte, String foglio, String particella, String sub, BigDecimal pkCuaa);
	
	public String getSoggettoCollegatoImmobili(String codEnte, String foglio, String particella, String sub, String unimm, BigDecimal pkCuaa);
	public String getSoggettoCollegatoTerreni(String codEnte, String foglio, String particella, String sub, BigDecimal pkCuaa);

	public List<Date[]> getIntervalliProprietaTerrByFPS(RicercaOggettoCatDTO ro);
	public List<Date[]> getIntervalliProprietaUiuByFPS(RicercaOggettoCatDTO ro);

	public List<TerrenoDerivazioneDTO> getTerreniGeneratori(String codEnte,BigDecimal ideMutaIniOrig);
	public List<TerrenoDerivazioneDTO> getTerreniDerivati(String codEnte, BigDecimal ideMutaFineOrig);

	public Date[] getMinMaxDateValTerreno(RicercaOggettoCatDTO rc);
	public Date[] getMinMaxDateValUiu(RicercaOggettoCatDTO rc);

	public String[] getLatitudineLongitudine(String foglio, String particella,String codEnte);
	public String[] getLatitudineLongitudineFromXY(String x, String y);

	public String getDescTipoDocumento(String codice);

	public List<Sitidstr> getListaVieByCodiceVia(RicercaOggettoCatDTO ro);
	
	//Implementazione GITOUT WS1
	public List<CoordBaseDTO> getCoordUiByTopo(RicercaCivicoDTO rc);

	public List<ImmobileBaseDTO> getPrincipalieGraffatiByFP(RicercaOggettoCatDTO roc);

	public List<ImmobileBaseDTO> getListaGraffatiPrincipale(RicercaOggettoCatDTO roc);

	public ImmobileBaseDTO getPrincipaleDellaGraffata(RicercaOggettoCatDTO roc);

	//Implementazione GITOUT WS2.1
	public List<Object[]> getListaImmobiliByParams(RicercaOggettoCatDTO roc);
	
	//Implementazione GITOUT WS2.2
	public List<ConsSoggTab> getSoggettiByPIVA(RicercaSoggettoCatDTO rs);
	
	//Implementazione GITOUT WS10
	public List<Sititrkc> getListaTerreniByParams(RicercaOggettoCatDTO roc);

	
		
}
