package demo.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "update star set deleted_at=now() where id=?")
@SQLRestriction("deleted_at is null")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var memberId: String,
    var name: String,
    var email: String,
    var password: String,
    var role: String,
    var createdAt: String,
    var updatedAt: String,
    var deletedAt: String,
)