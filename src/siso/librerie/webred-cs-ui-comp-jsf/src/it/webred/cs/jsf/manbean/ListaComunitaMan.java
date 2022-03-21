package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsCComunita;
import it.webred.cs.jsf.interfaces.IListaComunita;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@NoneScoped
public class ListaComunitaMan extends CsUiCompBaseBean implements IListaComunita{

	public static final String TIPO_COMUNITA_RESI_MINORE="Residenza Minori";
	public static final String TIPO_COMUNITA_SEMI_RESI_MINORE="Semi-residenza Minori";
	public static final String TIPO_COMUNITA_RESI_ADULTO = "Residenza Adulti";

	private String tipoComunita;
	
	private Long idComunita;
	private CsCComunita comunitaSel;

	public ListaComunitaMan(){}
	
	public ListaComunitaMan(String descTipoComunita){
		this.tipoComunita = descTipoComunita;
	}
	
	@Override
	public ArrayList<SelectItem> getLstComunita() {
		ArrayList<SelectItem> lst = new ArrayList<SelectItem>();
		lst.add(new SelectItem(null, "- seleziona -"));
		try {
			List<CsCComunita> lstC = this.caricaComunita();
			for(CsCComunita c : lstC){
				CsAAnaIndirizzo anaInd = c.getCsAAnaIndirizzo();
				String descrizione = c.getSogggiuRagsoc()+" - "+anaInd.getIndirizzo()+", "+anaInd.getCivicoNumero()+" - "+anaInd.getComDes()+" ("+anaInd.getProv()+")";
				lst.add(new SelectItem(c.getSettoreId(),descrizione));
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		return lst;
	}
	
	private List<CsCComunita> caricaComunita()  {
		List<CsCComunita> lst = new ArrayList<CsCComunita>();
		BaseDTO bo = new BaseDTO();
		fillEnte(bo);
		bo.setObj(tipoComunita);
		try {
			BaseDTO base = new BaseDTO();
			fillEnte(base);
			base.setObj(this.tipoComunita);
				
			lst = confService.findComunitaByDescTipo(base);
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		return lst;
	}

	private CsCComunita getComunita(Long id){
		List<CsCComunita> lst =  this.caricaComunita();
		CsCComunita componente = null;
		int i=0;
		boolean trovato = false;
		while(i<lst.size() && !trovato){
			Long idC = lst.get(i).getSettoreId();
			if(idC.compareTo(id)==0){
				trovato=true;
				componente = lst.get(i);
			}
			i++;
		}
		return componente;
	}
	
	@Override
	public void aggiorna() {
		if(idComunita!=null)
			this.comunitaSel = this.getComunita(idComunita);
		else
			this.comunitaSel=null;
	}

	
	public String getTipoComunita() {
		return tipoComunita;
	}

	public void setTipoComunita(String tipoComunita) {
		this.tipoComunita = tipoComunita;
	}

	public Long getIdComunita() {
		return idComunita;
	}

	public void setIdComunita(Long idComunita) {
		this.idComunita = idComunita;
	}

	public CsCComunita getComunitaSel() {
		return comunitaSel;
	}
	
	public void setComunitaSel(CsCComunita comunitaSel) {
		this.comunitaSel = comunitaSel;
	}
}
