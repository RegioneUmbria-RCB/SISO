package it.webred.ct.service.indice.web.civico;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.civico.dto.ListaCiviciByVia;
import it.webred.ct.data.access.basic.indice.civico.dto.SitCivicoDTO;

public class CivicoDataProviderImpl extends CivicoBaseBean implements
		CivicoDataProvider {

	private String idViaUnico;
	
	public String getIdViaUnico() {
		return idViaUnico;
	}

	public void setIdViaUnico(String idViaUnico) {
		this.idViaUnico = idViaUnico;
	}

	public List<SitCivicoDTO> getListaCiviciByVia(int start, int rowNumber) {

		if (idViaUnico != null) {
			try {
				//List<SitCivicoDTO> result = civicoService.getListaCiviciByVia(start, rowNumber, idViaUnico);
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaCiviciByVia lcv = new ListaCiviciByVia();
				lcv.setStart(start);
				lcv.setRowNumber(rowNumber);
				lcv.setId(idViaUnico);
				indDataIn.setListaCiviciByVia(lcv);
				
				List<SitCivicoDTO> result = civicoService.getListaCiviciByVia(indDataIn);
				return result;
			} catch (Throwable t) {
				super.addErrorMessage("listaunici.error", t.getMessage());
				t.printStackTrace();

			}
		}
		return new ArrayList<SitCivicoDTO>();

	}

	public long getListaCiviciByViaRecordCount() {

		if (idViaUnico != null) {
			try {

				IndiceDataIn indDataIn = new IndiceDataIn();
				indDataIn.setObj(idViaUnico);
				
				return civicoService.getListaCiviciByViaRecordCount(indDataIn);
				
				//return civicoService.getListaCiviciByViaRecordCount(idViaUnico);

			} catch (Throwable t) {
				super.addErrorMessage("listaunici.error", t.getMessage());
				t.printStackTrace();
			}
		}
		return 0L;

	}

	public void resetData() {

	}

}
