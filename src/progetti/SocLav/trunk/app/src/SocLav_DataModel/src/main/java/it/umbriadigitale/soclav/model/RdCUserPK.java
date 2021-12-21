package it.umbriadigitale.soclav.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdCUserPK implements Serializable {

		@Column(name="LOGIN")
		private String login;
		
		@Column(name="ENTE")
		private String ente;

		public RdCUserPK() {}
		
		public RdCUserPK(String login, String ente) {
			this.ente= ente;
			this.login = login;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}
		public String getEnte() {
			return ente;
		}

		public void setEnte(String ente) {
			this.ente = ente;
		}
		
 	
}

