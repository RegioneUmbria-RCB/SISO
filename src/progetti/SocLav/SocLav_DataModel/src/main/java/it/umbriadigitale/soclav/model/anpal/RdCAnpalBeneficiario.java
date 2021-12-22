package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="RDC_ANPAL_BENEFICIARIO")
public class RdCAnpalBeneficiario implements Serializable {

		@EmbeddedId
	    private RdCBeneficiarioPK id;
		
		@Column(name="CF_RICHIEDENTE")
		private String cfRichiedente;
		
		@Column(name="DATA_DOMANDA")
		private Date dataDomanda;
		
		@Column(name="DATA_DECORRENZA_BEN")
		private Date dataDecorrenzaBen;
		
		@Column(name="NOME")
		private String nome;
		
		@Column(name="COGNOME")
		private String cognome;
		
		private String sesso;
		
		@Column(name="DATA_NASCITA")
		private Date dataNascita;
		
		@Column(name="NASCITA_LUOGO_COD")
		private String nascitaLuogoCod;
		
		@Column(name="NASCITA_LUOGO_DES")
		private String nascitaLuogoDes;
		
		private String ruolo;
		
		private String telefono;
		private String email;
		
		@Column(name="CITTADINANZA_COD")
		private String cittadinanzaCod;
		
		@Column(name="RESIDENZA_COMUNE_COD")
		private String residenzaComuneCod;
		
		@Column(name="RESIDENZA_COMUNE_DES")
		private String residenzaComuneDes;
		
		@Column(name="RESIDENZA_INDIRIZZO")
		private String residenzaIndirizzo;
		
		@Column(name="RESIDENZA_CAP")
		private String residenzaCap;
		
		@Column(name="RESIDENZA_CPI_COD")
		private String residenzaCPICod;
		
		@Column(name="DOMICILIO_COMUNE_COD")
		private String domicilioComuneCod;
		
		@Column(name="DOMICILIO_COMUNE_DES")
		private String domicilioComuneDes;
		
		@Column(name="DOMICILIO_INDIRIZZO")
		private String domicilioIndirizzo;
		
		@Column(name="DOMICILIO_CAP")
		private String domicilioCap;
		
		@Column(name="STATO_COD")
		private String statoCod;
		
		@Column(name="DT_INS")
		private Date dtIns;
		
		@Column(name="DT_MOD")
		private Date dtMod;
		
		@Column(name="COD_STATO_DOMANDA_INPS")
		private String codStatoDomandaInps;
		
		@Column(name="COD_SAP")
		private String codSap;
		
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name ="COD_SAP", insertable = false,  updatable = false)
		private RdCAnpalSAP sap;
		
		@OneToOne
		@JoinColumn(name="RESIDENZA_CPI_COD", insertable = false, updatable = false)
		private RdCTbCpi cpi;
		
		public String getCodSap() {
			return codSap;
		}

		public void setCodSap(String codSap) {
			this.codSap = codSap;
		}

		
		
		public String getCodStatoDomandaInps() {
			return codStatoDomandaInps;
		}

		public void setCodStatoDomandaInps(String codStatoDomandaInps) {
			this.codStatoDomandaInps = codStatoDomandaInps;
		}
		
		public boolean isRichiedente() {
			return this.ruolo!=null && "R".equalsIgnoreCase(ruolo);
		}
		
		public boolean isFamiliare() {
			return this.ruolo!=null && "M".equalsIgnoreCase(ruolo);
		}

		public String getCfRichiedente() {
			return cfRichiedente;
		}

		public void setCfRichiedente(String cfRichiedente) {
			this.cfRichiedente = cfRichiedente;
		}

		public Date getDataDomanda() {
			return dataDomanda;
		}

		public void setDataDomanda(Date dataDomanda) {
			this.dataDomanda = dataDomanda;
		}

		public Date getDataDecorrenzaBen() {
			return dataDecorrenzaBen;
		}

		public void setDataDecorrenzaBen(Date dataDecorrenzaBen) {
			this.dataDecorrenzaBen = dataDecorrenzaBen;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getNascitaLuogoCod() {
			return nascitaLuogoCod;
		}

		public void setNascitaLuogoCod(String nascitaLuogoCod) {
			this.nascitaLuogoCod = nascitaLuogoCod;
		}

		public String getNascitaLuogoDes() {
			return nascitaLuogoDes;
		}

		public void setNascitaLuogoDes(String nascitaLuogoDes) {
			this.nascitaLuogoDes = nascitaLuogoDes;
		}

		public String getRuolo() {
			return ruolo;
		}

		public void setRuolo(String ruolo) {
			this.ruolo = ruolo;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCittadinanzaCod() {
			return cittadinanzaCod;
		}

		public void setCittadinanzaCod(String cittadinanzaCod) {
			this.cittadinanzaCod = cittadinanzaCod;
		}

		public String getResidenzaComuneCod() {
			return residenzaComuneCod;
		}

		public void setResidenzaComuneCod(String residenzaComuneCod) {
			this.residenzaComuneCod = residenzaComuneCod;
		}

		public String getResidenzaComuneDes() {
			return residenzaComuneDes;
		}

		public void setResidenzaComuneDes(String residenzaComuneDes) {
			this.residenzaComuneDes = residenzaComuneDes;
		}

		public String getResidenzaIndirizzo() {
			return residenzaIndirizzo;
		}

		public void setResidenzaIndirizzo(String residenzaIndirizzo) {
			this.residenzaIndirizzo = residenzaIndirizzo;
		}

		public String getResidenzaCap() {
			return residenzaCap;
		}

		public void setResidenzaCap(String residenzaCap) {
			this.residenzaCap = residenzaCap;
		}

		public String getResidenzaCPICod() {
			return residenzaCPICod;
		}

		public void setResidenzaCPICod(String residenzaCPICod) {
			this.residenzaCPICod = residenzaCPICod;
		}

		public String getDomicilioComuneCod() {
			return domicilioComuneCod;
		}

		public void setDomicilioComuneCod(String domicilioComuneCod) {
			this.domicilioComuneCod = domicilioComuneCod;
		}

		public String getDomicilioComuneDes() {
			return domicilioComuneDes;
		}

		public void setDomicilioComuneDes(String domicilioComuneDes) {
			this.domicilioComuneDes = domicilioComuneDes;
		}

		public String getDomicilioIndirizzo() {
			return domicilioIndirizzo;
		}

		public void setDomicilioIndirizzo(String domicilioIndirizzo) {
			this.domicilioIndirizzo = domicilioIndirizzo;
		}

		public String getDomicilioCap() {
			return domicilioCap;
		}

		public void setDomicilioCap(String domicilioCap) {
			this.domicilioCap = domicilioCap;
		}

		public String getStatoCod() {
			return statoCod;
		}

		public void setStatoCod(String statoCod) {
			this.statoCod = statoCod;
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

		public RdCAnpalSAP getSap() {
			return sap;
		}

		public void setSap(RdCAnpalSAP sap) {
			this.sap = sap;
		}
		
		public RdCBeneficiarioPK getId() {
			return id;
		}

		public void setId(RdCBeneficiarioPK id) {
			this.id = id;
		}

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public RdCTbCpi getCpi() {
			return cpi;
		}

		public void setCpi(RdCTbCpi cpi) {
			this.cpi = cpi;
		}
		
}
