/**
*
* CLASS : SymbolTable
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 31, 2010
*
*/

import java.util.HashMap;

public class SymbolTable
{
    private HashMap<String, DataType> table;

    public SymbolTable()
    {
         table = new HashMap<String, DataType>();
    }

    public boolean contains(String id, String defName)
    {
    	String key = defName + ":" + id;
        return table.containsKey(key);
    }

    private boolean contains(String id)
    {
        return table.containsKey(new String(id));
    }
    
    public DataType lookup(String id, String defName, int line) throws Exception
    {
        if (!contains(id, defName))
		  {
            throw new Exception("Identifier not found: " + id);
		  }
        DataType dt = (DataType)table.get(new String(defName + ":" + id));
        dt.addReferenceNumber(line);
        return dt;
    }

    public void add(String id, DataType type) throws Exception
    {
        if (contains(id))
		  {
		  		if (id.startsWith("returnValue:main"))
					throw new Exception("No more than one main function.");
				else
					throw new Exception("Identifier already exists: " +  id);
		  }
        table.put(new String(id), type);
    }


    public String toString()
    {
    	String[] keys;
    	keys = (String[])table.keySet().toArray(new String[table.keySet().size()]);

    	String str = "";
    	String[] defVal = new String[2];
    	String def = "";
    	String val = "";
    	int    defLineNum = 0;
    	String refLineNums = "";
    	DataType dt;
    	
    	for (int i=0; i<keys.length; i++) {
    		
    		dt = table.get(keys[i]);
    		defVal = keys[i].split(":");
    		def = defVal[0];
    		val = defVal[1];
    		defLineNum = dt.getDefLineNumber();
    		refLineNums = dt.getReferenceNumber();
    		
    		if (def.equals("returnValue")) {
    			str += "\"" + val + "\":\n\ttype = " + dt + "\n\tdeclared on = " + defLineNum 
    			+ "\n\treferences = " + refLineNums + "\n\n";
    		} else {
    			str += "\"" + val + "\" in \"" + def + "\":\n\ttype = " + dt + "\n\tdeclared on = " + defLineNum 
    			+ "\n\treferences = " + refLineNums + "\n\n";
    		}
    		
    	}
    	
        return str;
    }
}