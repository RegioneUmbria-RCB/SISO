package it.webred.cs.csa.ejb.dto.mobi;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogSettoreTipoIntCfgDTO;
import it.webred.cs.data.model.view.VMobiIntErog;

import java.io.Serializable;
import java.util.List;

public class FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO implements Serializable {

	private static final long serialVersionUID = 7609974482932118973L;
	private List<VMobiIntErog> vMobiIntErog;
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
	
}
