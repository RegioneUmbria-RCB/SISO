package it.umbriadigitale.rest.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;


// tanks to: amacoder

public class AppException extends Exception {

	
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;

		/** 
		 * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
		 * the developer does not have to look into the response headers. If null a default 
		 */
		Integer status;
		
		/** application specific error code */
		int appSpecificCode; 
			
		/** link documenting the exception */	
		String link;
		
		/** detailed error description for developers*/
		String developerMessage;	
		
		/** timestamp of the error */
		Timestamp time;
		
		
		/**
		 * 
		 * @param status
		 * @param code
		 * @param message
		 * @param developerMessage
		 * @param link
		 */
		protected AppException(int status, int appSpecificCode, String message,
				String developerMessage, String link) {
			super(message);
			this.status = status;
			this.time = new java.sql.Timestamp(System.currentTimeMillis());
			this.appSpecificCode = appSpecificCode;
			this.developerMessage = developerMessage;
			this.link = link;
		}
		
		protected AppException(Exception e, String message ) {
			super(message);
			setAppSpecificCode(-1);
			StringWriter errorStackTrace = new StringWriter();
			e.printStackTrace(new PrintWriter(errorStackTrace));
			setDeveloperMessage(errorStackTrace.toString());
			setLink(null);
			
		}

		public Timestamp getTime() {
			return time;
		}

		public AppException() { }

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}



		public int getAppSpecificCode() {
			return appSpecificCode;
		}

		public void setAppSpecificCode(int appSpecificCode) {
			this.appSpecificCode = appSpecificCode;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}

		public void setDeveloperMessage(String developerMessage) {
			this.developerMessage = developerMessage;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
						
	}
	


