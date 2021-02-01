package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_DICHIARANTI database table.
 * 
 */
@Entity
@Table(name="DOCFA_DICHIARANTI")
public class DocfaDichiaranti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DIC_COGNOME")
	private String dicCognome;

	@Column(name="DIC_NOME")
	private String dicNome;

	@Column(name="DIC_RES_CAP")
	private String dicResCap;

	@Column(name="DIC_RES_COM")
	private String dicResCom;

	@Column(name="DIC_RES_INDIR")
	private String dicResIndir;

	@Column(name="DIC_RES_NUMCIV")
	private String dicResNumciv;

	@Column(name="DIC_RES_PROV")
	private String dicResProv;

	@Column(name="FLAG_DOC_TELEM")
	private BigDecimal flagDocTelem;

    @Temporal( TemporalType.DATE)
	private Date fornitura;

    @Id
	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;

	@Column(name="TEC_CODFISC")
	private String tecCodfisc;

	@Column(name="TEC_COGNOME")
	private String tecCognome;

	@Column(name="TEC_ISC_ALBO")
	private BigDecimal tecIscAlbo;

	@Column(name="TEC_ISC_NUMORD")
	private BigDecimal tecIscNumord;

	@Column(name="TEC_ISC_PROV")
	private String tecIscProv;

	@Column(name="TEC_NOME")
	private String tecNome;

    public DocfaDichiaranti() {
    }

	public String getDicCognome() {
		return this.dicCognome;
	}

	public void setDicCognome(String dicCognome) {
		this.dicCognome = dicCognome;
	}

	public String getDicNome() {
		return this.dicNome;
	}

	public void setDicNome(String dicNome) {
		this.dicNome = dicNome;
	}

	public String getDicResCap() {
		return this.dicResCap;
	}

	public void setDicResCap(String dicResCap) {
		this.dicResCap = dicResCap;
	}

	public String getDicResCom() {
		return this.dicResCom;
	}

	public void setDicResCom(String dicResCom) {
		this.dicResCom = dicResCom;
	}

	public String getDicResIndir() {
		return this.dicResIndir;
	}

	public void setDicResIndir(String dicResIndir) {
		this.dicResIndir = dicResIndir;
	}

	public String getDicResNumciv() {
		String s = this.dicResNumciv;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
				
		}
		return s;
	}

	public void setDicResNumciv(String dicResNumciv) {
		this.dicResNumciv = dicResNumciv;
	}

	public String getDicResProv() {
		return this.dicResProv;
	}

	public void setDicResProv(String dicResProv) {
		this.dicResProv = dicResProv;
	}

	public BigDecimal getFlagDocTelem() {
		return this.flagDocTelem;
	}

	public void setFlagDocTelem(BigDecimal flagDocTelem) {
		this.flagDocTelem = flagDocTelem;
	}

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getProtocolloReg() {
		return this.protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

	public String getTecCodfisc() {
		return this.tecCodfisc;
	}

	public void setTecCodfisc(String tecCodfisc) {
		this.tecCodfisc = tecCodfisc;
	}

	public String getTecCognome() {
		return this.tecCognome;
	}

	public void setTecCognome(String tecCognome) {
		this.tecCognome = tecCognome;
	}

	public BigDecimal getTecIscAlbo() {
		return this.tecIscAlbo;
	}

	public void setTecIscAlbo(BigDecimal tecIscAlbo) {
		this.tecIscAlbo = tecIscAlbo;
	}

	public BigDecimal getTecIscNumord() {
		return this.tecIscNumord;
	}

	public void setTecIscNumord(BigDecimal tecIscNumord) {
		this.tecIscNumord = tecIscNumord;
	}

	public String getTecIscProv() {
		return this.tecIscProv;
	}

	public void setTecIscProv(String tecIscProv) {
		this.tecIscProv = tecIscProv;
	}

	public String getTecNome() {
		return this.tecNome;
	}

	public void setTecNome(String tecNome) {
		this.tecNome = tecNome;
	}

}