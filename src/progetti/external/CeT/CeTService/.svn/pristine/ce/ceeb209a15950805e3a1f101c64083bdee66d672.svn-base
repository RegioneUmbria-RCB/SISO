package it.webred.ct.data.model.concedilizie;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.locazioni.LocazioneAPK;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the MI_CONC_EDILIZIE_VISURE database table.
 * 
 */
@Entity
@Table(name="MI_CONC_EDILIZIE_VISURE")
@IndiceEntity(sorgente="35")
@IdClass(ConcEdilizieVisurePK.class)
public class ConcEdilizieVisure implements Serializable {
	private static final long serialVersionUID = 1L;

	@IndiceKey(pos="1")
	@Id
	private BigDecimal inxdoc;
	
	@IndiceKey(pos="2")
	@Id
	@Column(name="TIPO_ATTO")
	private String tipoAtto;
	
	@Column(name="DATA_DOC")
	private String dataDoc;
	
	@Column(name="NUM_PROT_GEN")
	private String numProtGen;
	
	@Column(name="ANNO_PROT_GEN")
	private String annoProtGen;

	@Column(name="NUM_PROT_SETT")
	private String numProtSett;

	@Column(name="NUMERO_ATTO")
	private String numeroAtto;
	
	private String altravia;

	private String altriciv;

	private BigDecimal civico;

	@Column(name="CIVICO_SUB")
	private String civicoSub;

	private String destinazione;

	private String inxvia;

	@Column(name="NOME_INTESTATARIO")
	private String nomeIntestatario;

	@Column(name="NOME_VIA")
	private String nomeVia;

	private BigDecimal privata;

	private String riparto;

	private String tipologia;

	private String tpv;

	@Column(name="VINCOLO_AMBIENTALE")
	private String vincoloAmbientale;

	public ConcEdilizieVisure() {
	}


	public String getAltravia() {
		return altravia;
	}

	public void setAltravia(String altravia) {
		this.altravia = altravia;
	}

	public String getAltriciv() {
		return altriciv;
	}

	public void setAltriciv(String altriciv) {
		this.altriciv = altriciv;
	}

	public BigDecimal getCivico() {
		return civico;
	}

	public void setCivico(BigDecimal civico) {
		this.civico = civico;
	}

	public String getCivicoSub() {
		return civicoSub;
	}

	public void setCivicoSub(String civicoSub) {
		this.civicoSub = civicoSub;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public String getInxvia() {
		return inxvia;
	}

	public void setInxvia(String inxvia) {
		this.inxvia = inxvia;
	}

	public String getNomeIntestatario() {
		return nomeIntestatario;
	}

	public void setNomeIntestatario(String nomeIntestatario) {
		this.nomeIntestatario = nomeIntestatario;
	}

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}

	public BigDecimal getPrivata() {
		return privata;
	}

	public void setPrivata(BigDecimal privata) {
		this.privata = privata;
	}

	public String getRiparto() {
		return riparto;
	}

	public void setRiparto(String riparto) {
		this.riparto = riparto;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTpv() {
		return tpv;
	}

	public void setTpv(String tpv) {
		this.tpv = tpv;
	}

	public String getVincoloAmbientale() {
		return vincoloAmbientale;
	}

	public void setVincoloAmbientale(String vincoloAmbientale) {
		this.vincoloAmbientale = vincoloAmbientale;
	}
	
	public String getAnnoProtGen() {
		return annoProtGen;
	}

	public void setAnnoProtGen(String annoProtGen) {
		this.annoProtGen = annoProtGen;
	}

	public String getDataDoc() {
		return dataDoc;
	}

	public void setDataDoc(String dataDoc) {
		this.dataDoc = dataDoc;
	}

	public BigDecimal getInxdoc() {
		return inxdoc;
	}

	public void setInxdoc(BigDecimal inxdoc) {
		this.inxdoc = inxdoc;
	}

	public String getNumProtGen() {
		return numProtGen;
	}

	public void setNumProtGen(String numProtGen) {
		this.numProtGen = numProtGen;
	}

	public String getNumProtSett() {
		return numProtSett;
	}

	public void setNumProtSett(String numProtSett) {
		this.numProtSett = numProtSett;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	
	public String getStringId(){
		 
		return //this.numProtGen!=null ? this.numProtGen : "" +"|"+
		       //this.annoProtGen!=null ? this.annoProtGen : "" +"|"+
			   //this.numProtSett!=null ? this.numProtSett : "" +"|"+
			   this.inxdoc.toString() +"|"+ (this.tipoAtto!=null ? this.tipoAtto : "") ;
			   //this.numeroAtto!=null ? this.numeroAtto : "" +"|"+
			   //this.dataDoc!=null ? this.dataDoc : "" +"|"+
			   
		
	}

}