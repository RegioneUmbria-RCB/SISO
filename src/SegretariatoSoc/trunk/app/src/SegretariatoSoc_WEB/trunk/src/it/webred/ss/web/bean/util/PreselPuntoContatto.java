package it.webred.ss.web.bean.util;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class PreselPuntoContatto implements Serializable{
	
	private PuntoContatto puntoContatto;

	public PreselPuntoContatto(){
		puntoContatto = new PuntoContatto();
	}
	
	public PuntoContatto getPuntoContatto() {
		return puntoContatto;
	}

	public void setPuntoContatto(PuntoContatto puntoContatto) {
		this.puntoContatto = puntoContatto;
	}

	public void onChangePuntoContatto(){
		List<SelectItem> lstPCont = puntoContatto.getUfficio().getListaPContatto();
		if(puntoContatto.getIdPContatto()!=null && puntoContatto.getIdPContatto()>0){
			for(SelectItem si : lstPCont){
				if((Long)si.getValue()==puntoContatto.getIdPContatto())
					puntoContatto.setNomePContatto(si.getLabel());
			}
		}else{
			puntoContatto.setIdPContatto(null);
			puntoContatto.setNomePContatto(null);
		}
	}
	
	public void resetOrganizzazione(){
		this.puntoContatto.resetOrganizzazione();
	}
	
	public void resetUfficio(){
		this.puntoContatto.resetUfficio();
	}
	
	public void resetPContatto(){
		this.puntoContatto.resetPContatto();
	}


	
}
