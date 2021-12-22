package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.json.controller.JsonExtController;

import java.util.Date;

public class ValSinbaController extends JsonExtController<ValSinbaBean> {

	private static final long serialVersionUID = 1L;
	protected CsDSinba dataModelSinba;
	
	protected AccessTableSinbaSessionBeanRemote sinbaService = (AccessTableSinbaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSinbaSessionBean");;

	public void loadData(CsDSinba scheda) throws Exception {
		dataModelSinba=scheda;
		super.loadData(scheda.getCsDValutazione());
		restore();
	}
	
	@Override
	public String getDescrizioneScheda() { //Scheda SINBA
		return "Scheda SINBA";
	}

	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA;
	}
	
	@Override
	public Date getDtAmministrativa() {
		return ((ValSinbaBean)getJsonCurrent()).getDatiGenerali().getDataRiferimentoValutazione();
	}

	@Override
	public void save(String classVersion) throws Exception {

		super.save(classVersion);
		
		if(this.getDataModelSinba()==null || this.getDataModelSinba().getCsDValutazione()==null){
			//TODO: salvare CsDSinba - dati esportazione - vuoti se nuovo
			dataModelSinba = new CsDSinba();
			dataModelSinba.setCsDValutazione(this.dataModel);
			dataModelSinba.setDiarioId(this.getDataModel().getDiarioId());
			
			BaseDTO dto = new BaseDTO();
			this.fillEnte(dto);
			dto.setObj(dataModelSinba);
			sinbaService.salvaSchedaSinba(dto);
			
		}
		
	}

	public CsDSinba getDataModelSinba() {
		return dataModelSinba;
	}

	public void setDataModelSinba(CsDSinba dataModelSinba) {
		this.dataModelSinba = dataModelSinba;
	}

}
