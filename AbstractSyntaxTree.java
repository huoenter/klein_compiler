/**
*
* CLASS : AbstractSyntaxTree
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 18, 2010
*
*/

abstract public class AbstractSyntaxTree
{
    public String toString() {return "AST";}
}


class Program extends AbstractSyntaxTree
{
    private Def[] definitions;

    public Program(Def[] d)
    {
        definitions = d;
    }

    public Def[] definitions() {return definitions;}

    public String toString()
    {
    	String str = "";
    	
    	for (int i=0; i<definitions.length; i++) {
    		str += definitions[i] + "\n";
    	}
    	
        return str;
    }
}


class Def extends AbstractSyntaxTree
{
    private Identifier id;
	private Formals formal;
	private Declaration typeId;
	private Body body;

    public Def(Identifier i, Formals f, Declaration tid, Body b)
    {
        id = i;
		formal = f;
		typeId = tid;
		body = b;
    }

    public Identifier identifier() 	{return id;}
	public Formals formal() 		{return formal;}
	public Declaration typeId() 	{return typeId;}
	public Body body() 				{return body;}

    public String toString()
    {
        return id + "(" + formal + ")" + " : " +
		         typeId + "\n" + body + "\n";
    }
}


abstract class Expression extends AbstractSyntaxTree
{
	private Expression leftOperand;
	private Expression rightOperand;
	
	public Expression(Expression rightExpr)
	{
		leftOperand  = null;
		rightOperand = rightExpr;
	}

	public void addLeftOperand(Expression leftExpr)
	{
		leftOperand = leftExpr;
	}

	public Expression leftOperand()  {return leftOperand;}
	public Expression rightOperand() {return rightOperand;}
	
	public void setLeftOperand (Expression leftExpr) {leftOperand  = leftExpr;}
	public void setRightOperand(Expression rightExpr) {rightOperand = rightExpr;}
		
	protected String operatorString() {return "operator";}

	public String toString()
	{
		return leftOperand + " " +
				operatorString() + " " +
				rightOperand;
	}
}


class Actual extends Expression
{
	private Expression exp;
	
	public Actual(Expression exp) {
		super(null);
		this.exp = exp;
	}
	
	public Expression expression() {return this.exp;}
	
	public String toString() { return this.exp.toString();}
}


class Actuals extends Expression
{
    private Actual[] actuals;

    public Actuals(Actual[] actuals)
    {
    	super(null);
        this.actuals = actuals;
    }
    
    public Actual[] actuals() {return this.actuals;}

    public String toString() {
    	
    	String actualString = "";
    	
    	for (int i=0; i<actuals.length; i++) {
    		actualString += actuals[i] + "\n";
    	}
    	
    	return actualString;
    }
}


class Body extends AbstractSyntaxTree
{
    private PrintStatement[] prints;
    private Expression[] exprs;

    public Body(PrintStatement[] prints, Expression[] exprs)
    {
        this.prints = prints;
        this.exprs  = exprs;
    }

    public PrintStatement[] printStatements() {return this.prints;}
    
    public Expression[] expressions() {return this.exprs;}
    
    public String toString() {
    	
    	String printString = "";
    	String exprsString = "";
    	
    	for (int i=0; i<prints.length; i++) {
    		printString += prints[i] + "\n";
    	}
    	
    	for (int i=0; i<exprs.length; i++) {
    		exprsString += exprs[i] + "\n";
    	}
    	
    	return printString + exprsString;
    }
}


class Formals extends AbstractSyntaxTree
{
    private Formal[] formals;

    public Formals(Formal[] formals)
    {
        this.formals = formals;
    }
    
    public Formal[] formals() {return this.formals;}

    public String toString() {
    	
    	String formalString = "";
    	
    	for (int i=0; i<formals.length; i++) {
    		formalString += formals[i] + "\n";
    	}
    	
    	return formalString;
    }
}


class Literal extends Expression
{
	public Literal() {super(null);}
	public String toString() {return "";};
}


class IntegerValue extends Literal
{
    private long intVal;

    public IntegerValue(long i)
    {
        intVal = i;
    }

    public long intValue() {return intVal;}
    
    public String toString() {return String.valueOf(intVal);}
}


class BooleanValue extends Literal
{
    private String boolVal;

    public BooleanValue(String b)
    {
        boolVal = b;
    }

    public String boolValue() {return boolVal;}
    
    public String toString() {return String.valueOf(boolVal);}
}


class Identifier extends Expression
{
    private String identifier;
    private int	   lineNumber;

    public Identifier(String id, int line)
    {
        super(null);
        identifier = id;
        lineNumber = line;
    }

