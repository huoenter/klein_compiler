/**
*
* CLASS : Token
*
* @author Keisha Harthoorn
* @version September 12 2010
*
*/

public class Token
{
	public static final int Identifier      = 0;
//	public static final int IntDeclaration  = 1;
//	public static final int BoolDeclaration = 2;
	public static final int Print           = 3;
	public static final int PlusOp          = 4;
	public static final int MinusOp         = 5;
	public static final int TimesOp         = 6;
	public static final int DivideOp        = 7;
//	public static final int IntValue        = 8;
//	public static final int BoolValue       = 9;
//	public static final int CharValue       = 10;
	public static final int EOFToken        = 11;
	public static final int AssignmentOp    = 12;
	public static final int ConditionalOp   = 13;
//	public static final int Function        = 14;
	public static final int LeftParen       = 15;
	public static final int RightParen      = 16;
	public static final int TypeIdentifier  = 17;
	public static final int Comment         = 18;
	public static final int Comma           = 19;
	public static final int LogicOp         = 20;
	public static final int LessThanOp      = 21;
	public static final int Literal         = 22;
	public static final int Boolean         = 23;
	public static final int Integer         = 24;
	
	private int     tokenType;
	private long    intValue;
	private String  stringValue;
	private boolean boolValue;
	private int		lineNumber;
	
	// Constructors
	protected Token(int type, String string, long integer, boolean bool, int line)
	{
		tokenType   = type;
		stringValue = string;
		intValue    = integer;
		boolValue   = bool;
		lineNumber  = line;
	}
	
	public Token(int type, int line)
	{
		this(type, "A", 0, true, line);
	}
	
	public Token (int type, String string, int line)
	{
		this(type, string, 0, true, line);
	}
	
	public Token(int type, long integer, int line)
	{
		this(type, "A", integer, true, line);
	}
	
	public Token(int type, boolean bool, int line)
	{
		this(type, "A", 0, bool, line);
	}
	
	// Methods
	public String getSymbol()throws Exception {
		switch (this.getType()) {
		case Token.Identifier:
			return "identifier";
//		case Token.IntDeclaration:
//			return "integer";
//		case Token.BoolDeclaration:
//			return "boolean";
		case Token.Print:
			return "print";
		case Token.PlusOp:
			return "+";
		case Token.MinusOp:
			return "-";
		case Token.TimesOp:
			return "*";
		case Token.DivideOp:
			return "/";
//		case Token.IntValue:
//			return "literal";
//		case Token.BoolValue:
//			return "literal";
//		case Token.CharValue:
//			return "charvalue";
		case Token.EOFToken:
			return "eof";
		case Token.AssignmentOp:
			return "=";
		case Token.ConditionalOp:
			return this.getStringVal();
//		case Token.Function:
//			return "function";
		case Token.LeftParen:
			return "(";
		case Token.RightParen:
			return ")";
		case Token.TypeIdentifier:
			return ":";
		case Token.Comment:
			return "//";
		case Token.Comma:
			return ",";
		case Token.LogicOp:
			return this.getStringVal();
		case Token.LessThanOp:
			return "<";
		case Token.Literal:
			return "literal";
		case Token.Boolean:
			return "boolean";
		case Token.Integer:
			return "integer";
		default:
			return null;
		}
			
	} // end getSymbol()
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public int getType()
	{
		return tokenType;
	}
	
	public String getStringVal()
	{
		return stringValue;
	}
	
	public long getIntVal()
	{
		return intValue;
	}
	
	public boolean getBoolVal()
	{
		return boolValue;
	}	
	
}