package ghastlith.passwordgenerator.argument;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArgumentProcessor {

  private static final String UNMATCHED_LOG_FORMAT = "Unrecognized argument: {}";

  public Arguments parse(final String... args) {
    final var arguments = new Arguments();

    parser(arguments).parseArgs(args);
    arguments.getUnmatched()
        .forEach(arg -> log.warn(UNMATCHED_LOG_FORMAT, arg));

    return arguments;
  }

  private CommandLine parser(final Object arguments) {
    return new CommandLine(arguments)
        .setOptionsCaseInsensitive(true)
        .setAbbreviatedOptionsAllowed(true);
  }

}
