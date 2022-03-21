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

	public List<PersonaFindResult> findDatiAnagrafici(RicercaAnagraficaBean rab)
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
			
/*			<resultListField>
 * 
			<Field>PZ_ID</Field>			<!-- Codice -->
			<Field>PZ_CFIS</Field>			<!-- Codice fiscale -->
			<Field>PZ_TSAN</Field>			<!-- Tessera sanitaria -->
			<Field>PZ_DSCADTESS</Field>		<!-- Data di scadenza tessera sanitaria -->
			<Field>PZ_ISI</Field>			<!-- Codice ISI per stranieri -->
			<Field>PZ_STP</Field>			<!-- Codice STP per stranieri temporaneamente presenti -->
			<Field>PZ_DRILSTP</Field>		<!-- Data di rilascio STP -->
			<Field>PZ_DSCADSTP</Field>		<!-- Data di scadenza STP -->
			<Field>PZ_AIRE</Field>			<!-- Codice AIRE -->
			<Field>PZ_DSCADAIRE</Field>		<!-- Data di scadenza della tessera AIRE -->
			<Field>PZ_CODICE_OLD</Field>	<!-- Codice vecchia anagrafe regionale/aziendale -->
			<Field>PZ_NORMALIZZATO</Field>	<!-- Cognome e nome normalizzati nel formato COGNOME*NOME -->
			<Field>PZ_COGN</Field>			<!-- Cognome -->
			<Field>PZ_NOME</Field>			<!-- Nome -->
			<Field>PZ_DT_NAS</Field>		<!-- Data nascita -->
			<Field>PZ_DT_DEC</Field>		<!-- Data di decesso -->
			<Field>PZ_SESSO</Field>			<!-- Sesso -->
			<Field>PZ_COM_NAS</Field>		<!-- Identificativo comune (o stato estero) di nascita -->
			<Field>CNAS_CM_DESC</Field>		<!-- Descrizione comune di nascita -->
			<Field>PZ_DCOM_NAS</Field>		<!-- Descrizione del comune di nascita (per stranieri) -->
			<Field>PZ_CITT</Field>			<!-- Identificativo della cittadinanza -->
			<Field>DESC_CITT</Field>		<!-- Descrizione cittadinanza -->
			<Field>PZ_FSTATO</Field>		<!-- FLAG Stato Paziente: Attivo Disattivo -->
			<Field>PZ_CATEGORIA_CITT</Field>		<!-- Identificativo Categoria cittadino -->
			<Field>STATO</Field>			<!-- Stato -->
			<Field>PZ_REG_RES</Field>		<!-- REGIONI - CODICE INTERNO -->
			<Field>PZ_COM_RES</Field>		<!-- Identificativo del comune di residenza -->
			<Field>CRES_CM_DESC</Field>		<!-- Descrizione comune di residenza -->
			<Field>PZ_DCOM_RES</Field>		<!-- Descrizione del comune di residenza per residenti all'estero -->
			<Field>PZ_CAP_RES</Field>		<!-- C.A.P. di residenza -->
			<Field>PZ_IND_RES</Field>		<!-- Indirizzo di residenza -->
			<Field>PZ_STRADA_RES</Field>	<!-- Strada Residenza (Stradario) -->
			<Field>PZ_NCIV_RES</Field>		<!-- Numero civico di residenza -->
			<Field>PZ_COM_DOM</Field>		<!-- Identificativo del comune di domicilio -->
			<Field>DESC_COM_DOM</Field>		<!-- Descrizione comune di domicilio -->
			<Field>PZ_DCOM_DOM</Field>		<!-- Descrizione comune domicilio per comuni esteri -->
			<Field>PZ_CAP_DOM</Field>		<!-- CAP di domicilio -->
			<Field>PZ_IND_DOM</Field>		<!-- Indirizzo di domicilio -->
			<Field>PZ_STRADA_DOM</Field>	<!-- Strada domicilio (Stradario) -->
			<Field>PZ_NCIV_DOM</Field>		<!-- Numero civico di domicilio -->
			<Field>PZ_ASL_APP</Field>		<!-- ASL di appartenenza (o di residenza) -->
			<Field>DESC_ASL_APP</Field>		<!-- Descrizione ASL Appartenenza -->
			<Field>PZ_REG_ASSI</Field>		<!-- Regione di assistenza (o di domicilio) -->
			<Field>PZ_ASL_ASS</Field>		<!-- ASL di assistenza (o di domicilio) -->
			<Field>DESC_ASL_ASS</Field>		<!-- Descrizione ASL Assistenza -->
			<Field>PZ_ASL_PRO</Field>			<!-- ASL di provenienza -->
			<Field>PZ_TUTORE</Field>			<!-- Tutore per compatibilità SACS -->
			<Field>PZ_MOT_CESASS</Field>		<!-- Motivo Cessazione Assistenza -->
			<Field>PZ_NUCLEO_FAMILIARE</Field>	<!-- Nucleo Familiare -->
			<Field>PZ_POS_ANAGRAFICA</Field>	<!-- Identificativo posizione anagrafica -->
			<Field>PZ_COMUNITA</Field>		<!-- Identificativo casa protetta, casa di riposo, comunita, ecc. -->
			<Field>PZ_FTUTORE</Field>		<!-- Flag di esistenza del tutore -->
			<Field>PZ_TEL1</Field>			<!-- Recapito telefonico 1 -->
			<Field>PZ_TEL2</Field>			<!-- Recapito telefonico 2 -->
			<Field>PZ_TEL3</Field>			<!-- Recapito telefonico 3 -->
			<Field>PZ_FLAGPRIV</Field>		<!-- Flag per Privacy -->
			<Field>PZ_STACIV</Field>		<!-- Identificativo dello stato civile -->
			<Field>PZ_COM_LAV</Field>		<!-- Identificativo del comune di lavoro -->
			<Field>PZ_COM_DEC</Field>		<!-- Identificativo del comune di decesso -->
			<Field>PZ_CAU_DEC</Field>		<!-- Identificativo della causa del decesso IDC_9 -->
			<Field>PZ_FCERTIF</Field>		<!-- Flag per paziente (C)ertificato / (T)emporaneo -->
			<Field>PZ_FCOMPL</Field>		<!-- Flag per record (C)ompleto / (P)arziale -->
			<Field>PZ_UT_CERT</Field>		<!-- Utente di certificazione -->
			<Field>PZ_DT_CERT</Field>		<!-- Data di certificazione -->
			<Field>PZ_CONIUGE</Field>		<!-- Cognome del coniuge -->
			<Field>PZ_DOCUMENTO</Field>		<!-- Numero di un documento (es. 'CI: 123456789') -->
			<Field>PZ_DIS_RES</Field>		<!-- Distretto di residenza -->
			<Field>PZ_ASL_EMI</Field>		<!-- ASL di emigrazione -->
			<Field>PZ_STRANIERO</Field>		<!-- Straniero (L)egalmente o (I)llegalmente presente -->
			<Field>PZ_FCEE</Field>			<!-- Flag CEE -->
			<Field>PZ_DIS_DOM</Field>		<!-- Distretto di domicilio -->
			<Field>PZ_COM_IMM</Field>		<!-- Identificativo comune di immigrazione (provenienza) -->
			<Field>PZ_DCOM_IMM</Field>		<!-- Descrizione comune di immigrazione (provenienza) -->
			<Field>PZ_DT_IMM</Field>		<!-- Data di immigrazione -->
			<Field>PZ_COM_EMI</Field>		<!-- Identificativo comune di emigrazione (destinazione) -->
			<Field>PZ_DCOM_EMI</Field>		<!-- Descrizione comune di emigrazione (destinazione) -->
			<Field>PZ_DT_EMI</Field>		<!-- Data di emigrazione -->
			<Field>PZ_MEDICO</Field>		<!-- Ultimo medico valido -->
			<Field>PZ_DT_MEDICO</Field>		<!-- Data di scelta dell'ultimo medico valido -->
			<Field>WITH_ALIAS</Field>		<!-- Presenza alias -->
			<Field>MASTER_ID</Field>		<!-- Id Master -->
		</resultListField>*/

			
			find.setQueryName("sianc.paziente.PazienteFind");
			find.setResultListField(new Find.ResultListField());
			find.getResultListField().getField().add("PZ_ID");
			find.getResultListField().getField().add("PZ_COGN");
			find.getResultListField().getField().add("PZ_NOME");
			find.getResultListField().getField().add("PZ_DT_NAS");
			find.getResultListField().getField().add("PZ_CFIS");
			find.getResultListField().getField().add("PZ_DT_DEC");

			Find.ListFilter listFilter = new Find.ListFilter();
			
