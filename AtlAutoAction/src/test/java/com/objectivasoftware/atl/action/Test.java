package com.objectivasoftware.atl.action;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features",
        format = { "pretty","html:cucumber-html-reports",
                "json:cucumber-html-reports/cucumber.json",
                "junit:cucumber-html-reports/cucumber.xml"},
        tags = {"@menuUI"},
        monochrome = true,
        dryRun = false,
        glue = "com.objectivasoftware.atl.action"
        )

public class Test {

}
