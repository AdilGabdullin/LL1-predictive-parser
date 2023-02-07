package parser;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import parser.ParsingTable;
import parser.Token;
import parser.Tokenizer;

public class Parser {

  public boolean verbose = false;
  private Queue<Token> tokens;
  private ParsingTable table;
  private Stack<String> stack;
  private Map<String, String> symbolTable;
  private String context; // varible or contstant

  public Parser() {
    table = new ParsingTable();
  }

  public void parse(String code) {
    symbolTable = new Hashtable<String, String>();
    if (verbose) System.out.println(code);
    tokens = Tokenizer.getTokens(code);
    initStack(code);
    while (!stack.peek().equals("$") && tokens.size() > 0) {
      printState();
      if (stack.peek().equals("integer-value")) {
        parseIntegerValue();
      } else if (stack.peek().equals("name")) {
        parseName();
      } else if (currentTableRow() == null) {
        parsePrimitive();
      } else if (currentDerivation() == null) {
        parseLambda();
      } else {
        parseDerivation();
      }
    }
    printState();
    parseEnd();
  }

  private void initStack(String code) {
    stack = new Stack<String>();
    stack.push("$");
    stack.push("project-declaration");
  }

  private void printState() {
    if (!verbose) return;
    System.out.print(context);
    System.out.print("stack: ");
    System.out.print(stack);
    String value = tokens.size() > 0 ? tokens.peek().value : null;
    System.out.println(" token: \"" + value + "\"");
  }

  private Map<String, String[]> currentTableRow() {
    return table.get(stack.peek());
  }

  private String[] currentDerivation() {
    if (currentTableRow() == null) return null;
    if (tokens.peek().type == TokenType.NAME) {
      return currentTableRow().get("name");
    }
    if (tokens.peek().type == TokenType.INTEGER_VALUE) {
      return currentTableRow().get("integer-value");
    }
    return currentTableRow().get(tokens.peek().value);
  }

  private void parseEnd() {
    if (tokens.size() > 0 || stack.size() > 1) error();
    System.out.println("Successfully parsed");
  }

  private void parseIntegerValue() {
    if (tokens.peek().type != TokenType.INTEGER_VALUE) error();
    tokens.remove();
    stack.pop();
  }

  private void parseName() {
    if (tokens.peek().type != TokenType.NAME) error();
    checkSymbolTable();
    tokens.remove();
    stack.pop();
  }

  private void parsePrimitive() {
    String top = stack.peek();
    String value = tokens.peek().value;
    if (!top.equals(value)) error();
    updateContext();
    tokens.remove();
    stack.pop();
  }

  private void updateContext() {
    String top = stack.peek();
    if (
      top.equals("var") ||
      top.equals("const-item") ||
      top.equals("project") ||
      top.equals("subroutine") ||
      top.equals("ass-stmt")
    ) {
      context = top;
    }
    if (
      top.equals(";") &&
      context != null &&
      (
        context.equals("var") ||
        context.equals("const-item") ||
        context.equals("project") ||
        context.equals("subroutine")
      )
    ) {
      context = null;
    }
  }

  private void parseLambda() {
    if (currentTableRow().get("lambda") == null) error();
    stack.pop();
  }

  private void parseDerivation() {
    updateContext();
    String[] derivation = currentDerivation();
    String top = stack.pop();
    for (int i = derivation.length - 1; i >= 0; i--) {
      stack.push(derivation[i]);
    }
  }

  private void checkSymbolTable() {
    String name = tokens.peek().value;
    String varType = symbolTable.get(name);
    if (context == null) {
      if (
        varType != null &&
        (varType.equals("var") || varType.equals("const-item"))
      ) {} else {
        error("undefined name: \"" + name + "\"");
      }
    } else if (context.equals("var") || context.equals("const-item")) {
      symbolTable.put(name, context);
      if (verbose) System.out.println("symbolTable:" + symbolTable);
      return;
    } else if (context.equals("ass-stmt")) {
      if (varType != null && varType.equals("var")) {
        context = null;
      } else {
        error("assignment to undefined variable: \"" + name + "\"");
      }
    }
  }

  private void error() {
    String message = "ERROR!\nexpected \"" + stack.peek() + "\"";
    if (tokens.size() == 0) {
      System.out.println(message + " found end of file");
      System.exit(1);
    }
    Token token = tokens.peek();
    System.out.println(
      message + " found \"" + token.value + "\" at line " + token.line
    );
    System.exit(1);
  }

  private void error(String message) {
    System.out.println("ERROR!\n" + message);
    System.exit(1);
  }
}
