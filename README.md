# Banking Project
This project is a RESTful API for a banking application that allows customers to create bank accounts and transfer funds between them.

Installation
Clone the repository: git clone https://github.com/raahimthedream/banking_api_code.git

Navigate to the project directory: cd banking_api_challange

Install dependencies: mvn install
### Usage

Start the application: mvn spring-boot:run

The application will run on http://localhost:8080

Endpoints

Create Account

POST /api/v1/customers/{customerId}/accounts

RequestBody: BigDecimal initialDeposit

Response: BankAccount

Creates a new bank account for the specified customer with the initial deposit amount.

### Transfer Funds

POST /api/v1/accounts/{fromAccountId}/transfer?toAccountId={toAccountId}&amount={amount}

RequestBody: None

Response: None

Transfers funds from one bank account to another.

Get Balance

GET /api/v1/accounts/{accountId}/balance

RequestBody: None

Response: BigDecimal balance

Gets the current balance of the specified bank account.
