
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package it.gov.lavoro.servizi.servicerdc.types;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "richiesta_RDC_beneficiari_dato_codProtocolloInps".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInps.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "beneficiario_Type".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Beneficiario_Type.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "risposta_servizio_RDC_Type".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Risposta_servizio_RDC_Type.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "esito_Type".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Esito_Type.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "richiesta_RDC_beneficiari_dato_CodiceFiscale".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_CodiceFiscale.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://servizi.lavoro.gov.it/serviceRDC/types".equals(namespaceURI) &&
                  "beneficiari".equals(typeName)){
                   
                            return  it.gov.lavoro.servizi.servicerdc.types.Beneficiari.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    