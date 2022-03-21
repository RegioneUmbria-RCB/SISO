package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;

public class SitBonBan extends TabellaDwhMultiProv
{
	private BigDecimal tipoRecord;
	private String tipologiaFornitura;
	private BigDecimal annoRiferimento;
	private String codiceCatastaleBeneficiario;
	private String codiceFiscaleBeneficiario;
	private String esitoValidazioneCf;
	private String abi;
	private String cab;
	private String numeroBonifico;
	private DataDwh dataDisposizione;
	private BigDecimal numeroSoggettiOrdinanti;
	private String codiceFiscaleOrdinante;
	private String codiceFiscaleAmministratore;
	private String valuta;
	private BigDecimal importoComplessivo;
	private String normativaRiferimento;
	private String fineRiga;

	
	@Override
	public String getValueForCtrHash(){
		return 	 tipoRecord +
				tipologiaFornitura  +
			 annoRiferimento+
			 codiceCatastaleBeneficiario + 
			codiceFiscaleBeneficiario +
			esitoValidazioneCf +
			abi +
			cab +
			numeroBonifico +
			dataDisposizione +
			numeroSoggettiOrdinanti +
			codiceFiscaleOrdinante +
			codiceFiscaleAmministratore +
			valuta +
			importoComplessivo +
			normativaRiferimento +
			fineRiga;
	}


	public BigDecimal getTipoRecord() {
		return tipoRecord;
	}


	public void setTipoRecord(BigDecimal tipoRecord) {
		this.tipoRecord = tipoRecord;
	}




	public String getTipologiaFornitura() {
		return tipologiaFornitura;
	}




	public void setTipologiaFornitura(String tipologiaFornitura) {
		this.tipologiaFornitura = tipologiaFornitura;
	}




	public BigDecimal getAnnoRiferimento() {
		return annoRiferimento;
	}




	public void setAnnoRiferimento(BigDecimal annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}




	public String getCodiceCatastaleBeneficiario() {
		return codiceCatastaleBeneficiario;
	}




	public void setCodiceCatastaleBeneficiario(String codiceCatastaleBeneficiario) {
		this.codiceCatastaleBeneficiario = codiceCatastaleBeneficiario;
	}




	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}




	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}




	public String getEsitoValidazioneCf() {
		return esitoValidazioneCf;
	}




	public void setEsitoValidazioneCf(String esitoValidazioneCf) {
		this.esitoValidazioneCf = esitoValidazioneCf;
	}




	public String getAbi() {
		return abi;
	}




	public void setAbi(String abi) {
		this.abi = abi;
	}




	public String getCab() {
		return cab;
	}




	public void setCab(String cab) {
		this.cab = cab;
	}




	public String getNumeroBonifico() {
		return numeroBonifico;
	}




	public void setNumeroBonifico(String numeroBonifico) {
		this.numeroBonifico = numeroBonifico;
	}




	public DataDwh getDataDisposizione() {
		return dataDisposizione;
	}




	public void setDataDisposizione(DataDwh dataDisposizione) {
		this.dataDisposizione = dataDisposizione;
	}




	public BigDecimal getNumeroSoggettiOrdinanti() {
		return numeroSoggettiOrdinanti;
	}




	public void setNumeroSoggettiOrdinanti(BigDecimal numeroSoggettiOrdinanti) {
		this.numeroSoggettiOrdinanti = numeroSoggettiOrdinanti;
	}




	public String getCodiceFiscaleOrdinante() {
		return codiceFiscaleOrdinante;
	}




	public void setCodiceFiscaleOrdinante(String codiceFiscaleOrdinante) {
		this.codiceFiscaleOrdinante = codiceFiscaleOrdinante;
	}




	public String getCodiceFiscaleAmministratore() {
		return codiceFiscaleAmministratore;
	}




	public void setCodiceFiscaleAmministratore(String codiceFiscaleAmministratore) {
		this.codiceFiscaleAmministratore = codiceFiscaleAmministratore;
	}




	public String getValuta() {
		return valuta;
	}




	public void setValuta(String valuta) {
		this.valuta = valuta;
	}




	public BigDecimal getImportoComplessivo() {
		return importoComplessivo;
	}




	public void setImportoComplessivo(BigDecimal importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}




	public String getNormativaRiferimento() {
		return normativaRiferimento;
	}




	public void setNormativaRiferimento(String normativaRiferimento) {
		this.normativaRiferimento = normativaRiferimento;
	}




	public String getFineRiga() {
		return fineRiga;
	}




	public void setFineRiga(String fineRiga) {
		this.fineRiga = fineRiga;
	}

 

	
}
