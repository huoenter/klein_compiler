import java.util.ArrayList;

public class SemanticLookup {

	private static SemanticLookup sl = null;
	private static ArrayList<String> names;
	private static ArrayList<SemanticAction> action;

	private SemanticLookup() {
	
		names = loadNames();
		action = loadActions();
	
	} // end constructor

	
	public static SemanticLookup getSemanticLookup() {
	
		if (sl == null) {
			sl = new SemanticLookup();
		}
		
		return sl;
		
	} // end getSemanticLookup()
	
	
	public static SemanticAction getAction(String name) {
		
		SemanticAction sa = null;
		
		int index = names.indexOf(name);
		
		if (index != -1) {
			
			try {
				
				sa = (action.get(index)).getClass().newInstance();
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sa;
		
	} // end getAction()


	private ArrayList<String> loadNames() {
	
		ArrayList<String> al = new ArrayList<String>();
		
		al.add("make-and-expr");
		al.add("make-bool");
		al.add("make-bool-decl");
		al.add("make-def");
		al.add("make-divide-expr");
		al.add("make-equals-expr");
		al.add("make-formal");
		al.add("make-identifier");
		al.add("make-if");
		al.add("make-int");
		al.add("make-int-decl");
		al.add("make-less-than-expr");
		al.add("make-minus-expr");
		al.add("make-not-expr");
		al.add("make-or-expr");
		al.add("make-plus-expr");
		al.add("make-print-stmt");
		al.add("make-program");
		al.add("make-times-expr");
		al.add("make-literal");
		al.add("make-body");
		al.add("make-formals");
		al.add("make-function-call");
		al.add("make-actual");
		al.add("make-actuals");
		
		return al;
		
	} // end loadNames()
		
	
	private ArrayList<SemanticAction> loadActions() {
	
		ArrayList<SemanticAction> al = new ArrayList<SemanticAction>();
		
		al.add(new MakeAndExpr());
		al.add(new MakeBool());
		al.add(new MakeBoolDecl());
		al.add(new MakeDef());
		al.add(new MakeDividesExpr());
		al.add(new MakeEqualsExp());
		al.add(new MakeFormal());
		al.add(new MakeIdentifier());
		al.add(new MakeIf());
		al.add(new MakeInt());
		al.add(new MakeIntDecl());
		al.add(new MakeLessThanExpr());
		al.add(new MakeMinusExpr());
		al.add(new MakeNotExpr());
		al.add(new MakeOrExpr());
		al.add(new MakePlusExpr());
		al.add(new MakePrintStatement());
		al.add(new MakeProgram());
		al.add(new MakeTimesExpr());
		al.add(new MakeLiteral());
		al.add(new MakeBody());
		al.add(new MakeFormals());
		al.add(new MakeFunctionCall());
		al.add(new MakeActual());
		al.add(new MakeActuals());
		
		return al;
		
	} // end loadActions()
			

} // END CLASS SemanticLookup