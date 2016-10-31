## Protokoll Team Middleware Meeting #5

Datum: 24.10.2016

Teilnehmer
Nico, Fabian, Michel

### Agenda

1. Review 17.10.
2. Usecase: Gamecontroller
3. Zeitplan
4. Todo

### 5.1 Review 17.10
- Timestamps werden erfolgreich übertragen
- DebugDataGenerator kann mit Parametern gestartet werden, sodass er auch direkt in die Datenbank schreibt
- build&deploy funktioniert sowohl auf den Server, als auch auf die Raspi

### 5.2 Usecase: Gamecontroller
- Lukas und Nico untersuchen die Möglichkeit, die Sensordaten als Controller-Input für ein Viedeospiel zu verwenden.

### 5.3 Zeitplan
Bis inkl. Montag 31.10.2016:
- Nico baut Triangulation mit den Beacons
- Dokumentation erstellen, insbesondere Benutzeranleitung für den DebugDataGenerator
- Fabian passt den DebugDataGenerator an, sodass er auch die neuen Sensortypen unterstützt 
- Michel schreibt ein Setup-Script für den Raspi

### 5.4 TODO

- Datenbearbeitung klären
- fehlersemantik, caching
- Software-Architektur erstellen
- Kontroll-Strukturen entwerfen (review, tests?)