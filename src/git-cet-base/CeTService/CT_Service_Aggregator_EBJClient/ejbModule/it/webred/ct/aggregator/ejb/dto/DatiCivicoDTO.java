package it.webred.ct.aggregator.ejb.dto;

import java.io.Serializable;

import it.webred.ct.data.access.basic.catasto.dto.ParticellaInfoDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class DatiCivicoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ViaDTO[] listaVieRicerca;
	
	private ParticellaInfoDTO[] infoParticelleCivico;
	
	private int countUnder18;
	
	private int count18_65;
	
	private int countOver65;
	
	private int countFamResidenti;
	
	private int countFamResidentiProprietarie;
	
	private int countLocazioni;
	
	private int countLicenzeCommercio;
	
	private SitLicenzeCommercioDTO[] listaLicenzeCommercio;
	
	private Double avgOMImq;


	public ParticellaInfoDTO[] getInfoParticelleCivico() {
		return infoParticelleCivico;
	}

	public void setInfoParticelleCivico(ParticellaInfoDTO[] infoParticelleCivico) {
		this.infoParticelleCivico = infoParticelleCivico;
	}

	public int getCountUnder18() {
		return countUnder18;
	}

	public void setCountUnder18(int countUnder18) {
		this.countUnder18 = countUnder18;
	}

	public int getCount18_65() {
		return count18_65;
	}

	public void setCount18_65(int count18_65) {
		this.count18_65 = count18_65;
	}

	public int getCountOver65() {
		return countOver65;
	}

	public void setCountOver65(int countOver65) {
		this.countOver65 = countOver65;
	}

	public int getCountFamResidenti() {
		return countFamResidenti;
	}

	public void setCountFamResidenti(int countFamResidenti) {
		this.countFamResidenti = countFamResidenti;
	}

	public int getCountFamResidentiProprietarie() {
		return countFamResidentiProprietarie;
	}

	public void setCountFamResidentiProprietarie(int countFamResidentiProprietarie) {
		this.countFamResidentiProprietarie = countFamResidentiProprietarie;
	}

	public int getCountLocazioni() {
		return countLocazioni;
	}

	public void setCountLocazioni(int countLocazioni) {
		this.countLocazioni = countLocazioni;
	}

	public int getCountLicenzeCommercio() {
		return countLicenzeCommercio;
	}

	public void setCountLicenzeCommercio(int countLicenzeCommercio) {
		this.countLicenzeCommercio = countLicenzeCommercio;
	}

	public Double getAvgOMImq() {
		return avgOMImq;
	}

	public void setAvgOMImq(Double avgOMImq) {
		this.avgOMImq = avgOMImq;
	}

	public void setListaLicenzeCommercio(SitLicenzeCommercioDTO[] listaLicenzeCommercio) {
		this.listaLicenzeCommercio = listaLicenzeCommercio;
	}

	public SitLicenzeCommercioDTO[] getListaLicenzeCommercio() {
		return listaLicenzeCommercio;
	}

	public void setListaVieRicerca(ViaDTO[] listaVieRicerca) {
		this.listaVieRicerca = listaVieRicerca;
	}

	public ViaDTO[] getListaVieRicerca() {
		return listaVieRicerca;
	}
}
