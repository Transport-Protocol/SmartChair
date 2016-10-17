Team Database 17.10.2016

##Teilnehmer: 
Rene, Ralf, Julia

## Agenda
	aktueller Status
	vorläufige Zielsetzungen für den Rest des Projekts
	Aufgabenverteilung bis nächste Woche

##Aktueller Status

Server:
- nimmt Daten aus der Datenbank auf und sendet diese an den Client
- nur aktuellster Datensatz -> noch nicht für mehrere Stühle geeignet
Client: 
- Druckdaten an falscher Position, aber korrekt
- Temperatur korrekt, jedoch ungünstige grafische Aufbereitung

##vorläufige Zielsetzungen für den Rest des Projekts

Temperaturanzeige
- Skalen anpassen
- Timestamps einbinden

Druckanzeige:
- Updaterate höher (50ms)
- Anordnung der Sensoren in der Grafik

Java Applet einbinden (Stuhl ausrichtung)

weitere Sensoren in die DB und den Server einbinden
-> Darstellung für weitere Sensoren ausarbeiten

Unterstützung für weitere Stühle
-> Server muss Datenbank abfragen
-> Daten an client senden

Lageplan
-> als Navigation/zur Selektion einzelner Stühle verwenden?

Client auf Angular umbauen

##Aufgabenverteilung bis nächste Woche

Ralf
- Angular: socket.io zum laufen bekommen
- Angular: in server einbinden, bzw unseren Server anpassen
Rene und Julia
- Druckanzeige (siehe oben)

- Angular tutorials durcharbeiten

Alle: 
- (opt) Temperaturanzeige (siehe oben)
- Ideen für Darstellung sammeln