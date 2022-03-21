package it.webred.cs.json.barthel.ver1;

import it.webred.cs.json.barthel.JsonBarthelBaseDataBean;
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
public class JsonBarthelIADLDataBean extends JsonBarthelBaseDataBean{

	protected String capacitaUsareTelefono;
	protected String fareAcquisti;
	protected String preparazioneCibo;
	protected String governoDellaCasa;
	protected String biancheria;
	protected String mezziDiTrasporto;
	protected String responsabilitaUsoFarmaci;
	protected String capacitaGestioneDenaro;
	protected String intervistaA;

	@JsonIgnore protected List<SelectItem> listaCapacitaUsareTelefono;
	@JsonIgnore protected List<SelectItem> listaFareAcquisti;
	@JsonIgnore protected List<SelectItem> listaPreparazioneCibo;
	@JsonIgnore protected List<SelectItem> listaGovernoDellaCasa;
	@JsonIgnore protected List<SelectItem> listaBiancheria;
	@JsonIgnore protected List<SelectItem> listaMezziDiTrasporto;
	@JsonIgnore protected List<SelectItem> listaResponsabilitaUsoFarmaci;
	@JsonIgnore protected List<SelectItem> listaCapacitaGestioneDenaro;
	@JsonIgnore protected String 		   intervistaALabel;
	
	public JsonBarthelIADLDataBean() {
		
		super();
		capacitaUsareTelefono = null;
		fareAcquisti = null;
		preparazioneCibo = null;
		governoDellaCasa = null;
		biancheria = null;
		mezziDiTrasporto = null;
		responsabilitaUsoFarmaci = null;
		capacitaGestioneDenaro = null;
		intervistaA = null;

		intervistaALabel = "Compilata attraverso intervista a:";
		
		listaCapacitaUsareTelefono = CreateOptionList( "A",
		new StringIntPairBean( "Usa il telefono di propria iniziativa, stacca il microfono e compone il numero, ecc.", 1), 
		new StringIntPairBean( "Compone solo alcuni numeri ben conosciuti.", 1), 	
		new StringIntPairBean( "Risponde al telefono ma non è capace di comporre il numero.", 1),  	
		new StringIntPairBean( "Non è capace di usare il telefono.", 0));

		listaFareAcquisti = CreateOptionList( "B", 
		new StringIntPairBean( "Fa tutte le proprie spese senza aiuto.", 1 ), 	
		new StringIntPairBean( "Fa piccoli acquisti senza aiuto.", 0 ),
		new StringIntPairBean( "Ha bisogno di essere accompagnato quando deve acquistare qualcosa.", 0 ), 	
		new StringIntPairBean( "Completamente incapace di fare acquisti.", 0 ));

		listaPreparazioneCibo = CreateOptionList( "C",
		new StringIntPairBean( "Organizza, prepara e serve pasti adeguatamente preparati indipendentemente.", 1 ),
		new StringIntPairBean( "Prepara pasti adeguati se sono approvvigionati gli ingredienti.", 0 ),
		new StringIntPairBean( "Scalda e serve pasti preparati oppure prepara cibi ma non mantiene una dieta adeguata.", 0 ), 	
		new StringIntPairBean( "Ha bisogno di avere cibi preparati e serviti.", 0 ));

		listaGovernoDellaCasa = CreateOptionList( "D", 
		new StringIntPairBean( "Mantiene la casa da solo o con occasionale assistenza (per es. aiuto per i lavori pesanti).", 1), 	
		new StringIntPairBean( "Esegue i compiti quotidiani leggeri ma non mantiene un accettabile livello di pulizia della casa.", 1), 	
		new StringIntPairBean( "Ha bisogno di aiuto in ogni operazione di mantenimento della casa.", 1),
		new StringIntPairBean( "Non partecipa a nessuna operazione di governo della casa.", 0));

		listaBiancheria = CreateOptionList( "E", 
		new StringIntPairBean( "Fa il bucato personale completamente.", 1 ),
		new StringIntPairBean( "Lava le piccole cose (sciacqua le calze, fazzoletti, ecc.).", 1 ), 	
		new StringIntPairBean( "Tutta la biancheria deve essere lavata da altri.", 0 ));

		listaMezziDiTrasporto = CreateOptionList( "F",
		new StringIntPairBean( "Si sposta da solo sui mezzi pubblici o guida la propria auto.", 1),
		new StringIntPairBean( "Si sposta in taxi, ma non usa mezzi di trasporto pubblici).", 1),
		new StringIntPairBean( "Usa i mezzi pubblici se assistito o accompagnato da qualcuno.", 1),
		new StringIntPairBean( "Può spostarsi solo con taxi o auto e con l'assistenza di altri.", 0),
		new StringIntPairBean( "Non si sposta per niente.", 0));
		
		listaResponsabilitaUsoFarmaci = CreateOptionList( "G",
		new StringIntPairBean( "Prende le medicine che gli sono state affidate, in dosi e tempi giusti.", 1), 	
		new StringIntPairBean( "Prende le medicine se sono preparate in anticipo in dosi separate.", 0),
		new StringIntPairBean( "Non è in grado di prendere le medicine da solo.", 0));

		listaCapacitaGestioneDenaro = CreateOptionList( "H", 
		new StringIntPairBean( "Maneggia le proprie finanze in modo indipendente (riscuote ed amministra i propri introiti, pensione, ecc.).", 1 ), 	
		new StringIntPairBean( "E' in grado di fare piccoli acquisti ma non quelli importanti.", 1 ), 	
		new StringIntPairBean( "E' incapace di maneggiare i soldi.", 0 ));
	
	}

