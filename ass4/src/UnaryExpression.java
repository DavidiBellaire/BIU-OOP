import java.util.List;
import java.util.Map;

/**
 * The UnaryExpression class is an abstract base for all unary logical
 * expressions — those operating on a single sub-expression. It provides
 * shared behavior for evaluating and listing variables. Concrete unary
 * expressions supply their own operator logic and string representation.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    /**
     * Constructor — creates a unary expression wrapping a single
     * sub-expression.
     *
     * @param expression the operand expression.
     */
    public UnaryExpression(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Inner expression cannot be null.");
        }
        this.expression = expression;
    }

    /**
     * @return the inner sub-expression.
     */
    protected Expression getExpression() {
        return this.expression;
    }

    /**
     * Applies this unary operator to a boolean value.
     *
     * @param value the value of the operand.
     * @return the result of applying the operator.
     */
    protected abstract Boolean applyOperator(Boolean value);

    /**
     * Evaluates this expression by evaluating the inner sub-expression
     * and applying the operator.
     *
     * @param assignment the variable assignment.
     * @return the boolean result.
     * @throws Exception if a variable is not assigned.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean value = this.expression.evaluate(assignment);
        return this.applyOperator(value);
    }

    /**
     * Returns the list of variables in this expression — the variables of
     * the inner sub-expression.
     *
     * @return a list of variable names.
     */
    public List<String> getVariables() {
        return this.expression.getVariables();
    }
}