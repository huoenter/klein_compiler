/**
*
* CLASS : MakeIf
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class  MakeIf implements SemanticAction
{
    public MakeIf()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Expression elseExpr   = (Expression)semanticStack.pop();
        Expression thenExpr = (Expression)semanticStack.pop();
		Expression ifExpr = (Expression)semanticStack.pop();
        semanticStack.push(new IfStatement(ifExpr, thenExpr, elseExpr));
    }

    public String toString()
    {
        return "[MakeIf]";
    }
}