/**
 * The Nand class represents the logical NAND of two expressions —
 * the negation of AND.
 */
public class Nand extends BinaryExpression {

    /**
     * Constructor — creates the NAND of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public Nand(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the NAND symbol "A".
     */
    protected String getSymbol() {
        return "A";
    }

    /**
     * Applies logical NAND to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return the negation of the conjunction.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return !(leftValue && rightValue);
    }

    /**
     * Returns a new Nand with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Nand with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Nand(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this NAND to use only Nand — it is already Nand, so just
     * nandify the operands.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        return new Nand(this.getLeft().nandify(), this.getRight().nandify());
    }

    /**
     * Converts this NAND to use only Nor.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression left = this.getLeft().norify();
        Expression right = this.getRight().norify();
        // AND(x,y) in Nor = ((x V x) V (y V y)).
        Expression andPart = new Nor(new Nor(left, left), new Nor(right, right));
        // NOT of that = (andPart V andPart).
        return new Nor(andPart, andPart);
    }

    /**
     * Simplifies this NAND expression.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        Expression left = this.getLeft().simplify();
        Expression right = this.getRight().simplify();

        if (this.getVariables().isEmpty()) {
            try {
                return new Val(new Nand(left, right).evaluate());
            } catch (Exception e) {
                return new Nand(left, right);
            }
        }

        String leftStr = left.toString();
        String rightStr = right.toString();

        // x A F = T
        if (leftStr.equals("F") || rightStr.equals("F")) {
            return new Val(true);
        }
        // x A T = (~(x))
        if (rightStr.equals("T")) {
            return new Not(left).simplify();
        }
        if (leftStr.equals("T")) {
            return new Not(right).simplify();
        }
        // x A x = (~(x))
        if (leftStr.equals(rightStr)) {
            return new Not(left).simplify();
        }

        return new Nand(left, right);
    }
}