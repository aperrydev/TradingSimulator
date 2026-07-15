# TradingSimulator

A console-based stock trading simulator built in Java with live market data,
cost-basis portfolio tracking, and SQLite persistence.

## Features

* Live stock prices via the Alpha Vantage API
* Buy and sell stocks with real ticker symbols
* Average-cost basis tracking per position with realized P&L on every sale
* Exact monetary math using BigDecimal throughout (no floating-point money)
* SQLite persistence via JDBC — accounts and portfolios survive restarts
* Input validation and API error handling (invalid tickers, rate limits)
* JUnit 5 test suite covering trading logic and edge cases

## Setup

1. Get a free API key at [alphavantage.co](https://www.alphavantage.co/)
2. Create a `config.properties` file in the project root: API_KEY = your_key_here
3. Add the jars in `lib/` to your classpath (IntelliJ: File → Project
   Structure → Modules → Dependencies)
4. Run `Main.java` — the database (`trading.db`) is created automatically

## Built With

* Java
* Alpha Vantage API
* SQLite (via xerial sqlite-jdbc)
* JUnit 5

## Roadmap

* [ ] User login with multiple persistent accounts
* [ ] FIFO lot tracking as an alternative cost-basis method
* [ ] Quote caching to respect API rate limits
* [ ] Spring Boot REST API
* [ ] Web frontend
