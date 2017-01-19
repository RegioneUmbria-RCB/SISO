package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.controller.JsonController;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Date;
import java.util.List;

public class SchedaBarthelController extends JsonController {

	private static final long serialVersionUID = 1L;

	private JsonBarthelBean jsonOriginal;
	private JsonBarthelBean jsonCurrent;

	
	public List<String> validaBarthelData() {
		return jsonCurrent.getMainData().checkObbligatorieta();
	}

	public List<String> validaIADLData() {
		return jsonCurrent.getIadlData().checkObbligatorieta();
	}

	public List<String> validaPatologiePsichiatricheData() {
		return jsonCurrent.getPatologiePsichiatricheData().checkObbligatorieta();
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
	
	public void loadData( CsDValutazione schedaMultidim, CsDValutazione schedaBarthel) throws Exception {
		dataModelPadre = schedaMultidim;
		super.loadData(schedaBarthel);
	}

	@Override
	public void restore() {
		jsonCurrent = new JsonBarthelBean( jsonOriginal );
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

	public void calcolaPunteggioTotaleBarthel() {
		jsonCurrent.getMainData().calcolaPunteggioTotale();
	}

	public void calcolaPunteggioTotaleIADL() {
		jsonCurrent.getIadlData().calcolaPunteggioTotale();
	}

	@Override
	public JsonBarthelBean getJsonCurrent() {
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
		return  jsonCurrent.patologiePsichiatricheData.dataDiValutazione;
	}


	@Override
	public <T extends JsonBaseBean> Class<T> getClasseBean() {
		return  (Class<T>)JsonBarthelBean.class;
	}

	@Override
	public void assignJsonOriginal(JsonBaseBean jsonOriginal) {
		this.jsonOriginal = (JsonBarthelBean)jsonOriginal;
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.BARTHEL_ID;
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


	@Override
	public String getDescrizioneScheda() {
		return "descrizioneSchedaBarthel";
	}

}
