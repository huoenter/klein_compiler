/**
*
* CLASS : MakeBoolDecl
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.Stack;

public class MakeBoolDecl implements SemanticAction
{
   public MakeBoolDecl()
   {
   }

   public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
   {
	   semanticStack.push(new BooleanDeclaration());
   }

   public String toString()
   {
      return "[MakeBoolDecl]";
   }
}