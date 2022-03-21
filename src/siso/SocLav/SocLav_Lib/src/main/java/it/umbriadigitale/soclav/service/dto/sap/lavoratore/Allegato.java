package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.ConoscenzeInformatiche;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.LingueStraniere;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.LstAltreInformazioni;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.LstFormazioneProfessionale;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato.LstTitoliStudio;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Allegato implements Serializable {

	/*

	<allegato>        
		<titolistudio_lst>
			<titolostudio>    
				...
			</titolostudio>        
		</titolistudio_lst>
		<formazioneprofessionale_lst>
			<formazioneprofessionale>    
				...
			</formazioneprofessionale>
		</formazioneprofessionale_lst>
		<linguestraniere_lst>
			<linguastraniera>    
				...
			</linguastraniera>
		</linguestraniere_lst>
		<conoscenzeinformatiche_lst>
				...
		</conoscenzeinformatiche_lst>
		<altreinformazioni_lst>
				...
		</altreinformazioni_lst>    
	</allegato>    
	 * */
	
	private LstTitoliStudio titolistudio_lst;
	private LstFormazioneProfessionale formazioneprofessionale_lst;
	private LingueStraniere linguestraniere_lst;
	private ConoscenzeInformatiche conoscenzeinformatiche_lst;
	private LstAltreInformazioni altreinformazioni_lst;
	
	public LstFormazioneProfessionale getFormazioneprofessionale_lst() {
		return formazioneprofessionale_lst;
	}
	public void setFormazioneprofessionale_lst(LstFormazioneProfessionale formazioneprofessionale_lst) {
		this.formazioneprofessionale_lst = formazioneprofessionale_lst;
	}
	public LingueStraniere getLinguestraniere_lst() {
		return linguestraniere_lst;
	}
	public void setLinguestraniere_lst(LingueStraniere linguestraniere_lst) {
		this.linguestraniere_lst = linguestraniere_lst;
	}
	public ConoscenzeInformatiche getConoscenzeinformatiche_lst() {
		return conoscenzeinformatiche_lst;
	}
	public void setConoscenzeinformatiche_lst(ConoscenzeInformatiche conoscenzeinformatiche_lst) {
		this.conoscenzeinformatiche_lst = conoscenzeinformatiche_lst;
	}
	public LstTitoliStudio getTitolistudio_lst() {
		return titolistudio_lst;
	}
	public void setTitolistudio_lst(LstTitoliStudio titolistudio_lst) {
		this.titolistudio_lst = titolistudio_lst;
	}
	public LstAltreInformazioni getAltreinformazioni_lst() {
		return altreinformazioni_lst;
	}
	public void setAltreinformazioni_lst(LstAltreInformazioni altreinformazioni_lst) {
		this.altreinformazioni_lst = altreinformazioni_lst;
	}
}
