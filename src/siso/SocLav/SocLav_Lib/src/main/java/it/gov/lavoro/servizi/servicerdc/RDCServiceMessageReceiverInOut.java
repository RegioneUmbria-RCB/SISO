
/**
 * RDCServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package it.gov.lavoro.servizi.servicerdc;

        /**
        *  RDCServiceMessageReceiverInOut message receiver
        */

        public class RDCServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        RDCServiceSkeletonInterface skel = (RDCServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("ricevi_RDC_by_codiceFiscale".equals(methodName)){
                
                it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari risposta_RDC_beneficiari14 = null;
	                        it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE wrappedParam =
                                                             (it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_RDC_beneficiari14 =
                                                   
                                                   
                                                         skel.ricevi_RDC_by_codiceFiscale(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_RDC_beneficiari14, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/serviceRDC",
                                                    "ricevi_RDC_by_codiceFiscale"));
                                    } else 

            if("ricevi_RDC_by_codProtocolloInps".equals(methodName)){
                
                it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari risposta_RDC_beneficiari16 = null;
	                        it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE wrappedParam =
                                                             (it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_RDC_beneficiari16 =
                                                   
                                                   
                                                         skel.ricevi_RDC_by_codProtocolloInps(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_RDC_beneficiari16, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/serviceRDC",
                                                    "ricevi_RDC_by_codProtocolloInps"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari wrapricevi_RDC_by_codiceFiscale(){
                                it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari wrappedElement = new it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari();
                                return wrappedElement;
                         }
                    
                         private it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari wrapricevi_RDC_by_codProtocolloInps(){
                                it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari wrappedElement = new it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servicerdc.types.Risposta_RDC_beneficiari.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    