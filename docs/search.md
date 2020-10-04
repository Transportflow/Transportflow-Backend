# Stop Search

Find a stop nearby or by name with the following endpoints.

# Nearby

Get stops geographically located around a coordinate.

### Request

GET `/:provider/nearby`

**Query Params:**
| Name        | Description                   | Type     | Required        |
| ----------- | ----------------------------- | -------- | --------------- |
| lat         | Search origin latitude        | Double   | Yes             |
| lng         | Search origin longitude       | Double   | Yes             |
| radius      | Search radius                 | Int      | No, default 250 |
| results     | Max. number of stops          | Int      | No, default 5   |
| stops       | Search for stops              | Bool     | No              |
| pois        | Search for points of interest | Bool     | No              |

### Response

`200` - Success
```json
[
  {
    "id": "33000005",
    "name": "Pirnaischer Platz",
    "location": {
      "type": "location",
      "name": "Pirnaischer Platz",
      "address": "Pirnaischer Platz",
      "longitude": 13.74342676007171,
      "latitude": 51.04964500490755,
      "altitude": 0
    },
    "products": [
      {
        "name": "Tram",
        "title": "Straßenbahn",
        "img": "https://upload.wikimedia.org/wikipedia/commons/a/a6/Tram-Logo.svg"
      },
      ...
    ],
    "distance": 98
  },
  ...
]
```

`400` - Bad Request<br>
Latitude/Longitude are not specified or no numbers

# Search

Get stops based on a search string.

### Request

GET `/:provider/locations`

**Query Params:**
| Name        | Description                   | Type     | Required        |
| ----------- | ----------------------------- | -------- | --------------- |
| query       | Search string                 | String   | Yes             |
| results     | Max. number of stops          | Int      | No, default 7   |
| address     | Search for addresses          | Bool     | No              |
| pois        | Search for points of interest | Bool     | No              |

### Response

`200` - Success
```json
[
  {
    "id": "33000005",
    "name": "Pirnaischer Platz",
    "location": {
      "type": "location",
      "name": "Pirnaischer Platz",
      "address": "Pirnaischer Platz",
      "longitude": 13.74342676007171,
      "latitude": 51.04964500490755,
      "altitude": 0
    },
    "products": [
      {
        "name": "Tram",
        "title": "Straßenbahn",
        "img": "https://upload.wikimedia.org/wikipedia/commons/a/a6/Tram-Logo.svg"
      },
      ...
    ],
    "distance": 0
  },
  ...
]
```

`400` - Bad Request<br>
No query specified
