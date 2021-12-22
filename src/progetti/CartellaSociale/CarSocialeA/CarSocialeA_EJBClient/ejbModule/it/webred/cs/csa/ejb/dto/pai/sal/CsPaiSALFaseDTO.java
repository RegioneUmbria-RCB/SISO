package it.webred.cs.csa.ejb.dto.pai.sal;

import java.io.Serializable;
import java.util.Date;


public class CsPaiSALFaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private CsPaiSalDTO sal;
	
	private Integer codice;

	private Date dataDa;

	private Date dataA;

	private Long paiSalId;
	
	private String descrizione;
	
	private Date dataFaseSal;
	
	public CsPaiSALFaseDTO(){
		super();
	}

	public CsPaiSALFaseDTO(Integer codice, String descrizione){
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

	public CsPaiSalDTO getSal() {
		return sal;
	}

	public void setSal(CsPaiSalDTO sal) {
		this.sal = sal;
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

	public Long getPaiSalId() {
		return paiSalId;
	}

	public void setPaiSalId(Long paiSalId) {
		this.paiSalId = paiSalId;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataFaseSal() {
		return dataFaseSal;
	}

	public void setDataFaseSal(Date dataFaseSal) {
		this.dataFaseSal = dataFaseSal;
	}
	
}

