
package it.escsolution.escwebgis.docfa.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ControlloClassamentoConsistenzaDTO;
import it.webred.utils.GenericTuples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Docfa extends EscObject implements Serializable{
	
	String tecCognome;
	String tecNome;
	String protocollo ; 
	String dataVariazione ; 
	String causale ; 
	String soppressione ; 
	String variazione ; 
	String costituzione ; 
	String cognome ; 
	String nome ; 
	String denominazione ; 
	String codiceFiscale ; 
	String partitaIva ; 
	String operazione ; 
	String foglio ; 
	String particella ; 
	String subalterno ; 

	String dichiarante ; 
	String indirizzoDichiarante;
	String indirizzoUiu;
	String chiave;
	String fornitura;
	String derivSpe;

	String zona;
	String classe;
	String categoria;
	String consistenza;
	String superfice;
	String rendita;
	String tipo;
	String luogo;
	String superficeMetrici;
	String ambiente;
	String altezza;
	String identificativoImm;
	String dataRegistrazione;

	String numero;
	String dataProtocollo;
	ArrayList indPart = new ArrayList();
	String presenzaGraffati;
	
	ControlloClassamentoConsistenzaDTO conCls;
	
	public ControlloClassamentoConsistenzaDTO getConCls() {
		return conCls;
	}
	public void setConCls(ControlloClassamentoConsistenzaDTO conCls) {
		this.conCls = conCls;
	}
	RenditaDocfa rendDocfa;
	String flagNC;
	String codEnte;
	GenericTuples.T2<String,String> coord;
	String nrProg;

	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getNumero()
	{
		return numero;
	}
	public void setNumero(String numero)
	{
		this.numero = numero;
	}
	
	public String getDataRegistrazione()
	{
		return dataRegistrazione;
	}
	public void setDataRegistrazione(String dataRegistrazione)
	{
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	
	public String getFornitura()
	{
		return fornitura;
	}
	public void setFornitura(String fornitura)
	{
		this.fornitura = fornitura;
	}
	public String getCausale()
	{
		return causale;
	}
	public void setCausale(String causale)
	{
		this.causale = causale;
	}
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale)
	{
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome()
	{
		return cognome;
	}
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}
	public String getCostituzione()
	{
		return costituzione;
	}
	public void setCostituzione(String costituzione)
	{
		this.costituzione = costituzione;
	}
	public String getDataVariazione()
	{
		return dataVariazione;
	}
	public void setDataVariazione(String dataVariazione)
	{
		this.dataVariazione = dataVariazione;
	}
	public String getDenominazione()
	{
		return denominazione;
	}
	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}
	public String getDichiarante()
	{
		return dichiarante;
	}
	public void setDichiarante(String dichiarante)
	{
		this.dichiarante = dichiarante;
	}
	public String getFoglio()
	{
		return foglio;
	}
	public void setFoglio(String foglio)
	{
		this.foglio = foglio;
	}
	public String getIndirizzoDichiarante()
	{
		return indirizzoDichiarante;
	}
	public void setIndirizzoDichiarante(String indirizzoDichiarante)
	{
		this.indirizzoDichiarante = indirizzoDichiarante;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getOperazione()
	{
		return operazione;
	}
	public void setOperazione(String operazione)
	{
		this.operazione = operazione;
	}
	public String getParticella()
	{
		return particella;
	}
	public void setParticella(String particella)
	{
		this.particella = particella;
	}

	public String getPartitaIva()
	{
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva)
	{
		this.partitaIva = partitaIva;
	}
	public String getProtocollo()
	{
		return protocollo;
	}
	public void setProtocollo(String protocollo)
	{
		this.protocollo = protocollo;
	}
	public String getSoppressione()
	{
		return soppressione;
	}
	public String getChiave()
	{
		return protocollo+"|"+fornitura.substring(6,10)+fornitura.substring(3,5)+fornitura.substring(0,2);
	}

	public void setSoppressione(String soppressione)
	{
		this.soppressione = soppressione;
	}
	public String getSubalterno()
	{
		return subalterno;
	}
	public void setSubalterno(String subalterno)
	{
		this.subalterno = subalterno;
	}
	public String getVariazione()
	{
		return variazione;
	}
	public void setVariazione(String variazione)
	{
		this.variazione = variazione;
	}
	public String getDerivSpe()
	{
		return derivSpe;
	}
	public void setDerivSpe(String derivSpe)
	{
		this.derivSpe = derivSpe;
	}

	public String getCategoria()
	{
		return categoria;
	}
	public void setCategoria(String categoria)
	{
		this.categoria = categoria;
	}
	public String getClasse()
	{
		return classe;
	}
	public void setClasse(String classe)
	{
		this.classe = classe;
	}
	public String getConsistenza()
	{
		return consistenza;
	}
	public void setConsistenza(String consistenza)
	{
		this.consistenza = consistenza;
	}

	public String getRendita()
	{
		return rendita;
	}
	public void setRendita(String rendita)
	{
		this.rendita = rendita;
	}
	public String getSuperfice()
	{
		return superfice;
	}
	public void setSuperfice(String superfice)
	{
		this.superfice = superfice;
	}
	public String getZona()
	{
		return zona;
	}
	public void setZona(String zona)
	{
		this.zona = zona;
	}
	public String getLuogo()
	{
		return luogo;
	}
	public void setLuogo(String luogo)
	{
		this.luogo = luogo;
	}
	public String getAltezza()
	{
		return altezza;
	}
	public void setAltezza(String altezza)
	{
		this.altezza = altezza;
	}
	public String getAmbiente()
	{
		return ambiente;
	}
	public void setAmbiente(String ambiente)
	{
		this.ambiente = ambiente;
	}
	public String getIdentificativoImm()
	{
		return identificativoImm;
	}
	public void setIdentificativoImm(String identificativoImm)
	{
		this.identificativoImm = identificativoImm;
	}
	public String getSuperficeMetrici()
	{
		return superficeMetrici;
	}
	public void setSuperficeMetrici(String superficeMetrici)
	{
		this.superficeMetrici = superficeMetrici;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public ArrayList getIndPart() {
		return indPart;
	}
	public void setIndPart(ArrayList indPart) {
		this.indPart = indPart;
	}
	public String getPresenzaGraffati() {
		return presenzaGraffati;
	}
	public void setPresenzaGraffati(String presenzaGraffati) {
		this.presenzaGraffati = presenzaGraffati;
	}
	public String getIndirizzoUiu() {
		return indirizzoUiu;
	}
	public void setIndirizzoUiu(String indirizzoUiu) {
		this.indirizzoUiu = indirizzoUiu;
	}
	public RenditaDocfa getRendDocfa() {
		return rendDocfa;
	}
	public void setRendDocfa(RenditaDocfa rendDocfa) {
		this.rendDocfa = rendDocfa;
	}
	public String getFlagNC() {
		return flagNC;
	}
	public void setFlagNC(String flagNC) {
		this.flagNC = flagNC;
	}
	public String getTecCognome() {
		return tecCognome;
	}
	public void setTecCognome(String tecCognome) {
		this.tecCognome = tecCognome;
	}
	public String getTecNome() {
		return tecNome;
	}
	public void setTecNome(String tecNome) {
		this.tecNome = tecNome;
	}
	public GenericTuples.T2<String, String> getCoord() {
		return coord;
	}
	public void setCoord(GenericTuples.T2<String, String> coord) {
		this.coord = coord;
	}
	public String getNrProg() {
		return nrProg;
	}
	public void setNrProg(String nrProg) {
		this.nrProg = nrProg;
	}


}
