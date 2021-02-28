package am.egs.repository;

import am.egs.model.entity.ArticleEntity;
import am.egs.model.entity.TopicEntity;
import am.egs.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
@Validated
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<List<ArticleEntity>> findAllByTopic(@NotNull final TopicEntity topicEntity);

    Optional<List<ArticleEntity>> findAllByUser(@NotNull final UserEntity userEntity);
}
