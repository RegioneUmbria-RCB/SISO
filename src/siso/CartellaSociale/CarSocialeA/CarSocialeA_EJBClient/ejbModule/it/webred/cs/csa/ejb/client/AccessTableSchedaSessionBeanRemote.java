package it.webred.cs.csa.ejb.client;

import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiSocialiSintesiDTO;
import it.webred.cs.csa.ejb.dto.cartella.RisorsaCalcDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsATribunale;


@Remote
public interface AccessTableSchedaSessionBeanRemote {

	public void saveCsA(BaseDTO dto);
	
	public void updateCsA(BaseDTO dto);
	
	public void eliminaCsById(BaseDTO dto);
	
	public List<?> findCsBySoggettoId(BaseDTO dto) ;
	
	public CsADisabilita getDisabilitaById(BaseDTO dto);

	public CsADatiSociali getDatiSocialiById(BaseDTO dto);

	public CsADatiInvalidita getDatiInvaliditaById(BaseDTO dto);

	public CsATribunale getTribunaleById(BaseDTO dto);

	public CsAFamigliaGruppo getFamigliaGruppoById(BaseDTO dto);

	public List<CsAComponente> findComponentiFamigliaAllaDataBySoggettoId(BaseDTO dto);
	
	public List<RisorsaCalcDTO> findComponentiGiaFamigliariBySoggettoCf(BaseDTO dto);

	public DatiSocialiSintesiDTO findDatiSocialiAttiviBySoggettoCf(BaseDTO dto);

	public Hashtable<String, RisorsaFamDTO> findRisorseFamiliariBySoggettoCf(BaseDTO dto);
	
	public CsAComponente findComponenteById(BaseDTO dto);

	public void verificaAllineamentoDatiInvalidita(BaseDTO dto);
}