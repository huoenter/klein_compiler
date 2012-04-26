import java.io.FileInputStream;
import java.io.PushbackInputStream;


public class TinyMachineCodeGeneratorTest {

	public static void main(String[] args) {
		
		try
		{
			// Any files which are used to test the parser should be
			// added onto this String array.  
			String[] files = {"main_with_math.txt"};
//							  "factorial.txt",
//							  "fibonacci.txt",
//							  "hypotenuse.txt",
//							  "leapyear.txt",
//							  "messmeup.txt",
//							  "power.txt",
//							  "simple_program.txt",
//							  "simple_program_2.txt"};
			
			for (int i=0; i<files.length; i++) {
			
//				System.out.println("Running " + files[i] + " Test\n\n");
				
				Parser parser = new Parser(new Scanner(new PushbackInputStream(new FileInputStream(files[i]))));
				
				AbstractSyntaxTree ast = parser.parse();
				
				SymbolTableFactory stf = new SymbolTableFactory((Program) ast);
				SymbolTable table = stf.build();				
				
				TypeChecker tc    = new TypeChecker( table );
				
				tc.check((Program) ast);
				
				ThreeCodeGenerator tcg = new ThreeCodeGenerator(table);
				tcg.check((Program) ast);
				
				TinyMachineCodeGenerator tmcg = new TinyMachineCodeGenerator("three_address_code.txt", table);
				tmcg.parse();
			}
		
		} catch (Exception excep) {
				System.out.println(excep);
		}
		
	}
}