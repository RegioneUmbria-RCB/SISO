package it.webred.ct.data.access.basic.compravendite;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.compravendite.CompravenditeMUIService;
import it.webred.ct.data.access.basic.compravendite.dao.CompravenditeMuiDAO;
import it.webred.ct.data.access.basic.compravendite.dto.RicercaCompravenditeDTO;
import it.webred.ct.data.access.basic.compravendite.dto.SoggettoCompravenditeDTO;
import it.webred.ct.data.model.compravendite.MuiFabbricatiIdentifica;
import it.webred.ct.data.model.compravendite.MuiNotaTras;

@Stateless 
public class CompravenditeMUIServiceBean extends CTServiceBaseBean implements CompravenditeMUIService {
	@Autowired
	private CompravenditeMuiDAO compravenditeMuiDAO;
	private static final long serialVersionUID = 1L;
	
	public List<MuiNotaTras>  getListaNoteByFPS(RicercaOggettoCatDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaNoteByFPS(rc);
	}

	@Override
	public List<MuiNotaTras> getListaNoteTerrenoByFP(RicercaOggettoCatDTO rc)	throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaNoteTerrenoByFP(rc);
	}
	
	@Override
	public List<SoggettoCompravenditeDTO> getListaSoggettiNota(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
			return compravenditeMuiDAO.getListaSoggettiNota(rc);
	}
	
	@Override
	public List<MuiNotaTras>  getListaNoteByFP(RicercaOggettoCatDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaNoteByFP(rc);
	}

	@Override
	public List<MuiFabbricatiIdentifica> getListaUIByNotaFabb(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaUIByNotaFabbr(rc);
	}
	
	//GITOUT WS3
	@Override
	public List<MuiNotaTras> getListaNoteByCriteria(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaNoteByCriteria(rc);
	}
	
	@Override
	public List<Object[]> getFabbricatiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getFabbricatiByParams(rc);
	}
	
	@Override
	public List<Object[]> getTerreniByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getTerreniByParams(rc);
	}

	@Override
	public List<Object[]> getSoggettiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getSoggettiByParams(rc);
	}

	@Override
	public List<MuiNotaTras>  getListaNoteByCoord(RicercaOggettoCatDTO rc) throws CompravenditeMUIException {
		return compravenditeMuiDAO.getListaNoteByCoord(rc);
	}
	
	
	
}
