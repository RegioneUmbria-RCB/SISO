package it.webred.ss.ejb.client;

import it.webred.ss.data.model.ArBufferSsInvio;
import it.webred.ss.data.model.ArOOrganizzazione;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsClassificazioneMotivazione;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsIntervento;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsInterventoEconomicoTipo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsSchedaPrivacy;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.SintesiSchedeUfficioDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SsSchedaSessionBeanRemote {
	
	public void saveConsensoPrivacy(BaseDTO dto);
	public Long saveAccesso(BaseDTO dto);
	public Long saveSegnalante(BaseDTO dto);
	public Long saveRiferimento(BaseDTO dto);
	public Long saveMotivazione(BaseDTO dto);
	public Long saveServizi(BaseDTO dto);
	public SsScheda saveScheda(BaseDTO dto);
	public Long saveAnagrafica(BaseDTO dto);
	public SsSchedaSegnalato saveSegnalato(BaseDTO dto);
	public Long writeNotaDiario(BaseDTO dto);
	public Long writeIndirizzo(BaseDTO dto);
	public void eliminaScheda(BaseDTO dto);
	
	public void updateCompletamentoScheda(BaseDTO dto);
	
	public void updateAccesso(BaseDTO dto);
	public void updateSegnalante(BaseDTO dto);
	public void updateRiferimento(BaseDTO dto);
	public void updateMotivazione(BaseDTO dto);
	public void updateInterventi(BaseDTO dto);
	public void updateNotaDiario(BaseDTO dto);
	public void deleteNotaDiario(BaseDTO dto);

	public SsScheda readScheda(BaseDTO dto);
	public List<SsScheda> readSchedeIn(BaseDTO dto);
	public List<SsUfficio> readUffici(BaseDTO dto);
	public List<SsRelUffPcontOrg> readUffPcontByOrganizzazione(BaseDTO dto);
	public SsUfficio readUfficio(BaseDTO dto);
	public List<SsMotivazione> readMotivazioni(BaseDTO dto);
	public List<SsMotivazione>readMotivazioniByClasId(BaseDTO dto);
	
	public List<SsClassificazioneMotivazione> readClassificazioneMotivazione(BaseDTO dto);
	public SsMotivazione readMotivazione(BaseDTO dto);
	public List<SsIntervento> readInterventi(BaseDTO dto);
	public SsIntervento readIntervento(BaseDTO dto);
		
	public boolean esistonoPContatto(BaseDTO dto);
	
	public void writeMotivazioneScheda(BaseDTO dto);
	public void writeInterventoScheda(BaseDTO dto);
	public void deleteMotivazioniScheda(BaseDTO dto);
	public void deleteInterventiScheda(BaseDTO dto);
	public List<SsMotivazioniSchede> readMotivazioniScheda(BaseDTO dto);
	public List<String> readMotivazioniSchedaById(BaseDTO dto);
	public List<SsInterventiSchede> readInterventiScheda(BaseDTO dto);
	
	public SsAnagrafica readAnagraficaByCf(BaseDTO dto);
	public SsAnagrafica readAnagraficaById(BaseDTO dto);
	public List<SsAnagrafica> readAnagraficheByCf(BaseDTO dto);
	public HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>>  countAnagraficaInSsInComplete(BaseDTO dto);
	public HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>>  countAnagraficaInSsComplete(BaseDTO dto);
	public Boolean isAnagraficaEsterna(BaseDTO dto);	
	
	public List<SsSchedaSegnalato> readSegnalati(BaseDTO dto);
	public SsSchedaSegnalato readSegnalatoById(BaseDTO dto);
	public SsSchedaSegnalante readSegnalanteById(BaseDTO dto);
	public List<SsScheda> readSchede(BaseDTO dto);
	public List<SsScheda> readSchedeByCF(BaseDTO dto);
	public List<SsScheda> readSchedeByUfficioCF(BaseDTO dto);
	public List<SsSchedaSegnalato> readSchedeSegnalatoByCF(BaseDTO dto);
	public List<SsSchedaSegnalato> readSchedeByDenominazione(BaseDTO dto);
	public List<SsDiario> readDiarioSociale(BaseDTO dto);
	public List<SsInterventoEconomico> readInterventiEconomici(BaseDTO dto);
	public List<SsInterventoEconomicoTipo> readInterventiEconomiciTipi(BaseDTO dto);
	public void writeInterventoEconomico(BaseDTO dto);
	public SsInterventoEconomicoTipo readInterventoEconomicoTipoByTipo(BaseDTO dto);
	public boolean isSchedaCompleta(BaseDTO dto);
	public SsSchedaPrivacy findSchedaPrivacyById(BaseDTO dto);
	public SsSchedaPrivacy findSchedaPrivacyByCfEnte(BaseDTO dto);
	
	public List<SsTipoScheda> readTipiScheda(BaseDTO dto);
	public SsTipoScheda readTipoSchedaByTipo(BaseDTO dto);
	public SsTipoScheda readTipoSchedaById(BaseDTO dto);
	public SsRelUffPcontOrg getSsRelUffPcontOrg(BaseDTO dto);
	
	/*Lista Schede UDC*/
	public Long countTotaleSchedeInUfficio(BaseDTO dto);
	public Long countSchedeSospeseInUfficio(BaseDTO dto);
	public Long countSchedeInCaricoInUfficio(BaseDTO dto);
	public Long countSchedeEliminateInUfficio(BaseDTO dto);
	
	public List<DatiSchedaListDTO> searchSchedeInUfficio(SsSearchCriteria dto);
	public Long countSchedeInUfficio(SsSearchCriteria dto);
	
	//public List<SsScheda> readSchedeSoggetto(BaseDTO dto);
	public Long countSchedeCompleteSoggetto(SsSearchCriteria dto);
	public List<DatiSchedaListDTO> searchSchedeCompleteSoggetto(SsSearchCriteria ss);
	
	public List<DatiSchedaListDTO> searchSchedeIncomplete(SsSearchCriteria dto);
	public Long countSchedeIncomplete(SsSearchCriteria dto);
	public List<OperatoreDTO> getListaOperatori(BaseDTO dto);
	public List<SsOOrganizzazione> readOrganizzazioniZona(BaseDTO dto);
	public List<ArOOrganizzazione> readOrganizzazioniFuoriZona(BaseDTO dto);
	public List<SsOOrganizzazione> readOrganizzazioniAltre(BaseDTO dto);
	public List<SsRelUffPcontOrg> readUfficiOrganizzazione(BaseDTO dto);
	public ArBufferSsInvio inviaScheda(BaseDTO dto);
	public SsOOrganizzazione readSsOOrganizzazioniById(BaseDTO dto);
	public ArOOrganizzazione readArOOrganizzazioniById(BaseDTO dto);
	public List<ArBufferSsInvio> readSchedeInviateByIdOrigZs(BaseDTO dto);
	public void annullaInvioScheda(BaseDTO dto);
	public int countSchedeInviateUfficioNonLette(SsSearchCriteria dto);
	public int countSchedeInviateEnteNonLette(SsSearchCriteria dto);
	public Long countSchedeInviateUfficio(SsSearchCriteria dto);
	public Long countSchedeInviateEnte(SsSearchCriteria dto);
	public List<DatiSchedaListDTO> searchSchedeInviateUfficio(SsSearchCriteria dto);
	public List<DatiSchedaListDTO> searchSchedeInviateEnte(SsSearchCriteria dto);
	public ArOOrganizzazione readSsArOOrganizzazioniById(BaseDTO dto);
	public Long saveAccessoInviante(BaseDTO bAccessoDto);
	public ArBufferSsInvio riceviSchedaBuffer(BaseDTO ricevutaBufferdto);
	public SsSchedaAccessoInviante readSsSchedaAccessoInvianteByIdNuovaScheda(BaseDTO dto);
	public boolean esistonoDuplicatiCF(BaseDTO dto);	
}
