package it.webred.ct.data.model.c336;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


/**
 * The persistent class for the S_C336_GRID_ATTRIB_CAT_A2 database table.
 * 
 */
@Entity
@Table(name="S_C336_GRID_ATTRIB_CAT_A2")
public class C336GridAttribCatA2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="VAL_ALT_RIDOTTA")
	private String valAltRidotta;

	@Column(name="VAL_CAR_ANDRONE")
	private BigDecimal valCarAndrone;

	@Column(name="VAL_NUM_UIU_PIANO")
	private BigDecimal valNumUiuPiano;

	@Column(name="VAL_SER_IGIENICO_ESTERNO")
	private String valSerIgienicoEsterno;

	@Column(name="VAL_SUP_MEDIA_UIU")
	private BigDecimal valSupMediaUiu;

	@Column(name="VAL_UIU_ACCESSO_BALLATOIO")
	private String valUiuAccessoBallatoio;

	@Column(name="VAL_UIU_SEMINTERRATE")
	private String valUiuSeminterrate;
	
	@Column(name="VAL_CORPI_ACCESSORI")
	private String valCorpiAccessori;
	
	@Transient
	private String valUiuAccessoBallatoioDecoded;
	@Transient
	private String valSerIgienicoEsternoDecoded;
	@Transient
	private String valUiuSeminterrateDecoded;
	@Transient
	private String valAltRidottaDecoded;
	@Transient
	private String valCorpiAccessoriDecoded;
	
	
	@Transient
	private String valCarAndroneDecoded;
	@Transient
	private String valNumUiuPianoDecoded;
	@Transient
	private String valSupMediaUiuDecoded;
	

    public C336GridAttribCatA2() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getValAltRidotta() {
		return this.valAltRidotta;
	}

	public void setValAltRidotta(String valAltRidotta) {
		this.valAltRidotta = valAltRidotta;
	}

	public BigDecimal getValCarAndrone() {
		return this.valCarAndrone;
	}

	public void setValCarAndrone(BigDecimal valCarAndrone) {
		this.valCarAndrone = valCarAndrone;
	}

	public BigDecimal getValNumUiuPiano() {
		return this.valNumUiuPiano;
	}

	public void setValNumUiuPiano(BigDecimal valNumUiuPiano) {
		this.valNumUiuPiano = valNumUiuPiano;
	}

	public String getValSerIgienicoEsterno() {
		return this.valSerIgienicoEsterno;
	}

	public void setValSerIgienicoEsterno(String valSerIgienicoEsterno) {
		this.valSerIgienicoEsterno = valSerIgienicoEsterno;
	}

	public BigDecimal getValSupMediaUiu() {
		return this.valSupMediaUiu;
	}

	public void setValSupMediaUiu(BigDecimal valSupMediaUiu) {
		this.valSupMediaUiu = valSupMediaUiu;
	}

	public String getValUiuAccessoBallatoio() {
		return this.valUiuAccessoBallatoio;
	}

	public void setValUiuAccessoBallatoio(String valUiuAccessoBallatoio) {
		this.valUiuAccessoBallatoio = valUiuAccessoBallatoio;
	}

	public String getValUiuSeminterrate() {
		return this.valUiuSeminterrate;
	}

	public void setValUiuSeminterrate(String valUiuSeminterrate) {
		this.valUiuSeminterrate = valUiuSeminterrate;
	}

	public void setValCorpiAccessori(String valCorpiAccessori) {
		this.valCorpiAccessori = valCorpiAccessori;
	}

	public String getValCorpiAccessori() {
		return valCorpiAccessori;
	}

	public String getValCarAndroneDecoded() {
		valCarAndroneDecoded = "";
		if(valCarAndrone!=null){
			if(new BigDecimal(0).compareTo(valCarAndrone)==0)
				valCarAndroneDecoded = "angusto";
			else if(new BigDecimal(2).compareTo(valCarAndrone)==0)
				valCarAndroneDecoded = "normale";
			else if(new BigDecimal(3).compareTo(valCarAndrone)==0)
				valCarAndroneDecoded = "ampio";
		}
		return valCarAndroneDecoded;
	}

	public String getValNumUiuPianoDecoded() {
		valNumUiuPianoDecoded = "";
		if(valNumUiuPiano!=null){
			if(new BigDecimal(0).compareTo(valNumUiuPiano)==0)
				valNumUiuPianoDecoded = "4 o più";
			else if(new BigDecimal(2).compareTo(valNumUiuPiano)==0)
				valNumUiuPianoDecoded = "3";
			else if(new BigDecimal(3).compareTo(valNumUiuPiano)==0)
				valNumUiuPianoDecoded = "fino a 2";
		}
		
		return valNumUiuPianoDecoded;
	}

	public String getValSupMediaUiuDecoded() {
		valSupMediaUiuDecoded = "";
		if(valSupMediaUiu!=null){
			if(new BigDecimal(0).compareTo(valSupMediaUiu)==0)
				valSupMediaUiuDecoded = "fino a 50-65 mq";
			else if(new BigDecimal(1).compareTo(valSupMediaUiu)==0)
				valSupMediaUiuDecoded = "intermedia";
			else if(new BigDecimal(2).compareTo(valSupMediaUiu)==0)
				valSupMediaUiuDecoded = "oltre i 100-120 mq";
		}
		return valSupMediaUiuDecoded;
	}
	
	private String decodeSiNo(String val){
		if(val!=null){
			if(val.equals("N"))
				return "No";
			else
				return "Si";
		}else return "";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValUiuAccessoBallatoioDecoded() {
		return decodeSiNo(valUiuAccessoBallatoio);
	}

	public String getValSerIgienicoEsternoDecoded() {
		return decodeSiNo(valSerIgienicoEsterno);
	}

	public String getValUiuSeminterrateDecoded() {
		return decodeSiNo(valUiuSeminterrate);
	}

	public String getValAltRidottaDecoded() {
		return decodeSiNo(valAltRidotta);
	}

	public String getValCorpiAccessoriDecoded() {
		return decodeSiNo(valCorpiAccessori);
	}

	
	
}