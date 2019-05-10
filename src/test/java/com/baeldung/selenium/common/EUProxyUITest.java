package com.baeldung.selenium.common;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.common.GlobalConstants;

public class EUProxyUITest extends BaseUISeleniumTest {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.server.port}")
    private String proxyServerPort;

    @Value("${proxy.username}")
    private String proxyUsername;

    @Value("${proxy.password}")
    private String proxyPasswod;

    int retryCount = 0;
    int maxRetries = 5;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        retryCount = 0;
    }

    @Test
    @Tag("vat-pricing-test")
    @Tag(GlobalConstants.TAG_DAILY_EU_PROXY)
    public final void givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown() {

        try {
            page.openNewWindowWithProxy(proxyHost, proxyServerPort, proxyUsername, proxyPasswod);            

            page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

            page.loadUrl();                        

            assertTrue(page.vatPricesAvailableThePage(), "VAT prices not displayed in EU region. Proxy Server:" + proxyHost + ":" + proxyServerPort);

        } catch (Exception e) {
            // e.printStackTrace();
            logger.info("Exception----> " + e.getMessage());
            if (maxRetries == retryCount + 1) {
                logger.debug(maxRetries + " retries completed with TimeoutException");
                fail(e.getMessage());
            } else {
                page.closeWindow();
                retryCount++;
                givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown();
            }
        }
    }

}
