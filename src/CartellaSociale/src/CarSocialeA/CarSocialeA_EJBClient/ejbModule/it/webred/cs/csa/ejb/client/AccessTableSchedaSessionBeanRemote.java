package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAContributi;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAServizi;
import it.webred.cs.data.model.CsATribunale;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccessTableSchedaSessionBeanRemote {

	public void saveCsA(BaseDTO dto);
	
	public void updateCsA(BaseDTO dto);
	
	public void eliminaCsById(BaseDTO dto);
	
	public List<?> findCsBySoggettoId(BaseDTO dto);
	
	public CsADisabilita getDisabilitaById(BaseDTO dto);

	public CsADatiSociali getDatiSocialiById(BaseDTO dto);

	public CsADatiInvalidita getDatiInvaliditaById(BaseDTO dto);

	public CsATribunale getTribunaleById(BaseDTO dto);

	public CsAServizi getServiziById(BaseDTO dto);

	public CsAContributi getContributiById(BaseDTO dto);

	public CsAFamigliaGruppo getFamigliaGruppoById(BaseDTO dto);

	public CsAFamigliaGruppo findFamigliaAllaDataBySoggettoId(BaseDTO dto);
	
	public List<CsAAnagrafica> findComponentiGiaFamigliariBySoggettoCf(BaseDTO dto);
	
	public List<CsACasoOpeTipoOpe> findCasoOpeTipoOpeByCasoId(BaseDTO dto);

	public Boolean existsDatiSocialiAttiviBySoggettoCf(BaseDTO dto);

	public Hashtable<String, RisorsaFamDTO> findRisorseFamiliariBySoggettoCf(BaseDTO dto);
	
	
	
	
}