import java.util.*;
/**
 * Creates a polynomial expression as a collection of monomials. Polynomials
 * may have one or more terms.
 *
 * @author Jordan Chin
 * @version 1.1
 */
public class Polynomial
{
    public TreeMap<Integer,Float> poly;

    /**
     * Constructs an empty Polynomial object, according to the order of
     * exponents, highest to lowest.
     */
    public Polynomial()
    {
        //Creates TreeMap in reverse order of integers to hold exponents
        //(keys) and coefficients (values)
        poly = new TreeMap<Integer,Float>(Collections.reverseOrder());
    }

    /**
     * Constructs a Polynomial object, with one term in it. This is also
     * according to the order of exponents, highest to lowest.
     * @param coeff the coefficient
     * @param exp the exponent
     */
    public Polynomial(float coeff, int exp){
        //Creates TreeMap in reverse order of integers to hold exponents
        //(keys) and coefficients (values), and adds parameters to the
        //TreeMap, thus creating a monomial.
        poly = new TreeMap<Integer,Float>(Collections.reverseOrder());
        poly.put(exp,coeff);
    }

    /**
     * Constructs a copy of the Polynomial object passed into it.
     * @param a the Polynomial object to be copied
     */
    public Polynomial(Polynomial a){
        //Creates TreeMap in reverse order of integers to hold exponents
        //(keys) and coefficients (values), and adds each mapping to the new
        //TreeMap.
        poly = new TreeMap<Integer,Float>(Collections.reverseOrder());
        for (Map.Entry<Integer,Float> ent : a.poly.entrySet())
            poly.put(ent.getKey(),ent.getValue());
    }

    /**
     * Tests other Polynomial objects.
     */
    public static void test(){
        Polynomial a = new Polynomial(4,3);
        Polynomial b = new Polynomial(1,1);
        Polynomial c = new Polynomial(a);
        
        System.out.println("b = " + b);
        // System.out.println("c = " + c);

        // Polynomial d = a.mult(c);
        // System.out.println("d = " + d);
        // System.out.println("a = " + a);
        // System.out.println("c = " + c);
        // System.out.println(a.equals(c));
    }

    /**
     * Creates the String representation of the Polynomial. Each term in the
     * Polynomial is in this format:
     *             +/- ax^b             where a - the coefficient
     *                                        b - the exponent
     * @return the String representation of the Polynomial.
     */
    public String toString(){
        // intitialise all of the variables used in the making of the String,
        //and the calling of the TreeMap representation of 'this' Polynomial
        //object
        String s = "";
        char var = 'x';
        int counter = 0, exp;
        float coeff;
        TreeMap<Integer,Float> poly = this.poly;
        String coeffString, toExp, mono;
        //For loop goes through all Map entries in TreeMap
        for (Map.Entry<Integer,Float> ent : poly.entrySet()){
            //Coefficient and exponent for each entry intiialised
            coeff = ent.getValue();
            exp = ent.getKey();
            //Format for generic term initialized.
            coeffString = coeff + "";
            toExp = var + "^" + exp;
            //Special cases for terms
            if (coeff==0){//if coefficient is 0, term is 0
                coeffString = "0";
                toExp =  "";
            }
            else if (exp==0)//if the exponent is 0, it is just a number and
            //and has no x-component to it.
                toExp = "";
            else if (exp==1)//if the expoenent is 1, do not return the carat
            //'^' or the value of the exponent '1'.
                toExp = var + "";
            if (Math.abs(coeff)==1)//if the exponent is 1, do not
            //return the 1 as a part of the
            //coefficient, but just its sign
                if (((int) Math.signum(coeff))==1)//if the 1 is
                //positive, do not put a
                //sign (will be dealt
                //with later)
                    coeffString = "";
                else//otherwise, return a negative sign (without the 1)
                    coeffString = "-";
            //format for creating each term
            mono = coeffString + "" + toExp;
            //checks if the number is positive and if the counter is not
            //zero. Counter is used only for the first term, so that
            //positive terms do not have the positive sign on them when they
            //are passed through.
            if (((int) Math.signum(coeff))==1&&counter!=0)
                s += "+" + mono;
            else{
                s += mono;
                counter++;
            }
        }
        //String returned
        return s;
    }

    /**
     * Gets the TreeMap embedded within the Polynomial.
     * @return the TreeMap of the Polynomial.
     */
    public TreeMap getPoly(){
        return this.poly;
    }

    /**
     * Sets the Polynomial called to the one being passed as an argument by
     * referencing the TreeMap of the argument.
     * @param poly a Polynomial that will be referenced
     */
    public void setPoly(Polynomial poly){
        this.poly = poly.poly;
        return;
    }

