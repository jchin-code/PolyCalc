
/**
 * Class containing information for Division
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class Division extends Operator
{
    /**
     * Creates Division objects with the priority and sign embedded
     */
    public Division()
    {
        priority = 2;
        sign = '/';
    }
    
    /**
     * Checks if Object is Division Object by comparing signs.
     * @param o a Object, more than likely one that can be downcasted to a
     * char
     * @return true if object has the sign of an Division Object; false
     * otherwise
     */
    public boolean isDiv(Object o){
        return o.equals(this.getSign());
    }
}
