import java.util.TreeMap;

/**
 * AbstractExpression is the root abstract class for all expressions. It
 * implements the Expression interface and provides the functionality
 * shared by every expression — the no-argument evaluate(), and the
 * default isNot()/getInner() behavior used for double-negation detection.
 */
public abstract class AbstractExpression implements Expression {

    /**
     * Evaluates the expression using an empty assignment.
     *
     * @return the boolean result.
     * @throws Exception if the expression contains an unassigned variable.
     */
    public Boolean evaluate() throws Exception {
        return this.evaluate(new TreeMap<String, Boolean>());
    }

    /**
     * By default an expression is not a Not. Overridden by Not.
     *
     * @return false.
     */
    public boolean isNot() {
        return false;
    }

    /**
     * By default returns this expression. Overridden by Not to return its
     * inner expression.
     *
     * @return this expression.
     */
    public Expression getInner() {
        return this;
    }
}