
/**
 * Class containing information for Addition
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class Addition extends Operator
{
    /**
     * Creates Addition objects with the priority and sign embedded
     */
    public Addition()
    {
        priority = 1;
        sign = '+';
    }
    
    /**
     * Checks if Object is Additon Object by comparing signs.
     * @param o an Object, more than likely one that can be downcasted to a
     * char
     * @return true if object has the sign of an Addition Object; false
     * otherwise
     */
    public boolean isAdd(Object o){
        return o.equals(this.getSign());
    }
}
