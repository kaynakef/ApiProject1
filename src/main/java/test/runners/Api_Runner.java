package test.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class )
@CucumberOptions(
        features ="src/main/resources/features",
        glue={"test/stepdefinitions"},
        dryRun = false,
        tags = "",
        plugin = {"pretty", "html:target/uiReport.html", "rerun:target/uiFailedTests.txt",
                "json:target/cucumber-reports/cucumber.json"}

)

public class Api_Runner {

}
