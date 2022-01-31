package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class UserInfoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String enteId;
    protected String userId;
    protected String password;
    
	public String getEnteId() {
		return enteId;
	}
	
	public void setEnteId(String enteId) {
		this.enteId = enteId;
	}
	
	public boolean isSetEnteId() {
        return (this.enteId != null);
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isSetUserId() {
        return (this.userId != null);
    }
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isSetPassword() {
        return (this.password != null);
    }

}
