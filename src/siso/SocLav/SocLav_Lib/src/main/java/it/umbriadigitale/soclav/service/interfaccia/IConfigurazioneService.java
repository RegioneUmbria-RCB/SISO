package it.umbriadigitale.soclav.service.interfaccia;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import it.umbriadigitale.soclav.model.RdCUserCpi;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;

public interface IConfigurazioneService {

	public List<RdCTbCpi> getListaCPI(Map filters);
	public void associaCPIUtente(String username, List<RdCTbCpi> codiciCPI);
	public List<String> getListaProv(String regione);
	public List<String> getListaRegioni();
	public RdCTbCpi findRdCTbCpi(String arg);
	public void abilita(List<RdCUserCpi> selectedCPIUtente);
	public void disabilita(List<RdCUserCpi> selectedCPIUtente);
	public void elimina(List<RdCUserCpi> selectedCPIUtente);
}
