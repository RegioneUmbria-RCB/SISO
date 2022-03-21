package it.webred.verificaCoordinate.dto.request;

import java.io.Serializable;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

public class CommonUserInfoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected UserInfoDTO userInfo;

	public UserInfoDTO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDTO userInfo) {
		this.userInfo = userInfo;
	}

	public boolean isSetUserInfo() {
        return (this.userInfo != null);
    }

}
