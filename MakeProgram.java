/**
*
* CLASS : MakeProgram
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 17, 2010
*
*/

import java.util.ArrayList;
import java.util.Stack;

public class MakeProgram implements SemanticAction
{
    public MakeProgram()
    {
    }

    public void execute(Stack<AbstractSyntaxTree> semanticStack, Token lastToken)
    {
    	ArrayList<Def> tempDefinitions = new ArrayList<Def>();
    	
    	while (!semanticStack.empty()) {
    		tempDefinitions.add((Def)semanticStack.pop());
    	}
    	
    	Def[] definitions = new Def[tempDefinitions.size()];
    	int count = 0;
    	
    	for (int i=tempDefinitions.size()-1; i>=0; i--) {
    		definitions[count] = tempDefinitions.get(i);
    		count++;
    	}
    	
    	semanticStack.push(new Program(definitions));
    }

   public String toString()
   {
      return "[MakeProgram]";
   }
}