    public String idvalue() {return identifier;}
    public String toString() {return identifier;}
    public int	  getLineNumber() {return lineNumber;}
    
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Identifier))
            return false;
        Identifier other = (Identifier)obj;
        return identifier.equals(other.idvalue());
    }
}


class IntegerDeclaration extends Declaration
{
	
	public IntegerDeclaration() {}
    
    public String toString()
    {
    	return "integer";
    }
}


class Declaration extends AbstractSyntaxTree
{
	
	public Declaration(){}

    public String toString()
    {
        return "";
    }
}


class BooleanDeclaration extends Declaration
{

	public BooleanDeclaration(){}
    
    public String toString()
    {
    	return "boolean";
    }
}


class PrintStatement extends AbstractSyntaxTree
{
    private Actuals actuals;

    public PrintStatement(Actuals actuals)
    {
        this.actuals = actuals;
    }

    public Actuals actuals() {return actuals;}
    
	public String toString() {
		String str = "print(";
		
		for (int i=0; i<actuals.actuals().length; i++) {
			str += actuals.actuals()[i] + ", ";
		}
		
		str += ")";
		
		return str;
	}
}


class FunctionCall extends Expression
{
	
	private Identifier name;
	private Actuals actuals;
	
	public FunctionCall(Identifier name, Actuals actuals)
	{
		super(null);
		this.name = name;
		this.actuals = actuals;
	}
	
	public Identifier getName() {return name;}
	public Actuals getActuals() {return actuals;}
	
	public String toString() {
		String str = name + "(";
		
		for (int i=0; i<actuals.actuals().length; i++) {
			str += actuals.actuals()[i] + ", ";
		}
		
		str += ")";
		
		return str;
	}
	
}


class PlusExpr extends Expression
{
    public PlusExpr(Expression expr) {super(expr);}

    public PlusExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "+";}
    
    public String toString() {return leftOperand() + " + " + rightOperand();}
}


class MinusExpr extends Expression
{
    public MinusExpr(Expression expr) {super(expr);}

    public MinusExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "-";}
    
    public String toString() {return leftOperand() + " - " + rightOperand();}
}


class TimesExpr extends Expression
{
    public TimesExpr(Expression expr) {super(expr);}

    public TimesExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "*";}
    
    public String toString() {return leftOperand() + " * " + rightOperand();}
}


class DividesExpr extends Expression
{
    public DividesExpr(Expression expr) {super(expr);}

    public DividesExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "/";}
    
    public String toString() {return leftOperand() + " / " + rightOperand();}
}


class EqualsExpr extends Expression
{
    public EqualsExpr(Expression expr) {super(expr);}

    public EqualsExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "=";}
    
    public String toString() {return leftOperand() + " = " + rightOperand();}
}

class LessThanExpr extends Expression
{
    public LessThanExpr(Expression expr) {super(expr);}

    public LessThanExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "<";}
    
    public String toString() {return leftOperand() + " < " + rightOperand();}
}


class AndExpr extends Expression
{
    public AndExpr(Expression expr) {super(expr);}

    public AndExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }
 
    protected String operatorString() {return "and";}
    
    public String toString() {return leftOperand() + " and " + rightOperand();}
}


class OrExpr extends Expression
{
    public OrExpr(Expression expr) {super(expr);}

    public OrExpr(Expression left, Expression right)
    {
        super(right);
        addLeftOperand(left);
    }

    protected String operatorString() {return "or";}
    
    public String toString() {return leftOperand() + " or " + rightOperand();}
}


class NotExpr extends Expression
{
	private Expression exp;
	
	public NotExpr(Expression exp) {
		super(null);
		this.exp = exp;
	}
	
	public Expression expression() {return this.exp;}
	
	public String toString() {return "not " + exp;}
}


class Formal extends AbstractSyntaxTree
{
	private Identifier identifier;
	private Declaration typeId;
	
	public Formal(Identifier id, Declaration tid)
	{
		identifier = id;
		typeId     = tid;
	}
	
	public Identifier identifier() {return identifier;}
	public Declaration typeId() {return typeId;}
	
	public String toString()
	{
		return identifier + " : " + typeId + "\n";
	}
	
}


class IfStatement extends Expression
{
	private Expression ifExpr;
	private Expression thenExpr;
	private Expression elseExpr;
	
	public IfStatement(Expression ifE, Expression thenE, Expression elseE)
	{
		super(null);
		ifExpr   = ifE;
		thenExpr = thenE;
		elseExpr = elseE;
	}
	
	public Expression ifExpression()   {return ifExpr;}
	public Expression thenExpression() {return thenExpr;}
	public Expression elseExpression() {return elseExpr;}
	
	public String toString()
   {
       return "if " + ifExpr + " then " + thenExpr + 
		        " else " + elseExpr + "endif" + "\n";
   }
}