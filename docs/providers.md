# Providers

To use virtually any function of transportflow a provider has to be submitted.

# Provider overview

Get all available providers.

### Request

GET `/providers`

**Query Params:** None

### Response

`200` - Success
```json
[
  {
    "regionName": "Deutsche Bahn (DB)",
    "region": "Deutsche Bahn",
    "provider": "DB",
    "image": "https://images.unsplash.com/...",
    "textColor": "white",
    "beta": false,
    "products": [
      {
        "name": "nationalExpress",
        "title": "ICE",
        "img": "https://upload.wikimedia.org/wikipedia/commons/b/bc/ICE-Logo.svg"
      },
      ...
    ]
  },
  ...
]
```

# Provider information

Get information about a specific provider.

### Request

GET `/:provider`

**Query Params:** None

### Response

`200` - Success
```json
{
  "regionName": "Deutsche Bahn (DB)",
  "region": "Deutsche Bahn",
  "provider": "DB",
  "image": "https://images.unsplash.com/...",
  "textColor": "white",
  "beta": false,
  "products": [
    {
      "name": "nationalExpress",
      "title": "ICE",
      "img": "https://upload.wikimedia.org/wikipedia/commons/b/bc/ICE-Logo.svg"
    },
    ...
  ]
}
```

`400` - Bad request<br>
Provider not found
