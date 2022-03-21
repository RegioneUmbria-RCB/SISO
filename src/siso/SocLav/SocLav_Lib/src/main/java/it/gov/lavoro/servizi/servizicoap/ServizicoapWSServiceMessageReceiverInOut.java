
/**
 * ServizicoapWSServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package it.gov.lavoro.servizi.servizicoap;

        /**
        *  ServizicoapWSServiceMessageReceiverInOut message receiver
        */

        public class ServizicoapWSServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        ServizicoapWSServiceSkeletonInterface skel = (ServizicoapWSServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("invioSAP".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP risposta_invioSAP49 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.InvioSAP wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.InvioSAP)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.InvioSAP.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_invioSAP49 =
                                                   
                                                   
                                                         skel.invioSAP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_invioSAP49, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "invioSAP"));
                                    } else 

            if("notificaSAP".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP risposta_notificaSAP51 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.NotificaSAP wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.NotificaSAP)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.NotificaSAP.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_notificaSAP51 =
                                                   
                                                   
                                                         skel.notificaSAP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_notificaSAP51, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "notificaSAP"));
                                    } else 

            if("richiestaSAP_N00_A02".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02 risposta_richiestaSAP_N00_A0253 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02 wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_richiestaSAP_N00_A0253 =
                                                   
                                                   
                                                         skel.richiestaSAP_N00_A02(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_richiestaSAP_N00_A0253, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "richiestaSAP_N00_A02"));
                                    } else 

            if("richiestaCodSAPRegTitolare".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare risposta_richiestaCodSAPRegTitolare55 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_richiestaCodSAPRegTitolare55 =
                                                   
                                                   
                                                         skel.richiestaCodSAPRegTitolare(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_richiestaCodSAPRegTitolare55, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "richiestaCodSAPRegTitolare"));
                                    } else 

            if("verificaEsistenzaSAP".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP risposta_verificaEsistenzaSAP57 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_verificaEsistenzaSAP57 =
                                                   
                                                   
                                                         skel.verificaEsistenzaSAP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_verificaEsistenzaSAP57, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "verificaEsistenzaSAP"));
                                    } else 

            if("richiestaSAP".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP risposta_richiestaSAP59 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_richiestaSAP59 =
                                                   
                                                   
                                                         skel.richiestaSAP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_richiestaSAP59, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "richiestaSAP"));
                                    } else 

            if("recuperaListaSAPNonAttive".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive risposta_recuperaListaSAPNonAttive61 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_recuperaListaSAPNonAttive61 =
                                                   
                                                   
                                                         skel.recuperaListaSAPNonAttive(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_recuperaListaSAPNonAttive61, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "recuperaListaSAPNonAttive"));
                                    } else 

            if("annullaSAP".equals(methodName)){
                
                it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP risposta_annullaSAP63 = null;
	                        it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP wrappedParam =
                                                             (it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               risposta_annullaSAP63 =
                                                   
                                                   
                                                         skel.annullaSAP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), risposta_annullaSAP63, false, new javax.xml.namespace.QName("http://servizi.lavoro.gov.it/servizicoap",
                                                    "annullaSAP"));
                                    
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
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.InvioSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.InvioSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.NotificaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.NotificaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP wrapinvioSAP(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP wrapnotificaSAP(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02 param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02 wraprichiestaSAP_N00_A02(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02 wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare wraprichiestaCodSAPRegTitolare(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP wrapverificaEsistenzaSAP(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP wraprichiestaSAP(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive wraprecuperaListaSAPNonAttive(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP wrapannullaSAP(){
                                it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP wrappedElement = new it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP();
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
        
                if (it.gov.lavoro.servizi.servizicoap.types.InvioSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.InvioSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_invioSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.NotificaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.NotificaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_notificaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP_N00_A02.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_N00_A02.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.RichiestaCodSAPRegTitolare.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaCodSAPRegTitolare.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.RecuperaListaSAPNonAttive.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_recuperaListaSAPNonAttive.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.AnnullaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP.class.equals(type)){
                
                           return it.gov.lavoro.servizi.servizicoap.types.Risposta_annullaSAP.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    