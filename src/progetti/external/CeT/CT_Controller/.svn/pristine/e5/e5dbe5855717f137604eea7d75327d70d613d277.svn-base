package it.webred.ct.rulengine.web.bean.cataloghi;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.InfoCatDTO;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class InfoCatalogoBean extends CataloghiBaseBean {

	private static Logger logger = Logger.getLogger(InfoCatalogoBean.class.getName());

	private String enteSelezionato;
	private String layerSelezionato;
	private List<InfoCatDTO> listaColonne = new ArrayList<InfoCatDTO>();
	private String idLayerSel;
	private String idLayerStandard;

	public void initPanel() {
			
		listaColonne = new ArrayList<InfoCatDTO>();
		
		CataloghiDataIn dataIn = new CataloghiDataIn();
		dataIn.setEnteId(enteSelezionato);
		dataIn.setTable(layerSelezionato);
		List<String> lista = pgtService.getListaColonne(dataIn);
		
		PgtSqlLayerPK pk = new PgtSqlLayerPK();
		pk.setId(idLayerSel!=null ? new Long(idLayerSel):null);
		pk.setStandard(idLayerStandard);
		
		for(String col: lista){
			dataIn.setColonnaTema(col);
			PgtSqlInfoLayer pgtInfo = pgtService.getInfoLayerByCol(dataIn);
			InfoCatDTO dto = new InfoCatDTO();
			if(pgtInfo != null){
				dto.setVisibile(true);
				dto.setAlias(pgtInfo.getNameAlias());
			}
			dto.setColonna(col);
			listaColonne.add(dto);
		}
		
	}

	public void doSaveInfo() {
		
		String message = "cataloghi.info.save";

		try {

			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(enteSelezionato);
			
			List<PgtSqlInfoLayer> listInfoLayer = new ArrayList<PgtSqlInfoLayer>();
			for (InfoCatDTO dto : listaColonne) {
				if(dto.isVisibile()){
					PgtSqlInfoLayer pgt = new PgtSqlInfoLayer();
					pgt.setIdLayer(new Long(idLayerSel));
					pgt.setStandard(idLayerStandard);
					pgt.setNameCol(dto.getColonna());
					pgt.setNameAlias(dto.getAlias());
					listInfoLayer.add(pgt);
				}
			}
			dataIn.setListPgtSqlInfoLayer(listInfoLayer);
			pgtService.mergeInfoLayer(dataIn);

			super.addInfoMessage(message);

		} catch (Throwable t) {
			super.addErrorMessage(message + ".error", t.getMessage());
			logger.error("Errore save temi",t);

		}

	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public List<InfoCatDTO> getListaColonne() {
		return listaColonne;
	}

	public void setListaColonne(List<InfoCatDTO> listaColonne) {
		this.listaColonne = listaColonne;
	}

	public String getIdLayerSel() {
		return idLayerSel;
	}

	public void setIdLayerSel(String idLayerSel) {
		this.idLayerSel = idLayerSel;
	}

	public String getIdLayerStandard() {
		return idLayerStandard;
	}

	public void setIdLayerStandard(String idLayerStandard) {
		this.idLayerStandard = idLayerStandard;
	}

	public String getLayerSelezionato() {
		return layerSelezionato;
	}

	public void setLayerSelezionato(String layerSelezionato) {
		this.layerSelezionato = layerSelezionato;
	}

}
