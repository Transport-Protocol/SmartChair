## Protokoll Team Middleware Meeting #4

Datum: 17.10.2016

Teilnehmer
Nico, Fabian, Michel

### Agenda

1. Review 10.10.
2. Status Prototyptest
3. Zeitplan
4. Todo

### 4.1 Review 10.10
- Da NTP nicht durch die Firewall des netzwerkes kommt, stellt der Ubuntu-Server den RasPis NTP zur synchronisation zur Verfügung. Der Server wird grob über http-timestamps synchronisiert.
- Timestamps sind im das Messageprotokll implemtentiert.
- DB-Anbindung funktioniert.
- GUI für Debugging ist auf dem Raspi-Display verfügbar

### 4.2 Status Prototyptest
- Empfang der Daten von Team Sensor erfolgreich
- Daten aus MQ1 auslesen und an MQ2 weiterreichen erfolgreich
- Daten aus MQ2 auslesen und in DB füllen erfolgreich

### 4.3 Zeitplan
Bis inkl. Montag 24.10.2016:
- Übertragung der Timestamps in die Datenbank testen
- DebugdatenGenerator erweitern, sodass er direkt in die DB schreibt
- Gradle Buildscript mit build&deploy versehen

### 4.3 TODO

- Datenbearbeitung klären
- fehlersemantik, caching
- Software-Architektur erstellen
- Kontroll-Strukturen entwerfen (review, tests?)