/**
*
* CLASS : MakeNotExpr
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/


import java.util.Stack;

public class MakeNotExpr implements SemanticAction
{
    public MakeNotExpr()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Expression atom = (Expression)semanticStack.pop();//Not sure about this here...should expression be atom?
        semanticStack.push(new NotExpr(atom));
    }

    public String toString()
    {
        return "[MakeNotExpr]";
    }
}