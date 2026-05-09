package ghastlith.cerberus.argument;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

/**
 * Data object parsed from user inputted application arguments.
 */
@Getter
public class Arguments {

  @Option(names = "--length", defaultValue = "20")
  private int length;

  @Option(names = "--alphanumeric", defaultValue = "false")
  private boolean isAlphanumeric;

  @Unmatched
  private List<String> unmatched = new ArrayList<>();

}
