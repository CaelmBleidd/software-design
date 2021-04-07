package stonks.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import stonks.model.Stock
import stonks.model.User

@Repository
interface StockRepository: JpaRepository<Stock, String> {
    fun findByCompany(company: String): Stock?
}

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByLogin(login: String): User?
}