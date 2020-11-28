package visitor

import token.Bracket
import token.NumberToken
import token.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)

    fun visit(token: Bracket)

    fun visit(operation: Operation)
}