import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature",
        // glue = { "classpath:com.qa.automation" },
        plugin = {"pretty", "html:target/cucumber-html-report"}
       // , tags={"@Postitive"}
)
public class TestRunner {

}
