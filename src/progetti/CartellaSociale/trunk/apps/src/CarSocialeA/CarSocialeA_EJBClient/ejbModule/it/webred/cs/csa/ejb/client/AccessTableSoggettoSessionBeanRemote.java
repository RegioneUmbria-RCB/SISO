package it.webred.cs.csa.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.SearchRdCDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiAnagraficaCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAAnagraficaLog;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;

@Remote
public interface AccessTableSoggettoSessionBeanRemote {
	
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto);
	
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto);
	
	public Boolean esisteSchedaSoggettoByCF(BaseDTO dto);
	
	public List<CsASoggettoLAZY> getSoggettiByDenominazione(BaseDTO dto);
	
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieBySoggetto(BaseDTO dto);
	
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieAttualiBySoggetto(BaseDTO dto);
	
	public void saveSoggettoCategoria(BaseDTO dto);
	
	public boolean eliminaSoggettoCategorieBySoggetto(BaseDTO dto);
	
	public List<CsCCategoriaSociale> getCatSocAttualiBySoggetto(BaseDTO dto);
	
	public List<CsCCategoriaSociale> getCatSocAttualiByCF(BaseDTO dto);
	
	public Boolean existsCatSocAttualiBySoggetto(BaseDTO dto);
		
	public List<DatiCasoListaDTO> getCasiSoggettoLAZY(PaginationDTO dto) throws Throwable;

	public Integer getCasiSoggettoCount(PaginationDTO dto);

	public ContatoreCasiDTO getCasiPerCategoriaCount(PaginationDTO dto);
	
	public CsASoggettoLAZY updateSoggetto(BaseDTO dto);

	public CsASoggettoLAZY saveSoggetto(BaseDTO dto);

	public List<CsASoggettoMedico> getSoggettoMedicoBySoggetto(BaseDTO dto);

	public void saveSoggettoMedico(BaseDTO dto);
	
	public void updateSoggettoMedico(BaseDTO dto);
	
	public void eliminaSoggettoMedicoBySoggetto(BaseDTO dto);

	public List<CsASoggettoStatoCivile> getSoggettoStatoCivileBySoggetto(BaseDTO dto);

	public void saveSoggettoStatoCivile(BaseDTO dto);

	public void updateSoggettoStatoCivile(BaseDTO dto);
	
	public void eliminaSoggettoStatoCivileBySoggetto(BaseDTO dto);
	
	public List<CsASoggettoStatus> getSoggettoStatusBySoggetto(BaseDTO dto);

	public void saveSoggettoStatus(BaseDTO dto);

	public void updateSoggettoStatus(BaseDTO dto);
	
	public void eliminaSoggettoStatusBySoggetto(BaseDTO dto);

	public CsAAnagrafica getAnagraficaById(BaseDTO dto);
	
	public CsAAnagrafica saveAnagrafica(BaseDTO dto);

	public CsASoggettoLAZY getSoggettoById(BaseDTO dto);

	//public CsOOrganizzazione getLastOrganizzazioneSoggetto(BaseDTO dto) throws Exception;

	public CsOSettore getLastItStepSettoreSoggetto(BaseDTO dto) throws Exception;

	public CsASoggettoStatoCivile getStatoCivileAttualeBySoggetto(BaseDTO dto);

	public CsAAnagraficaLog saveAnagraficaLog(BaseDTO dto);//SISO-874

	public Long salvaAnagraficaCaso(DatiAnagraficaCasoDTO casoDTO) throws Exception;

	public List<CsRdcAnagraficaGepi> getAnagraficheRdCGepi(SearchRdCDTO dto) throws Throwable;

	public Integer getAnagraficheRdCGepiCount(SearchRdCDTO dto);
	
	public Boolean isBeneficiarioRdC(BaseDTO dto);

	public Boolean hasNucleoBeneficiarioRdC(BaseDTO dto);
	//SISO-1127  Inizio
	public List<CsAComponenteAnagCasoGit> getAnagraficaComponenteCasoProvenienza(BaseDTO bDto);
	//SISO-1266
	public List<CsAComponenteAnagCasoGit> getAnagraficaComponenteCasoNoIdProvenienza(BaseDTO bDto);
	public void elaboraVariazioniSoggettoCasoGit(BaseDTO dto);
	public List<CsAComponenteAnagCasoGit> getAnagraficheCasiGitAggiornate(BaseDTO dto);
	public void updateAnagraficaComponenteCaso(BaseDTO bDto);
	public CsAComponenteAnagCasoGit getAnagraficaCasoGitAggiornataBySoggettoId(BaseDTO dto);
	public CsAComponenteAnagCasoGit getAnagraficaComponenteCasoSoggettoId(BaseDTO bDto);

	public void saveAggiornamentoAnagraficaCasoGit(BaseDTO dto);

	//SISO-1127 Fine


	
	
}
