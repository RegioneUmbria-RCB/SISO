package it.webred.ct.service.indice.web.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.dto.AggregaUnici;
import it.webred.ct.data.access.basic.indice.dto.CambiaUnico;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.ListaTotale;
import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.access.basic.indice.dto.SitSorgenteDTO;
import it.webred.ct.data.access.basic.indice.via.dto.SitViaDTO;
import it.webred.ct.data.model.indice.SitEnteSorgente;
import it.webred.ct.service.indice.web.IndiceBaseBean;

public class IndiceCommonBean extends IndiceCommonData{

	public void doApriListaTotale(){
		
		try{
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			
			ListaTotale lt = new ListaTotale();
			lt.setCriteria(criteria);
			lt.setStartm(0);
			lt.setNumberRecord(0);
			indDataIn.setListaTotale(lt);
			fillEnte(indDataIn);
			
			listaDTO = indiceService.getListaTotale(indDataIn);
			//listaDTO = indiceService.getListaTotale(criteria, 0, 0);
			
		}catch(Throwable t) {
			super.addErrorMessage("listatotali.error", t.getMessage());
			t.printStackTrace();
		}
	
	}
	
	public void doSelezionaDaValidare(){
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
			
			if(!op.isCheck() && !op.isValidato())
				op.setCheck(true);
			else op.setCheck(false);
		
		}
	}
	
	public void doValida(){
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		String msg = "valida";
		IndiceOperationCriteria opCriteria = new IndiceOperationCriteria();
		
		try{
			for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
				
				if(op.isCheck()){
					opCriteria.setHash(op.getCtrHash());
					opCriteria.setIdSorgente(dto.getEnteSorgente().getId());
					opCriteria.setProgressivo(dto.getProgressivoES());
					fillEnte(opCriteria);
					indiceService.validaSitTotale(opCriteria);
				}
			}
			
			//refresh lista
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			ListaTotale lt = new ListaTotale();
			lt.setCriteria(criteria);
			lt.setNumberRecord(0);
			lt.setStartm(0);
			indDataIn.setListaTotale(lt);
			fillEnte(indDataIn);
			
			listaDTO = indiceService.getListaTotale(indDataIn);
			//listaDTO = indiceService.getListaTotale(criteria, 0, 0);
			
			super.addInfoMessage(msg);
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}
	}
	
	public void doInvalida() {
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		String msg = "invalida";		
		IndiceOperationCriteria opCriteria = new IndiceOperationCriteria();

		try{
			for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
				
				if(op.isCheck()){
					opCriteria.setHash(op.getCtrHash());
					opCriteria.setIdSorgente(dto.getEnteSorgente().getId());
					opCriteria.setProgressivo(dto.getProgressivoES());
					fillEnte(opCriteria);
					indiceService.invalidaSitTotale(opCriteria);
				}
			}
			
			//refresh lista
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			ListaTotale lt = new ListaTotale();
			lt.setCriteria(criteria);
			lt.setNumberRecord(0);
			lt.setStartm(0);
			indDataIn.setListaTotale(lt);
			fillEnte(indDataIn);
			
			listaDTO = indiceService.getListaTotale(indDataIn);			
			//listaDTO = indiceService.getListaTotale(criteria, 0, 0);
			
			super.addInfoMessage(msg);
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}
		
	}
	
	public void doCambiaUnico() {
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		String msg = "cambiaunico";
		IndiceOperationCriteria opCriteria = new IndiceOperationCriteria();
		IndiceDataIn indDataIn = new IndiceDataIn();
		
		try{
			for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
				
				if(op.isCheck()){
					opCriteria.setHash(op.getCtrHash());
					opCriteria.setIdSorgente(dto.getEnteSorgente().getId());
					opCriteria.setProgressivo(dto.getProgressivoES());
					fillEnte(opCriteria);
					
					CambiaUnico cu = new CambiaUnico();
					cu.setCriteria(opCriteria);
					cu.setNuovoIdUnico(nuovoIdUnico);
					BigDecimal idUnicoOld = indiceService.getUnicoDaNativoTotale(opCriteria);
					if (idUnicoOld != null) {
						cu.setNativaOld(idUnicoOld);
					}
					indDataIn.setCambiaUnico(cu);
					fillEnte(indDataIn);
					
					indiceService.cambiaUnico(indDataIn);
					if (idUnicoOld != null) {
						indDataIn.setObj(idUnicoOld.longValue());
						indiceService.cancellaUnicoById(indDataIn);
					}
					//indiceService.cambiaUnico(opCriteria, nuovoIdUnico);
				}
			}
			
			//refresh lista
			
						
			ListaTotale lt = new ListaTotale();
			lt.setCriteria(criteria);
			lt.setNumberRecord(0);
			lt.setStartm(0);
			indDataIn.setListaTotale(lt);
			fillEnte(indDataIn);
			
			listaDTO = indiceService.getListaTotale(indDataIn);
			//listaDTO = indiceService.getListaTotale(criteria, 0, 0);
			
			super.addInfoMessage(msg);
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}
	}
	
	public void doAssociaACreaUnico() {
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		String msg = "nuovounico";
		IndiceOperationCriteria opCriteria = new IndiceOperationCriteria();
		
		try{
			for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
				
				if(op.isCheck()){
					opCriteria.setHash(op.getCtrHash());
					opCriteria.setIdSorgente(dto.getEnteSorgente().getId());
					opCriteria.setProgressivo(dto.getProgressivoES());
					fillEnte(opCriteria);
					indiceService.associaANuovoUnico(opCriteria);
				}
			}
			
			//refresh lista
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			ListaTotale lt = new ListaTotale();
			lt.setCriteria(criteria);
			lt.setNumberRecord(0);
			lt.setStartm(0);
			indDataIn.setListaTotale(lt);
			fillEnte(indDataIn);
			
			listaDTO = indiceService.getListaTotale(indDataIn);
			//listaDTO = indiceService.getListaTotale(criteria, 0, 0);
			
			super.addInfoMessage(msg);
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}
		
	}
	
	public void doControllaNativo() {
		
		SitSorgenteDTO dto = listaDTO.get(sorgenteIndex);
		IndiceOperationCriteria opCriteria = new IndiceOperationCriteria();
		//reset campi controllo
		controlloTotaleNativo = false;
		labelTotaleNativo = "";
		
		try{
			for(SitOperationDTO op: (List<SitOperationDTO>)dto.getListaTotali()){
				
				if(op.isCheck()){
					opCriteria.setHash(op.getCtrHash());
					fillEnte(opCriteria);
					BigDecimal idUnico = indiceService.getUnicoDaNativoTotale(opCriteria);
					
					if(idUnico != null){
						controlloTotaleNativo = true;
						labelTotaleNativo += op.getLabel() + "; ";
					}
				}
			}

		}catch(Throwable t) {
			t.printStackTrace();
		}		
	}
	
