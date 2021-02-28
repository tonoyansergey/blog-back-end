package am.egs.service.impl;

import am.egs.model.dto.User;
import am.egs.model.dto.UserProfile;
import am.egs.model.entity.*;
import am.egs.repository.ArticleRepository;
import am.egs.repository.TopicRepository;
import am.egs.service.ValidationService;
import am.egs.util.exception.PersistFailureException;
import am.egs.util.exception.RecordNotFoundException;
import am.egs.model.bean.UserBean;
import am.egs.model.dto.PasswordResetDTO;
import am.egs.repository.UserRepository;
import am.egs.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private ArticleRepository articleRepository;
    private TopicRepository topicRepository;
    private ValidationService validationService;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final ArticleRepository articleRepository, final TopicRepository topicRepository, final ModelMapper mapper, final PasswordEncoder passwordEncoder, final ValidationService validationService) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @Override
    public UserBean getById(@NotNull Long id) {
        final Optional<UserEntity> optionalUser = userRepository.findById(id);
        return mapper.map(optionalUser
                .orElseThrow(() -> new RecordNotFoundException("User not found")), UserBean.class);
    }

    @Override
    public List<UserBean> getAll() {
        final Optional<List<UserEntity>> optionalList = Optional.of(userRepository.findAll());
        return mapper.map(optionalList
                .orElseThrow(() -> new RecordNotFoundException("Users not found")),new TypeToken<List<UserBean>>() {}.getType());
    }

    @Override
    public UserBean getByUsername(@NotBlank final String username) {
        LOGGER.info("attempt to gеt user from database with username: {}", username);

        return mapper.map(userRepository.findOneByUsername(username).orElseThrow(() -> new RecordNotFoundException("user not found with specified username")),UserBean.class);
    }

    @Override
    public UserProfile save(@NotNull final UserBean userBean) {
        LOGGER.info("attempt to save user to database with username: {}", userBean.getUsername());
        userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));

        return mapper.map(Optional.of(userRepository.save(mapper.map(userBean, UserEntity.class)))
                .orElseThrow(() -> new PersistFailureException("error while user persistence")), UserProfile.class);
    }

    @Override
    public UserProfile update(@NotNull UserBean userBean) {
        // find the entity to be updated
        UserEntity userToUpdate = userRepository.getOne(userBean.getId());

//        userToUpdate = mapper.map(userBean, UserEntity.class);

        // set the new values
        userToUpdate.setFirstName(userBean.getFirstName());
        userToUpdate.setLastName(userBean.getLastName());
        userToUpdate.setUsername(userBean.getUsername());
        userToUpdate.setEmail(userBean.getEmail());
        userToUpdate.setPhoto(userBean.getPhoto());
        userToUpdate.setPassword(passwordEncoder.encode(userBean.getPassword()));
        userToUpdate.setRating(userBean.getRating());
        userToUpdate.setOwnArticles(mapper.map(userBean.getOwnArticles(), new TypeToken<List<ArticleEntity>>() {}.getType()));
        userToUpdate.setSavedArticles(mapper.map(userBean.getSavedArticles(), new TypeToken<List<ArticleEntity>>() {}.getType()));
        userToUpdate.setReaders(mapper.map(userBean.getReaders(), new TypeToken<List<UserEntity>>() {}.getType()));
        userToUpdate.setReadingUsers(mapper.map(userBean.getReadingUsers(), new TypeToken<List<UserEntity>>() {}.getType()));
        userToUpdate.setReadingTopics(mapper.map(userBean.getReadingTopics(), new TypeToken<List<TopicEntity>>() {}.getType()));
        userToUpdate.setRole(mapper.map(userBean.getRole(), RoleEntity.class));

        // update the entity
        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userToUpdate));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), UserProfile.class);
    }

    @Override
    public User updateRating(@NotNull Double rating, @NotNull Long userId) {
        // find the entity to be updated
        UserEntity userToUpdate = userRepository.getOne(userId);

        // set the new values
        userToUpdate.setRating(rating);

        // update the entity
        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userToUpdate));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), User.class);
    }

    @Override
    public User updateSavedArticles(@NotNull Long savedArticleId, @NotNull Long userId) {
        // find the entity to be updated
        UserEntity userEntity = userRepository.getOne(userId);
        ArticleEntity articleEntity = articleRepository.getOne(savedArticleId);

        // update saved articles list
        userEntity.getSavedArticles().add(articleEntity);

        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userEntity));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), User.class);
    }

    @Override
    public User updateReaders(@NotNull Long readerId, @NotNull Long userId) {
        // find the entity to be updated
        UserEntity userEntity = userRepository.getOne(userId);
        UserEntity readerEntity = userRepository.getOne(readerId);

        // update readers list
        userEntity.getReaders().add(readerEntity);

        //update reading list
        readerEntity.getReadingUsers().add(userEntity);

        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userEntity));
        Optional.of(userRepository.save(readerEntity)).orElseThrow(() -> new PersistFailureException("Error while updating user"));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), User.class);
    }

    @Override
    public User updateReadingUsers(@NotNull Long readingUserId, @NotNull Long userId) {
        // find the entity to be updated
        UserEntity userEntity = userRepository.getOne(userId);
        UserEntity readingUserEntity = userRepository.getOne(readingUserId);

        // update reading list
        userEntity.getReadingUsers().add(readingUserEntity);

        // update readers list
        readingUserEntity.getReaders().add(userEntity);

        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userEntity));
        Optional.of(userRepository.save(readingUserEntity)).orElseThrow(() -> new PersistFailureException("Error while updating user"));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), User.class);
    }

    @Override
    public User updateReadingTopics(@NotNull Long topicId, @NotNull Long userId) {
        // find the entity to be updated
        UserEntity userEntity = userRepository.getOne(userId);
        TopicEntity topicEntity = topicRepository.getOne(topicId);

        // update reading topics list
        userEntity.getReadingTopics().add(topicEntity);

        final Optional<UserEntity> optionalEntity = Optional.of(userRepository.save(userEntity));
        return mapper.map(optionalEntity
                .orElseThrow(() -> new PersistFailureException("Error while updating user")), User.class);
    }

    @Override
    public void resetPassword(@NotNull final PasswordResetDTO passwordResetDTO) {
        final PasswordResetTokenEntity tokenEntity = validationService.verifyPasswordResetToken(passwordResetDTO);

        final UserEntity userEntity = userRepository.findOneByEmail(tokenEntity.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("No user found"));

        userEntity.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));

        Optional.of(userRepository.save(userEntity))
                .orElseThrow(() -> new PersistFailureException("error while user persistence"));
    }
}
