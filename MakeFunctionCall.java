/**
*
* CLASS : MakeFunctionCall
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class  MakeFunctionCall implements SemanticAction
{
    public MakeFunctionCall()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	Actuals actuals = (Actuals) semanticStack.pop();
    	Identifier name = (Identifier) semanticStack.pop();
    	
    	semanticStack.push(new FunctionCall(name , actuals));
   }

   public String toString()
   {
      return "[MakeFunctionCall]";
   }
}