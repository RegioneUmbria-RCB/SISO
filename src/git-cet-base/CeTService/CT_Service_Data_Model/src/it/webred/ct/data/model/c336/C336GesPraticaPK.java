package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

public class C336GesPraticaPK implements Serializable {
		//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		@Column(name="ID_PRATICA")
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long idPratica;

		@Column(name="USER_NAME")
		private String userName;

	    @Temporal( TemporalType.DATE)
		@Column(name="DT_INI_GES")
		private java.util.Date dtIniGes;
	    
	    public C336GesPraticaPK() {
	    }

		public Long getIdPratica() {
			return idPratica;
		}

		public void setIdPratica(Long idPratica) {
			this.idPratica = idPratica;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public java.util.Date getDtIniGes() {
			return dtIniGes;
		}

		public void setDtIniGes(java.util.Date dtIniGes) {
			this.dtIniGes = dtIniGes;
		}
	    
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof C336GesPraticaPK)) {
				return false;
			}
			C336GesPraticaPK castOther = (C336GesPraticaPK)other;
			return 
				(this.idPratica == castOther.idPratica)
				&& this.userName.equals(castOther.userName)
				&& this.dtIniGes.equals(castOther.dtIniGes);

	    }
	    
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.idPratica.longValue() ^ ( new Long(this.idPratica.longValue()) >>> 32)));
			hash = hash * prime + this.userName.hashCode();
			hash = hash * prime + this.dtIniGes.hashCode();
			
			return hash;
	    }
}
