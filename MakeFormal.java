/**
*
* CLASS : MakeFormal
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class  MakeFormal implements SemanticAction
{
    public MakeFormal()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
        Declaration type = (Declaration)semanticStack.pop();
		Identifier id       = (Identifier)semanticStack.pop();
        semanticStack.push(new Formal(id, type)); //Formal class should add the :
    }

    public String toString()
    {
        return "[MakeFormal]";
    }
}