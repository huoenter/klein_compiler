public class Frame {
	String name;
	int seqNum;
	int spAddr;
	int calledLine;
	boolean wait;
	
	private final int FRAME_SIZE = 15;
	
	public Frame(String n, int seq, int sp, int line)
	{
		name = n;
		seqNum = seq;
		spAddr = sp;
		calledLine = line;
		wait = false;
	}
	
	public boolean ifMain(){
		return seqNum == 0;
	}
	
	public int getSp(){
		return spAddr;
	}
	
	public int getPreviousSp(){
		return spAddr - FRAME_SIZE;
	}
	
	public int getNextSp(){
		return spAddr + FRAME_SIZE;
	}
	
	public int getSeqNum()
	{
		return seqNum;
	}
	
	public int getCalledLine()
	{
		return calledLine;
	}
	
	public String getName()
	{
		return name;
	}
}