/**
*
* CLASS : Scanner
*
* @authors Keisha Harthoorn, Robert Main, and Chen Huo
* @version September 22, 2010
*
*
*/

import java.io.PushbackInputStream;
import java.util.HashMap;

public class Scanner
{
	private PushbackInputStream input;
	private Token peekToken;
	
	private String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
	private String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int	   lineCount;
	
	public HashMap<String, Integer> keywords = new HashMap<String, Integer>();											 
	
	public Scanner(PushbackInputStream stream)
    {
		lineCount = 1;
		input = stream;	
		peekToken = null;
		//keywords.put("print",   Token.Print);
		keywords.put("print",   Token.Print);
		keywords.put("integer", Token.Integer);
		keywords.put("boolean", Token.Boolean);
		keywords.put("true",    Token.Literal);
		keywords.put("false",   Token.Literal);
		keywords.put("if",      Token.ConditionalOp);
		keywords.put("then",    Token.ConditionalOp);
		keywords.put("else",    Token.ConditionalOp);
		keywords.put("endif",   Token.ConditionalOp);
		keywords.put("main",    Token.Identifier);
		keywords.put("not",     Token.LogicOp);
		keywords.put("or",      Token.LogicOp);
		keywords.put("and",     Token.LogicOp);
   }
	
	public int getLineNumber() {
		return lineCount;
	}
	
	public Token lookAhead() throws Exception
	{
		if (peekToken == null)
		{
			peekToken = getFollowingToken();
		}
		return peekToken;
	}
	
	public Token nextToken() throws Exception
	{
		Token result;
		if (peekToken != null)
		{
			result = peekToken;
			peekToken = null;
		}
		else
		{
			result = getFollowingToken();
		}

		return result;
	}
	
	protected Token getFollowingToken() throws Exception
	{
       int nextByte = getNextByte();
       
       if (nextByte == -1 || nextByte == 255) 
    	   // If the last character of a file is read,
			// which should be -1, and is then unread,
			// then the next time that character is read
			// it will come back as 255 instead of -1.
			// As far as we can tell, 255 is just as good
			// of an EOF statement as -1.
       {
    	   return new Token(Token.EOFToken, getLineNumber());
	   }
       
       char  nextChar = (char)nextByte;
       Token result;

       switch (nextChar)
       {
          case '/': 
             result = isCommentOrDivide();
             break;
          case '*': 
             result = new Token(Token.TimesOp, nextChar, getLineNumber());
             break;
          case '=': 
             result = new Token(Token.AssignmentOp, getLineNumber());
             break;
          case '+': 
             result = new Token(Token.PlusOp, getLineNumber());
             break;
          case '-': 
        	  result = negOrMinus();
              break;
          case '<': 
        	  result = new Token(Token.LessThanOp, getLineNumber());
        	  break;
          case '(':
        	  result = new Token(Token.LeftParen, getLineNumber());
        	  break;
          case ')': 
        	  result = new Token(Token.RightParen, getLineNumber());
        	  break;
          case ',': 
        	  result = new Token(Token.Comma, getLineNumber());
        	  break;
          case ':': 
        	  result = new Token(Token.TypeIdentifier, getLineNumber());
        	  break;	
          case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g':
          case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n':
          case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u':
          case 'v': case 'w': case 'x': case 'y': case 'z': 
          case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G':
          case 'H': case 'I': case 'J': case 'K': case 'L': case 'M': case 'N':
          case 'O': case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U':
          case 'V': case 'W': case 'X': case 'Y': case 'Z':
             result = checkForIdentifier(Character.toString(nextChar));
             break;
          case '0':
             result = resultFromState2();
             break;
          case '1': case '2': case '3': case '4': case '5':
          case '6': case '7': case '8': case '9':
             result = resultFromState1(nextChar);
             break;
          default:
             input.unread(nextChar);
             throw new Exception("Invalid character " + nextChar );
		}
		return result;
	}
	
	protected Token isCommentOrDivide() throws Exception
	{
		char nextChar = read();
		Token result;

		if (nextChar == '/')
		{
			String comment = "";
			nextChar = read();
			
			while(nextChar != '\n') {
				comment += nextChar;
				nextChar = read();
			}
			lineCount++;
			result = new Token(Token.Comment, comment, getLineNumber());
		}
		else
		{
			unread(nextChar);
			result = new Token(Token.DivideOp, getLineNumber());
		}
		return result;
	}
	
	protected Token negOrMinus() throws Exception
	{
		char nextChar = read();
		Token result;
		if (isWhitespace(nextChar))
		{
			result = new Token(Token.MinusOp, getLineNumber());
		}
		else
		{
			input.unread(nextChar);
			result = resultFromState3("-");
		}
		return result;
	}
	
