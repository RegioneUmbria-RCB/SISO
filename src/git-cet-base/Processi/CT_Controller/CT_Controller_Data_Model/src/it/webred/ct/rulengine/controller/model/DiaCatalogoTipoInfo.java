package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="DIA_CATALOGO_TIPOINFO")
public class DiaCatalogoTipoInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id	
	private long id;
	
	//bi-directional many-to-one association to DiaTestata
    @ManyToOne
	@JoinColumn(name="IDCATALOGODIA")
	private DiaCatalogo diaCatalogo;
    	
	@Column(name="DES_PROPERTIES_FIND")
	private String desPropertiesFind;

	@Column(name="DES_BEAN_CLASSNAME")
	private String desBeanClassName;
	
	@Column(name="DES_BEAN_PROPERTIES")
	private String desBeanProperties;
	
	private String descrizione;
	
	@Column(name="FK_AM_FONTE")
	private long fkAmFonte;
		
	private String informazione;
	
	public DiaCatalogoTipoInfo() {}

	public long getFkAmFonte() {
		return fkAmFonte;
	}

	public String getInformazione() {
		return informazione;
	}

	public void setFkAmFonte(long fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}

	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}

	public DiaCatalogo getDiaCatalogo() {
		return diaCatalogo;
	}

	public void setDiaCatalogo(DiaCatalogo diaCatalogo) {
		this.diaCatalogo = diaCatalogo;
	}

	public String getDesPropertiesFind() {
		return desPropertiesFind;
	}

	public void setDesPropertiesFind(String desPropertiesFind) {
		this.desPropertiesFind = desPropertiesFind;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesBeanClassName() {
		return desBeanClassName;
	}

	public void setDesBeanClassName(String desBeanClass) {
		this.desBeanClassName = desBeanClass;
	}

	public String getDesBeanProperties() {
		return desBeanProperties;
	}

	public void setDesBeanProperties(String desBeanProperties) {
		this.desBeanProperties = desBeanProperties;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
