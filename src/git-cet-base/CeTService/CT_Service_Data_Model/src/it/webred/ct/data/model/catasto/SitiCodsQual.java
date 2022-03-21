package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITICODS_QUAL database table.
 * 
 */
@Entity
@Table(name="SITICODS_QUAL")
public class SitiCodsQual implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="CODI_QUAL")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigDecimal codiQual;

	@Column(name="DESC_QUAL")
	private String descQual;
	
	@Column(name="COD_ELEG")
	private Long codEleg;
	
	@Column(name="COD_UTIL")
	private Long codUtil;
	
	@Column(name="COD_VARI")
	private Long codVari;
	
	@Column(name="COD_EDIF")
	private Long codEdif;
	
    public SitiCodsQual() {
    }



	public String getDescQual() {
		return descQual;
	}



	public void setDescQual(String descQual) {
		this.descQual = descQual;
	}



	public BigDecimal getCodiQual() {
		return codiQual;
	}



	public void setCodiQual(BigDecimal codiQual) {
		this.codiQual = codiQual;
	}



	public Long getCodEleg() {
		return codEleg;
	}



	public void setCodEleg(Long codEleg) {
		this.codEleg = codEleg;
	}



	public Long getCodUtil() {
		return codUtil;
	}



	public void setCodUtil(Long codUtil) {
		this.codUtil = codUtil;
	}



	public Long getCodVari() {
		return codVari;
	}



	public void setCodVari(Long codVari) {
		this.codVari = codVari;
	}



	public Long getCodEdif() {
		return codEdif;
	}



	public void setCodEdif(Long codEdif) {
		this.codEdif = codEdif;
	}

	

}