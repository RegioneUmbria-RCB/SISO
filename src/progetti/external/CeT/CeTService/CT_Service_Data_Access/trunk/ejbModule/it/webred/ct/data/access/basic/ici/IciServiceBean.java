package it.webred.ct.data.access.basic.ici;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.ici.dao.IciDAO;
import it.webred.ct.data.access.basic.ici.dto.IciDTO;
import it.webred.ct.data.access.basic.ici.dto.OggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaOggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaViaIciDTO;
import it.webred.ct.data.access.basic.ici.dto.VersamentoIciAnnoDTO;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.ici.SitTIciVia;
import it.webred.ct.data.model.ici.VTIciSoggAll;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;
@Stateless
public class IciServiceBean extends CTServiceBaseBean implements IciService { 
 
	private static final long serialVersionUID = 1L;
	@Autowired 
	private IciDAO iciDAO;
	
	@Override
	public List<String> getListaProvenienzaIci(RicercaOggettoIciDTO ro){
		return iciDAO.getListaProvenienzaIci();
	}
	
	@Override
	public List<SitTIciSogg> getListaSogg(RicercaSoggettoIciDTO rs) {
		List<SitTIciSogg> lista=null;
		if (rs.getCodFis()!=null && !rs.getCodFis().equals(""))
			lista = iciDAO.getListaSoggByCF(rs);
		return lista;
	}

	@Override
	public List<OggettoIciDTO> getListaOggettiByIdSogg(RicercaSoggettoIciDTO rs) {
		List<OggettoIciDTO> lista=null;
		if (rs.getIdSoggIci()!=null && !rs.getIdSoggIci().equals(""))
			lista = iciDAO.getListaOggettiByIdSogg(rs);
		return lista;
		
	}
	
	@Override
	public List<SitTIciOggetto> getListaDistintaOggettiByIdSogg(RicercaSoggettoIciDTO rs) {
		return iciDAO.getListaDistintaOggettiByIdSogg(rs);
	}
	
	@Override
	public List<VTIciSoggAll> getListaSoggettiByOgg(RicercaOggettoIciDTO ro) {
		List<VTIciSoggAll> lista=null;
		logger.debug("IciServiceBean - getListaSoggettiByOgg. IdExt:" + ro.getIdExt());
		if (ro.getIdExt()!=null && !ro.getIdExt().equals(""))
			lista = iciDAO.getListaSoggettiByOgg(ro);
		return lista;
				
	}

	@Override
	public List<SitTIciSogg> searchSoggetto(RicercaSoggettoIciDTO rs) {
		List<SitTIciSogg> lista=null;
		lista = iciDAO.searchSoggetto(rs);
		return lista;
	}
	public List<VersamentoIciAnnoDTO> getSommaVersamenti(RicercaSoggettoIciDTO rs) {
		return iciDAO.getSommaVersamenti(rs);
	}

	@Override
	public SitTIciSogg getSoggettoById(RicercaSoggettoIciDTO rs) {
		return iciDAO.getSoggettoById(rs);	
	}

	@Override
	public SitTIciVia getViaByIdExt(RicercaViaIciDTO rv) {
		return iciDAO.getViaByIdExt(rv);
	}	
	
	public List<SitTIciOggetto> getOggettiByFabbricato(RicercaOggettoDTO ro)	{
		List<SitTIciOggetto> listaOgg= new ArrayList<SitTIciOggetto>();
		List<SitTIciOggetto> listaTemp = new ArrayList<SitTIciOggetto>();
		String provenienza = ro.getProvenienza();
		if(provenienza!=null && !provenienza.trim().equals("")){
			listaTemp =iciDAO.getOggettiByProvFabbricato(ro);
		}else{
			List<String> provs = iciDAO.getListaProvenienzaIci();
			for(String prov:provs){
				ro.setProvenienza(prov);
				listaTemp.addAll(iciDAO.getOggettiByProvFabbricato(ro));
			}	
		}
		
		if (ro.getDtRif() == null)
			listaOgg = listaTemp;
		else {
			listaOgg= filtraOggettiValidiAllaData(listaTemp, ro.getDtRif());
		}
		
		return listaOgg;
	}

