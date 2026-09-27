package kz.iitu.springlab.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevBanner implements EnvironmentBanner {

    @Override
    public String getMessage() {
        return "Development profile is active";
    }
}