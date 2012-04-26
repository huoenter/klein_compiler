/**
*
* CLASS : MakeFormals
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;
import java.util.ArrayList;

public class  MakeFormals implements SemanticAction
{
    public MakeFormals()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	ArrayList<Formal> tempFormals = new ArrayList<Formal>();
    	
    	while (semanticStack.peek() instanceof Formal) {
    		tempFormals.add((Formal)semanticStack.pop());
    	}
    	
    	Formal[] formals = new Formal[tempFormals.size()];
    	int count = 0;
    	
    	for (int i=tempFormals.size()-1; i>=0; i--) {
    		formals[count] = tempFormals.get(i);
    		count++;
    	}
    	
    	
    	semanticStack.push(new Formals(formals));
    }

    public String toString()
    {
        return "[MakeFormals]";
    }
}