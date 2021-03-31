package com.sahajamit.bdd;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Class to run the Cucumber Tests.
 */


@RunWith(CucumberReportRunner.class)
@CucumberOptions(
        features = {"classpath:features/"},
        glue = "classpath:com.sahajamit",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        tags = "@api"
)
public class RunCucumberTest {
}
