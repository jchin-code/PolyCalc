
/**
 * Class containing information for Subtraction
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class Subtraction extends Operator
{
    /**
     * Creates Subtraction objects with the priority and sign embedded
     */
    public Subtraction()
    {
        priority = 1;
        sign = '-';
    }
    
    /**
     * Checks if Object is Subtraction Object by comparing signs.
     * @param o an Object, more than likely one that can be downcasted to a
     * char
     * @return true if object has the sign of an Subtraction Object; false
     * otherwise
     */
    public boolean isSub(Object o){
        return o.equals(this.getSign());
    }
}
