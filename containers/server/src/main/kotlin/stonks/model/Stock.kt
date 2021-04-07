package stonks.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

@Entity
data class Stock(
    @Id @GeneratedValue var id: UUID? = null,
    @Column(unique = true) @NotEmpty var company: String? = null,
    @Range(min = 1L, max = 1000L) var price: Long = 0,
    @Range(max = 500L) var count: Long = 0
)