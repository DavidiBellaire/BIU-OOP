/**
 * The And class represents the logical AND (conjunction) of two
 * expressions. String form: (x & y).
 */
public class And extends BinaryExpression {

    /**
     * Constructor — creates the AND of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public And(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the AND symbol "&".
     */
    protected String getSymbol() {
        return "&";
    }

    /**
     * Applies logical AND to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return the conjunction of the two values.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return leftValue && rightValue;
    }

    /**
     * Returns a new And with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new And with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new And(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this AND to use only Nand.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        Expression left = this.getLeft().nandify();
        Expression right = this.getRight().nandify();
        Expression inner = new Nand(left, right);
        return new Nand(inner, inner);
    }

    /**
     * Converts this AND to use only Nor.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression left = this.getLeft().norify();
        Expression right = this.getRight().norify();
        return new Nor(new Nor(left, left), new Nor(right, right));
    }

    /**
     * Simplifies this AND expression. Applies the rules:
     * x &amp; T = x, x &amp; F = F, x &amp; x = x, and constant folding for
     * variable-free expressions.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        // Step 1 — simplify both operands first (recursion).
        Expression left = this.getLeft().simplify();
        Expression right = this.getRight().simplify();

        // Step 2 — if there are no variables, fold to a constant.
        if (left.getVariables().isEmpty() && right.getVariables().isEmpty()) {
            try {
                return new Val(new And(left, right).evaluate());
            } catch (Exception e) {
                return new And(left, right);
            }
        }

        String leftStr = left.toString();
        String rightStr = right.toString();

        // Step 3 — apply the AND simplification rules.

        // x & F = F  (and symmetrically F & x = F)
        if (leftStr.equals("F") || rightStr.equals("F")) {
            return new Val(false);
        }
        // x & T = x  (and symmetrically T & x = x)
        if (rightStr.equals("T")) {
            return left;
        }
        if (leftStr.equals("T")) {
            return right;
        }
        // x & x = x
        if (leftStr.equals(rightStr)) {
            return left;
        }

        // Step 4 — no rule applied; return with simplified operands.
        return new And(left, right);
    }
}