package am.egs.model.bean;

import am.egs.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBean {

    private Long id;

    private String firstName;

    private String lastName;

    @NotBlank
    private String username;

    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    private String photo;

    @NotBlank
    private String password;

    private Double rating;

    private List<ArticleBean> ownArticles;

    private List<ArticleBean> savedArticles;

    private List<User> readers;

    private List<User> readingUsers;

    private List<TopicBean> readingTopics;

    private RoleBean role;
}
