package it.webred.siso.ws.client.anag.model;





import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Input")
public class Input {

	private OpenSession openSession;
	private Get get;
	private Find find;
	private String sessionID;
	private String closeSession;

	@XmlElement(name = "OpenSession")
	public OpenSession getOpenSession() {
		return openSession;
	}

	public void setOpenSession(OpenSession openSession) {
		this.openSession = openSession;
	}

	@XmlAttribute(name = "SessionID")
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	@XmlElement(name = "Get")
	public Get getGet() {
		return get;
	}

	public void setGet(Get get) {
		this.get = get;
	}

	@XmlElement(name = "Find")
	public Find getFind() {
		return find;
	}

	public void setFind(Find find) {
		this.find = find;
	}

	@XmlElement(name = "CloseSession")
	public String getCloseSession() {
		return closeSession;
	}

	public void setCloseSession() {
		this.closeSession =" ";
	}

	
	
	

	
}
