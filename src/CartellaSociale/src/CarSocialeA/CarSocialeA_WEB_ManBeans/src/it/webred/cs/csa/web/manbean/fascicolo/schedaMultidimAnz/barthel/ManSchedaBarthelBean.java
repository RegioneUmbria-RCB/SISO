package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.web.manbean.report.dto.schedaMultidimensionale.BarthelPdfDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.dto.JsonBaseBean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ManSchedaBarthelBean extends SchedaValutazioneManBean implements ISchedaBarthel, Serializable {

	private static final long serialVersionUID = 1L;

	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	
	JsonBarthelBean jsonOriginal;
	JsonBarthelBean jsonCurrent;
	
	private CsDValutazione valutazioneSchedaMultidim;
	private CsDValutazione valutazioneSchedaBarthel;
	
	public ManSchedaBarthelBean(){
	}
	
	public boolean validaData(){
		return true;
	}
	
	@Override
	public boolean save() {
		boolean ok = false;
		try {
				//ora salva
				addInfoFromProperties( "salva.ok" );
		} 
		catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		return false;
	}

	@Override
	public void restore() {

	}

	@Override
	public void init(CsDValutazione schedaMultidim, CsDValutazione schedaBarthel) {
		// TODO Auto-generated method stub
		jsonOriginal = null;
		jsonCurrent = new JsonBarthelBean(jsonOriginal);
		
	}
	
	@Override
	public void init(ISchedaValutazione bean) {}
	
	public JsonBarthelBean getJsonCurrent() {
		return jsonCurrent;
	}

	@Override
	public JsonBaseBean getSelected() {
		return this.jsonCurrent;
	}

	@Override
	public boolean elimina(){
	    boolean ok = false; 
		//TODO
	    return ok;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void setIdSchedaUdc(Long idUdc) {
		
	}

	@Override
	public CsDValutazione getCurrentModel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getIdSchedaUdc() {
		return null;
	}

	@Override
	public void fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map){
		JsonBarthelBean bean = getJsonCurrent();
		
		BarthelPdfDTO pdf = new BarthelPdfDTO();
		//TODO
		listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/barthel.jrxml");
		map.put("barthel", new JRBeanCollectionDataSource(Arrays.asList(pdf)));

		
	}

}
