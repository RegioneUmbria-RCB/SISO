package it.webred.mui;

public interface Constants {

	 public static final String TEMPLATE = "/template.jsp";
	 public static final String POPUP_TEMPLATE = "/templatePopup.jsp";
	 public static final String POPUP_DISCRIMINATOR = "Popup";
	 public static final String NOTEMPLATE_DISCRIMINATOR = "NoTemplate";
	 public static final String FORMPOST_DISCRIMINATOR = "formPost";
	 public static final String CONTROLLER_SES = "controller";
	 public static final String CONTROLLER_PASS_REQ = "controlled";
	 public static final String MAIN_URI_REQ = "main_page";
	 public static final String CONNECTION_TIME_REQ = "connection_time";
	 public static final String USER_REQ = "MUI_USER";
	 public static final String MUI_APP_APPCTX = "MUI_APP";
	 public static final String MUI_USERPROFILE_SES = "MUI_USERPROFILE";
	 public static final String DBTIME_REQ = "DB_TIME";
	 public static final int MUI_MAX_RESULTS = 200;
	 public static final long MUI_DATE_DURATION = 60000;
	 public static final long MUI_DBDATE_DURATION = 3600000;
	 public static final String UPLOAD_LIST_VARNAME = "miDupFornituras";
	 public static final String DAP_LIST_VARNAME = "miConsDaps";
	 public static final String DOCFA_DAP_VARNAME = "docfaDaps";
	 public static final String R0602_VARNAME = "R0602";
	 public static final String BELFIORE_VARNAME = "BELFIORE";
	 public static final long CACHE_DURATION = 2*1000*60*60*4;//2 perche divide per 2 *1000 secondi *60 minuti * 60 ore * 4 numero ore 
	 public static final long CACHE_SLEEP_CYCLE = 15000;
	
}
