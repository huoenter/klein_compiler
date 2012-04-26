/**
* CLASS : SymbolTableFactory
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 28, 2010
*
*/
//add the type of the formals?
public class SymbolTableFactory
{
    private Program program;

    public SymbolTableFactory(Program ast)
    {
        program = ast;
    }

    public SymbolTable build() throws Exception
    {
        SymbolTable t = new SymbolTable();
        Def[] d = program.definitions();

        for (int i = 0; i < d.length; i++)
        {
            Def current = d[i];
            String defName = current.identifier().idvalue();
            
            DataType currentType = null;
            
            if (current.typeId() instanceof IntegerDeclaration)
            {
                currentType = new DataType(DataType.Integer, current.identifier().getLineNumber());
            }	 
            else
			{
                if (current.typeId() instanceof BooleanDeclaration)
				{
                    currentType = new DataType(DataType.Boolean, current.identifier().getLineNumber());
				}	  
                else
                {
                    throw new Exception("invalid type declaration");
				}	  
			}
            
            // Add definition to list
			t.add("returnValue:" + defName, currentType);

			Formal[] formal = current.formal().formals();
			DataType formalType = null;
			
			for (int j = 0; j < formal.length; j++)
			{
				if (formal[j].typeId() instanceof IntegerDeclaration)
				{
			    formalType = new DataType(DataType.Integer, formal[j].identifier().getLineNumber());
				}	 
				else
				{
					if (formal[j].typeId() instanceof BooleanDeclaration)
					{
						formalType = new DataType(DataType.Boolean, formal[j].identifier().getLineNumber());
					}
					else
					{
						throw new Exception("invalid type declaration");
					}	  
				}		  
				
				t.add(defName + ":" + formal[j].identifier().idvalue(), formalType);
			}

        }

        return t;
    }
}
