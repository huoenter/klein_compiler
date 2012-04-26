import java.util.ArrayList;
import java.util.Stack;


public class Parser {

	ArrayList<String>       terminals;
	ArrayList<String>       nonTerminals;
	int[][]                 index;
	ArrayList<String[]>     rules;
	Stack<String>           stack;
	private Scanner         input;
	ArrayList<String>		emptySets;
	SemanticLookup			semanticLookup;
	Stack<AbstractSyntaxTree> semanticStack;
	
	public Parser(Scanner input) {
		
		this.terminals       = populateTerminals();
		this.nonTerminals    = populateNonTerminals();
		this.index           = populateIndex();
		this.rules           = populateRules();
		this.stack           = new Stack<String>();
		this.input           = input;
		this.emptySets       = populateEmptySets();
		this.semanticLookup  = SemanticLookup.getSemanticLookup();
		this.semanticStack   = new Stack<AbstractSyntaxTree>();
		
	} // end constructor
	
	
	public AbstractSyntaxTree parse() throws Exception {

		Token    in;
		Token	 previousToken = null;
		String   nextStackItem;
		int      terminalPos;
		int      nonTerminalPos;
		String[] ruleList;		
		
		this.stack.push("$");
		this.stack.push("program");
		
		while (true) {
			
			in            = input.lookAhead();
			nextStackItem = this.stack.peek();
			
			if (nextStackItem.equals("$")) {
				return semanticStack.pop();
			}
			
			if (in.getType() == Token.Comment) {
				input.nextToken();
				continue;
			}
			
			if (nextStackItem.equals("emptySet")) {
				this.stack.pop();
				continue;
			}
			
			if (in.getType() == Token.EOFToken) {
				
				if (this.emptySets.contains(nextStackItem)) {
					this.stack.pop();
					continue;
				}
			}
			
			if (this.terminals.contains(nextStackItem)) {

				if (nextStackItem.equals(in.getSymbol())) {
					
					previousToken = input.nextToken();
					this.stack.pop();
	
				} else {
					return null;
				}
				
			} else if (this.nonTerminals.contains(nextStackItem)) {

				terminalPos = this.terminals.indexOf(in.getSymbol());
				nonTerminalPos = this.nonTerminals.indexOf(nextStackItem);
				
				if (terminalPos == -1) {
					return null;
				}
				
				if (nonTerminalPos == -1) {
					return null;
				}
				
				if (this.index[terminalPos][nonTerminalPos] != 0) {
					this.stack.pop();
					
					ruleList = this.rules.get(this.index[terminalPos][nonTerminalPos]);
					
					for (int i=ruleList.length-1; i>=0; i--) {
						this.stack.push(ruleList[i]);
					}
					
				} else {
					if(input.keywords.containsKey(in.getSymbol()))
						throw new Exception("Invalid keyword usage : " + in.getSymbol());
					else
						throw new Exception("Invalid input : " + in.getSymbol());
				}
				
			} else {
				
				SemanticAction action = SemanticLookup.getAction(stack.pop());
				action.execute(semanticStack, previousToken);
				
			}
		}
	} // end parse
	
	
	public ArrayList<String> populateEmptySets() {
		
		ArrayList<String> al = new ArrayList<String>();
		
		al.add("definitionsPri");
		al.add("formals");
		al.add("nonemptyformalsPri");
		al.add("exprPri");
		al.add("boolexprPri");
		al.add("termPri");
		al.add("factorPri");
		al.add("atomPri");
		al.add("actuals");
		al.add("nonemptyactualsPri");
		
		return al;
		
	} // end populateEmptySets()
	
	
	public ArrayList<String> populateTerminals() {
		
		ArrayList<String> al = new ArrayList<String>();
		
		al.add("identifier");
		al.add("print");
		al.add("+");
		al.add("-");
		al.add("*");
		al.add("/");
		al.add("integer");
		al.add("boolean");
		al.add("literal");
		al.add("char");
		al.add("$");
		al.add("=");
		al.add("function");
		al.add("and");
		al.add("or");
		al.add("(");
		al.add(")");
		al.add("comment");
		al.add(",");
		al.add("if");
		al.add("<");
		al.add("not");
		al.add("then");
		al.add("else");
		al.add("endif");
		al.add(":");
		
		return al;
		
	} // end populateTerminals()
	
	
	public ArrayList<String> populateNonTerminals() {
		
		ArrayList<String> al = new ArrayList<String>();
		
		al.add("program");
		al.add("definitions");
		al.add("definitionsPri");
		al.add("def");
		al.add("formals");
		al.add("nonemptyformals");
		al.add("nonemptyformalsPri");
		al.add("formal");
		al.add("body");
		al.add("type");
		al.add("expr");
		al.add("exprPri");
		al.add("boolexpr");
		al.add("boolexprPri");
		al.add("term");
		al.add("termPri");
		al.add("factor");
		al.add("factorPri");
		al.add("atom");
		al.add("atomPri");
		al.add("actuals");
		al.add("nonemptyactuals");
		al.add("nonemptyactualsPri");
		al.add("printnt");
		
		return al;
		
	} // end populateTerminals()
	
	
	public int[][] populateIndex() {
		// only populate the indexes which need to be populated
		// if a lookup is done for an index which is not there
		// 0 will be returned
		
		// [terminal][nonTerminal]
		int[][] al = new int[25][24];
		
		al[0][0] = 1;
		al[0][1] = 2;
		al[0][2] = 3;
		al[10][2] = 4;
		al[0][3] = 5;
		al[0][4] = 7;
		al[10][4] = 6;
		al[16][4] = 6;
		al[0][5] = 8;
		al[10][6] = 10;
		al[16][6] = 10;
		al[18][6] = 9;
		al[0][7] = 11;
		al[0][8] = 13;
		al[1][8] = 12;
		al[8][8] = 13;
		al[19][8] = 13;
		al[21][8] = 13;
		al[6][9] = 14;
		al[7][9] = 15;
		al[0][10] = 16;
		al[8][10] = 16;
		al[19][10] = 16;
		al[21][10] = 16;
		al[0][11] = 19;
		al[10][11] = 19;
		al[13][11] = 18;
		al[14][11] = 17;
		al[16][11] = 19;
		al[18][11] = 19;
		al[22][11] = 19;
		al[23][11] = 19;
		al[24][11] = 19;
		al[0][12] = 20;
		al[8][12] = 20;
		al[19][12] = 20;
		al[21][12] = 20;
		al[0][13] = 23;
		al[10][13] = 23;
		al[11][13] = 22;
		al[13][13] = 23;
		al[14][13] = 23;
		al[16][13] = 23;
		al[18][13] = 23;
		al[20][13] = 21;
		al[22][13] = 23;
		al[23][13] = 23;
		al[24][13] = 23;
		al[0][14] = 24;
		al[8][14] = 24;
		al[19][14] = 24;
		al[21][14] = 24;
		al[0][15] = 27;
		al[2][15] = 25;
		al[3][15] = 26;
		al[10][15] = 27;
		al[11][15] = 27;
		al[13][15] = 27;
		al[14][15] = 27;
		al[16][15] = 27;
		al[18][15] = 27;
		al[20][15] = 27;
		al[22][15] = 27;
		al[23][15] = 27;
		al[24][15] = 27;
		al[0][16] = 28;
		al[8][16] = 28;
		al[19][16] = 28;
		al[21][16] = 28;
		al[0][17] = 31;
		al[2][17] = 31;
		al[3][17] = 31;
		al[4][17] = 29;
		al[5][17] = 30;
		al[10][17] = 31;
		al[11][17] = 31;
		al[13][17] = 31;
		al[14][17] = 31;
		al[16][17] = 31;
		al[18][17] = 31;
		al[20][17] = 31;
		al[22][17] = 31;
		al[23][17] = 31;
		al[24][17] = 31;
		al[0][18] = 34;
		al[8][18] = 35;
		al[19][18] = 32;
		al[21][18] = 33;
		al[0][19] = 37;
		al[2][19] = 37;
		al[3][19] = 37;
		al[4][19] = 37;
		al[5][19] = 37;
		al[10][19] = 37;
		al[11][19] = 37;
		al[13][19] = 37;
		al[14][19] = 37;
		al[15][19] = 36;
		al[16][19] = 37;
		al[18][19] = 37;
		al[20][19] = 37;
		al[22][19] = 37;
		al[23][19] = 37;
		al[24][19] = 37;
		al[0][20] = 39;
		al[8][20] = 39;
		al[10][20] = 38;
		al[16][20] = 38;
		al[19][20] = 39;
		al[21][20] = 39;
		al[0][21] = 40;
		al[8][21] = 40;
		al[19][21] = 40;
		al[21][21] = 40;
		al[10][22] = 42;
		al[16][22] = 42;
		al[18][22] = 41;
		al[1][23] = 43;
		
		return al;
		
	} // end populateTerminals()
	
	
	public ArrayList<String[]> populateRules() {

		ArrayList<String[]> al = new ArrayList<String[]>();
		
		al.add(0, null);
		al.add(1, new String[] {"definitions", "make-program"});
		al.add(2, new String[] {"def", "definitionsPri"});
		al.add(3, new String[] {"def", "definitionsPri"});
		al.add(4, new String[] {"emptySet"});
		al.add(5, new String[] {"identifier", "make-identifier", "(", "formals", ")", "make-formals", ":", "type", "body", "make-body", "make-def"});
		al.add(6, new String[] {"emptySet"});
		al.add(7, new String[] {"nonemptyformals"});
		al.add(8, new String[] {"formal", "nonemptyformalsPri"});
		al.add(9, new String[] {",", "formal", "nonemptyformalsPri"});
		al.add(10, new String[] {"emptySet"});
		al.add(11, new String[] {"identifier", "make-identifier", ":", "type", "make-formal"});
		al.add(12, new String[] {"printnt", "body"});
		al.add(13, new String[] {"expr"});
		al.add(14, new String[] {"integer", "make-int-decl"});
		al.add(15, new String[] {"boolean", "make-bool-decl"});
		al.add(16, new String[] {"boolexpr", "exprPri"});
		al.add(17, new String[] {"or", "boolexpr", "exprPri", "make-or-expr"});
		al.add(18, new String[] {"and", "boolexpr", "exprPri", "make-and-expr"});
		al.add(19, new String[] {"emptySet"});
		al.add(20, new String[] {"term", "boolexprPri"});
		al.add(21, new String[] {"<", "term", "boolexprPri", "make-less-than-expr"});
		al.add(22, new String[] {"=", "term", "boolexprPri", "make-equals-expr"});
		al.add(23, new String[] {"emptySet"});
		al.add(24, new String[] {"factor", "termPri"});
		al.add(25, new String[] {"+", "factor", "termPri", "make-plus-expr"});
		al.add(26, new String[] {"-", "factor", "termPri", "make-minus-expr"});
		al.add(27, new String[] {"emptySet"}); 
		al.add(28, new String[] {"atom", "factorPri"});
		al.add(29, new String[] {"*", "atom", "factorPri", "make-times-expr"});
		al.add(30, new String[] {"/", "atom", "factorPri", "make-divide-expr"});
		al.add(31, new String[] {"emptySet"});
		al.add(32, new String[] {"if", "expr", "then", "expr", "else", "expr", "endif", "make-if"});
		al.add(33, new String[] {"not", "atom", "make-not-expr"});
		al.add(34, new String[] {"identifier", "make-identifier", "atomPri"});
		al.add(35, new String[] {"literal", "make-literal"});
		al.add(36, new String[] {"(", "actuals", ")", "make-actuals", "make-function-call"});
		al.add(37, new String[] {"emptySet"});
		al.add(38, new String[] {"emptySet"});
		al.add(39, new String[] {"nonemptyactuals"});
		al.add(40, new String[] {"expr", "make-actual", "nonemptyactualsPri"});
		al.add(41, new String[] {",", "expr", "make-actual", "nonemptyactualsPri"});
		al.add(42, new String[] {"emptySet"});
		al.add(43, new String[] {"print", "(", "actuals", ")", "make-actuals", "make-print-stmt"});
		
		return al;
		
	} // end populateTerminals()
	
} // END CLASS Parser

