package am.egs.service;

import am.egs.model.bean.ArticleBean;
import am.egs.model.bean.TopicBean;
import am.egs.model.bean.UserBean;
import am.egs.model.dto.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ArticleService {

    ArticleBean getById(@NotNull final Long id);

    List<ArticleBean> getAll();

    List<ArticleBean> getAllByTopic(@NotNull final TopicBean topic);

    List<ArticleBean> getAllByUser(@NotNull final User user);

    ArticleBean save(@NotNull final ArticleBean article);

    ArticleBean update(@NotNull final ArticleBean article);
}
