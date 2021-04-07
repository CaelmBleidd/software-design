package stonks.model

import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

@Entity
data class User(
    @Id @GeneratedValue var id: UUID? = null,
    @NotEmpty @Column(unique = true) var login: String? = null,
    @Range(max = 50_000L) var money: Long = 0,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "company") var portfolio: MutableList<Stock> = mutableListOf()
)