import java.util.*;
/**
 * Evaluates all expressions passed into it
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class ExprEval
{
    public class InToPost
    {
        public class PolyFsm
        {
            String input;

            public PolyFsm(String input){
                this.input = input;
            }

            /**
             * Parses a String input, separating it into Polynomial Objects,
             * Operator Objects, and parentheses.
             * @param input the input String, which should be in the form of a
             * polynomial
             * @return a Deque of tokens, all casted as Objects; null if an error
             * exists
             */
            public Deque tokenList(){
                //Declaration and initialisation of variables.
                //NOTE: exp should NEVER be negative and coeff does not need to be
                //negative since it is handled as an Operator. THEREFORE, a negative
                //value of coeff or exp is an error
                Deque list = new ArrayDeque();
                PolyToken curState = PolyToken.WSIG;
                int i, inputLen = input.length(), opCount, cpCount, exp = -1;
                i = opCount = cpCount = 0;
                float coeff = -1;
                String coeffStr, expStr;
                coeffStr = expStr = "";

                while (curState != PolyToken.ERR && i < inputLen){
                    char c = input.charAt(i++);
                    switch (curState){
                        case WSIG:
                        //For no Whitespaces, if c is Operator and no Operator is
                        //at the end of the Deque, if it is not preceeded by 
                        if (!Character.isWhitespace(c))
                            if (isOperator(c)&&!(list.peekLast() instanceof Operator)){
                                if (isNotPreceded(list.peekLast(),c))
                                    list.add(new Polynomial(0,0));
                                list.add(operatorObject(c));
                            }
                            else if (c=='('){
                                list.add(c);
                                opCount++;
                                curState = PolyToken.OP;
                            }
                            else if (c==')'&&!instOfOperator(list.peekLast())){
                                list.add(c);
                                cpCount++;
                                curState = PolyToken.CP;
                            }
                            else if (Character.isDigit(c)){
                                coeffStr += c;
                                curState = PolyToken.INT;
                            }
                            else if (c=='.'){
                                coeffStr += c;
                                curState = PolyToken.WDEC;
                            }
                            else if (c=='x')
                                curState = PolyToken.WEXP;
                            else
                                curState = PolyToken.ERR;
                        break;
                        case INT:
                        if (Character.isDigit(c))
                            coeffStr += c;
                        else if (c=='x')
                            curState = PolyToken.WEXP;
                        else if (c == '.'){
                            coeffStr += c;
                            curState = PolyToken.WDEC;
                        }
                        else if (!Character.isWhitespace(c)){
                            if (isOperator(c)){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                                list.add(new Polynomial(coeff, 0));
                                list.add(operatorObject(c));
                                curState = PolyToken.WSIG;
                            }
                            else if (c==')'){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                                list.add(new Polynomial(coeff, 0));
                                list.add(c);
                                cpCount++;
                                curState = PolyToken.CP;
                            }
                            else
                                curState = PolyToken.ERR;
                        }  
                        else{
                            coeff = Float.parseFloat(coeffStr);
                            coeffStr = "";
                            list.add(new Polynomial(coeff, 0));
                            curState = PolyToken.WSIG;
                        } 
                        break;
                        case WDEC:
                        if (Character.isDigit(c)){
                            coeffStr += c;
                            curState = PolyToken.DEC;
                        }
                        else 
                            curState = PolyToken.ERR; 
                        break;
                        case DEC:
                        if (Character.isDigit(c))
                            coeffStr += c;
                        else if (c=='x')
                            curState = PolyToken.WEXP;
                        else if (isOperator(c)){
                            if (coeffStr!=""){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                            }
                            else
                                coeff = 1;
                            list.add(new Polynomial(coeff, 0));
                            list.add(operatorObject(c));
                            curState = PolyToken.WSIG;
                        }
                        else if (c==')'){
                            if (coeffStr!=""){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                            }
                            else
                                coeff = 1;
                            list.add(new Polynomial(coeff, 0));
                            list.add(c);
                            cpCount++;
                            curState = PolyToken.CP;
                        }

                        else 
                            curState = PolyToken.ERR;
                        break;
                        case WEXP:
                        if (c=='^')
                            curState = PolyToken.EXP;
                        else if (!Character.isWhitespace(c)){
                            if (isOperator(c)){
                                if (coeffStr!=""){
                                    coeff = Float.parseFloat(coeffStr);
                                    coeffStr = "";
                                }
                                else
                                    coeff = 1;
                                list.add(new Polynomial(coeff, 1));
                                list.add(operatorObject(c));
                                curState = PolyToken.WSIG;
                            }
                            else if (c==')'){
                                if (coeffStr!=""){
                                    coeff = Float.parseFloat(coeffStr);
                                    coeffStr = "";
                                }
                                else
                                    coeff = 1;
                                list.add(new Polynomial(coeff, 1));
                                list.add(c);
                                cpCount++;
                                curState = PolyToken.CP;
                            }
                            else
                                curState = PolyToken.ERR;
                        }
                        else{ 
                            if (coeffStr!=""){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                            }
                            else
                                coeff = 1;
                            list.add(new Polynomial(coeff, 1));
                            curState = PolyToken.WSIG;
                        }
                        break;
                        case EXP:
                        if (Character.isDigit(c))
                            expStr += c;
                        else if (!Character.isWhitespace(c)){
                            if (isOperator(c)){
                                if (coeffStr!=""){
                                    coeff = Float.parseFloat(coeffStr);
                                }
                                else
                                    coeff = 1;
                                exp = Integer.parseInt(expStr);
                                coeffStr = expStr = "";
                                list.add(new Polynomial(coeff, exp));
                                list.add(operatorObject(c));
                                curState = PolyToken.WSIG;
                            }
                            else if (c==')'){
                                if (coeffStr!=""){
                                    coeff = Float.parseFloat(coeffStr);
                                }
                                else
                                    coeff = 1;
                                exp = Integer.parseInt(expStr);
                                coeffStr = expStr = "";
                                list.add(new Polynomial(coeff, exp));
                                list.add(c);
                                cpCount++;
                                curState = PolyToken.CP;
                            }
                            else
                                curState = PolyToken.ERR;
                        }
                        else if (expStr!= ""){
                            if (coeffStr!=""){
                                coeff = Float.parseFloat(coeffStr);
                            }
                            else
                                coeff = 1;
                            exp = Integer.parseInt(expStr);
                            coeffStr = expStr = "";
                            list.add(new Polynomial(coeff, exp));
                            curState = PolyToken.WSIG;
                        }
                        else
                            curState = PolyToken.ERR;

                        break;
                        case OP:
                        if (!Character.isWhitespace(c))
                            if (c=='('){
                                list.add(c);
                                opCount++;
                            }
                            else if (c=='x')
                                curState = PolyToken.WEXP;
                            else if (c=='.')
                                curState = PolyToken.DEC;
                            else if (Character.isDigit(c)){
                                coeffStr += c;
                                curState = PolyToken.INT;
                            }
                            else if (c=='+'||c=='-'){
                                list.add(new Polynomial(0,0));
                                list.add(operatorObject(c));
                                curState = PolyToken.WSIG;
                            }
                            else
                                curState = PolyToken.ERR;
                        break;
                        case CP:
                        if (!Character.isWhitespace(c))
                            if (c==')'){
                                list.add(c);
                                cpCount++;
                            }
                            else if (isOperator(c)){
                                list.add(operatorObject(c));
                                curState = PolyToken.WSIG;
                            }
                            else 
                                curState = PolyToken.ERR;
                        break;
                    }

                }
                if (curState!=PolyToken.WSIG&&curState.isGood()&&curState!=PolyToken.CP){
                    if (curState==PolyToken.INT||curState==PolyToken.DEC){
                        coeff = Float.parseFloat(coeffStr);
                        exp = 0;
                    }
                    else if (curState==PolyToken.WEXP){
                        if (coeffStr!=""){
                            coeff = Float.parseFloat(coeffStr);
                            coeffStr = "";
                        }
                        else
                            coeff = 1;
                        exp = 1;
                    }
                    else if (curState==PolyToken.EXP){
                        if (expStr!=""){
                            if (coeffStr!=""){
                                coeff = Float.parseFloat(coeffStr);
                                coeffStr = "";
                            }
                            else
                                coeff = 1;
                            exp = Integer.parseInt(expStr);
                        }
                        else
                            return null;
                    }
                    list.add(new Polynomial(coeff,exp));
                }

                if (!curState.isGood()||opCount!=cpCount)
                    list = null;
                return list;
            }

            /** 
             * Checks to see if object is a close bracket.
             * @param o an Object
             * @return true if it is an close bracket; false otherwise
             */
            private boolean instOfCloseBrac(Object o){
                if (o==null)
                    return false;
                else
                    return o.equals(')');
            }

            /**
             * Checks if object is an operator
             * @param o an Object
             * @return true if the Object is an operator by character; false
             * otherwise
             */
            private boolean instOfOperator(Object o){
                if (o==null)
                    return false;
                else
                    return o.equals('+')||o.equals('-')||o.equals('*')||o.equals('/');
            }

            /**
             * Checks if Object is a Polynomial Object
             * @param o an Object
             * @return true if the Object is a Polynomial Object; false otherwise
             */
            private boolean instOfPoly(Object o){
                return o instanceof Polynomial;
            }

            /**
             * Checks if the character is an operator
             * @param c a Character
             * @return true if c is an operator character; false otherwise
             */
            private boolean isOperator(Character c){
                return c=='+'||c=='-'||c=='*'||c=='/';
            }

            /**
             * Replaces operator characters with Operator Objects
             * @param c the character sign of the Operators
             * @return the Operator which the character sign belongs to
             */
            private Operator operatorObject(Character c){
                if (!isOperator(c))
                    return null;

                if (c=='+')
                    return new Addition();
                else if (c=='-')
                    return new Subtraction();
                else if (c=='*')
                    return new Multiplication();
                else 
                    return new Division();
            }

            /**
             * Checks if the character is not preceeded by any close bracket or
             * Polynomial
             * @param o an Object
             * @param c a Character
             * @return true if the character is not precceded; false otherwise
             */
            private boolean isNotPreceded(Object o,Character c){
                return (instOfCloseBrac(o)||!instOfPoly(o))&&(c=='+'||c=='-');
            }

            /**
             * Tests the Finite State Machine
             */
            public void test(){
                PolyFsm a = new PolyFsm("x^2+3.0+2.0x^2+x");
                System.out.println(a.tokenList()); }
        }

        String input;
        
        public InToPost(String input){
            this.input = input;
        }
        
        
        /**
         * Converts a parsed Deque into one with a postfix notation, like
         *          a b +           instead of              a + b.
         * This helps the computer to understand priority where mathematical
         * operations are concerned, and easier to compute.
         * @param input the String, which should be presented as a Polynomial
         * @return a Deque containing the postfix notation of the Polynomial
         */
        public Deque infixToPostfix()
        {
            PolyFsm fsm = new PolyFsm(input);
            Deque newList = new ArrayDeque(), fsmList = fsm.tokenList();
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
        public void test(){
            System.out.println(
            (new InToPost("-2x^2 + - 4 2")).infixToPostfix());
        }
    }

    String input;
    
    public ExprEval(String input){
        this.input = input;
    }
    
    /**
     * Evaluates an expression passed as an argument.
     * @param input a String, which should be in the form of a Polynomial
     * @return the Polynomial result from the evaluation
     */
    public Polynomial eval(){
        Polynomial result = new Polynomial(), poly1, poly2;
        InToPost iToP = new InToPost(input);
        Deque<Polynomial> evalStack = new ArrayDeque(); 
        Deque infToPostf = iToP.infixToPostfix();
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
        System.out.println((new ExprEval("-2x^2 - 2")).eval());
    }
}