/**
*
* INTERFACE : SemanticAction
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public interface SemanticAction
{
   public void   execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken);
   public String toString();
}