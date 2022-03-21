package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;
import java.util.Date;

public class CsPaiAffidoStatoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private CsPaiAffidoDTO affido;
	
	private Integer codice;

	private Date dataDa;

	private Date dataA;

	private Long paiAffidoId;
	
	private String descrizione;
	
	private Date dataStatoAffido;
	
	public CsPaiAffidoStatoDTO(){
		super();
	}
	
	public CsPaiAffidoStatoDTO(Integer codice, String descrizione){
		dataDa = new Date();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPaiAffidoDTO getAffido() {
		return affido;
	}

	public void setAffido(CsPaiAffidoDTO affido) {
		this.affido = affido;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Long getPaiAffidoId() {
		return paiAffidoId;
	}

	public void setPaiAffidoId(Long paiAffidoId) {
		this.paiAffidoId = paiAffidoId;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
    //SISO-993
	public Date getDataStatoAffido() {
		return dataStatoAffido;
	}

	public void setDataStatoAffido(Date dataStatoAffido) {
		this.dataStatoAffido = dataStatoAffido;
	}

	
}
