//
// FILE    : README.txt
// AUTHOR  : Keisha Harthoorn, Robert Main, Chen Huo
// DATE    : 11/4/10
// TASK    : A readme file for our compiler, at the typechecker stage (Compiler 4).
// VERSION : 4.0
//

Classes :
 
Scanner.java
Token.java
Parser.java
RunParser.java
AbstractSyntaxTree.java
MakeAndExpr.java
MakeBody.java
MakeBool.java
MakeBoolDecl.java
MakeDef.java
MakeDividesExpr.java
MakeEqualsExpr.java
MakeFormal.java
MakeFormals.java
MakeIdentifier.java
MakeIf.java
MakeInt.java
MakeIntDecl.java
MakeLessThanExpr.java
MakeLiteral.java
MakeMinusExpr.java
MakeNotExpr.java
MakeOrExpr.java
MakePlusExpr.java
MakePrintStatement.java
MakeProgram.java
MakeTimesExpr.java
SemanticAction.java
SemanticLookup.java
RunParser.java
RunScanner.java
/** NEW
TypeChecker.java
TypeCheckerTest.java
SymbolTable.java
SymbolTableFactory.java
DataType.java
MakeActual.java
MakeActuals.java
MakeFunctionCall.java
PrettyPrinter.java
*/


Klein Programs :

factorial.txt
fibonacci.txt
hypotenuse.txt 
leapyear.txt   
messmeup.txt
power.txt
simple_program.txt
simple_program_2.txt


Other Files :

firstandfollow.docx
grammar_list.docx
parsetable.xlsx


Our Design : 

Scanner - We decided to implement the FSMs in our scanner as a switch.  It then calls the appropriate procedures depending on 
the value of what was read in from the string in order to produce the proper token/error.  

Parser(part one) - For our parser, we created the first sets, follow sets, and parse table by hand.  We then created 3 arraylists 
and a 2-dimensional array and populated them in their appropriate methods.  The arraylists contain the terminals, nonterminals, 
and rules of the grammar.  The 2-dimensional array contains indices of [terminals][nonterminals], and serves as our parse table.  
Here we populated the table with the rules of our grammar by using the numberings in grammar_list.docx.  If a lookup is performed 
for an index that does not exist, 0 is returned.  The driving method of the Parser class is the parse() method.  This method uses 
the table driven parse algorithm in order to parse a Klein program.  Currently, our parser only determines whether or not a given 
program is valid and prints true or false and all function calls.

Parser(part two) - For the second part of our parser, we created an AbstracSyntaxTree class to define the different abstract syntax 
objects.  We also created a Make[SemanticAction] class for every Semantic Action in our grammar. We added make-[SemanticAction] to 
our grammar for every semantic action.  Our SemanticLookup class finds which make-[SemanticAction] is on the stack and determines 
what action to take after that point.  We also added another arm to the parsing algorithm in our Parser class for abstract syntax.

Type Checker - For this section of our compiler, we created a TypeChecker class that runs through the program and creates a symbol
table for the given file.  It then runs through the symbol table and checks to make sure that the type definitions given in the
program are valid. It also checks to make sure that:
1. There is a unique user-defined function named main
2. There is no user-defined function named print
3. No other reserved word is used as an identifier
4. Each function refers only to local data objects
5. All literals satisfy the semantic definition of the language



Brief Explanation of Classes :

Token.java - This is where the values of our tokens are stored.  It defines the possible tokens for Klein as integer values.  It 
contains accessor methods so that values of the tokens can be returned.  It also has a getSymbol() method, which is used for the 
stack in our parser.

Scanner.java - The scanner class scans the input stream for valid tokens.  It detects and reports lexical errors and produces a 
sequence of tokens.

Parser.java - This class contains our parser.  The "meat" of this program is in the parse() method, where the parse table algorithm
is used to parse Klein programs.  The rest of this class creates the arraylists that contain the terminals, nonterminals, and rules 
for our parse table.  There is an optional method called "getError()" which prints out an error message if there is one.

RunParser.java - This file is used to test the parser.  It loops a set of test Klein programs through the parser one at a time and 
runs the parse() method which determins if the given Klein program is valid or not.

AbstractSyntaxTree.java - This file contains all the classes for the abstract syntax of our grammar.  It represents the abstract 
syntax tree.

Make[SemanticAction].java - These files contain the appropriate execute() method for each semantic action.

SemanticLookup.java - This file finds which Make[SemanticAction] class to run after finding a make-[SemanticAction] on the stack.

SemanticAction.java - This class is merely the interface for our semantic actions.

PrettyPrinter.java - This class produces the AST in a "pretty" format.

TypeChecker.java - The type checker for our compiler.  It makes sure that all defined types are valid.

TypeCheckerTest.java - Used to test the type checker and symbol table.

SymbolTable.java - Builds the symbol table for the type checker.

SymbolTableFactory.java - Builds a symbol table to be used in TypeCheckerTest.

DataType.java - Defines the data types for Klein.



Testing:

Given the set of test Klein files you wish to test the parser with do the following:

1. Open TypeCheckerTest.java in an editor
2. Find the line "String[] files = {...};" near the top of the program
3. Add or remove any files you wish to run by adding the path to the file to the list
4. Compile the given file for our compiler
5. Run the TypeCheckerTest class

The output gives the following information:
	1. The name of the identifier (and what function it's located in)
	2. The type of the identifier
	3. What line the identifier was declared on
	4. What lines the identifier are referenced on

The output produces something like the following:

Running messmeup.txt Test


"main":
	type = boolean
	declared on = 1
	references = 

"m" in "testnum":
	type = integer
	declared on = 4
	references = 5, 

"n" in "main":
	type = integer
	declared on = 1
	references = 2, 

"testnum":
	type = boolean
	declared on = 4
	references = 2, 