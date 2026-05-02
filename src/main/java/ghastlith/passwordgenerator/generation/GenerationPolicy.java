package ghastlith.passwordgenerator.generation;

import ghastlith.passwordgenerator.argument.Arguments;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record GenerationPolicy(
    @Min(value = 16, message = "Length must be at least 16")
    @Max(value = 32, message = "Length must be at most 32")
    int length,
    boolean hasSymbols
) {

  public static GenerationPolicy fromArguments(final Arguments arguments) {
    return GenerationPolicy.builder()
        .length(arguments.getLength())
        .hasSymbols(!arguments.isAlphanumeric())
        .build();
  }

}
