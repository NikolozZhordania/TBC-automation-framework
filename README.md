# **TBC Bank App Automation — Strategy & Reasoning**

---

## **3.1 Why Automate This?**

**Consumer Loan Calculator**  
_Risk_: Manual calculation errors; multiple input combinations.  
_Business Value_: Ensures correct monthly payment displayed; critical for customer trust.  
_Why UI Automation_: Calculator logic depends on UI inputs and displayed results; validating through UI catches
formatting and display issues, not just backend logic.  
**Rationale**: Critical financial calculation; prevents costly errors.

**Currency Exchange Conversion**  
_Risk_: Wrong rates displayed, miscalculation.  
_Business Value_: Customers rely on accurate conversion; errors can lead to complaints or financial loss.  
_Why UI Automation_: Validates both the rate display and swap functionality visually; ensures cross-browser consistency.  
**Rationale**: Ensures correct exchange rates; client-side logic must be validated.

**ATM Location & Filtering**  
_Risk_: Wrong or missing ATMs shown; incorrect city filtering.  
_Business Value_: UX-critical; users rely on accurate location info.  
_Why UI Automation_: Map and list elements are dynamic; UI automation verifies both filtering and map highlighting.  
**Rationale**: Prevents customer confusion; UI filtering cannot be verified via backend only.

---

## **3.2 Selector Strategy**

**Navigation item – “ჩემთვის”**  
_Selector_: `//div[contains(@class,'navigation-item') and normalize-space(text())='ჩემთვის']`  
_Why stable_: Uses exact visible text, which rarely changes for core navigation.  
_What could break_: If the UI label text changes or is translated differently.  
_Priority_: High

**Loan amount input**  
_Selector_: `//input[@min='200' and @max='80000']`  
_Why stable_: Relies on business-defined min/max attributes, not UI structure.  
_What could break_: DOM restructuring, changing input field types, or removing these attributes.  
_Priority_: High

**Money transfer card**  
_Selector_: `//tbcx-pw-money-transfer-system-card//tbcx-pw-card`  
_Why stable_: Component-based; each transfer option is wrapped in a dedicated component.  
_What could break_: Adding/removing cards or redesigning card layout.  
_Priority_: Medium

**Google Maps marker**  
_Selector_: `.//gmp-advanced-marker`  
_Why stable_: Targets the map marker directly inside the Google Maps component.  
_What could break_: Lazy loading, API changes, shadow DOM complexity, or marker not rendered yet.  
_Priority_: Medium

**Currency exchange swap button**  
_Selector_: `//div[contains(@class,'exchange-rates-calculator__swap')]//button`  
_Why stable_: Unique functional button with a clear CSS class for swapping currencies.  
_What could break_: CSS class name changes or redesign of the calculator.  
_Priority_: High

**Locator priority**: visible text -> business attributes -> component elements -> CSS class.

---

## **3.3 Flaky Test Awareness**

**Consumer Loan Calculation Test – Mobile Inputs**
- Mobile XPaths used:
    - Month count input: `//span[normalize-space(text())='თვე']/preceding::input[1]`
    - Locations button: `(//a[@href='/ka/atms&branches']//span[text()=' მისამართები'])[3]`
    - Currency Exchange button: `(//tbcx-pw-mega-menu-quick-acitons-item//span[text()=' ვალუტის კურსები'])[3]`

- **Fragility**:
    - All rely on text labels or positional indices (`[3]`) which can break if the DOM changes or additional elements are added.
    - Using `preceding::input[1]` or indexed spans is sensitive to minor structural changes in the page.
    - These locators are less ideal than stable IDs or `data-*` attributes.

- **Why necessary**:
    - On mobile, many elements are hidden, inside collapsible menus, or rendered differently.
    - Standard stable locators do not work reliably on small viewports, so relative XPaths with labels/indices were the only way to interact with them.

**Locations Test – Map Marker**
- Locator: `.//gmp-advanced-marker`
- **Fragility**:
    - Marker is **inside a closed shadow root** but somehow accessible, which is an anomaly.
    - Mobile viewport + slower map rendering causes *CITY FILTER* test to fail; desktop version usually passes.

---

## **3.4 Mobile & Desktop**

**Locations / Consumer Loan Scenario**

**Mobile**:
- Hamburger menu, accordion expansion, slower map rendering.
- Google Maps markers may be **inside shadow DOM** and sometimes outside the viewport.
- Explicit visibility checks and `.scrollIntoView()` are used to ensure reliable interaction.

**Desktop**:
- Horizontal menu, fully visible dropdowns, faster map loading.
- Shadow DOM markers still exist, but larger viewport reduces flakiness.

**UX Risk**: Hidden elements, slow rendering, or inaccessible markers may confuse users or cause test failures.

**Automation Mitigation**:
- Device-specific locators.
- Explicit waits for visibility.
- Shadow root handling where necessary.
- Scroll markers into view on mobile before interaction.



 