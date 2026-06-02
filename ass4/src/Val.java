import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Val class represents a constant boolean value — either true ("T")
 * or false ("F"). It is a leaf in the expression tree: it has no
 * sub-expressions and no variables.
 */
public class Val extends AbstractExpression {
    private Boolean value;

    /**
     * Constructor — creates a constant value expression.
     *
     * @param value the boolean value (true or false).
     */
    public Val(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("Val value cannot be null.");
        }
        this.value = value;
    }

    /**
     * Evaluates to the constant value, regardless of the assignment.
     *
     * @param assignment the variable assignment (ignored).
     * @return the constant boolean value.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) {
        return this.value;
    }

    /**
     * Returns an empty list — a constant has no variables.
     *
     * @return an empty list.
     */
    public List<String> getVariables() {
        return new ArrayList<String>();
    }

    /**
     * Returns "T" for true and "F" for false.
     *
     * @return the string representation.
     */
    public String toString() {
        if (this.value) {
            return "T";
        }
        return "F";
    }

    /**
     * A constant has no variables to assign, so returns itself unchanged.
     *
     * @param var        the variable to replace (ignored).
     * @param expression the replacement expression (ignored).
     * @return this same constant.
     */
    public Expression assign(String var, Expression expression) {
        return new Val(this.value);
    }

    /**
     * A constant is already in its simplest Nand form — returns itself.
     *
     * @return this same constant.
     */
    public Expression nandify() {
        return new Val(this.value);
    }

    /**
     * A constant is already in its simplest Nor form — returns itself.
     *
     * @return this same constant.
     */
    public Expression norify() {
        return new Val(this.value);
    }

    /**
     * A constant is already fully simplified — returns itself.
     *
     * @return this same constant.
     */
    public Expression simplify() {
        return new Val(this.value);
    }

}