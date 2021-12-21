package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.fse.FseSearchCriteria;
import it.webred.cs.csa.ejb.dto.fse.ListaFseDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableDatiPorSessionBeanRemote {
	
	public CsExtraFseDatiLavoro getCsCDatiLavoroById(BaseDTO dto);
	public CsExtraFseDatiLavoro findDatiPorUdcBySchedaId(BaseDTO dto);
	
	public CsExtraFseDatiLavoro saveDatiPor(BaseDTO dto);
	public CsExtraFseDatiLavoro fillDatiLavoroPor(CsExtraFseDatiLavoro dl,String username);
	
	public void eliminaDatiPor(BaseDTO dto);
	public SiruResultDTO validaSiru(BaseDTO dto);
	public List<String> validaStampa(BaseDTO dto);
	
	
	/*Lista*/
	public List<ListaFseDTO> getListaFse(FseSearchCriteria searchCriteria);
	public Integer getListaFseCount(FseSearchCriteria searchCriteria);
	public List<KeyValueDTO> findListaTipiFse(CeTBaseObject cet);
	public List<KeyValueDTO> getListaComuniResidenzaUsati(CeTBaseObject cet);
	public List<KeyValueDTO> findListaProgettiUsati(CeTBaseObject cet);
}