	protected Token checkForIdentifier(String buffer) throws Exception
	{
		char nextChar = read();
		
		while (true) {
			
			if (uppercaseLetters.indexOf(nextChar) != -1
					|| lowercaseLetters.indexOf(nextChar) != -1
					|| isNumericDigit(nextChar)) {
				
				buffer += nextChar;
				nextChar = read();
				
			} else {
				unread(nextChar);
				break;
			}
		}	
		
		if (buffer.length() > 256)
		{
			throw new Exception("Identifiers must be less than 256 characters.");
		}
		
		return checkForKeyword(buffer);
		
	}
	
	protected Token checkForKeyword(String buffer) throws Exception
	{
		Token result;
		if (keywords.containsKey(buffer))
		{
			result = new Token(keywords.get(buffer), buffer, getLineNumber());
		}
		else
		{
			result = new Token(Token.Identifier, buffer, getLineNumber());
		}
		return result;
	}
	
	protected Token resultFromState2() throws Exception
   {
		char nextChar = read();
		
	   if (isWhitespace(nextChar)
			|| nextChar == ')'
			|| nextChar == ',')
      {
		   return new Token(Token.Literal, 0, getLineNumber()); // new
      }
		else
		{	
		   input.unread(nextChar);	
			throw new Exception("Expected whitespace following the zero.");
		}	
   }
	
	protected Token resultFromState3(String buffer) throws Exception
	{
		char nextChar = read();
		Token result;
		if (nextChar == '0')
		{
			throw new Exception("Cannot have a negative sign followed by a zero.");
		}
		else if (isNumericDigit(nextChar) && nextChar != '0')
		{
			result = resultFromState4("-" + nextChar);
		}
		else // It's a character
		{
			input.unread(nextChar);
			result = new Token(Token.MinusOp, getLineNumber());
		}
		return result;
	}
	
	protected Token resultFromState4(String buffer) throws Exception
	{
		char nextChar = read();
		
      while (isNumericDigit(nextChar))
      {
         buffer  += nextChar; 
         nextChar = read();
      }
		
		if (buffer.length() > 11)
		{
			throw new Exception("Integer too small: " + buffer);
		}
		long temp = Long.parseLong(buffer);
		if (temp < -4294967296L)// 2^32 - 1		     
		{
			throw new Exception("Integer too small: " + buffer);
		}	
		
		input.unread(nextChar);

		if (!isWhitespace(nextChar) 
				&& nextChar != ')'
				&& nextChar != ',')
		{
			throw new Exception("Expected a whitespace following the negative integer.");
		}
		
		return new Token(Token.Literal, Long.parseLong(buffer), getLineNumber()); // new
	}
	
	protected Token resultFromState1(char firstInt) throws Exception
	{
		char nextChar = read();
		String buffer = "";
		buffer += firstInt;
		
		while (isNumericDigit(nextChar))
		{
			buffer += nextChar;
			nextChar = read();
		}
			if (buffer.length() > 10)
			{
				throw new Exception("Integer too large: " + buffer);
			}
			long temp = Long.parseLong(buffer);
			if ( temp > 4294967295L)// 2^32 - 1		     
			{
				throw new Exception("Integer too large: " + buffer);
			}	 
		unread(nextChar);
		
		if (!isWhitespace(nextChar) 
				&& nextChar != ')'
				&& nextChar != ',')
		{
			throw new Exception("Expected whitespace following the positive integer.");
		}
			return new Token(Token.Literal, Long.parseLong(buffer), getLineNumber());

	}
	
    protected int getNextByte() throws Exception
    {
       int nextChar;
       while (true)
       {
          nextChar = input.read();
          if ( nextChar == -1 )
             return -1;
          if ( !isWhitespace((char) nextChar) )
             return nextChar;
       }
    }
	 
	 protected boolean isNumericDigit(char c)
    {
       return ('0' <= c) && (c <= '9');
    }
    
    protected boolean isWhitespace(char c)
    {
 		return c == ' '  ||
				isNewLine(c) ||
			    c == '\r' ||
 			    c == '\b' ||
 			    c == '\f' ||
 			    c == '\t';
    }
    
    protected boolean isNewLine(char c) {
    	if (c == '\n') {
    		lineCount++;
    		return true;
    	}
    	return false;
    }
	 
	protected char read() throws Exception
    {
        return (char) input.read();
    }

    protected void unread( char c ) throws Exception
    {
        input.unread( (int) c );
    }
	
}


