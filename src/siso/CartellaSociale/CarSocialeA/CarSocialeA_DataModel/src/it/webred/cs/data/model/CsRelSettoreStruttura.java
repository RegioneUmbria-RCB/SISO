package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_REL_SETTORE_STRUTTURA")
@NamedQuery(name="CsRelSettoreStruttura.findAll", query="SELECT c FROM CsRelSettoreStruttura c")
public class CsRelSettoreStruttura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="ID_SETTORE")
	private Long idSettore;

	@Column(name="ID_STRUTTURA")
	private Long idStruttura; //id della struttura mappata in AR_RP_STRUTTURA e mappata a livello di AM con codice belfiore fittizio

	@Column(name="COD_BELFIORE_FITTIZIO")
	private String codBelfioreFittizio; //id della struttura mappata in AR_RP_STRUTTURA e mappata a livello di AM con codice belfiore fittizio
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public String getCodBelfioreFittizio() {
		return codBelfioreFittizio;
	}

	public void setCodBelfioreFittizio(String codBelfioreFittizio) {
		this.codBelfioreFittizio = codBelfioreFittizio;
	}


}