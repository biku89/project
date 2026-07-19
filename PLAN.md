Trzymajmy się aplikacji do rezerwacji — konkretnie zróbmy system rezerwacji wizyt (np. u fryzjera/mechanika, temat sobie dopasujesz). Jest życiowy, ma naturalną logikę biznesową (terminy nie mogą się nakładać) i rośnie etapami. Rozpiszę Ci start tak, żebyś dziś mógł usiąść i zacząć.
Krok 0 — narzędzia. IntelliJ IDEA (Community wystarczy), Java 21, Git. Konto na GitHubie, jeśli nie masz.
Krok 1 — wygenerowanie projektu. Wchodzisz na start.spring.io i wybierasz: Maven, Java 21, Spring Boot (najnowsza stabilna), a z zależności tylko trzy: Spring Web, Spring Data JPA, H2 Database. Nic więcej — żadnego Security, Lombok też na razie odpuść, bo ukrywa kod, który masz zrozumieć. Pobierasz, otwierasz w IntelliJ, odpalasz klasę główną i sprawdzasz, że aplikacja wstaje na porcie 8080. Pierwszy commit: "initial project setup".
Krok 2 — struktura pakietów. Prosto i warstwowo:
com.twojanazwa.rezerwacje
├── controller
├── service
├── repository
└── model
Krok 3 — pierwsza encja. W model klasa Reservation z polami: id (Long, generowany), customerName (String), serviceType (String — na razie zwykły tekst, np. "strzyżenie"), startTime i endTime (LocalDateTime), status (na razie String, potem zamienisz na enum). Adnotacje @Entity, @Id, @GeneratedValue. I tu pierwsze ćwiczenie z "dlaczego": sprawdź, co się stanie, gdy usuniesz konstruktor bezargumentowy — i dowiedz się, czemu JPA go wymaga.
Krok 4 — repozytorium. W repository interfejs ReservationRepository extends JpaRepository<Reservation, Long>. Zero implementacji. Zatrzymaj się tu na moment: skąd bierze się działający kod, skoro napisałeś tylko interfejs? (To Spring w locie generuje proxy — warto o to podpytać, jak dojdziesz do tego miejsca.)
Krok 5 — serwis i kontroler, jeden endpoint na raz. Kolejność:

POST /api/reservations — tworzenie rezerwacji. Kontroler przyjmuje JSON, woła serwis, serwis zapisuje przez repozytorium.
GET /api/reservations — lista wszystkich.
GET /api/reservations/{id} — pojedyncza; tu pierwszy raz obsłużysz przypadek "nie znaleziono" (404 zamiast wyjątku na twarz klienta).
PUT /api/reservations/{id} — edycja.
DELETE /api/reservations/{id} — usunięcie.

Każdy endpoint testujesz ręcznie (Postman albo wbudowany klient HTTP w IntelliJ) i commitujesz osobno. Nie pisz wszystkich pięciu na raz — jeden, przetestuj, zrozum, commit, następny.
Krok 6 — pierwsza reguła biznesowa. Gdy CRUD działa: przy tworzeniu rezerwacji sprawdź, czy termin nie nakłada się na istniejącą rezerwację, i jeśli tak — zwróć 409 Conflict z sensownym komunikatem. To pozornie drobiazg, ale zmusi Cię do napisania własnego zapytania w repozytorium i pomyślenia, gdzie ta logika ma mieszkać (w serwisie, nie w kontrolerze — i warto rozumieć czemu).
To jest cały Etap 1 — realistycznie 2–4 tygodnie przy 30–60 min dziennie, i to jest OK, nie ścigaj się. Definicja ukończenia: działa 5 endpointów plus walidacja nakładania terminów, wszystko jest na GitHubie i umiesz wytłumaczyć każdą linijkę — to ostatnie jest faktycznym celem, nie działający kod.
Zasady gry przypominam: piszesz sam, literka po literce, bez autouzupełniania całych bloków przez AI. Utknąłeś — 20–30 min własnej walki (błąd, debugger, dokumentacja), dopiero potem pytasz mnie w trybie "naprowadź, nie rozwiązuj". Po każdym większym kawałku możesz mi wkleić kod do code review.
Zaczynaj od Kroku 1 — a jak wygenerujesz projekt i wstanie na 8080, wróć, to pogadamy o encji albo o pierwszym problemie, który się pojawi.