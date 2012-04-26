/**
*
* CLASS : MakeBody
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;
import java.util.ArrayList;

public class  MakeBody implements SemanticAction
{
    public MakeBody()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	ArrayList<PrintStatement> tempPrints = new ArrayList<PrintStatement>();
    	ArrayList<Expression> tempExpressions = new ArrayList<Expression>();
    	
    	while (!(semanticStack.peek() instanceof Declaration)) {
    		
    		if (semanticStack.peek() instanceof PrintStatement) {
    			tempPrints.add((PrintStatement)semanticStack.pop());
    		
    		} else {
    			tempExpressions.add((Expression) semanticStack.pop());
    		}
    		
    	}
    	
    	PrintStatement[] prints = new PrintStatement[tempPrints.size()];
    	Expression[] expressions = new Expression[tempExpressions.size()];
    	
    	int count = 0;
    	for (int i=tempPrints.size()-1; i>=0; i--) {
    		prints[count] = tempPrints.get(i);
    		count++;
    	}
    	
    	count = 0;
    	for (int i=tempExpressions.size()-1; i>=0; i--) {
    		expressions[count] = tempExpressions.get(i);
    		count++;
    	}
    	
    	semanticStack.push(new Body(prints, expressions));
    }

    public String toString()
    {
        return "[MakeBody]";
    }
}