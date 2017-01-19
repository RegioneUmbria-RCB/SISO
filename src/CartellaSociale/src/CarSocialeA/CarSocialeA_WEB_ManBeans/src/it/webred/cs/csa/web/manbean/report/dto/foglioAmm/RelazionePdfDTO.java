package it.webred.cs.csa.web.manbean.report.dto.foglioAmm;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;



public class RelazionePdfDTO extends BasePdfDTO {

	
		private String ufficio = EMPTY_VALUE;
		private String dataOdierna = EMPTY_VALUE;
		private String anagrafica= EMPTY_VALUE;
		private String socioAmbientale= EMPTY_VALUE;
		private String parentale = EMPTY_VALUE;	
		private String sanitaria=EMPTY_VALUE;
		private String medicoCur=EMPTY_VALUE;
		private String isee=EMPTY_VALUE;
		private String assistenteS = EMPTY_VALUE;
		private String responsabileS= EMPTY_VALUE;
		

		
	
		public String getSocioAmbientale() {
			return socioAmbientale;
		}
		public void setSocioAmbientale(String socioAmbientale) {
			this.socioAmbientale = format2(socioAmbientale);
		}
		public String getParentale() {
			return parentale;
		}
		public void setParentale(String parentale) {
			this.parentale = format2(parentale);
		}
		public String getUfficio() {
			return ufficio;
		}
		public void setUfficio(String ufficio) {
			this.ufficio = format2(ufficio);
		}
		public String getAssistenteS() {
			return assistenteS;
		}
		public void setAssistenteS(String assistenteS) {
			this.assistenteS = format2(assistenteS);
		}
		public String getResponsabileS() {
			return responsabileS;
		}
		public void setResponsabileS(String responsabileS) {
			this.responsabileS = format2(responsabileS);
		}
		public String getAnagrafica() {
			return anagrafica;
		}
		public void setAnagrafica(String anagrafica) {
			this.anagrafica = format2(anagrafica);
		}
		public String getSanitaria() {
			return sanitaria;
		}
		public void setSanitaria(String sanitaria) {
			this.sanitaria = format2(sanitaria);
		}
		public String getMedicoCur() {
			return medicoCur;
		}
		public void setMedicoCur(String medicoCur) {
			this.medicoCur = format2(medicoCur);
		}
	
		public String getDataOdierna() {
			return dataOdierna;
		}
		public void setDataOdierna(String dataOdierna) {
			this.dataOdierna = format2(dataOdierna);
		}
		public String getIsee() {
			return isee;
		}
		public void setIsee(String isee) {
			this.isee = format2(isee);
		}
	

		
}
