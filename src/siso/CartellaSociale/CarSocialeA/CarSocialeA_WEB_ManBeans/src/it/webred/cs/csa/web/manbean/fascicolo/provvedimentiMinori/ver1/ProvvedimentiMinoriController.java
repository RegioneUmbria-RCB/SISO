package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

public class ProvvedimentiMinoriController extends JsonExtController<ProvvedimentiMinoriBean> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescrizioneScheda() {
		return "Provvedimenti minori";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE;
	}

//extends JsonController {

//	private static final long serialVersionUID = 1L;
//
//	private ProvvedimentiMinoriBean jsonOriginal;
//	private ProvvedimentiMinoriBean jsonCurrent;
//
//
//	public void loadData(CsDValutazione padre, CsDValutazione scheda) throws Exception {
//		dataModelPadre = padre;
//		super.loadData(scheda);
//	}
//
//	@Override
//	public void restore() {
//		jsonCurrent = new ProvvedimentiMinoriBean(jsonOriginal);
//	}
//
//
//	@Override
//	public ProvvedimentiMinoriBean getJsonCurrent() {
//		return jsonCurrent;
//	}
//
//	@Override
//	public Long getDiarioId() {
//		return (jsonOriginal != null && dataModel != null) ? dataModel.getDiarioId() : null;
//	}
//
//	@Override
//	public Long getDiarioPadreId() {
//		return dataModelPadre != null ? dataModelPadre.getDiarioId() : null;
//	}
//
//	@Override
//	public void assignJsonOriginal(JsonBaseBean jsonOriginal) {
//		this.jsonOriginal = (ProvvedimentiMinoriBean) jsonOriginal;
//	}
//
//	@Override
//	public int getTipoDiarioId() {
//		return DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE;
//	}
//
//
//	@Override
//	public String getDescrizioneScheda() {
//		return "Provvedimenti Minori";
//	}
//
//	@Override
//	public <T extends JsonBaseBean> Class<T> getClasseBean() {
//		
//		return  (Class<T>)ProvvedimentiMinoriBean.class;
//	}
//
//	@Override
//	public Date getDtAmministrativa() {
//		
//		return null;
//	}
//	@Override
//	public Date getDtChiusuraDa() {
//		return null;
//	}
//
//	@Override
//	public Date getDtChiusuraA() {
//		return null;
//	}
//
//	@Override
//	public Date getDtAttivazioneDa() {
//		return null;
//	}
//
//	@Override
//	public Date getDtAttivazioneA() {
//		return null;
//	}
//
//	@Override
//	public Date getDtSospensioneDa() {
//		return null;
//	}
//
//	@Override
//	public Date getDtSospensioneA() {
//		return null;
//	}


}
