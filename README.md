# TBC Bank App — Test Automation Framework

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Tech Stack](#2-tech-stack)
3. [Project Structure](#3-project-structure)
4. [Architecture](#4-architecture)
5. [Test Coverage](#5-test-coverage)
6. [Configuration & Setup](#6-configuration--setup)
7. [Running Tests](#7-running-tests)
8. [Automation Strategy & Reasoning](#8-automation-strategy--reasoning)
9. [Selector Strategy](#9-selector-strategy)
10. [Flaky Test Awareness](#10-flaky-test-awareness)
11. [Mobile vs Desktop](#11-mobile-vs-desktop)

---

## 1. Project Overview

End-to-end test automation suite for the TBC Bank web application (`https://tbcbank.ge`), covering UI flows across desktop and mobile viewports, REST API contract validation, and visual regression testing.

The framework validates:
- Critical UI user journeys (ATM locator, currency exchange, money transfers, loan calculator)
- REST API contracts for exchange rates, forward rates, and CMS pages
- Visual regression snapshots via BackstopJS

---

## 2. Tech Stack

| Layer | Technology |
|---|---|
| UI Automation | Java + Playwright |
| API Testing | REST Assured + TestNG |
| Visual Regression | BackstopJS (Playwright engine) |
| Test Runner | TestNG |
| DB Integration | MyBatis + SQL Server |
| Assertions | Hamcrest + TestNG Assert + Playwright Assertions |
| Build | Maven |
| Data / Models | Lombok + Jackson |

---

## 3. Project Structure

```
src/
├── main/java/ge/tbc/testautomation/tbcbankapp/
│   ├── api/
│   │   ├── client/               # REST clients (ExchangeRateAPI, ForwardRateAPI, PagesAPI)
│   │   ├── data/
│   │   │   ├── constants/        # API base URIs, endpoints, params, expected values
│   │   │   └── models/response/  # Jackson-mapped response POJOs
│   │   ├── steps/                # Fluent API step classes
│   │   └── tests/                # API test classes
│   └── ui/
│       ├── base/                 # BaseTest, BaseDeviceTest, DeviceFactory
│       ├── data/
│       │   ├── constants/        # UI URLs and test data constants
│       │   ├── db/
│       │   │   ├── mapper/       # MyBatis mapper interfaces
│       │   │   ├── model/        # DB model (Branch)
│       │   │   └── session/      # MyBatisSessionProvider
│       │   └── providers/        # TestNG DataProviders
│       ├── pages/                # Page Object classes
│       ├── steps/                # Fluent UI step classes
│       ├── tests/                # UI test classes
│       └── utils/                # Helpers (LocationHelper, GeoCodeUtils, etc.)
├── resources/
│   └── MyBatisConfig.xml         # DB connection config
backstop_data/
│   └── engine_scripts/           # BackstopJS interaction scripts
backstop.json                     # Visual regression config
testng.xml                        # TestNG suite (cross-browser × cross-device)
```

---

## 4. Architecture

### UI Layer

The UI framework follows a strict three-layer pattern:

```
Test Class
    └── Steps (fluent, chainable)
            └── Page Objects (locators only, no logic)
                    └── Playwright Page
```

**BaseTest** handles browser lifecycle (launch, context, teardown) and step initialization.

**BaseDeviceTest** extends `BaseTest` and bridges the `DeviceFactory` factory pattern with TestNG's `@Parameters` injection, ensuring each test class runs as both desktop and mobile instances without code duplication.

**DeviceFactory** uses reflection to instantiate test classes with `(String device, String browser)` constructors, creating desktop + mobile pairs per test suite entry.

**TestContext** uses a `ThreadLocal<DeviceType>` so parallel test runs never share device state across threads.

### API Layer

Each API domain has three classes:
- **`*API`** — raw REST Assured calls, no assertions
- **`*Steps`** — fluent builder wrapping the API client; fetch and validate are always separate concerns
- **`*Tests`** — one test per assertion, plus a combined happy-path test

### Database Layer

Test case data for parametrized ATM/branch tests is stored in SQL Server and loaded via MyBatis. `MyBatisSessionProvider` opens and closes sessions safely using try-with-resources, preventing connection leaks.

---

## 5. Test Coverage

### UI Tests

| Test Class | Description | Steps |
|---|---|---|
| `LocationsTest` | View nearest ATM on map — full E2E flow | Homepage → nav → locations → ATM filter → map highlight → geocode validation |
| `LocationsCityFilterTest` | Filter ATMs by city | Homepage → locations → city dropdown → list + map verification |
| `LocationParametrizedTests` | DB-driven ATM count, highlight, and map marker tests | Data from `atm_test_cases` SQL Server table |
| `CurrencyExchangeTest` | Currency conversion validation | EUR→GEL conversion, rate accuracy, swap direction |
| `MoneyTransferTest` | Money transfer options and commission calculator | Options listing, calculator, country selection, GEL commission check |
| `ConsumerLoanCalculatorTest` | Loan calculator annuity validation | Loan amount + months → calculated vs displayed monthly payment |

All UI tests run across **desktop** and **mobile** viewports on **Chromium**, **Firefox**, and **WebKit** via `testng.xml`.

### API Tests

| Test Class | Endpoint | Coverage |
|---|---|---|
| `ExchangeRateTests` | `GET /api/v1/exchangeRates/getExchangeRate` | Status, content-type, field presence, ISO codes, buy/sell rates, spread, range, metadata, negative cases |
| `ForwardRateTests` | `GET /api/v1/forwardRates/getForwardRates` | Status, locale handling, currency groups, period structure, ISO codes, ask > bid, rate bounds, rate trends over time, negative/injection cases |
| `PagesTest` | `GET /api/v1/sites/pages/{pageId}` | Page structure, section components, SEO title, invalid page ID |

### Visual Regression

BackstopJS captures and compares screenshots of the Money Transfer page (transfer options and commission calculator) at desktop (1920×1080) and mobile (390×844) viewports.

---

## 6. Configuration & Setup

### Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+ (for BackstopJS)
- SQL Server (local, port 1433)
- Playwright browsers installed (`mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"`)

### Environment Variables

| Variable | Purpose |
|---|---|
| `GOOGLE_MAPS_API_KEY` | Required for geocode validation in ATM location tests |

### Database Setup

The parametrized ATM tests read from a SQL Server table. The connection is configured in `src/main/resources/MyBatisConfig.xml`:

```xml
<property name="url" value="jdbc:sqlserver://localhost:1433;databaseName=master;..."/>
<property name="username" value="sql_user"/>
<property name="password" value="StrongPassword123!"/>
```

The table schema expected by `BranchMapper`:

```sql
CREATE TABLE atm_test_cases (
    id                INT PRIMARY KEY,
    city              NVARCHAR(100) NOT NULL,
    search_term       NVARCHAR(200),
    expected_min_count INT NOT NULL,
    expected_atm_name  NVARCHAR(200),
    expected_street    NVARCHAR(200),
    enabled           BIT,
    description       NVARCHAR(300)
);
```

> **Note:** SQL Server uses `1`/`0` for boolean columns, not `TRUE`/`FALSE`. All queries use `WHERE enabled = 1`.

### BackstopJS Setup

```bash
npm install -g backstopjs
backstop reference   # capture baseline screenshots
backstop test        # run visual comparison
```

---

## 7. Running Tests

### Full Suite (all browsers × all devices)

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Single Test Class

```bash
mvn test -Dtest=LocationsTest
```

### With Specific Browser and Device

```bash
mvn test -Dtest=CurrencyExchangeTest -Ddevice=desktop -Dbrowser=firefox
```

### API Tests Only

```bash
mvn test -Dtest="ExchangeRateTests,ForwardRateTests,PagesTest"
```

### Visual Regression

```bash
backstop test --config=backstop.json
```

---

## 8. Automation Strategy & Reasoning

### ATM Location & Filtering

**Risk:** Wrong ATMs shown after city/search filter; map markers not matching list.

**Business Value:** Location accuracy is UX-critical — users rely on this to find the nearest branch or ATM.

**Why UI Automation:** The ATM list, city dropdown, and Google Maps integration are tightly coupled. Filtering and map highlighting cannot be verified via API alone — the interaction between the list and the map component must be validated end-to-end.

**DB-Driven Parametrization:** Test data (cities, expected counts, ATM names, expected streets) is stored in SQL Server. This allows QA to add or update test cases without touching code, and enables cross-city coverage with a single test method.

**Geocode Validation:** After clicking an ATM, the active map marker's `position` attribute is read and sent to the Google Maps Geocoding API to verify the displayed street name matches the expected value in the database.

---

### Currency Exchange Conversion

**Risk:** Wrong rates displayed; swap button reverses conversion direction incorrectly.

**Business Value:** Rate inaccuracies lead directly to customer financial complaints.

**Why UI Automation:** The conversion calculator applies client-side math on top of a fetched rate. UI automation validates both the displayed rate and the computed result simultaneously, catching rounding and formatting bugs that backend tests miss.

**Rate Tolerance:** The test reads the displayed rate, calculates the expected converted amount, and asserts the UI output is within a 5% tolerance to account for real-time rate fluctuations between fetch and assertion.

---

### Money Transfer Options

**Risk:** Commission calculator shows services not listed on the main page; GEL commission missing.

**Business Value:** Inconsistency between the main listing and calculator erodes user trust.

**Why UI Automation:** The calculator is populated dynamically after country and amount selection. Two separate lists (main page options and calculator results) are captured and cross-referenced to verify consistency.

---

### Consumer Loan Calculator

**Risk:** Displayed monthly payment doesn't match the actual annuity formula.

**Business Value:** Incorrect loan payment display is a regulatory and reputational risk.

**Why UI Automation:** The calculator reads user input, fetches the nominal rate, and displays a payment — all through the UI. `ConsumerLoanHelper` extracts all values from the page and independently calculates the annuity payment using the standard formula, asserting the displayed value is within the configured tolerance (`DELTA = 0.005`).

---

## 9. Selector Strategy

**Locator priority (most to least preferred):**
`ARIA role + text` → `business attributes` → `component element tag` → `CSS class` → `XPath`

| Element | Selector | Why Stable | Risk |
|---|---|---|---|
| ATM service button | `getByRole(BUTTON).filter(hasText("ბანკომატები"))` | Accessible role + visible label | If label text changes |
| City dropdown | `getByRole(BUTTON, name("აირჩიე ქალაქი"))` | ARIA name is semantically defined | If ARIA name is removed |
| Loan amount input | `//input[@min='200' and @max='80000']` | Business-defined constraints, not UI structure | If input range changes |
| ATM list items | `app-atm-branches-section-list-item` | Custom Angular component tag, unlikely to change | Component rename |
| Map markers | `google-map gmp-advanced-marker[tabindex='-1']` | Google Maps component API attribute | Google Maps SDK update |
| Cookie accept | `getByRole(BUTTON).filter(hasText("თანხმობა"))` | Visible text on a button | Text or component change |

---

## 10. Flaky Test Awareness

### ATM Location Test — Map Marker

**Locator:** `gmp-advanced-marker[tabindex='-1']`

**Fragility:**
- The marker sits inside a Google Maps web component with a shadow root. It is currently accessible, but this depends on how the Angular app renders the component.
- Slow map rendering on mobile can cause the marker not to appear within the default timeout.
- The `position` attribute (used for geocoding) is a custom attribute set by the Angular wrapper, not a Google Maps API guarantee.

**Mitigation:**
- Explicit `assertThat(activeMapMarker.first()).isVisible()` before reading `position`.
- `waitForMapToUpdate()` called before any map assertion.

---

### LocationsCityFilterTest — Mobile Viewport

**Fragility:** City filter + geocode validation in a single step on mobile is more fragile due to slower rendering and smaller viewport causing map markers to fall outside the visible area.

**Mitigation:**
- `scrollIntoViewIfNeeded()` before clicking ATM items.
- Increased timeouts for map-related assertions on mobile.

---

### LocationParametrizedTests — NullPointerException Risk

**Fragility:** `LocationHelper locationHelper = new LocationHelper(page)` is declared as a field initializer. At field initialization time (before `@BeforeClass`), `page` is `null` — this will throw a `NullPointerException` at runtime.

**Fix:** Move instantiation into `navigateToAtmSection()` or use `locationSteps.getATMListCount()` instead.

---

## 11. Mobile vs Desktop

Each UI test runs in both contexts via `DeviceFactory`, which creates two test instances per suite entry — one for desktop (1920×1080) and one for mobile (390×844 with iPhone 15 user agent).

| Concern | Desktop | Mobile |
|---|---|---|
| Navigation | Horizontal nav bar, `forMeButton` click | Burger menu, `burgerMenu` click |
| Dropdowns | Inline visible | Accordion-style, requires extra click |
| Loan inputs | `//input[@min='200']` visible | Falls back to `getByLabel` + ancestor XPath |
| Map markers | Larger viewport, faster render | Slower render, markers may be off-screen |
| ATM list | Always visible | May require scroll |
| Geolocation | Set to Tbilisi coordinates (41.707225, 44.849191) for both | Same |

`TestContext.getDevice()` is checked in `HomeSteps` at every navigation branch, keeping device-specific logic centralized and out of page objects and test classes.