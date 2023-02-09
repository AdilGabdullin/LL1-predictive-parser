# LL1 predictive parser

## Grammar:
    project-declaration -> project-def "."
    project-def -> project-heading declarations compound-stmt
    project-heading -> project "name" ";"
    declarations -> const-decl var-decl subroutine-decl*
    const-decl -> const ( const-item ";" )+ | lambda
    const-item -> "name" = "integer-value"
    var-decl -> var ( var-item ";" )+ | lambda
    var-item -> name-list ":" int
    name-list -> "name" ( "," "name" )* 
    subroutine-decl -> subroutine-heading declarations compound-stmt ";" | lambda
    subroutine-heading -> subroutine "name" ";"
    compund-stmt -> begin stmt-list end
    stmt-list -> ( statement ";" )*
    statement -> ass-stmt | inout-stmt | if-stmt | while-stmt | compound-stmt | lambda
    ass-stmt -> "name" ":=" arith-exp
    arith-exp -> term ( add-sign term )*
    term -> factor ( mul-sign factor )*
    factor -> "(" arith-exp ")" | name-value
    name-value -> "name" | "integer-value"
    add-sign -> "+" | "-"
    mul-sign -> "*" | "/" | "%"
    inout-stmt -> scan "(" "name" ")" | print "(" name-value ")"
    if-stmt -> if bool-exp then statement else-part endif
    else-part -> else statement | lambda
    while-stmt -> while bool-exp do statement
    bool-exp -> name-value relational-oper name-value 
    relational-oper -> "=" | "|=" | "<" | "=<" | ">" | "=>"

## Usage:
    java Main tests/valid01.txt

## Commands:
### compile
    javac Main.java
### run
    java Main <FILENAME>
### run in verbose mode
    java Main <FILENAME> verbose
### tests
    bash run-tests.sh

## Classes:
### Parser
Parses code and prints "Successfully parsed" or dies with detailed error. In verbose mode also outputs stack, token and symbol table on each iteration
### Tokenizer
Transforms code to a queue of tokens
### ParsingTable
derrivation table
### Token
Primitive data transfer class
### TokenType
Enum KEYWORD, OPERATOR, NAME, INTEGER_VALUE, DELIMETER, UNKNOWN
