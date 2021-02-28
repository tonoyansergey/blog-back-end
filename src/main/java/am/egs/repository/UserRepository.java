package am.egs.repository;

import am.egs.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
@Validated
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByUsername(@NotBlank final String username);

    Optional<UserEntity> findOneByEmail(@NotBlank final String email);

}
