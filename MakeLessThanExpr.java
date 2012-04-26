/**
*
* CLASS : MakeLessThanExpr
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/


import java.util.Stack;

public class MakeLessThanExpr implements SemanticAction
{
    public MakeLessThanExpr()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Expression rightSide = (Expression)semanticStack.pop();
        Expression leftSide  = (Expression)semanticStack.pop();
        semanticStack.push(new LessThanExpr(leftSide, rightSide));
    }

    public String toString()
    {
        return "[MakeLessThanExpr]";
    }
}