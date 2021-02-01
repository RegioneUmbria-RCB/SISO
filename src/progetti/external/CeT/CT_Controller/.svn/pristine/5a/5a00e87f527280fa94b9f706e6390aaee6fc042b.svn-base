package it.webred.ct.rulengine.web.bean.cataloghi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.rulengine.web.bean.abcomandi.AbComandiBean;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class CataloghiBean extends CataloghiBaseBean {

	private static Logger logger = Logger.getLogger(AbComandiBean.class
			.getName());

	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<SelectItem> listaLayer = new ArrayList<SelectItem>();
	private List<PgtSqlLayer> listaPgtSqlLayer = new ArrayList<PgtSqlLayer>();
	private boolean filtroLayerWithoutDeco;
	private String layerSelezionato;
	private PgtSqlLayer pgtSqlLayerSelezionato;
	private String enteSelezionato;
	private List<SelectItem> listaColonneTema = new ArrayList<SelectItem>();
	private String colonnaTemaSelezionata;
	private String colonnaTemaDescrSelezionata;
	private String descrInfoSelezionata;
	private String colonnaInfoSelezionata;
	private List<TemaDTO> listaTemi = new ArrayList<TemaDTO>();
	private boolean codDescrTemaCongruenti=true; 
	private String idLayerSel;
	private String idLayerStandard;
	
	private String index;
	private String tipoColore;
	
	@PostConstruct
	public void init() {
		if (listaEnti.size() == 0) {
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				//seleziono il primo della lista
				if(listaEnti.size() == 0)
					enteSelezionato = comune.getBelfiore();
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune
						.getDescrizione()));
			}
			if (enteSelezionato != null && !enteSelezionato.equals("9999"))
				doCaricaLayer();
		}

	}

	public void doCaricaLayer() {
		codDescrTemaCongruenti=true;
		listaLayer = new ArrayList<SelectItem>();

		if (enteSelezionato != null && !enteSelezionato.equals("9999")) {
			try {

				CataloghiDataIn dataIn = new CataloghiDataIn();
				dataIn.setEnteId(enteSelezionato);
				List<PgtSqlLayer> lista = new ArrayList<PgtSqlLayer>();
				if (filtroLayerWithoutDeco)
					lista = pgtService.getListaLayerWithoutDeco(dataIn);
				else
					lista = pgtService.getListaLayer(dataIn);
				listaPgtSqlLayer=lista;
				
				listaTemi = new ArrayList<TemaDTO>();

				// aggiorno l'ente su shapebean
				ShapeBean sBean = (ShapeBean) getBeanReference("shapeBean");
				sBean.setEnteSelezionato(enteSelezionato);

			} catch (Throwable t) {
				super.addErrorMessage("cataloghi.column.error", t.getMessage());
				logger.error("",t);
			}
		}
	}

	public void doCaricaColonne() {
		codDescrTemaCongruenti=true;
		listaColonneTema = new ArrayList<SelectItem>();

		if (enteSelezionato != null && layerSelezionato != null) {
			try {

				CataloghiDataIn dataIn = new CataloghiDataIn();
				dataIn.setEnteId(enteSelezionato);
				dataIn.setTable(layerSelezionato);

				List<String> lista = pgtService.getListaColonne(dataIn);
				for (String str : lista) {
					listaColonneTema.add(new SelectItem(str, str));
				}

				// se i temi sono già stati salvati li carico
				pgtSqlLayerSelezionato = pgtService
						.getLayerByTable(dataIn);
				logger.debug("doCaricaColonne()-- id-standard: " + pgtSqlLayerSelezionato.getId().getId().toString() + "/" + pgtSqlLayerSelezionato.getId().getStandard());
				logger.debug("doCaricaColonne()--colonnaTema caricata: " + pgtSqlLayerSelezionato.getNameColTema());
				if (pgtSqlLayerSelezionato.getNameColTema() != null) {
					logger.debug("doCaricaColonne()-->cerca listaTemi");
					colonnaTemaSelezionata = pgtSqlLayerSelezionato
							.getNameColTema();
					colonnaTemaDescrSelezionata = pgtSqlLayerSelezionato
							.getNameColTemaDescr();
					
					dataIn.setPkLayer(pgtSqlLayerSelezionato.getId());
					
					dataIn.setDecoExist(true);
					listaTemi = pgtService.getListaTemi(dataIn);
	
				} else {
					listaTemi = new ArrayList<TemaDTO>();
					colonnaTemaSelezionata = "";
					colonnaTemaDescrSelezionata = "";
				}
				if (pgtSqlLayerSelezionato.getNameColInfo() != null) {
					colonnaInfoSelezionata = pgtSqlLayerSelezionato.getNameColInfo();
				}else colonnaInfoSelezionata = "";
				if (pgtSqlLayerSelezionato.getDescrColInfo() != null) {
					descrInfoSelezionata = pgtSqlLayerSelezionato.getDescrColInfo();
				}else descrInfoSelezionata = "";
				
			} catch (Throwable t) {
				super.addErrorMessage("cataloghi.column.error", t.getMessage());
				logger.error("",t);

			}
		}
	}

	public void doCaricaTemi() {
		listaTemi = new ArrayList<TemaDTO>();

		if (colonnaTemaSelezionata != null
				&& !"".equals(colonnaTemaSelezionata)) {
			try {

				CataloghiDataIn dataIn = new CataloghiDataIn();
				dataIn.setEnteId(enteSelezionato);
				dataIn.setTable(layerSelezionato);
				if (!"".equals(colonnaTemaDescrSelezionata))
					dataIn.setDescrTema(colonnaTemaDescrSelezionata);
				else
					dataIn.setDescrTema(null);
				PgtSqlLayer pgtSqlLayer = pgtService.getLayerByTable(dataIn);
	
				dataIn.setPkLayer(pgtSqlLayer.getId());			
				dataIn.setColonnaTema(colonnaTemaSelezionata);
				listaTemi = pgtService.getListaTemi(dataIn);
				codDescrTemaCongruenti= controllaCongruenzaCodDescrTema();

			} catch (Throwable t) {
				super.addErrorMessage("cataloghi.temi.error", t.getMessage());
				logger.error("Errore caricando i temi",t);

			}
		}
	
	}

	public void doSaveTemi() {
		
		String message = "cataloghi.temi.save";

		try {

			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(enteSelezionato);
			
			List<PgtSqlDecoLayer> listDecoLayer = new ArrayList<PgtSqlDecoLayer>();
			for (TemaDTO dto : listaTemi) {
				dto.getPgtSqlDecoLayer().setStandard(idLayerStandard);
				dto.getPgtSqlDecoLayer().setId(null);
				listDecoLayer.add(dto.getPgtSqlDecoLayer());
			}
			dataIn.setListPgtSqlDecoLayer(listDecoLayer);
			pgtService.mergeDecoLayer(dataIn);
				
			// aggiorno PgtSqlLayer
			pgtSqlLayerSelezionato.setNameColTema(colonnaTemaSelezionata);
			pgtSqlLayerSelezionato
					.setNameColTemaDescr(colonnaTemaDescrSelezionata);
			pgtSqlLayerSelezionato.setDescrColInfo(descrInfoSelezionata);
			pgtSqlLayerSelezionato.setNameColInfo(colonnaInfoSelezionata);
			dataIn.setPgtSqlLayer(pgtSqlLayerSelezionato);
			pgtService.mergeLayer(dataIn);

			super.addInfoMessage(message);

		} catch (Throwable t) {
			super.addErrorMessage(message + ".error", t.getMessage());
			logger.error("Errore save temi",t);

		}

	}
	
	public void doCancellaCatalogo() {
		
		String message = "cataloghi.temi.delete";
		
		try{
			RicercaPgtDTO rp = new RicercaPgtDTO();
			rp.setEnteId(enteSelezionato);
			rp.setId(new Long(idLayerSel).longValue());
			rp.setStandard(idLayerStandard);
			pgtSqlLayerSelezionato = pgtService.getLayerByPK(rp);
			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(enteSelezionato);
			dataIn.setPgtSqlLayer(pgtSqlLayerSelezionato);
			pgtService.eliminaLayer(dataIn);	
			//cancellazione tabella 
			if(pgtSqlLayerSelezionato.getNomeFile()!=null){
				String 	sql = "{call UTIL.DROP_TABLE(?)}";
				rp = new RicercaPgtDTO();
				rp.setEnteId(enteSelezionato);
				rp.setStatementSql(sql);
				rp.setNameTable(pgtSqlLayerSelezionato.getNameTable());
				rp.setDropTable(true);
				pgtService.executeStatement(rp);
			}
			//
			pgtSqlLayerSelezionato=new PgtSqlLayer();
			layerSelezionato="";
			idLayerSel="";
			idLayerStandard="";
			colonnaTemaSelezionata="";
			colonnaTemaDescrSelezionata="";
			descrInfoSelezionata="";
			colonnaInfoSelezionata="";
			listaTemi=new ArrayList<TemaDTO>();
			listaPgtSqlLayer = new ArrayList<PgtSqlLayer>();
			doCaricaLayer();
			
			super.addInfoMessage(message);

		} catch (Throwable t) {
			super.addErrorMessage(message + ".error", t.getMessage());
			logger.error("Errore delete temi",t);

		}
	}
	
	private String normalizzaColor(String color) {

		String normalized = color;
		normalized = normalized.replaceFirst("rgb\\(", "");
		normalized = normalized.replaceAll(",", "");
		normalized = normalized.replaceAll("\\)", "");
		return normalized;

	}
	
	public void doSubmit(){
		
	}

	public String goCataloghi() {
		return "controller.cataloghi";
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public boolean isFiltroLayerWithoutDeco() {
		return filtroLayerWithoutDeco;
	}

	public void setFiltroLayerWithoutDeco(boolean filtroLayerWithoutDeco) {
		this.filtroLayerWithoutDeco = filtroLayerWithoutDeco;
	}

	public List<SelectItem> getListaColonneTema() {
		return listaColonneTema;
	}

	public void setListaColonneTema(List<SelectItem> listaColonneTema) {
		this.listaColonneTema = listaColonneTema;
	}

	public String getColonnaTemaSelezionata() {
		return colonnaTemaSelezionata;
	}

	public void setColonnaTemaSelezionata(String colonnaTemaSelezionata) {
		this.colonnaTemaSelezionata = colonnaTemaSelezionata;
	}

	public List<TemaDTO> getListaTemi() {
		return listaTemi;
	}

	public void setListaTemi(List<TemaDTO> listaTemi) {
		this.listaTemi = listaTemi;
	}

	public List<SelectItem> getListaLayer() {
		return listaLayer;
	}

	public void setListaLayer(List<SelectItem> listaLayer) {
		this.listaLayer = listaLayer;
	}

	public String getLayerSelezionato() {
		return layerSelezionato;
	}

	public void setLayerSelezionato(String layerSelezionato) {
		this.layerSelezionato = layerSelezionato;
	}

	public String getColonnaTemaDescrSelezionata() {
		return colonnaTemaDescrSelezionata;
	}

	public void setColonnaTemaDescrSelezionata(
			String colonnaTemaDescrSelezionata) {
		this.colonnaTemaDescrSelezionata = colonnaTemaDescrSelezionata;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public List<PgtSqlLayer> getListaPgtSqlLayer() {
		return listaPgtSqlLayer;
	}

	public void setListaPgtSqlLayer(List<PgtSqlLayer> listaPgtSqlLayer) {
		this.listaPgtSqlLayer = listaPgtSqlLayer;
	}
	
	public boolean controllaCongruenzaCodDescrTema() {
		boolean retVal=true;
		if (pgtSqlLayerSelezionato.getNameColTema()!= null && pgtSqlLayerSelezionato.getNameColTemaDescr() != null) {
			if (! pgtSqlLayerSelezionato.getNameColTema().equalsIgnoreCase(colonnaTemaSelezionata) ||
				! pgtSqlLayerSelezionato.getNameColTemaDescr().equalsIgnoreCase(colonnaTemaDescrSelezionata)) {
					logger.debug("ATTENZIONE: COLONNE INCONGRUENTI");
					retVal=false;
				}
		}
		
		return retVal;
	}

	public boolean isCodDescrTemaCongruenti() {
		return codDescrTemaCongruenti;
	}

	public void setCodDescrTemaCongruenti(boolean codDescrTemaCongruenti) {
		this.codDescrTemaCongruenti = codDescrTemaCongruenti;
	}

	public PgtSqlLayer getPgtSqlLayerSelezionato() {
		return pgtSqlLayerSelezionato;
	}

	public void setPgtSqlLayerSelezionato(PgtSqlLayer pgtSqlLayerSelezionato) {
		this.pgtSqlLayerSelezionato = pgtSqlLayerSelezionato;
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getTipoColore() {
		return tipoColore;
	}

	public void setTipoColore(String tipoColore) {
		this.tipoColore = tipoColore;
	}

	public String getDescrInfoSelezionata() {
		return descrInfoSelezionata;
	}

	public void setDescrInfoSelezionata(String descrInfoSelezionata) {
		this.descrInfoSelezionata = descrInfoSelezionata;
	}

	public String getColonnaInfoSelezionata() {
		return colonnaInfoSelezionata;
	}

	public void setColonnaInfoSelezionata(String colonnaInfoSelezionata) {
		this.colonnaInfoSelezionata = colonnaInfoSelezionata;
	}
	
}
