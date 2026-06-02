/**
 * The Nor class represents the logical NOR of two expressions —
 * the negation of OR. String form: (x V y).
 */
public class Nor extends BinaryExpression {

    /**
     * Constructor — creates the NOR of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the NOR symbol "V".
     */
    protected String getSymbol() {
        return "V";
    }

    /**
     * Applies logical NOR to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return the negation of the disjunction.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return !(leftValue || rightValue);
    }

    /**
     * Returns a new Nor with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Nor with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Nor(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this NOR to use only Nand. NOR(x,y) = NOT(OR(x,y)),
     * built from Nand primitives.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        Expression left = this.getLeft().nandify();
        Expression right = this.getRight().nandify();
        // OR(x,y) in Nand = ((x A x) A (y A y)).
        Expression orPart = new Nand(new Nand(left, left), new Nand(right, right));
        // NOT of that = (orPart A orPart).
        return new Nand(orPart, orPart);
    }

    /**
     * Converts this NOR to use only Nor — it is already Nor, so just
     * norify the operands.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        return new Nor(this.getLeft().norify(), this.getRight().norify());
    }

    /**
     * Simplifies this NOR expression.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        Expression left = this.getLeft().simplify();
        Expression right = this.getRight().simplify();

        if (this.getVariables().isEmpty()) {
            try {
                return new Val(new Nor(left, right).evaluate());
            } catch (Exception e) {
                return new Nor(left, right);
            }
        }

        String leftStr = left.toString();
        String rightStr = right.toString();

        // x V T = F
        if (leftStr.equals("T") || rightStr.equals("T")) {
            return new Val(false);
        }
        // x V F = (~(x))
        if (rightStr.equals("F")) {
            return new Not(left).simplify();
        }
        if (leftStr.equals("F")) {
            return new Not(right).simplify();
        }
        // x V x = (~(x))
        if (leftStr.equals(rightStr)) {
            return new Not(left).simplify();
        }

        return new Nor(left, right);
    }
}