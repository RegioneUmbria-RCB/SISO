package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_GRUPPO_VUL_PART")
public class FseChkGruppoVulPart implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = -6621621351285023793L;
	@Id
	private String CodiceVulnerabilePa;
	private String DescrVulnerabilePa;
	
	@Column(name ="CODICE_VULNERABILE_PA")
	public String getCodiceVulnerabilePa() {
		return CodiceVulnerabilePa;
	}
	public void setCodiceVulnerabilePa(String codiceVulnerabilePa) {
		CodiceVulnerabilePa = codiceVulnerabilePa;
	}
	
	@Column(name ="DESCR_VULNERABILE_PA")
	public String getDescrVulnerabilePa() {
		return DescrVulnerabilePa;
	}
	public void setDescrVulnerabilePa(String descrVulnerabilePa) {
		DescrVulnerabilePa = descrVulnerabilePa;
	}
	
	
}
