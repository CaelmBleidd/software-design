package configuration

import aspect.ProfilingExecutionTimeAspect
import calculator.token.BracketState
import calculator.token.EmptyState
import calculator.token.NumberState
import calculator.token.OperationState
import calculator.token.State
import calculator.token.Tokenizer
import calculator.visitor.CalcVisitor
import calculator.visitor.ParserVisitor
import calculator.visitor.TokenVisitor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
open class ContextConfiguration {
    @Bean
    open fun tokenizer(): Tokenizer = Tokenizer()

    @Bean
    open fun parserVisitor(): TokenVisitor = ParserVisitor()

    @Bean
    open fun calcVisitor(): TokenVisitor = CalcVisitor()

    @Bean
    open fun emptyState(): State = EmptyState()

    @Bean
    open fun bracketState(): State = BracketState()

    @Bean
    open fun numberState(): State = NumberState()

    @Bean
    open fun operationState(): State = OperationState()

    @Bean
    open fun aspect(): ProfilingExecutionTimeAspect = ProfilingExecutionTimeAspect()
}