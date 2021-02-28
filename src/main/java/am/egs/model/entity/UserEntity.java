package am.egs.model.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rating", columnDefinition = "double default '0'", insertable = false)
    private Double rating;

    @OneToMany(mappedBy = "user")
    private List<ArticleEntity> ownArticles;

    @ManyToMany
    @JoinTable(
            name = "user_saved_article",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "saved_article_id") }
    )
    private List<ArticleEntity> savedArticles;

    @ManyToMany
    @JoinTable(
            name = "user_reader",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "reader_id") }
    )
    private List<UserEntity> readers;

    @ManyToMany
    @JoinTable(
            name = "user_reading_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "reading_user_id") }
    )
    private List<UserEntity> readingUsers;

    @ManyToMany
    @JoinTable(
            name = "user_reading_topic",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "reading_topic_id") }
    )
    private List<TopicEntity> readingTopics;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "bigint(20) default '2'", insertable = false)
    private RoleEntity role;
}
