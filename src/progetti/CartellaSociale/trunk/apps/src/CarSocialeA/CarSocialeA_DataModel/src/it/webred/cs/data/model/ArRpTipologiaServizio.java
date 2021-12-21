package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="AR_RP_TIPOLOGIA_SERVIZIO")
@NamedQuery(name="TipologiaServizio.findAll", query="SELECT t FROM ArRpTipologiaServizio t")
public class ArRpTipologiaServizio implements Serializable {
	
		private static final long serialVersionUID = 1L;

		@Id
		private long id;


		private String descrizione;
		
		@Column(name = "N_MIN_CONS")
		private Long nMinCons;
		
		@Column(name = "N_MIN_MSNA_CONS")
		private Long nMinMsnaCons;
		
		@Column(name = "N_MIN_AREA_PENALE_CONS")
		private Long nMinAreaPenaleCons;
		
		@Column(name = "N_MIN_EMERG_CONS")
		private Long nMinEmergCons;
		
		@Column(name = "N_FRATRIE_CONS")
		private Long nFratrieCons;
		
		@Column(name = "ETA_MIN")
		private Long etaMin;
		
		@Column(name = "ETA_MAX")
		private Long etaMax;
		
		
		public ArRpTipologiaServizio() {
			
		}


		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public String getDescrizione() {
			return descrizione;
		}


		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}


		public Long getnMinCons() {
			return nMinCons;
		}


		public void setnMinCons(Long nMinCons) {
			this.nMinCons = nMinCons;
		}


		public Long getnMinMsnaCons() {
			return nMinMsnaCons;
		}


		public void setnMinMsnaCons(Long nMinMsnaCons) {
			this.nMinMsnaCons = nMinMsnaCons;
		}


		public Long getnMinAreaPenaleCons() {
			return nMinAreaPenaleCons;
		}


		public void setnMinAreaPenaleCons(Long nMinAreaPenaleCons) {
			this.nMinAreaPenaleCons = nMinAreaPenaleCons;
		}


		public Long getnMinEmergCons() {
			return nMinEmergCons;
		}


		public void setnMinEmergCons(Long nMinEmergCons) {
			this.nMinEmergCons = nMinEmergCons;
		}


		public Long getnFratrieCons() {
			return nFratrieCons;
		}


		public void setnFratrieCons(Long nFratrieCons) {
			this.nFratrieCons = nFratrieCons;
		}


		public Long getEtaMin() {
			return etaMin;
		}


		public void setEtaMin(Long etaMin) {
			this.etaMin = etaMin;
		}


		public Long getEtaMax() {
			return etaMax;
		}


		public void setEtaMax(Long etaMax) {
			this.etaMax = etaMax;
		}

		

}