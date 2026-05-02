package ghastlith.passwordgenerator.generation;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@Validated
@RequiredArgsConstructor
public class GenerationEngine {

  private final SecureRandom random;

  public String generatePassword(@Valid final GenerationPolicy policy) {
    throw new RuntimeException("method not implemented");
  }

}
