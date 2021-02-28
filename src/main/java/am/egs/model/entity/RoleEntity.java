package am.egs.model.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity implements Serializable {

    @Column(name = "role_name", nullable = false)
    private String roleName;
}
