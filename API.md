# TicketGuru API -dokumentaatio

## Johdanto

Tämä dokumentti kuvaa TicketGuru-järjestelmän REST-rajapintaa tapahtumien, lippujen ja myyntitapahtumien käsittelyyn. Rajapinta mahdollistaa tapahtumien hallinnan, lippujen luomisen, myyntitapahtumien käsittelyn ja lippujen tarkastamisen.

## Base URL

`http://localhost:8080/api`

## Autentikaatio

Ei vaadita tässä kehitysversionissa.

---

## Testailija (Ovella)

### GET /api/tickets/{code}
Hakee lipun sen tunnistekoodin perusteella (lippujen tarkastus ovella).

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus            |
|------|--------|-------------------|
| code | String | Lipun tunnitekoodi |

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi     | Kuvaus             |
|---------------|-------------------|
| 200 OK        | Lipun tiedot       |
| 404 Not Found | Lippua ei löytynyt |

**Vastaus-esimerkki (200):**
```json
{
  "id": 1,
  "code": "A1B2C3D4E5F6G7H8",
  "status": "VALID",
  "ticketType": {
    "id": 1,
    "description": "Aikuinen",
    "price": 25.00
  },
  "sale": {
    "id": 1,
    "createdAt": "2026-03-03T10:30:00"
  },
  "usedAt": null
}
```

### PUT /api/tickets/{code}/use
Merkitsee lipun käytetyksi (ovella tarkastuksella).

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus            |
|------|--------|-------------------|
| code | String | Lipun tunnistekoodi |

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi              | Kuvaus                    |
|------------------------|---------------------------|
| 200 OK                 | Lippu merkitty käytetyksi |
| 404 Not Found          | Lippua ei löytynyt        |
| 409 Conflict           | Lippu on jo käytetty      |

---

## Lipunmyyjä

### POST /api/sales
Luo uuden myyntitapahtuman ja luo automaattisesti liput.

**Polkuparametrit:** Ei

**Query-parametrit:** Ei

**Request Body (JSON):**

```json
{
  "sellerId": 1,
  "eventId": 1,
  "items": [
    { "ticketTypeId": 1, "quantity": 2 },
    { "ticketTypeId": 2, "quantity": 1 }
  ]
}
```

**Vastaus:**

| Tilakoodi       | Kuvaus                         |
|-----------------|--------------------------------|
| 201 Created     | Myynti luotu onnistuneesti     |
| 400 Bad Request | Virheellinen syöte             |

**Vastaus-esimerkki (201):**
```json
{
  "id": 5,
  "createdAt": "2026-03-03T10:30:00",
  "totalAmount": 75.00,
  "sellerId": 1,
  "ticketIds": [12, 13, 14]
}
```

### GET /api/sales
Hakee kaikki myyntitapahtumat.

**Polkuparametrit:** Ei

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi | Kuvaus          |
|-----------|-----------------|
| 200 OK    | Lista myynneistä |

### GET /api/sales/{id}
Hakee yksittäisen myyntitapahtuman ID:n perusteella.

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus            |
|------|--------|-------------------|
| id   | Long   | Myynti-ID         |

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi     | Kuvaus                 |
|---------------|------------------------|
| 200 OK        | Myyntitapahtuman tiedot |
| 404 Not Found | Myyntiä ei löytynyt    |

### GET /api/tickets
Hakee kaikki liput (valinnainen status-suodatin).

**Polkuparametrit:** Ei

**Query-parametrit:**

| Nimi   | Tyyppi | Kuvaus                          |
|--------|--------|--------------------------------|
| status | String | Suodatetaan statuksen perusteella (VALID/USED) |

**Request Body:** Ei

**Vastaus:**

| Tilakoodi | Kuvaus   |
|-----------|----------|
| 200 OK    | Lista lipuista |

---

## Admin

### GET /api/events
Hakee kaikki tapahtumat järjestelmästä.

**Polkuparametrit:** Ei

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi | Kuvaus             |
|-----------|--------------------|
| 200 OK    | Lista tapahtumista |

### GET /api/events/{id}
Hakee yksittäisen tapahtuman ID:n perusteella.

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus              |
|------|--------|---------------------|
| id   | Long   | Tapahtuman tunniste |

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi     | Kuvaus                 |
|---------------|------------------------|
| 200 OK        | Tapahtuman tiedot      |
| 404 Not Found | Tapahtumaa ei löytynyt |

### POST /api/events
Luo uuden tapahtuman.

**Polkuparametrit:** Ei

**Query-parametrit:** Ei

**Request Body (JSON):**

```json
{
  "name": "Kevätmessut 2026",
  "venue": "Messukeskus",
  "city": "Helsinki",
  "startTime": "2026-04-10T10:00:00"
}
```

**Vastaus:**

| Tilakoodi       | Kuvaus                        |
|-----------------|-------------------------------|
| 201 Created     | Tapahtuma luotu onnistuneesti |
| 400 Bad Request | Virheellinen syöte            |

### PUT /api/events/{id}
Päivittää olemassa olevan tapahtuman.

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus              |
|------|--------|---------------------|
| id   | Long   | Tapahtuman tunniste |

**Query-parametrit:** Ei

**Request Body (JSON):** Päivitetyt tapahtuman tiedot

**Vastaus:**

| Tilakoodi     | Kuvaus                 |
|---------------|------------------------|
| 200 OK        | Tapahtuma päivitetty   |
| 404 Not Found | Tapahtumaa ei löytynyt |

### DELETE /api/events/{id}
Poistaa tapahtuman.

**Polkuparametrit:**

| Nimi | Tyyppi | Kuvaus              |
|------|--------|---------------------|
| id   | Long   | Tapahtuman tunniste |

**Query-parametrit:** Ei

**Request Body:** Ei

**Vastaus:**

| Tilakoodi     | Kuvaus                 |
|---------------|------------------------|
| 204 No Content | Poisto onnistui        |
| 404 Not Found  | Tapahtumaa ei löytynyt |

---

## Tiedon mallit

### Sale (Myyntitapahtuma)
```json
{
  "id": "Long",
  "createdAt": "LocalDateTime",
  "totalAmount": "BigDecimal",
  "seller": {
    "id": "Long",
    "username": "String"
  },
  "tickets": ["List<Ticket>"]
}
```

### Ticket (Lippu)
```json
{
  "id": "Long",
  "code": "String",
  "status": "String (VALID/USED)",
  "ticketType": {
    "id": "Long",
    "description": "String",
    "price": "BigDecimal"
  },
  "sale": {
    "id": "Long"
  },
  "usedAt": "LocalDateTime"
}
```

### Event (Tapahtuma)
```json
{
  "id": "Long",
  "name": "String",
  "venue": "String",
  "city": "String",
  "startTime": "LocalDateTime",
  "ticketTypes": ["List<TicketType>"]
}
```

### TicketType (Lipputyyppi)
```json
{
  "id": "Long",
  "description": "String (esim. Aikuinen, Lapsi)",
  "price": "BigDecimal",
  "event": {
    "id": "Long"
  }
}
```

---

## Huomiot
- API käyttää H2 in-memory -tietokantaa kehitykseen.
- Kaikki vastaukset ovat JSON-muodossa.
- Päivämäärät ovat ISO-muodossa (esim. 2026-03-03T10:00:00).
- Lippukoodit generoidaan automaattisesti jokaisen lipun luonnissa.
- Lippuja voidaan merkitä käytetyksi ainoastaan kertaalleen (status muuttuu VALID → USED).