	@Override
	public List<SitTIciOggetto> getOggettiByUI(RicercaOggettoDTO ro) {
		List<SitTIciOggetto> listaOgg= new ArrayList<SitTIciOggetto>();
		List<SitTIciOggetto> listaTemp = new ArrayList<SitTIciOggetto>();
		if(ro.getProvenienza()!=null){
			listaTemp = iciDAO.getOggettiByProvUI(ro);
		}else{
			//Recupero tutte le provenienze disponibili
			List<String> provs = iciDAO.getListaProvenienzaIci();
			for(String prov : provs)
				listaTemp.addAll(iciDAO.getOggettiByProvUI(ro));
		}

		if (ro.getDtRif() == null)
			listaOgg = listaTemp;
		else {
			listaOgg= filtraOggettiValidiAllaData(listaTemp, ro.getDtRif());
		}
		return listaOgg;
		
	}

	@Override
	public List<SitTIciOggetto> getListaOggettiAiCiviciIci(RicercaOggettoIciDTO ro) {
		List<SitTIciOggetto> listaOgg= new ArrayList<SitTIciOggetto>();
		List<SitTIciOggetto> listaTemp = iciDAO.getListaOggettiAiCiviciIci(ro);
		logger.info("getListaOggettiAiCiviciIci - Data Rif: " + ro.getDtRif() );
		if (ro.getDtRif() == null)
			listaOgg = listaTemp;
		else {
			listaOgg = filtraOggettiValidiAllaData(listaTemp, ro.getDtRif());
		}
		return listaOgg;
		
	}
	
	private List<SitTIciOggetto> filtraOggettiValidiAllaData(List<SitTIciOggetto> listaDaFiltrare , Date dtRif ){
		List<SitTIciOggetto> listaOgg = new ArrayList<SitTIciOggetto>();
		
		if (listaDaFiltrare != null && listaDaFiltrare.size() > 0) {
			for (SitTIciOggetto ogg: listaDaFiltrare){
				int anno=0;
				try  {
					anno=Integer.parseInt(ogg.getYeaRif());
				}catch (NumberFormatException nfe){	}
				if (anno ==0) {
					try  {
						anno=Integer.parseInt(ogg.getYeaDen());
					}catch (NumberFormatException nfe){	}
				}
				if (anno != 0){
					GregorianCalendar gc = new GregorianCalendar(anno, 11, 31);
					Date dtRifIci = gc.getTime();
					if (dtRifIci.compareTo(dtRif) <= 0 )
						listaOgg.add(ogg);
				}else
					listaOgg.add(ogg);
			}
		}	
		
		return listaOgg;
	}
	
	@Override
	public List<IciDTO> getListaIciByFPSOgg(RicercaOggettoDTO ro) {
			
			List<IciDTO> lista= new ArrayList<IciDTO>();
			
			List<SitTIciOggetto> oggettiIci =  this.getOggettiByUI(ro);
			for(SitTIciOggetto oggetto : oggettiIci){
				IciDTO ici = new IciDTO();
				RicercaOggettoIciDTO roi = new RicercaOggettoIciDTO();
				roi.setIdExt(oggetto.getIdExt());
				List<VTIciSoggAll> soggetti = iciDAO.getListaSoggettiByOgg(roi);
				ici.setOggetto(oggetto);
				ici.setSoggetti(soggetti);
				lista.add(ici);
			}
			
			return lista;
		}

	@Override
	public List<SitTIciOggetto> getListaOggettiByListaIdOggDWh(List<SitTIciOggetto> listaKey) {
		return iciDAO.getListaOggettiByListaIdOggDWh(listaKey);
	}
	
}
