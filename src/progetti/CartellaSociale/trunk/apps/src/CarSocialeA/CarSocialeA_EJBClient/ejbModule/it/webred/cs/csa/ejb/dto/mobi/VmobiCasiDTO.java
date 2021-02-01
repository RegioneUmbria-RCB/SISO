package it.webred.cs.csa.ejb.dto.mobi;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.webred.cs.csa.ejb.dto.CsRelRelazioneProblDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.view.VMobiCasi;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VmobiCasiDTO extends VMobiCasi{

	
	
	private List<DiarioAnagraficaDTO> anagraficheSoggettiInteressati ;
	private List<CsRelRelazioneProblDTO> problematichePrecedenti;
	
	
	public void setByVmobiCaso(VMobiCasi caso)
	{
		
		this.setCasoId(caso.getCasoId());
		this.setFlagResponsabile(caso.getFlagResponsabile());
		this.setOpeCognome(caso.getOpeCognome());
		this.setOpeNome(caso.getOpeCognome());
		this.setOperatoreId(caso.getOperatoreId());
		this.setOrgBelfiore(caso.getOrgBelfiore());
		this.setOrgNome(caso.getOpeNome());
		this.setSettoreId(caso.getSettoreId());
		this.setSettoreNome(caso.getSettoreNome());
		this.setSoggCell(caso.getSoggCell());
		this.setSoggCf(caso.getSoggCf());
		this.setSoggCittadinanza(caso.getSoggCittadinanza());
		this.setSoggCognome(caso.getSoggCognome());
		this.setSoggEmail(caso.getSoggEmail());
		this.setSoggNome(caso.getSoggNome());
		this.setSoggSesso(caso.getSoggSesso());
		this.setSoggTel(caso.getSoggTel());
		this.setUsername(caso.getUsername());
this.anagraficheSoggettiInteressati=new ArrayList<DiarioAnagraficaDTO>();
this.problematichePrecedenti= new ArrayList<CsRelRelazioneProblDTO>();
		
	}
	
	public List<DiarioAnagraficaDTO> getAnagraficheSoggettiInteressati() {
		return anagraficheSoggettiInteressati;
	}
	public void setAnagraficheSoggettiInteressati(
			List<DiarioAnagraficaDTO> anagraficheSoggettiInteressati) {
		this.anagraficheSoggettiInteressati = anagraficheSoggettiInteressati;
	}
	public List<CsRelRelazioneProblDTO> getProblematichePrecedenti() {
		return problematichePrecedenti;
	}
	public void setProblematichePrecedenti(
			List<CsRelRelazioneProblDTO> problematichePrecedenti) {
		this.problematichePrecedenti = problematichePrecedenti;
	}
	
	
}
