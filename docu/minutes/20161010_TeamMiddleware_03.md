## Protokoll Team Middleware Meeting #3

Datum: 10.10.2016

Teilnehmer
Nico, Fabian, Michel

### Agenda

1. Review 26.09.
2. Status Prototyptest
2. Zeitplan
3. Todo

### 3.1 Review 26.09.
- Wir werden RabbitMQ mit selbst entwickelten Java-Komponenten verwenden.
- Wir haben ein Tool, das uns Beispieldatensätze in die Messagequeue schreibt.
- Es gibt einen ersten Entwurf für das zusammenspiel den einzelnen Komponenten.

### 3.2 Status Prototyptest
- Empfang der Daten von Team Sensor erfolgreich
- Daten aus MQ1 auslesen und an MQ2 weiterreichen erfolgreich
- Daten aus MQ2 auslesen und in DB füllen nicht erfolgreich, da Differenzen in der Datenstruktur

### 3.3 Zeitplan 

Bis inkl. Montag 17.10.2016:
- NTP auf Server und Raspis einrichten
- Timestamps in das Messageprotokll implementieren
- GUI auf Raspi implementieren
- DB-Anbindung fixen
	
### 3.4 TODO

- Datenbearbeitung klären
- fehlersemantik, caching
- Software-Architektur erstellen
- Kontroll-Strukturen entwerfen (review, tests?)