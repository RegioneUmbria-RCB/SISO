package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.DatiPorDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.fse.FseSearchCriteria;
import it.webred.cs.csa.ejb.dto.fse.ListaFseDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruInputDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruOutputDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsTbIngMercato;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;



@Stateless
public class AccessTableDatiPorSessionBean extends CarSocialeBaseSessionBean implements AccessTableDatiPorSessionBeanRemote {
	
	@Autowired
	private DatiPorDAO dao;

	@EJB
	private AccessTableDominiSiruSessionBeanRemote dominiSiruService;

	@Override
	public CsExtraFseDatiLavoro getCsCDatiLavoroById(BaseDTO dto) {	
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return dao.getCsCDatiLavoroById((Long)dto.getObj());
		else
			return null;
	}

	@Override
	public CsExtraFseDatiLavoro findDatiPorUdcBySchedaId(BaseDTO dto){
		return dao.findDatiPorUdcBySchedaId((Long)dto.getObj());
	}
	
	@Override
	public CsExtraFseDatiLavoro saveDatiPor(BaseDTO dto){
		CsExtraFseDatiLavoro lavoro = (CsExtraFseDatiLavoro) dto.getObj();
		lavoro = this.fillDatiLavoroPor(lavoro, dto.getUserId());
		return dao.saveDatiPor(lavoro);
	}
	

	@Override
	public void eliminaDatiPor(BaseDTO dto) {
		dao.eliminaDatiPor((Long)dto.getObj());
	}
	
	@Override
	public CsExtraFseDatiLavoro fillDatiLavoroPor(CsExtraFseDatiLavoro dl, String username){
		if(dl!=null){
			
			if (dl.getId()==null) {
				dl.getMaster().setDtIns(new Date());
				dl.getMaster().setUsrIns(username);
				dl.setUserIns(username);
				dl.setDtIns(new Date());
			} else {
				dl.setUserMod(username);
				dl.setDtMod(new Date());
				dl.getMaster().setUsrMod(username);
				dl.getMaster().setDtMod(new Date());
			}
			
			if(dl.getMaster().getSiru()!=null){
				Long masterId = dl.getMaster().getId();
				if(masterId==null)
					dl.getMaster().getSiru().setMaster(dl.getMaster());
				else{
					dl.getMaster().getSiru().setId(masterId);
					dl.getMaster().getSiru().setMaster(dl.getMaster());
				}
			}
		}
		return dl;
	}
	
	
	@Override
	public SiruResultDTO validaSiru(BaseDTO dto) {
		try{
			SiruInputDTO spd = (SiruInputDTO) dto.getObj();
			
			ConfigurazioneFseDTO mappaConf  = (ConfigurazioneFseDTO)dto.getObj2();
			SiruResultDTO result = new SiruResultDTO();
			List<String> msg = new ArrayList<String>();
			SiruOutputDTO siru = new SiruOutputDTO();
			
			siru.setSesso(spd.getSesso()!=null ? spd.getSesso().charAt(0) : null);
			siru.setDataNascita(spd.getDataNascita());
			
			String msgSiru = " selezionato non presente nel ";
			boolean occupato = false;
			boolean disoccupato = false;
			boolean inCercaPrimaOccupazione = false;
			boolean regMarche = false;
			String modelloPor = this.getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
			if (!StringUtils.isBlank(modelloPor))
				regMarche = modelloPor.contains("Marche");
			
			msgSiru+=regMarche ? "tracciato POR-FSE" :  "SIRU";
	
			CsTbIngMercato ingMercato = spd.getCsTbIngMercato();
			if(ingMercato != null &&  DataModelCostanti.TipiIngMercato.OCCUPATO.equalsIgnoreCase(ingMercato.getId())){
				occupato = true;
			}
			if(ingMercato != null && DataModelCostanti.TipiIngMercato.DISOCCUPATO.equalsIgnoreCase(ingMercato.getId())){
				disoccupato = true;
			}
			if(ingMercato != null && DataModelCostanti.TipiIngMercato.CERCA_PRIMA_OCCUPAZIONE.equalsIgnoreCase(ingMercato.getId())){
				inCercaPrimaOccupazione = true;
			}
	
			// titolo di studio
			if (spd.getIdTitoloStudio() != null) {
				SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.TITOLO_STUDIO.name(),spd.getIdTitoloStudio());
				if (sd == null) msg.add("Titolo di studio"+msgSiru);
				else siru.setTitoloStudio(sd.getCodiceSiru());
			}
	
			// condizione mercato ingresso
			if(ingMercato != null && !StringUtils.isBlank(ingMercato.getId()) && !StringUtils.isBlank(ingMercato.getDescrizione())){ 
				SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.CONDIZIONE_MERCATO.name(), spd.getCsTbIngMercato().getId());
				if(sd==null) msg.add("Condizione di ingresso nel mondo del lavoro"+msgSiru);
				else siru.setCondMercatoIngresso(sd.getCodiceSiru());
			}else if(ingMercato == null || StringUtils.isBlank(ingMercato.getId()) || StringUtils.isBlank(ingMercato.getDescrizione())){
				msg.add("Inserire un valore valido per 'Condizione nel mercato di lavoro in ingresso'");
			}
	
