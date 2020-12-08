package calculator.token

import configuration.ApplicationContextProvider

open class Tokenizer {
    open var tokens: MutableList<Token>? = mutableListOf()
    open val result: List<Token>
        get() = tokens.let {
            if (actualState !is EmptyState) {
                actualState!!.handle(' ', this)
            }
            it!!
        }
    open var actualState: State? = EmptyState()

    open fun handle(symbol: Char) = actualState!!.handle(symbol, this)

    companion object {
        val operations = hashSetOf('+', '-', '/', '*')
        val brackets = hashSetOf('(', ')')
    }
}

abstract class State {
    abstract fun handle(symbol: Char, tokenizer: Tokenizer)

    open fun nextState(symbol: Char): State {
        val ctx = ApplicationContextProvider.context
        return when {
            symbol in Tokenizer.brackets -> ctx.getBean(BracketState::class.java).also { it.bracket = symbol }
            symbol in Tokenizer.operations -> ctx.getBean(OperationState::class.java).also { it.operator = symbol }
            symbol.isDigit() -> ctx.getBean(NumberState::class.java).also { it.setFirstChar(symbol) }
            symbol.isWhitespace() -> ctx.getBean(EmptyState::class.java)
            else -> error("Unexpected symbol: $symbol")
        }
    }
}

open class EmptyState : State() {
    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.actualState = nextState(symbol)
    }
}

open class BracketState : State() {
    open var bracket: Char? = null

    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.tokens!!.add(Bracket(bracket!!))
        tokenizer.actualState = nextState(symbol)
    }
}

open class NumberState : State() {
    open var number: StringBuilder? = null

    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        if (symbol.isDigit()) {
            number!!.append(symbol)
        } else {
            tokenizer.tokens!!.add(NumberToken("$number".toLong()))
            tokenizer.actualState = nextState(symbol)
        }
    }

    fun setFirstChar(firstDigit: Char) {
        number = StringBuilder().apply { append(firstDigit) }
    }
}

open class OperationState : State() {
    open var operator: Char? = null

    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.tokens!!.add(Operation(operator!!))
        tokenizer.actualState = nextState(symbol)
    }
}


