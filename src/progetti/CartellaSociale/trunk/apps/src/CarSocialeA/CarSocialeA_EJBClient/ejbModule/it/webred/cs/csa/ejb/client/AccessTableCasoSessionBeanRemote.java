package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiOperatoreBean;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableCasoSessionBeanRemote {
	
	public List<CsACaso> findAll(CeTBaseObject dto);

	public CsACaso findCasoById(BaseDTO dto);

	public void salvaOperatoreCaso(BaseDTO dto);
	
	public void updateOperatoreCaso(BaseDTO dto);

	public void eliminaOperatoreTipoOpByCasoId(BaseDTO dto);
	
	public void eliminaOperatoreTipoOpById(BaseDTO dto);

	public List<CsACasoOpeTipoOpe> getListaOperatoreTipoOpByCasoId(BaseDTO dto);
	
	public List<CsACasoAccessoFascicolo> getListaAccessoFascicoloByCasoId(BaseDTO dto); //SISO-812

	public Boolean existsTipoOperatore(BaseDTO dto);
	
	public List<CasiOperatoreBean> loadCaricoLavoroByCatSocOrg(BaseDTO dto) throws Exception;

	public void updateCaso(BaseDTO dto);
	
	public void eliminaOperatoreTipoOp2ByCasoId(BaseDTO dto);
	
	public void eliminaAccessoFascicoloByCasoId(BaseDTO dto);//SISO-812

	public void salvaOperatoreCaso2(BaseDTO dto);
	
	public void salvaAccessoFascicoloCaso(BaseDTO dto);  //SISO-812

	public List<CsACasoOpeTipoOpe2> getListaOperatoreTipoOp2ByCasoId(BaseDTO dto);

	public DatiOperatoreDTO findResponsabileCaso(BaseDTO dto);
	
	public Long aggiornaResponsabileCaso(BaseDTO dto);

	public Boolean getFlagGestioneFascicolo(BaseDTO dto);

    public List<CsACaso> findCasoByCognomeAndNome(BaseDTO dto);

	public Integer countDatiStorici(BaseDTO dto);
	
	public List<CsACasoAccessoFascicolo> findAccessoFascicoloByIdOrganizzazioneAndIdSettore(BaseDTO dto); //SISO-812
	
	public boolean getFlagNascondiInformazioniAttualeByCasoSettoreOrganizzazione(BaseDTO dto); //SISO-812

	public List<VSsSchedeUdcDiff> controllaModificheSchedaCompletaUDC(CeTBaseObject dto);

	public CsACaso inizializzaNuovoCaso(CeTBaseObject dto);
	
	public CsOOperatoreSettore findDestinatarioAlertCaso(BaseDTO dto);
}
