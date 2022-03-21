package it.umbriadigitale.soclav.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SL_ANAGRAFICA")
public class SlAnagrafica implements Serializable {

	
		@Id
		@SequenceGenerator(name="SL_ANAGRAFICA_ID_GENERATOR", sequenceName="SQ_ID", allocationSize = 1)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SL_ANAGRAFICA_ID_GENERATOR")
	    private Long id;
		
		@Column(name="NOME")
		private String nome;
		@Column(name="COGNOME")
		private String cognome;
		@Column(name="CF")
		private String cf;
		@Column(name="DATA_NASCITA")
		private Date dataNascita;
		@Column(name="PROV_NASCITA_COD")
		private String provNascitaCod;
		@Column(name="COMUNE_NASCITA_COD")
		private String comuneNascitaCod;
		@Column(name="COMUNE_NASCITA_DES")
		private String comuneNascitaDes;
		@Column(name="STATO_NASCITA_COD")
		private String statoNascitaCod;
		@Column(name="STATO_NASCITA_DES")
		private String statoNascitaDes;
		@Column(name="SESSO")
		private String sesso;
		@Column(name="CITTADINANZA")
		private String cittadinanza;
		@Column(name="DATA_DECESSO")
		private Date dataDecesso;
		@Column(name="PATTO_DI_INCLUSIONE")
		private String pattoDiInclusione;
		
		
		
		
		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public String getCf() {
			return cf;
		}

		public void setCf(String cf) {
			this.cf = cf;
		}

		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getProvNascitaCod() {
			return provNascitaCod;
		}

		public void setProvNascitaCod(String provNascitaCod) {
			this.provNascitaCod = provNascitaCod;
		}

		public String getComuneNascitaCod() {
			return comuneNascitaCod;
		}

		public void setComuneNascitaCod(String comuneNascitaCod) {
			this.comuneNascitaCod = comuneNascitaCod;
		}

		public String getComuneNascitaDes() {
			return comuneNascitaDes;
		}

		public void setComuneNascitaDes(String comuneNascitaDes) {
			this.comuneNascitaDes = comuneNascitaDes;
		}

		public String getStatoNascitaCod() {
			return statoNascitaCod;
		}

		public void setStatoNascitaCod(String statoNascitaCod) {
			this.statoNascitaCod = statoNascitaCod;
		}

		public String getStatoNascitaDes() {
			return statoNascitaDes;
		}

		public void setStatoNascitaDes(String statoNascitaDes) {
			this.statoNascitaDes = statoNascitaDes;
		}

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public String getCittadinanza() {
			return cittadinanza;
		}

		public void setCittadinanza(String cittadinanza) {
			this.cittadinanza = cittadinanza;
		}

		public Date getDataDecesso() {
			return dataDecesso;
		}

		public void setDataDecesso(Date dataDecesso) {
			this.dataDecesso = dataDecesso;
		}

		public String getPattoDiInclusione() {
			return pattoDiInclusione;
		}

		public void setPattoDiInclusione(String pattoDiInclusione) {
			this.pattoDiInclusione = pattoDiInclusione;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
		
	
}