	/*protected List<SelectItem> CreateOptionList( String baseClassName, StringIntPairBean... args )
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
				mapLabel.put(className, pair.getKey() + " [ " + pair.getValue() + " ] ");
			}
		}
		return retList;
	}*/

	public Integer calcolaPunteggioTotale()
	{
		punteggioTotale = 0;
		
		punteggioTotale += StringUtils.isNotEmpty( capacitaUsareTelefono ) ? mapPunteggio.get( capacitaUsareTelefono ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( fareAcquisti ) ? mapPunteggio.get( fareAcquisti ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( preparazioneCibo ) ? mapPunteggio.get( preparazioneCibo ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( governoDellaCasa ) ? mapPunteggio.get( governoDellaCasa ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( biancheria ) ? mapPunteggio.get( biancheria ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( mezziDiTrasporto ) ? mapPunteggio.get( mezziDiTrasporto ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( responsabilitaUsoFarmaci ) ? mapPunteggio.get( responsabilitaUsoFarmaci ) : 0;
		punteggioTotale += StringUtils.isNotEmpty( capacitaGestioneDenaro ) ? mapPunteggio.get( capacitaGestioneDenaro ) : 0;
		
		return punteggioTotale;
	}
	
	public List<String> checkObbligatorieta() {
		
		List<String> messagges = new LinkedList<String>();
		
		if( StringUtils.isEmpty( capacitaUsareTelefono) ) messagges.add("- Capacità di usare il telefono");
		if( StringUtils.isEmpty( fareAcquisti) ) messagges.add("- Capacità di fare acquisti");
		if( StringUtils.isEmpty( preparazioneCibo) ) messagges.add("- Capacità di preparazione del cibo");
		if( StringUtils.isEmpty( governoDellaCasa) ) messagges.add("- Capacità del governo della casa");
		if( StringUtils.isEmpty( biancheria) ) messagges.add("- Capacità di gestione della biancheria");
		if( StringUtils.isEmpty( mezziDiTrasporto) ) messagges.add("- Capacità di utilizzare i mezzi di trasporto");
		if( StringUtils.isEmpty( responsabilitaUsoFarmaci) ) messagges.add("- Capacità di usare correttamente i medicinali");
		if( StringUtils.isEmpty( capacitaGestioneDenaro) ) messagges.add("- Capacità di usare correttamente il denaro");
		if( StringUtils.isEmpty( intervistaA ) ) messagges.add("- Chi hai intervistato per redigere la scheda IADL");

		return messagges;
	}

	public String getCapacitaUsareTelefono() {
		return capacitaUsareTelefono;
	}
	public void setCapacitaUsareTelefono(String capacitaUsareTelefono) {
		this.capacitaUsareTelefono = capacitaUsareTelefono;
	}
	public String getFareAcquisti() {
		return fareAcquisti;
	}
	public void setFareAcquisti(String fareAcquisti) {
		this.fareAcquisti = fareAcquisti;
	}
	public String getPreparazioneCibo() {
		return preparazioneCibo;
	}
	public void setPreparazioneCibo(String preparazioneCibo) {
		this.preparazioneCibo = preparazioneCibo;
	}
	public String getGovernoDellaCasa() {
		return governoDellaCasa;
	}
	public void setGovernoDellaCasa(String governoDellaCasa) {
		this.governoDellaCasa = governoDellaCasa;
	}
	public String getBiancheria() {
		return biancheria;
	}
	public void setBiancheria(String biancheria) {
		this.biancheria = biancheria;
	}
	public String getMezziDiTrasporto() {
		return mezziDiTrasporto;
	}
	public void setMezziDiTrasporto(String mezziDiTrasporto) {
		this.mezziDiTrasporto = mezziDiTrasporto;
	}
	public String getResponsabilitaUsoFarmaci() {
		return responsabilitaUsoFarmaci;
	}
	public void setResponsabilitaUsoFarmaci(String responsabilitaUsoFarmaci) {
		this.responsabilitaUsoFarmaci = responsabilitaUsoFarmaci;
	}
	public String getCapacitaGestioneDenaro() {
		return capacitaGestioneDenaro;
	}
	public void setCapacitaGestioneDenaro(String capacitaGestioneDenaro) {
		this.capacitaGestioneDenaro = capacitaGestioneDenaro;
	}

	public String getIntervistaA() {
		return intervistaA;
	}

	public void setIntervistaA(String intervistaA) {
		this.intervistaA = intervistaA;
	}

	public String getIntervistaALabel() {
		return intervistaALabel;
	}

	public List<SelectItem> getListaCapacitaUsareTelefono() {
		return listaCapacitaUsareTelefono;
	}

	public List<SelectItem> getListaFareAcquisti() {
		return listaFareAcquisti;
	}

	public List<SelectItem> getListaPreparazioneCibo() {
		return listaPreparazioneCibo;
	}

	public List<SelectItem> getListaGovernoDellaCasa() {
		return listaGovernoDellaCasa;
	}

	public List<SelectItem> getListaBiancheria() {
		return listaBiancheria;
	}

	public List<SelectItem> getListaMezziDiTrasporto() {
		return listaMezziDiTrasporto;
	}

	public List<SelectItem> getListaResponsabilitaUsoFarmaci() {
		return listaResponsabilitaUsoFarmaci;
	}

	public List<SelectItem> getListaCapacitaGestioneDenaro() {
		return listaCapacitaGestioneDenaro;
	}

	@Override
	public String getPunteggioTotaleLegenda() {
		
		return "";
	}

}
