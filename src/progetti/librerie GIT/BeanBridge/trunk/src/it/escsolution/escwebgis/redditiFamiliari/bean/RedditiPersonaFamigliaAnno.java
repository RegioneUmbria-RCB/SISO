package it.escsolution.escwebgis.redditiFamiliari.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RedditiPersonaFamigliaAnno extends EscObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idExtDFamiglia;
	private String idExtDPersona;
	private String codFisc;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String codParentela;
	private String rappParentela;
	private BigDecimal reddito;
	private BigDecimal contributi;
	private int numContributi;
	private int numImmCatasto;
	private BigDecimal percPossRendita;
	private int immDichComune;
	private int immDichFuoriComune;
	private Date dtIniRif;
	private Date dtFinRif;
	private String chiaveDettRedditi;
	private String chiaveDettCatasto;
	private String chiaveDettContributi;
	
	private final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	private final DecimalFormat DF = new DecimalFormat();
	{
		DF.setGroupingUsed(true);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		DF.setDecimalFormatSymbols(dfs);
		DF.setMinimumFractionDigits(2);
	}
	
	public String getIdExtDFamiglia() {
		return idExtDFamiglia;
	}
	
	public void setIdExtDFamiglia(String idExtDFamiglia) {
		this.idExtDFamiglia = idExtDFamiglia;
	}

	public String getIdExtDPersona() {
		return idExtDPersona;
	}

	public void setIdExtDPersona(String idExtDPersona) {
		this.idExtDPersona = idExtDPersona;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public String getDataNascitaStr() {
		return dataNascita == null ? "-" : SDF.format(dataNascita);
	}

	public String getCodParentela() {
		return codParentela;
	}

	public void setCodParentela(String codParentela) {
		this.codParentela = codParentela;
	}

	public String getRappParentela() {
		return rappParentela;
	}

	public void setRappParentela(String rappParentela) {
		this.rappParentela = rappParentela;
	}

	public BigDecimal getReddito() {
		return reddito;
	}

	public void setReddito(BigDecimal reddito) {
		this.reddito = reddito;
	}
	
	public String getRedditoStr() {
		return reddito == null || reddito.doubleValue() == 0D ? "-" : DF.format(reddito.doubleValue());
	}

	public BigDecimal getContributi() {
		return contributi;
	}

	public void setContributi(BigDecimal contributi) {
		this.contributi = contributi;
	}
	
	public String getContributiStr() {
		return contributi == null ? "0,00" : DF.format(contributi.doubleValue());
	}

	public int getNumContributi() {
		return numContributi;
	}

	public void setNumContributi(int numContributi) {
		this.numContributi = numContributi;
	}

	public int getNumImmCatasto() {
		return numImmCatasto;
	}

	public void setNumImmCatasto(int numImmCatasto) {
		this.numImmCatasto = numImmCatasto;
	}

	public BigDecimal getPercPossRendita() {
		return percPossRendita;
	}

	public void setPercPossRendita(BigDecimal percPossRendita) {
		this.percPossRendita = percPossRendita;
	}
	
	public String getPercPossRenditaStr() {
		return percPossRendita == null ? "0,00" : DF.format(percPossRendita.doubleValue());
	}

	public int getImmDichComune() {
		return immDichComune;
	}

	public void setImmDichComune(int immDichComune) {
		this.immDichComune = immDichComune;
	}

	public int getImmDichFuoriComune() {
		return immDichFuoriComune;
	}

	public void setImmDichFuoriComune(int immDichFuoriComune) {
		this.immDichFuoriComune = immDichFuoriComune;
	}

	public Date getDtIniRif() {
		return dtIniRif;
	}

	public void setDtIniRif(Date dtIniRif) {
		this.dtIniRif = dtIniRif;
	}
	
	public String getDtIniRifStr() {
		return dtIniRif == null ? "-" : SDF.format(dtIniRif);
	}

	public Date getDtFinRif() {
		return dtFinRif;
	}

	public void setDtFinRif(Date dtFinRif) {
		this.dtFinRif = dtFinRif;
	}
	
	public String getDtFinRifStr() {
		return dtFinRif == null ? "-" : SDF.format(dtFinRif);
	}

	public String getChiaveDettRedditi() {
		return chiaveDettRedditi;
	}

	public void setChiaveDettRedditi(String chiaveDettRedditi) {
		this.chiaveDettRedditi = chiaveDettRedditi;
	}

	public String getChiaveDettCatasto() {
		return chiaveDettCatasto;
	}

	public void setChiaveDettCatasto(String chiaveDettCatasto) {
		this.chiaveDettCatasto = chiaveDettCatasto;
	}

	public String getChiaveDettContributi() {
		return chiaveDettContributi;
	}

	public void setChiaveDettContributi(String chiaveDettContributi) {
		this.chiaveDettContributi = chiaveDettContributi;
	}

}
