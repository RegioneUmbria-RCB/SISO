package it.webred.cs.json.controller;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.JsonBaseDTO;
import it.webred.cs.data.model.CsDClob;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.json.dto.JsonBaseBean;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	
	protected ObjectMapper objectMapper;
	protected CsDValutazione dataModel;
	protected CsDValutazione dataModelPadre;
	protected CsOOperatoreSettore operatoreSettore;
	protected Long casoId;
	protected Long udcId;
	protected Long visSecondoLivello=null; //SISO-812
	
	public <T extends JsonBaseBean> T  mapJsonToBean(String json, Class<T>  type) throws JsonParseException, JsonMappingException, IOException{
		objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, type);
	}
	
	public String mapBeanToJson(JsonBaseBean bean) throws JsonProcessingException{
		objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(bean);
	}
	
/*	public String getVersione() {
		return getClass().getName();
	}*/
	
	public void loadData(CsDValutazione scheda) throws Exception {
		dataModel=scheda;
		
		if(dataModel != null) {
			CsDClob clob = dataModel.getCsDDiario().getCsDClob();
				try {	
				if( StringUtils.isNotEmpty(clob.getDatiClob()) ) 
					assignJsonOriginal(mapJsonToBean(clob.getDatiClob(), getClasseBean()));
				
				}catch (Exception e) {
					logger.error("Errore in caricamento JSON: ID["+clob.getId()+"] Clob["+clob.getDatiClob()+"]", e);
					assignJsonOriginal(null);
					throw e;
				}
			}

		restore();
	}

	
	public abstract void assignJsonOriginal(JsonBaseBean jsonOriginal);
	public abstract <T extends JsonBaseBean> T getJsonCurrent();
	
	//Re-istanzia JsonCurrent come JsonOriginal
	public abstract void restore();
	public abstract <T extends JsonBaseBean> Class<T> getClasseBean();
	public abstract Long getDiarioId();
	public abstract Long getDiarioPadreId();
	public abstract Date getDtAmministrativa();
	public abstract Date getDtChiusuraDa();
	public abstract Date getDtChiusuraA();
	public abstract Date getDtAttivazioneDa();
	public abstract Date getDtAttivazioneA();
	public abstract Date getDtSospensioneDa();
	public abstract Date getDtSospensioneA();
	
	public abstract int    getTipoDiarioId();
	public abstract String getDescrizioneScheda();
	
	public void elimina() throws Exception{
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getDiarioId());
		diarioService.deleteSchedaJson(dto);
	}
	
	public void save(String classVersion) throws Exception{
		
		String jsonString = this.mapBeanToJson(getJsonCurrent());

		JsonBaseDTO dto = new JsonBaseDTO(); 
		fillEnte( dto );
		
		//JSON 
		dto.setDiarioId(getDiarioId());
		dto.setDiarioPadreId(getDiarioPadreId());
		dto.setVersione(classVersion);
		dto.setDescrizione(getDescrizioneScheda());
		dto.setJsonString( jsonString );
		
		//Valorizzo i dati del diario
		
		dto.setTipoDiarioId(getTipoDiarioId());
		dto.setCasoId(getCasoId());
		dto.setSchedaUdcId(getUdcId());
		dto.setOpSettore( getOperatoreSettore() );
		dto.setDtAmministrativa(getDtAmministrativa());
		dto.setDtSospensioneDa(getDtSospensioneDa());
		dto.setDtSospensioneA(getDtSospensioneA());
		dto.setDtAttivazioneDa(getDtAttivazioneDa());
		dto.setDtAttivazioneA(getDtAttivazioneA());
		dto.setDtChiusuraDa(getDtChiusuraDa());
		dto.setDtChiusuraA(getDtChiusuraA());
		
	    //SISO-812 
		dto.setVisSecLivello(getVisSecondoLivello());
		
		CsDValutazione valSalvata = diarioService.saveSchedaJson(dto);
		loadData(valSalvata);
		
		
	}

	public CsOOperatoreSettore getOperatoreSettore() {
		return operatoreSettore;
	}

	public void setOperatoreSettore(CsOOperatoreSettore operatoreSettore) {
		this.operatoreSettore = operatoreSettore;
	}
	
	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getUdcId() {
		return udcId;
	}

	public void setUdcId(Long udcId) {
		this.udcId = udcId;
	}
	
	public CsDValutazione getDataModel(){
		return this.dataModel;
	}

	public Long getVisSecondoLivello() {
		return visSecondoLivello;
	}

	public void setVisSecondoLivello(Long visSecondoLivello) {
		this.visSecondoLivello = visSecondoLivello;
	}

}
