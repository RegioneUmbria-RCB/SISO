package it.webred.cs.jsf.manbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.jsf.bean.ProtocolloDsuBean;
import it.webred.cs.jsf.interfaces.IProtocolloDsu;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
@ViewScoped
public class ProtocolloDsuMan extends CsUiCompBaseBean implements IProtocolloDsu {

	private ProtocolloDsuBean dto;
	
	public ProtocolloDsuMan(){
		dto = new ProtocolloDsuBean();
	}
	
	public ProtocolloDsuMan(CsIPs ps){
		dto = new ProtocolloDsuBean();
		if(ps!=null){
			dto.setAnno(ps.getAnnoProtDSU());
			dto.setPrefisso(ps.getPrefixProtDSU());
			dto.setNumero(ps.getNumProtDSU());
			dto.setProgressivo(ps.getProgProtDSU());
		}
	}
	
	public ProtocolloDsuMan(CsDIsee isee) {
		dto = new ProtocolloDsuBean();
		if(isee!=null){
			dto.setAnno(!StringUtils.isEmpty(isee.getAnnoRif()) ? Integer.parseInt(isee.getAnnoRif()) : null);
			dto.setPrefisso(isee.getPrefixProtDSU());
			dto.setNumero(isee.getNumProtDSU());
			dto.setProgressivo(isee.getProgProtDSU());
		}
	}

	@Override
	public List<String> getListaAnniDsu() {
		ArrayList<String> listaAnni = null;

		if (listaAnni == null) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			listaAnni = new ArrayList<String>();
			listaAnni.add(String.valueOf(year));
			listaAnni.add(String.valueOf(year - 1));
			listaAnni.add(String.valueOf(year - 2));
			listaAnni.add(String.valueOf(year - 3));
			listaAnni.add(String.valueOf(year - 4));
			listaAnni.add(String.valueOf(year - 5));
		}
		return listaAnni;
	}
	
	@Override
	public void cbxAnnoDsuListener(AjaxBehaviorEvent event){
		if(dto.isAnnoSelected())
			dto.setPrefisso(DataModelCostanti.CSIPs.PREFIX_PROT_DSU + dto.getAnno());
		else {
			dto.setAnno(null);
			resetProtDSU();
		}
	}
	
	@Override
	public void resetProtDSU() {
		dto.setPrefisso(null);
		dto.setNumero(null);
		dto.setProgressivo(null);
	}


	public ProtocolloDsuBean getDto() {
		return dto;
	}

	public void setDto(ProtocolloDsuBean dto) {
		this.dto = dto;
	}

	@Override
	public void valorizza(CsIPs csIPs) {
		if (!csIPs.isSituazioneEconomicaVerificata()) {
			dto.setAnno(null);
			resetProtDSU();
			csIPs.setDataDSU(null);
		}else{
			if(dto.getAnno()!=null && dto.getAnno().intValue()==0) 			dto.setAnno(null);
			if(dto.getAnno()==null || StringUtils.isBlank(dto.getNumero())) this.resetProtDSU();
		}

		csIPs.setAnnoProtDSU(dto.getAnno());
		csIPs.setPrefixProtDSU(dto.getPrefisso());
		csIPs.setNumProtDSU(dto.getNumero());
		csIPs.setProgProtDSU(dto.getProgressivo());
	}

	@Override
	public boolean valida(Date dataDSU) {
	  boolean valida = 	
	    (dataDSU != null && dto.isAnnoSelected()   && !StringUtils.isEmpty(dto.getNumero()) ||
		(dataDSU == null && !dto.isAnnoSelected()  && StringUtils.isEmpty(dto.getNumero())));
	  return valida;
	}

	@Override
	public void valorizza(CsDIsee isee) {
		isee.setAnnoRif(dto.getAnno()!=null && dto.getAnno()>0 ? dto.getAnno().toString() : null);
		isee.setPrefixProtDSU(dto.getPrefisso());
		isee.setNumProtDSU(dto.getNumero());
		isee.setProgProtDSU(dto.getProgressivo());
	}

	@Override
	public String getStampa() {
		String s =  "Anno di riferimento: "+ (dto.getAnno() !=null && dto.getAnno()>0  ? dto.getAnno() : "    ");
		s+= " Protocollo DSU: " + dto.getProtocolloCompleto();
		return s;
	}

}
	


