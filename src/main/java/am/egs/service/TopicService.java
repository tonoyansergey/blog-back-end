package am.egs.service;

import am.egs.model.bean.TopicBean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface TopicService {

    TopicBean getById(@NotNull final Long id);

    List<TopicBean> getAll();

    TopicBean getByName(@NotBlank final String name);

    TopicBean save(@NotNull final TopicBean topic);

    TopicBean update(@NotNull final TopicBean topic);
}
