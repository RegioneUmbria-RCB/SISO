package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdCBeneficiarioPK implements Serializable {

		@Column(name="PROTOCOLLO_INPS_COD")
	    private String protocolloINPSCod;
		
		private String cf;

		public RdCBeneficiarioPK() {}
		
		public RdCBeneficiarioPK(String protocollo, String cf) {
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
