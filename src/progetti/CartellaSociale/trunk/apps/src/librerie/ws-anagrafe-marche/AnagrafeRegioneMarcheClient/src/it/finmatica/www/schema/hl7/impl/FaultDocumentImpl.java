/*
 * An XML document type.
 * Localname: Fault
 * Namespace: http://www.finmatica.it/schema/HL7
 * Java type: it.finmatica.www.schema.hl7.FaultDocument
 *
 * Automatically generated - do not modify.
 */
package it.finmatica.www.schema.hl7.impl;
/**
 * A document containing one Fault(@http://www.finmatica.it/schema/HL7) element.
 *
 * This is a complex type.
 */
public class FaultDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.FaultDocument
{
    
    public FaultDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FAULT$0 = 
        new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Fault");
    
    
    /**
     * Gets the "Fault" element
     */
    public it.finmatica.www.schema.hl7.FaultDocument.Fault getFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.FaultDocument.Fault target = null;
            target = (it.finmatica.www.schema.hl7.FaultDocument.Fault)get_store().find_element_user(FAULT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Fault" element
     */
    public void setFault(it.finmatica.www.schema.hl7.FaultDocument.Fault fault)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.FaultDocument.Fault target = null;
            target = (it.finmatica.www.schema.hl7.FaultDocument.Fault)get_store().find_element_user(FAULT$0, 0);
            if (target == null)
            {
                target = (it.finmatica.www.schema.hl7.FaultDocument.Fault)get_store().add_element_user(FAULT$0);
            }
            target.set(fault);
        }
    }
    
    /**
     * Appends and returns a new empty "Fault" element
     */
    public it.finmatica.www.schema.hl7.FaultDocument.Fault addNewFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.FaultDocument.Fault target = null;
            target = (it.finmatica.www.schema.hl7.FaultDocument.Fault)get_store().add_element_user(FAULT$0);
            return target;
        }
    }
    /**
     * An XML Fault(@http://www.finmatica.it/schema/HL7).
     *
     * This is a complex type.
     */
    public static class FaultImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.FaultDocument.Fault
    {
        
        public FaultImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName FAULTCODE$0 = 
            new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "FaultCode");
        private static final javax.xml.namespace.QName FAULTSTRING$2 = 
            new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "FaultString");
        
        
        /**
         * Gets the "FaultCode" element
         */
        public java.lang.String getFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "FaultCode" element
         */
        public org.apache.xmlbeans.XmlString xgetFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTCODE$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "FaultCode" element
         */
        public void setFaultCode(java.lang.String faultCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTCODE$0);
                }
                target.setStringValue(faultCode);
            }
        }
        
        /**
         * Sets (as xml) the "FaultCode" element
         */
        public void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTCODE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTCODE$0);
                }
                target.set(faultCode);
            }
        }
        
        /**
         * Gets the "FaultString" element
         */
        public java.lang.String getFaultString()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTRING$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "FaultString" element
         */
        public org.apache.xmlbeans.XmlString xgetFaultString()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTRING$2, 0);
                return target;
            }
        }
        
        /**
         * Sets the "FaultString" element
         */
        public void setFaultString(java.lang.String faultString)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTRING$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTSTRING$2);
                }
                target.setStringValue(faultString);
            }
        }
        
        /**
         * Sets (as xml) the "FaultString" element
         */
        public void xsetFaultString(org.apache.xmlbeans.XmlString faultString)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTRING$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTRING$2);
                }
                target.set(faultString);
            }
        }
    }
}
