import java.util.*;
/**
 * Goes through the parsed collection to rearrange items into a postfix
 * notation.
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class InToPost
{
    /**
     * Converts a parsed Deque into one with a postfix notation, like
     *          a b +           instead of              a + b.
     * This helps the computer to understand priority where mathematical
     * operations are concerned, and easier to compute.
     * @param input the String, which should be presented as a Polynomial
     * @return a Deque containing the postfix notation of the Polynomial
     */
    public static Deque infixToPostfix(String input)
    {
        PolyFsm fsm = new PolyFsm();
        Deque newList = new ArrayDeque(), fsmList = fsm.tokenList(input);
        Deque opList = new ArrayDeque();
        Object token;
        Iterator iter;

        try{
            iter = fsmList.iterator();
        } catch (NullPointerException e){return null;}
        while (iter.hasNext()){
            token = iter.next();
            if (token instanceof Polynomial)
                newList.add(token);
            else if (token.equals('('))
                opList.push(token);
            else if (token.equals(')')){
                while (!opList.isEmpty()&&!opList.peek().equals('('))
                    newList.add(opList.pop());
                opList.pop();
            }
            else if (token instanceof Operator){
                Operator tok = (Operator) token;
                while (!opList.isEmpty()&&!opList.peek().equals('(')
                &&(((Operator) opList.peek()).getPriority()>=tok.getPriority()))
                    newList.add((Operator) opList.pop());
                opList.push(tok);
            }
        }
        while (!opList.isEmpty())
            newList.add((Operator) opList.pop());
        return newList;
    }

    /**
     * Tests the postfix method
     */
    public static void test(){
        System.out.println(infixToPostfix("-2x^2 + - 4 2"));
    }
}
