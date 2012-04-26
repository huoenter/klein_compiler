/**
*
* CLASS : MakeActuals
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;
import java.util.ArrayList;

public class  MakeActuals implements SemanticAction
{
    public MakeActuals()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	ArrayList<Actual> tempActuals = new ArrayList<Actual>();
    	
    	while (semanticStack.peek() instanceof Actual) {
    		tempActuals.add((Actual)semanticStack.pop());
    	}
    	
    	Actual[] actuals = new Actual[tempActuals.size()];
    	int count = 0;
    	
    	for (int i=tempActuals.size()-1; i>=0; i--) {
    		actuals[count] = tempActuals.get(i);
    		count++;
    	}
    	
    	
    	semanticStack.push(new Actuals(actuals));
    }

    public String toString()
    {
        return "[MakeActuals]";
    }
}