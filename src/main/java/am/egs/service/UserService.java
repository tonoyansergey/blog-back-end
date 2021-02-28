package am.egs.service;

import am.egs.model.bean.UserBean;
import am.egs.model.dto.PasswordResetDTO;
import am.egs.model.dto.User;
import am.egs.model.dto.UserProfile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

    UserBean getById(@NotNull final Long id);

    List<UserBean> getAll();

    UserBean getByUsername(@NotBlank final String username);

    UserProfile save(@NotNull final UserBean userBean);

    UserProfile update(@NotNull final UserBean userBean);

    User updateRating(@NotNull final Double rating, @NotNull final Long userId);

    User updateSavedArticles(@NotNull final Long savedArticleId, @NotNull final Long userId);

    User updateReaders(@NotNull final Long readerId, @NotNull final Long userId);

    User updateReadingUsers(@NotNull final Long readingUserId, @NotNull final Long userId);

    User updateReadingTopics(@NotNull final Long topicId, @NotNull final Long userId);

    void resetPassword(@NotNull final PasswordResetDTO passwordResetDTO);
}
