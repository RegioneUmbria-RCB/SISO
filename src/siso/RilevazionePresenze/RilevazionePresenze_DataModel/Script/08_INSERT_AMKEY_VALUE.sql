--per i clienti che non hanno il verticale Rilevazione Presenze il parametro non deve essere uguale a 1

INSERT INTO &AMGIT..AM_KEY_VALUE git VALUES ( 'smartwelfare.rilevazionepresenze.abilita','1','1','','N','0','param.globali','','Flag abilitare la visualizzazione di pannelli solo per il SIGESS o per chi ha il verticale Rilevazione Presenze', (SELECT MAX(ID)+1 FROM &AMGIT..AM_KEY_VALUE) );