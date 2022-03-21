package it.webred.cs.json.barthel.ver1;

import java.util.ArrayList;
import java.util.List;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.jsf.bean.ComuneBean;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBarthelBean extends JsonBaseBean {

	private static final long serialVersionUID = 1L;
	
	protected JsonBarthelMainDataBean mainData;
	protected JsonBarthelIADLDataBean iadlData;
	protected JsonBarthelPatologiePsichiatricheDataBean patologiePsichiatricheData;
	
	public JsonBarthelBean() {
		mainData = new JsonBarthelMainDataBean();
		iadlData = new JsonBarthelIADLDataBean();
		patologiePsichiatricheData = new JsonBarthelPatologiePsichiatricheDataBean();
	}

	public JsonBarthelBean(JsonBarthelBean jsonOriginal) {
	
		try {
			if( jsonOriginal != null )  {
				mainData = (JsonBarthelMainDataBean) BeanUtils.cloneBean( jsonOriginal.getMainData() );
				iadlData = (JsonBarthelIADLDataBean) BeanUtils.cloneBean( jsonOriginal.getIadlData() );
				patologiePsichiatricheData = (JsonBarthelPatologiePsichiatricheDataBean) BeanUtils.cloneBean( jsonOriginal.getPatologiePsichiatricheData() );
			}
			else
			{
				mainData = new JsonBarthelMainDataBean();
				iadlData = new JsonBarthelIADLDataBean();
				patologiePsichiatricheData = new JsonBarthelPatologiePsichiatricheDataBean();
			}

			mainData.calcolaPunteggioTotale();
			iadlData.calcolaPunteggioTotale();
		} 
		catch (Exception ex) {
			logger.error(ex);
			throw new Error( ex );
		}
	}
	
	@Override
	public JsonBarthelBean autoClone() throws Exception{
		JsonBarthelBean clone = (JsonBarthelBean)super.autoClone();
		
		clone.setIadlData((JsonBarthelIADLDataBean ) BeanUtils.cloneBean( this.getIadlData() ));
		clone.setMainData((JsonBarthelMainDataBean) BeanUtils.cloneBean( this.getMainData()));
		clone.setPatologiePsichiatricheData((JsonBarthelPatologiePsichiatricheDataBean) BeanUtils.cloneBean(this.getPatologiePsichiatricheData() ));
		
		clone.getMainData().calcolaPunteggioTotale();
		clone.getIadlData().calcolaPunteggioTotale();
		
		return clone;
	}

	public JsonBarthelMainDataBean getMainData() {
		return mainData;
	}

	public JsonBarthelIADLDataBean getIadlData() {
		return iadlData;
	}

	public JsonBarthelPatologiePsichiatricheDataBean getPatologiePsichiatricheData() {
		return patologiePsichiatricheData;
	}

	@Override
	public List<String> checkObbligatorieta() {
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	public void setMainData(JsonBarthelMainDataBean mainData) {
		this.mainData = mainData;
	}

	public void setIadlData(JsonBarthelIADLDataBean iadlData) {
		this.iadlData = iadlData;
	}

	public void setPatologiePsichiatricheData(
			JsonBarthelPatologiePsichiatricheDataBean patologiePsichiatricheData) {
		this.patologiePsichiatricheData = patologiePsichiatricheData;
	}

}
