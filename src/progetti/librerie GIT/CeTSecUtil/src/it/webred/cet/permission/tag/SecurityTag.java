package it.webred.cet.permission.tag;

import java.util.HashMap;

import it.webred.cet.permission.CeTUser;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class SecurityTag extends BodyTagSupport {
	
	private String permission;
	private String item;
	
	
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
   public int doStartTag() throws JspTagException {
	 HttpSession session =  this.pageContext.getSession();
	 CeTUser user = (CeTUser) session.getAttribute("user");
	 if (user == null)
		 return SKIP_BODY;
	 
	 
	 HashMap<String, String> permHash = user.getPermList();
	 String currentEnte = user.getCurrentEnte();
	 
	 String permKey = "permission@-@" + currentEnte + "@-@" + item + "@-@" + permission;
	 
	 if (permHash.get(permKey) != null)
		 return EVAL_BODY_TAG;
	
	 return SKIP_BODY;
	 
   }

}
