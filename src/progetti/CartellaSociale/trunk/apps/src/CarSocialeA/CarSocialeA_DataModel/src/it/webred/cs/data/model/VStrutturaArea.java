package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.webred.cs.data.model.pti.RichiestaDisponibilitaPaiPti;

@Entity
@Table(name = "V_STRUTTURA_AREA")
@NamedQueries(value={
		@NamedQuery(name = "VStrutturaArea.findAll", query = "SELECT vs FROM VStrutturaArea vs"),
		@NamedQuery(name = "VStrutturaArea.findStruttura", query = "SELECT distinct s.idStruttura, s.nomeStruttura FROM  VStrutturaArea s"),
	    @NamedQuery(name = "VStrutturaArea.findArea", query = "SELECT s.id, s.descrizione FROM  VStrutturaArea s where idStruttura = :idStruttura"),
	    @NamedQuery(name = "VStrutturaArea.findAllArea", query = "SELECT DISTINCT a.idArea, a.descrizione FROM  VStrutturaArea a WHERE idArea IS NOT null ")
	})
public class VStrutturaArea  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STRUTTURA")
	private long idStruttura;

	private String abilitato;

	private String indirizzo;

	@Column(name="NOME_STRUTTURA")
	private String nomeStruttura;
	
	@Column(name="FLAG_ATTREZZATO")
	private String flagAttrezzato;

	@Column(name="TIPO_STRUTTURA")
	private long tipoStruttura;
	
	@Column(name="TIPOLOGIA_SERVIZIO")
	private String tipologiaServizio;
	
	@Column(name="GESTORE_SERVIZIO")
	private String gestoreServizio;
	
	@Column(name="TELEFONO_FISSO_GESTORE")
	private String telefonoFissoGestore;
	
	@Column(name="MAIL_GESTORE")
	private String mailGestore;
	
	@Column(name="PEC_GESTORE")
	private String pecGestore;
	
	@Column(name="COORDINATORE")
	private String coordinatore;
	
	@Column(name="TELEFONO_FISSO_COORDINATORE")
	private String telefonoFissoCoordinatore;
	
	@Column(name="MAIL_COORDINATORE")
	private String mailCoordinatore;
	
	@Column(name="CODICE_COMUNE")
	private String codComune;
	
	@Column(name="DESCRIZIONE_COMUNE")
	private String descrizioneComune;

	@Column(name="ID_AREA")
	private long idArea;

	@Column(name = "COD_BELFIORE_COMUNE")
	private String codBelfioreComune;
	
	private String descrizione;
	
	@Column(name = "MACRO_TIPOLOGIA_SERVIZIO")
	private Long idMacroTipologiaServizio;
	
	@Column(name = "ID_TIPOLOGIA_SERVIZIO")
	private Long idTipologiaServizio;

	@OneToMany(mappedBy = "strutturaArea", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RichiestaDisponibilitaPaiPti> richiesteDisponibilita;
	
	public VStrutturaArea() {
	}

	
	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato; 
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNomeStruttura() {
		return this.nomeStruttura;
	}

	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}

	public long getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public long getIdStruttura() {
		return idStruttura;
	}


	public void setIdStruttura(long idStruttura) {
		this.idStruttura = idStruttura;
	}


	public long getIdArea() {
		return idArea;
	}


	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getFlagAttrezzato() {
		return flagAttrezzato;
	}


	public void setFlagAttrezzato(String flagAttrezzato) {
		this.flagAttrezzato = flagAttrezzato;
	}


	public String getTipologiaServizio() {
		return tipologiaServizio;
	}


	public void setTipologiaServizio(String tipologiaServizio) {
		this.tipologiaServizio = tipologiaServizio;
	}

	public String getGestoreServizio() {
		return gestoreServizio;
	}


	public void setGestoreServizio(String gestoreServizio) {
		this.gestoreServizio = gestoreServizio;
	}


	public String getTelefonoFissoGestore() {
		return telefonoFissoGestore;
	}


	public void setTelefonoFissoGestore(String telefonoFissoGestore) {
		this.telefonoFissoGestore = telefonoFissoGestore;
	}


	public String getMailGestore() {
		return mailGestore;
	}


	public void setMailGestore(String mailGestore) {
		this.mailGestore = mailGestore;
	}


	public String getPecGestore() {
		return pecGestore;
	}


	public void setPecGestore(String pecGestore) {
		this.pecGestore = pecGestore;
	}


	public String getCoordinatore() {
		return coordinatore;
	}


	public void setCoordinatore(String coordinatore) {
		this.coordinatore = coordinatore;
	}


	public String getTelefonoFissoCoordinatore() {
		return telefonoFissoCoordinatore;
	}


	public void setTelefonoFissoCoordinatore(String telefonoFissoCoordinatore) {
		this.telefonoFissoCoordinatore = telefonoFissoCoordinatore;
	}


	public String getMailCoordinatore() {
		return mailCoordinatore;
	}


	public void setMailCoordinatore(String mailCoordinatore) {
		this.mailCoordinatore = mailCoordinatore;
	}


	public String getCodComune() {
		return codComune;
	}


	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}


	public String getDescrizioneComune() {
		return descrizioneComune;
	}


	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}


	public List<RichiestaDisponibilitaPaiPti> getRichiesteDisponibilita() {
		return richiesteDisponibilita;
	}


	public void setRichiesteDisponibilita(List<RichiestaDisponibilitaPaiPti> richiesteDisponibilita) {
		this.richiesteDisponibilita = richiesteDisponibilita;
	}


	public String getCodBelfioreComune() {
		return codBelfioreComune;
	}


	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}


	public Long getIdMacroTipologiaServizio() {
		return idMacroTipologiaServizio;
	}


	public void setIdMacroTipologiaServizio(Long idMacroTipologiaServizio) {
		this.idMacroTipologiaServizio = idMacroTipologiaServizio;
	}


	public Long getIdTipologiaServizio() {
		return idTipologiaServizio;
	}


	public void setIdTipologiaServizio(Long idTipologiaServizio) {
		this.idTipologiaServizio = idTipologiaServizio;
	}
	
}
