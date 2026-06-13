/**
 * The Or class represents the logical OR (disjunction) of two
 * expressions. String form: (x | y).
 */
public class Or extends BinaryExpression {

    /**
     * Constructor — creates the OR of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the OR symbol "|".
     */
    protected String getSymbol() {
        return "|";
    }

    /**
     * Applies logical OR to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return the disjunction of the two values.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return leftValue || rightValue;
    }

    /**
     * Returns a new Or with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Or with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Or(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this OR to use only Nand.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        Expression left = this.getLeft().nandify();
        Expression right = this.getRight().nandify();
        return new Nand(new Nand(left, left), new Nand(right, right));
    }

    /**
     * Converts this OR to use only Nor.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression left = this.getLeft().norify();
        Expression right = this.getRight().norify();
        Expression inner = new Nor(left, right);
        return new Nor(inner, inner);
    }

    /**
     * Simplifies this OR expression.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        Expression left = this.getLeft().simplify();
        Expression right = this.getRight().simplify();

        if (left.getVariables().isEmpty() && right.getVariables().isEmpty()) {
            try {
                return new Val(new Or(left, right).evaluate());
            } catch (Exception e) {
                return new Or(left, right);
            }
        }

        String leftStr = left.toString();
        String rightStr = right.toString();

        // x | T = T
        if (leftStr.equals("T") || rightStr.equals("T")) {
            return new Val(true);
        }
        // x | F = x
        if (rightStr.equals("F")) {
            return left;
        }
        if (leftStr.equals("F")) {
            return right;
        }
        // x | x = x
        if (leftStr.equals(rightStr)) {
            return left;
        }

        return new Or(left, right);
    }
}