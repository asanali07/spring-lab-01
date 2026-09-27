package kz.iitu.springlab.web;

import kz.iitu.springlab.config.AppProperties;
import kz.iitu.springlab.config.EnvironmentBanner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties appProperties;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(
            AppProperties appProperties,
            EnvironmentBanner banner,
            Environment environment) {

        this.appProperties = appProperties;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("owner", appProperties.owner());
        result.put("group", appProperties.group());

        Map<String, Object> mail = new LinkedHashMap<>();
        mail.put("enabled", appProperties.mail().enabled());
        mail.put("retryCount", appProperties.mail().retryCount());
        mail.put("from", appProperties.mail().from());

        result.put("mail", mail);

        Map<String, Object> security = new LinkedHashMap<>();
        security.put(
                "tokenTtl",
                appProperties.security().tokenTtl().toString()
        );
        security.put(
                "minPasswordLength",
                appProperties.security().minPasswordLength()
        );

        result.put("security", security);

        result.put(
                "port",
                environment.getProperty(
                        "local.server.port",
                        environment.getProperty("server.port")
                )
        );

        result.put(
                "activeProfiles",
                Arrays.asList(environment.getActiveProfiles())
        );

        result.put("banner", banner.getMessage());

        return result;
    }
}