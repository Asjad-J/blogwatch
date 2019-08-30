package com.baeldung.selenium.common;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.common.GlobalProperties;
import com.baeldung.common.Utils;
import com.baeldung.common.config.CommonConfig;
import com.baeldung.common.config.MyApplicationContextInitializer;
import com.baeldung.crawler4j.config.Crawler4jMainCofig;
import com.baeldung.crawler4j.controller.TutorialsRepoCrawlerController;
import com.baeldung.selenium.config.SeleniumContextConfiguration;
import com.baeldung.site.SitePage;
import com.google.common.util.concurrent.RateLimiter;

@ContextConfiguration(classes = { CommonConfig.class, SeleniumContextConfiguration.class, Crawler4jMainCofig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class BaseUISeleniumTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SitePage page;    
    
    @Autowired
    protected TutorialsRepoCrawlerController tutorialsRepoCrawlerController;
    
    @Autowired
    protected RateLimiter rateLimiter;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }
    
    protected boolean shouldSkipUrl(String testName) {
        if (Utils.excludePage(page.getUrl(), GlobalProperties.properties.get(testName), true)) {
            logger.info("URL skipped for test:" + testName + "Skipped URL:" + page.getUrl());
            return true;
        }
        return false;
    }

}
