package it.webred.cs.json.isee.ver2;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.json.controller.JsonController;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class IseeController extends JsonController {

	private static final long serialVersionUID = 1L;

	private IseeBean jsonOriginal;
	private IseeBean jsonCurrent;

	public IseeController() {
		jsonCurrent = new IseeBean();
	}
	
	public List<String> validaData() {
		return jsonCurrent.checkObbligatorieta();
	}


/*	public void loadData( CsDValutazione schedaMultidim, CsDValutazione schedaBarthel) throws Exception {
		valutazioneSchedaMultidim = schedaMultidim;
		valutazioneSchedaBarthel = schedaBarthel;
		
		try {
			if(valutazioneSchedaBarthel != null) {
				CsDClob clob = valutazioneSchedaBarthel.getCsDDiario().getCsDClob();
				if( StringUtils.isNotEmpty(clob.getDatiClob()) ) {
					jsonOriginal = (JsonBarthelBean)mapJsonToBean(clob.getDatiClob(), JsonBarthelBean.class);
					
					ObjectMapper objectMapper = new ObjectMapper();
					jsonOriginal = objectMapper.readValue(clob.getDatiClob(), JsonBarthelBean.class);
				}
			}
		}
		catch (Exception e) {
			jsonOriginal = null;
			throw e;
		}

		restore();
	}*/
	
	public void loadData( CsDValutazione padre, CsDValutazione isee) throws Exception {
		dataModelPadre = padre;
		super.loadData(isee);
	}

	@Override
	public void restore() {
		if(jsonOriginal!=null){
			try{
				jsonCurrent = (IseeBean)BeanUtils.cloneBean(jsonOriginal);
				} 
			catch (Exception ex) {
				logger.error(ex);
				throw new Error( ex );
			}
		}else
			jsonCurrent = new IseeBean();
	}

/*	public void save() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(jsonCurrent);
		String jsonString = this.mapBeanToJson(jsonCurrent);

		SchedaBarthelDTO schedaBarthelDTO = new SchedaBarthelDTO(); 
		fillEnte( schedaBarthelDTO );

		//JSON 
		schedaBarthelDTO.setDiarioId( jsonOriginal != null ? valutazioneSchedaBarthel.getDiarioId() : null );
		schedaBarthelDTO.setDiarioPadreId( valutazioneSchedaMultidim.getDiarioId() );
		schedaBarthelDTO.setCasoId(valutazioneSchedaMultidim.getCsDDiario().getCsACaso().getId());
		schedaBarthelDTO.setJsonString( jsonString );
		schedaBarthelDTO.setOpSettore( this.getOperatoreSettore() );
		schedaBarthelDTO.setDataAmministrativa( jsonCurrent.patologiePsichiatricheData.dataDiValutazione );
		schedaBarthelDTO.setVersione(getVersione());
		schedaBarthelDTO.setDescrizione(this.getDescrizioneScheda());
		diarioService.saveSchedaBarthel( schedaBarthelDTO );
	}*/

	
	@Override
	public IseeBean getJsonCurrent() {
		return jsonCurrent;
	}

	@Override
	public Long getDiarioId() {
		return (jsonOriginal != null && dataModel!=null) ? dataModel.getDiarioId() : null;
	}

	@Override
	public Long getDiarioPadreId() {
		return  dataModelPadre!=null ? dataModelPadre.getDiarioId() : null;
	}

	@Override
	public Date getDtAmministrativa() {
		return  jsonCurrent.getDataIsee();
	}


	@Override
	public <T extends JsonBaseBean> Class<T> getClasseBean() {
		return  (Class<T>)IseeBean.class;
	}

	@Override
	public void assignJsonOriginal(JsonBaseBean jsonOriginal) {
		this.jsonOriginal = (IseeBean)jsonOriginal;
	}

	@Override
	public Date getDtChiusuraDa() {
		return jsonCurrent.getDataScadenzaIsee();
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

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.ISEE_ID;
	}

	@Override
	public String getDescrizioneScheda() {
		return "scheda ISEE";
	}

}
