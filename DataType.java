/**
*
* CLASS : DataType
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version October 31, 2010
*
*/

public class DataType
{
	public static final String Boolean = "boolean";
	public static final String Integer = "integer";

   private String typeName;
   private int	  defLineNumber;
   private String referenceLineNumbers;

   public DataType(String name, int defLineNumber)
   {
       this.typeName = name;
       this.defLineNumber = defLineNumber;
       this.referenceLineNumbers = "";
   }
   
   public int getDefLineNumber() {
	   return this.defLineNumber;
   }
   
   public void addReferenceNumber(int line) {
	   this.referenceLineNumbers += line + ", ";
   }
   
   public String getReferenceNumber() {
	   return this.referenceLineNumbers;
   }
   
   public String getType() {
	   return this.typeName;
   }

   public String toString()
   {
       return typeName;
   }
}