package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.*;
import it.webred.cs.data.model.*; 
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableDiarioSessionBeanRemote {
	// TODO: HiWeb
	public CsDDiario createDiario(BaseDTO dto) throws Exception;

	public CsDDiario updateDiario(BaseDTO dto) throws Exception;

	public CsDDiario updateDiarioNR(BaseDTO dto) throws Exception;

	public void saveDiarioChild(BaseDTO dto);

	public List<CsDColloquioBASIC> getColloquios(BaseDTO dto) throws Exception;

	public CsDColloquio findColloquioById(BaseDTO dto) throws Exception;

	public void updateColloquio(BaseDTO dto) throws Exception;

	public CsTbTipoDiario findTipoDiarioById(BaseDTO tipoDiarioIdDTO) throws Exception;

	public CsDDiario findDiarioById(BaseDTO dto) throws Exception;

	public List<RelazioneDTO> findRelazioniByCaso(BaseDTO i);

	public List<CsDRelazione> findRelazioniByCasoTipoIntervento(InterventoDTO i);

	public CsDRelazione findRelazioneLazyById(BaseDTO dto);

	public RelazioneDTO findRelazioneFullById(BaseDTO dto);

	public void deleteDiario(BaseDTO b);

	public RelazioneDTO saveRelazione(BaseDTO dto) throws Exception;

	public void updateRelazione(BaseDTO dto) throws Exception;

	public List<CsDDiarioDoc> findDiarioDocById(BaseDTO b);

	public void saveDiarioDoc(BaseDTO b);

	public void deleteDiarioDoc(BaseDTO b);

	public List<CsDValutazione> getSchedeValutazioneSoggetto(BaseDTO dto);

	public List<CsDValutazione> getSchedeValutazioneUdcId(BaseDTO dto);

	public void deleteSchedeValutazioneByUdcId(BaseDTO dto) throws Exception;

	public CsDClob createClob(BaseDTO clobDTO);

	public void updateClob(BaseDTO clobDTO);

	public void updateSchedaMultidimAnz(BaseDTO schedaMultidimAnzDto);

	public CsDPai saveSchedaPai(PaiDTO dto) throws Exception;

	public void deleteSchedaPai(PaiDTO dto);

	public CsDValutazione saveSchedaJson(JsonBaseDTO jsonbaseDTO) throws Exception;

	public void saveSchedaBarthel(SchedaBarthelDTO schedaBarthelDTO) throws Exception;

	public CsDValutazione findValutazioneChildByPadreId(JsonBaseDTO dto);

	public List<CsDDocIndividuale> findDocIndividualiByCaso(BaseDTO dto);

	public void updateDocIndividuale(BaseDTO dto) throws Exception;

	public CsDDiario saveDocIndividuale(BaseDTO dto) throws Exception;

	public void deleteDocIndividuale(BaseDTO dto) throws Exception;

	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByTipoDiarioId(RelSettCatsocEsclusivaDTO dto);

	public CsRelSettCatsocEsclusiva findRelSettCatsocEsclusivaByIds(RelSettCatsocEsclusivaDTO dto);

	public void saveCsRelSettCatsocEsclusiva(BaseDTO dto);

	public void updateCsRelSettCatsocEsclusiva(BaseDTO dto);

	public void deleteCsRelSettCatsocEsclusiva(RelSettCatsocEsclusivaDTO dto);

	public List<CsDScuola> findScuoleByCaso(BaseDTO dto);

	public void updateScuola(BaseDTO dto) throws Exception;

	public CsDDiario saveScuola(BaseDTO dto) throws Exception;

	public void deleteScuola(BaseDTO dto) throws Exception;

	public List<CsDIsee> findIseeByCaso(BaseDTO dto);

	public void updateIsee(BaseDTO dto) throws Exception;

	public CsDDiario saveIsee(BaseDTO dto) throws Exception;

	public void deleteIsee(BaseDTO dto) throws Exception;

	public void deleteSchedaJson(BaseDTO dto) throws Exception;

	public CsDDiarioBASIC findDiarioBASICById(BaseDTO dto) throws Exception;

	public CsDIsee findIseeById(BaseDTO dto);

	public CsFlgIntervento findFglInterventoById(BaseDTO dto);

	public CsDValutazione findValutazioneById(BaseDTO dto);

	public List<CsDRelazione> findRelazioniInScadenza(CeTBaseObject i);
	
	public PaiDTO findPaiFullById(BaseDTO dto);

	public CsDPai savePai(BaseDTO dto) throws Exception;

	public void deletePai(BaseDTO dto);

	public void updatePai(BaseDTO dto) throws Exception;

	public List<CsDPai> findPaiByCaso(BaseDTO dto);

	public void salvaRifRelazioneToPai(BaseDTO dto);

	public CsCDiarioConchi getDiarioConchi(BaseDTO dto);
	
	public CsDRelazione findLastRelazioneProblAperte(BaseDTO dto);

	public void deleteCsRelRelazioneProbl(BaseDTO dto);

	public List<CsDDiario> findDiarioBySchedaId(BaseDTO dto);

	public CsDValutazione saveSchedaValutazione(CsDValutazione schedaValutazione);
	
	public CsDDocIndividuale findDocIndividualeById(BaseDTO dto); //SISO-438

	public void createAndsaveDocIndividuale(DocIndividualeDTO dto) throws Exception; //SISO-438

	public List<CsDDocIndividuale> findDocIndividualeByCfSchedaSegnalato(BaseDTO dto); //SISO-438

	public void salvaColloquio(BaseDTO dto) throws Exception;
	
	public List<DiarioAnagraficaDTO> findDiarioAnagraficaByDiarioId(BaseDTO dto); //SISO-763
	
	public List<DiarioAnagraficaDTO> saveDiarioAnagrafica(BaseDTO dto) throws Exception; //SISO-763
	
	public List<Long> findDiarioAnaAttProfessionaliCasoIdsByAnagraficaId(BaseDTO bdto) throws Exception; //SISO-763
	
	public List<CsDValutazione> getSchedeValutazionebyTipo(BaseDTO dto);   // SISO-818
	
	public ConfrontoSsCsDTO estraiDatiSchedaSS(BaseDTO dto);

	public ConfrontoSsCsDTO estraiDatiSchedaCS(BaseDTO dto);

	public List<CsDPai> findPaiAperti(CeTBaseObject dto);
}
