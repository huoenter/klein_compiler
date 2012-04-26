import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TinyMachineCodeGenerator {

	private BufferedReader in;
	private SymbolTable table;
	private int instruction;
	private String[] operands;

	private final int MAX_BRANCH = 18;	
	
	//const dealing with the frame
	private final int RETURN_ADDR = 7;   //hmm... return_addr actually stores
										//the value returned from the callee
	private final int RETURN_VAL = 6;
	private final int LOCAL_RETURN = 8;
	private final int PARAM = 9;
//	private final int CALLER_ADDRESS = 9;
//	private final int FUNCTION_ENTRY = 0;
	private final int SP_START = 1;
	private final int ULTI_RETURN = 0;
	public final int FRAME_SIZE = 15;
//	private final int PARA_SIZE = 9;
	
	private int para_count = 0;
	private String[] para;
	
	/*
	private String[] func_entry;
	private int[] func_count;
	private int pro_id;
	private int current_func = -1;
	private final int MAX_PROCEDURE = 55;
	
	private int sp, fp;    //point to the start of the last frame
	*/
	private int sp;
	private ArrayList<Frame> rts;
	private FuncEntry[] fe;
	private int func_count;
	private final int MAX_PROCEDURE = 88;
	
	private int[] condIns = new int[MAX_BRANCH];
	private int[] offset = new int[MAX_BRANCH];
	private int branchNum;
	private boolean[] wait = new boolean[MAX_BRANCH];
	
	private Frame currentFrame;
	
	public TinyMachineCodeGenerator(String fileName, SymbolTable table) {
		
		try {
			this.in  = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		this.table = table;
		this.instruction = 0;
		operands = null;
		branchNum = 0;
		/*
		func_entry = new String[MAX_PROCEDURE];
		func_count = new int[MAX_PROCEDURE];
		pro_id = 0;
		*/
		
		rts = new ArrayList<Frame>();
		fe = new FuncEntry[MAX_PROCEDURE];
		func_count = 0;
		
		para = new String[6];
		
		// initialize the stack pointer
		sp = SP_START;
		
		for(int i = 0; i < MAX_BRANCH; i++)
		{
			wait[i] = false;
		}
	} // end constructor
	
	private void printComment(String str) {
		System.out.println("*" + str);
	}
	
	public void parse() throws IOException {
		String line;
		String[] lineSeg;
		String cmd;
		
		//initialize "main"
		rts.add(new Frame("main", 0, sp, instruction));
		currentFrame = rts.get(0);
		
		printPrelude();
		
		while ((line = in.readLine()) != null) {
			
			lineSeg = line.split(" ");
			cmd = lineSeg[0];
			
//			printComment(line);
			
			if (cmd.equals("ACTUAL")) {
				String register = lineSeg[2].substring(1,lineSeg[2].length());
				//println("LD " + register + "," + (register + sp) + "(6)");
				load(atoi(register), currentFrame.getSp() + atoi(register));
				store(atoi(register), currentFrame.getNextSp() + atoi(register) + PARAM);
				comment("load the actual to the next frame's temp area.");
			}
			else if (cmd.equals("END")) {
//				current_func = -1;
				//println("OUT " + (SP_START + RETURN_ADDR) + ",0,0");
				comment("End of " + lineSeg[1]);
				sp = sp - FRAME_SIZE;
				//currentFrame.spAddr = currentFrame.spAddr - FRAME_SIZE;
				
				
				if (lineSeg[1].equals("main")) {

					println("OUT 0,0,0");
					println("HALT 0,0,0");
				}
				else {
					fe[func_count - 1].abEnd = instruction;
			//		rts.remove(rts.size() - 1);
			//		currentFrame = rts.get(rts.size() - 1);
				}

			}
			else if (cmd.equals("PARAM")) {
				if(!currentFrame.getName().equals("main")) {
				para[para_count] = lineSeg[1];
				comment("count the parameter " + lineSeg[1]);
				para_count++;
				}
			}
			else if (cmd.equals("ENTRY")) {
//				func_count++;
				comment("entry of " + lineSeg[1]);
				para_count = 0;
				
				fe[func_count] = new FuncEntry(lineSeg[1], instruction);
//				comment("new an entry of " + lineSeg[1] + " start at line : " + instruction);
				currentFrame = matchFrame(lineSeg[1]);
//				comment("currentFrame = " + currentFrame.getName());
				if (!lineSeg[1].equals("main")){
				if (currentFrame.wait || true) {
//					comment("currentFrame = " + currentFrame.getName());
//					int target = currentFrame.getCalledLine();
//					int offset = instruction - corresFrame.getCalledLine();
//					sp = sp + FRAME_SIZE;
//					currentFrame = new Frame(lineSeg[1], func_count, sp, target);
					rts.add(currentFrame);
					setPC(instruction, currentFrame.getCalledLine());
					
//					println("LDC 0," + offset + "(0)");
//					System.out.println(corresFrame.getCalledLine() + ": " + "JNE 6," 
//														+ offset + "(7)");
				}
				}
				//else {}
				func_count++;
			}
			else if (cmd.equals("CALL")) {
//				println("HALT 0,0,0");
				sp = sp + FRAME_SIZE;

	//			comment("next : it's a call. func_entry = " + func_count);
				
				println("LDC 0," + (instruction + 3) + "(0)");
				store(0, ULTI_RETURN);
				
				rts.add(new Frame(lineSeg[1], func_count, sp, instruction));
				
				int flag = matchEntry(lineSeg[1]);
				if (flag > -1) {
//					currentFrame = new Frame(lineSeg[1], func_count + 1, sp, instruction);
					setPC(fe[flag].getAbStart(), instruction);
	
				} else {
					instruction++;
					comment("currentFrame = " + currentFrame.getName());
					currentFrame.wait = true;
					currentFrame.calledLine = instruction - 1;
				}
				comment("Vnext : call " + lineSeg[1]);
			} else if (cmd.equals("RETURN")) {  //DONE
				// NEED TO CHANGE THIS LATER
				comment("~return of " + currentFrame.getName());
//				comment(currentFrame.getName());
				String register = lineSeg[1].substring(1,lineSeg[1].length());
				load(atoi(register), currentFrame.getSp() + LOCAL_RETURN);
				
				//*********if it is not the first caller, return it to the caller's returned value
				//*********else, print out the result and halt

					//println("ST " + register + "," + (sp + RETURN_VAL) + "(6)\t 
					//store the value that want to return in return_val");
					store(atoi(register), currentFrame.getSp() + RETURN_VAL);
					comment("store the value that want to return in return_val");
					//println("LD " + register + "," + (sp + RETURN_ADDR) + "(6)\t get the stored value");
					load(atoi(register), currentFrame.getSp() + RETURN_VAL);
				if(!currentFrame.ifMain()) {
					store(atoi(register), currentFrame.getPreviousSp() + RETURN_ADDR);
					comment("next: jump back to the caller");
			//		println("JNE " + register + ",-" + (instruction - func_count[current_func]) + 
	//													"(7)\t Seems right. Severe WARNING");
//					int offset = currentFrame.getCalledLine() - fe[func_count].abEnd;
//					println("JNE 6," + offset + "(7)");
					
					//setPC(currentFrame.getCalledLine() + 1, instruction);
					println("LD 7,0(6)");
				//	instruction++;
//					comment("still alive?");
//					fe[func_count - 1].abEnd = instruction;
					
					
//					rts.remove(rts.size() - 1);
//					currentFrame = rts.get(rts.size() - 1);
//					currentFrame.spAddr = currentFrame.spAddr + FRAME_SIZE;
			//		current_func = -1;
				} 
				else {					
					load(0, SP_START + RETURN_ADDR);
					println("OUT " + register + ",0,0");
					println("HALT 0,0,0");
				}
				sp = sp -FRAME_SIZE;

				
			} else if (cmd.startsWith("T")) {
				
				
				// USING INDEX FROM TEMP REGISTER IN 3-ADDRESS CODE
				// MAY CAUSE PROBLEMS IF A REGISTER GREATER THAN 6
				// IS NEEDED
				String register = cmd.substring(1,cmd.length());
				
				//convert true to 1 and false to 0
				if (lineSeg[2].equals("true")){
					lineSeg[2] = "1";
				} else if (lineSeg[2].equals("false")){
					lineSeg[2] = "0";
				} 
				boolean b = true;
				for(int i = 0; i < para_count; i++){
					if (lineSeg[2].equals(para[i])) {
						//assign the i-th temp of the caller to the local
						//println("LD " + register + "," + (sp - FRAME_SIZE + i) + "(6)");
						load(atoi(register), currentFrame.getSp() + i + PARAM);
						store(atoi(register), currentFrame.getSp() + atoi(register));
						store(atoi(register), currentFrame.getSp() + LOCAL_RETURN);
			//			comment("highest risky place to be wrong.");
						b = false;
					}
				}
				if(b){
					println("LDC " + register + "," + lineSeg[2] + "(0)");
	//				println("ST " + register + "," + （sp + register) + "(6)");   
					//to the corresponding address of the frame
					store(atoi(register), currentFrame.getSp() + atoi(register));
					//println("ST " + register + "," + (sp + LOCAL_RETURN) + "(6)");
					store(atoi(register), currentFrame.getSp() + LOCAL_RETURN);
				}
				
			} else if (cmd.equals("ADD")) {
				
				makeArithmetic("ADD", lineSeg[1]);
				
			} else if (cmd.equals("SUB")) {
				
				makeArithmetic("SUB", lineSeg[1]);
				
			} else if (cmd.equals("MUL")) {
				
				makeArithmetic("MUL", lineSeg[1]);
				
			} else if (cmd.equals("DIV")) {
				
				makeArithmetic("DIV", lineSeg[1]);
				
			} else if (cmd.equals("AND")) {
				
				makeArithmetic("MUL", lineSeg[1]);
			
			} else if (cmd.equals("OR")) {
			// 1 for true and 0 for false. After adding them together, divid by 2.
				operands = lineSeg[1].split(",");
				String leftOp = getRegister(operands[1]);
				
				//println("LD " + leftOp + "," + leftOp + "(6)");
				load(atoi(leftOp), currentFrame.getSp() + atoi(leftOp));
				
				String rightOp = getRegister(operands[2]);
				//println("LD " + rightOp + "," + rightOp + "(6)");
				load(atoi(rightOp), currentFrame.getSp() + atoi(rightOp));
				
				String temp = getRegister(operands[0]);
				
				println("ADD " + temp + "," + leftOp + "," + rightOp);

				println("JEQ " + temp + ",2(7)");   // false
				println("LDC " + temp + ",1(0)");
				println("JGT " + temp + ",1(7)");
				
				println("LDC " + temp + ",0(0)");
				//println("ST " + temp + "," + temp + "(6)");
				store(atoi(temp), currentFrame.getSp() + atoi(temp));
				//println("ST " + temp + "," + LOCAL_RETURN + "(6)");
				store(atoi(temp), currentFrame.getSp() + LOCAL_RETURN);
				
			} else if (cmd.equals("NOT")) {
				
				operands = lineSeg[1].split(",");
				String temp = getRegister(operands[0]);
				println("LDC " + temp + ",1(0)");
	
				String rightOp = getRegister(operands[1]);
				
				println("MUL " + temp + "," + temp + "," + rightOp);
				println("JNE " + temp + "2(7)");  //if jump, now temp = 1 & so right = 1;
				// want temp =0
				
				println("LDC " + temp + "1(0)");  //not jump, now temp = 0 so right = 0;
				// want temp = 1
				
				println("JNE " + temp + "1(7)");  //goto store temp = 1;
				println("LDC " + temp + "0(0)");  //want right = 1, then let temp = 0
				//println("ST " + temp + "," + temp + "(6)");
				store(atoi(temp), currentFrame.getSp() + atoi(temp));
				//println("ST " + temp + "," + LOCAL_RETURN + "(6)");
				store(atoi(temp), currentFrame.getSp() + LOCAL_RETURN);
				
				
			} else if (cmd.equals("EQL")) {
			
				makeArithmetic("SUB", lineSeg[1]);
				
				String temp = getRegister(operands[0]);
				//println("LD " + temp + "," + temp + "(6)");
				load(atoi(temp), currentFrame.getSp() + atoi(temp));
				
				println("JNE " + temp + "," + "3(7)");  //jump 1 instruction if equal
				String one = getRegister(operands[1]); //unlike the abandoned version
				//for NOT, I think it's
				//safe to reuse the register of another operand
//				println("LDC " + one + ",1(0)");
//				println("ADD " + temp + "," + temp + "," + one); //let temp = 0 + 1 = 1
				println("JNE 6,1(7)");
				
				println("SUB " + temp + "," + temp + "," + temp);   //if not equal, temp = 0
				//println("ST " + temp + "," + temp + "(6)");
				store(atoi(temp), currentFrame.getSp() + atoi(temp));
				store(atoi(temp), currentFrame.getSp() + LOCAL_RETURN);
				//println("ST " + temp + "," + LOCAL_RETURN + "(6)");
				
				
			} else if (cmd.equals("LESS_THAN")) { 
				comment("start of <");
				makeArithmetic("SUB", lineSeg[1]);
				
				String temp = getRegister(operands[0]);
				//println("LD " + temp + "," + temp + "(6)");
				load(atoi(temp), currentFrame.getSp() + atoi(temp));
				println("JLT " + temp + "," + "2(7)");  //jump 2 instructions if less than
				//the book has errors for JGE & JEQ... in the table 8-12
				println("SUB " + temp + "," + temp + "," + temp);   //if not less than, temp = 0
				println("JEQ " + temp + "," + "1(7)");
				
				
				println("DIV " + temp + "," + temp + "," + temp); 
				
				//note: temp != 0 here
				//println("ST " + temp + "," + temp + "(6)");
				//println("ST " + temp + "," + LOCAL_RETURN + "(6)");
				store(atoi(temp), currentFrame.getSp() + atoi(temp));
				store(atoi(temp), currentFrame.getSp() + LOCAL_RETURN);
				comment("end of <");
				
			} else if (cmd.equals("IF_NOT")) {
				branchNum = atoi(lineSeg[3].substring(1, lineSeg[3].length()));   
				//get the # of label
				println("LD 0," + (currentFrame.getSp() + LOCAL_RETURN) + "(6)");
				comment("end of the condition");
				condIns[branchNum] = instruction;
//				System.out.println("Branch #" + branchNum);
				instruction++;
				offset[branchNum] = 0;
//				operands = lineSeg[1].split(",");
			
			} else if (cmd.equals("LABEL")) {
				int bNum = atoi(lineSeg[1].substring(1, lineSeg[1].length())); 
				offset[bNum] = instruction - condIns[bNum] - 1;
//				System.out.println("Label #" + bNum);
//				println("LD 0," + LOCAL_RETURN + "(6)");
				System.out.println(condIns[bNum] + ": JEQ 0"
														 + "," + offset[bNum] + "(7)");
			} else if (cmd.equals("IN")) {
				println("IN " + getRegister(lineSeg[1]) + ",0,0");
			} else if (cmd.equals("WRITE")) {
				println("LD 0," + (currentFrame.getSp() + LOCAL_RETURN) + "(6)");
				println("OUT 0,0,0");
			}
//			println(line);
		}
		
//		printPostlude();
	}
	
	private void load(int reg, int mem){
		println("LD " + reg + "," + mem + "(6)");
	}
	
	private void store(int reg, int mem){
		println("ST " + reg + "," + mem + "(6)");
	}
	
	private void makeArithmetic(String operation, String ops) {
		
		operands = ops.split(",");
		String leftOp = getRegister(operands[1]);
		
		//println("LD " + leftOp + "," + leftOp + "(6)");
		load(atoi(leftOp), currentFrame.getSp() + atoi(leftOp));
		String rightOp = getRegister(operands[2]);
		//println("LD " + rightOp + "," + (sp + rightOp) + "(6)");
		load(atoi(rightOp), currentFrame.getSp() + atoi(rightOp));
		
		String temp = getRegister(operands[0]);
		println(operation + " " + temp + "," + leftOp + "," + rightOp);
		//println("ST " + temp + "," + (sp + temp) + "(6)");
		store(atoi(temp), atoi(temp) + currentFrame.getSp());
		//println("ST " + temp + "," + (sp + LOCAL_RETURN) + "(6)");
		store(atoi(temp), currentFrame.getSp() + LOCAL_RETURN);
	}
	
	private void comment(String str){
		System.out.println("*^ " + str);
	}
	
	private int atoi(String str){
		return Integer.parseInt(str);
	}
	
	private String getRegister(String reg) {
		return reg.substring(1,reg.length());
	}
	
	private int matchEntry(String str) {
//		comment(fe[0].getName());
		for (int i = 0; i < func_count; i++) {
			if (fe[i].getName().equals(str)) {
				return i;
			}
		}
		return -1;
	}
	
	private Frame matchFrame(String str) {
		int index = -1;
		for (int i = 0; i < rts.size(); i++) {
			if (rts.get(i).getName().equals(str)) {
				index = i;
			}
		}
		return rts.get(index);
	}
	
	private void JumpToCode(int a, int b)
	{
//		println("LDC 0,0(0)");
		println("JNE 6," + (b - a) + "(7)");
	}
	
	private void printPrelude() {
		// BOOK SAYS LD, IS IT SUPPOSED TO BE LDA?
		println("LDA 6,0(0)");
		println("ST 0,0(0)");
		
	}
	
	
	private void printPostlude() {
		println("HALT 0,0,0");		
	}
	
	private void setPC(int tar, int index) {
		System.out.println(index + ": LDC 7," + tar + "(0)");
	}
	
	private void println(String str) {
		System.out.println(instruction + ": " + str);
		instruction++;
		
//		try {
//			out.write(str + "\n");
//		} catch (IOException e) { } 
		
	}
	
} // END CLASS TinyMachineCodeGenerator