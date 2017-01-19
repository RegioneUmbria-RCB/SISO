package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableSoggettoSessionBeanRemote {
	
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto);
	
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto);
	
	public Boolean esisteSchedaSoggettoByCF(BaseDTO dto);
	
	public List<CsASoggettoLAZY> getSoggettiByDenominazione(BaseDTO dto);
	
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieBySoggetto(BaseDTO dto);
	
	public List<CsASoggettoCategoriaSocLAZY> getSoggettoCategorieAttualiBySoggetto(BaseDTO dto);
	
	public void saveSoggettoCategoria(BaseDTO dto);
	
	public boolean eliminaSoggettoCategorieBySoggetto(BaseDTO dto);
	
	public List<CsCCategoriaSocialeBASIC> getCatSocAttualiBySoggetto(BaseDTO dto);
		
	public List<DatiCasoListaDTO> getCasiSoggettoLAZY(PaginationDTO dto);

	public Integer getCasiSoggettoCount(PaginationDTO dto);

	public ContatoreCasiDTO getCasiPerCategoriaCount(PaginationDTO dto);
	
	public void updateSoggetto(BaseDTO dto);

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

}
