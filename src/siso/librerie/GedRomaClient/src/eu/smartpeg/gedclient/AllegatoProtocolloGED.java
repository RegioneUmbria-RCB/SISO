package eu.smartpeg.gedclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AllegatoProtocolloGED {
	
	private String descrizione;
	private String nomeFile;
	private byte[] allegato;
	
	/**
	 * Metodo di factory per creare oggetto allegato GED da allegare a protocollo su GED
	 * @param descrizione
	 * @param nomeFile
	 * @param allegato
	 * @return
	 */
	public static AllegatoProtocolloGED CreaAllegato(String descrizione, String nomeFile, byte[] allegato ) {
		
		// TODO validazione
		// TODO encoding contenuto file
		
		AllegatoProtocolloGED allegatoProtocollo = new AllegatoProtocolloGED();
		allegatoProtocollo.setDescrizione(descrizione);
		allegatoProtocollo.setNomeFile(nomeFile);
		allegatoProtocollo.setAllegato(allegato);
		
		return allegatoProtocollo;		
	}
	
	/**
	 *  Metodo di factory per creare oggetto allegato GED da allegare a protocollo su GED
	 * @param descrizione
	 * @param nomeFile
	 * @param fileAllegato
	 * @return
	 * @throws IOException 
	 */
	public static AllegatoProtocolloGED CreaAllegato(String descrizione, String nomeFile, File fileDaAllegare ) throws IOException {
	
		// TODO validazione parametri input
		
		byte[] fileContent = Files.readAllBytes(fileDaAllegare.toPath());		
		return AllegatoProtocolloGED.CreaAllegato(descrizione, nomeFile, fileContent);		
	}
	
	
	private AllegatoProtocolloGED() {
		super();
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	private void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	private void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getAllegato() {
		return allegato;
	}

	private void setAllegato(byte[] allegato) {
		this.allegato = allegato;
	}
	
	@Override
	public String toString() {
		return "AllegatoProtocolloGED [descrizione=" + descrizione + ", nomeFile=" + nomeFile + ", allegato=" + allegato.length + "]";
	}

}
