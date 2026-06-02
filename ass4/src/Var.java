import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Var class represents a logical variable, such as "x" or "y". It is
 * a leaf in the expression tree. Evaluating a variable looks up its value
 * in the given assignment; if the variable is not assigned, an exception
 * is thrown.
 */
public class Var extends AbstractExpression {
    private String name;

    /**
     * Constructor — creates a variable with the given name.
     *
     * @param name the name of the variable.
     */
    public Var(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Var name cannot be null.");
        }
        this.name = name;
    }

    /**
     * Evaluates the variable by looking up its value in the assignment.
     *
     * @param assignment the variable assignment.
     * @return the boolean value assigned to this variable.
     * @throws Exception if this variable is not present in the assignment.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            throw new Exception("Variable '" + this.name + "' is not assigned.");
        }
        return assignment.get(this.name);
    }

    /**
     * Returns a list containing this variable's name.
     *
     * @return a single-element list with this variable's name.
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<String>();
        variables.add(this.name);
        return variables;
    }

    /**
     * Returns the variable's name.
     *
     * @return the string representation.
     */
    public String toString() {
        return this.name;
    }

    /**
     * If this variable matches the given variable name, returns the
     * replacement expression. Otherwise returns this variable unchanged.
     *
     * @param var        the variable name to replace.
     * @param expression the expression to substitute in.
     * @return the replacement expression, or this variable.
     */
    public Expression assign(String var, Expression expression) {
        if (this.name.equals(var)) {
            return expression;
        }
        return new Var(this.name);
    }

    /**
     * A variable is atomic — returns itself.
     *
     * @return this same variable.
     */
    public Expression nandify() {
        return new Var(this.name);
    }

    /**
     * A variable is atomic — returns itself.
     *
     * @return this same variable.
     */
    public Expression norify() {
        return new Var(this.name);
    }

    /**
     * A variable is atomic — returns itself.
     *
     * @return this same variable.
     */
    public Expression simplify() {
        return new Var(this.name);
    }

}