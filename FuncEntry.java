public class FuncEntry{
	int abStart;
	int abEnd;
	String name;
	
	public FuncEntry(String n, int s, int e)
	{
		abStart = s;
		abEnd = e;
		name = n;
	}
	
	public FuncEntry(String n, int s)
	{
		abStart = s;
		name = n;
	}
	
	
	public FuncEntry(int e)
	{
		abEnd = e;
	}

	public int getAbStart()
	{
		return abStart;
	}
	
	public int getAbEnd()
	{
		return abEnd;
	}
	
	public String getName()
	{
		return name;
	}
}