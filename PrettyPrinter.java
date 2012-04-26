public class PrettyPrinter
{
	 public static void showAST(AbstractSyntaxTree ast, int indent)
    {
        if (ast instanceof Program)
        {
            Program p = (Program)ast;
            showSpaces(indent);
            System.out.println("PROGRAM");
            
            Def[] defs = p.definitions();
            
            for (int i=0; i<defs.length; i++) {
            	showAST(defs[i], indent+5);
            }
        
        }
        else if (ast instanceof Def)
        {
            Def d = (Def)ast;
            showSpaces(indent);
            System.out.println("DEFINITION");
			showAST(d.identifier(), indent+3);
			showAST(d.formal(), indent+3);
			
			showSpaces(indent+3);
			System.out.print("return ");
			showAST(d.typeId(), 0);
			
			showAST(d.body(), indent+3);
        
        }
        else if (ast instanceof Formals)
        {
        	showSpaces(indent);
    	    System.out.println("FORMALS");
    	    
			Formals formals = (Formals)ast;

			for (int i = 0; i < formals.formals().length; i++)
			{
				showAST(formals.formals()[i], indent+3);
			}
        
        }
        else if (ast instanceof Formal)
        {
        	Formal formal = (Formal)ast;
            showAST(formal.identifier(), indent+3);
			showAST(formal.typeId(), indent+6);
				
        }
        else if (ast instanceof FunctionCall)
        {
        	FunctionCall funcCall = (FunctionCall) ast;
        	showSpaces(indent);
        	System.out.println("FUNCTION CALL");
        	showAST(funcCall.getName(), indent+3);
        	showAST(funcCall.getActuals(), indent+3);
        }
        else if (ast instanceof Actuals)
        {
        	Actuals actuals = (Actuals) ast;
        	showSpaces(indent);
        	System.out.println("ACTUALS");
        	
        	for (int i=0; i<actuals.actuals().length; i++) {
        		showAST(actuals.actuals()[i], indent+3);
        	}
        }
        else if (ast instanceof Actual)
        {
        	Actual actual = (Actual) ast;
        	showAST(actual.expression(), indent+3);
        }
        else if (ast instanceof IntegerDeclaration)
        {
        	showSpaces(indent);
        	System.out.println("type(integer)");
        
        }
        else if (ast instanceof BooleanDeclaration)
        {
        	showSpaces(indent);
        	System.out.println("type(boolean)");
        }
        else if (ast instanceof Body)
        {
        	Body body = (Body)ast;
			PrintStatement[] prints = body.printStatements();
			Expression[] exprs = body.expressions();
			
			showSpaces(indent);
			System.out.println("BODY");
				
            for (int i = 0; i < prints.length; i++)
			{
				showAST(prints[i], indent+3);
			}
			
            for (int i = 0; i < exprs.length; i++)
			{
				showAST(exprs[i], indent+3);
			}
        
        }
		else if (ast instanceof IntegerValue )  
		{
			IntegerValue integervalue = (IntegerValue)ast;
			showSpaces(indent);
			System.out.println(integervalue.intValue());
		
		}
		else if (ast instanceof BooleanValue )  
		{
			BooleanValue booleanvalue = (BooleanValue)ast;
			showSpaces(indent);
			System.out.println(booleanvalue.boolValue());
		
		}
		else if (ast instanceof Identifier )  
		{
			Identifier identifier = (Identifier)ast;
			showSpaces(indent);
			System.out.println("identifier(" + identifier.idvalue() + ")");
		
		}
		else if (ast instanceof PrintStatement )  
		{
			PrintStatement printstatement = (PrintStatement)ast;
			showSpaces(indent);
			System.out.println("PRINT");
			
			for (int i=0; i<printstatement.actuals().actuals().length; i++) {
        		showAST(printstatement.actuals().actuals()[i], indent+3);
        	}
		
		}
		else if (ast instanceof  PlusExpr )  
		{
			PlusExpr plusexpr = (PlusExpr)ast;
			showSpaces(indent);
			System.out.println("+");
			showAST(plusexpr.leftOperand(), indent+3);
			showAST(plusexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  MinusExpr )  
		{
			MinusExpr minusexpr = (MinusExpr)ast;
			showSpaces(indent);
			System.out.println("-");
			showAST(minusexpr.leftOperand(), indent+3);
			showAST(minusexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  TimesExpr )  
		{
			TimesExpr timesexpr = (TimesExpr)ast;
			showSpaces(indent);
			System.out.println("*");
			showAST(timesexpr.leftOperand(), indent+3);
			showAST(timesexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  DividesExpr )  
		{
			DividesExpr dividesexpr = (DividesExpr)ast;
			showSpaces(indent);
			System.out.println("/");
			showAST(dividesexpr.leftOperand(), indent+3);
			showAST(dividesexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  EqualsExpr )  
		{
			EqualsExpr equalsexpr = (EqualsExpr)ast;
			showSpaces(indent);
			System.out.println("=");
			showAST(equalsexpr.leftOperand(), indent+3);
			showAST(equalsexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  LessThanExpr )  
		{
			LessThanExpr lessthanexpr = (LessThanExpr)ast;
			showSpaces(indent);
			System.out.println("<");
			showAST(lessthanexpr.leftOperand(), indent+3);
			showAST(lessthanexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  AndExpr )  
		{
			AndExpr andexpr = (AndExpr)ast;
			showSpaces(indent);
			System.out.println("and");
			showAST(andexpr.leftOperand(), indent+3);
			showAST(andexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  OrExpr )  
		{
			OrExpr orexpr = (OrExpr)ast;
			showSpaces(indent);
			System.out.println("or");
			showAST(orexpr.leftOperand(), indent+3);
			showAST(orexpr.rightOperand(), indent+3);
		
		}
		else if (ast instanceof  NotExpr )  
		{
			NotExpr notexpr = (NotExpr)ast;
			showSpaces(indent);
			System.out.println("not");
			showAST(notexpr.expression(), indent+3);
		
		}
		else if (ast instanceof  IfStatement )  
		{
			IfStatement ifstatement = (IfStatement)ast;
			showSpaces(indent);
			System.out.println("if");
			showAST(ifstatement.ifExpression(), indent+3);
			showSpaces(indent);
			System.out.println("else");
			showAST(ifstatement.thenExpression(), indent+3);
			showSpaces(indent);
			System.out.println("then");
			showAST(ifstatement.elseExpression(), indent+3);
		}
} 

public static void showSpaces( int count )
    {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

}