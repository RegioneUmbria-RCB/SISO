package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TrascodificheRequestDTO  implements Serializable {

  
		private static final long serialVersionUID = 7609974482932118973L;
	  
		private String nomeTabella;   
		
		private Long idInviante;  
		
		private String nomeInviante;

		public String getNomeTabella() {
			return nomeTabella;
		}

		public void setNomeTabella(String nomeTabella) {
			this.nomeTabella = nomeTabella;
		}

		public Long getIdInviante() {
			return idInviante;
		}

		public void setIdInviante(Long idInviante) {
			this.idInviante = idInviante;
		}

		public String getNomeInviante() {
			return nomeInviante;
		}

		public void setNomeInviante(String nomeInviante) {
			this.nomeInviante = nomeInviante;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
	 
}
