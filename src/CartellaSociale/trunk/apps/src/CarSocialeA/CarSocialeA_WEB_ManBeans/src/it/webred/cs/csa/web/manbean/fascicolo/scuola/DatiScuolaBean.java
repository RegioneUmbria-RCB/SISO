package it.webred.cs.csa.web.manbean.fascicolo.scuola;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDScuola;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbScuola;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.data.model.CsTbTipoScuola;
import it.webred.cs.jsf.interfaces.IDatiScuola;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

public class DatiScuolaBean extends FascicoloCompBaseBean implements IDatiScuola {

	private List<CsTbTipoScuola> listaTipoScuole;
	
	private List<SelectItem> listaComuni;
	private List<CsDScuola> listaScuole;
	private List<SelectItem> listaAnni;
	private List<SelectItem> listaTipi;
	private List<SelectItem> listaNomi;
	private List<SelectItem> listaGradi;
	private List<SelectItem> listaProgetti;
	private int idxSelected;
	private String comune; 
	private CsDScuola scuola;
	private boolean renderScuole;
		
	AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	@Override
	public void initializeData() {
		
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			listaScuole = diarioService.findScuoleByCaso(dto);

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
		
		scuola = new CsDScuola();
		scuola.setCsTbScuola(new CsTbScuola());
		scuola.setCsTbTipoScuola(new CsTbTipoScuola());
		listaNomi = new ArrayList<SelectItem>();
		listaNomi.add(new SelectItem("0", "- seleziona -"));
		
	}
	
	@Override
	public void carica() {
		
		scuola = listaScuole.get(idxSelected);
		if(scuola.getCsTbScuola()==null)
		   scuola.setCsTbScuola(new CsTbScuola());
		
		aggiornaNomi();
		
		//listaNomi.add(new SelectItem(scuola.getCsTbScuola().getId(), scuola.getCsTbScuola().getDescrizione()));
		
	}
	
