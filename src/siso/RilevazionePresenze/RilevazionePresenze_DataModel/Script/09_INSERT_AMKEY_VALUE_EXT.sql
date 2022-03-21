---per i Clienti che non hanno il verticale Rilevazione Presenze il parametro non deve essere uguale a 1

INSERT INTO &AMGIT..AM_KEY_VALUE_EXT (KEY_CONF, VALUE_CONF,EXT_TYPE, SECTION_NAME, ID) 
                VALUES ('smartwelfare.rilevazionepresenze.abilita', '1' ,'1', 'param.globali', &AMGIT..SEQ_KEY_VALUE_EXT.nextval);