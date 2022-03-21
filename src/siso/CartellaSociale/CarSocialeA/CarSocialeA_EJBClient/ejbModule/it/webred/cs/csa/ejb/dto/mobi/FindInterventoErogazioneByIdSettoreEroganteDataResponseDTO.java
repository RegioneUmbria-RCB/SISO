package it.webred.cs.csa.ejb.dto.mobi;

import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogSettoreTipoIntCfgDTO;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.view.VMobiCasi;
import it.webred.cs.data.model.view.VMobiIntErog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

public class FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO implements Serializable {

	private static final long serialVersionUID = 7609974482932118973L;
	private List<VMobiIntErog> vMobiIntErog;
	//TASK SISO 1044
	private List<VmobiCasiDTO> vMobiCasiDTO;
	
	//private List<DiarioAnagraficaDTO> anagraficheSoggettiInteressati;
	
	private List<SelectItem> catalogoattivita;
	private List<SelectItem> catalogoProblematiche;
	private List<CsCDiarioConchi> listaConChi;
	private List<SelectItem> listaRiunioneCon;
	private List<SelectItem> listaRichiestaIndagine;
	private HashMap<String, List<TriageItemDTO>> triageValueMap;
	//private List<CsRelRelazioneProbl> problematichePrecedenti;
	//**************

	private List<ErogSettoreTipoIntCfgDTO> configInterventi;
	
	private List<KeyValueDTO> lstDiarioConChi;
	private List<KeyValueDTO> lstDiarioDove;
	private List<KeyValueDTO> lstTipoColloquio;

	public List<VMobiIntErog> getvMobiIntErog() {
		return vMobiIntErog;
	}

	public void setvMobiIntErog(List<VMobiIntErog> vMobiIntErog) {
		this.vMobiIntErog = vMobiIntErog;
	}

	public List<ErogSettoreTipoIntCfgDTO> getConfigInterventi() {
		return configInterventi;
	}

	public void setConfigInterventi(List<ErogSettoreTipoIntCfgDTO> configInterventi) {
		this.configInterventi = configInterventi;
	}

	public List<KeyValueDTO> getLstDiarioConChi() {
		return lstDiarioConChi;
	}

	public void setLstDiarioConChi(List<KeyValueDTO> lstDiarioConChi) {
		this.lstDiarioConChi = lstDiarioConChi;
	}

	public List<KeyValueDTO> getLstDiarioDove() {
		return lstDiarioDove;
	}

	public void setLstDiarioDove(List<KeyValueDTO> lstDiarioDove) {
		this.lstDiarioDove = lstDiarioDove;
	}

	public List<KeyValueDTO> getLstTipoColloquio() {
		return lstTipoColloquio;
	}

	public void setLstTipoColloquio(List<KeyValueDTO> lstTipoColloquio) {
		this.lstTipoColloquio = lstTipoColloquio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

		
	public List<SelectItem> getListaRiunioneCon() {
		return listaRiunioneCon;
	}

	public void setListaRiunioneCon(List<SelectItem> listaRiunioneCon) {
		this.listaRiunioneCon = listaRiunioneCon;
	}

	

	public HashMap<String, List<TriageItemDTO>> getTriageValueMap() {
		return triageValueMap;
	}

	public void setTriageValueMap(
			HashMap<String, List<TriageItemDTO>> triageValueMap) {
		this.triageValueMap = triageValueMap;
	}

	
	public List<SelectItem> getCatalogoattivita() {
		return catalogoattivita;
	}

	public void setCatalogoattivita(List<SelectItem> catalogoattivita) {
		this.catalogoattivita = catalogoattivita;
	}

	public List<SelectItem> getCatalogoProblematiche() {
		return catalogoProblematiche;
	}

	public void setCatalogoProblematiche(List<SelectItem> catalogoProblematiche) {
		this.catalogoProblematiche = catalogoProblematiche;
	}

	public List<CsCDiarioConchi> getListaConChi() {
		return listaConChi;
	}

	public void setListaConChi(List<CsCDiarioConchi> listaConChi) {
		this.listaConChi = listaConChi;
	}

	public List<SelectItem> getListaRichiestaIndagine() {
		return listaRichiestaIndagine;
	}

	public void setListaRichiestaIndagine(List<SelectItem> listaRichiestaIndagine) {
		this.listaRichiestaIndagine = listaRichiestaIndagine;
	}

	public List<VmobiCasiDTO> getvMobiCasiDTO() {
		return vMobiCasiDTO;
	}

	public void setvMobiCasiDTO(List<VmobiCasiDTO> vMobiCasiDTO) {
		this.vMobiCasiDTO = vMobiCasiDTO;
	}
	
}
