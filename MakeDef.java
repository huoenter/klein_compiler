/**
*
* CLASS : MakeDef
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class  MakeDef implements SemanticAction
{
    public MakeDef()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Body body = (Body)semanticStack.pop();
        Declaration type = (Declaration)semanticStack.pop();
		Formals formals  = (Formals)semanticStack.pop();
		Identifier id   = (Identifier)semanticStack.pop();
        semanticStack.push(new Def(id, formals, type, body)); //Def class should add the () and :
    }

    public String toString()
    {
        return "[MakeDef]";
    }
}
