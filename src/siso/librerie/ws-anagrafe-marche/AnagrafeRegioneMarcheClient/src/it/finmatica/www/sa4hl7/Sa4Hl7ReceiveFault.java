
/**
 * Sa4Hl7ReceiveFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package it.finmatica.www.sa4hl7;

public class Sa4Hl7ReceiveFault extends java.lang.Exception{

    private static final long serialVersionUID = 1549021305754L;
    
    private it.finmatica.www.schema.hl7.FaultDocument faultMessage;

    
        public Sa4Hl7ReceiveFault() {
            super("Sa4Hl7ReceiveFault");
        }

        public Sa4Hl7ReceiveFault(java.lang.String s) {
           super(s);
        }

        public Sa4Hl7ReceiveFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public Sa4Hl7ReceiveFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(it.finmatica.www.schema.hl7.FaultDocument msg){
       faultMessage = msg;
    }
    
    public it.finmatica.www.schema.hl7.FaultDocument getFaultMessage(){
       return faultMessage;
    }
}
    