import java.util.*;
/**
 * Reads Strings of all kinds and then decides how to deal with them
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class LineRead
{
    public HashMap<Character,Polynomial> savedPoly;
    
    /**
     * Constructor containing the memory used to call back Polynomials
     * if necessary
     */
    public LineRead(){
        savedPoly = new HashMap<Character,Polynomial>();
    }
    
    /**
     * Reads inputs of Polynomials, variables and equations and determines
     * how to handle them.
     * @param input a String, which can be a Polynomial, variables in place
     * of Polynomials, or equations (declarations) will would be stored in
     * memory.
     * @return a String representing the complete evaluation
     */
    public String lineRead(String input){
        Polynomial res;
        String[] polyParse = input.split("=+"), keyParse;
        if (polyParse.length==0||polyParse.length>2)
            return null;
        String poly, keyStr, result, output;
        char key = '\u0000', tok;
        output = result = poly = "";
        if (polyParse.length==1)
            poly = polyParse[0];
        else{
            keyParse = polyParse[0].split("\\s+");
            if (keyParse.length>1)
                return null;
            key = keyParse[0].charAt(0);
            poly = polyParse[1];
        }
        
        for (int i=0; i < poly.length(); i++){
            tok = poly.charAt(i);
            if (!Character.isWhitespace(tok))
                if (this.savedPoly.containsKey(tok))
                    result += "(" + this.savedPoly.get(tok) + ")";
                else
                    result += tok;
        }
        
        res = ExprEval.eval(result); //could give null
        if (res==null){
            return null;
        }
        
        if (key != '\u0000'){
            this.savedPoly.put(key,res);
            output += key + " = " + res;
        }
        else output = "" + res;
        
        return output;
    }
    
    /**
     * Tests the lineRead method
     */
    public static void test(){
        LineRead lr = new LineRead();
        System.out.println(lr.lineRead("a = x^2 + 3"));
        System.out.println(lr.lineRead("b = 2x^2 + x"));
        System.out.println(lr.lineRead("c = 2"));
        System.out.println(lr.lineRead("d=2=3"));
    }
}
