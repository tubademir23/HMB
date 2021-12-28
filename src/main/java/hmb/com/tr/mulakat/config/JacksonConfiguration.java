package hmb.com.tr.mulakat.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson mapper configuration
 */
@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer mapperCustomizer() {
        return builder -> {
            builder.failOnUnknownProperties(false);
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        };
    }
}