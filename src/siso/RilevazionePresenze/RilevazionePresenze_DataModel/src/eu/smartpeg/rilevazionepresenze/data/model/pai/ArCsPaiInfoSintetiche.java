package eu.smartpeg.rilevazionepresenze.data.model.pai;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



	@Entity
	@Table(name = "AR_CS_PAI_INFO_SINTETICHE")
	@NamedQuery(name = "ArCsPaiInfoSintetiche.findAll", query = "SELECT c FROM ArCsPaiInfoSintetiche c")
	public class ArCsPaiInfoSintetiche implements Serializable {
		
	
		private static final long serialVersionUID = 1L;

		@Id
		@SequenceGenerator(name = "AR_CS_PAI_INFO_SINTETICHE_ID_GENERATOR", sequenceName = "SQ_PAI_INFO_SINTESI", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AR_CS_PAI_INFO_SINTETICHE_ID_GENERATOR")
		private Long id;

		@Column(name = "ID_PAI_PTI")
		private Long idPaiPTI;

		@Column(name = "COD_ROUTING")
		private String codRouting;

		@Column(name = "ID_DOC_PAI")
		private Long idDocPai;
		
		@Column(name = "FLAG_MSNA")
		private Boolean msna;			//Minore Straniero Non Accompagnato

		@Column(name = "FLAG_COPIA_FORNITA")
		private Boolean flagCopiaFornita;	
		
		@Column(name = "FLAG_ETA_ACCERTATA")
		private Boolean flagEtaAccertata;	
		
		@Column(name = "FLAG_SUPP_LEGALE")
		private Boolean flagSuppLegale;	
		
		@Column(name = "FLAG_SERVIZIO_INF_OR")
		private Boolean flagServizioInfOr;	
		
		@Column(name = "FLAG_TRATTA_ART17")
		private Boolean flagTrattaArt17;	
		
		@Column(name = "FLAG_ALFABETIZZATO")
		private Boolean flagAlfabetizzato;

		@OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "ID_DOC_PAI", referencedColumnName = "id",  insertable = false, updatable = false)
	    private ArCsPaiPtiDocumento arCsPaiPtiDocumento;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		
		public String getCodRouting() {
			return codRouting;
		}

		public void setCodRouting(String codRouting) {
			this.codRouting = codRouting;
		}

		public Long getIdPaiPTI() {
			return idPaiPTI;
		}

		public void setIdPaiPTI(Long idPaiPTI) {
			this.idPaiPTI = idPaiPTI;
		}

		public Long getIdDocPai() {
			return idDocPai;
		}

		public void setIdDocPai(Long idDocPai) {
			this.idDocPai = idDocPai;
		}

		public Boolean getMsna() {
			return msna;
		}

		public void setMsna(Boolean msna) {
			this.msna = msna;
		}

		public Boolean getFlagCopiaFornita() {
			return flagCopiaFornita;
		}

		public void setFlagCopiaFornita(Boolean flagCopiaFornita) {
			this.flagCopiaFornita = flagCopiaFornita;
		}

		public Boolean getFlagEtaAccertata() {
			return flagEtaAccertata;
		}

		public void setFlagEtaAccertata(Boolean flagEtaAccertata) {
			this.flagEtaAccertata = flagEtaAccertata;
		}

		public Boolean getFlagSuppLegale() {
			return flagSuppLegale;
		}

		public void setFlagSuppLegale(Boolean flagSuppLegale) {
			this.flagSuppLegale = flagSuppLegale;
		}

		public Boolean getFlagServizioInfOr() {
			return flagServizioInfOr;
		}

		public void setFlagServizioInfOr(Boolean flagServizioInfOr) {
			this.flagServizioInfOr = flagServizioInfOr;
		}

		public Boolean getFlagTrattaArt17() {
			return flagTrattaArt17;
		}

		public void setFlagTrattaArt17(Boolean flagTrattaArt17) {
			this.flagTrattaArt17 = flagTrattaArt17;
		}

		public Boolean getFlagAlfabetizzato() {
			return flagAlfabetizzato;
		}

		public void setFlagAlfabetizzato(Boolean flagAlfabetizzato) {
			this.flagAlfabetizzato = flagAlfabetizzato;
		}

		public ArCsPaiPtiDocumento getArCsPaiPtiDocumento() {
			return arCsPaiPtiDocumento;
		}

		public void setArCsPaiPtiDocumento(ArCsPaiPtiDocumento arCsPaiPtiDocumento) {
			this.arCsPaiPtiDocumento = arCsPaiPtiDocumento;
		}	
	
}