	@Override
	public void salva() {
		
		if(!validaScuola())
			return;
		
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
	
		    if(!(scuola.getCsTbScuola().getId()!=null && scuola.getCsTbScuola().getId().longValue()>0))
	        	scuola.setCsTbScuola(null);
			
			if(scuola.getDiarioId() != null) {
				
				//modifica
				scuola.getCsDDiario().setUsrMod(dto.getUserId());
				scuola.getCsDDiario().setDtMod(new Date());
				dto.setObj(scuola);
				
				diarioService.updateScuola(dto);
			}
			else {
				//trovo caso
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setIdCaso(csASoggetto.getCsACaso().getId());

		        CsACaso csa = new CsACaso();
		        csa.setId(idCaso);
		        
		        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
		        cstd.setId(new Long(DataModelCostanti.TipoDiario.DATI_SCUOLA_ID)); 
		        Date dtIns = new Date();
		        
		        scuola.getCsDDiario().setCsACaso(csa);
		        scuola.getCsDDiario().setCsTbTipoDiario(cstd);
		        scuola.getCsDDiario().setDtIns(dtIns);
		        scuola.getCsDDiario().setUserIns(dto.getUserId());
		        scuola.getCsDDiario().setResponsabileCaso(this.getOperResponsabileCaso().getId());
		        scuola.getCsDDiario().setCsOOperatoreSettore(opSettore);
		        scuola.getCsDDiario().setDtAmministrativa(dtIns);
		   
				dto.setObj(scuola);
				
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
	
	private boolean validaScuola() {
		
		boolean ok = true;
		
		/*boolean annoScolasticoOk = scuola.getAnnoScolastico() != null && !"".equals(scuola.getAnnoScolastico());
		if(!annoScolasticoOk){
			ok = false;
        	addError("Il campo Anno Scolastico è obbligatorio", "");
		}*/
        
		boolean tipoScuolaOk =scuola.getCsTbTipoScuola().getId()!=null && scuola.getCsTbTipoScuola().getId().longValue()>0;
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
	
	private void setCsTbTipoScuola(){
		
		if(scuola.getCsTbTipoScuola().getId() != null && scuola.getCsTbTipoScuola().getId().intValue()>0){
			long idSel = scuola.getCsTbTipoScuola().getId().intValue();
			boolean trovato = false;
			int i=0;
		    while(!trovato && i<this.getListaTipoScuole().size()){
		    	CsTbTipoScuola s = this.listaTipoScuole.get(i);
		    	if(s.getId().intValue()==idSel){
		    		scuola.setCsTbTipoScuola(s);
		    		trovato=true;
		    	}
		    	i++;
		    }
		 }
	}
	
	private void setCsTbScuola(){}
	
	public void aggiornaNomi() {
		
		setCsTbTipoScuola();
		renderScuole = false;
		listaNomi = new ArrayList<SelectItem>();
		listaNomi.add(new SelectItem("", "- seleziona -"));
		if(comune!=null && !comune.isEmpty() 
				&& scuola.getCsTbTipoScuola().getId() != null && scuola.getCsTbTipoScuola().getId().intValue()>0) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			dto.setObj2(scuola.getCsTbTipoScuola().getId());
			dto.setObj3(comune);
			
			 List<CsTbScuola> lstCs = new ArrayList<CsTbScuola>();
			 boolean searchByAnno = scuola.getAnnoScolastico() != null && !"".equals(scuola.getAnnoScolastico());
			if(searchByAnno){
				dto.setObj(scuola.getAnnoScolastico());
				lstCs = confService.getScuoleByComuneAnnoTipo(dto);
			}else
				lstCs = confService.getScuoleByComuneTipo(dto);
			
			for(CsTbScuola cs: lstCs){
				String descrizione = cs.getDescrizione();
				if(!searchByAnno)
					descrizione += " - a.s. "+cs.getAnnoScolastico();
				listaNomi.add(new SelectItem(cs.getId(), descrizione));
			}
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
	public List<CsDScuola> getListaScuole() {
		return listaScuole;
	}

	public void setListaScuole(List<CsDScuola> listaScuole) {
		this.listaScuole = listaScuole;
	}

	@Override
	public List<SelectItem> getListaComuni() {
		if(listaComuni == null) {
			listaComuni = new ArrayList<SelectItem>();
			listaComuni.add(new SelectItem("", "- seleziona -"));
			Map<String, CsTbScuola> map = new HashMap<String, CsTbScuola>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<String> lstCs = confService.getComuniScuole(cet);
			for(String c: lstCs)
				listaComuni.add(new SelectItem(c, c));
		}
		return listaComuni;
	}
	
	@Override
	public List<SelectItem> getListaAnni() {
		if(listaAnni == null) {
			listaAnni = new ArrayList<SelectItem>();
			listaAnni.add(new SelectItem("", "- seleziona -"));
			Map<String, CsTbScuola> map = new HashMap<String, CsTbScuola>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbScuola> lstCs = confService.getScuole(cet);
			for(CsTbScuola cs: lstCs) {
				if(!map.containsKey(cs.getAnnoScolastico())) {
					listaAnni.add(new SelectItem(cs.getAnnoScolastico(), cs.getAnnoScolastico()));
					map.put(cs.getAnnoScolastico(), cs);
				}
			}
		}
		return listaAnni;
	}

	public void setListaAnni(List<SelectItem> listaAnni) {
		this.listaAnni = listaAnni;
	}
	
	@Override
	public List<SelectItem> getListaTipi() {
		if(listaTipi == null) {
			listaTipi = new ArrayList<SelectItem>();
			listaTipi.add(new SelectItem("0", "- seleziona -"));
			for(CsTbTipoScuola cs: this.getListaTipoScuole()) 
				listaTipi.add(new SelectItem(cs.getId(), cs.getDescrizione()));
		}
		return listaTipi;
	}

	public void setListaTipi(List<SelectItem> listaTipi) {
		this.listaTipi = listaTipi;
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
		if(listaGradi == null) {
			listaGradi = new ArrayList<SelectItem>();
			listaGradi.add(new SelectItem("", "- seleziona -"));
			listaGradi.add(new SelectItem("1", "1"));
			listaGradi.add(new SelectItem("2", "2"));
			listaGradi.add(new SelectItem("3", "3"));
			listaGradi.add(new SelectItem("4", "4"));
			listaGradi.add(new SelectItem("5", "5"));
		}
		return listaGradi;
	}

	public void setListaGradi(List<SelectItem> listaGradi) {
		this.listaGradi = listaGradi;
	}

	public void setListaProgetti(List<SelectItem> listaProgetti) {
		this.listaProgetti = listaProgetti;
	}
	
	public List<SelectItem> getListaProgetti() {
		if (listaProgetti == null) {
			listaProgetti = new ArrayList<SelectItem>();
			listaProgetti.add(new SelectItem("", "- seleziona -"));
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbTipoProgetto> lista = confService.getTipiProgetto(cet);
			for (CsTbTipoProgetto cs : lista) {
				SelectItem si = new SelectItem(cs.getId(), cs.getDescrizione());
				si.setDisabled(!cs.isAbilitato());
				listaProgetti.add(si);
			}
		}
		return listaProgetti;
	}


	public String getComune() {
		return comune;
	}

	public void setListaTipoScuole(List<CsTbTipoScuola> listaTipoScuole) {
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

	public List<CsTbTipoScuola> getListaTipoScuole() {
		if(this.listaTipoScuole==null){
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			listaTipoScuole = confService.getTipoScuole(cet);
		}
		return this.listaTipoScuole;
	}
	
	@Override
	public void aggiornaAltroNome(){
		if(scuola.getCsTbScuola().getId()!=null && scuola.getCsTbScuola().getId()>0)
			scuola.setNome(null);
	}
}
