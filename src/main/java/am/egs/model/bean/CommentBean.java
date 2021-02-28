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
public class CommentBean {

    @NotBlank
    private String commentText;

    @NotNull
    private User user;

    @NotNull
    private ArticleBean article;
}
