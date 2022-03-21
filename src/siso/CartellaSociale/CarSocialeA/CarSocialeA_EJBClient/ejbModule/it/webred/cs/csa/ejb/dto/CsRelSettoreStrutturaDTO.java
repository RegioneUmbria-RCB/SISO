package it.webred.cs.csa.ejb.dto;

public class CsRelSettoreStrutturaDTO {

		
		private Long id;


		private Long idSettore;

		
		private Long idStruttura; 

		private String codBelfioreFittizio;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getIdSettore() {
			return idSettore;
		}

		public void setIdSettore(Long idSettore) {
			this.idSettore = idSettore;
		}

		public Long getIdStruttura() {
			return idStruttura;
		}

		public void setIdStruttura(Long idStruttura) {
			this.idStruttura = idStruttura;
		}

		public String getCodBelfioreFittizio() {
			return codBelfioreFittizio;
		}

		public void setCodBelfioreFittizio(String codBelfioreFittizio) {
			this.codBelfioreFittizio = codBelfioreFittizio;
		}

	

}
