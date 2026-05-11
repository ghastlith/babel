package ghastlith.babel.argument;

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

  @Option(names = "--length")
  private int length = 20;

  @Option(names = "--alphanumeric")
  private boolean isAlphanumeric = false;

  @Unmatched
  private List<String> unmatched = new ArrayList<>();

}
