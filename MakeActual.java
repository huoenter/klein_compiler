/**
*
* CLASS : MakeActual
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeActual implements SemanticAction
{
    public MakeActual()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        semanticStack.push(new Actual((Expression)semanticStack.pop()));
    }

    public String toString()
    {
        return "[MakeActual]";
    }
}