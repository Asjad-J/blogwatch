package com.baeldung.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YAMLProperties {

    private static Yaml yaml = new Yaml();
    public static Map<String, List<String>> exceptionsForEmptyReadmeTest = fetchYMLPropertiesMap("ignore-list.yaml");
    public static Map<String, List<String>> exceptionsForTestHittingAllUrls = fetchYMLPropertiesMap("exceptions-for-tests-hitting-all-urls.yaml");
    public static Map<String, List<String>> multiSiteTargetUrls = fetchYMLPropertiesMap("multi-site-tests-target-urls.yaml");
    public static Map<String, String> redirectsTestData = fetchYMLProperties("redirects.yaml");

    public static Map<String, List<String>> fetchYMLPropertiesMap(String fileName) {
        Map<String, List<String>> output = new HashMap<>();
        InputStream fileStream = YAMLProperties.class.getClassLoader().getResourceAsStream(fileName);
        output = yaml.load(fileStream);
        return output;
    }

    public static Map<String, String> fetchYMLProperties(String fileName) {
        Map<String, String> output = new HashMap<>();
        InputStream fileStream = YAMLProperties.class.getClassLoader().getResourceAsStream(fileName);
        output = yaml.load(fileStream);
        return output;
    }

}
