import java.io.FileInputStream;
import java.io.PushbackInputStream;

public class RunParser {

	public static void main(String[] args) {
		try {
			
			// Any files which are used to test the parser should be
			// added onto this String array.  
			String[] files = {
								//"factorial.txt"};
								"fibonacci.txt"};
//								"hypotenuse.txt",
//								"leapyear.txt",
//								"messmeup.txt",
//								"power.txt",
//								"simple_program.txt",
//								"simple_program_2.txt"};
			Scanner s;
			Parser p;
			AbstractSyntaxTree ast;

			for (int i=0; i<files.length; i++) {
				
				s = new Scanner(new PushbackInputStream(new FileInputStream(files[i])));
				p = new Parser(s);
				ast = p.parse();
				
				System.out.println("Running " + files[i] + " Test\n\n");
				
				if (ast instanceof AbstractSyntaxTree) {
					System.out.println("AST Returned");
					System.out.println("\n\n");
					PrettyPrinter.showAST(ast, 0);
				} else {
					System.out.println("null Returned");
				}
								
				System.out.println("\n\n");
			}
			
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();}
		
	}
	
}
