package it.webred.cs.data.model.affido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="CS_PAI_AFFIDO_FAMIGLIA_AFF")
public class CsPaiAffidoFamigliaAffidataria {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_AFFIDO_FAM_AFF_ID_GENERATOR", sequenceName="SQ_PAI_AFFIDO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_AFFIDO_FAM_AFF_ID_GENERATOR")
	private Long id;
	
	@Column(name = "CARATTERISTICHE")
	private String caratteristiche;
	
	@Column(name = "CODICE_BANCA_DATI_FAM")
	private String codiceBancaDatiFamiglie;
	
	@Column(name = "PADRE_ID")
	private Long csAComponenteIdPadre;
	
	@Column(name = "MADRE_ID")
	private Long csAComponenteIdMadre;
	
	@Column(name = "CODICE_TIPO_FAMIGLIA")
	private String codiceTipoFamiglia;
	
	@Column(name = "CODICE_IDONEITA")
	private String codiceIdoneita;
	
	@Column(name = "FUORI_REGIONE")
	private Boolean fuoriRegione;
	
	@Column(name="CONOSCIUTA_DAL_MINORE")
	private Boolean conosciutaDalMinore;
	
	@Column(name = "COGNOME_PADRE")
	private String baseCognomePadre;
	
	@Column(name = "NOME_PADRE")
	private String baseNomePadre;
	
	@Column(name = "CF_PADRE")
	private String baseCfPadre;
	
	@Column(name = "COGNOME_MADRE")
    private String baseCognomeMadre;
	
	@Column(name = "NOME_MADRE")
	private String baseNomeMadre;
	
	@Column(name = "CF_MADRE")
	private String baseCfMadre;
	
	@Column(name = "MOTIVAZIONI", length=2048)
	private String motivazioni;

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

	public String getBaseCognomePadre() {
		return baseCognomePadre;
	}

	public void setBaseCognomePadre(String baseCognomePadre) {
		this.baseCognomePadre = baseCognomePadre;
	}

	public String getBaseNomePadre() {
		return baseNomePadre;
	}

	public void setBaseNomePadre(String baseNomePadre) {
		this.baseNomePadre = baseNomePadre;
	}

	public String getBaseCfPadre() {
		return baseCfPadre;
	}

	public void setBaseCfPadre(String baseCfPadre) {
		this.baseCfPadre = baseCfPadre;
	}

	public String getBaseCognomeMadre() {
		return baseCognomeMadre;
	}

	public void setBaseCognomeMadre(String baseCognomeMadre) {
		this.baseCognomeMadre = baseCognomeMadre;
	}

	public String getBaseNomeMadre() {
		return baseNomeMadre;
	}

	public void setBaseNomeMadre(String baseNomeMadre) {
		this.baseNomeMadre = baseNomeMadre;
	}

	public String getBaseCfMadre() {
		return baseCfMadre;
	}

	public void setBaseCfMadre(String baseCfMadre) {
		this.baseCfMadre = baseCfMadre;
	}

	public String getMotivazioni() {
		return motivazioni;
	}

	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}

}
