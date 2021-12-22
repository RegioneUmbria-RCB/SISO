package it.webred.siso.ws.client.anag.model;




import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Output")
public class Output {
	private Exception exception;
	private OpenSession openSession;
	private Get get;
	private Find find;
	private String closeSession;

	@XmlElement(name = "Exception")
	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@XmlElement(name = "OpenSession")
	public OpenSession getOpenSession() {
		return openSession;
	}

	public void setOpenSession(OpenSession openSession) {
		this.openSession = openSession;
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

	public void setCloseSession(String closeSession) {
		this.closeSession = closeSession;
	}
	@XmlRootElement(name = "Exception")
	@XmlType(propOrder = { "code", "description"})
	public static class Exception {
		private String code;
		private String description;
		
		@XmlElement(name = "Code")
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		@XmlElement(name = "Description")
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}


}
