package configuration

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

open class ApplicationContextProvider : ApplicationContextAware {
    override fun setApplicationContext(ctx: ApplicationContext) {
        context = ctx
    }

    companion object {
        lateinit var context: ApplicationContext
    }
}