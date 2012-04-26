/**
*
* CLASS : MakeIdentifier
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeIdentifier implements SemanticAction
{
    public MakeIdentifier()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	//System.out.println("\t\tPushing Identifier Onto Semantic Stack " + lastToken.getStringVal());
        semanticStack.push(new Identifier(lastToken.getStringVal(), lastToken.getLineNumber()));
    }

    public String toString()
    {
        return "[MakeIdentifier]";
    }
}
