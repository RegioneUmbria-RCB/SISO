package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class LstFormazioneProfessionale implements Serializable {

	private List<FormazioneProfessionale> formazioneprofessionale;

	public List<FormazioneProfessionale> getFormazioneprofessionale() {
		return formazioneprofessionale;
	}

	public void setFormazioneprofessionale(List<FormazioneProfessionale> formazioneprofessionale) {
		this.formazioneprofessionale = formazioneprofessionale;
	}

}
