package it.webred.ct.data.access.basic.compravendite.dao;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.compravendite.CompravenditeMUIException;
import it.webred.ct.data.access.basic.compravendite.dto.RicercaCompravenditeDTO;
import it.webred.ct.data.access.basic.compravendite.dto.SoggettoCompravenditeDTO;
import it.webred.ct.data.model.compravendite.MuiFabbricatiIdentifica;
import it.webred.ct.data.model.compravendite.MuiNotaTras;

import java.util.List;

public interface CompravenditeMuiDAO {
	public List<MuiNotaTras>  getListaNoteByFPS(RicercaOggettoCatDTO rc) throws CompravenditeMUIException;
	public List<MuiNotaTras>  getListaNoteTerrenoByFP(RicercaOggettoCatDTO rc) throws CompravenditeMUIException;
	public List<SoggettoCompravenditeDTO> getListaSoggettiNota(RicercaCompravenditeDTO rc) throws CompravenditeMUIException ;
	public List<MuiNotaTras> getListaNoteByFP(RicercaOggettoCatDTO rc)		throws CompravenditeMUIException;
	public List<MuiFabbricatiIdentifica> getListaUIByNotaFabbr(RicercaCompravenditeDTO rc)		throws CompravenditeMUIException;
	//GITOUT WS3
	public List<MuiNotaTras> getListaNoteByCriteria(RicercaCompravenditeDTO rc) throws CompravenditeMUIException;
	public List<Object[]> getFabbricatiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException;
	public List<Object[]> getTerreniByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException;
	public List<Object[]> getSoggettiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException;
	public List<MuiNotaTras> getListaNoteByCoord(RicercaOggettoCatDTO rc) throws CompravenditeMUIException;
	
}
