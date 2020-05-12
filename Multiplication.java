
/**
 * Class containing information for Multiplication
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class Multiplication extends Operator
{
    /**
     * Creates Multiplication objects with the priority and sign embedded
     */
    public Multiplication()
    {
        priority = 2;
        sign = '*';
    }
    
    /**
     * Checks if Object is Multiplication Object by comparing signs.
     * @param o a Object, more than likely one that can be downcasted to a
     * char
     * @return true if object has the sign of an Multiplication Object; false
     * otherwise
     */
    public boolean isMult(Object o){
        return o.equals(this.getSign());
    }
}
