package am.egs.model.entity;

import am.egs.model.TokenStatus;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "verification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class VerificationEntity extends BaseEntity implements Serializable {

    @Column(name = "verCode", nullable = false, columnDefinition = "VARCHAR(250) COLLATE latin1_general_cs")
    private String verCode;

    @Column(name = "email", nullable = false)
    private String email;


    @Column(name="status", nullable = false, insertable = false, columnDefinition = "varchar(250) default 'ACTIVE' "  )
    @Enumerated(value = EnumType.STRING)
    private TokenStatus status;

    public VerificationEntity(final String verCode, final String email) {
        this.verCode = verCode;
        this.email = email;
    }
}
