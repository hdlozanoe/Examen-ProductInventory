# Inventory Microservice

This is a simple inventory microservice that:

- Stores inventory quantities in-memory
- Exposes endpoints to consult and update quantities
- Calls the `producto` microservice to fetch product details
- Emits inventory-change events as messages printed to the terminal

Running

1. Start the `producto` service (the product microservice) on port 8080.
2. From the `inventory` folder, run:

```powershell
mvn spring-boot:run
```

Endpoints

- `GET /inventory/{productId}` — returns product info (from producto) and inventory.
- `PUT /inventory/{productId}` — set quantity, JSON body: `{ "quantity": 10 }`.
- `POST /inventory/{productId}/buy?quantity=1` — reduces quantity by given amount.

Inventory events are printed to the inventory service console like:

```
[inventory-event] productId=1 quantity changed 10->9
```
