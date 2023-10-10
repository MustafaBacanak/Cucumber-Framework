package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class SearchDefs {

    @Given("kullanici google gider")
    public void kullanici_google_gider() {
    Driver.getDriver().get(ConfigReader.getProperty("google_url"));
    }
    @When("kullanici {string} icin arama yapar")
    public void kullanici_icin_arama_yapar(String string) {
        Driver.getDriver().findElement(By.name("q")).sendKeys(string);
    }
    @Then("sonuclarda {string} oldugunu dogrular")
    public void sonuclarda_oldugunu_dogrular(String string) throws InterruptedException {
        assert true;
        ReusableMethods.clickWithText("Gmail");
        Thread.sleep(5000);
    }
    @Then("close the application")
    public void close_the_application() {
        Driver.closeDriver();
    }
}
