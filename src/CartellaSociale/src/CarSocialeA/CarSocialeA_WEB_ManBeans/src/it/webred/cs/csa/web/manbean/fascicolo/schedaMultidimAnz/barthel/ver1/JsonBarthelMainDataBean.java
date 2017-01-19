package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel.ver1;

import it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel.JsonBarthelBaseDataBean;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.dto.utility.StringIntPairBean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alessandro Feriani
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBarthelMainDataBean extends JsonBarthelBaseDataBean{

	protected String alimentazione;
	protected String bagnoDoccia;
	protected String igienePersonale;
	protected String abbigliamento;
	protected String continenzaIntestinale;
	protected String continenzaUrinaria;
	protected Integer numeroPannoloni;
	protected String usoGabinetto;
	protected String lettoCarrozzina;
	protected String deambulazione; 
	protected String scale;
	protected String usoCarrozzina;
	protected String intervistaA;
	
	protected String deambulatore;
	protected String carrozzina;
	protected String materassinoCuscinoAntidecubito;    
	protected String lettoOrtopedico;
	protected String presidioPerIncontinenza;
	protected String altraProtesi;
	protected String altraProtesiDaChi;

	@JsonIgnore protected String  			numeroPannoloniLabel;
	@JsonIgnore protected String 		   	intervistaALabel;

	@JsonIgnore protected List<SelectItem> 	listaAlimentazione;
	@JsonIgnore protected List<SelectItem> 	listaBagnoDoccia;
	@JsonIgnore protected List<SelectItem> 	listaIgienePersonale;
	@JsonIgnore protected List<SelectItem> 	listaAbbigliamento;
	@JsonIgnore protected List<SelectItem> 	listaContinenzaIntestinale;
	@JsonIgnore protected List<SelectItem> 	listaContinenzaUrinaria;
	@JsonIgnore protected List<SelectItem> 	listaUsoGabinetto;
	@JsonIgnore protected List<SelectItem> 	listaLettoCarrozzina;
	@JsonIgnore protected List<SelectItem> 	listaDeambulazione; 
	@JsonIgnore protected List<SelectItem> 	listaScale;
	@JsonIgnore protected List<SelectItem> 	listaUsoCarrozzina;
	
	@JsonIgnore protected List<String> 		listaDaChi;
	
	public JsonBarthelMainDataBean(){
		
		super();
		
		alimentazione = ""; 
		bagnoDoccia = null;
		igienePersonale = null;
		abbigliamento = null;
		continenzaIntestinale = null;
		continenzaUrinaria = null;
		usoGabinetto = null;
		lettoCarrozzina = null;
		deambulazione = null;
		scale = null;
		usoCarrozzina = null;
		intervistaA = null;
		
		intervistaALabel = "Compilata attraverso intervista a:";
		
		numeroPannoloni = null;
		numeroPannoloniLabel = "Se il soggetto ha un punteggio ≤ 5 e non è portatore di catatere vescicale a permanenza riportare il numero di pannoloni nelle 24 ore";
		
		listaAlimentazione = CreateOptionList( "A",
		new StringIntPairBean( "Capace di alimentarsi da solo quando i cibi sono preparati su di un vassoio o tavolo raggiungibile. Se usa un ausilio deve essere capace di utilizzarlo, tagliare la carne e, se lo desidera, usare il sale e pepe, spalmare il burro, ecc.", 10 ), 	
		new StringIntPairBean( "Indipendente nell'alimentarsi con i cibi preparati su di un vassoio, ad eccezione di tagliare la carne, aprire il contenitore del latte, girare il coperchio di un vasetto, ecc. Non è necessaria la presenza di un'altra persona.", 8 ),
		new StringIntPairBean( "Capace di alimentarsi da solo, con supervisione. Richiede assistenza nelle attività associate come versare il latte nel thé, usare il sale e pepe, spalmare il burro, girare un piatto di portata o altro.", 5 ),
		new StringIntPairBean( "Capace di utilizzare una posata, in genere un cucchiaio, ma qualcuno deve assistere attivamente durante il pasto.", 2 ),
		new StringIntPairBean( "Dipendente per tutti gli aspetti. Deve essere imboccato.", 0 ) );

		listaBagnoDoccia = CreateOptionList( "B",
		new StringIntPairBean( "Capace di fare il bagno in vasca, la doccia, o una spugnatura completa. Autonomo in tutte le operazioni, senza la presenza di un'altra persona, quale che sia il metodo usato", 5 ),
		new StringIntPairBean( "Necessità di supervisione per sicurezza (trasferimenti, temperatura dell'acqua, ecc.)", 4 ),
		new StringIntPairBean( "Necessita di aiuto per trasferimento nella doccia /bagno oppure nel lavarsi o asciugarsi", 3 ),
		new StringIntPairBean( "Necessita di aiuto per tutte le operazioni", 1 ),
		new StringIntPairBean( "Totale dipendenza nel lavarsi", 0 ) );
		
		listaIgienePersonale = CreateOptionList( "C",
		new StringIntPairBean( "Capace di lavarsi mani e faccia, pettinarsi, lavarsi i denti e radersi. Un uomo deve essere capace di usare, senza aiuto, qualsiasi tipo di rasoio, comprese tutte le manipolazioni necessarie. Una donna deve essere in grado di truccarsi, se abituata (non sono da considerare le attività relative all'acconciatura dei capelli)", 5 ),
		new StringIntPairBean( "In grado di attendere all'igiene personale, ma necessita di aiuto minimo prima e/o dopo le operazioni", 4 ),
		new StringIntPairBean( "Necessita di aiuto per una o più operazioni dell'igiene personale", 3 ),
		new StringIntPairBean( "Necessita di aiuto per tutte le operazioni", 1 ),
		new StringIntPairBean( "Incapace di attendere all'igiene personale, dipendente sotto tutti gli aspetti", 0 ) );
				
		listaAbbigliamento = CreateOptionList( "D", 
		new StringIntPairBean( "Capace di indossare, togliere e chiudere correttamente gli indumenti, allacciarsi le scarpe e toglierle, applicare oppure togliere un corsetto od una protesi", 10 ),
		new StringIntPairBean( "Necessita solo di un minimo aiuto per alcuni aspetti, come bottoni, cerniere, reggiseno, lacci di scarpe", 8 ),
		new StringIntPairBean( "Necessita di aiuto per mettere o togliere qualsiasi indumento", 5 ),
		new StringIntPairBean( "Capace di collaborare in qualche modo, ma dipendente sotto tutti gli aspetti", 2 ),
		new StringIntPairBean( "Dipendente sotto tutti gli aspetti e non collabora", 0 ));
		
		listaContinenzaIntestinale = CreateOptionList( "E", 
		new StringIntPairBean( "Controllo intestinale completo e nessuna perdita, capace di mettersi supposte o praticarsi un enteroclisma se necessario", 10 ),
		new StringIntPairBean( "Può necessitare di una supervisione per l'uso di supposte o enteroclisma, occasionali perdite", 8 ),
		new StringIntPairBean( "Capace di assumere una posizione appropriata, ma non può eseguire manovre facilitatorie o pulirsi da solo senza assistenza, ed ha perdite frequenti. Necessita di aiuto nell'uso dei dispositivi come pannoloni, ecc", 5 ),
		new StringIntPairBean( "Necessita di aiuto nell'assumere una posizione appropriata e necessita di manovre facilitatorie", 2 ),
		new StringIntPairBean( "Incontinente", 0 ));
		
		listaContinenzaUrinaria = CreateOptionList( "F",
		new StringIntPairBean( "Controllo completo durante il giorno e la notte e/o indipendente con i dispositivi esterni o interni", 10 ),
		new StringIntPairBean( "Generalmente asciutto durante il giorno e la notte, ha occasionalmente qualche perdita o necessita di minimo aiuto per l'uso dei dispositivi esterni o interni", 8 ),
		new StringIntPairBean( "In genere asciutoo durante il giorno ma non di notte, necessario aiuto parziale nell'uso dei dispositivi", 5 ),
		new StringIntPairBean( "Incontinente ma in grado di cooperare nell'applicazione di un dispositivo esterno o interno", 2 ),
		new StringIntPairBean( "Incontinente o catetere a dimora (sottolineare la voce che interessa). Dipendente per l'applicazione di dispositivi interni o esterni", 0 ));
		
		listaUsoGabinetto = CreateOptionList( "G", 
		new StringIntPairBean( "Capace di trasferirsi sul e dal gabinetto, gestire i vestiti senza sporcarsi, usare la carta igienica senza aiuto. Se necessario, può usare la comoda o padella, o il pappagallo, ma deve essere in grado di svuotarli e pulirli", 10 ),
		new StringIntPairBean( "Necessita di supervisione per la sicurezza con l'uso del normale gabinetto. Usa la comoda indipendentemente tranne che per svuotarla e pulirla", 8 ),
		new StringIntPairBean( "Necessita di aiuto per svestirsi/vestirsi, per i trasferimenti e per lavare le mani", 5 ),
		new StringIntPairBean( "Necessita di aiuto per tutti gli aspetti", 2 ),
		new StringIntPairBean( "Completamente dipendente", 0 ));
		
		listaLettoCarrozzina = CreateOptionList( "H",
		new StringIntPairBean( "Capace di avvicinarsi con sicurezza al letto, bloccare i freni, sollevare le pedane, trasferirsi con sicurezza sul letto, sdraiarsi, rimettersi seduto sul bordo, cambiare la posizione della carrozzina, trasferirsi con sicurezza. E' indipendente durante tutte le fasi", 15 ),
		new StringIntPairBean( "Necessaria la presenza di una persona per maggior fiducia o per supervisione a scopo di sicurezza", 12 ),
		new StringIntPairBean( "Necessario minimo aiuto da parte di una persona per uno o più aspetti del trasferimento", 8 ),
		new StringIntPairBean( "Collabora, ma richiede massimo aiuto da parte di una persona durante tutti i movimenti del trasferimento", 3 ),
		new StringIntPairBean( "Non collabora al trasferimento. Necessarie due persone per trasferire l'anziano con o senza un sollevatore meccanico", 0));
		
		listaDeambulazione = CreateOptionList( "I",
		new StringIntPairBean( "Capace di portare una protesi se necessario, bloccarla, sbloccarla, assumere la stazione eretta, sedersi e piazzare gli ausili a portata di mano. In grado di usare le stampelle, bastoni, walker e deambulare per almeno 50 mt. senza aiuto o supervisione", 15 ),
		new StringIntPairBean( "Indipendente nella deambulazione, ma con autonomia minore di 50 mt. Necessita di supervisione per maggior fiducia o sicurezza in situazioni pericolose", 12 ),
		new StringIntPairBean( "Necessita di assistenza di una persona per raggiungere gli ausili e/o per la loro manipolazione", 8 ),
		new StringIntPairBean( "Necessita della presenza costante di uno o più assistenti durante la deambulazione", 3 ),
		new StringIntPairBean( "Non in grado di deambulare autonomamente", 0 ));
		
		listaScale = CreateOptionList( "L", 
		new StringIntPairBean( "In grado di salire e scendere una rampa di scale con sicurezza, senza aiuto o supervisione. In grado di usare il corrimano, bastone o stampelle se necessario, ed è in grado di portarli con sé durante la salita o discesa", 10 ),
		new StringIntPairBean( "In genere non richiede assistenza. Occasionalmente necessita di supervisione, per sicurezza (es. a causa di rigidità mattutina, dispnea, ecc.)", 8 ),
		new StringIntPairBean( "Capace di salire e scendere le scale, ma non in grado di gestire gli ausili e necessita di supervisione ed assistenza", 5 ),
		new StringIntPairBean( "Necessita di aiuto per salire e scendere le scale (compreso eventuale uso di ausili)", 2 ),
		new StringIntPairBean( "Incapace di salire e scendere le scale", 0 ));
		
		listaUsoCarrozzina = CreateOptionList( "M",
		new StringIntPairBean( "Capace di compiere autonomamente tutti gli spostamenti (girando intorno agli angoli, rigirarsi, avvicinarsi al tavolo, letto, wc, ecc.). L'autonomia deve essere = di 50 mt", 5 ),
		new StringIntPairBean( "Capace di spostarsi autonomamente, per periodi ragionevolmente lunghi, su terreni e superfici regolari. Può essere necessaria assistenza per fare curve strette", 4 ),
		new StringIntPairBean( "Necessita la presenza dell'assistenza costante di una persona per avvicinare la carrozzina al tavolo, al letto, ecc", 3 ),
		new StringIntPairBean( "Capace di spostarsi solo per brevi tratti e superfici piane, necessaria assistenza per tutte le manovre", 1 ),
		new StringIntPairBean( "Dipendenti negli spostamenti con la carrozzina", 0));
		
		listaDaChi = new LinkedList<String>();
		listaDaChi.add("ASL");
		listaDaChi.add("Personale");
		listaDaChi.add("Nessuno");
	}

/*	protected List<SelectItem> CreateOptionList( String baseClassName, StringIntPairBean... args )
	{
		List<SelectItem> retList = new LinkedList<SelectItem>();
		
		if( args != null )
		{
			for( int i = 0; i < args.length; i++)
			{
				StringIntPairBean pair = args[i];
				String className = baseClassName + i;
				retList.add( new SelectItem( className, pair.getKey() + " [ " + pair.getValue() + " ] " ));
				mapPunteggio.put( className, pair.getValue() );
				mapDescrizioni.put(className, pair.getKey());
			}
		}
		return retList;
	}*/
	
	public Integer calcolaPunteggioTotale(){
		punteggioTotale = 0;

		punteggioTotale += StringUtils.isNotEmpty( alimentazione ) ? mapPunteggio.get( alimentazione ): 0;
		punteggioTotale += StringUtils.isNotEmpty( bagnoDoccia ) ? mapPunteggio.get( bagnoDoccia ): 0;
		punteggioTotale += StringUtils.isNotEmpty( igienePersonale ) ? mapPunteggio.get( igienePersonale ): 0;
		punteggioTotale += StringUtils.isNotEmpty( abbigliamento ) ? mapPunteggio.get( abbigliamento ): 0;
		punteggioTotale += StringUtils.isNotEmpty( continenzaIntestinale ) ? mapPunteggio.get( continenzaIntestinale ): 0;
		punteggioTotale += StringUtils.isNotEmpty( continenzaUrinaria ) ? mapPunteggio.get( continenzaUrinaria ): 0;
		punteggioTotale += StringUtils.isNotEmpty( usoGabinetto ) ? mapPunteggio.get( usoGabinetto ): 0;
		punteggioTotale += StringUtils.isNotEmpty( lettoCarrozzina ) ? mapPunteggio.get( lettoCarrozzina ): 0;
		punteggioTotale += StringUtils.isNotEmpty( deambulazione ) ? mapPunteggio.get( deambulazione ): 0; 
		punteggioTotale += StringUtils.isNotEmpty( scale ) ? mapPunteggio.get( scale ): 0;
		punteggioTotale += StringUtils.isNotEmpty( usoCarrozzina ) ? mapPunteggio.get( usoCarrozzina ): 0;

		return punteggioTotale;
	}
	
	public String getPunteggioTotaleLegenda(){
		String label = "";

			if(punteggioTotale>=100)
				return "Autosufficiente";
			else if(punteggioTotale<100 && punteggioTotale>=91 )
				return "Quasi autosufficiente";
			else if(punteggioTotale<=90 && punteggioTotale>=75 )
				return "Dipendenza lieve";
			else if(punteggioTotale<=74 && punteggioTotale>=50 )
				return "Dipendenza moderata";
			else if(punteggioTotale<=49 && punteggioTotale>=25 )
				return "Dipendenza grava";
			else if(punteggioTotale<24)
				return "Dipendenza completa";
			
			
			
		return label;
	}
	
	public List<String> checkObbligatorieta() {
		
		List<String> messagges = new LinkedList<String>();

		double punteggioTotale = calcolaPunteggioTotale();
		if( punteggioTotale <= 5.0 && (numeroPannoloni == null || numeroPannoloni <= 0) )
			messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il numero di pannolini");
		
		if ( StringUtils.isEmpty( alimentazione ) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'alimentazione");
		if ( StringUtils.isEmpty( bagnoDoccia) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema del bagno/doccia (lavarsi)");
		if ( StringUtils.isEmpty( igienePersonale) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'igiene personale");
		if ( StringUtils.isEmpty( abbigliamento) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'abbigliamento");
		if ( StringUtils.isEmpty( continenzaIntestinale) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'incontinenza intestinale");
		if ( StringUtils.isEmpty( continenzaUrinaria) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'incontinenza urinaria");
		if ( StringUtils.isEmpty( usoGabinetto) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'uso del gabinetto");					
		if ( StringUtils.isEmpty( lettoCarrozzina ) )	messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema del trasferimento letto carrozzina");
		if ( StringUtils.isEmpty( deambulazione) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema della deambulazione");
		if ( StringUtils.isEmpty( scale) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema delle scale");
		if ( StringUtils.isEmpty( usoCarrozzina) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato il tema dell\'uso della carrozzina");
		if ( StringUtils.isEmpty( intervistaA ) ) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato chi hai intervistato per redigere la scheda Barthel");

		
		if ( StringUtils.isNotEmpty( altraProtesiDaChi ) && !"Nessuno".equals( altraProtesiDaChi ) )
			if( StringUtils.isEmpty(altraProtesi)) messagges.add("- Attenzione! Nella scheda BARTHEL non hai indicato 'altro da specificare' nella sezione protesi");
		
		return messagges;
	}

	//******************************************************************************************
	//******************************************************************************************
	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getBagnoDoccia() {
		return bagnoDoccia;
	}

	public void setBagnoDoccia(String bagnoDoccia) {
		this.bagnoDoccia = bagnoDoccia;
	}

	public String getIgienePersonale() {
		return igienePersonale;
	}

	public void setIgienePersonale(String igienePersonale) {
		this.igienePersonale = igienePersonale;
	}

	public String getAbbigliamento() {
		return abbigliamento;
	}

	public void setAbbigliamento(String abbigliamento) {
		this.abbigliamento = abbigliamento;
	}

	public String getContinenzaIntestinale() {
		return continenzaIntestinale;
	}

	public void setContinenzaIntestinale(String continenzaIntestinale) {
		this.continenzaIntestinale = continenzaIntestinale;
	}

	public String getContinenzaUrinaria() {
		return continenzaUrinaria;
	}

	public void setContinenzaUrinaria(String continenzaUrinaria) {
		this.continenzaUrinaria = continenzaUrinaria;
	}

	public Integer getNumeroPannoloni() {
		return numeroPannoloni;
	}

	public void setNumeroPannoloni(Integer numeroPannoloni) {
		this.numeroPannoloni = numeroPannoloni;
	}

	public String getUsoGabinetto() {
		return usoGabinetto;
	}

	public void setUsoGabinetto(String usoGabinetto) {
		this.usoGabinetto = usoGabinetto;
	}

	public String getLettoCarrozzina() {
		return lettoCarrozzina;
	}

	public void setLettoCarrozzina(String lettoCarrozzina) {
		this.lettoCarrozzina = lettoCarrozzina;
	}

	public String getDeambulazione() {
		return deambulazione;
	}

	public void setDeambulazione(String deambulazione) {
		this.deambulazione = deambulazione;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getUsoCarrozzina() {
		return usoCarrozzina;
	}

	public void setUsoCarrozzina(String usoCarrozzina) {
		this.usoCarrozzina = usoCarrozzina;
	}

	public String getIntervistaA() {
		return intervistaA;
	}

	public void setIntervistaA(String intervistaA) {
		this.intervistaA = intervistaA;
	}

	public String getDeambulatore() {
		return deambulatore;
	}

	public void setDeambulatore(String deambulatore) {
		this.deambulatore = deambulatore;
	}

	public String getCarrozzina() {
		return carrozzina;
	}

	public void setCarrozzina(String carrozzina) {
		this.carrozzina = carrozzina;
	}

	public String getMaterassinoCuscinoAntidecubito() {
		return materassinoCuscinoAntidecubito;
	}

	public void setMaterassinoCuscinoAntidecubito(
			String materassinoCuscinoAntidecubito) {
		this.materassinoCuscinoAntidecubito = materassinoCuscinoAntidecubito;
	}

	public String getLettoOrtopedico() {
		return lettoOrtopedico;
	}

	public void setLettoOrtopedico(String lettoOrtopedico) {
		this.lettoOrtopedico = lettoOrtopedico;
	}

	public String getPresidioPerIncontinenza() {
		return presidioPerIncontinenza;
	}

	public void setPresidioPerIncontinenza(String presidioPerIncontinenza) {
		this.presidioPerIncontinenza = presidioPerIncontinenza;
	}

	public String getAltraProtesi() {
		return altraProtesi;
	}

	public void setAltraProtesi(String altraProtesi) {
		this.altraProtesi = altraProtesi;
	}

	public String getAltraProtesiDaChi() {
		return altraProtesiDaChi;
	}

	public void setAltraProtesiDaChi(String altraProtesiDaChi) {
		this.altraProtesiDaChi = altraProtesiDaChi;
	}

	public String getIntervistaALabel() {
		return intervistaALabel;
	}

	public void setIntervistaALabel(String intervistaALabel) {
		this.intervistaALabel = intervistaALabel;
	}

	public String getNumeroPannoloniLabel() {
		return numeroPannoloniLabel;
	}

	public List<SelectItem> getListaAlimentazione() {
		return listaAlimentazione;
	}

	public List<SelectItem> getListaBagnoDoccia() {
		return listaBagnoDoccia;
	}

	public List<SelectItem> getListaIgienePersonale() {
		return listaIgienePersonale;
	}

	public List<SelectItem> getListaAbbigliamento() {
		return listaAbbigliamento;
	}

	public List<SelectItem> getListaContinenzaIntestinale() {
		return listaContinenzaIntestinale;
	}

	public List<SelectItem> getListaContinenzaUrinaria() {
		return listaContinenzaUrinaria;
	}

	public List<SelectItem> getListaUsoGabinetto() {
		return listaUsoGabinetto;
	}

	public List<SelectItem> getListaLettoCarrozzina() {
		return listaLettoCarrozzina;
	}

	public List<SelectItem> getListaDeambulazione() {
		return listaDeambulazione;
	}

	public List<SelectItem> getListaScale() {
		return listaScale;
	}

	public List<SelectItem> getListaUsoCarrozzina() {
		return listaUsoCarrozzina;
	}

	public List<String> getListaDaChi() {
		return listaDaChi;
	}

	public HashMap<String, Integer> getMapPunteggio() {
		return mapPunteggio;
	}

	public Integer getPunteggioTotale() {
		return punteggioTotale;
	}
	
	public String fillReportAusili(){
		String s = "";
		s+= "<b>Deambulatore:</b> "+this.deambulatore;
		s+= "<br/><b>Carrozzina:</b> "+this.carrozzina;
		s+= "<br/><b>Materassino:</b> "+this.materassinoCuscinoAntidecubito;
		s+= "<br/><b>Letto ortopedico:</b> "+this.lettoOrtopedico;
		s+= "<br/><b>Presidio per incontinenza:</b> "+this.presidioPerIncontinenza;
		s+= this.altraProtesi!=null ? ("<br/><b>"+this.altraProtesi+":</b> "+this.altraProtesiDaChi):"";
		return s;		
	}
}
