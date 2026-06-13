/**
 * The Xnor class represents the logical XNOR of two expressions —
 * the negation of XOR (true when both operands are equal).
 * String form: (x # y).
 */
public class Xnor extends BinaryExpression {

    /**
     * Constructor — creates the XNOR of two expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public Xnor(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * @return the XNOR symbol "#".
     */
    protected String getSymbol() {
        return "#";
    }

    /**
     * Applies logical XNOR to two values.
     *
     * @param leftValue  the left operand value.
     * @param rightValue the right operand value.
     * @return true if the values are equal.
     */
    protected Boolean applyOperator(Boolean leftValue, Boolean rightValue) {
        return leftValue.equals(rightValue);
    }

    /**
     * Returns a new Xnor with the variable assigned in both operands.
     *
     * @param var        the variable to replace.
     * @param expression the replacement expression.
     * @return a new Xnor with the substitution applied.
     */
    public Expression assign(String var, Expression expression) {
        return new Xnor(this.getLeft().assign(var, expression),
                this.getRight().assign(var, expression));
    }

    /**
     * Converts this XNOR to use only Nand. XNOR(x,y) = NOT(XOR(x,y)).
     *
     * @return the Nand-only equivalent expression.
     */
    public Expression nandify() {
        // XNOR = NOT(XOR). Build XOR in Nand, then negate.
        Expression left = this.getLeft().nandify();
        Expression right = this.getRight().nandify();
        Expression mid = new Nand(left, right);
        Expression xorPart = new Nand(new Nand(left, mid), new Nand(right, mid));
        return new Nand(xorPart, xorPart);
    }

    /**
     * Converts this XNOR to use only Nor. XNOR(x,y) = NOT(XOR(x,y)).
     *
     * @return the Nor-only equivalent expression.
     */
    public Expression norify() {
        Expression left = this.getLeft().norify();
        Expression right = this.getRight().norify();
        Expression orPart = new Nor(new Nor(left, left), new Nor(right, right));
        Expression norPart = new Nor(left, right);
        Expression xorPart = new Nor(orPart, norPart);
        return new Nor(xorPart, xorPart);
    }

    /**
     * Simplifies this XNOR expression.
     *
     * @return a simplified expression.
     */
    public Expression simplify() {
    Expression left = this.getLeft().simplify();
    Expression right = this.getRight().simplify();

    // constant folding — אם אין משתנים, חשב ישירות
    if (left.getVariables().isEmpty() && right.getVariables().isEmpty()) {
        try {
            return new Val(new Xnor(left, right).evaluate());
        } catch (Exception e) {
            return new Xnor(left, right);
        }
    }

    String leftStr = left.toString();
    String rightStr = right.toString();

    // x # x = T
    if (leftStr.equals(rightStr)) {
        return new Val(true);
    }

    return new Xnor(left, right);
}
}