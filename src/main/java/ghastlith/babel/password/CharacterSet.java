package ghastlith.babel.password;

import static java.util.stream.Collectors.joining;

import java.util.EnumSet;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sets containing the lists of characters allowed during password generation.
 */
@Getter
@RequiredArgsConstructor
public enum CharacterSet {

  UPPER_CASE_LETTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
  LOWER_CASE_LETTERS("abcdefghijklmnopqrstuvwxyz"),
  NUMBERS("0123456789"),
  SPECIAL("!#$%&()*+,-./:;<=>?@[]^_{}~");

  private final String characters;

  private static final Predicate<CharacterSet> NO_OP_FILTER = x -> true;

  public static String allCharacters(final boolean hasSymbols) {
    return EnumSet.allOf(CharacterSet.class)
        .stream()
        .filter(hasSymbols ? NO_OP_FILTER : set -> set != SPECIAL)
        .map(CharacterSet::getCharacters)
        .collect(joining());
  }

}
