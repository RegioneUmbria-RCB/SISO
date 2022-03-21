package it.umbriadigitale.soclav.model.gepi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdCGepiBeneficiarioPK implements Serializable {

		@Column(name="FK_RDC_DOMANDA")
	    private String protocolloINPSCod;
		
		private String cf;

		public RdCGepiBeneficiarioPK() {}
		
		public RdCGepiBeneficiarioPK(String protocollo, String cf) {
			this.protocolloINPSCod= protocollo;
			this.cf = cf;
		}
		
		public String getProtocolloINPSCod() {
			return protocolloINPSCod;
		}

		public void setProtocolloINPSCod(String protocolloINPSCod) {
			this.protocolloINPSCod = protocolloINPSCod;
		}

		public String getCf() {
			return cf;
		}

		public void setCf(String cf) {
			this.cf = cf;
		}
		
		
}
