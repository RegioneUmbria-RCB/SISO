package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VariazioneAttestazione  extends VariazioneBase implements Serializable {

	 
	private String numeroProtocolloDSUVariata;
	private String dataPresentazioneDSUVariata;
	private String numeroProtocolloMittenteDSUVariata;
	private String noteVariazione;
	private List<DiscordanzeContestazione> discordanzaContestazione = new ArrayList<DiscordanzeContestazione>();
	 
	public String getNumeroProtocolloDSUVariata() {
		return numeroProtocolloDSUVariata;
	}
	public void setNumeroProtocolloDSUVariata(String numeroProtocolloDSUVariata) {
		this.numeroProtocolloDSUVariata = numeroProtocolloDSUVariata;
	}
	public String getDataPresentazioneDSUVariata() {
		return dataPresentazioneDSUVariata;
	}
	public void setDataPresentazioneDSUVariata(String dataPresentazioneDSUVariata) {
		this.dataPresentazioneDSUVariata = dataPresentazioneDSUVariata;
	}
	public String getNumeroProtocolloMittenteDSUVariata() {
		return numeroProtocolloMittenteDSUVariata;
	}
	public void setNumeroProtocolloMittenteDSUVariata(
			String numeroProtocolloMittenteDSUVariata) {
		this.numeroProtocolloMittenteDSUVariata = numeroProtocolloMittenteDSUVariata;
	}
	public String getNoteVariazione() {
		return noteVariazione;
	}
	public void setNoteVariazione(String noteVariazione) {
		this.noteVariazione = noteVariazione;
	}
	public List<DiscordanzeContestazione> getDiscordanzaContestazione() {
		return discordanzaContestazione;
	}
	public void setDiscordanzaContestazione(
			List<DiscordanzeContestazione> discordanzaContestazione) {
		this.discordanzaContestazione = discordanzaContestazione;
	}
	
	public void addDiscordanzaContestazione(
			 DiscordanzeContestazione  discordanzaContestazione) {
		this.discordanzaContestazione.add(discordanzaContestazione);
	}
}
