package it.umbriadigitale.soclav.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="RDC_USER")
public class RdCUser implements Serializable {
	
		@Id
		private String username;
		
		@Column(name="PATH_ROOT")
		private String pathRoot;
	
		@Column(name="ENTE_DEFAULT")
		private String enteDefault;

		private String cognome;
	
		private String nome;
		
		private String sesso;
		
		@Column(name="DATA_NASCITA")
		private Date dataNascita;
		
		@Column(name="COMUNE_NASCITA")
		private String comuneNascita;
		
		private String email;

		 @ManyToMany(fetch = FetchType.EAGER)
			@JoinTable(
				name="RDC_USER_ENTE"
				, joinColumns={
					@JoinColumn(name="RDC_USER_FK")
					}
				, inverseJoinColumns={
					@JoinColumn(name="RDC_COMUNE_FK")
					}
				)
			private List<RdCComune> rdcEnti;
		 
		
		@OneToMany(mappedBy="user")
		@LazyCollection(LazyCollectionOption.FALSE)
		private List<RdCUserCpi> rdcCpi;


		public List<RdCComune> getRdcEnti() {
			return rdcEnti;
		}

		public void setRdcEnti(List<RdCComune> rdcEnti) {
			this.rdcEnti = rdcEnti;
		}

		public String getPathRoot() {
			return pathRoot;
		}

		public void setPathRoot(String pathRoot) {
			this.pathRoot = pathRoot;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getComuneNascita() {
			return comuneNascita;
		}

		public void setComuneNascita(String comuneNascita) {
			this.comuneNascita = comuneNascita;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getEnteDefault() {
			return enteDefault;
		}

		public void setEnteDefault(String enteDefault) {
			this.enteDefault = enteDefault;
		}

		public List<RdCUserCpi> getRdcCpi() {
			return rdcCpi;
		}

		public void setRdcCpi(List<RdCUserCpi> rdcCpi) {
			this.rdcCpi = rdcCpi;
		}

}