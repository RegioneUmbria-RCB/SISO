package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the S_C336_SK_CAR_GEN_FABBRICATO database table.
 * 
 */
@Entity
@Table(name="S_C336_SK_CAR_GEN_FABBRICATO")
public class C336SkCarGenFabbricato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="VAL_ALTEZZA_MEDIA")
	private Float valAltezzaMedia;

	@Column(name="VAL_ANDRONE")
	private String valAndrone;

	@Column(name="VAL_ASCENSORE")
	private String valAscensore;

	@Column(name="VAL_CAR_ARCHIT_DECORATIVE")
	private String valCarArchitDecorative;

	@Column(name="VAL_MANUT_GEN_E_RIFINITURE")
	private String valManutGenERifiniture;

	@Column(name="VAL_NUMERO_INGRESSI")
	private Long valNumeroIngressi;

	@Column(name="VAL_NUMERO_SCALE")
	private Long valNumeroScale;

	@Column(name="VAL_NUMERO_UIU_PER_PIANO")
	private String valNumeroUiuPerPiano;

	@Column(name="VAL_PIANI_FUORI_TERRA")
	private Long valPianiFuoriTerra;

	@Column(name="VAL_STRUTTURA_PORTANTE")
	private String valStrutturaPortante;

    public C336SkCarGenFabbricato() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getValAndrone() {
		return this.valAndrone;
	}

	public void setValAndrone(String valAndrone) {
		this.valAndrone = valAndrone;
	}

	public String getValAscensore() {
		return this.valAscensore;
	}

	public void setValAscensore(String valAscensore) {
		this.valAscensore = valAscensore;
	}

	public String getValCarArchitDecorative() {
		return this.valCarArchitDecorative;
	}

	public void setValCarArchitDecorative(String valCarArchitDecorative) {
		this.valCarArchitDecorative = valCarArchitDecorative;
	}

	public String getValManutGenERifiniture() {
		return this.valManutGenERifiniture;
	}

	public void setValManutGenERifiniture(String valManutGenERifiniture) {
		this.valManutGenERifiniture = valManutGenERifiniture;
	}

	public String getValNumeroUiuPerPiano() {
		return this.valNumeroUiuPerPiano;
	}

	public void setValNumeroUiuPerPiano(String valNumeroUiuPerPiano) {
		this.valNumeroUiuPerPiano = valNumeroUiuPerPiano;
	}

	
	public String getValStrutturaPortante() {
		return this.valStrutturaPortante;
	}

	public void setValStrutturaPortante(String valStrutturaPortante) {
		this.valStrutturaPortante = valStrutturaPortante;
	}

	public Float getValAltezzaMedia() {
		return valAltezzaMedia;
	}

	public void setValAltezzaMedia(Float valAltezzaMedia) {
		this.valAltezzaMedia = valAltezzaMedia;
	}

	public Long getValNumeroIngressi() {
		return valNumeroIngressi;
	}

	public void setValNumeroIngressi(Long valNumeroIngressi) {
		this.valNumeroIngressi = valNumeroIngressi;
	}

	public Long getValNumeroScale() {
		return valNumeroScale;
	}

	public void setValNumeroScale(Long valNumeroScale) {
		this.valNumeroScale = valNumeroScale;
	}

	public Long getValPianiFuoriTerra() {
		return valPianiFuoriTerra;
	}

	public void setValPianiFuoriTerra(Long valPianiFuoriTerra) {
		this.valPianiFuoriTerra = valPianiFuoriTerra;
	}

	
}