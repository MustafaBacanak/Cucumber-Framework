@google_search
Feature: ilk feature file
  Background: Google_sayfasina_git
    Given kullanici google gider

  @iphone
  Scenario: TC01_google_iphone_arama
    When kullanici "Fenerbahçe" icin arama yapar
    Then sonuclarda "Fenerbahçe" oldugunu dogrular
    Then close the application
