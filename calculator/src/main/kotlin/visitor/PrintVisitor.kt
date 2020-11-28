package visitor

import java.lang.StringBuilder
import token.Bracket
import token.NumberToken
import token.Operation

class PrintVisitor: TokenVisitor {
    private val resultBuilder = StringBuilder()
    val result: String
        get() = resultBuilder.toString()

    override fun visit(token: NumberToken) {
        resultBuilder.append(token.value)
        resultBuilder.append(' ')
    }

    override fun visit(token: Bracket) {
        resultBuilder.append(token.value)
        resultBuilder.append(' ')
    }

    override fun visit(operation: Operation) {
        resultBuilder.append(operation.value)
        resultBuilder.append(' ')
    }

}