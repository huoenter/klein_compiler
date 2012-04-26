/**
*
* CLASS : MakeIntDecl
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeIntDecl implements SemanticAction
{
   public MakeIntDecl()
   {
   }

   public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
   {
       //semanticStack.push(new IntegerDeclaration((Identifier)semanticStack.pop()));
	   semanticStack.push(new IntegerDeclaration());
   }

   public String toString()
   {
      return "[MakeIntDecl]";
   }
}
