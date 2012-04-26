/**
*
* CLASS : MakeOrExpr
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/


import java.util.Stack;

public class MakeOrExpr implements SemanticAction
{
    public MakeOrExpr()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Expression rightSide = (Expression)semanticStack.pop();
        Expression leftSide  = (Expression)semanticStack.pop();
        semanticStack.push(new OrExpr(leftSide, rightSide));
    }

    public String toString()
    {
        return "[MakeOrExpr]";
    }
}