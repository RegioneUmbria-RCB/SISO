package it.umbriadigitale.soclav.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdCUserCpiPK implements Serializable {

		private String username;
		
		@Column(name="COD_CPI")
		private String codCpi;
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getCodCpi() {
			return codCpi;
		}

		public void setCodCpi(String codCpi) {
			this.codCpi = codCpi;
		}

}

