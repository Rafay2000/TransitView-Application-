TransitView – MVP produkt

Prosjektbeskrivelse

Dette prosjektet er et objektorientert transportsystem utviklet i Java, med SQL-database for persistenslagring og databasehåndtering. Systemet simulerer rutebaserte busstjenester og gir mulighet til å planlegge, lagre, favorisere,  og hente trips, ruter, stopp, busser og tidsplaner.

Hovedmålet med prosjektet er å bygge en Minimum Viable Product (MVP) som demonstrerer hvordan et transportsystem kan håndtere flere brukere, ruter, busser og tidsplaner på en strukturert og skalerbar måte. Samt. gi brukeren de nødvendige detaljene enn vanlig kolletkivtransport sytsem ikke fremvises.

Case:

I oppgaven simuleres en kollektivtransport-situasjon med flere busser, stopp med tilhørende tider og ruter. Systemet skal:

Registrere busser med kapasitet og type.
Definere ruter med flere stopp i riktig rekkefølge.
Legge til tidsplaner for hver trip med avgang og ankomst for hvert stopp.
Tillate kunder å se trips og tilhørende rute og tid.
Administrere data gjennom repository-lag med SQL-implementasjon.

Eksempel-case brukt i systemet:

Rute FR-GR03: fra Fredrikstad Busstasjon → Glemmen → Østfoldhallen → Greåker.

Buss R4 kjører ruten, med avgang og ankomst tidspunkter definert i Schedule, ekstra info tilknyttet til ruten som estimert tid til neste bussholdeplass, km avstand, og forklarking på hvor bussen stopper.

Kunde kan reservere eller se informasjon om trips.

Systemstruktur følger en hexadoginal-struktur på følgende måte:

## Prosjektstruktur
```
backend/
└── src/
└── main/
├── java/
│ └── hiof_project/
│ ├── domain/
│ │ ├── exception/
│ │ └── model/
│ │ ├── transport_system/
│ │ └── user_system/
│ └── service/
├── infrastructure/
│ └── adapters/
│ ├── api/
│ └── db/
│ └── config/
├── ports/
│ ├── in/
│ └── out/
└── resources/
├── application.properties
└── db_scripts/

docs/
└── diagrams/
├── classdiagram/
└── sequencediagram/

frontend/
└── src/
├── assets/
└── components/
```

Repository-lag: Kommuniserer med SQL-database.

Domain-modeller: Objekter som representerer Bus, Trip, Route, Stop og Schedule.

Schedule & ScheduleTimer: Holdes som objekter i Trip, ingen egen repository – tidsplanen er i rekkefølge.

TripRepository: Håndterer trips og kobler rute, buss og schedule sammen.

Mål med MVP:

Demonstrere grunnleggende CRUD-operasjoner for Bus, Trip, Stop og Route.
Vise hvordan tidsplaner (Schedule) kan kobles til trips.
Lage et klart, modulært repository- og database-oppsett som kan skaleres videre.
Gi en fungerende backend som kan testes med SQL-database uten UI.

Teknologier som ble brukt:
```
Java (backend)
React Framework (CSS, JavaScript og HTML)
SQL (Microsoft SQL i Azure database)
JDBC / PreparedStatement for databasekommunikasjon
Maven / Gradle (prosjektstyring)
Git / GitHub for versjonskontroll
Eventuell APIs for GPS, kart og sanntidskontroll
```
