package parser;

import static java.util.Map.entry;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class ParsingTable extends Hashtable<String, Map<String, String[]>> {

  public ParsingTable() {
    super();
    Entry<String, String[]> lambda = entry("lambda", new String[] {});

    // project-declaration -> project-def "."
    Map<String, String[]> projectDeclaration = Map.ofEntries(
      entry("project", new String[] { "project-def", "." })
    );
    this.put(
        "project-declaration",
        Map.ofEntries(entry("project", new String[] { "project-def", "." }))
      );

    // project-def -> project-heading declarations compound-stmt
    Map<String, String[]> projectDef = Map.ofEntries(
      entry(
        "project",
        new String[] { "project-heading", "declarations", "compound-stmt" }
      )
    );
    this.put("project-def", projectDef);

    // project-heading -> project "name" ";"
    Map<String, String[]> projectHeading = Map.ofEntries(
      entry("project", new String[] { "project", "name", ";" })
    );
    this.put("project-heading", projectHeading);

    // declarations -> const-decl var-decl subroutine-decl*
    String[] declarationsDerivation = new String[] {
      "const-decl",
      "var-decl",
      "subroutine-decl*",
    };
    Map<String, String[]> declarations = Map.ofEntries(
      entry("const", declarationsDerivation),
      entry("var", declarationsDerivation),
      entry("subroutine", declarationsDerivation),
      lambda
    );
    this.put("declarations", declarations);

    // const-decl -> const ( const-item ";" )+ | λ
    Map<String, String[]> constDecl = Map.ofEntries(
      entry(
        "const",
        new String[] { "const", "const-item", ";", "(const-item;)*" }
      ),
      lambda
    );
    this.put("const-decl", constDecl);

    // ( const-item ";" )* -> const-item ";" ( const-item ";" )* | λ
    Map<String, String[]> constItemList = Map.ofEntries(
      entry("name", new String[] { "const-item", ";", "(const-item;)*" }),
      lambda
    );
    this.put("(const-item;)*", constItemList);

    // const-item -> "name" = "integer-value"
    Map<String, String[]> constItem = Map.ofEntries(
      entry("name", new String[] { "name", "=", "integer-value" })
    );
    this.put("const-item", constItem);

    // var-decl -> var (var-item ";" )+ | λ
    Map<String, String[]> varDecl = Map.ofEntries(
      entry("var", new String[] { "var", "var-item", ";", "(var-item;)*" }),
      lambda
    );
    this.put("var-decl", varDecl);

    // (var-item ";")* -> var-item ";" (var-item ";")*
    Map<String, String[]> varItemList = Map.ofEntries(
      entry("name", new String[] { "var-item", ";", "(var-item;)*" }),
      lambda
    );
    this.put("(var-item;)*", varItemList);

    // var-item -> name-list ":" int
    Map<String, String[]> varItem = Map.ofEntries(
      entry("name", new String[] { "name-list", ":", "int" })
    );
    this.put("var-item", varItem);

    // name-list -> "name" ( "," "name" )*
    Map<String, String[]> nameList = Map.ofEntries(
      entry("name", new String[] { "name", "(,name)*" })
    );
    this.put("name-list", nameList);

    // ("," "name")* -> "," "name" ( "," "name" )* | λ
    Map<String, String[]> nameStar = Map.ofEntries(
      entry(",", new String[] { ",", "name", "(,name)*" }),
      lambda
    );
    this.put("(,name)*", nameStar);

    // subroutine-decl* -> subroutine-decl subroutine-decl*
    Map<String, String[]> subroutineDeclStar = Map.ofEntries(
      entry(
        "subroutine",
        new String[] { "subroutine-decl", "subroutine-decl*" }
      ),
      lambda
    );
    this.put("subroutine-decl*", subroutineDeclStar);

    // subroutine-decl -> subroutine-heading declarations compound-stmt “;” | λ
    Map<String, String[]> subroutineDecl = Map.ofEntries(
      entry(
        "subroutine",
        new String[] {
          "subroutine-heading",
          "declarations",
          "compound-stmt",
          ";",
        }
      )
    );
    this.put("subroutine-decl", subroutineDecl);

    // subroutine-heading -> subroutine "name" ";"
    Map<String, String[]> subroutineHeading = Map.ofEntries(
      entry("subroutine", new String[] { "subroutine", "name", ";" })
    );
    this.put("subroutine-heading", subroutineHeading);

    // compound-stmt -> begin stmt-list end
    Map<String, String[]> compoundStmt = Map.ofEntries(
      entry("begin", new String[] { "begin", "stmt-list", "end" })
    );
    this.put("compound-stmt", compoundStmt);

    // stmt-list -> ( statement ";" )*
    String[] stmtListDerivation = new String[] {
      "statement",
      ";",
      "stmt-list",
    };
    this.put(
        "stmt-list",
        Map.ofEntries(
          entry("name", stmtListDerivation),
          entry("scan", stmtListDerivation),
          entry("print", stmtListDerivation),
          entry("if", stmtListDerivation),
          entry("while", stmtListDerivation),
          entry("begin", stmtListDerivation),
          lambda
        )
      );

    // statement -> ass-stmt | inout-stmt | if-stmt | while-stmt | compound-stmt | λ
    this.put(
        "statement",
        Map.ofEntries(
          entry("name", new String[] { "ass-stmt" }),
          entry("scan", new String[] { "inout-stmt" }),
          entry("print", new String[] { "inout-stmt" }),
          entry("if", new String[] { "if-stmt" }),
          entry("while", new String[] { "while-stmt" }),
          entry("begin", new String[] { "compound-stmt" }),
          lambda
        )
      );

    // ass-stmt -> "name" ":=" arith-exp
    this.put(
        "ass-stmt",
        Map.ofEntries(entry("name", new String[] { "name", ":=", "arith-exp" }))
      );

    // arith-exp -> term ( add-sign term )*
    String[] arithExpDerivation = new String[] { "term", "(add-sign term)*" };
    this.put(
        "arith-exp",
        Map.ofEntries(
          entry("(", arithExpDerivation),
          entry("name", arithExpDerivation),
          entry("integer-value", arithExpDerivation)
        )
      );

    // ( add-sign term )* -> add-sign term ( add-sign term )* | λ
    this.put(
        "(add-sign term)*",
        Map.ofEntries(
          entry("+", new String[] { "add-sign", "term", "(add-sign term)*" }),
          entry("-", new String[] { "add-sign", "term", "(add-sign term)*" }),
          lambda
        )
      );

    // term -> factor ( mul-sign factor )*
    String[] factorDerivation = new String[] { "factor", "(mul-sign factor)*" };
    this.put(
        "term",
        Map.ofEntries(
          entry("(", factorDerivation),
          entry("name", factorDerivation),
          entry("integer-value", factorDerivation)
        )
      );

    // ( mul-sign factor )* -> mul-sign factor ( mul-sign factor )* | λ
    String[] mulSigFactorStarDerivation = new String[] {
      "mul-sign",
      "factor",
      "(mul-sign factor)*",
    };
    this.put(
        "(mul-sign factor)*",
        Map.ofEntries(
          entry("*", mulSigFactorStarDerivation),
          entry("/", mulSigFactorStarDerivation),
          entry("%", mulSigFactorStarDerivation),
          lambda
        )
      );

    // factor -> "(" arith-exp ")" | name-value
    this.put(
        "factor",
        Map.ofEntries(
          entry("(", new String[] { "(", "arith-exp", ")" }),
          entry("name", new String[] { "name-value" }),
          entry("integer-value", new String[] { "name-value" })
        )
      );

    // name-value -> "name" | "integer-value"
    this.put(
        "name-value",
        Map.ofEntries(
          entry("name", new String[] { "name" }),
          entry("integer-value", new String[] { "integer-value" })
        )
      );

    // add-sign -> "+" | "-"
    this.put(
        "add-sign",
        Map.ofEntries(
          entry("+", new String[] { "+" }),
          entry("-", new String[] { "-" })
        )
      );

    // mul-sign -> "*" | "/" | "%"
    this.put(
        "mul-sign",
        Map.ofEntries(
          entry("/", new String[] { "/" }),
          entry("*", new String[] { "*" }),
          entry("%", new String[] { "%" })
        )
      );

    // inout-stmt -> scan "(" "name" ")" | print "(" name-value ")"
    this.put(
        "inout-stmt",
        Map.ofEntries(
          entry("scan", new String[] { "scan", "(", "name", ")" }),
          entry("print", new String[] { "print", "(", "name-value", ")" })
        )
      );

    // if-stmt -> if bool-exp then statement else-part endif
    this.put(
        "if-stmt",
        Map.ofEntries(
          entry(
            "if",
            new String[] {
              "if",
              "bool-exp",
              "then",
              "statement",
              "else-part",
              "endif",
            }
          )
        )
      );

    // else-part -> else statement | λ
    this.put(
        "else-part",
        Map.ofEntries(
          entry("else", new String[] { "else", "statement" }),
          lambda
        )
      );

    // while-stmt -> while bool-exp do statement
    this.put(
        "while-stmt",
        Map.ofEntries(
          entry(
            "while",
            new String[] { "while", "bool-exp", "do", "statement" }
          )
        )
      );

    // bool-exp -> name-value relational-oper name-value
    String[] boolExpDerivation = new String[] {
      "name-value",
      "relational-oper",
      "name-value",
    };
    this.put(
        "bool-exp",
        Map.ofEntries(
          entry("name", boolExpDerivation),
          entry("integer-value", boolExpDerivation)
        )
      );

    // relational-oper -> "=" | "|=" | "<" | "=<" | ">" | "=>"
    this.put(
        "relational-oper",
        Map.ofEntries(
          entry("=", new String[] { "=" }),
          entry("|=", new String[] { "|=" }),
          entry("<", new String[] { "<" }),
          entry("=<", new String[] { "=<" }),
          entry(">", new String[] { ">" }),
          entry("=>", new String[] { "=>" })
        )
      );
  }
}
