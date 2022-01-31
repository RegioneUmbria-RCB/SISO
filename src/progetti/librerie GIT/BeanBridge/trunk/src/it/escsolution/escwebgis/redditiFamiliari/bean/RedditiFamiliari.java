package it.escsolution.escwebgis.redditiFamiliari.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class RedditiFamiliari extends EscObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String idExtDFamiglia;
	private String idExtDPersona;
	private Date dataIniRif;
	private Date dataFinRif;
	private Date dtIniDatoPf;
	private Date dtFinDatoPf;
	private String codfisc;
	private String cognome;
	private String nome;
	private String sesso;
	private Date dataNascita;
	private Date dataMor;
	private Date dataInizioResidenza;
	private Date dataImm;
	private Date dataEmi;
	
	private LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<RedditiPersonaFamigliaAnno>>> redPersFamAnn;
	
	private boolean viewContributi;
	
	private final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	public String getChiave() { 
		return id == null ? codfisc : id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getDataIniRif() {
		return dataIniRif;
	}

	public void setDataIniRif(Date dataIniRif) {
		this.dataIniRif = dataIniRif;
	}
	
	public String getDataIniRifStr() {
		return dataIniRif == null ? "-" : SDF.format(dataIniRif);
	}

	public Date getDataFinRif() {
		return dataFinRif;
	}

	public void setDataFinRif(Date dataFinRif) {
		this.dataFinRif = dataFinRif;
	}
	
	public String getDataFinRifStr() {
		return dataFinRif == null ? "-" : SDF.format(dataFinRif);
	}

	public Date getDtIniDatoPf() {
		return dtIniDatoPf;
	}

	public void setDtIniDatoPf(Date dtIniDatoPf) {
		this.dtIniDatoPf = dtIniDatoPf;
	}
	
	public String getDtIniDatoPfStr() {
		return dtIniDatoPf == null ? "-" : SDF.format(dtIniDatoPf);
	}

	public Date getDtFinDatoPf() {
		return dtFinDatoPf;
	}

	public void setDtFinDatoPf(Date dtFinDatoPf) {
		this.dtFinDatoPf = dtFinDatoPf;
	}
	
	public String getDtFinDatoPfStr() {
		return dtFinDatoPf == null ? "-" : SDF.format(dtFinDatoPf);
	}

	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
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

	public Date getDataMor() {
		return dataMor;
	}

	public void setDataMor(Date dataMor) {
		this.dataMor = dataMor;
	}
	
	public String getDataMorStr() {
		return dataMor == null ? "-" : SDF.format(dataMor);
	}

	public Date getDataInizioResidenza() {
		return dataInizioResidenza;
	}

	public void setDataInizioResidenza(Date dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}
	
	public String getDataInizioResidenzaStr() {
		return dataInizioResidenza == null ? "-" : SDF.format(dataInizioResidenza);
	}

	public Date getDataImm() {
		return dataImm;
	}

	public void setDataImm(Date dataImm) {
		this.dataImm = dataImm;
	}
	
	public String getDataImmStr() {
		return dataImm == null ? "-" : SDF.format(dataImm);
	}

	public Date getDataEmi() {
		return dataEmi;
	}

	public void setDataEmi(Date dataEmi) {
		this.dataEmi = dataEmi;
	}
	
	public String getDataEmiStr() {
		return dataEmi == null ? "-" : SDF.format(dataEmi);
	}

	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<RedditiPersonaFamigliaAnno>>> getRedPersFamAnn() {
		return redPersFamAnn;
	}

	public void setRedPersFamAnn(LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<RedditiPersonaFamigliaAnno>>> redPersFamAnn) {
		this.redPersFamAnn = redPersFamAnn;
	}

	public boolean isViewContributi() {
		return viewContributi;
	}

	public void setViewContributi(boolean viewContributi) {
		this.viewContributi = viewContributi;
	}

}
