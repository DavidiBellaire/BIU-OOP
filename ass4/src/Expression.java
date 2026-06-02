import java.util.List;
import java.util.Map;

/**
 * The Expression interface represents a logical expression that can be
 * evaluated, queried for its variables, have variables assigned to it,
 * converted to Nand/Nor-only form, and simplified.
 */
public interface Expression {

    /**
     * Evaluates the expression using the variable values provided in the
     * assignment. If the expression contains a variable which is not in
     * the assignment, an exception is thrown.
     *
     * @param assignment a map from variable names to boolean values.
     * @return the boolean result of evaluating the expression.
     * @throws Exception if a variable in the expression is not assigned.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * Evaluates the expression using an empty assignment. Useful when the
     * expression contains no variables.
     *
     * @return the boolean result of evaluating the expression.
     * @throws Exception if the expression contains an unassigned variable.
     */
    Boolean evaluate() throws Exception;

    /**
     * Returns a list of the variables appearing in the expression.
     *
     * @return a list of variable names.
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     *
     * @return the string representation.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression. Does not modify the
     * current expression.
     *
     * @param var        the variable to replace.
     * @param expression the expression to substitute in its place.
     * @return a new expression with the substitution applied.
     */
    Expression assign(String var, Expression expression);

    /**
     * Returns the expression tree resulting from converting all the
     * operations to the logical Nand operation.
     *
     * @return an equivalent expression using only Nand.
     */
    Expression nandify();

    /**
     * Returns the expression tree resulting from converting all the
     * operations to the logical Nor operation.
     *
     * @return an equivalent expression using only Nor.
     */
    Expression norify();

    /**
     * Indicates whether this expression is a Not expression. Used to
     * detect double negation without instanceof.
     *
     * @return true if this is a Not expression.
     */
    boolean isNot();

    /**
     * Returns the inner expression of a Not. For non-Not expressions,
     * returns this expression itself.
     *
     * @return the inner expression.
     */
    Expression getInner();

    /**
     * Returns a simplified version of the current expression.
     *
     * @return a simplified equivalent expression.
     */
    Expression simplify();
}