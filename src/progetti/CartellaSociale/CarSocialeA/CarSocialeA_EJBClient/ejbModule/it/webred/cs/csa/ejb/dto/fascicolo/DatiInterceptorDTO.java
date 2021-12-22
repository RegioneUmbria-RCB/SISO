package it.webred.cs.csa.ejb.dto.fascicolo;

import java.io.Serializable;

public class DatiInterceptorDTO implements Serializable {

	private Long settSecondoLivello;
	private Long idSettoreDiario;
	
	public Long getSettSecondoLivello() {
		return settSecondoLivello;
	}
	public void setSettSecondoLivello(Long settSecondoLivello) {
		this.settSecondoLivello = settSecondoLivello;
	}
	public Long getIdSettoreDiario() {
		return idSettoreDiario;
	}
	public void setIdSettoreDiario(Long idSettoreDiario) {
		this.idSettoreDiario = idSettoreDiario;
	}
	
}
