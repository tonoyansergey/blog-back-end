package am.egs.repository;

import am.egs.model.TokenStatus;
import am.egs.model.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> getByTokenAndStatusAndCreatedOnIsGreaterThan(@NotBlank final String token, @NotNull final TokenStatus status, @NotNull final LocalDateTime now);
}
