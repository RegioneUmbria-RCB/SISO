package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ScuoleSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.base.CsPaiFaseChiusuraDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.data.model.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;
import java.util.TreeMap;

import javax.ejb.Remote;
import javax.faces.model.SelectItem;

@Remote
public interface AccessTableConfigurazioneSessionBeanRemote {

	public List<KeyValueDTO> getListaStatoCivile(CeTBaseObject cet);

	public List<CsTbStatoCivile> getStatoCivile(CeTBaseObject cet);

	public CsTbStatoCivile getStatoCivileByIdExtCet(BaseDTO dto);

	public CsTbStatoCivile getStatoCivileByDescrizione(BaseDTO dto);

	public CsTbStatoCivile getStatoCivileByCodice(BaseDTO dto);

	public List<KeyValueDTO> getStatus(CeTBaseObject cet);

	public CsTbStatus getStatusById(BaseDTO dto);

	public List<CsTbPermesso> getPermesso(CeTBaseObject cet);

	public List<CsTbAssenzaPermesso> getPermessoSenzaSoggiorno(CeTBaseObject cet); // SISO-792

	public CsTbAssenzaPermesso getAssenzaPermessoById(BaseDTO dto); // SISO-792

	public CsTbPermesso getPermessoById(BaseDTO dto);

	public List<CsTbTipoIndirizzo> getTipoIndirizzo(CeTBaseObject cet);

	public CsTbTipoIndirizzo getTipoIndirizzoById(BaseDTO dto);
	
	public List<CsTbTipoContributo> getTipoContributo(CeTBaseObject cet);

	public List<CsTbTipologiaFamiliare> getTipologieFamiliari(CeTBaseObject cet);

	public CsTbTipologiaFamiliare getTipologiaFamiliareById(BaseDTO dto);

	public List<CsTbResponsabilita> getResponsabilita(CeTBaseObject cet);

	public List<KeyValueDTO> getProblematiche(CeTBaseObject cet);

	public CsTbProblematica getProblematicaById(BaseDTO dto);

	public List<CsTbProbl> getProbl(CeTBaseObject cet);

	public CsTbProbl getProblById(BaseDTO cet);

	public List<KeyValueDTO> getStesuraRelazioniPer(CeTBaseObject cet);

	public List<KeyValueDTO> getTitoliStudio(CeTBaseObject cet);

	public CsTbTitoloStudio getTitoloStudioById(BaseDTO dto);

	public CsTbTitoloStudio getTitoloStudioByDescrizione(BaseDTO dto);

	public List<CsTbProfessione> getProfessioni(CeTBaseObject cet);

	public List<CsTbTutela> getTutele(CeTBaseObject cet);

	public void salvaOrganizzazione(BaseDTO dto);

	public void updateOrganizzazione(BaseDTO dto);

	public void eliminaOrganizzazione(BaseDTO dto);

