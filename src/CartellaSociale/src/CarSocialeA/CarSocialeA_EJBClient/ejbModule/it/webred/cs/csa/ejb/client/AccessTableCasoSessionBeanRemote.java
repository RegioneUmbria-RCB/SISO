package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableCasoSessionBeanRemote {

	public CsACaso newCaso(IterDTO dto);

	public List<CsACaso> findAll(IterDTO dto);

	public CsACaso findCasoById(IterDTO dto);

	public void setDataModifica(IterDTO dto);

	public void salvaOperatoreCaso(BaseDTO dto);
	
	public void updateOperatoreCaso(BaseDTO dto);

	public void eliminaOperatoreTipoOpByCasoId(BaseDTO dto);
	
	public void eliminaOperatoreTipoOpById(BaseDTO dto);

	public List<CsACasoOpeTipoOpe> getListaOperatoreTipoOpByCasoId(BaseDTO dto);
	
	public CsACasoOpeTipoOpe findCasoOpeResponsabile(BaseDTO dto);
	
//	public CsOOperatore findResponsabile(BaseDTO dto);

	public CsOOperatoreTipoOperatore findOperatoreTipoOperatoreByOpSettore(BaseDTO dto);

	public CsACasoOpeTipoOpe findCasoOpeTipoOpeByOpSettore(BaseDTO dto);
	
	public Integer countCasiByResponsabileCatSociale(BaseDTO dto);

	public void updateCaso(BaseDTO dto);
	
	public void eliminaOperatoreTipoOp2ByCasoId(BaseDTO dto);

	public void salvaOperatoreCaso2(BaseDTO dto);

	public List<CsACasoOpeTipoOpe2> getListaOperatoreTipoOp2ByCasoId(BaseDTO dto);

	public CsOOperatoreBASIC findResponsabileBASIC(BaseDTO dto);
	
	public Long aggiornaResponsabileCaso(BaseDTO dto);



}