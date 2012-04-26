/**
*
* CLASS : MakePlusExpr
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/


import java.util.Stack;

public class MakePlusExpr implements SemanticAction
{
    public MakePlusExpr()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Expression rightSide = (Expression)semanticStack.pop();
        Expression leftSide  = (Expression)semanticStack.pop();
        semanticStack.push(new PlusExpr(leftSide, rightSide));
    }

    public String toString()
    {
        return "[MakePlusExpr]";
    }
}