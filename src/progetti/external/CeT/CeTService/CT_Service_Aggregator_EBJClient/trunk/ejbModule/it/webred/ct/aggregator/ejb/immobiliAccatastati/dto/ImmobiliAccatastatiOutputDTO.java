package it.webred.ct.aggregator.ejb.immobiliAccatastati.dto;

import org.apache.log4j.Logger;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ImmobiliAccatastatiOutputDTO extends CeTBaseObject{

	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	private static final long serialVersionUID = 1L;
	private String sezione;
    private String foglio;
    private String numero;
    private String subalterno;
    private String sub;
    private String partitaCatastale;
    private String codiceVia;
    private String descrizioneVia;
    private String civico;
    private String indirizzoCatastale;
    private String percentualePossesso;
    private String tipoTitolo;
    private String desTipoTitolo;
    private String categoria;
    private String classe;
    private String consistenza;
    private String rendita;
    private String supCat;
    private String tipoEvento;
    private String dataInizioUiu;
    private String dataFineUiu;
    private String dataInizioTit;
    private String dataFineTit;
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getPartitaCatastale() {
		return partitaCatastale;
	}
	public void setPartitaCatastale(String partitaCatastale) {
		this.partitaCatastale = partitaCatastale;
	}
	public String getCodiceVia() {
		return codiceVia;
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public String getDescrizioneVia() {
		return descrizioneVia;
	}
	public void setDescrizioneVia(String descrizioneVia) {
		this.descrizioneVia = descrizioneVia;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getIndirizzoCatastale() {
		return indirizzoCatastale;
	}
	public void setIndirizzoCatastale(String indirizzoCatastale) {
		this.indirizzoCatastale = indirizzoCatastale;
	}
	public String getPercentualePossesso() {
		return percentualePossesso;
	}
	public void setPercentualePossesso(String percentualePossesso) {
		this.percentualePossesso = percentualePossesso;
	}
	public String getTipoTitolo() {
		return tipoTitolo;
	}
	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}
	public String getDesTipoTitolo() {
		return desTipoTitolo;
	}
	public void setDesTipoTitolo(String desTipoTitolo) {
		this.desTipoTitolo = desTipoTitolo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getConsistenza() {
		return consistenza;
	}
	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}
	public String getRendita() {
		return rendita;
	}
	public void setRendita(String rendita) {
		this.rendita = rendita;
	}
	public String getSupCat() {
		return supCat;
	}
	public void setSupCat(String supCat) {
		this.supCat = supCat;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getDataInizioUiu() {
		return dataInizioUiu;
	}
	public void setDataInizioUiu(String dataInizioUiu) {
		this.dataInizioUiu = dataInizioUiu;
	}
	public String getDataFineUiu() {
		return dataFineUiu;
	}
	public void setDataFineUiu(String dataFineUiu) {
		this.dataFineUiu = dataFineUiu;
	}
	public String getDataInizioTit() {
		return dataInizioTit;
	}
	public void setDataInizioTit(String dataInizioTit) {
		this.dataInizioTit = dataInizioTit;
	}
	public String getDataFineTit() {
		return dataFineTit;
	}
	public void setDataFineTit(String dataFineTit) {
		this.dataFineTit = dataFineTit;
	}
	public void printRecord() {
		String s = sezione+"|"+foglio+"|"+numero+"|"+subalterno+"|"+sub+"|"+partitaCatastale+"|"+codiceVia+"|"+descrizioneVia+"|"+civico+"|"+indirizzoCatastale+"|"+percentualePossesso+"|"+tipoTitolo+"|"+desTipoTitolo+"|"+categoria+"|"+classe+"|"+consistenza+"|"+rendita+"|"+supCat+"|"+tipoEvento+"|"+dataInizioUiu+"|"+dataFineUiu+"|"+dataInizioTit+"|"+dataFineTit;
		logger.info(s);
	}
    
}
