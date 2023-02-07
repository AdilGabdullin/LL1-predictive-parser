package parser;

import java.util.Set;
import parser.TokenType;

public class Token {

  public TokenType type;
  public int line;
  public String value;
  private static final Set<String> KEYWORDS = Set.of(
    "project",
    "begin",
    "end",
    "var",
    "const",
    "subroutine",
    "int",
    "scan",
    "print",
    "if",
    "then",
    "else",
    "endif",
    "while",
    "do"
  );
  private static final Set<String> OPERATORS = Set.of(
    ":=",
    "+",
    "-",
    "*",
    "/",
    "%",
    "=",
    "|=",
    "<",
    "=<",
    ">",
    "=>"
  );
  private static final Set<String> DELIMETERS = Set.of(",", ".", ";");

  public Token(String value, int line) {
    this.type = getType(value);
    this.line = line;
    this.value = value;
  }

  public String toString() {
    return "keyword" + '"' + this.value + "\" " + this.type.name();
  }

  private static TokenType getType(String value) {
    if (KEYWORDS.contains(value)) return TokenType.KEYWORD;
    if (OPERATORS.contains(value)) return TokenType.OPERATOR;
    if (DELIMETERS.contains(value)) return TokenType.DELIMETER;
    if (Character.isDigit(value.charAt(0))) return TokenType.INTEGER_VALUE;
    if (value.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) return TokenType.NAME;
    return TokenType.UNKNOWN;
  }
}
