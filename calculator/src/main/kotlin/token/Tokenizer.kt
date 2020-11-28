package token

class Tokenizer {
    val tokens = mutableListOf<Token>()
    val result: List<Token>
        get() = tokens.let {
            if (actualState !is EmptyState) {
                actualState.handle(' ', this)
            }
            it
        }
    var actualState: State = EmptyState

    fun handle(symbol: Char) = actualState.handle(symbol, this)

    companion object {
        val operations = hashSetOf('+', '-', '/', '*')
        val brackets = hashSetOf('(', ')')
    }
}

sealed class State {
    abstract fun handle(symbol: Char, tokenizer: Tokenizer)

    fun nextState(symbol: Char) = when {
        symbol in Tokenizer.brackets -> BracketState(symbol)
        symbol in Tokenizer.operations -> OperationState(symbol)
        symbol.isDigit() -> NumberState(symbol)
        symbol.isWhitespace() -> EmptyState
        else -> error ("Unexpected symbol: $symbol")
    }
}

object EmptyState : State() {
    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.actualState = nextState(symbol)
    }
}

class BracketState(private val bracket: Char) : State() {
    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.tokens += Bracket(bracket)
        tokenizer.actualState = nextState(symbol)
    }
}

class NumberState(private val firstDigit: Char) : State() {
    private val number = StringBuilder().apply { append(firstDigit) }

    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        if (symbol.isDigit()) {
            number.append(symbol)
        } else {
            tokenizer.tokens += NumberToken("$number".toLong())
            tokenizer.actualState = nextState(symbol)
        }
    }
}

class OperationState(private val operator: Char) : State() {
    override fun handle(symbol: Char, tokenizer: Tokenizer) {
        tokenizer.tokens += Operation(operator)
        tokenizer.actualState = nextState(symbol)
    }
}


