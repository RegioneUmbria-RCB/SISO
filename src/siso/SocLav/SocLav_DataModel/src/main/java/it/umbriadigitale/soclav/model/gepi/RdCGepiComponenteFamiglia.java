package it.umbriadigitale.soclav.model.gepi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RDC_GEPI_COMPONENTE_FAMIGLIA")
public class RdCGepiComponenteFamiglia implements Serializable {
	
	@EmbeddedId
	private RdCGepiBeneficiarioPK id;
    
	@Column(name="NOME_COMPLETO")
	private String nomeCompleto;
	
	@Column(name="RELAZIONE_PARENTALE")
	private String relazioneParentale;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	private String genere;
	
	@Column(name="CONDIZIONE_OCCUPAZIONALE")
	private String condOccupazionale;
	
	@Column(name="DID_FIRMATA")
	private String didFirmata;
	
	@Column(name="STATO_PATTO_LAVORO")
	private String statoPattoLavoro;
	
	private String disabilita;
	
	@Column(name="STATO_BENEFICIO")
	private String statoBeneficio;
	
	private String motivazione;
	private String nazionalita;
	
	@Column(name="COD_COMUNE_NAS")
	private String luogoNascitaCod;
	
	@Column(name="COMUNE_NAS")
	private String luogoNascitaDes;
	
	private String indirizzo;
	private Boolean convivente;
	
	@Column(name="TITOLO_STUDIO")
	private String titoloStudio;
	
	@Column(name="QUALIFICA_PROFESSIONALE")
	private String qualificaProfessionale;
	
	@Column(name="FREQUENZA_CORSI")
	private String frequenzaCorsi;
	
	@Column(name="PRESTAZIONI_EROG_INPS")
	private String prestazioniErogInps;
	
	@Column(name="TITOLO_SOGIORNO")
	private String titoloSoggiorno;
	
	private String naspi;
	
	@Column(name="PERIODO_DISOCCUPAZIONE")
	private String periodoDisoccupazione;
	
	private String studente;
	private String telefono;
	private String cellulare;
	private String email;
	
	@ManyToOne 
	@JoinColumn(name="FK_RDC_DOMANDA", insertable=false, updatable=false)
	private RdCGepiDomanda domanda;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="DT_MOD")
	private Date dtMod;
	
	public RdCGepiBeneficiarioPK getId() {
		return id;
	}
	public void setId(RdCGepiBeneficiarioPK id) {
		this.id = id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getRelazioneParentale() {
		return relazioneParentale;
	}
	public void setRelazioneParentale(String relazioneParentale) {
		this.relazioneParentale = relazioneParentale;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public String getCondOccupazionale() {
		return condOccupazionale;
	}
	public void setCondOccupazionale(String condOccupazionale) {
		this.condOccupazionale = condOccupazionale;
	}
	public String getDidFirmata() {
		return didFirmata;
	}
	public void setDidFirmata(String didFirmata) {
		this.didFirmata = didFirmata;
	}
	public String getStatoPattoLavoro() {
		return statoPattoLavoro;
	}
	public void setStatoPattoLavoro(String statoPattoLavoro) {
		this.statoPattoLavoro = statoPattoLavoro;
	}
	public String getDisabilita() {
		return disabilita;
	}
	public void setDisabilita(String disabilita) {
		this.disabilita = disabilita;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public String getNazionalita() {
		return nazionalita;
	}
	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}
	public String getLuogoNascitaCod() {
		return luogoNascitaCod;
	}
	public void setLuogoNascitaCod(String luogoNascitaCod) {
		this.luogoNascitaCod = luogoNascitaCod;
	}
	public String getLuogoNascitaDes() {
		return luogoNascitaDes;
	}
	public void setLuogoNascitaDes(String luogoNascitaDes) {
		this.luogoNascitaDes = luogoNascitaDes;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Boolean getConvivente() {
		return convivente;
	}
	public void setConvivente(Boolean convivente) {
		this.convivente = convivente;
	}
	public String getTitoloStudio() {
		return titoloStudio;
	}
	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}
	public String getQualificaProfessionale() {
		return qualificaProfessionale;
	}
	public void setQualificaProfessionale(String qualificaProfessionale) {
		this.qualificaProfessionale = qualificaProfessionale;
	}
	public String getFrequenzaCorsi() {
		return frequenzaCorsi;
	}
	public void setFrequenzaCorsi(String frequenzaCorsi) {
		this.frequenzaCorsi = frequenzaCorsi;
	}
	public String getPrestazioniErogInps() {
		return prestazioniErogInps;
	}
	public void setPrestazioniErogInps(String prestazioniErogInps) {
		this.prestazioniErogInps = prestazioniErogInps;
	}
	public String getTitoloSoggiorno() {
		return titoloSoggiorno;
	}
	public void setTitoloSoggiorno(String titoloSoggiorno) {
		this.titoloSoggiorno = titoloSoggiorno;
	}
	public String getNaspi() {
		return naspi;
	}
	public void setNaspi(String naspi) {
		this.naspi = naspi;
	}
	public String getPeriodoDisoccupazione() {
		return periodoDisoccupazione;
	}
	public void setPeriodoDisoccupazione(String periodoDisoccupazione) {
		this.periodoDisoccupazione = periodoDisoccupazione;
	}
	public String getStudente() {
		return studente;
	}
	public void setStudente(String studente) {
		this.studente = studente;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatoBeneficio() {
		return statoBeneficio;
	}
	public void setStatoBeneficio(String statoBeneficio) {
		this.statoBeneficio = statoBeneficio;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public Date getDtMod() {
		return dtMod;
	}
	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}
	
}
