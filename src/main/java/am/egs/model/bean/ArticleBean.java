package am.egs.model.bean;

import am.egs.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleBean {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String content;

    @NotBlank
    private String photo;

    @NotBlank
    private TopicBean topic;

    @NotBlank
    private User user;

    @NotNull
    private Double rating;
}
