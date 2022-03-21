package it.webred.cs.csa.ejb.client.configurazione;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsRelCatsocTipoInter;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface AccessTableCatSocialeSessionBeanRemote {

	public String getCategoriaSocialeById(BaseDTO dto);
	
	public List<CsCCategoriaSociale> getCategorieSociali(CeTBaseObject cet);
	
	public List<CsCCategoriaSociale> getCategorieSocialiAll(CeTBaseObject cet);
	
	public List<CsCCategoriaSociale> getCategorieSocialiByDesc(BaseDTO dto);
	
	public List<KeyValueDTO> getCategorieSocialiBySettore(BaseDTO dto);
	
	public Boolean isCategoriaSocialeSettore(BaseDTO dto);
	
	public List<CsRelCatsocTipoInter> findRelCatsocTipoInterByCatSoc(BaseDTO dto);
		
	public void eliminaRelCatsocTipoInter(BaseDTO dto);
	
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocByCatSoc(BaseDTO dto);
	
	public Boolean existsRelSettoreCatsoc(BaseDTO dto);
	
	public void eliminaRelSettoreCatsoc(BaseDTO dto);
	
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO dto);
	
	public void eliminaRelSettCsocTipoInter(BaseDTO dto);

	public void salvaCategoriaSociale(BaseDTO dto);

	public void updateCategoriaSociale(BaseDTO dto);

	public void eliminaCategoriaSociale(BaseDTO dto);

	public void updateRelCatsocTipoInter(BaseDTO dto);

	public void updateRelSettoreCatsoc(BaseDTO dto);

	public void updateRelSettCsocTipoInter(BaseDTO dto);

	public CsRelSettoreCatsoc getRelSettoreCatsocById(BaseDTO dto);

	public CsRelCatsocTipoInter getRelCatsocTipoInterById(BaseDTO dto);

	public List<CsRelSettCsocTipoInter> findRelSettCatsocTipoInterByCatSoc(BaseDTO dto);

	public void salvaRelSettCatsocEsclusiva(BaseDTO dto);

	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByCatSoc(BaseDTO dto);

	public void updateRelSettCatsocEsclusiva(BaseDTO dto);

	public void eliminaRelSettCatsocEsclusiva(BaseDTO dto);

	public List<CsRelSettoreCatsoc> findRelSettoreCatsocBySettore(BaseDTO bDto);
	
	public void aggiungiSettoreTipoInter(BaseDTO dto);
}