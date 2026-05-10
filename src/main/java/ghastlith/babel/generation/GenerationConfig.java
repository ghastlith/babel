package ghastlith.babel.generation;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerationConfig {

  @Bean
  SecureRandom random() {
    final var seed = Instant.now().toString().getBytes();
    return new SecureRandom(seed);
  }

}
