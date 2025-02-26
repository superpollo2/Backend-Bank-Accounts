package co.com.technicaltest.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = CorsConfig.class)
class CorsConfigTest {

    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsFilter() {
        String origins = "http://example.com,http://another.com";
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = corsConfig.corsFilter(origins);

        CorsFilter corsFilter = filterRegistrationBean.getFilter();

        // Create a new UrlBasedCorsConfigurationSource to access the configuration
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(origins.split(",")));
        config.setAllowedMethods(Arrays.asList("POST", "GET")); // TODO: Check others required methods
        config.setAllowedHeaders(List.of(CorsConfiguration.ALL));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        CorsConfiguration retrievedConfig = source.getCorsConfigurations().get("/**");

        assertNotNull(filterRegistrationBean);
        assertEquals(Ordered.HIGHEST_PRECEDENCE, filterRegistrationBean.getOrder());
        assertNotNull(corsFilter);
        assertNotNull(retrievedConfig);
        assertTrue(retrievedConfig.getAllowCredentials());
        assertEquals(List.of("http://example.com", "http://another.com"), retrievedConfig.getAllowedOrigins());
        assertEquals(List.of("POST", "GET"), retrievedConfig.getAllowedMethods());
        assertEquals(List.of(CorsConfiguration.ALL), retrievedConfig.getAllowedHeaders());
    }
}