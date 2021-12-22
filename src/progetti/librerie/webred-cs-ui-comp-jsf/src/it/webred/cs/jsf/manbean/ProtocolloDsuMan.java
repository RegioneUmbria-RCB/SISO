package it.webred.cs.jsf.manbean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.jsf.bean.ProtocolloDsuBean;
import it.webred.cs.jsf.interfaces.IProtocolloDsu;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;
import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeParams;
import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

@ManagedBean
@ViewScoped
public class ProtocolloDsuMan extends CsUiCompBaseBean implements IProtocolloDsu {

	private ProtocolloDsuBean dto;
	private String codfisc;
	private String idRichiesta;
	
	private String codPrestazione; //Serve solo per l'interrogazione al WS ISEE
	private Long tipologia;        //Serve solo per l'interrogazione al WS ISEE - interpetazione di quale precaricare nella form.
	
	private List<DatiIsee> elencoAttestazioni = null;
	
	public ProtocolloDsuMan(){
		dto = new ProtocolloDsuBean();
	}
	
	public ProtocolloDsuMan(CsIPs ps, String cf){
		this();
		if(ps!=null){
			dto.setAnno(ps.getAnnoProtDSU());
			dto.setPrefisso(ps.getPrefixProtDSU());
			dto.setNumero(ps.getNumProtDSU());
			dto.setProgressivo(ps.getProgProtDSU());
			codfisc = cf;
			idRichiesta = "E"+(ps.getCsIInterventoEsegMast()!=null ? ps.getCsIInterventoEsegMast().getId():"");
			codPrestazione = ps.getCodPrestazione();
			tipologia = DataModelCostanti.TipoIsee.ORDINARIO;
		}
	}
	
	public ProtocolloDsuMan(CsDIsee isee) {
		this();
		if(isee!=null){
			dto.setAnno(!StringUtils.isEmpty(isee.getAnnoRif()) ? Integer.parseInt(isee.getAnnoRif()) : null);
			dto.setPrefisso(isee.getPrefixProtDSU());
			dto.setNumero(isee.getNumProtDSU());
			dto.setProgressivo(isee.getProgProtDSU());
			codfisc = isee.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getCf();
			idRichiesta = "C"+isee.getCsDDiario().getCsACaso().getIdentificativo();
			if(isee.getTipoIsee()!=null)
				tipologia = isee.getTipoIsee().getId();
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
	public DatiIsee trovaAttestazione(){
		DatiIsee isee = null;
		if(elencoAttestazioni!=null && !elencoAttestazioni.isEmpty()){
		  if(isTipologiaSelected()){
			int i = 0;
			while(isee==null && i<elencoAttestazioni.size()){
				DatiIsee tmp = elencoAttestazioni.get(i);
				Long tmpTipo = tmp.getTipo().getId();
				if(tmpTipo.equals(tipologia))
					isee = SerializationUtils.clone(tmp);
				i++;
			}
		  }else if(elencoAttestazioni.size()==1 && !isTipologiaSelected()){ //Se il risultato è unico precarico 
			  isee = SerializationUtils.clone(elencoAttestazioni.get(0));
		  }
		}
	
		if(isee!=null){
			dto.setPrefisso(isee.getProtDSU_prefisso() + dto.getAnno());
			dto.setAnno(isee.getProtDSU_anno());
			dto.setNumero(isee.getProtDSU_numero());
			dto.setProgressivo(isee.getProtDSU_progressivo());
		
		}else{ 
			resetProtDSU();
			dto.setPrefisso(DataModelCostanti.CSIPs.PREFIX_PROT_DSU + dto.getAnno());
		}
		
		return isee;
	}
	
	@Override
	public DatiIsee cbxAnnoDsuListener(){
		DatiIsee iseeSelezionato = null;
		elencoAttestazioni = null;
		if(dto.isAnnoSelected()){
			
			RicercaIseeParams params = new RicercaIseeParams();
			fillEnte(params);
			params.setCf(codfisc);
			Date dataValidita = null;
			try {
				dataValidita = ddMMyyyy.parse("31/12/"+dto.getAnno());
			} catch (ParseException e) {}
			params.setDataValidita(dataValidita);
			params.setIdCasoErogazione(idRichiesta);
			params.setCodPrestazione(codPrestazione);
			RicercaIseeResult res = getRicercaIseeBean().ricercaIsee(params);
			elencoAttestazioni = res!=null ? res.getElencoAttestazioni() : null;
			iseeSelezionato = trovaAttestazione();
		}else {
			dto.setAnno(null);
			resetProtDSU();
		}
		return iseeSelezionato;
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

	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getCodPrestazione() {
		return codPrestazione;
	}

	public void setCodPrestazione(String codPrestazione) {
		this.codPrestazione = codPrestazione;
	}
	
	public boolean isTipologiaSelected(){
		return tipologia!=null && tipologia.intValue()>0;
	}

	public Long getTipologia() {
		return tipologia;
	}

	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
	}

	public List<DatiIsee> getElencoAttestazioni() {
		return elencoAttestazioni;
	}

	public void setElencoAttestazioni(List<DatiIsee> elencoAttestazioni) {
		this.elencoAttestazioni = elencoAttestazioni;
	}

	@Override
	public boolean isRenderMessage() {
		return  elencoAttestazioni!=null && elencoAttestazioni.size()>1 && !this.isTipologiaSelected();
	}

}
	


