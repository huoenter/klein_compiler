import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ThreeCodeGenerator {

	private SymbolTable symbolTable;
	private ArrayList<String> tempRegisters;
	private int labelCount;
	private BufferedWriter out;

	ThreeCodeGenerator(SymbolTable sTable) {
		
		symbolTable = sTable;
		tempRegisters = initializeTempRegisters(20);
		labelCount = 0;
		
		try {
			out = new BufferedWriter(new FileWriter("three_address_code.txt"));
		} catch (IOException e) { e.printStackTrace(); }
		
	} // end constructor
	
		
	public void check(Program program) throws Exception {
		
		Def[] definitions = program.definitions();
		
		for (int i = 0; i < definitions.length; i++) {
			
			checkDef(definitions[i]);
		}
		
		out.close();
		
	}
	
	
	public void checkDef(Def def) throws Exception {
		
		Formal[] formals = def.formal().formals();
		if (def.identifier().toString().equals("main"))
		{
			for (int i=0; i<formals.length; i++)
			{
				println("IN " + getTempRegister() + ",0,0");
			}
		}
		
		println("ENTRY " + def.identifier());
		
		for (int i=0; i<formals.length; i++) {
			println("PARAM " + formals[i].identifier());
		}
		
		checkPrint(def.body().printStatements(), def.identifier().idvalue());
		
		checkBody(def.body(), DataType.Integer, def.identifier().idvalue());
		
		println("END " + def.identifier());
					
	}
	
	
	public String checkPrint(PrintStatement[] ps, String defName) throws Exception {
		
		for (int i=0; i<ps.length; i++) {
		
			Actual[] actuals = ps[i].actuals().actuals();
			
			for (int x = 0; x<actuals.length; x++) {
			
					String actualParam = checkExpression(actuals[x], defName);
					println("WRITE " + actualParam);
					freeTempRegister(actualParam);
			
			}
			
		}
		
// 		for (int i=0; i<ps.length; i++) {
// 			String tempOne = this.getTempRegister();
// 			String actualParam = checkExpression(ps[i].actuals(), defName);
// 			println(tempOne + " := " + actualParam);
// 			println("WRITE " + tempOne);
// 			freeTempRegister(actualParam);
// 			freeTempRegister(tempOne);
// 			
// 		}
		
		return "";
	}

	
	public void checkBody(Body body, String type, String defName) throws Exception {
		
		Expression[] exprs = body.expressions();
		String temp = "";
			
		for (int i = 0; i < exprs.length; i++) {
			temp = checkExpression(exprs[i], defName);
			
			if (!isEmptyString(temp)) {
				println("RETURN " + temp);
			}
			
		}
	}

	
	public String checkExpression(Expression expr, String defName) throws Exception {
		
		if (expr instanceof Identifier) {
			
			Identifier id = (Identifier)expr;			
			String tempOne = getTempRegister();
			println(tempOne + " := " + id.idvalue());
								return tempOne;
			
		} else if (expr instanceof IntegerValue) {
			
			IntegerValue i = (IntegerValue) expr;
			String tempOne = getTempRegister();
			println(tempOne + " := " + i.toString());

			return tempOne;
			
		} else if (expr instanceof BooleanValue) {
			
			BooleanValue b = (BooleanValue) expr;
			String tempOne = getTempRegister();
			println(tempOne + " := " + b.toString());
		
			return tempOne;
			
		}
		 else if (expr instanceof PlusExpr) {
						
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			
			String tempOne = checkExpression(left, defName);
			String tempTwo = checkExpression(right, defName);
			String tempThree = getTempRegister();

			println("ADD " + tempThree + "," + tempOne + "," + tempTwo);
			freeTempRegister(tempOne);
			freeTempRegister(tempTwo);
					
			return tempThree;
			
		} else if (expr instanceof MinusExpr) {
			
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			
			String tempOne = checkExpression(left, defName);
			String tempTwo = checkExpression(right, defName);
			String tempThree = getTempRegister();
			
			println("SUB " + tempThree + "," + tempOne + "," + tempTwo);
			freeTempRegister(tempOne);
			freeTempRegister(tempTwo);
				
			return tempThree;

		} else if (expr instanceof TimesExpr) {
			
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			
			String tempOne = checkExpression(left, defName);
			String tempTwo = checkExpression(right, defName);
			String tempThree = getTempRegister();
			
			println("MUL " + tempThree + "," + tempOne + "," + tempTwo);
			freeTempRegister(tempOne);
			freeTempRegister(tempTwo);
					
			return tempThree;
			
		} else if (expr instanceof DividesExpr) {
			
			Expression left = expr.leftOperand();
			Expression right = expr.rightOperand();
			
			String tempOne = checkExpression(left, defName);
			String tempTwo = checkExpression(right, defName);
			String tempThree = getTempRegister();
			
			println("DIV " + tempThree + "," + tempOne + "," + tempTwo);
			freeTempRegister(tempOne);
			freeTempRegister(tempTwo);
			
			return tempThree;
			
		} else if (expr instanceof EqualsExpr) {
			
			Expression left = expr.leftOperand();
			String leftHand = checkExpression(left, defName);
			
			Expression right = expr.rightOperand();
			String rightHand = checkExpression(right, defName);

			String tempOne = getTempRegister();
			
			println("EQL " + tempOne + "," + leftHand + "," + rightHand);
			freeTempRegister(leftHand);
			freeTempRegister(rightHand);
			
			return tempOne;
			
		} else if (expr instanceof AndExpr) {
			
			Expression left = expr.leftOperand();
			String leftHand = checkExpression(left, defName);
			
			Expression right = expr.rightOperand();
			String rightHand = checkExpression(right, defName);

			String tempOne = getTempRegister();
			
			println("AND " + tempOne + "," + leftHand + "," + rightHand);
			freeTempRegister(leftHand);
			freeTempRegister(rightHand);
					
			return tempOne;
			
		} else if (expr instanceof OrExpr) {
			
			Expression left = expr.leftOperand();
			String leftHand = checkExpression(left, defName);
			
			Expression right = expr.rightOperand();
			String rightHand = checkExpression(right, defName);

			String tempOne = getTempRegister();
			
			println("OR " + tempOne + "," + leftHand + "," + rightHand);
			freeTempRegister(leftHand);
			freeTempRegister(rightHand);
					
			return tempOne;
	
		} else if (expr instanceof NotExpr) {
			
			NotExpr notExpr = (NotExpr) expr;
			String rightHand = checkExpression(notExpr.expression(), defName);
			
			String tempOne = getTempRegister();
			
			println("NOT " + tempOne + "," + rightHand);
			freeTempRegister(rightHand);
					
			return tempOne;
		
		} else if (expr instanceof LessThanExpr) {
			
			Expression left = expr.leftOperand();
			String leftHand = checkExpression(left, defName);
			
			Expression right = expr.rightOperand();
			String rightHand = checkExpression(right, defName);

			String tempOne = getTempRegister();
			
			println("LESS_THAN " + tempOne + "," + leftHand + "," + rightHand);
			freeTempRegister(leftHand);
			freeTempRegister(rightHand);
					
			return tempOne;
			
		} else if (expr instanceof FunctionCall) {
			
			FunctionCall function = (FunctionCall) expr;
			Identifier funcname = function.getName();
			
			Actual[] actuals = function.getActuals().actuals();
			String[] tempRegisters = new String[actuals.length];
			
			for (int i = 0; i < actuals.length; i++) {
				tempRegisters[i] = checkExpression(actuals[i].expression(), defName);
			}
			
			for (int i = 0; i < tempRegisters.length; i++) {
				println("ACTUAL PARAM " + tempRegisters[i]);
				freeTempRegister(tempRegisters[i]);
			}
			
			println("CALL " + funcname.idvalue());
				
			return "";

		} else if (expr instanceof IfStatement) {
			
			IfStatement ifStatement = (IfStatement)expr;
			String label = makeLabel();
			
			String ifExpr = checkExpression(ifStatement.ifExpression(), defName);
			
			println("IF_NOT " + ifExpr + " GOTO " + label);
			freeTempRegister(ifExpr);
			
			String thenExpr = checkExpression(ifStatement.thenExpression(), defName);
			
			if (!isEmptyString(thenExpr)) {
				println("RETURN " + thenExpr);
			} else {
				println("RETURN T0");
			}

			freeTempRegister(thenExpr);
			
			println("LABEL " + label);
			String elseExpr = checkExpression(ifStatement.elseExpression(), defName);
			
			if (!isEmptyString(elseExpr)) {
				println("RETURN " + elseExpr);
			} else {
				println("RETURN T0");
			}
	
			freeTempRegister(elseExpr);
			
			return "";
			
		}  else if (expr instanceof Actual) {
			
			Actual actual = (Actual) expr;
			return checkExpression(actual.expression(), defName);
			
		} 
		else if (expr instanceof Actuals)
		{
			Actual[] actuals = ((Actuals)expr).actuals();
			String tempOne = "";
			for (int i=0; i < actuals.length; i++)
			{
				tempOne = checkExpression(actuals[i], defName);
			}
			return tempOne;
		}
		else {
			out.close();
			throw new Exception("Invalid expression found." + expr.toString());
		}
													
	}
	
	
	private ArrayList<String> initializeTempRegisters(int size) {

		ArrayList<String> al = new ArrayList<String>();
		for (int i=0; i<size; i++) {
			al.add("");
		}
		
		return al;
	}
	
	
	private String getTempRegister() {
		int pos = tempRegisters.indexOf("");
		tempRegisters.set(pos, ("T" + pos));
		return tempRegisters.get(pos);
	}
	
	
	private void freeTempRegister(String tr) {
		tempRegisters.set(tempRegisters.indexOf(tr), "");
	}
	
	
	private void print(String str) {
		System.out.print(str);
	}

	
	private void println(String str) {
//		System.out.println(str);
		
		try {
			out.write(str + "\n");
		} catch (IOException e) { } 
		
	}
	
	
	private String makeLabel() {
		String label = "L" + labelCount;
		labelCount++;
		return label;
	}
	
	
	private boolean isEmptyString(String str) {
		return (str.trim()).equals("");
	}
} // END CLASS ThreeCodeGenerator