	public List<CsOOrganizzazione> getOrganizzazioniAll(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioni(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioniAccesso(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioniByCodCatastale(BaseDTO dto);

	public CsOOrganizzazione getOrganizzazioneByCodFittizio(BaseDTO dto);

	public CsOOrganizzazione getOrganizzazioneById(BaseDTO dto);

	/* SISO-663 SM */
	public CsOOrganizzazione getOrganizzazioneCapofila(CeTBaseObject cet);
	/* -=- */

	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto);

	public List<SettoreCatSocialeDTO> findSettoreDTOByOrganizzazione(BaseDTO dto);

	public List<CsOSettore> getSettoreAll(CeTBaseObject cet);

	public List<CsOSettore> getSettoriDatiSociali(CeTBaseObject cet);

	public void salvaSettore(BaseDTO dto);

	public void updateSettore(BaseDTO dto);

	public void eliminaSettore(BaseDTO dto);

	public CsTbIcd10 getIcd10ById(BaseDTO dto);

	public List<String> getIcd10CodIniziali(CeTBaseObject cet);

	public List<CsTbIcd10> getIcd10ByCodIniziali(BaseDTO dto);

	public CsTbIcd9 getIcd9ById(BaseDTO dto);

	public List<String> getIcd9CodIniziali(CeTBaseObject cet);

	public List<CsTbIcd9> getIcd9ByCodIniziali(BaseDTO dto);

	public List<CsTbTipoRapportoCon> getTipoRapportoConParenti(CeTBaseObject cet);

	public List<CsTbTipoRapportoCon> getTipoRapportoConConoscenti(CeTBaseObject cet);

	public CsTbTipoRapportoCon mappaRelazioneParentale(BaseDTO cet);

	public List<CsTbPotesta> getPotesta(CeTBaseObject cet);

	public List<CsTbDisponibilita> getDisponibilita(CeTBaseObject cet);

	public List<KeyValueDTO> getTipoPai(CeTBaseObject cet);

	public List<CsTbMotivoChiusuraPai> getMotivoChiusuraPai(CeTBaseObject cet);

	public List<CsTbContatto> getContatti(CeTBaseObject cet);

	public List<KeyValueDTO> getMacroSegnalazioni(CeTBaseObject cet);

	public CsTbMacroSegnal getMacroSegnalazioneById(BaseDTO dto);

	public List<CsTbMicroSegnal> getMicroSegnalazioni(CeTBaseObject cet);

	public CsTbMicroSegnal getMicroSegnalazioneById(BaseDTO dto);

	public List<KeyValueDTO> getMotivoSegnalazioni(CeTBaseObject cet);

	public CsTbMotivoSegnal getMotivoSegnalazioneById(BaseDTO dto);

	public List<KeyValueDTO> getDisabEnte(CeTBaseObject cet);

	public List<KeyValueDTO> getDisabGravita(CeTBaseObject cet);

	public CsTbDisabGravita getDisabGravitaById(BaseDTO dto);

	public List<CsTbDisabTipologia> getDisabTipologia(CeTBaseObject cet);

	public CsTbDisabTipologia getDisabTipologiaById(BaseDTO dto);

	public List<CsTbServComunita> getServComunita(CeTBaseObject cet);

	public List<CsTbServLuogoStr> getServLuogoStr(CeTBaseObject cet);

	public List<CsTbServResRetta> getServResRetta(CeTBaseObject cet);

	public List<CsTbServSemiresRetta> getServSemiresRetta(CeTBaseObject cet);

	public List<CsTbBuono> getBuoni(CeTBaseObject cet);

	public List<CsTbEsenzioneRiduzione> getEsenzioniRiduzioni(CeTBaseObject cet);

	public List<CsTbMotivoChiusuraInt> getMotiviChiusuraIntervento(CeTBaseObject cet);

	public CsTbMotivoChiusuraInt getMotivoChiusuraIntervento(BaseDTO cet);

	public List<CsTbTipoDiario> getTipiDiario(CeTBaseObject cet);

	public List<CsTbTipoRetta> getTipiRetta(CeTBaseObject cet);

	public List<CsTbTipoRientriFami> getTipiRientriFami(CeTBaseObject cet);

	public List<CsTbTipoComunita> getTipiComunita(CeTBaseObject cet);

	public CsTbTipoComunita findTipoComunitaByDesc(BaseDTO cet);

	public CsTbTipoRetta getTipoRetta(BaseDTO cet);

	public CsTbTipoRientriFami getTipoRientriFami(BaseDTO cet);

	public List<CsTbTipoOperatore> getTipiOperatore(CeTBaseObject cet);

	public CsTbTipoOperatore getTipoOperatoreById(BaseDTO dto);

	public List<KeyValueDTO> getinterventiUOL(CeTBaseObject cet);

	public List<KeyValueDTO> getTipiCirc4(CeTBaseObject cet);

	public List<KeyValueDTO> getTipiProgetto(CeTBaseObject cet);

	public CsTbInterventiUOL getInterventiUOLById(BaseDTO dto);

	public CsTbTipoCirc4 getTipoCirc4ById(BaseDTO dto);

	public CsTbTipoProgetto getTipoProgettoById(BaseDTO dto);

	public CsCfgParametri getParametro(BaseDTO dto);

	public CsTbTipoContributo getTipoContributo(BaseDTO cet);

	public List<KeyValueDTO> getAnniScolastici(CeTBaseObject dto);

	public List<KeyValueDTO> getComuniScuole(CeTBaseObject dto);

	public List<KeyValueDTO> getTipoScuole(CeTBaseObject cet);

	public List<KeyValueDTO> searchNomiScuole(ScuoleSearchCriteria dto);

	public List<CsTbSettoreImpiego> getSettoreImpiego(CeTBaseObject cet);

	public List<KeyValueDTO> getTipoContratto(CeTBaseObject cet);

	public TreeMap<String, List<CsTbCondLavoro>> getMappaCondLavoro(CeTBaseObject cet);

	public List<CsTbCondLavoro> getCondLavoro(CeTBaseObject cet);

	public CsTbCondLavoro getCondLavoroById(BaseDTO dto);

	public CsTbCondLavoro getCondLavoroByDescrizione(BaseDTO dto);

	public CsTbProfessione getProfessioneById(BaseDTO d);

	public CsTbSettoreImpiego getSettoreImpiegoById(BaseDTO d);

	public List<CsTbGVulnerabile> getGruppiVulnerab(CeTBaseObject cet);

	public List<KeyValueDTO> getGruppiVulnerabili(CeTBaseObject cet);
	
	public CsTbGVulnerabile getGrVulnerabileById(BaseDTO dto);

	public CsTbDisponibilita getDisponibilitaById(BaseDTO dto);

	public List<CsTbCittadinanzaAcq> getCittadinanzeAcquisite(CeTBaseObject cet);

	public CsTbCittadinanzaAcq getCittadinanzaAcqById(BaseDTO dto);

	public List<CsTbSchedaMultidim> getParamsSchedaMultidim(BaseDTO dto);

	public CsTbSchedaMultidim getParamSchedaMultidim(BaseDTO dto);

	public List<CsTbSottocartellaDoc> getTipoCartelle(BaseDTO dto);

	public CsTbSottocartellaDoc getTipoCartellaById(BaseDTO dto);

	public List<CsTbUnitaMisura> getCsTbUnitaMisura(BaseDTO dto);

	public List<CsTbMicroAttivita> getMicroAttivita(CeTBaseObject cet);

	public CsTbMicroAttivita getMicroAttivitaById(BaseDTO dto);

	public CsTbMacroAttivita getMacroAttivitaById(BaseDTO dto);

	public List<CsTbMacroAttivita> getMacroAttivita(CeTBaseObject cet);

	public List<CsTbMicroAttivita> getMicroAttivitaByIdMacroAttivita(BaseDTO dto);

	public CsOSettore getSettoreById(BaseDTO dto);

	public CsTbTipoRapportoCon getTipoRapportoConByCodice(BaseDTO dto);

	public List<CsTbTipoAlert> getTipiAlert(CeTBaseObject cet);

	public String findCodFormProgetto(BaseDTO dto);

	public List<CsTbFormaGiuridica> getFormeGiuridiche(CeTBaseObject cet);

	public CsTbFormaGiuridica getFormaGiuridicaById(BaseDTO d);

	public CsTbIngMercato getIngMercatoById(BaseDTO d);

	public List<CsCTipoColloquio> getTipoColloquios(BaseDTO dto) throws Exception;

	public List<CsCDiarioDove> getDiarioDoves(BaseDTO dto);

	public List<CsCDiarioConchi> getDiarioConchis(BaseDTO dto);

	public List<CsTbSinaDomanda> getListaDomandaSina(BaseDTO dto);

	public List<CsTbTipoAbitazione> getListaTipoAbitazione(BaseDTO dto);

	public List<CsTbAbitTitoloGodimento> getListaTitoloGod(BaseDTO dto);

	public List<CsTbAbitGestProprietario> getListaGestProprietario(BaseDTO dto);

	public ArTbPrestazioniInps findArTbPrestazioniInpsByCodice(BaseDTO dto);

	public List<Boolean> getFlagNascondiInformazioniFlag(BaseDTO dto); // SISO-812

	public List<ArTbPrestazioniInps> getPrestazioniInpsSinBa(BaseDTO dto);

	public List<CsTbMacroIntervento> readPDSMacro(BaseDTO dto);

	public List<CsTbMicroIntervento> readPDSMicro(BaseDTO dto);

	public List<KeyValueDTO> getStruttureTribunale(CeTBaseObject bo);

	public List<CsTbTipoPai> findListaProgettiPai(CeTBaseObject bo);

	// SISO-1160
	public ArBiInviante findInviante(BaseDTO dto);

	// SISO-1190
	public List<CsTbTitoloStudio> getTbTitoloStudioAbilitato(BaseDTO dto);

	public List<CsTbTipoIsee> getListaTipoIsee(CeTBaseObject cet);

	// SISO-1172
	public List<KeyValueDTO> getLstMotivoChiusuraPai(BaseDTO dto);

	public List<CsTbSecondoLivello> getListaSecondoLivello(CeTBaseObject cet);

	public String findCodiceSinbaMotivoChiusura(BaseDTO b);

	// #ROMACAPITALE
	public List<Long> findIdSettoriByInterventoISTATandInterventoCustom(BaseDTO dto);

	public List<Long> findIdSettoriByInterventoCustom(BaseDTO dto);

	public List<CsOSettore> findSettoriById(List<Long> lst);

	public List<KeyValueDTO> findStruttura(CeTBaseObject cet);

	public List<KeyValueDTO> findArea(BaseDTO dto);

	public List<KeyValueDTO> findAllArea(CeTBaseObject cet);

	public String findCodiceDocumentoGed(BaseDTO dto); // GED
	// #FINE ROMACAPITALE

	public CsTbProgettoAltro getProgettoAltroById(BaseDTO dto); // SISO-1131

	public CsTbProgettoAltro getProgettoAltroByDescrizione(String descrizione);// SISO-1131

	public List<CsTbUnitaMisura> getCsTbUnitaMisuraByInterventoIstatCustom(BaseDTO dto) throws Exception;

	public CsTbTipoIsee getTipoIsee(BaseDTO cet);

	public void salva2Livello(BaseDTO dto);

	// SISO-1278
	public List<CsTbSinaRisposta> getCsTbSinaRispostaByDomandaId(BaseDTO dto);

	public List<SelectItem> getSsProvenienza(CeTBaseObject cet);

	// SISO-1275
	public CsPaiFaseChiusuraDTO getPaiFaseChiusuraById(BaseDTO dto);

	public CsTbMotivoChiusuraPai getMotivoChiusuraPaiById(BaseDTO dto);

	public Boolean esisteAlmenoUnMotivoChiusura(BaseDTO dto);

	/* PROGETTI e SOTTOCORSI */
	public List<ArFfProgetto> findProgettiByBelfioreOrganizzazione(BaseDTO dto); // SISO-522 - modificato SISO-575
	
	public ArFfProgetto getProgettiById(BaseDTO dto);

	public ArFfProgettoAttivita getProgettoAttivitaById(BaseDTO dto);
	
	public List<CsOSettore> getSettoreRiunione(CeTBaseObject cet); //SISO-1481
	
	public ConfigurazioneFseDTO loadCampiFse(BaseDTO dto);

	public List<KeyValueDTO> getListaTipoMinore(CeTBaseObject cet) throws Exception;
	
	public CsTbTipoMinore getTipoMinoreById(BaseDTO dto);
	
	public CsOSettore findSettoreByRelStruttura(BaseDTO dto);
	
	public List<VStrutturaArea> findAllStruttura(CeTBaseObject cet);

	public List<TipoStruttura> getLstTipoStrutturaByTipoFunzione(BaseDTO dto);

	public CsRelSettoreStruttura findSettoreByIdStruttura(BaseDTO dto);

	public boolean verificaUsoArProgettoAttivita(BaseDTO dto);

	public Long findIdProgettoPaiByDesc(BaseDTO dto);

	public CsTbTipoPai findTipoPaiById(BaseDTO dto);

	public List<KeyValueDTO> getDurataRicLavoro(CeTBaseObject cet);

	public CsTbDurataRicLavoro findDurataRicLavoroById(BaseDTO dto);

}
