package it.umbriadigitale.soclav.service.interfaccia;

import java.util.List;

import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLav;
import it.umbriadigitale.soclav.service.dto.CartellaSocialeDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiDomandaDTO;

public interface IAmbitiSocialeService {

	public List<GePiDomandaDTO> search(List<String> entiAbilitati, int first, int size);
	public Integer count(List<String> entiAbilitati, int first, int size);
	public RdcConsensiSocToLav salvaConsenso(String cf, Boolean val, String enteFrom);
	public boolean canViewDatiLavoro(String cf, String ente);
	public List<CartellaSocialeDTO> findCartelleSociali(String cf, String ente);
	
	public List<String> loadListaProtocolli();
}
