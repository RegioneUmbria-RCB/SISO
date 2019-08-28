package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaDTO;
import it.webred.cs.data.model.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;
import java.util.TreeMap;

import javax.ejb.Remote;

@Remote
public interface AccessTableConfigurazioneSessionBeanRemote {

	public List<CsTbStatoCivile> getStatoCivile(CeTBaseObject cet);

	public CsTbStatoCivile getStatoCivileByIdExtCet(BaseDTO dto);

	public CsTbStatoCivile getStatoCivileByDescrizione(BaseDTO dto);

	public CsTbStatoCivile getStatoCivileByCodice(BaseDTO dto);

	public List<CsTbStatus> getStatus(CeTBaseObject cet);

	public CsTbStatus getStatusById(BaseDTO dto);

	public List<CsTbPermesso> getPermesso(CeTBaseObject cet);
	
	public List<CsTbAssenzaPermesso> getPermessoSenzaSoggiorno(CeTBaseObject cet);  //SISO-792
	
	public CsTbAssenzaPermesso getAssenzaPermessoById(BaseDTO dto);  //SISO-792

	public CsTbPermesso getPermessoById(BaseDTO dto);

	public List<CsTbTipoIndirizzo> getTipoIndirizzo(CeTBaseObject cet);

	public List<CsTbTipoContributo> getTipoContributo(CeTBaseObject cet);

	public List<CsTbTipologiaFamiliare> getTipologieFamiliari(CeTBaseObject cet);

	public CsTbTipologiaFamiliare getTipologiaFamiliareById(BaseDTO dto);

	public List<CsTbResponsabilita> getResponsabilita(CeTBaseObject cet);

	public List<CsTbProblematica> getProblematiche(CeTBaseObject cet);
	
	public CsTbProblematica getProblematicaById(BaseDTO dto);

	public List<CsTbProbl> getProbl(CeTBaseObject cet);
	
	public CsTbProbl getProblById(BaseDTO cet);

	public List<CsTbStesuraRelazioniPer> getStesuraRelazioniPer(CeTBaseObject cet);

	public List<CsTbTitoloStudio> getTitoliStudio(CeTBaseObject cet);

	public CsTbTitoloStudio getTitoloStudioById(BaseDTO dto);

	public List<CsTbProfessione> getProfessioni(CeTBaseObject cet);

	public List<CsTbTutela> getTutele(CeTBaseObject cet);

	public void salvaOrganizzazione(BaseDTO dto);

	public void updateOrganizzazione(BaseDTO dto);

	public void eliminaOrganizzazione(BaseDTO dto);

	public List<CsOOrganizzazione> getOrganizzazioniAll(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioni(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioniBelfiore(CeTBaseObject cet);

	public CsOOrganizzazione getOrganizzazioneByBelfiore(BaseDTO dto);
	
	public CsOOrganizzazione getOrganizzazioneById(BaseDTO dto);
	
	/* SISO-663 SM */
	public CsOOrganizzazione getOrganizzazioneCapofila(CeTBaseObject cet);
	/* -=- */

	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto);

	public List<CsOSettore> getSettoreAll(CeTBaseObject cet);

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
	
	public CsTbTipoRapportoCon getTipoRapportoDaRelazPar(BaseDTO cet);

	public List<CsTbPotesta> getPotesta(CeTBaseObject cet);

	public List<CsTbDisponibilita> getDisponibilita(CeTBaseObject cet);

	public List<CsTbTipoPai> getTipoPai(CeTBaseObject cet);

	public List<CsTbMotivoChiusuraPai> getMotivoChiusuraPai(CeTBaseObject cet);

	public List<CsTbContatto> getContatti(CeTBaseObject cet);

	public List<CsTbMacroSegnal> getMacroSegnalazioni(CeTBaseObject cet);

	public CsTbMacroSegnal getMacroSegnalazioneById(BaseDTO dto);

	public List<CsTbMicroSegnal> getMicroSegnalazioni(CeTBaseObject cet);

	public CsTbMicroSegnal getMicroSegnalazioneById(BaseDTO dto);

	public List<CsTbMotivoSegnal> getMotivoSegnalazioni(CeTBaseObject cet);

	public CsTbMotivoSegnal getMotivoSegnalazioneById(BaseDTO dto);

	public List<CsTbDisabEnte> getDisabEnte(CeTBaseObject cet);

	public List<CsTbDisabGravita> getDisabGravita(CeTBaseObject cet);

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

	public List<CsTbInterventiUOL> getinterventiUOL(CeTBaseObject cet);

	public List<CsTbTipoCirc4> getTipiCirc4(CeTBaseObject cet);

	public List<CsTbTipoProgetto> getTipiProgetto(CeTBaseObject cet);

	public CsTbInterventiUOL getInterventiUOLById(BaseDTO dto);

	public CsTbTipoCirc4 getTipoCirc4ById(BaseDTO dto);

	public CsTbTipoProgetto getTipoProgettoById(BaseDTO dto);

	public CsCfgParametri getParametro(BaseDTO dto);

	public CsTbTipoContributo getTipoContributo(BaseDTO cet);

	public List<CsTbScuola> getScuole(CeTBaseObject cet);

	public List<String> getComuniScuole(CeTBaseObject dto);

	public List<CsTbScuola> getScuoleByComuneTipo(BaseDTO dto);

	public List<CsTbScuola> getScuoleByComuneAnnoTipo(BaseDTO dto);

	public List<CsTbTipoScuola> getTipoScuole(CeTBaseObject cet);

	public List<CsTbSettoreImpiego> getSettoreImpiego(CeTBaseObject cet);

	public List<CsTbTipoContratto> getTipoContratto(CeTBaseObject cet);

	public TreeMap<String, List<CsTbCondLavoro>> getMappaCondLavoro(CeTBaseObject cet);

	public List<CsTbCondLavoro> getCondLavoro(CeTBaseObject cet);

	public CsTbCondLavoro getCondLavoroById(BaseDTO dto);

	public CsTbProfessione getProfessioneById(BaseDTO d);

	public CsTbSettoreImpiego getSettoreImpiegoById(BaseDTO d);

	public List<CsTbGVulnerabile> getGruppiVulnerab(CeTBaseObject cet);

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

	public List<CsTbMapTipoRapGit2Cs> caricaMappaRelazioniParentaliEnte(CeTBaseObject cet);

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
	
	public List<ArTbPrestazioniInps> getPrestazioniInpsSina(BaseDTO dto);

    public CsTbTipoAbitazione  getParamAbitazione(BaseDTO dto);
	
	public CsTbAbitTitoloGodimento getParamTitoloGod(BaseDTO dto);

	public ArTbPrestazioniInps findArTbPrestazioniInpsByCodice(BaseDTO dto);
}
