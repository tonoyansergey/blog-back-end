package am.egs.service.impl;

import am.egs.model.bean.ArticleBean;
import am.egs.model.bean.TopicBean;
import am.egs.model.dto.User;
import am.egs.model.entity.ArticleEntity;
import am.egs.model.entity.TopicEntity;
import am.egs.model.entity.UserEntity;
import am.egs.repository.ArticleRepository;
import am.egs.service.ArticleService;
import am.egs.util.exception.PersistFailureException;
import am.egs.util.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private ModelMapper mapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper mapper) {
        this.articleRepository = articleRepository;
        this.mapper = mapper;
    }

    @Override
    public ArticleBean getById(@NotNull Long id) {
        final Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        return mapper.map(optionalArticle
                .orElseThrow(() -> new RecordNotFoundException("Articles not found")), ArticleBean.class);
    }

    @Override
    public List<ArticleBean> getAll() {
        final Optional<List<ArticleEntity>> optionalList = Optional.of(articleRepository.findAll());
        return mapper.map(optionalList
                .orElseThrow(() -> new RecordNotFoundException("Articles not found")),new TypeToken<List<ArticleBean>>() {}.getType());
    }

    @Override
    public List<ArticleBean> getAllByTopic(@NotNull TopicBean topic) {
        final Optional<List<ArticleEntity>> optionalList = articleRepository.findAllByTopic(mapper.map(topic, TopicEntity.class));
        return mapper.map(optionalList
                .orElseThrow(() -> new RecordNotFoundException("Articles not found")),new TypeToken<List<ArticleBean>>() {}.getType());
    }

    @Override
    public List<ArticleBean> getAllByUser(@NotNull User user) {
        final Optional<List<ArticleEntity>> optionalList = articleRepository.findAllByUser(mapper.map(user, UserEntity.class));
        return mapper.map(optionalList
                .orElseThrow(() -> new RecordNotFoundException("Articles not found")),new TypeToken<List<ArticleBean>>() {}.getType());
    }

    @Override
    public ArticleBean save(@NotNull ArticleBean article) {
        final Optional<ArticleEntity> articleEntity = Optional.of(articleRepository.save(mapper.map(article, ArticleEntity.class)));
        return mapper.map(articleEntity
                .orElseThrow(() -> new PersistFailureException("Error while saving article")), ArticleBean.class);
    }

    @Override
    public ArticleBean update(@NotNull ArticleBean article) {
        // find the entity to be updated
        ArticleEntity articleToUpdate = articleRepository.getOne(article.getId());

        // set the new values
        articleToUpdate.setTitle(article.getTitle());
        articleToUpdate.setDescription(article.getDescription());
        articleToUpdate.setContent(article.getContent());
        articleToUpdate.setPhoto(article.getPhoto());
        articleToUpdate.setTopic(mapper.map(article.getTopic(), TopicEntity.class));
        articleToUpdate.setUser(mapper.map(article.getUser(), UserEntity.class));

        // update the entity
        final Optional<ArticleEntity> optionalArticle = Optional.of(articleRepository.save(articleToUpdate));

        return mapper.map(optionalArticle
                .orElseThrow(() -> new PersistFailureException("Error while updating topic")), ArticleBean.class);
    }
}
