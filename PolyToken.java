
/**
 * Contains all of the different states for the finite state machine, in order
 * to properly parse a string into operators and operands
 *
 * @author Jordan Chin
 * @version 1.0
 */
public enum PolyToken
{
    WSIG(true), WINT(false), INT(true), WDEC(false), DEC(true), WEXP(true),
    EXP(true), WOP(false), OP(false), WCP(false), CP(true), END(true),
    ERR(false);
    
    private final boolean isGood;
    /** Assigns boolean to Object.*/
    PolyToken(boolean isGood){this.isGood = isGood;}
    /** Indication the boolean for a state @return the boolean of state called*/
    public boolean isGood(){return isGood;}
}
