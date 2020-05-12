
/**
 * Super class for legitimate operators of Polynomials.
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class Operator
{
    //priority and sign of operator declared
    public int priority;
    public char sign;

    /**
     * Gets the priority of the Operator.
     * @return the priority of the Operator.
     */
    public int getPriority(){
        return this.priority;
    }

    /**
     * Gets the sign of the Operator.
     * @return the sign of the Operator.
     */
    public char getSign(){
        return this.sign;
    }

    /**
     * Operates on two Polynomials
     * @param p1 the first Polynomial
     * @param p2 the second Polynomial
     * @return the result of the operation on the two Polynomials
     */
    public Polynomial operate(Polynomial p1, Polynomial p2){
        if (this instanceof Addition) //if Operator is Add, add
            return p1.add(p2);
        else if (this instanceof Subtraction) //if it is Subtract, subtract
            return p1.subtract(p2);
        else if (this instanceof Multiplication) //if it is multiply, mult
            return p1.mult(p2);
        else if (this instanceof Division) //if it is divide, divide
            return p1.div(p2);
        else
            return null; //if it is none of these, return a null
    }
}
