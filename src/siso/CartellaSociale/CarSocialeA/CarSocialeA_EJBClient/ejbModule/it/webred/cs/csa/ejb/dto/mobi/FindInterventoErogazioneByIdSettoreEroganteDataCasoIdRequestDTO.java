package it.webred.cs.csa.ejb.dto.mobi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO implements Serializable {

	private static final long serialVersionUID = 7609974482932118973L;
	private List<BigDecimal>  idSettori;
	private Date dataValiditaErogazione;
	private BigDecimal casoId;
	
	public List<BigDecimal> getIdSettori() {
		return idSettori;
	}
	public void setIdSettori(List<BigDecimal> idSettori) {
		this.idSettori = idSettori;
	}
	public Date getDataValiditaErogazione() {
		return dataValiditaErogazione;
	}
	public void setDataValiditaErogazione(Date dataValiditaErogazione) {
		this.dataValiditaErogazione = dataValiditaErogazione;
	}
	public BigDecimal getCasoId() {
		return casoId;
	}
	public void setCasoId(BigDecimal casoId) {
		this.casoId = casoId;
	}
		
}
