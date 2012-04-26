/**
*
* CLASS : MakeInt
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeInt implements SemanticAction
{
    public MakeInt()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        semanticStack.push(new IntegerValue(lastToken.getIntVal()));
    }

    public String toString()
    {
        return "[MakeInt]";
    }
}
