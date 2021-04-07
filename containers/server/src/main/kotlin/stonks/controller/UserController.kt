package stonks.controller

import org.hibernate.validator.constraints.Range
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import stonks.service.UserService

@RestController
@RequestMapping("/api/v1/user")
class UserController(val userService: UserService) {
    @PostMapping
    fun register(@RequestParam login: String) = userService.register(login)

    @GetMapping("/{login}")
    fun authorize(@PathVariable login: String) = userService.findByLogin(login)

    @PutMapping("/deposit/{login}")
    fun deposit(@PathVariable login: String, @RequestParam @Range(max = 10000L) value: Long) =
        userService.deposit(login, value)

    @GetMapping("/portfolio/{login}")
    fun getPortfolio(@PathVariable login: String) = userService.getPortfolio(login)

    @GetMapping("/money/{login}")
    fun findMoney(@PathVariable login: String) = userService.findMoney(login)

    @PutMapping("/buy/{login}")
    fun buy(@PathVariable login: String, @RequestParam company: String, @RequestParam @Range(max = 100L) count: Long) =
        userService.buy(login, company, count)

    @PutMapping("/sell/{login}")
    fun sell(@PathVariable login: String, @RequestParam company: String, @RequestParam @Range(max = 100L) count: Long) =
        userService.sell(login, company, count)
}