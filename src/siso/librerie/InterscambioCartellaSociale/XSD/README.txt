2017-11-16

-Utilizzare il file customBindings.xjb in fase di generazione classi via JAXB 2.2:
 poiché gli schemi XSD di riferimento (PRSS_IN001004ZZ_mod.xsd per la request,
 MCCI_IN000002UV01_mod.xsd per la response) utilizzano alcune direttive XSD
 particolari (princiaplmente, elementi con mixed=true), la normale generazione
 JAXB rende di fatto impossibile la gestione di alcune casistiche formalmente
 valide

-la libreria deploya il contenuto della cartella XSD al path resources/XSD