package it.webred.cs.csa.web.manbean.fascicolo.scuola;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ListaDatiScuolaDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ScuoleSearchCriteria;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDScuola;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.interfaces.IDatiScuola;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

public class DatiScuolaBean extends FascicoloCompSecondoLivello implements IDatiScuola {

	private List<SelectItem> listaTipoScuole;
	private List<SelectItem> listaProgetti;
	private List<SelectItem> listaComuni;
	private List<SelectItem> listaAnni;
	private List<SelectItem> listaGradi;
	private List<SelectItem> listaNomi;
	
	private List<ListaDatiScuolaDTO> listaScuole;
	
	private int idxSelected;
	private String comune; 
	private CsDScuola scuola;
	private boolean renderScuole;
		
	@Override
	public void initializeData() {
		
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			listaScuole = diarioService.findScuoleByCaso(dto);
			
			loadItems();

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}
	
	@Override
	public void nuovo() {
		comune = null;
		scuola = new CsDScuola();
		//scuola.setCsTbScuola(new CsTbScuola());
		//scuola.setCsTbTipoScuola(new CsTbTipoScuola());
		listaNomi = new ArrayList<SelectItem>();
	}
	
	@Override
	public void carica() {
		
		ListaDatiScuolaDTO selezione = listaScuole.get(idxSelected);
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(selezione.getDiarioId());
		scuola = diarioService.getScuolaById(dto);
		comune = scuola.getCsTbScuola()!=null ? scuola.getCsTbScuola().getComune() : null;
		
		/*if(scuola.getCsTbScuola()==null)
		   scuola.setCsTbScuola(new CsTbScuola());*/
		scuola.getCsDDiario().setVisSecondoLivello(null); //SISO-812
		aggiornaNomi();

	}
	
	@Override
	protected void save(){	
		if(!validaScuola())
			return;
		
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
	
			valorizzaSecondoLivello(scuola.getCsDDiario());
			dto.setObj(scuola);
			if(scuola.getDiarioId() != null)
				diarioService.updateScuola(dto);
			else {
				
				dto.setObj2(idCaso);
				dto.setObj3(opSettore);
				
				CsDDiario csd = diarioService.saveScuola(dto);
				scuola.setCsDDiario(csd);
				scuola.setDiarioId(csd.getId());
				
			}
			
			initializeData();
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	public void elimina() {
		
		try {
		
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(scuola);
			diarioService.deleteScuola(dto);
		
			initializeData();
			
			addInfoFromProperties("elimina.ok");
		
		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private void loadItems(){
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		
		if(listaTipoScuole==null)
			listaTipoScuole = convertiLista(confService.getTipoScuole(cet));
		
		if(listaProgetti==null)
			listaProgetti = convertiLista(confService.getTipiProgetto(cet));
		
		if(listaComuni == null)
			listaComuni =  convertiLista(confService.getComuniScuole(cet));
		
		if(listaAnni == null) {
			listaAnni = convertiLista(confService.getAnniScolastici(cet));
		}
		
		if(listaGradi == null) {
			listaGradi = new ArrayList<SelectItem>();
			listaGradi.add(new SelectItem("1", "1"));
			listaGradi.add(new SelectItem("2", "2"));
			listaGradi.add(new SelectItem("3", "3"));
			listaGradi.add(new SelectItem("4", "4"));
			listaGradi.add(new SelectItem("5", "5"));
		}
	}
		
	private boolean validaScuola() {
		
		boolean ok = true;
		
		/*boolean annoScolasticoOk = scuola.getAnnoScolastico() != null && !"".equals(scuola.getAnnoScolastico());
		if(!annoScolasticoOk){
			ok = false;
        	addError("Il campo Anno Scolastico è obbligatorio", "");
		}*/
        
		boolean tipoScuolaOk =scuola.getTipoScuolaId()!=null && scuola.getTipoScuolaId().longValue()>0;
        if(!tipoScuolaOk){
        	ok = false;
        	addError("Il campo Tipo di Scuola è obbligatorio", "");
        }
        
	   /* if(annoScolasticoOk && tipoScuolaOk && !(scuola.getCsTbScuola().getId()!=null && scuola.getCsTbScuola().getId().longValue()>0)){
	       	ok = false;
	       	addError("Il campo Nome scuola è obbligatorio", "");
	      }*/
		
		return ok;
	}
	
	public void aggiornaNomi() {
		
		renderScuole = false;
		listaNomi = new ArrayList<SelectItem>();
		if(!StringUtils.isBlank(comune) && scuola.getTipoScuolaId()!=null && scuola.getTipoScuolaId().intValue()>0) {
			ScuoleSearchCriteria dto = new ScuoleSearchCriteria();
			fillEnte(dto);
			
			dto.setTipo(scuola.getTipoScuolaId());
			dto.setComune(comune);
			dto.setAnno(scuola.getAnnoScolasticoId());
			
			listaNomi = convertiLista(confService.searchNomiScuole(dto));
			
			renderScuole = true;
		}
	}

	@Override
	public Long getIdCaso() {
		return idCaso;
	}

	@Override
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	@Override
	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	@Override
	public List<ListaDatiScuolaDTO> getListaScuole() {
		return listaScuole;
	}

	public void setListaScuole(List<ListaDatiScuolaDTO> listaScuole) {
		this.listaScuole = listaScuole;
	}

	@Override
	public List<SelectItem> getListaComuni() {
		return listaComuni;
	}
	
	@Override
	public List<SelectItem> getListaAnni() {
		return listaAnni;
	}

	public void setListaAnni(List<SelectItem> listaAnni) {
		this.listaAnni = listaAnni;
	}
	
	@Override
	public List<SelectItem> getListaNomi() {
		return listaNomi;
	}

	public void setListaNomi(List<SelectItem> listaNomi) {
		this.listaNomi = listaNomi;
	}

	@Override
	public List<SelectItem> getListaGradi() {
		return listaGradi;
	}

	public void setListaGradi(List<SelectItem> listaGradi) {
		this.listaGradi = listaGradi;
	}

	public void setListaProgetti(List<SelectItem> listaProgetti) {
		this.listaProgetti = listaProgetti;
	}
	
	public List<SelectItem> getListaProgetti() {
		return listaProgetti;
	}


	public String getComune() {
		return comune;
	}

	public void setListaTipoScuole(List<SelectItem> listaTipoScuole) {
		this.listaTipoScuole = listaTipoScuole;
	}

	public void setListaComuni(List<SelectItem> listaComuni) {
		this.listaComuni = listaComuni;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	@Override
	public CsDScuola getScuola() {
		return scuola;
	}

	public void setScuola(CsDScuola scuola) {
		this.scuola = scuola;
	}

	@Override
	public boolean isRenderScuole() {
		return renderScuole;
	}

	public void setRenderScuole(boolean renderScuole) {
		this.renderScuole = renderScuole;
	}

	@Override
	public void aggiornaAltroNome(){
		if(this.isDisabilitaCampoLiberoScuola())
			scuola.setNome(null);
	}

	@Override
	public List<SelectItem> getListaTipoScuole() {
		return listaTipoScuole;
	}
	
	public boolean isDisabilitaCampoLiberoScuola(){
		return !StringUtils.isBlank(scuola.getScuolaId());
	}
	
	public String getDescrizioneOperatore(String username){
		return super.getCognomeNomeUtente(username);
	}
}