/*			<!--
			idPaziente				Identificativo del paziente
			normalizzata			Se ‘S’ la ricerca su nomePaziente e cognomePaziente è normalizzata
			cognomePaziente			Cognome del paziente
			nomePaziente			Nome del paziente
			sesso					Sesso
			dataNascitaDa			Inizio range per data di nascita
			dataNascitaA			Fine range per data di nascita
			codiceComuneNascita		Codice del comune di nascita
			numeroTesseraSanitaria	Numero della tessera sanitaria
			codiceFiscaleISISTP		Codice fiscale,ISI o STP
			aslAppartenenza		    Asl di appartenenza
			codiceComuneResidenza	Codice del comune di residenza
			flagStato				Flag stato Attivo/Disattivo
			elencoCodici			Elenco identificativi paziente separato da virgola
			codiceOld				Corrispondente al campo PZ_CODICE_OLD
			righeMassime			Numero massimo di righe da estrarre
			ordinamentoRicerca		Campo di ordinamento della ricerca
			-->
*/

			
			this.addFiltro("cognomePaziente", rab.getCognomePaziente(), listFilter);
			this.addFiltro("nomePaziente", rab.getNomePaziente(), listFilter);
			this.addFiltro("codiceFiscaleISISTP", rab.getCodiceFiscale(), listFilter);
			this.addFiltro("sesso", rab.getSesso(), listFilter);
			this.addFiltro("dataNascitaDa", rab.getDataNascitaDa(), listFilter);
			this.addFiltro("dataNascitaA", rab.getDataNascitaA(), listFilter);
			
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
				temp.setDataMor(records.get(i).getField().get(5).getValue());
				listResult.add(temp);
			}
			return listResult;
		} catch (JAXBException e) {
			throw new AnagrafeException(e);
		}

	}
	
	private void addFiltro(String nome, String value, Find.ListFilter listFilter){
		if(value!=null){
			Find.ListFilter.Filter filtro = new Find.ListFilter.Filter();
			filtro.setName(nome);
			filtro.setValue(value.trim());
			listFilter.getFilter().add(filtro);
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
			
			String istatNascita = dto.getCodiceISTATComuneNascita().getValue();
			String descNascita = dto.getDescrizioneComuneNascita().getValue();
			if(istatNascita!=null && istatNascita.length()==3){
				p.setCodStatoNas(istatNascita);
				p.setDesStatoNas(descNascita);
			}else{
				p.setIstatComNas(istatNascita);
				p.setDesComNas(istatNascita);
			}
			
			p.setCodIstatCittadinanza(dto.getCodiceISTATCittadinanza().getValue());
			p.setStatoCivile(dto.getCodiceStatoCivile().getValue());
			
			p.setCodiceRegionaleMedico(dto.getCodiceRegionaleMedico().getValue());
			p.setNumeroTesseraSanitaria(dto.getNumeroTesseraSanitaria().getValue());
			p.setMedicoDataScelta(dto.getDataSceltaMedico().getValue());
			p.setMedicoDataRevoca(dto.getDataRinunciaMedico().getValue());
			
			p.setIstatComResidenza(dto.getCodiceISTATComuneResidenza().getValue());
			p.setIndirizzoResidenza(dto.getIndirizzoResidenza().getValue());
			p.setCivicoResidenza(dto.getNumeroCivicoResidenza().getValue());
			
			p.setIstatComDomicilio(dto.getCodiceISTATComuneDomicilio().getValue());
			p.setIndirizzoDomicilio(dto.getIndirizzoDomicilio().getValue());
			p.setCivicoDomicilio(dto.getNumeroCivicoDomicilio().getValue());
			String idPazienteOut = dto.getIdPaziente().getValue();
			p.setIdPaziente(idPazienteOut);
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
			this.addFiltro("idPaziente", rab.getIdPaziente().toString(), listFilter);
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
			pfr.setCodStatoNas(p.getField().get(6).getValue());
			pfr.setDesComNas(p.getField().get(7).getValue());
			// pfr.setSiglaProvNas(p.getField().get().getValue());
			pfr.setStatoCivile(p.getField().get(8).getValue());
			pfr.setCodiceRegionaleMedico(p.getField().get(9).getValue());
			
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
