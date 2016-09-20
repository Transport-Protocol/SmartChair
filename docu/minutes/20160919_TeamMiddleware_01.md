## Protokoll Team Middleware Meeting #1

Datum: 19.09.2016

Teilnehmer
Nico, Fabian, Michel

### Agenda

1. Schnittstellen zu den anderen Teams
2. Technologien
3. Zeitplan
4. TODO

### 1.1 Schnittstellen zu den anderen Teams

- Zu den Sensoren stellen wir eine Message-Que zur Verfügung, in die diese Daten schieben können. Datenstruktur und -typen zeitnah klären.
- Die Datenbank wird als Schnittstelle zum DB-Team deklariert. 
  Wir implementieren einen Server, der den Netzwerktraffic regelt und die Daten in der DB ablegt. Rückkanäle wie z.B. Trigger sind zu erarbeiten.

### 1.2	Technologien

- Lösung für Messageque
- Server-Applikation auf dem Ubuntu-PC
- Client-Applikationen auf den RaspPies
- Programmiersprache

### 1.3 Zeitplan 

Bis inkl. Montag 26.09.2016:

	Nico betreibt Technologieanalyse und bereitet Vorschläge für die zu verwendenden und strukturen vor.
	Fabian organisiert informationen über Sensordaten und entwirft einen Sensortestdatengenerator.
	Michel klärt mit dem DB-Team das Datenbankschema und die Anbindung an die Server-Applikation
Bis inkl. Montag 03.10.2016:
	Testdaten aus dem Generator sollen an die DB weitergereicht werden und verwendbar sein.

### 1.4 TODO

- Datenbearbeitung klären
- fehlersemantik, caching
- Software-Architektur erstellen
- Kontroll-Strukturen entwerfen (review, tests?)
- Netzwerkprotokoll zwischen Server und Client entwerfen