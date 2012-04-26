/**
*
* CLASS : MakePrintStatement
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakePrintStatement implements SemanticAction
{
	public MakePrintStatement()
	{
	}
    
	public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
	{
		Actuals actuals = (Actuals) semanticStack.pop();
		
		semanticStack.push(new PrintStatement(actuals));
	
	}

	public String toString()
	{
		return "[MakePrintStatement]";
	}
}
