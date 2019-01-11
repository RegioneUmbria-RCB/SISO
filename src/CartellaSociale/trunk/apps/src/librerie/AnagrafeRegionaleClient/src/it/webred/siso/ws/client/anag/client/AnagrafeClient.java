package it.webred.siso.ws.client.anag.client;



import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.siso.ws.client.anag.model.Find;
import it.webred.siso.ws.client.anag.model.Find.ListRecord.Record;
import it.webred.siso.ws.client.anag.model.Get;
import it.webred.siso.ws.client.anag.model.Input;
import it.webred.siso.ws.client.anag.model.OpenSession;
import it.webred.siso.ws.client.anag.model.Output;
import it.webred.siso.ws.client.anag.model.SiancPazientePazienteBean;
import it.webred.siso.ws.client.client.SisoWSClient;
import it.webred.siso.ws.client.client.exception.FaultResponseException;
import it.webred.siso.ws.client.client.exception.SisoClientException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class AnagrafeClient extends SisoWSClient {
	URL urlWSDL;
	AnagrafeClientContext context = null;
	
	public AnagrafeClient(URL urlWSDL) throws AnagrafeException {
		try {	
			this.urlWSDL = urlWSDL;
			context = new AnagrafeClientContext(urlWSDL);
		} catch (Exception e) {
			throw new AnagrafeException(e);
		}
	}

	private OpenSession openSession;
	private SiancPazientePazienteBean paziente;
	

	public void  openSession(RicercaAnagraficaBean rab)
			throws AnagrafeException, AnagrafeSessionException {
		try {

			
			OpenSession openSession = new OpenSession();
			openSession.setUser(rab.getUsername());
			openSession.setPassword(rab.getPassword());
			openSession.setEntita("FIND_PAZ");

			Input input = new Input();
			input.setOpenSession(openSession);
			
			
			context.setInput(input);

			execute(context);
			
			
			Output output = (Output) context.getOutput();
			
			if (output == null || output.getOpenSession() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_INVALID_LOGIN);
			}
			this.openSession = output.getOpenSession();
	
		} catch (Exception e) {
			throw new AnagrafeException(e);
		}

	}
	
	public void closeSession() throws AnagrafeException,
	AnagrafeSessionException {
	try {
		if (this.openSession == null
				|| this.openSession.getSessionID() == null) {
			throw new AnagrafeSessionException(
					AnagrafeSessionException.ERROR_NO_SESSION_OPENED_ON_CLOSE);
		}
		
	
	
		Input payload = new Input();
		payload.setCloseSession();
		payload.setSessionID(this.openSession.getSessionID());
	
		context.setInput(payload);
	
		execute(context);
		
		
		Output out = (Output) context.getOutput();
		
	
		if (out == null || out.getCloseSession() == null) {
			throw new AnagrafeSessionException(
					AnagrafeSessionException.ERROR_CLOSE);
		} else {
			this.openSession = null;
		}
	} catch (Exception e) {
		throw new AnagrafeException(e);
	}

}
	
	private String find(RicercaAnagraficaBean rab) throws AnagrafeException,
	AnagrafeSessionException {
	try {

		if (this.openSession == null
				|| this.openSession.getSessionID() == null) {
			throw new AnagrafeSessionException(
					AnagrafeSessionException.ERROR_NO_SESSION_OPENED_ON_CLOSE);
		}
		
	
	
		Input payload = new Input();
		payload.setSessionID(this.openSession.getSessionID());
		Find find = new Find();
		find.setQueryName("sianc.paziente.PazienteFind");
		find.setResultListField(new Find.ResultListField());
		find.getResultListField().getField().add("PZ_ID");
		Find.ListFilter listFilter = new Find.ListFilter();
		Find.ListFilter.Filter filtroCF = new Find.ListFilter.Filter();
		filtroCF.setName("codiceFiscaleISISTP");
		filtroCF.setValue(rab.getCodiceFiscale());
		listFilter.getFilter().add(filtroCF);
		find.setListFilter(listFilter);
		payload.setFind(find);
	

	
		context.setInput(payload);
		execute(context);
		Output out = (Output) context.getOutput();

		if (out == null || out.getFind() == null
				|| out.getFind().getListRecord() == null
				|| out.getFind().getListRecord().getRecord() == null
				|| out.getFind().getListRecord().getRecord().isEmpty()) {
			return null;
		}
	
		return out.getFind().getListRecord().getRecord().get(0).getField()
				.get(0).getValue();


	} catch (Exception e) {
	
		throw new AnagrafeException(e);
	}

}


	


	public SiancPazientePazienteBean get(RicercaAnagraficaBean rab)
			throws AnagrafeException, AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}

			String idPaziente = find(rab);
			if (idPaziente == null) {
				return null;
			}

			
			Get get = new Get();
			get.setListKey(new Get.ListKey());
			get.getListKey().setIdPaziente(idPaziente);

			Input payload = new Input();
			payload.setGet(get);
			payload.setSessionID(this.openSession.getSessionID());

			context.setInput(payload);
			execute(context);
			Output out = (Output) context.getOutput();
	
			
			if (out == null || out.getGet() == null
					|| out.getGet().getPazienteBean() == null) {
				return null;
			}
			return out.getGet().getPazienteBean();
		} catch (Exception e) {
			throw new AnagrafeException(e);
		}

	}

	public List<PersonaFindResult> findCognome(RicercaAnagraficaBean rab)
			throws AnagrafeException, AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}
			Input payload = new Input();
			payload.setSessionID(this.openSession.getSessionID());
			Find find = new Find();
			find.setQueryName("sianc.paziente.PazienteFind");
			find.setResultListField(new Find.ResultListField());
			find.getResultListField().getField().add("PZ_ID");
			find.getResultListField().getField().add("PZ_COGN");
			find.getResultListField().getField().add("PZ_NOME");
			find.getResultListField().getField().add("PZ_DT_NAS");
			Find.ListFilter listFilter = new Find.ListFilter();
			Find.ListFilter.Filter filtroCognome = new Find.ListFilter.Filter();
			filtroCognome.setName("cognomePaziente");
			filtroCognome.setValue(rab.getCognomePaziente());
			listFilter.getFilter().add(filtroCognome);
			find.setListFilter(listFilter);
			payload.setFind(find);

			
			context.setInput(payload);
			try {
				try {
					execute(context);
				} catch (FaultResponseException e) {
					throw new AnagrafeException(e);
				}
			} catch (SisoClientException e) {
				throw new AnagrafeException(e);
			}
			Output out = (Output) context.getOutput();

			
			if (out == null || out.getFind() == null
					|| out.getFind().getListRecord() == null
					|| out.getFind().getListRecord().getRecord() == null
					|| out.getFind().getListRecord().getRecord().isEmpty()) {
				return null;
			}

			List<Record> records = out.getFind().getListRecord().getRecord();
			List<PersonaFindResult> listResult = new ArrayList<PersonaFindResult>();
			for (int i = 0; i < records.size(); i++) {
				PersonaFindResult temp = new PersonaFindResult();
				temp.setIdPaziente(records.get(i).getField().get(0).getValue());
				temp.setCognome(records.get(i).getField().get(1).getValue());
				temp.setNome(records.get(i).getField().get(2).getValue());
				temp.setDataNascita(records.get(i).getField().get(3).getValue());
				listResult.add(temp);
			}
			return listResult;
		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}


	public List<PersonaFindResult> findNome(RicercaAnagraficaBean rab)
			throws AnagrafeException, AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}
			Input payload = new Input();
			payload.setSessionID(this.openSession.getSessionID());
			Find find = new Find();
			find.setQueryName("sianc.paziente.PazienteFind");
			find.setResultListField(new Find.ResultListField());
			find.getResultListField().getField().add("PZ_ID");
			find.getResultListField().getField().add("PZ_COGN");
			find.getResultListField().getField().add("PZ_NOME");
			find.getResultListField().getField().add("PZ_DT_NAS");
			Find.ListFilter listFilter = new Find.ListFilter();
			Find.ListFilter.Filter filtroNome = new Find.ListFilter.Filter();
			filtroNome.setName("nomePaziente");
			filtroNome.setValue(rab.getNomePaziente());
			listFilter.getFilter().add(filtroNome);
			find.setListFilter(listFilter);
			payload.setFind(find);

			context.setInput(payload);
			try {
				try {
					execute(context);
				} catch (FaultResponseException e) {
					throw new AnagrafeException(e);
				}
			} catch (SisoClientException e) {
				throw new AnagrafeException(e);
			}
			Output out = (Output) context.getOutput();
			
			
			
			if (out == null || out.getFind() == null
					|| out.getFind().getListRecord() == null
					|| out.getFind().getListRecord().getRecord() == null
					|| out.getFind().getListRecord().getRecord().isEmpty()) {
				return null;
			}

			List<Record> records = out.getFind().getListRecord().getRecord();
			List<PersonaFindResult> listResult = new ArrayList<PersonaFindResult>();
			for (int i = 0; i < records.size(); i++) {
				PersonaFindResult temp = new PersonaFindResult();
				temp.setIdPaziente(records.get(i).getField().get(0).getValue());
				temp.setCognome(records.get(i).getField().get(1).getValue());
				temp.setNome(records.get(i).getField().get(2).getValue());
				temp.setDataNascita(records.get(i).getField().get(3).getValue());
				listResult.add(temp);
			}
			return listResult;
		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}

	public List<PersonaFindResult> findCognomeNome(RicercaAnagraficaBean rab)
			throws AnagrafeException, AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}
			Input payload = new Input();
			payload.setSessionID(this.openSession.getSessionID());
			Find find = new Find();
			find.setQueryName("sianc.paziente.PazienteFind");
			find.setResultListField(new Find.ResultListField());
			find.getResultListField().getField().add("PZ_ID");
			find.getResultListField().getField().add("PZ_COGN");
			find.getResultListField().getField().add("PZ_NOME");
			find.getResultListField().getField().add("PZ_DT_NAS");
			find.getResultListField().getField().add("PZ_CFIS");

			Find.ListFilter listFilter = new Find.ListFilter();
			Find.ListFilter.Filter filtroCognome = new Find.ListFilter.Filter();
			filtroCognome.setName("cognomePaziente");
			filtroCognome.setValue(rab.getCognomePaziente());
			listFilter.getFilter().add(filtroCognome);
			Find.ListFilter.Filter filtroNome = new Find.ListFilter.Filter();
			filtroNome.setName("nomePaziente");
			filtroNome.setValue(rab.getNomePaziente());
			listFilter.getFilter().add(filtroNome);
			find.setListFilter(listFilter);

			payload.setFind(find);

			context.setInput(payload);
			try {
				try {
					execute(context);
				} catch (FaultResponseException e) {
					throw new AnagrafeException(e);
				}
			} catch (SisoClientException e) {
				throw new AnagrafeException(e);
			}
			Output out = (Output) context.getOutput();
			

			
			if (out == null || out.getFind() == null
					|| out.getFind().getListRecord() == null
					|| out.getFind().getListRecord().getRecord() == null
					|| out.getFind().getListRecord().getRecord().isEmpty()) {
				return null;
			}

			List<Record> records = out.getFind().getListRecord().getRecord();
			List<PersonaFindResult> listResult = new ArrayList<PersonaFindResult>();
			for (int i = 0; i < records.size(); i++) {
				PersonaFindResult temp = new PersonaFindResult();
				temp.setIdPaziente(records.get(i).getField().get(0).getValue());
				temp.setCognome(records.get(i).getField().get(1).getValue());
				temp.setNome(records.get(i).getField().get(2).getValue());
				temp.setDataNascita(records.get(i).getField().get(3).getValue());
				temp.setCodfisc(records.get(i).getField().get(4).getValue());
				listResult.add(temp);
			}
			return listResult;
		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}
	
	public PersonaFindResult getDatiAnagraficiBaseByIdPaziente(RicercaAnagraficaBean rab) throws AnagrafeException, AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}

			if (rab.getIdPaziente() == null) {
				return null;
			}

			String idPaziente = rab.getIdPaziente();

			Get get = new Get();
			get.setListKey(new Get.ListKey());
			get.getListKey().setIdPaziente(idPaziente);

			Input payload = new Input();
			payload.setGet(get);
			payload.setSessionID(this.openSession.getSessionID());

			context.setInput(payload);
			try {
				try {
					execute(context);
				} catch (FaultResponseException e) {
					throw new AnagrafeException(e);
				}
			} catch (SisoClientException e) {
				throw new AnagrafeException(e);
			}
			Output out = (Output) context.getOutput();
			
			
			if (out == null || out.getGet() == null
					|| out.getGet().getPazienteBean() == null) {
				return null;
			}

			SiancPazientePazienteBean dto = out.getGet().getPazienteBean();
			PersonaFindResult p = new PersonaFindResult();
			p.setCognome(dto.getCognomePaziente().getValue());
			p.setNome(dto.getNomePaziente().getValue());
			p.setDataNascita(dto.getDataNascita().getValue());
			p.setCodfisc(dto.getCodiceFiscale().getValue());
			p.setDataMor(dto.getDataDecesso().getValue());
			p.setSesso(dto.getSesso().getValue());
			p.setIstatComNas(dto.getCodiceISTATComuneNascita().getValue());
			p.setDesComNas(dto.getDescrizioneComuneNascita().getValue());
			//p.setSiglaProvNas(null);
			p.setCodStatoNas(null);
			//p.setDesStatoNas(null);
			p.setCodIstatCittadinanza(dto.getCodiceISTATCittadinanza().getValue());
			p.setStatoCivile(dto.getCodiceStatoCivile().getValue());
			p.setCodiceRegionaleMedico(dto.getCodiceRegionaleMedico().getValue());
			p.setNumeroTesseraSanitaria(dto.getNumeroTesseraSanitaria().getValue());
			p.setIstatComResidenza(dto.getCodiceISTATComuneResidenza().getValue());
			p.setIndirizzoResidenza(dto.getIndirizzoResidenza().getValue());
			p.setCivicoResidenza(dto.getNumeroCivicoResidenza().getValue());
			
			p.setIstatComDomicilio(dto.getCodiceISTATComuneDomicilio().getValue());
			p.setIndirizzoDomicilio(dto.getIndirizzoDomicilio().getValue());
			p.setCivicoDomicilio(dto.getNumeroCivicoDomicilio().getValue());
			
			return p;

		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}

	public PersonaFindResult findDatiAnagraficiBaseByIdPaziente(
			RicercaAnagraficaBean rab) throws AnagrafeException,
			AnagrafeSessionException {
		try {
			if (this.openSession == null
					|| this.openSession.getSessionID() == null) {
				throw new AnagrafeSessionException(
						AnagrafeSessionException.ERROR_NO_SESSION_OPENED);
			}
			Input payload = new Input();
			payload.setSessionID(this.openSession.getSessionID());
			Find find = new Find();
			find.setQueryName("sianc.paziente.PazienteFind");
			find.setResultListField(new Find.ResultListField());

			find.getResultListField().getField().add("PZ_COGN");
			find.getResultListField().getField().add("PZ_NOME");
			find.getResultListField().getField().add("PZ_DT_NAS");
			find.getResultListField().getField().add("PZ_CFIS");
			find.getResultListField().getField().add("PZ_DT_DEC");
			find.getResultListField().getField().add("PZ_SESSO");
			// identificativo comune o stato estero di nascita 
			find.getResultListField().getField().add("PZ_COM_NAS");
			// descrizione comune nascita(ITALIA) 
			find.getResultListField().getField().add("CNAS_CM_DESC");
			// descrizione comune nascita(ESTERO) 
			//find.getResultListField().getField().add("PZ_DCOM_NAS");
			// find.getResultListField().getField().add(""); MANCA SIGLA
			// PROVINCIA NASCITA
			// stato civile
			find.getResultListField().getField().add("PZ_STACIV");
			find.getResultListField().getField().add("PZ_MEDICO");

			Find.ListFilter listFilter = new Find.ListFilter();
			Find.ListFilter.Filter filtroRicerca = new Find.ListFilter.Filter();
			filtroRicerca.setName("idPaziente");
			filtroRicerca.setValue(rab.getIdPaziente().toString());
			listFilter.getFilter().add(filtroRicerca);
			find.setListFilter(listFilter);

			payload.setFind(find);

			context.setInput(payload);
			try {
				try {
					execute(context);
				} catch (FaultResponseException e) {
					throw new AnagrafeException(e);
				}
			} catch (SisoClientException e) {
				throw new AnagrafeException(e);
			}
			Output out = (Output) context.getOutput();
			
			if (out == null || out.getFind() == null
					|| out.getFind().getListRecord() == null
					|| out.getFind().getListRecord().getRecord() == null
					|| out.getFind().getListRecord().getRecord().isEmpty()) {
				return null;
			}

			Record p = out.getFind().getListRecord().getRecord().get(0);
			PersonaFindResult pfr = new PersonaFindResult();

			pfr.setCognome(p.getField().get(0).getValue());
			pfr.setNome(p.getField().get(1).getValue());
			pfr.setDataNascita(p.getField().get(2).getValue());
			pfr.setCodfisc(p.getField().get(3).getValue());
			pfr.setDataMor(p.getField().get(4).getValue());
			pfr.setSesso(p.getField().get(5).getValue());
			// pfr.setDesStatoNas(p.getField().get(6).getValue());
			pfr.setIstatComNas(p.getField().get(6).getValue());
			pfr.setDesComNas(p.getField().get(7).getValue());
			// pfr.setSiglaProvNas(p.getField().get().getValue());
			pfr.setCodStatoNas(p.getField().get(6).getValue());
			pfr.setStatoCivile(p.getField().get(8).getValue());
			pfr.setCodiceRegionaleMedico(p.getField().get(8).getValue());
			
			return pfr;
		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}


	public SiancPazientePazienteBean getPaziente() {
		return paziente;
	}

	public void setPaziente(SiancPazientePazienteBean paziente) {
		this.paziente = paziente;
	}

	@Override
	protected com.sun.xml.bind.marshaller.NamespacePrefixMapper getNamespacePrefixMapper() {
		// TODO Auto-generated method stub
		return null;
	}



}
