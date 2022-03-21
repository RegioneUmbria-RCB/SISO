package it.webred.ct.data.access.basic.redditi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.ici.dao.IciDAO;
import it.webred.ct.data.access.basic.redditi.RedditiService;
import it.webred.ct.data.access.basic.redditi.dao.RedditiDAO;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeyTrascodificaDTO;
import it.webred.ct.data.access.basic.redditi.dto.RedditiDicDTO;
import it.webred.ct.data.model.redditi.RedDatiAnagrafici;
import it.webred.ct.data.model.redditi.RedDescrizione;
import it.webred.ct.data.model.redditi.RedDomicilioFiscale;
import it.webred.ct.data.model.redditi.RedRedditiDichiarati;
import it.webred.ct.data.model.redditi.RedRedditiDichiaratiPK;
import it.webred.ct.data.model.redditi.RedTrascodifica;

@Stateless
public class RedditiServiceBean extends CTServiceBaseBean implements
		RedditiService {
	@Autowired 
	private RedditiDAO redditiDAO;
	@Override
	public List<RedDatiAnagrafici> getListaSoggetti(RicercaSoggettoDTO rs) {
		List<RedDatiAnagrafici> lista= new ArrayList<RedDatiAnagrafici>();
		if (rs.getCodFis()!= null && ! rs.getCodFis().equals(""))
			lista=redditiDAO.getListaSoggettiByCF(rs);
		else {
			if (rs.getCognome()!= null && ! rs.getCognome().equals("") &&
				rs.getNome()!= null && ! rs.getNome().equals("") && 
				rs.getDtNas()!= null &&
				rs.getCodComNas()!= null && ! rs.getCodComNas().equals(""))
				lista=redditiDAO.getListaSoggettiPFByDatiAna(rs);
			else
				lista=redditiDAO.getListaSoggettiPGByDatiAna(rs);
		}
		return lista;		 
			
			
	}

	@Override
	public List<RedDatiAnagrafici> getListaSoggettiUltDic(RicercaSoggettoDTO rs) {
		List<RedDatiAnagrafici> listaSoggUltDic=new ArrayList<RedDatiAnagrafici>();
		List<RedDatiAnagrafici> listaSogg = getListaSoggetti(rs) ;
		String ultimoAnno="";
		//Si include nella lista solo l'ultimo anno di dichiarazione
		if (listaSogg != null && listaSogg.size() >0 ){
			listaSoggUltDic = new ArrayList<RedDatiAnagrafici>();
			ultimoAnno=listaSogg.get(0).getAnnoImposta();
			if (ultimoAnno==null)
				ultimoAnno="";
			for(RedDatiAnagrafici sogg:listaSogg) {
				if (!ultimoAnno.equals(sogg.getAnnoImposta())) 
					break;
				else
					listaSoggUltDic.add(sogg);
			}
		}
		return listaSoggUltDic;
	}

	@Override
	public List<RedditiDicDTO> getRedditiByKey(KeySoggDTO key) {
		
		List<RedRedditiDichiarati> lista= redditiDAO.getRedditiByKey(key);
		return this.elaboraListaRedditiDichiarati(lista, key);
	}
	
	@Override
	public List<RedditiDicDTO> getRedditiByKeyAnno(KeySoggDTO key) {
		
		List<RedRedditiDichiarati> lista= redditiDAO.getRedditiByKeyAnno(key);
		return this.elaboraListaRedditiDichiarati(lista, key);
	}

	@Override
	public RedDomicilioFiscale getDomicilioByKey(KeySoggDTO key) {
		RedDomicilioFiscale ele =null;
		ele= redditiDAO.getDomicilioByKey(key);
		return ele;
	}


	@Override
	public RedDatiAnagrafici getSoggettoByKey(KeySoggDTO key) {
		RedDatiAnagrafici ele =null;
		ele= redditiDAO.getSoggettoByKey(key);
		return ele; 
	}


	private List<RedditiDicDTO> elaboraListaRedditiDichiarati(List<RedRedditiDichiarati> lista, KeySoggDTO key){
		List<RedditiDicDTO> listaDTO = null;
		RedditiDicDTO eleListaDTO=null;
		
		RedDescrizione des = redditiDAO.getDescrizioneByKey(key);		
		KeyTrascodificaDTO  keyTras;
		RedTrascodifica tras;
		if (lista != null)  {
			listaDTO = new ArrayList<RedditiDicDTO>();
			logger.debug("SCORRE REDDITI.");
			for (RedRedditiDichiarati ele: lista) {
				logger.debug("ELEMENTO - annoimposta / codiceQuadro / valore cont: " + ele.getId().getAnnoImposta() + " / " + ele.getId().getCodiceQuadro() + " / " + ele.getValoreContabile());
				keyTras=new KeyTrascodificaDTO();
				keyTras.setTipoModello(des.getId().getTipoModello());
				keyTras.setAnnoImposta(ele.getId().getAnnoImposta());
				keyTras.setCodiceRiga(ele.getId().getCodiceQuadro());
				tras = redditiDAO.getTrascodificaByKey(keyTras);
				eleListaDTO = new RedditiDicDTO();
				RedRedditiDichiaratiPK id = new RedRedditiDichiaratiPK();
				id.setCodiceFiscaleDic(key.getCodFis());
				id.setIdeTelematico(key.getIdeTelematico());
				id.setAnnoImposta(ele.getId().getAnnoImposta());
				id.setCodiceQuadro(ele.getId().getCodiceQuadro() );
				eleListaDTO.setId(id);
				eleListaDTO.setDesQuadro(tras.getDescrizione());
				String valoreC = ele.getValoreContabile();
				if(valoreC != null && !valoreC.trim().equals("") && !valoreC.contains(",")){
					try {
						Double d = Double.parseDouble(valoreC.trim());
						d = new Double(d.doubleValue() / tras.getCentDivisore().intValue());
						valoreC = new DecimalFormat("#,##0.00").format(d);
					} catch (Exception e) {
						//lascia la stringa com'è
					}					
				}
				eleListaDTO.setValoreContabile(valoreC);
				eleListaDTO.setCodModello(des.getId().getTipoModello());
				listaDTO.add(eleListaDTO);
			}
		
		}
		return listaDTO;
	}


}
