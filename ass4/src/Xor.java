/**
 * The Xor class represents the logical XOR (exclusive or) of two
 * expressions. String form: (x ^ y).
 */
public class Xor extends BinaryExpression {

    /**
     * Constructor — creates the XOR of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public Xor(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the XOR symbol "^".
     */
    protected String getSymbol() {
        return "^";
    }

    /**
     * Applies logical XOR to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return true if the values differ.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return leftValue ^ rightValue;
    }

    /**
     * Returns a new Xor with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Xor with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Xor(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this XOR to use only Nand.
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        Expression left = this.getLeft().nandify();
        Expression right = this.getRight().nandify();
        Expression mid = new Nand(left, right);
        return new Nand(new Nand(left, mid), new Nand(right, mid));
    }

    /**
     * Converts this XOR to use only Nor.
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression left = this.getLeft().norify();
        Expression right = this.getRight().norify();
        Expression orPart = new Nor(new Nor(left, left), new Nor(right, right));
        Expression norPart = new Nor(left, right);
        return new Nor(orPart, norPart);
    }

    /**
     * Simplifies this XOR expression.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
        Expression left = this.getLeft().simplify();
        Expression right = this.getRight().simplify();

        if (this.getVariables().isEmpty()) {
            try {
                return new Val(new Xor(left, right).evaluate());
            } catch (Exception e) {
                return new Xor(left, right);
            }
        }

        String leftStr = left.toString();
        String rightStr = right.toString();

        // x ^ x = F
        if (leftStr.equals(rightStr)) {
            return new Val(false);
        }
        // x ^ T = (~(x))
        if (rightStr.equals("T")) {
            return new Not(left).simplify();
        }
        if (leftStr.equals("T")) {
            return new Not(right).simplify();
        }
        // x ^ F = x
        if (rightStr.equals("F")) {
            return left;
        }
        if (leftStr.equals("F")) {
            return right;
        }

        return new Xor(left, right);
    }
}