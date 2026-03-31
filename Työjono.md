## Työjono

### HTTP Basic Authentication

- Selvitetään, miten Spring Security lisätään projektiin  
- Tehdään SecurityConfig-luokka  
- Suojataan /api/** endpointit Basic Authenticationilla  
- Luodaan in-memory-käyttäjät (esim. admin, seller, checker)  
- Poistetaan CSRF käytöstä, jotta Postman-testaus toimii  

### Testaus

- Testataan Postmanilla:
  - ilman tunnuksia → 401 Unauthorized  
  - väärillä tunnuksilla → 401 Unauthorized  
  - oikeilla tunnuksilla → pyyntö onnistuu  

### Viimeistely

- Korjataan mahdolliset virheet  
- Varmistetaan, että autentikointi toimii kaikkien tarvittavien endpointtien kanssa  


### Tietokanta (MySQL)

- Asenna MySQL lokaalisti
- Luo tietokanta
- Luo käyttäjä ja anna oikeudet
- Testaa yhteys (esim. MySQL Workbench)

### Spring Boot konfigurointi

- Lisää MySQL dependency (pom.xml)
- Konfiguroi application.properties
- Käynnistä backend MySQLä
- Testaa että sovellus käynnistyy ilman virheitä
- Testaa CRUD endpoint toimii

### Dokumentaatio

- Kirjoita: miten MySQL otetaan käyttöön
- Kirjoita: miten vaihdetaan takaisin H2
- Kirjoita API-dokumentaatio
- Päivitä README.md
