package ghastlith.passwordgenerator.argument;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

@Getter
public class InputArguments {

  @Option(names = "--seed")
  private Optional<String> seed;

  @Option(names = "--length", defaultValue = "16")
  private int length;

  @Option(names = "--alphanumeric", defaultValue = "false")
  private boolean isAlphanumeric;

  @Unmatched
  private List<String> unmatched;

}
