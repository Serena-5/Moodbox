# 📦 Moodbox

Moodbox è una piattaforma e-commerce innovativa che propone **box emozionali multisensoriali**, pensate per offrire un’esperienza di benessere, introspezione e leggerezza. 

Il progetto gestisce l'intera esperienza di vendita, dalla navigazione degli utenti fino alla gestione logistica del catalogo e degli ordini.

---

## 🚀 Funzionalità Principali

### 👤 Area Cliente
Una volta effettuato l'accesso alla propria area riservata, il cliente potrà:
* Esplorare il catalogo delle molteplici box emozionali offerte.
* Selezionare le proprie box preferite e aggiungerle al carrello.
* Procedere all'acquisto in modo sicuro.
* Visualizzare lo storico dei propri ordini.

### 💼 Area Dipendente
La piattaforma prevede un'area dedicata allo staff aziendale per la gestione del business:
* Visualizzare e gestire tutti gli ordini ricevuti.
* Aggiornare il catalogo prodotti (inserimento di nuove box o rimozione di quelle esistenti).
* Monitorare lo stato delle scorte.

---

## 🛠️ Tecnologie Utilizzate

Il progetto è stato sviluppato seguendo l'architettura MVC (Model-View-Controller) sfruttando lo stack tecnologico classico del web development in Java:

* **Backend:** Java Servlet, JSP 
* **Frontend:** HTML5, CSS3, JavaScript
* **Database:** MySQL
* **IDE consigliato:** Eclipse IDE for Enterprise Java and Web Developers
* **Web Server:** Apache Tomcat

---

## 💻 Come Avviare il Progetto Localmente

Per testare o contribuire a Moodbox sul tuo computer, segui questi passaggi:

### Prerequisiti
Assicurati di avere installato sul tuo sistema:
* Java JDK (versione 8 o superiore)
* Eclipse IDE (con supporto Web Tools Platform - WTP)
* MySQL Server & MySQL Workbench
* Apache Tomcat

### Configurazione del Database
1. Apri MySQL Workbench ed esegui lo script SQL del database per creare le tabelle e popolare i dati iniziali.
2. Aggiorna i dati di connessione al database (host, username, password) all'interno del file di configurazione Java del progetto.

### Configurazione su Eclipse
1. Clona questo repository o scarica il codice sorgente.
2. Apri Eclipse e seleziona `File > Import > Existing Projects into Workspace`.
3. Associa il server **Apache Tomcat** al progetto tramite le proprietà del progetto (`Targeted Runtimes`).
4. Fai click con il tasto destro sul progetto -> `Run As > Run on Server`.
