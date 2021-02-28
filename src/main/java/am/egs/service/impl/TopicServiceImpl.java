package am.egs.service.impl;

import am.egs.model.bean.TopicBean;
import am.egs.model.entity.ArticleEntity;
import am.egs.model.entity.TopicEntity;
import am.egs.repository.TopicRepository;
import am.egs.service.TopicService;
import am.egs.util.exception.PersistFailureException;
import am.egs.util.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;
    private ModelMapper mapper;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, ModelMapper mapper) {
        this.topicRepository = topicRepository;
        this.mapper = mapper;
    }

    @Override
    public TopicBean getById(@NotNull Long id) {
        final Optional<TopicEntity> optionalTopic = topicRepository.findById(id);
        return mapper.map(optionalTopic
                .orElseThrow(() -> new RecordNotFoundException("Topics not found")), TopicBean.class);
    }

    @Override
    public List<TopicBean> getAll() {
        final Optional<List<TopicEntity>> optionalTopics = Optional.of(topicRepository.findAll());
        return mapper.map(optionalTopics
                .orElseThrow(() -> new RecordNotFoundException("Topics not found")), new TypeToken<List<TopicBean>>() {}.getType());
    }

    @Override
    public TopicBean getByName(@NotBlank String name) {
        final Optional<TopicEntity> optionalTopic = topicRepository.findByName(name);
        return mapper.map(optionalTopic
                .orElseThrow(() -> new RecordNotFoundException("Topics not found")), TopicBean.class);
    }

    @Override
    public TopicBean save(@NotNull TopicBean topic) {
        final Optional<TopicEntity> optionalTopic = Optional.of(topicRepository.save(mapper.map(topic, TopicEntity.class)));
        return mapper.map(optionalTopic
                .orElseThrow(() -> new PersistFailureException("Error while saving topic")), TopicBean.class);
    }

    @Override
    public TopicBean update(@NotNull TopicBean topic) {
        // find the entity to be updated
        TopicEntity topicToUpdate = topicRepository.getOne(topic.getId());

        // set the new values
        topicToUpdate.setName(topic.getName());
        topicToUpdate.setDefinition(topic.getDefinition());

        //update the entity
        final Optional<TopicEntity> optionalTopic = Optional.of(topicRepository.save(topicToUpdate));

        return mapper.map(optionalTopic
                .orElseThrow(() -> new PersistFailureException("Error while updating topic")), TopicBean.class);
    }
}