    /**
     * Compares two Polynomials to see if they are equal. Two Polynomials
     * are considered equal if their TreeMaps are equal.
     * @param obj an Object
     * @return true if the Polynomials are the equal; false otherwise
     */
    public boolean equals(Object obj){
        //checks if the object is of the Polynomial class; returns false if
        //not
        if (obj instanceof Polynomial){//downcasts object to a Polynomial
            //returns true if the TreeMaps
            //are equal
            Polynomial other = (Polynomial) obj;
            return this.getPoly().equals(other.getPoly());
        }
        else
            return false;
    }

   /**
    * Tests the methods in the Polynomial class.
    * @param args arguments of method
    */
    public static void main(String[] args){
        Polynomial p = new Polynomial(1,2); //p = x^2
        Polynomial q = new Polynomial(3,0); //q = 3
        Polynomial r = new Polynomial(0,3);
        Polynomial c = p.add(q);
        System.out.println(r);
        System.out.println("Add: \nc: " +c);
        System.out.println("p: " +p);
        System.out.println("q: " +q+"\n");
        Polynomial d = c.subtract(q);
        System.out.println("Subtract: \nd: " +d);
        System.out.println("p: " +p);
        System.out.println("q: " +q+"\n");
        // System.out.println(p.equals(p));
        Polynomial e = c.mult(q);
        System.out.println("Multiply: \ne: " +e);
        System.out.println("p: " +p);
        System.out.println("q: " +q+"\n");
        Polynomial f = e.div(q);
        System.out.println("Divide: \nf: " +f);
        System.out.println("p: " +p);
        System.out.println("q: " +q+"\n");
        return;
    }

    /**
     * Adds two Polynomials together, returning the result from the operation.
     * @param poly the Polynomial which is being added to the one being
     * called.
     * @return the Polynomial created from the addition.
     */
    public Polynomial add(Polynomial poly){
        //initialise the result, which will hold the Polynomial,
        // and the TreeMaps for all three Polynomials involved.
        Polynomial result = new Polynomial();
        TreeMap<Integer,Float> a = this.getPoly(), b = poly.getPoly();
        TreeMap<Integer,Float> c = result.getPoly();
        //Initialise the keySets for the two Polynomials to be added, in
        //addition to the float objects for each of the values of the
        //Polynomials and the one to be added.
        Set<Integer> aExp = a.keySet(), bExp = b.keySet();
        Float aVal, bVal, add;
        //For each exponent in a, if there is a corresponding exponent in b,
        //add the coefficients and add it to the result TreeMap. Otherwise,
        //add the coefficient in a to the result TreeMap.
        //Note: If any coefficient result is 0, it will not be added to the
        //TreeMap, unless it is 0 itself.
        for (Integer exp : aExp)
            if (b.containsKey(exp)){
                aVal = a.get(exp);
                bVal = b.get(exp);
                add = new Float(aVal+bVal);
                if (add==0&&exp!=0)
                    continue;
                c.put(exp,add);
            }
            else{
                aVal =  a.get(exp);
                add = new Float(aVal);
                if (add==0&&exp!=0)
                    continue;
                c.put(exp,add);
            }

        //For each exponent in b, if it is not in a, add it to the result
        //TreeMap.
        //Note: If any coefficient result is 0, it will not be added to the
        //TreeMap, unless it is 0 itself
        for (Integer exp : bExp)
            if (!a.containsKey(exp)){
                bVal = b.get(exp);
                add = new Float(bVal);
                if (add==0&&exp!=0)
                    continue;
                c.put(exp,add);
            }

        //result return
        return result;
    }

