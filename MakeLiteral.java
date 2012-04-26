/**
*
* CLASS : MakeLiteral
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/


import java.util.Stack;

public class MakeLiteral implements SemanticAction
{
    public MakeLiteral()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	SemanticAction sa;
    	
    	if (lastToken.getStringVal().equals("A")) {
    		
    		sa = new MakeInt();
    		sa.execute(semanticStack, lastToken);
    		
    	} else {
    		
    		sa = new MakeBool();
    		sa.execute(semanticStack, lastToken);
    		
    	}

    }

    public String toString()
    {
        return "[MakeLiteral]";
    }
}