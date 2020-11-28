package visitor

import token.Bracket
import token.NumberToken
import token.Operation
import token.Token

class CalcVisitor : TokenVisitor {
    private val tokens = ArrayDeque<Token>()
    val result: Long
        get() = tokens.let {
            require(it.size == 1) {
                error(
                    "Result stack contains more then one element, stack: " + tokens.joinToString(" ")
                )
            }
            val resultValue = it.first() as? NumberToken
                ?: error("The last element in the stack is ${it.first()::class}, not a NumberToken")
            resultValue.value
        }

    override fun visit(token: NumberToken) {
        tokens.addFirst(token)
    }

    override fun visit(token: Bracket) = Unit

    override fun visit(operation: Operation) {
        require(tokens.size >= 2) { error("We need 2+ elements in the stack for binary operation, found ${tokens.size}") }
        val right = tokens.removeFirst()
        val left = tokens.removeFirst()

        require(left is NumberToken) { error("Left value is ${left::class}, not a NumberToken") }
        require(right is NumberToken) { error("Right value if ${right::class}, not a NumberToken") }

        val leftNumber = left.value
        val rightNumber = right.value

        when (operation.value) {
            '+' -> tokens.addFirst(NumberToken(leftNumber + rightNumber))
            '-' -> tokens.addFirst(NumberToken(leftNumber - rightNumber))
            '/' -> tokens.addFirst(NumberToken(leftNumber / rightNumber))
            '*' -> tokens.addFirst(NumberToken(leftNumber * rightNumber))
            else -> error("Unexpected operation: ${operation.value}")
        }
    }
}