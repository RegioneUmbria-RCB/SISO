INSTALLAZIONE AUDIT

Info Generali:

_La libreria scriverà un record sulla tabella AM_AUDIT per ogni metodo da loggare
_Di default i campi loggati saranno solo tutti quelli dichiarati e non delle superclassi
_Per comprendere tutti i campi inherit aggiungere alla classe la annotazione @AuditInherit
_Per limitare l'audit ai campi scelti aggiungere alla classe la annotazione @AuditAnnotatedFields ed ai campi @AuditField

Audit per bean STATELESS:

_Libreria Audit nell'EAR
_Solo su classi Bean Stateless aggiungere su metodi scelti annotazione @Interceptors(AuditStateless.class)
_____________________________________________________________________________________________________________________________

INSTALLAZIONE VALIDAZIONE ACCESSO METODI

Info Generali:

_La libreria cercherà un record di login sulla tabella AM_AUDIT usando il sessionId 

Validazione per bean STATELESS:

_Libreria Audit nell'EAR
_Solo su classi Bean Stateless aggiungere su metodi scelti annotazione @Interceptors(ValidationStateless.class)






PER ESEGUIRE ENTRAMBI @Interceptors({ValidationStateless.class, AuditStateless.class})