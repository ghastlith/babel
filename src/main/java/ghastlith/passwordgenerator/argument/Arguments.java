package ghastlith.passwordgenerator.argument;

import java.util.List;

import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

@Getter
public class Arguments {

  @Option(names = "--length", defaultValue = "20")
  private int length;

  @Option(names = "--alphanumeric", defaultValue = "false")
  private boolean isAlphanumeric;

  @Unmatched
  private List<String> unmatched;

}
