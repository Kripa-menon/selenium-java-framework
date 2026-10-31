# Selenium Java Test Automation Framework

A data-driven, cross-browser Selenium framework for an e-commerce practice site
(https://rahulshettyacademy.com/client). It covers login, cart, checkout and order verification.

## Tech stack
- Java 21, Maven
- Selenium 4 (Selenium Manager for drivers)
- TestNG (data providers, listeners, parallel execution, retry analyzer)
- Extent Reports (HTML report with failure screenshots)
- Jackson (JSON test data)
- GitHub Actions (CI), Docker Compose (Selenium Grid)

## How to run locally
1. Clone the repo.
2. Copy `src/test/resources/config.example.properties` to `config.properties` in the same folder, and fill in your own test accounts. This file is git-ignored.
3. Run all tests:
   ```
   mvn test
   ```
4. Open `target/extent-report.html` for the report. Failure screenshots are in `target/screenshots`.

Headless or another browser (PowerShell):
```
$env:BROWSER="firefox"; $env:HEADLESS="true"; mvn test
```

## Configuration
Settings are layered, and later layers win:
1. `config.example.properties` (committed defaults)
2. `config.properties` (local, ignored by Git)
3. Environment variables (`BROWSER`, `HEADLESS`, `GRID_URL`, `EMAIL_1`, `PASSWORD_1`, and so on), used in CI