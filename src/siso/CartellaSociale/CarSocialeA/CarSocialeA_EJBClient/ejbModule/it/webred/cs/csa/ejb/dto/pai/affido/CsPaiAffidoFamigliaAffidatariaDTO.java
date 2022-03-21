package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;


public class CsPaiAffidoFamigliaAffidatariaDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;
	
	private String caratteristiche;
	
	private String motivazioni;
	
	private String codiceBancaDatiFamiglie;
	
	private Long csAComponenteIdPadre;

	private Long csAComponenteIdMadre;
	
	private String codiceTipoFamiglia;
	
	private String codiceIdoneita;
	
	private Boolean fuoriRegione = false;
	
	private Boolean conosciutaDalMinore = false;
	
	private CsPaiAffidoSoggFamigliaDTO padre = new CsPaiAffidoSoggFamigliaDTO();
	
	private CsPaiAffidoSoggFamigliaDTO madre = new CsPaiAffidoSoggFamigliaDTO();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaratteristiche() {
		return caratteristiche;
	}

	public void setCaratteristiche(String caratteristiche) {
		this.caratteristiche = caratteristiche;
	}

	public String getMotivazioni() {
		return motivazioni;
	}

	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}

	public String getCodiceBancaDatiFamiglie() {
		return codiceBancaDatiFamiglie;
	}

	public void setCodiceBancaDatiFamiglie(String codiceBancaDatiFamiglie) {
		this.codiceBancaDatiFamiglie = codiceBancaDatiFamiglie;
	}

	public Long getCsAComponenteIdPadre() {
		return csAComponenteIdPadre;
	}

	public void setCsAComponenteIdPadre(Long csAComponenteIdPadre) {
		this.csAComponenteIdPadre = csAComponenteIdPadre;
	}

	public Long getCsAComponenteIdMadre() {
		return csAComponenteIdMadre;
	}

	public void setCsAComponenteIdMadre(Long csAComponenteIdMadre) {
		this.csAComponenteIdMadre = csAComponenteIdMadre;
	}

	public String getCodiceTipoFamiglia() {
		return codiceTipoFamiglia;
	}

	public void setCodiceTipoFamiglia(String codiceTipoFamiglia) {
		this.codiceTipoFamiglia = codiceTipoFamiglia;
	}

	public String getCodiceIdoneita() {
		return codiceIdoneita;
	}

	public void setCodiceIdoneita(String codiceIdoneita) {
		this.codiceIdoneita = codiceIdoneita;
	}

	public Boolean getFuoriRegione() {
		return fuoriRegione;
	}

	public void setFuoriRegione(Boolean fuoriRegione) {
		this.fuoriRegione = fuoriRegione;
	}

	public Boolean getConosciutaDalMinore() {
		return conosciutaDalMinore;
	}

	public void setConosciutaDalMinore(Boolean conosciutaDalMinore) {
		this.conosciutaDalMinore = conosciutaDalMinore;
	}

	public CsPaiAffidoSoggFamigliaDTO getPadre() {
		return padre;
	}

	public void setPadre(CsPaiAffidoSoggFamigliaDTO padre) {
		this.padre = padre;
	}

	public CsPaiAffidoSoggFamigliaDTO getMadre() {
		return madre;
	}

	public void setMadre(CsPaiAffidoSoggFamigliaDTO madre) {
		this.madre = madre;
	}

}
