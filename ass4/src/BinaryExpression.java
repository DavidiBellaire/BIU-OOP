import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * The BinaryExpression class is an abstract base for all binary logical
 * expressions — those operating on two sub-expressions (left and right).
 * It provides shared behavior for evaluating, listing variables, and
 * producing a string representation. Concrete binary expressions supply
 * their own symbol and logical operator.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression left;
    private Expression right;

    /**
     * Constructor — creates a binary expression from two sub-expressions.
     *
     * @param left  the left operand.
     * @param right the right operand.
     */
    public BinaryExpression(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Operands cannot be null.");
        }
        this.left = left;
        this.right = right;
    }

    /**
     * @return the left sub-expression.
     */
    protected Expression getLeft() {
        return this.left;
    }

    /**
     * @return the right sub-expression.
     */
    protected Expression getRight() {
        return this.right;
    }

    /**
     * Returns the symbol used to represent this operator in a string.
     *
     * @return the operator symbol.
     */
    protected abstract String getSymbol();

    /**
     * Applies this binary operator to two boolean values.
     *
     * @param leftValue  the value of the left operand.
     * @param rightValue the value of the right operand.
     * @return the result of applying the operator.
     */
    protected abstract Boolean applyOperator(Boolean leftValue,
                                             Boolean rightValue);

    /**
     * Evaluates this expression by evaluating both sub-expressions and
     * applying the operator.
     *
     * @param assignment the variable assignment.
     * @return the boolean result.
     * @throws Exception if a variable is not assigned.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean leftValue = this.left.evaluate(assignment);
        Boolean rightValue = this.right.evaluate(assignment);
        return this.applyOperator(leftValue, rightValue);
    }

    /**
     * Returns the list of variables in this expression — the union of the
     * variables in both sub-expressions.
     *
     * @return a list of variable names.
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        for (String var : this.left.getVariables()) {
            if (!variables.contains(var)) {
                variables.add(var);
            }
        }
        for (String var : this.right.getVariables()) {
            if (!variables.contains(var)) {
                variables.add(var);
            }
        }
        return variables;
    }

    /**
     * Returns a string representation in the form.
     *
     * @return the string representation.
     */
    public String toString() {
        return "(" + this.left.toString() + " " + this.getSymbol()
                + " " + this.right.toString() + ")";
    }
}