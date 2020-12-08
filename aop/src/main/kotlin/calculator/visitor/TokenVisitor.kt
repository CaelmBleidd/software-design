package calculator.visitor

import calculator.token.Bracket
import calculator.token.NumberToken
import calculator.token.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)

    fun visit(token: Bracket)

    fun visit(operation: Operation)
}