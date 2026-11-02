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

## Architecture

```
src/test/java
├── base         BaseTest (setup/teardown, config) and DriverFactory (Chrome/Firefox/Grid)
├── pages        Page objects: Login, Products, Cart, Checkout, Confirmation, Orders
├── tests        TestNG tests: LoginTest, CartTest, CheckoutTest (data-driven)
├── listeners    TestListener (report + screenshots), RetryTransformer (auto-retry)
└── utils        JsonReader, ScreenshotUtil, ExtentManager, RetryAnalyzer
src/test/resources
├── testdata     JSON test data for the data provider
└── config       Layered settings (example file committed, real one git-ignored)
.github/workflows  CI: Chrome and Firefox matrix, report uploaded as artifact
docker-compose.yml Selenium Grid (hub + Chrome and Firefox nodes)
```

### Design decisions
- **Page Object Model:** locators and page actions live in `pages`, so tests read like steps and a UI change is fixed in one place.
- **Explicit waits only:** no `Thread.sleep`, so tests wait for the actual condition and stay fast and stable.
- **ThreadLocal driver:** each parallel test thread gets its own `WebDriver`, so threads never share a browser.
- **One account per thread:** parallel tests log in with different accounts so they don't interfere.
- **Layered config:** defaults, then local file, then environment variables, so secrets stay out of Git and CI can override anything.
- **Listeners:** reporting, screenshots on failure and retries are kept out of the tests.

## Limitations
- **Firefox:** Verified in CI (GitHub Actions) only. It was not run locally because the geckodriver download was blocked on my machine.
- **Selenium Grid:** `docker-compose.yml` and the `RemoteWebDriver` path in `DriverFactory` are written but have not been run, Docker needs virtualization, which isn’t available on my laptop, so the Grid setup is unverified.
- **Verified:** Chrome locally (headed, headless, parallel) and in CI; Firefox in CI.
- **Next steps:** Run the Grid once Docker is available, and confirm the tests pass through it.