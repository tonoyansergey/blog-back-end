package am.egs.repository;

import am.egs.model.entity.ArticleEntity;
import am.egs.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
@Validated
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<List<CommentEntity>> findAllByArticle(@NotNull final ArticleEntity article);
}
