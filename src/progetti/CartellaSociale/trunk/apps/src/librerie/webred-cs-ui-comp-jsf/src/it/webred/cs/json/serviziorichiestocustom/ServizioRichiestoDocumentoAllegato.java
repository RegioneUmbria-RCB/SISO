package it.webred.cs.json.serviziorichiestocustom;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ServizioRichiestoDocumentoAllegato {
 
	private long diarioId;
	private String nome;
	private byte[] contents;
	private String contentType;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public byte[] getContents() {
		return contents;
	}
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(long diarioId) {
		this.diarioId = diarioId;
	} 

    public StreamedContent getStreamedContent() {

      InputStream stream =  new ByteArrayInputStream(getContents());
      DefaultStreamedContent s = new DefaultStreamedContent(stream, 
      						getContentType(), //"image/jpg",
      						getNome() //"downloaded_optimus.jpg"
      						);
        
        return s;
    }
}
