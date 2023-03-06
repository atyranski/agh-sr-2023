# Zadanie domowe 1

## Opis:
* Napisać aplikację typu chat (5 pkt.)
  * Klienci łączą się serwerem przez protokół TCP
  * Serwer przyjmuje wiadomości od każdego klienta i rozsyła je do pozostałych (wraz z id/nickiem klienta)
  * Serwer jest wielowątkowy – każde połączenie od klienta powinno mieć swój wątek
  * Proszę zwrócić uwagę na poprawną obsługę wątków
* Dodać dodatkowy kanał UDP (3 pkt.) 
  * Serwer oraz każdy klient otwierają dodatkowy kanał UDP (ten sam numer portu jak przy TCP)
  * Po wpisaniu komendy ‘U’ u klienta przesyłana jest wiadomość przez UDP na serwer, który rozsyła ją do pozostałych klientów 
* Wiadomość symuluje dane multimedialne(można np. wysłać ASCII Art) Zaimplementować powyższy punkt w wersji multicast (2 pkt.)
  * Nie zamiast, tylko jako alternatywna opcja do wyboru (komenda ‘M’)
  * Multicast przesyła bezpośrednio do wszystkich przez adres grupowy (serwer może, ale nie musi odbierać)

## Uwagi:
* Zadanie można oddać w dowolnym języku programowania
* Nie wolno korzystać z frameworków do komunikacji sieciowej – tylko gniazda! Nie wolno też korzystać z Akka
* Wydajność rozwiązania (np. pula wątków)
* Poprawność rozwiązania (np. unikanie wysyłania wiadomości do nadawcy, obsługa wątków)

## Uruchomienie

Aby zbudować projekt należy wywołać komendy (najłatwiej z poziomu InteliiJ puścić dwa taski z paska z boku)
```
  gradle clean build
```

Otworzyć terminal i przejść do odpowiednich folderów (ofc można sobie przekopiować zbudowane jar'y gdzie indziej)
* `server/build/libs` - lokalizacja jar'a z programem serwera
* `client/build/libs` - lokalizacja jar'a z programem client'a

### Serwer
Aby uruchomić serwer potrzebne będą trzy parametry:
* `address` - adres, na którym będzie działał nasz serwer (domyślnie `localhost`)
* `port` - port, na którym będzie działał nasz serwer (domyślnie `3000`)
* `backlog` - maksymalna liczba oczekujących połączeń na gnieździe

Parametry te ustawiamy w momencie uruchomienia programu poprzez przekazanie je jako parametry wywołania.
Przykład:
```
java -jar .\server-0.0.1.jar --address 127.0.0.1 --port 3000 --backlog 20
```

Parametry można przekazywać w dowolnej kolejności.

### Klient
Aby uruchomić clienta potrzebne będą trzy parametry:
* `nickname` - nazwa, pod jaką ten użytkownik będzie widziany przez serwer i innych użytkowników (<b>wymagane</b>)
* `address` - adres serwera, do którego chcemy się podłączyć (domyślnie `localhost`)
* `port` - port, na którym pracuje nasz serwer (domyślnie `3000`)

Parametry te ustawiamy w momencie uruchomienia programu poprzez przekazanie je jako parametry wywołania.
Przykład:
```
java -jar .\client-0.0.1.jar --nickname client-1 --address 127.0.0.1 --port 3000
```

Parametry można przekazywać w dowolnej kolejności.

## Wysyłanie wiadomości
Z aplikacji klienta można wysyłać wiadomości na dwa sposoby - poprzez TCP oraz UDP.

### TCP
Aby wysłać wiadomość poprzez TCP można zastosować prefix `<tcp>`.
Przykład:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
<tcp>Wiadomość testowa TCP - prefix
```

Jako iż w tej aplikacji przesył poprzez protokół TCP jest domyślny, to można też wysyłać wiadomości bez tego prefixu.
Przykład:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
Wiadomość testowa TCP - bez prefixu
```

### UDP
Aby wysłać wiadomość poprzez UDP należy zastosować prefix `<udp>`.
Przykład:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
<udp>Wiadomość testowa UDP - prefix
```