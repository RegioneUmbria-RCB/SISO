package it.webred.cs.csa.ejb.ejb;

import java.util.List;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsRelCatsocTipoInter;
import it.webred.cs.data.model.CsRelCatsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSettoreCatsocPK;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableCatSocialeSessionBean extends CarSocialeBaseSessionBean implements AccessTableCatSocialeSessionBeanRemote  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CatSocialeDAO catSocialeDao;
	
	@Override
	public CsCCategoriaSociale getCategoriaSocialeById(BaseDTO dto) {
		return catSocialeDao.getCategoriaSocialeById((Long) dto.getObj());
	}
	
	@Override
	public List<CsCCategoriaSociale> getCategorieSocialiByDesc(BaseDTO dto) {
		return catSocialeDao.getCategorieSocialiByDesc((String) dto.getObj());
	}
	
	@Override
	public List<CsCCategoriaSociale> getCategorieSociali(CeTBaseObject cet) {
		return catSocialeDao.getCategorieSociali();
	}
	
	@Override
	public List<CsCCategoriaSociale> getCategorieSocialiAll(CeTBaseObject cet) {
		return catSocialeDao.getCategorieSocialiAll();
	}
	
	@Override
	public void salvaCategoriaSociale(BaseDTO dto) {
		catSocialeDao.salvaCategoriaSociale((CsCCategoriaSociale) dto.getObj());
	}
	
	@Override
	public void updateCategoriaSociale(BaseDTO dto) {
		catSocialeDao.updateCategoriaSociale((CsCCategoriaSociale) dto.getObj());
	}
	
	@Override
	public void eliminaCategoriaSociale(BaseDTO dto) {
		catSocialeDao.eliminaCategoriaSociale((Long) dto.getObj());
	}

	@Override
	public CsRelCatsocTipoInter getRelCatsocTipoInterById(BaseDTO dto) {
		return catSocialeDao.getRelCatsocTipoInterById((CsRelCatsocTipoInterPK) dto.getObj());
	}
	
	@Override
	public List<CsRelCatsocTipoInter> findRelCatsocTipoInterByCatSoc(BaseDTO dto) {
		return catSocialeDao.findRelCatsocTipoInterByCatSoc((Long) dto.getObj());
	}
	
	@Override
	public void salvaRelCatsocTipoInter(BaseDTO dto) {
		catSocialeDao.salvaRelCatsocTipoInter((CsRelCatsocTipoInter) dto.getObj());
	}
	
	@Override
	public void updateRelCatsocTipoInter(BaseDTO dto) {
		catSocialeDao.updateRelCatsocTipoInter((CsRelCatsocTipoInter) dto.getObj());
	}
	
	@Override
	public void eliminaRelCatsocTipoInter(BaseDTO dto) {
		catSocialeDao.eliminaRelCatsocTipoInter((Long) dto.getObj(), (Long) dto.getObj2());
	}
	
	@Override
	public CsRelSettoreCatsoc getRelSettoreCatsocById(BaseDTO dto) {
		return catSocialeDao.getRelSettoreCatsocById((CsRelSettoreCatsocPK) dto.getObj());
	}
	
	@Override
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocByCatSoc(BaseDTO dto) {
		return catSocialeDao.findRelSettoreCatsocByCatSoc((Long) dto.getObj());
	}
	
	@Override
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocBySettore(BaseDTO dto) {
		return catSocialeDao.findRelSettoreCatsocBySettore((Long) dto.getObj());
	}
	
	@Override
	public void salvaRelSettoreCatsoc(BaseDTO dto) {
		catSocialeDao.salvaRelSettoreCatsoc((CsRelSettoreCatsoc) dto.getObj());
	}
	
	@Override
	public void updateRelSettoreCatsoc(BaseDTO dto) {
		catSocialeDao.updateRelSettoreCatsoc((CsRelSettoreCatsoc) dto.getObj());
	}
	
	@Override
	public void eliminaRelSettoreCatsoc(BaseDTO dto) {
		catSocialeDao.eliminaRelSettoreCatsoc((Long) dto.getObj(), (Long) dto.getObj2());
	}
	
	@Override
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO dto) {
		return catSocialeDao.getRelSettCsocTipoInterById((CsRelSettCsocTipoInterPK) dto.getObj());
	}
	
	@Override
	public void salvaRelSettCsocTipoInter(BaseDTO dto) {
		catSocialeDao.salvaRelSettCsocTipoInter((CsRelSettCsocTipoInter) dto.getObj());
	}
	
	@Override
	public void updateRelSettCsocTipoInter(BaseDTO dto) {
		catSocialeDao.updateRelSettCsocTipoInter((CsRelSettCsocTipoInter) dto.getObj());
	}
	
	@Override
	public List<CsRelSettCsocTipoInter> findRelSettCatsocTipoInterByCatSoc(BaseDTO dto) {
		return catSocialeDao.findRelSettCatsocTipoInterByCatSoc((Long) dto.getObj());
	}
	
	@Override
	public void eliminaRelSettCsocTipoInter(BaseDTO dto) {
		catSocialeDao.eliminaRelSettCsocTipoInter((Long) dto.getObj(), (Long) dto.getObj2(), (Long) dto.getObj3());
	}
	
	@Override
	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByCatSoc(BaseDTO dto) {
		return catSocialeDao.findRelSettCatsocEsclusivaByCatSoc((Long) dto.getObj());
	}
	
	@Override
	public void salvaRelSettCatsocEsclusiva(BaseDTO dto) {
		catSocialeDao.salvaRelSettCatsocEsclusiva((CsRelSettCatsocEsclusiva) dto.getObj());
	}
	
	@Override
	public void updateRelSettCatsocEsclusiva(BaseDTO dto) {
		catSocialeDao.updateRelSettCatsocEsclusiva((CsRelSettCatsocEsclusiva) dto.getObj());
	}
	
	@Override
	public void eliminaRelSettCatsocEsclusiva(BaseDTO dto) {
		catSocialeDao.eliminaRelSettCatsocEsclusiva((Long) dto.getObj(), (Long) dto.getObj2(), (Long) dto.getObj3());
	}

}
