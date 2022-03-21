package it.webred.cs.csa.ejb.dto.mobi;

import java.io.Serializable;
import java.util.List;

import it.webred.cs.data.model.view.VMobiCasi;

public class FindCasiByOperatoreBySoggettoResponseDTO implements Serializable {

	private static final long serialVersionUID = 7609974482932118973L;
	private List<VMobiCasi> vMobiCasi;
	
	public List<VMobiCasi> getvMobiCasi() {
		return vMobiCasi;
	}

	public void setvMobiCasi(List<VMobiCasi> vMobiCasi) {
		this.vMobiCasi = vMobiCasi;
	}
			
}
