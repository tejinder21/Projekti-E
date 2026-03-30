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
