package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsRelCatsocTipoInter;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface AccessTableCatSocialeSessionBeanRemote {

	public CsCCategoriaSociale getCategoriaSocialeById(BaseDTO dto);
	
	public List<CsCCategoriaSociale> getCategorieSociali(CeTBaseObject cet);
	
	public List<CsCCategoriaSociale> getCategorieSocialiAll(CeTBaseObject cet);
	
	public List<CsCCategoriaSociale> getCategorieSocialiByDesc(BaseDTO dto);
	
	public List<CsRelCatsocTipoInter> findRelCatsocTipoInterByCatSoc(BaseDTO dto);
	
	public void salvaRelCatsocTipoInter(BaseDTO dto);
	
	public void eliminaRelCatsocTipoInter(BaseDTO dto);
	
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocByCatSoc(BaseDTO dto);
	
	public void salvaRelSettoreCatsoc(BaseDTO dto);
	
	public void eliminaRelSettoreCatsoc(BaseDTO dto);
	
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO dto);
	
	public void salvaRelSettCsocTipoInter(BaseDTO dto);
	
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

	public List<CsRelSettoreCatsoc> findRelSettoreCatsocBySettore(BaseDTO dto);

}