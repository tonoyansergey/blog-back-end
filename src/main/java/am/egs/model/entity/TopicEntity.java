package am.egs.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "topic")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TopicEntity extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "definition", nullable = false)
    private String definition;
}
