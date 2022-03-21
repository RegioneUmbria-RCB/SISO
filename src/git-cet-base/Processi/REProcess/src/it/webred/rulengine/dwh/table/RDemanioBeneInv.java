package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;


public class RDemanioBeneInv extends TabellaDwhMultiProv 
{

	private BigDecimal codInventario;
	private String codTipo;
	private String desTipo;
	private BigDecimal codTipoProprieta;
	private String desTipoProprieta;
	private BigDecimal codCatInventariale;
	private String desCatInventariale;
	private String codCategoria;
	private String desCategoria;
	private String codSottoCategoria;
	private String desSottoCategoria;
	private String mefTipologia;
	private String mefUtilizzo;
	private String mefFinalita;
	private String mefNaturaGiuridica;
	private String tipo;
	private String codCartella;
	private String desCartella;

	
	@Override
	public String getValueForCtrHash()
	{
		String hash = this.getIdOrig().getValore() + this.getProvenienza()  +
				mefNaturaGiuridica + mefFinalita + mefUtilizzo + mefTipologia+ 
				codSottoCategoria + codCategoria + codCatInventariale + codInventario +  codTipo +  desTipo +  tipo +
				codTipoProprieta +  desTipoProprieta ;
		return hash;
	}


	public BigDecimal getCodInventario() {
		return codInventario;
	}


	public void setCodInventario(BigDecimal codInventario) {
		this.codInventario = codInventario;
	}


	public String getCodTipo() {
		return codTipo;
	}


	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}


	public String getDesTipo() {
		return desTipo;
	}


	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}


	public BigDecimal getCodTipoProprieta() {
		return codTipoProprieta;
	}


	public void setCodTipoProprieta(BigDecimal codTipoProprieta) {
		this.codTipoProprieta = codTipoProprieta;
	}


	public String getDesTipoProprieta() {
		return desTipoProprieta;
	}


	public void setDesTipoProprieta(String desTipoProprieta) {
		this.desTipoProprieta = desTipoProprieta;
	}


	public BigDecimal getCodCatInventariale() {
		return codCatInventariale;
	}


	public void setCodCatInventariale(BigDecimal codCatInventariale) {
		this.codCatInventariale = codCatInventariale;
	}


	public String getDesCatInventariale() {
		return desCatInventariale;
	}


	public void setDesCatInventariale(String desCatInventariale) {
		this.desCatInventariale = desCatInventariale;
	}


	public String getCodCategoria() {
		return codCategoria;
	}


	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}


	public String getDesCategoria() {
		return desCategoria;
	}


	public void setDesCategoria(String desCategoria) {
		this.desCategoria = desCategoria;
	}


	public String getCodSottoCategoria() {
		return codSottoCategoria;
	}


	public void setCodSottoCategoria(String codSottoCategoria) {
		this.codSottoCategoria = codSottoCategoria;
	}


	public String getDesSottoCategoria() {
		return desSottoCategoria;
	}


	public void setDesSottoCategoria(String desSottoCategoria) {
		this.desSottoCategoria = desSottoCategoria;
	}


	public String getMefTipologia() {
		return mefTipologia;
	}


	public void setMefTipologia(String mefTipologia) {
		this.mefTipologia = mefTipologia;
	}


	public String getMefUtilizzo() {
		return mefUtilizzo;
	}


	public void setMefUtilizzo(String mefUtilizzo) {
		this.mefUtilizzo = mefUtilizzo;
	}


	public String getMefFinalita() {
		return mefFinalita;
	}


	public void setMefFinalita(String mefFinalita) {
		this.mefFinalita = mefFinalita;
	}


	public String getMefNaturaGiuridica() {
		return mefNaturaGiuridica;
	}


	public void setMefNaturaGiuridica(String mefNaturaGiuridica) {
		this.mefNaturaGiuridica = mefNaturaGiuridica;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getCodCartella() {
		return codCartella;
	}


	public void setCodCartella(String codCartella) {
		this.codCartella = codCartella;
	}


	public String getDesCartella() {
		return desCartella;
	}


	public void setDesCartella(String desCartella) {
		this.desCartella = desCartella;
	}

}
