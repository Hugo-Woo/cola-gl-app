# COLA General Ledger Application

This is a **General Ledger (GL)** application built using the **COLA (Clean Object-oriented & Layered Architecture)** framework. It demonstrates standard **Domain-Driven Design (DDD)** principles including Aggregates, Domain Services, and Gateways.

## Architecture

The project is divided into modules:
*   **start**: Application entry point and configuration.
*   **adapter**: HTTP Adapters (REST Controllers).
*   **app**: Application Services and Command/Query Executors. Orchestration layer.
*   **domain**: Pure business logic (Voucher, Balance, Domain Services). dependency-free.
*   **infrastructure**: Implementation of Repositories, Database access (H2), Gateways.
*   **client**: API definitions (DTOs, Commands, Services Interfaces).

## Features

1.  **Voucher Management**: Define Accounting Vouchers (Journals).
2.  **Posting (Entry)**: Post vouchers to update account balances using Double-Entry Bookkeeping rules.
3.  **Balance Query**: Check account balance for a specific period.

## Usage

### 1. Add Voucher
```bash
curl -X POST http://localhost:8080/api/voucher -H "Content-Type: application/json" -d '{
    "voucherDTO": {
        "voucherCode": "V001",
        "accountingDate": "2024-01-15",
        "description": "Initial Capital",
        "lines": [
            { "accountCode": "1001", "direction": "D", "amount": 1000.00, "description": "Cash" },
            { "accountCode": "3001", "direction": "C", "amount": 1000.00, "description": "Capital" }
        ]
    }
}'
```
Response: `{"success":true,"data":1}`

### 2. Post Voucher
```bash
curl -X POST http://localhost:8080/api/voucher/post -H "Content-Type: application/json" -d '{
    "voucherId": 1
}'
```

### 3. Check Balance
```bash
curl "http://localhost:8080/api/balance?accountCode=1001&period=2024-01"
```
Response: `{"success":true,"data":1000.00}`

## Build & Run
```bash
mvn clean install
java -jar cola-gl-start/target/cola-gl-start-1.0.0-SNAPSHOT.jar
```
