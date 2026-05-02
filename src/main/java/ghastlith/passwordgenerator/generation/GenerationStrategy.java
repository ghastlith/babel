package ghastlith.passwordgenerator.generation;

import ghastlith.passwordgenerator.argument.InputArguments;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record GenerationStrategy(
    @Min(value = 16, message = "Length must be at least 16")
    @Max(value = 32, message = "Length must be at most 32")
    int length,
    boolean isAlphanumeric
) {

  public static GenerationStrategy fromInputArguments(final InputArguments arguments) {
    return GenerationStrategy.builder()
        .length(arguments.getLength())
        .isAlphanumeric(arguments.isAlphanumeric())
        .build();
  }

}
