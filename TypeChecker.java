/**
*
* CLASS : TypeChecker
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 28, 2010
*
*/

// TODO: fix unnecessary return
// lessthanexpr

public class TypeChecker
{
	private SymbolTable symbolTable;
	
	public TypeChecker(SymbolTable sTable)
	{
		symbolTable = sTable;

	}
	
	public void check(Program program) throws Exception
   {
		Def[] definitions = program.definitions();
		int mainCount = 0;
		int printCount = 0;
		for (int i = 0; i < definitions.length; i++)
		{
			if (definitions[i].identifier().toString().equals("main"))
			{
				mainCount++;
			}
			else if (definitions[i].identifier().toString().equals("print"))
			{
				printCount++;
			}  	
			checkDef(definitions[i]);
		}
		if (mainCount == 0)
		{
			throw new Exception("No main function in program.");
		}
		else if (mainCount > 1)
		{
			throw new Exception("Can only have 1 'main' function. " +
			                    "Current program has " + mainCount);
		}
		if (printCount > 0)
		{
			throw new Exception("Cannot define own print function.");
		}
   }
	
	public void checkDef(Def def) throws Exception
	{
		if (def.typeId() instanceof IntegerDeclaration)
		{
			checkBody(def.body(), DataType.Integer, def.identifier().idvalue());
		}
		else if (def.typeId() instanceof BooleanDeclaration)
		{
			checkBody(def.body(), DataType.Boolean,  def.identifier().idvalue());
		}
		else
		{
      	throw new Exception("Type checker expected integer or boolean type declaration.");
		}
	}
	
	public void checkBody(Body body, String type, String defName) throws Exception
	{
		Expression[] exprs = body.expressions();
		
		
		if (type.equals(DataType.Integer))
		{
			for (int i = 0; i < exprs.length; i++)
			{
				checkExpression(exprs[i], DataType.Integer, defName);
			} 
		}
		else if (type == DataType.Boolean)
		{
			for (int i = 0; i < exprs.length; i++)
			{
				checkExpression(exprs[i], DataType.Boolean, defName);
			} 
		}
	}
	
	public String checkExpression(Expression expr, String type, String defName) throws Exception
	{
		if (expr instanceof Identifier)
		{
			Identifier id = (Identifier)expr;
			return symbolTable.lookup(id.idvalue(), defName, id.getLineNumber()).getType();
		}
		else if (expr instanceof IntegerValue)
		{
			return DataType.Integer;
		}
		else if (expr instanceof BooleanValue)
		{
			return DataType.Boolean;
		}
		else if (expr instanceof PlusExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);

			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for plus operation.");
			}	
		}
		else if (expr instanceof MinusExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for minus operation.");
			}	
		}
		else if (expr instanceof TimesExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for multiplication operation.");
			}	
		}
		else if (expr instanceof DividesExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for division operation.");
			}	
		}
		else if (expr instanceof EqualsExpr)
		{
			EqualsExpr equalsexpr = (EqualsExpr)expr;
			Expression left = equalsexpr.leftOperand();
			Expression right = equalsexpr.rightOperand();
			String leftType = checkExpression(left, DataType.Integer, defName);
			String rightType = checkExpression(right, DataType.Integer, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for equals operation.");
			}
		}
		else if (expr instanceof AndExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for 'and' operation.");
			}	
		}
		else if (expr instanceof OrExpr)
		{
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			String leftType = checkExpression(left, type, defName);
			String rightType = checkExpression(right, type, defName);
			
			if (leftType.equals(rightType))
			{
				return leftType;
			}
			else
			{
				throw new Exception("Types don't match for 'or' operation.");
			}	
		}
		else if (expr instanceof NotExpr)
		{
			NotExpr notExpr = (NotExpr)expr;
			return checkExpression(notExpr.rightOperand(), type, defName);
		}
		else if (expr instanceof LessThanExpr)
		{

			Expression left = expr.leftOperand();
			checkExpression(left, DataType.Integer, defName);
			Expression right = expr.rightOperand();
			checkExpression(right, DataType.Integer, defName);
			return DataType.Boolean;
		}
		else if (expr instanceof FunctionCall)
		{
			FunctionCall function = (FunctionCall)expr;
			Identifier funcname = function.getName();
			Actual[] actuals = function.getActuals().actuals();
			for (int i = 0; i < actuals.length; i++)
			{
				checkExpression(actuals[i].expression(), type, defName);
			}
			return symbolTable.lookup(funcname.idvalue(), "returnValue", funcname.getLineNumber()).getType();
		}
		else if (expr instanceof IfStatement)
		{
			IfStatement ifStatement = (IfStatement)expr; 
			checkExpression(ifStatement.ifExpression(), DataType.Boolean, defName);
			checkExpression(ifStatement.thenExpression(), type, defName);
			checkExpression(ifStatement.elseExpression(), type, defName);
		}
		else
		{
			throw new Exception("Invalid expression found.");
		}
		return type;											
	}

}