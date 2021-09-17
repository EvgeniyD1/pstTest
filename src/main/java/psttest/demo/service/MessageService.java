package psttest.demo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import psttest.demo.controller.requests.MessageRequest;
import psttest.demo.dao.MessageRepository;
import psttest.demo.domain.Message;
import psttest.demo.domain.User;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"message"})
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Cacheable(value = "message")
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Cacheable(value = "message")
    public Optional<Message> findById(Long messageId) {
        return messageRepository.findById(messageId);
    }

    @CacheEvict(value = "message", allEntries = true)
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @CacheEvict(value = "message", allEntries = true)
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @CacheEvict(value = "message", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public String deleteMessage(Long messageId) {
        Message messageFromDb = findById(messageId).orElseThrow();
        delete(messageFromDb);
        return "Message with ID = " + messageId + " deleted";
    }

    @CacheEvict(value = "message", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Message create(User user, MessageRequest request) {
        Message message = new Message();
        message.setText(request.getText());
        message.setUser(user);
        return save(message);
    }
}
