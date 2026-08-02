# PLAN — System rezerwacji wizyt (nauka Spring Boot)

## Zasady pracy
- Piszę sam, literka po literce — bez autouzupełniania całych bloków przez AI.
- Utknąłem → 20–30 min własnej walki (błąd, debugger, dokumentacja), dopiero potem pytam w trybie "naprowadź, nie rozwiązuj".
- Po każdym większym kawałku — code review + commit.
- Cel to rozumienie każdej linijki, nie działający kod. Regularność (godzina dziennie) bije intensywność.

## Stack
IntelliJ, Java 21, Maven, Spring Boot, Spring Web, Spring Data JPA, H2 (in-memory), Bean Validation.

---

## Etap 1 — CRUD ✅
- 5 endpointów (POST / GET lista / GET po id / PUT / DELETE)
- Constructor injection (DI), kontener IoC
- JpaRepository (proxy generowane w locie)
- Encja JPA (@Entity, @Id, @GeneratedValue, konstruktor bezargumentowy)
- Optional z .map() / orElseGet() — obsługa "nie znaleziono" bez if
- ResponseEntity (kiedy .build(), kiedy nie)
- Baza H2 in-memory (dane znikają po restarcie)

## Etap 2 — DTO, walidacja, reguły biznesowe — częściowo ✅
- **Request DTO** ✅ (record; klient nie narzuci id)
- **applyRequest** ✅ (wydzielone mapowanie request→encja, DRY)
- **Walidacja pól** ✅ (@NotBlank / @NotNull + @Valid → 400)
- **Globalna obsługa błędów** ✅ (@RestControllerAdvice + własne wyjątki)
- **Reguła czasów** ✅ (endTime po startTime → 400)
- **Reguła kolizji terminów** ✅ (własne @Query JPQL, 409, + self-collision przy update)
- **Response DTO** ✅ (encja odizolowana z obu stron; stream().map().toList())
- [ ] **Druga encja `Customer` + relacja `@ManyToOne`** ← następny krok
- [ ] Lazy loading (pojawi się przy relacji)

## Etap 3 — logika biznesowa (do zrobienia)
- [ ] Reguły typu: anulowanie najpóźniej dobę wcześniej, statusy rezerwacji
- [ ] status jako enum (zamiast String)

## Etap 4 — testy (do zrobienia)
- [ ] Testy jednostkowe serwisu (JUnit, Mockito)
- [ ] Testy integracyjne endpointów

## Etap 5 — dodatki (do zrobienia)
- [ ] Spring Security (logowanie)
- [ ] Migracje bazy (Flyway)
- [ ] PostgreSQL zamiast H2 (dane przeżywają restart)
- [ ] Docker + docker-compose (aplikacja + Postgres)

## Etap 5b — refaktor architektoniczny (do zrobienia)
- [ ] Przejście z architektury warstwowej na heksagonalną (porty i adaptery, wydzielenie domeny)
- [ ] Cel edukacyjny: poczuć na własnym kodzie, co ta architektura zmienia i co kosztuje

## Kiedyś (nie teraz)
- [ ] Kubernetes — dopiero gdy będzie wiele serwisów; przy jednej apce uczy głównie frustracji
- [ ] CI/CD, monitoring

---

## Notatki / drobiazgi do dopięcia
- [ ] **Konwencje REST w URL-ach** — /create, /get są zbędne (czasownik niesie metoda HTTP). Poprawić na /reservation, /reservation/{id}.
- [ ] **Mapper jako osobna klasa** — gdy encji i DTO przybędzie, wydzielić mapowanie z serwisu.
- [ ] **Własne komunikaty walidacji po polsku** (@NotBlank(message="...")).
- [ ] **ReservationNotFoundException** — alternatywny styl obsługi "nie znaleziono" (jawny wyjątek zamiast Optional). Trzymać jeden styl konsekwentnie.
- [ ] **Long → UUID dla id** — powód: IDOR (odgadywalne id), brak wycieku liczby rekordów. Zrobić przy okazji bezpieczeństwa (Etap 5).
- [ ] **Optymalizacja wielokrotnych wywołań bazy w update** (walidacja + findById + save).
- [ ] **orElse vs orElseGet** — orElse liczy argument zawsze, orElseGet leniwie (tylko gdy pusto).
- [ ] **Cross-field validation przez własną adnotację na record** — elegancko, ale sporo kodu; na później.
- [ ] **status jako enum** zamiast String.

## Tematy na rozmowy rekrutacyjne (opanowane po drodze)
- checked vs unchecked exceptions (RuntimeException = unchecked, nie wymusza try-catch)
- overlap dwóch przedziałów przez negację (De Morgan)
- constructor injection > field injection
- czemu Optional zamiast null