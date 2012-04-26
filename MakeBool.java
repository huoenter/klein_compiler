/**
*
* CLASS : MakeBool
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeBool implements SemanticAction
{
    public MakeBool()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        semanticStack.push(new BooleanValue(lastToken.getStringVal()));
    }

    public String toString()
    {
        return "[MakeBool]";
    }
}