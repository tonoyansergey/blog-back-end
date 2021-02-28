package am.egs.repository;

import am.egs.model.TokenStatus;
import am.egs.model.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Validated
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {

    Optional<VerificationEntity> findByVerCodeAndEmailAndStatusAndCreatedOnIsGreaterThan(@NotBlank final String verCode, @NotBlank final String email,
                                                                                         @NotNull final TokenStatus status, @NotNull final LocalDateTime now);
}