			// ateco
			if(mappaConf.getAzCodAteco().isAbilitato()){
				if (!StringUtils.isBlank(spd.getAzCodAteco())) {
					SiruDominioDTO sisoAteco = dominiSiruService.findById(SiruEnum.SISO_ATECO.name(),spd.getAzCodAteco());
					SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.ATECO.name(),sisoAteco.getCodiceSiru() + "_" + sisoAteco.getVersione());
					if (sd == null) msg.add("Ateco"+msgSiru);
					else siru.setCodAtecoAnno(sd.getCodiceSiru());
		
				} else if (occupato && mappaConf.getAzCodAteco().isObbligatorio() && StringUtils.isBlank(spd.getAzCodAteco())) {
					msg.add("Inserire 'Cod. Ateco'");
				}
			}
	
			// dimensione azienda
			if(mappaConf.getAzDimensione().isAbilitato()){
				if (!StringUtils.isBlank(spd.getDescDimAzienda())) {
					SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.DIMENSIONE_AZIENDA.name(), spd.getDescDimAzienda());
					if (sd == null) msg.add("Dimensione azienda"+msgSiru);
					else siru.setDimensioneAziendaId(sd.getCodiceSiru());
				} else if (occupato && mappaConf.getAzDimensione().isObbligatorio() && StringUtils.isBlank(spd.getDescDimAzienda())) {
					msg.add("Inserire 'Dimensione azienda'");
				}
			}
	
			// forma giuridica rna
			if(mappaConf.getAzFormaGiuridica().isAbilitato()){
				if (!StringUtils.isBlank(spd.getAzFormaGiuridica())) {
					SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.FORMA_GIURIDICA_RNA.name(),spd.getAzFormaGiuridica());
					if (sd == null) msg.add("Forma giuridica"+msgSiru);
					else siru.setFrmGiuridicaCod(sd.getCodiceSiru());
				} else if (occupato && mappaConf.getAzFormaGiuridica().isObbligatorio() && StringUtils.isBlank(spd.getAzFormaGiuridica())) {
					msg.add("Inserire 'Forma giuridica'");
				}
			}
	
			// gruppo vulnerabile
			if (!StringUtils.isBlank(spd.getGrVulnerabilita())) {
				SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.GRUPPO_VUL_PART.name(), spd.getGrVulnerabilita());
				if (sd == null) msg.add("Gruppo vulnerabile"+msgSiru);
				else siru.setCodVulnerabilePa(sd.getCodiceSiru());
			}
	
			// tipo orario lavoro
			if(mappaConf.getLavoroOrario().isAbilitato()){
				if (!StringUtils.isBlank(spd.getDescOrarioLavoro())) {
					SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.TIPO_ORARIO_LAVORO.name(), spd.getDescOrarioLavoro());
					if (sd == null) msg.add("Orario di lavoro"+msgSiru);
					else siru.setTipoOrarioLavoroId(sd.getCodiceSiru());
				} else if (occupato && mappaConf.getLavoroOrario().isObbligatorio() && StringUtils.isBlank(spd.getDescOrarioLavoro())) {
					msg.add("Inserire 'Orario di lavoro'");
				}
			}
	
			// tipologia lavoro
			if(mappaConf.getLavoroTipo().isAbilitato()){
				if (!StringUtils.isBlank(spd.getDescTipoLavoro())) {
					SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.TIPOLOGIA_LAVORO.name(), spd.getDescTipoLavoro());
					if (sd == null) msg.add("Tipologia di lavoro"+msgSiru);
					else siru.setTipologiaLavoroId(sd.getCodiceSiru());
				} else if (occupato && mappaConf.getLavoroTipo().isObbligatorio() && StringUtils.isBlank(spd.getDescTipoLavoro())) {
					msg.add("Inserire 'Tipologia di lavoro'");
				}
			}
	
			if(mappaConf.getAzCf().isValida() || mappaConf.getAzPi().isValida()){
				if (occupato && StringUtils.isBlank(spd.getAzPi()) && StringUtils.isBlank(spd.getAzCf())) {
					msg.add("Inserire P.IVA o il Codice Fiscale dell'azienda");
				}
			}
			if (mappaConf.getAzRagioneSociale().isValida() && occupato && StringUtils.isBlank(spd.getAzRagioneSociale())) {
				msg.add("Inserire 'Ragione Sociale' dell'azienda");
			}
			if (mappaConf.getAzVia().isValida() && occupato && StringUtils.isBlank(spd.getAzVia())) {
				msg.add("Inserire 'Via' dell'azienda");
			}
			// durata ricerca
			if(mappaConf.getDurataRicercaLavoro().isAbilitato()){
				if (!StringUtils.isBlank(spd.getDurataRicLavoroId())) {
					SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.DURATA_RICERCA.name(), spd.getDurataRicLavoroId());
					if (sd == null) msg.add("Durata di ricerca"+msgSiru);
					else siru.setDurataRicerca(sd.getCodiceSiru());
				} else if ((inCercaPrimaOccupazione || disoccupato) && mappaConf.getDurataRicercaLavoro().isObbligatorio() && StringUtils.isBlank(spd.getDurataRicLavoroId())) {
					msg.add("Inserire 'Durata di ricerca'");
				}
			}
			
			// cittadinanza
			if (!StringUtils.isBlank(spd.getCittadinanza())) {
				// Recupero da AM_TAB_NAZIONI il cod.Istat della cittadinanza
				SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.AM_NAZIONALITA.name(), spd.getCittadinanza());
				// ITALIA da 1 a -> 0000
				if (sd != null && sd.getCodiceSiru().equals("1")) {
					sd.setCodiceSiru("000");
				}
				// Verifico che sia mappato correttamente in CFS_CHK_CITTADINANZAZA
				sd = dominiSiruService.findById(SiruEnum.CITTADINANZA.name(), sd.getCodiceSiru());
				if (sd == null) msg.add("Nazione"+msgSiru);
				else siru.setCittadinanza(sd.getCodiceSiru());
			}
	
			// SISO 945 INIZIO
			// comune di nascita - stato di nascita
	
			if (!StringUtils.isBlank(spd.getComuneNascitaCod())) {
				// ITALIANO
				SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(), spd.getComuneNascitaCod());
				if (sd != null) siru.setComuneNascitaId(sd.getCodiceSiru());
				else msg.add("Comune di nascita"+msgSiru);
	
			} else if (!StringUtils.isBlank(spd.getStatoNascitaCod())) {
				// ESTERO
				// Recupero da AM_TAB_NAZIONI il cod.AG_ENTRATE della nazione (es.Zxxx privato del caratter iniziale) a partire dal cod.ISTAT
				SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.IT_NAZIONE_VIEW.name(), spd.getStatoNascitaCod());
	
				if (sd == null) msg.add("Nazione di nascita"+msgSiru);
				else siru.setNazioneNascitaId(sd.getCodiceSiru());
			} else msg.add("Nazione o Comune di nascita del soggetto non valorizzati");
			// SISO 945 FINE
	
			// comune azienda
			if(mappaConf.getAzComune().isAbilitato()){
				if (!StringUtils.isBlank(spd.getAzComuneCod())) { // !regMarche &&
					SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(), spd.getAzComuneCod());
					if (sd != null) siru.setComuneAziendaId(sd.getCodiceSiru());
					else msg.add("Comune azienda"+msgSiru);
				} else if (occupato && mappaConf.getAzComune().isObbligatorio() && StringUtils.isBlank(spd.getAzComuneCod())) {
					msg.add("Inserire il Comune azienda ");
				}
			}
			// residenza
			if(mappaConf.getPagResDom().isAbilitato()){
				if(!StringUtils.isBlank(spd.getFlagResDom())){
					SiruDominioDTO sd = dominiSiruService.findByIdSiso(SiruEnum.RESIDENZA.name(), spd.getFlagResDom());
					if(sd != null) siru.setFlagResidenza(new Integer(sd.getCodiceSiru()));
					else msg.add("Flag Residenza"+msgSiru);
				}
			}
	
			// SISO-962 Inizio
			if (!StringUtils.isBlank(spd.getCodIstatComuneResidenza())) {
				SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(),spd.getCodIstatComuneResidenza());
				if (sd != null)
					siru.setComuneResId(sd.getCodiceSiru());
				else
					msg.add("Comune residenza"+msgSiru);
				
			}
			if (!StringUtils.isBlank(spd.getCodIstatComuneDomicilio())) {
				SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(),spd.getCodIstatComuneDomicilio());
				if (sd != null)
					siru.setComuneDomId(sd.getCodiceSiru());
				else
					msg.add("Comune domicilio"+msgSiru);
				
			}
			// SISO-962 Fine
	
			result.setErrori(msg);
			result.setSiru(siru);
			
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<String> validaStampa(BaseDTO dto) {
		StampaFseDTO fse = (StampaFseDTO)dto.getObj();
		ConfigurazioneFseDTO config = (ConfigurazioneFseDTO)dto.getObj2();
		List<String> msg = new ArrayList<String>();
		String nomeModulo = this.getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
		boolean stampaPorMarche = "stampaPorMarche".equals(nomeModulo) ;
		if(nomeModulo!=null && (stampaPorMarche || nomeModulo.equals("stampaPorUmbria"))){// Possibili tipologie di POR 
			
		}else{
			msg.add("Nome del modulo configurato erroneamente: "+nomeModulo +" ,contattare l'assistenza." );
			return msg;
		}
				
		if(StringUtils.isBlank(fse.getCapResidenza()))
			msg.add("Informazioni insufficienti: CAP residenza obbligatorio");
		if(StringUtils.isBlank(fse.getComuneResidenza()))
			msg.add("Informazioni insufficienti: Comune residenza obbligatorio");
		if(StringUtils.isBlank(fse.getSiglaProvResidenza()))
			msg.add("Informazioni insufficienti: Provincia residenza obbligatorio");
		if(StringUtils.isBlank(fse.getViaResidenza()))
			msg.add("Informazioni insufficienti: Via residenza obbligatorio");
			
		if(StringUtils.isBlank(fse.getTitoloStudio()))
			msg.add("Informazioni insufficienti: selezionare il titolo di studio");
		else if("Non rilevato".equalsIgnoreCase(fse.getTitoloStudio()))
			msg.add("Valore 'Non rilevato' non ammesso per 'Titolo di Studio'");
		
		if(StringUtils.isBlank(fse.getCondLavoro()))
			msg.add("Informazioni insufficienti: selezionare la 'Condizione lavorativa'");
		else if("Non rilevato".equalsIgnoreCase(fse.getCondLavoro()) )
			msg.add("Valore 'Non rilevato' non ammesso per 'Condizione lavorativa'");
		
		if(fse.isDurataRicercaLavoro() && config.getDurataRicercaLavoro().isObbligatorio() && StringUtils.isBlank(fse.getLavoroDurataRicerca()) )
			msg.add("Informazioni insufficienti: selezionare la Durata ricerca lavoro");
		
		if(fse.getComunicaVul()==null)
			msg.add("Informazioni insufficienti: indicare se l'utente comunica la vulnerabilità");
		else if(fse.getComunicaVul() && StringUtils.isBlank(fse.getIdVulnerabile()))
			msg.add("Informazioni insufficienti: selezionare la vulnerabilità");
		
		if(StringUtils.isBlank(fse.getDtSottoscrizione()) && config.getDataSottoscrizione().isValida())
			msg.add("Informazioni insufficienti: inserire il campo "+config.getDataSottoscrizione().getLabel());
		
		if(StringUtils.isBlank(fse.getSoggettoAttuatore()) && config.getSoggettoAttuatore().isValida())
			msg.add("Informazioni insufficienti: inserire il campo "+config.getSoggettoAttuatore().getLabel());
		
		if(stampaPorMarche){
			if(StringUtils.isBlank(fse.getTelefono()) && StringUtils.isBlank(fse.getCellulare()) && StringUtils.isBlank(fse.getEmail()))
				msg.add("Informazioni insufficienti: inserire almeno un recapito tra: telefono fisso, cellulare, email");
			else{
				 Matcher mTel = DataModelCostanti.patternNumTelFisso.matcher(fse.getTelefono());
				 if(!StringUtils.isBlank(fse.getTelefono()) && !mTel.matches())
					 msg.add("Formato non corretto per il campo numero di telefono: "+fse.getTelefono());

				 Matcher mCel = DataModelCostanti.patternNumTelMobile.matcher(fse.getCellulare());
				 if(!StringUtils.isBlank(fse.getCellulare()) && !mCel.matches())
					 msg.add("Formato non corretto per il campo numero di cellulare: "+fse.getCellulare());
				 
				 if(!StringUtils.isBlank(fse.getEmail())){
					EmailValidator validator = EmailValidator.getInstance();
					boolean valido = validator.isValid(fse.getEmail());
					if(!valido)
						 msg.add("Formato non corretto per il campo e-mail: "+fse.getEmail());
				 }
			}
		}
		
		return msg;
	}

	@Override
	public List<ListaFseDTO> getListaFse(FseSearchCriteria searchCriteria) {
		return dao.getListaFse(searchCriteria);
	}

	@Override
	public Integer getListaFseCount(FseSearchCriteria searchCriteria) {
		return dao.countListaFse(searchCriteria);
	}

	@Override
	public List<KeyValueDTO> findListaTipiFse(CeTBaseObject cet) {
		return dao.findListaTipiFse();
	}

	@Override
	public List<KeyValueDTO> getListaComuniResidenzaUsati(CeTBaseObject cet) {
		return dao.getListaComuniResidenzaUsati();
	}

	@Override
	public List<KeyValueDTO> findListaProgettiUsati(CeTBaseObject cet) {
		return dao.findListaProgettiUsati();
	}
}
