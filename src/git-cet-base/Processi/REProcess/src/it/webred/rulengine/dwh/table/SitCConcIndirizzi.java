package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.TipoXml;

public class SitCConcIndirizzi extends TabellaDwhMultiProv {
		
		private String codiceVia;
		private String sedime;
		private String indirizzo;
		private TipoXml civicoComposto;
		private String civLiv1;
		private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());

		public String getCivLiv1() {
			return civLiv1;
		}

		public void setCivLiv1(String civLiv1) {
			this.civLiv1 = civLiv1;
		}

		public String getCodiceVia() {
			return codiceVia;
		}

		public void setCodiceVia(String codiceVia) {
			this.codiceVia = codiceVia;
		}

		public String getSedime() {
			return sedime;
		}

		public void setSedime(String sedime) {
			this.sedime = sedime;
		}

		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public TipoXml getCivicoComposto()
		{
			return civicoComposto;
		}

		public void setCivicoComposto(TipoXml civicoComposto)
		{
			this.civicoComposto = civicoComposto;
		}

		@Override
		public String getValueForCtrHash() {
			return (String)idExtCConcessioni.getRelazione().getValore()+codiceVia+sedime+indirizzo+civLiv1+getProvenienza();
		}

		public Relazione getIdExtCConcessioni()
		{
			return idExtCConcessioni;
		}

		public void setIdExtCConcessioni(ChiaveEsterna idExtCConcessioni)
		{
			RelazioneToMasterTable r = new RelazioneToMasterTable(SitCConcessioni.class,idExtCConcessioni);
			this.idExtCConcessioni = r;	
		}

		
		

		
}
