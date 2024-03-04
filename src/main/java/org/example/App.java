package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class App {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(
          new BrowserType.LaunchOptions().setHeadless(false));
      BrowserContext context = browser.newContext();
      Page page = context.newPage();

      page.navigate("https://parabank.parasoft.com/parabank/");

      // Verify that the title of the page is correct before the user logs in.
      assertThat(page).hasTitle("ParaBank | Welcome | Online Banking");

      // Perform login
      page.locator("input[name=\"username\"]").click();
      page.locator("input[name=\"username\"]").fill("manav7396");
      page.locator("input[name=\"password\"]").click();
      page.locator("input[name=\"password\"]").fill("j6pUm@TTwhF5j");
      page.getByRole(
          AriaRole.BUTTON,
          new Page.GetByRoleOptions().setName("Log In")).click();

      // Test title after login
      assertThat(page).hasTitle("ParaBank | Accounts Overview");
      // Ensure Accounts Overview
      assertThat(page.getByRole(
          AriaRole.HEADING,
          new Page.GetByRoleOptions().setName("Accounts Overview"))).hasText("Accounts Overview");

      // Move to the transfer funds page
      page.getByRole(
          AriaRole.LINK,
          new Page.GetByRoleOptions().setName("Transfer Funds")).click();

      // Ensure that the title of the page
      assertThat(page).hasTitle("ParaBank | Transfer Funds");
      // Ensure Transfer Funds
      assertThat(page.getByRole(
          AriaRole.HEADING,
          new Page.GetByRoleOptions().setName("Transfer Funds"))).hasText("Transfer Funds");

      page.locator("#amount").click();
      page.locator("#amount").fill("10");
      page.getByRole(
          AriaRole.BUTTON,
          new Page.GetByRoleOptions().setName("Transfer")).click();

      // Ensure that the heading 'Transfer Complete!
      assertThat(page.getByRole(
          AriaRole.HEADING,
          new Page.GetByRoleOptions().setName("Transfer Complete!"))).hasText("Transfer Complete!");

      page.getByRole(
          AriaRole.LINK,
          new Page.GetByRoleOptions().setName("Log Out")).click();

      // Test for logout brings back to main screen
      assertThat(page).hasTitle("ParaBank | Welcome | Online Banking");
    } catch (PlaywrightException e) {
      e.printStackTrace();
    }
  }
}
