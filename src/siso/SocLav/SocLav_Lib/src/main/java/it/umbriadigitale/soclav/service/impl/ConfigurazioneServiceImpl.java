package it.umbriadigitale.soclav.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdCUserCpi;
import it.umbriadigitale.soclav.model.RdCUserCpiPK;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;
import it.umbriadigitale.soclav.repository.RdCTbCpiCompetenzaRepository;
import it.umbriadigitale.soclav.repository.RdCTbCpiRepository;
import it.umbriadigitale.soclav.repository.RdCUserCpiRepository;
import it.umbriadigitale.soclav.service.interfaccia.IConfigurazioneService;

@Component("configurazioneService")
public class ConfigurazioneServiceImpl implements IConfigurazioneService {

	@Autowired
	@Qualifier("rdcTbCpiRepository")
	private RdCTbCpiRepository tbCpiRepository;
	
	@Autowired
	@Qualifier("rdcUserCpiRepository")
	private RdCUserCpiRepository rdcUserCpiRepository;
	
	@Autowired
	@Qualifier("rdcTbCpiCompetenzaRepository")
	private RdCTbCpiCompetenzaRepository tbCpiCompetenzaRepository;

	@Autowired
	@Qualifier("rdcUserCpiRepository")
	private RdCUserCpiRepository userCpiRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public List<RdCTbCpi> getListaCPI(Map filters) {
		String regione = (String)filters.get("regione");
		String provincia = (String)filters.get("provincia");
		List<RdCTbCpi> utenti;
		if(StringUtils.isEmpty(provincia))
			utenti = tbCpiCompetenzaRepository.findListaOrdinataRegione(regione);
		else
			utenti = tbCpiCompetenzaRepository.findListaOrdinataProvincia(provincia);
		
		return utenti;
	}


	@Override
	public List<String> getListaRegioni() {
		return tbCpiCompetenzaRepository.getListaRegioni();
	}
	
	@Override
	public List<String> getListaProv(String regione) {
		return tbCpiCompetenzaRepository.getListaProv(regione);
	}
	
	@Override
	public void associaCPIUtente(String username, List<RdCTbCpi> codiciCPI){
		if(codiciCPI.isEmpty()) {
			return;
		}
			
			
		for(RdCTbCpi cod : codiciCPI) {
			RdCUserCpi uc = new RdCUserCpi();
			RdCUserCpiPK pk = new RdCUserCpiPK();
			pk.setCodCpi(cod.getCodice());
			pk.setUsername(username);
			uc.setId(pk);
			uc.setAbilitato(Boolean.TRUE);
			
			this.userCpiRepository.save(uc);			
		}
	}


	@Override
	public RdCTbCpi findRdCTbCpi(String arg) {
		return this.tbCpiRepository.findOne(arg);
	}


	@Override
	public void abilita(List<RdCUserCpi> lista) {
		for(RdCUserCpi u : lista) {
			u.setAbilitato(Boolean.TRUE);
			rdcUserCpiRepository.save(u);
		}	
	}


	@Override
	public void disabilita(List<RdCUserCpi> lista) {
		for(RdCUserCpi u : lista) {
			u.setAbilitato(Boolean.FALSE);
			rdcUserCpiRepository.save(u);
		}	
	}


	@Override
	public void elimina(List<RdCUserCpi> lista) {
		for(RdCUserCpi u : lista)
			rdcUserCpiRepository.delete(u);
		
	}


}
