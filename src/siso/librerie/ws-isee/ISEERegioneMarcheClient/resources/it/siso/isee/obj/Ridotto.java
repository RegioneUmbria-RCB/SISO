package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ridotto implements Serializable {

	private String codiceFiscaleBeneficiario;
	private String corrente;
	private String dataPresentazioneModuloSostitutivo;
	private String protocolloDSUModuloSostitutivo;
	
	private List<ComponenteNucleoRistretto> nucleoRistretto;
	private ISEEBase iseeRistretto = null;
	
	private ComponenteSocioSanitarioResRistretto iseeSocioSanResRistretto;

	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}

	public String getCorrente() {
		return corrente;
	}

	public void setCorrente(String corrente) {
		this.corrente = corrente;
	}

	public String getDataPresentazioneModuloSostitutivo() {
		return dataPresentazioneModuloSostitutivo;
	}

	public void setDataPresentazioneModuloSostitutivo(
			String dataPresentazioneModuloSostitutivo) {
		this.dataPresentazioneModuloSostitutivo = dataPresentazioneModuloSostitutivo;
	}

	public String getProtocolloDSUModuloSostitutivo() {
		return protocolloDSUModuloSostitutivo;
	}

	public void setProtocolloDSUModuloSostitutivo(
			String protocolloDSUModuloSostitutivo) {
		this.protocolloDSUModuloSostitutivo = protocolloDSUModuloSostitutivo;
	}

	 

	public List<ComponenteNucleoRistretto> getNucleoRistretto() {
		return nucleoRistretto;
	}

	public void setNucleoRistretto(List<ComponenteNucleoRistretto> nucleoRistretto) {
		this.nucleoRistretto = nucleoRistretto;
	}

	public ISEEBase getIseeRistretto() {
		return iseeRistretto;
	}

	public void setIseeRistretto(ISEEBase iseeRistretto) {
		this.iseeRistretto = iseeRistretto;
	}

	public ComponenteSocioSanitarioResRistretto getIseeSocioSanResRistretto() {
		return iseeSocioSanResRistretto;
	}

	public void setIseeSocioSanResRistretto(
			ComponenteSocioSanitarioResRistretto iseeSocioSanResRistretto) {
		this.iseeSocioSanResRistretto = iseeSocioSanResRistretto;
	}
	
	
}