public void doAggregaUnici() {
		
		String msg = "aggregaunici";
		
		try{
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			AggregaUnici au = new AggregaUnici();
			au.setIdUno(new BigDecimal(criteria.getUnicoId()));
			au.setIdDue(new BigDecimal(nuovoIdUnico));
			au.setNuovoUnico(sitNuovoDTO);
			indDataIn.setAggregaUnici(au);
			fillEnte(indDataIn);
			
			indiceService.aggregaUnici(indDataIn);
			indDataIn.setObj(new Long(criteria.getUnicoId()).longValue());
			indiceService.cancellaUnicoById(indDataIn);
			indDataIn.setObj(new Long(nuovoIdUnico).longValue());
			indiceService.cancellaUnicoById(indDataIn);
			//indiceService.aggregaUnici(new BigDecimal(criteria.getUnicoId()), new BigDecimal(nuovoIdUnico), sitNuovoDTO);
			
			super.addInfoMessage(msg);
			
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}	
		
	}
	
	public void fillItemsEnteSorgente(){
		
		listaEnteSorgente = new ArrayList<SelectItem>();
		String msg = "listaentesorgente";
		
		try{
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			fillEnte(indDataIn);
			List<SitEnteSorgente> lista = indiceService.getListaEntiSorgenti(indDataIn);
			
			for(SitEnteSorgente ente: lista){
				listaEnteSorgente.add(new SelectItem(ente.getId(), ente.getDescrizione()));
			}
			
		}catch(Throwable t) {
			super.addErrorMessage(msg+".error", t.getMessage());
			t.printStackTrace();
		}	
		
	}
	
	public List<SelectItem> getListaEnteSorgente() {
		if(listaEnteSorgente.size() == 0)
			fillItemsEnteSorgente();
		return listaEnteSorgente;
	}

}
