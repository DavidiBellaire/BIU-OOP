/**
 * The Not class represents the logical NOT (negation) of a single
 * expression. String form: (~(x)).
 */
public class Not extends UnaryExpression {

    /**
     * Constructor — creates the negation of the given expression.
     *
     * @param expression the expression to negate.
     */
    public Not(Expression expression) {
        super(expression);
    }

    /**
     * Applies logical NOT to the value.
     *
     * @param value the operand value.
     * @return the negation of the value.
     */
    protected Boolean applyOperator(Boolean value) {
        return !value;
    }

    /**
     * Returns the string representation: (~(inner)).
     *
     * @return the string representation.
     */
    public String toString() {
        return "(~(" + this.getExpression().toString() + "))";
    }

    /**
     * Returns a new Not with the variable assigned in the inner
     * expression.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Not with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Not(this.getExpression().assign(var, expression));
    }

    /**
     * Converts this NOT expression to use only Nand.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        Expression inner = this.getExpression().nandify();
        return new Nand(inner, inner);
    }

    /**
     * Converts this NOT expression to use only Nor.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression inner = this.getExpression().norify();
        return new Nor(inner, inner);
    }

    /**
     * @return true — this is a Not expression.
     */
    public boolean isNot() {
        return true;
    }

    /**
     * @return the inner expression being negated.
     */
    public Expression getInner() {
        return this.getExpression();
    }

    /**
     * Simplifies this NOT expression. Applies double-negation elimination
     * (~~x = x) and constant folding.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        Expression inner = this.getExpression().simplify();

        // Constant folding — no variables, evaluate directly.
        if (this.getVariables().isEmpty()) {
            try {
                return new Val(new Not(inner).evaluate());
            } catch (Exception e) {
                return new Not(inner);
            }
        }

        // Double negation: (~(~(x))) = x.
        if (inner.isNot()) {
            return inner.getInner().simplify();
        }

        return new Not(inner);
    }
}