package ghastlith.passwordgenerator.generate;

import java.time.Instant;

import ghastlith.passwordgenerator.argument.InputArguments;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record GenerationStrategy(
    @Size(max = 32, message = "Seed must be at most 32 characters")
    byte[] seed,
    @Min(value = 8, message = "Length must be at least 8")
    @Max(value = 32, message = "Length must be at most 32")
    int length,
    boolean isAlphanumeric
) {

  public static GenerationStrategy fromInputArguments(final InputArguments arguments) {
    final var seed = arguments.getSeed()
        .orElseGet(() -> Instant.now().toString())
        .getBytes();

    return GenerationStrategy.builder()
        .seed(seed)
        .length(arguments.getLength())
        .isAlphanumeric(arguments.isAlphanumeric())
        .build();
  }

}
