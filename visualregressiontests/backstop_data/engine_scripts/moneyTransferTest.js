const TRANSFER_AMOUNT = "500";
const TRANSFER_COUNTRY = "გერმანია";
const MONEY_TRANSFER_PAGE_URL = "https://tbcbank.ge/ka/other-products/money-transfers";

module.exports = async (page, scenario, vp) => {

  console.log("[Step 1] Homepage access");

  await page.waitForSelector("body", { state: "visible", timeout: 15000 });

  try {
    await page.waitForSelector(
      'button:has-text("Accept"), button:has-text("OK"), [data-testid="cookie-accept"]',
      { timeout: 3000 }
    );
    const cookieBtn = await page.$(
      'button:has-text("Accept"), button:has-text("OK"), [data-testid="cookie-accept"]'
    );
    if (cookieBtn) {
      await cookieBtn.click();
      await page.waitForTimeout(500);
      console.log("[Step 1] Cookie banner accepted");
    }
  } catch (e) {
    console.log("[Step 1] No cookie banner found");
  }


  console.log("[Step 2] Opening navigation menu");

  try {
    const navMenu = await page.$(
      'button[aria-label="menu"], [data-testid="hamburger"], nav button, button[class*="menu"], button[class*="burger"]'
    );
    if (navMenu) {
      await navMenu.click();
      await page.waitForTimeout(800);
      console.log("[Step 2] Navigation menu opened");
    }
  } catch (e) {
    console.log("[Step 2] Could not open navigation menu:", e.message);
  }

  console.log("[Step 3] Navigating to Money Transfer page");

  await page.goto(MONEY_TRANSFER_PAGE_URL, { waitUntil: "domcontentloaded" });

  await page.waitForSelector(
    'h1, h2, [role="heading"]',
    { state: "visible", timeout: 15000 }
  );
  console.log("[Step 3] Money Transfer page loaded");

  console.log("[Step 4] Waiting for transfer option cards");

  await page.waitForSelector(
    "tbcx-pw-money-transfer-system-card tbcx-pw-card",
    { state: "visible", timeout: 20000 }
  );

  const transferOptionsValid = await page.evaluate(() => {
    const cards = Array.from(
      document.querySelectorAll("tbcx-pw-money-transfer-system-card tbcx-pw-card")
    );
    const supported = ["EUR", "USD", "GEL", "GBP", "RUB"];
    return cards.every((card) =>
      supported.some((currency) => card.textContent.includes(currency))
    );
  });

  if (!transferOptionsValid) {
    console.warn("[Step 4] WARNING: Some transfer options may be missing supported currencies");
  } else {
    console.log("[Step 4] All transfer options have supported currencies");
  }

  console.log("[Step 5] Opening Commission Calculator");

  const calculatorClicked = await page.evaluate(() => {
    const buttons = Array.from(document.querySelectorAll("button"));
    const target = buttons.find(
      (b) =>
        b.textContent.includes("საკომისიო") ||
        b.textContent.includes("Commission") ||
        b.textContent.includes("Calculator")
    );
    if (target) { target.click(); return true; }
    return false;
  });

  if (calculatorClicked) {
    console.log("[Step 5] Calculator button clicked");
    await page.waitForTimeout(1500);
  } else {
    console.warn("[Step 5] WARNING: Calculator button not found");
  }

  console.log("[Step 6] Entering transfer amount and selecting country");

  try {
    await page.waitForSelector(
      "tbcx-pw-money-transfer-fee-calculator div.input-with-label input",
      { state: "visible", timeout: 10000 }
    );
    await page.fill(
      "tbcx-pw-money-transfer-fee-calculator div.input-with-label input",
      TRANSFER_AMOUNT
    );
    console.log(`[Step 6] Amount entered: ${TRANSFER_AMOUNT}`);
  } catch (e) {
    console.warn("[Step 6] Could not fill amount:", e.message);
  }

  try {
    await page.evaluate(() => {
      const buttons = Array.from(
        document.querySelectorAll("tbcx-pw-money-transfer-fee-calculator button")
      );
      const target = buttons.find(
        (b) =>
          b.textContent.includes("აირჩიე ქვეყანა") ||
          b.textContent.includes("Select Country") ||
          b.textContent.includes("Country")
      );
      if (target) target.click();
    });
    console.log("[Step 6] Country dropdown opened");
    await page.waitForTimeout(1000);
  } catch (e) {
    console.warn("[Step 6] Could not open country dropdown:", e.message);
  }

  try {
    await page.waitForSelector(
      "div.tbcx-dropdown-popover-item__title-container",
      { state: "visible", timeout: 45000 }
    );
    await page.evaluate((country) => {
      const options = Array.from(
        document.querySelectorAll("div.tbcx-dropdown-popover-item__title-container")
      );
      const target = options.find((el) => el.textContent.includes(country));
      if (target) target.click();
    }, TRANSFER_COUNTRY);
    console.log(`[Step 6] Country selected: ${TRANSFER_COUNTRY}`);
  } catch (e) {
    console.warn("[Step 6] Could not select country:", e.message);
  }

  try {
    await page.waitForSelector(
      "tbcx-pw-money-transfer-system-card-loading",
      { state: "hidden", timeout: 45000 }
    );
    console.log("[Step 6] Skeleton loader gone");
  } catch (e) {
    console.log("[Step 6] Skeleton loader not found or already hidden");
  }

  await page.waitForSelector(
    "div.tbcx-pw-money-transfer-fee-calculator__cards",
    { state: "visible", timeout: 45000 }
  );
  console.log("[Step 6] Calculator result cards visible");

  console.log("[Step 7] Validating calculator results");

  const validationResult = await page.evaluate(() => {
    const cards = Array.from(
      document.querySelectorAll("div.tbcx-pw-money-transfer-fee-calculator__cards > *")
    );
    if (cards.length === 0) return { valid: false, error: "No calculator cards found" };

    const missingGel = cards.filter((card) => !card.textContent.includes("GEL"));
    if (missingGel.length > 0) {
      return { valid: false, error: `${missingGel.length} card(s) missing GEL commission` };
    }

    return { valid: true, count: cards.length };
  });

  if (!validationResult.valid) {
    console.warn(`[Step 7] WARNING: ${validationResult.error}`);
  } else {
    console.log(`[Step 7] Validated ${validationResult.count} calculator options with GEL commissions`);
  }

  await page.waitForTimeout(1000);
};