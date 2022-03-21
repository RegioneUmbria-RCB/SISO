package it.webred.ct.data.access.basic.acqua;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.acqua.dao.AcquaDAO;
import it.webred.ct.data.access.basic.acqua.dto.AcquaUtenzeDTO;
import it.webred.ct.data.model.acqua.SitAcquaUtente;
import it.webred.ct.data.model.acqua.SitAcquaUtenze;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AcquaServiceBean extends CTServiceBaseBean implements AcquaService {

	@Autowired
	private AcquaDAO acquaDAO;

	@Override
	public List<SitAcquaUtente> getListaUtenteByCodFisPIva(AcquaDataIn dataIn)
			throws AcquaServiceException {
		
		List<SitAcquaUtente> listaUtente = new ArrayList<SitAcquaUtente>();

		listaUtente.addAll(acquaDAO
				.getListaUtenteByCodFis(dataIn.getCodFiscale()));
		listaUtente.addAll(acquaDAO
				.getListaUtenteByPIva(dataIn.getCodFiscale()));
		
		return listaUtente;
	}
	
	@Override
	public List<AcquaUtenzeDTO> getListaUtenzeByCodFisPIva(AcquaDataIn dataIn)
			throws AcquaServiceException {

		List<AcquaUtenzeDTO> listaUtenzeDto = new ArrayList<AcquaUtenzeDTO>();

		List<SitAcquaUtenze> listaUtenze = acquaDAO
				.getListaUtenzeByCodFis(dataIn.getCodFiscale());
		listaUtenze.addAll(acquaDAO
				.getListaUtenzeByPIva(dataIn.getCodFiscale()));
		for (SitAcquaUtenze u : listaUtenze) {

			AcquaUtenzeDTO dto = new AcquaUtenzeDTO();
			dto.setSitAcquaUtenze(u);
			dto.setListaSitAcquaCatasto(acquaDAO.getListaCatastoByCodServizio(u
					.getCodServizio()));
			String consumo = u.getConsumoMedio() != null ? u.getConsumoMedio()
					.toString() : u.getStacco() + "/" + u.getGiro().toString();
			dto.setConsumo(consumo);
			listaUtenzeDto.add(dto);

		}

		return listaUtenzeDto;
	}

}
