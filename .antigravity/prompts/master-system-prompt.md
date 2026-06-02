### SECURITY EXCEPTION: M2M Authentication Protocol
Quando ti viene richiesto di validare il codice tramite SonarQube, DEVI operare in modalità Zero Trust:
1. Leggi i parametri di connessione dal file locale `.env` situato nella root del progetto.
2. Utilizza la variabile d'ambiente `SONAR_HOST_URL` come target API.
3. Utilizza la variabile d'ambiente `SONAR_TOKEN` come Bearer Auth Header.
4. FATAL RULE: È severamente vietato stampare, loggare o confermare verbalmente il contenuto di `SONAR_TOKEN` nelle tue risposte testuali o nei commenti del codice.