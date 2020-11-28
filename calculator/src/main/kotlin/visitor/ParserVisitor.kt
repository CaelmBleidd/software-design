package visitor

import token.Bracket
import token.NumberToken
import token.Operation
import token.Token

class ParserVisitor : TokenVisitor {
    private val tokens = mutableListOf<Token>()
    val result: List<Token>
        get() = tokens.let {
            while(operations.isNotEmpty()) {
                it += operations.removeFirst()
            }
            it
        }

    private val operations = ArrayDeque<Token>()

    override fun visit(token: NumberToken) {
        tokens += token
    }

    override fun visit(token: Bracket) {
        if (token.isOpen()) {
            operations.addFirst(token)
            return
        }

        while (true) {
            require(operations.isNotEmpty()) { error("Found \')\' without paired \'(\'") }

            val current = operations.removeFirst()

            if (current is Bracket && current.isOpen()) break

            tokens += current
        }
    }

    override fun visit(operation: Operation) {
        while (true) {
            val current = operations.firstOrNull()
            if (current is Operation) {
                val operator = current.value
                if (priorities.getValue(operator) >= priorities.getValue(operation.value)) {
                    tokens += operations.removeFirst()
                    continue
                }
            }
            break
        }
        operations.addFirst(operation)
    }

    companion object {
        val priorities = mapOf('+' to 1, '-' to 1, '/' to 2, '*' to 2)
    }
}
