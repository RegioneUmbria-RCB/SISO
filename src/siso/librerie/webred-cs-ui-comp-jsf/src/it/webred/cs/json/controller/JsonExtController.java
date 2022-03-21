package it.webred.cs.json.controller;

import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.dto.JsonBaseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public abstract class JsonExtController<TJsonBean extends JsonBaseBean> extends JsonController {

	private static final long serialVersionUID = 1L;

	private TJsonBean jsonOriginal;
	private TJsonBean jsonCurrent;
	
	public void load(TJsonBean json) throws Exception{
		TJsonBean jclone = (TJsonBean)json.autoClone();
		assignJsonOriginal(jclone);
		restore();
	}
	
	public void copyDataBetweenVersions(JsonBaseBean orig) throws Exception{
		TJsonBean  jclone = this.getJsonCurrent();
		BeanUtils.copyProperties(jclone, orig);
		assignJsonOriginal(jclone);
		restore();
	}


	@SuppressWarnings("unchecked")
	public Class<TJsonBean> getTypeParameterClass()
	{
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<TJsonBean>) paramType.getActualTypeArguments()[0];
	}

	protected TJsonBean getNewJsonBeanInstance()
	{

		TJsonBean instance;
		try {
			instance = getTypeParameterClass().newInstance();
			return instance;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public JsonExtController() {
		//descrizioneScheda = getDescrizioneScheda();
		//tipoDiarioId = getTipoDiarioId();
		jsonCurrent = getNewJsonBeanInstance();
	}


	public List<String> validaData() {
		return jsonCurrent.checkObbligatorieta();
	}

	public void loadData(CsDValutazione padre, CsDValutazione figlio) throws Exception {
		dataModelPadre = padre;
		super.loadData(figlio);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void restore() {
		if (jsonOriginal != null) {
			try {
				//jsonCurrent = (TJsonBean) BeanUtils.cloneBean(jsonOriginal);
				//L'implementazione del metodo nei singoli bean permette di passare dalla shallow alla deep copy quando ci sono oggetti complessi
				jsonCurrent = (TJsonBean) jsonOriginal.autoClone(); 
				
			} catch (Exception ex) {
				logger.error(ex);
				throw new Error(ex);
			}
		} else
			jsonCurrent = getNewJsonBeanInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsonBaseBean> T getJsonCurrent() {
		return (T) jsonCurrent;
	}

	@Override
	public Long getDiarioId() {
		return (jsonOriginal != null && dataModel != null) ? dataModel.getDiarioId() : null;
	}

	@Override
	public Long getDiarioPadreId() {
		return dataModelPadre != null ? dataModelPadre.getDiarioId() : null;
	}

	@Override
	public Date getDtAmministrativa() {
		if(this.getDiarioId()==null) return new Date();
		else return this.dataModel.getCsDDiario().getDtAmministrativa();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsonBaseBean> Class<T> getClasseBean() {
		return (Class<T>) getTypeParameterClass();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assignJsonOriginal(JsonBaseBean jsonOriginal) {
		this.jsonOriginal = (TJsonBean) jsonOriginal;
	}

	@Override
	public Date getDtChiusuraDa() {
		return null;
	}

	@Override
	public Date getDtChiusuraA() {
		return null;
	}

	@Override
	public Date getDtAttivazioneDa() {
		return null;
	}

	@Override
	public Date getDtAttivazioneA() {
		return null;
	}

	@Override
	public Date getDtSospensioneDa() {
		return null;
	}

	@Override
	public Date getDtSospensioneA() {
		return null;
	}

}
