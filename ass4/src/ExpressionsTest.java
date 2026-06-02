/* ID - 214165417 */
import java.util.Map;
import java.util.TreeMap;

/**
 * ExpressionsTest creates a logical expression with three variables and
 * prints its string form, its evaluated value, its Nandified form, its
 * Norified form, and its simplified form.
 */
public class ExpressionsTest {

    /**
     * Main entry point.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        Expression e = new Xor(
                new And(new Var("x"), new Var("y")),
                new Or(new Var("z"), new Val(true)));

        System.out.println(e);

        Map<String, Boolean> assignment = new TreeMap<String, Boolean>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);
        try {
            System.out.println(e.evaluate(assignment));
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        System.out.println(e.nandify());
        System.out.println(e.norify());
        System.out.println(e.simplify());
    }
}