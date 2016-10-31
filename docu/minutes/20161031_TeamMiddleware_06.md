## Protokoll Team Middleware Meeting #6

Datum: 31.10.2016

Teilnehmer
Nico, Fabian, Michel

### Agenda

1. Review 24.10.
2. Usecase: Gamecontroller
3. Zeitplan
4. Todo

### 6.1 Review 24.10
- Die Beacons geben uns eine ungefähre Position
- Die neuen Sensortypen erfordern eine refaktorisierung der Valuetypen, was bis zum nächsten Termin umgesetzt ist.
- Grundlegendes Setup-Script funktioniert, es fehlen noch einige Requirement-überprüfung
- vUSB für den Gamecontroller wurde verworfen, da wir es nicht zum laufen bekommen haben und es eine Lizenz erfordert

### 6.2 Usecase: Gamecontroller
- Hauke bestellt uns 2 Arduino Leonardo damit wir zusammen mit einem NODE MCU daraus tastendrücke simulieren können.

### 6.3 Zeitplan
Bis inkl. Montag 07.11.2016:
- Nico refaktorisiert die Valuetypen
- Fabian passt den TestdatenGenerator an die neuen Values an
- Michel passt die DB-Funktionen an die neuen Values an
- Michel implementiert Twitter in der Server-Applikation
- Nico überarbeitet die Triangulation um vielleicht bessere Ergebnisse zu erhalten
- Lukas und Nico gestalten die Schnittstelle für den Gamecontroller (NodeMCU) 


### 6.4 TODO

- Datenbearbeitung klären
- fehlersemantik, caching
- Software-Architektur erstellen
- Kontroll-Strukturen entwerfen (review, tests?)