    /**
     * Subtracts two Polynomials from each other, returning the result from
     * the operation.
     * @param poly the subtractor; the one with the minus '-' sign before it.
     * @return the Polynomial created from the subtraction.
     */
    public Polynomial subtract(Polynomial poly){
        //initialise the result, which will hold the Polynomial,
        // and the TreeMaps for all three Polynomials involved.
        Polynomial result = new Polynomial();
        TreeMap<Integer,Float> a = this.getPoly(), b = poly.getPoly();
        TreeMap<Integer,Float> c = result.getPoly();
        //Initialise the keySets for the two Polynomials to be subtracted, in
        //addition to the float objects for each of the values of the
        //Polynomials and the one to be subtracted.
        Set<Integer> aExp = a.keySet(), bExp = b.keySet();
        Float aVal, bVal, sub;
        //For each exponent in a, if there is a corresponding exponent in b,
        //subtract the coefficients and add it to the result TreeMap. 
        //Otherwise, add the coefficient in a to the result TreeMap.
        //Note: If any coefficient result is 0, it will not be added to the
        //TreeMap, unless it is 0 itself.
        for (Integer exp : aExp)
            if (b.containsKey(exp)){
                aVal = a.get(exp);
                bVal = b.get(exp);
                sub = new Float(aVal-bVal);
                if (sub==0&&exp!=0)
                    continue;
                c.put(exp,sub);
            }
            else{
                aVal = a.get(exp);
                sub = new Float(aVal);
                if (sub==0&&exp!=0)
                    continue;
                c.put(exp,sub);
            }

        //For each exponent in b, if it is not in a, add the negative 
        //coefficient to the result TreeMap.
        //Note: If any coefficient result is 0, it will not be added to the
        //TreeMap, unless it is 0 itself 
        for (Integer exp : bExp)
            if (!a.containsKey(exp)){
                bVal = b.get(exp);
                sub=new Float(-bVal);
                if (sub==0&&exp!=0)
                    continue;
                c.put(exp,sub);
            }

        //result returned
        return result;
    }

    /**
     * Multiplies two Polynomials together, returning the result from the
     * operation.
     * @param poly the Polynomial which is being multiplied to the one being
     * called.
     * @return the Polynomial created from the multiplication.
     */
    public Polynomial mult(Polynomial poly){
        //initialise the result, which will hold the Polynomial, and a
        //temporary Polynomial,  and the TreeMaps for all four Polynomials
        //involved.
        Polynomial result = new Polynomial(), temp;
        TreeMap<Integer,Float> a = this.getPoly(), b = poly.getPoly();
        TreeMap<Integer,Float> c = result.getPoly(), tem;
        //Initialise the keySets for the two Polynomials to be added, in
        //addition to the float objects for each of the values of the
        //Polynomials and the one to be multiplied.
        Set<Integer> aExp = a.keySet(), bExp = b.keySet();
        Float aVal, bVal, mul;
        Integer addInt;
        for (Integer exp : aExp){
            temp = new Polynomial();
            tem = temp.getPoly();
            for (Integer bEx : bExp){
                aVal = a.get(exp);
                bVal = b.get(bEx);
                mul = new Float(aVal*bVal);
                if (mul==0&&exp!=0)
                    continue;
                addInt = new Integer(exp+bEx);
                tem.put(addInt,mul);
            }
            result = result.add(temp);
        }
        return result;
    }

    /**
     * Divides two Polynomials together, returning the result from the
     * operation.
     * @param poly the Polynomial which is the divisor to the one being
     * called.
     * @return the Polynomial created from the division.
     */
    public Polynomial div(Polynomial poly){//Make more efficient
        //Polynomials intialised and declared
        Polynomial result = new Polynomial(), remain = new Polynomial();
        Polynomial d = new Polynomial(), e, t;
        remain = remain.add(this);
        d = d.add(poly);
        //checks if divisor is zero; if it is, return null
        if (d.isZero())
            return null;
        //checks if the dividend is zero: if it is, return zero polynomial
        if (this.isZero())
            return new Polynomial(0,0);
        //Extract TreeMap and other variables from Polynomials
        TreeMap<Integer,Float> r = remain.getPoly(), b = poly.getPoly();
        Integer bHighKey = b.firstKey(), remKey = r.firstKey();
        Float rVal, bVal, add;
        Integer addInt;
        //Continues as long as remainder is not zero and highest power of
        //remainder is >= than highest power of divisor.
        while (!remain.isZero()&&remKey>=bHighKey){

            e = new Polynomial();
            rVal = r.get(remKey);
            bVal = b.get(bHighKey);
            add = new Float(rVal/bVal);
            addInt = new Integer(remKey-bHighKey);
            t = new Polynomial(add,addInt);
            result = result.add(t);
            e = t.mult(d);
            remain = remain.subtract(e);
            r = remain.getPoly();
            if (r.isEmpty())
                break;
            remKey = r.firstKey();

        }
        //if remainder is not empty, return a null
        if (!r.isEmpty())
            return null;
        
        return result;
    }

    /**
     * Checks if a Polynomial contains zero coefficients.
     * @return true if the Polynomial contains zero coefficients; false
     * otherwise.
     */
    private boolean isZero(){
        TreeMap<Integer,Float> poly = this.getPoly();
        if (poly.isEmpty())
            return true;
        for (Float coeff : poly.values())
            if (!(coeff==0))
                return false;
        return true;
    }

}
