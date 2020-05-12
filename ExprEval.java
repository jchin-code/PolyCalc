import java.util.*;
/**
 * Evaluates all expressions passed into it
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class ExprEval
{
    /**
     * Evaluates an expression passed as an argument.
     * @param input a String, which should be in the form of a Polynomial
     * @return the Polynomial result from the evaluation
     */
    public static Polynomial eval(String input){
        Polynomial result = new Polynomial(), poly1, poly2;
        Deque<Polynomial> evalStack = new ArrayDeque(), 
        infToPostf = InToPost.infixToPostfix(input);
        Object token;
        Iterator iter;
        try{
            iter = infToPostf.iterator();
        } catch (NullPointerException e){ return null;}
        while (iter.hasNext()){
            token = iter.next();
            if (token instanceof Polynomial)
                evalStack.push((Polynomial) token);
            else if (token instanceof Operator){
                try{
                    poly2 = evalStack.pop();
                    poly1 = evalStack.pop();
                    result = ((Operator) token).operate(poly1, poly2);
                    evalStack.push(result);
                } catch (NullPointerException e) {return null;}
            }       
        }
        result = evalStack.pop();
        assert (evalStack.isEmpty());
        return result;
    }

    /**
     * Tests the evaluation method in this class
     */
    public static void test(){
        System.out.println(eval("-2x^2 - 2"));
    }
}