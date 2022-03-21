package it.webred.cs.jsf.manbean.common;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CommonSessionMan {

	
	
	public Date getCurrentDate() {
		Calendar now = Calendar.getInstance();
		return now.getTime();

	}
	
}
