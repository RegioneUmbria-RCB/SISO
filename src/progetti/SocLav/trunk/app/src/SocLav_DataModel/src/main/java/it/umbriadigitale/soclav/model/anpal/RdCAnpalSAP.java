package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RDC_ANPAL_SAP")
public class RdCAnpalSAP implements Serializable {

		@Id
	    private String id;
		private String sap;
		
		@Column(name="DT_INS")
		private Date dtIns;
		
		@Column(name="DT_MOD")
		private Date dtMod;
		
		
		
		
		public Date getDtIns() {
			return dtIns;
		}
		public void setDtIns(Date dtIns) {
			this.dtIns = dtIns;
		}
		public Date getDtMod() {
			return dtMod;
		}
		public void setDtMod(Date dtMod) {
			this.dtMod = dtMod;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSap() {
			return sap;
		}
		public void setSap(String sap) {
			this.sap = sap;
		}

}

