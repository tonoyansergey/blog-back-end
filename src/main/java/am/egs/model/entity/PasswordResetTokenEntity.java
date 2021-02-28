package am.egs.model.entity;

import am.egs.model.TokenStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pass_reset_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PasswordResetTokenEntity extends BaseEntity implements Serializable {

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="status", nullable = false, insertable = false, columnDefinition = "varchar(250) default 'ACTIVE' "  )
    @Enumerated(value = EnumType.STRING)
    private TokenStatus status;

    public PasswordResetTokenEntity(final String token, final String email) {
        this.token = token;
        this.email = email;
    }
}
