package it.webred.ss.web.bean.util;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.Serializable;

public class Organizzazione extends SegretariatoSocBaseBean implements Serializable{
	
	private String belfiore;
	private String nome;
	private Long id;
	
	public Organizzazione(){
		belfiore = null;
		nome="";
		id=null;
	}
	
	public Organizzazione(Long id, String belfiore, String nome){
		this.belfiore =belfiore;
		this.nome=nome;
		this.id=id;
	}
	
	public Organizzazione(SsOOrganizzazione o){
		if(o!=null){
			this.belfiore=o.getBelfiore();
			this.nome=o.getNome();
			this.id=o.getId();
		}
	}
	
	public int countSchedeRicevute() {
		int countSchede = 0;
		SsSchedaSessionBeanRemote schedaService;

		try {
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility
					.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB",
							"SsSchedaSessionBean");

			SsSearchCriteria dto = new SsSearchCriteria();
			fillUserData(dto);
			dto.setOrganizzazioneId(this.id);
			dto.setZonaSociale(UserBean.getBeanIstance().getZonaSociale());

			countSchede = schedaService.countSchedeInviateEnteNonLette(dto);

		} catch (Exception e) {
			countSchede=0;
		}

		return countSchede;
	}
	
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void fillModel(SsOOrganizzazione model){
		model.setId(this.id);
		model.setBelfiore(this.belfiore);
		model.setNome(this.getNome());
	}
	
}
