package it.webred.ct.data.access.basic.redditi.dao;

import java.util.List;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.redditi.RedditiServiceException;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeyTrascodificaDTO;
import it.webred.ct.data.model.redditi.RedDatiAnagrafici;
import it.webred.ct.data.model.redditi.RedDescrizione;
import it.webred.ct.data.model.redditi.RedDomicilioFiscale;
import it.webred.ct.data.model.redditi.RedRedditiDichiarati;
import it.webred.ct.data.model.redditi.RedTrascodifica;

public interface RedditiDAO {
	public List<RedDatiAnagrafici> getListaSoggettiByCF(RicercaSoggettoDTO rs) throws RedditiServiceException;
	public List<RedDatiAnagrafici> getListaSoggettiPFByDatiAna(RicercaSoggettoDTO rs) throws RedditiServiceException;
	public List<RedDatiAnagrafici> getListaSoggettiPGByDatiAna(RicercaSoggettoDTO rs) throws RedditiServiceException;
	public RedDatiAnagrafici getSoggettoByKey(KeySoggDTO key) throws RedditiServiceException;
	public RedDomicilioFiscale getDomicilioByKey(KeySoggDTO key) throws RedditiServiceException;
	public List<RedRedditiDichiarati> getRedditiByKey(KeySoggDTO key) throws RedditiServiceException;
	public RedDescrizione getDescrizioneByKey(KeySoggDTO key) throws RedditiServiceException;
	public RedTrascodifica getTrascodificaByKey(KeyTrascodificaDTO key) throws RedditiServiceException;
	public List<RedRedditiDichiarati> getRedditiByKeyAnno(KeySoggDTO rs) throws RedditiServiceException;
	public String getAnnoUltimiRedditiByCF(KeySoggDTO rs) throws RedditiServiceException;
	
}
