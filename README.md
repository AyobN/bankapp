# Bank App

A native Android banking app with a Kotlin/Spring Boot backend. Customers can log in, view their accounts, and edit account nicknames.

## Structure

- `backend/` — Spring Boot 4 + Kotlin REST API
- `android/` — Native Android app (Kotlin + Jetpack Compose)

## Stack

- **Backend:** Kotlin, Spring Boot 4, Spring Security, Spring Data JPA, JWT (jjwt), MySQL 8
- **Android:** Kotlin, Jetpack Compose, Retrofit, Material 3
- **Database:** MySQL 8 with hand-written schema in `backend/src/main/resources/db/schema.sql`

## API

| Method | Path | Auth |
|---|---|---|
| POST | `/api/auth/login` | — |
| GET | `/api/accounts` | Bearer |
| GET | `/api/accounts/{accountNumber}` | Bearer |
| PATCH | `/api/accounts/{accountNumber}` | Bearer |

## Running Locally

### Prerequisites
- JDK 21+
- MySQL 8 on `localhost:3306`
- Android Studio with an emulator

## Database
### Test Customers

All customers use the password `password123`.

| Customer Number | Password    |
|-----------------|-------------|
| 100000001       | password123 |
| 100000002       | password123 |
| 100000003       | password123 |
| 100000004       | password123 |
| 100000005       | password123 |

### Seeded Accounts

| Account Number | Customer  | Balance    | Nickname           |
|----------------|-----------|------------|--------------------|
| 100000001      | 100000001 | 1,500.50   | Main Checking      |
| 100000002      | 100000001 | 12,000.00  | Savings            |
| 100000003      | 100000001 | 250.75     | —                  |
| 200000001      | 100000002 | 750.25     | Vacation Fund      |
| 300000001      | 100000003 | 5,000.00   | Emergency Fund     |
| 300000002      | 100000003 | 100.00     | —                  |
| 400000001      | 100000004 | 25,000.00  | House Down Payment |
| 400000002      | 100000004 | 800.00     | Daily Spending     |
| 400000003      | 100000004 | 15,000.00  | —                  |
| 400000004      | 100000004 | 3,200.50   | Tax Savings        |
| 500000001      | 100000005 | 450.00     | —                  |
| 500000002      | 100000005 | 8,900.25   | Investment         |
