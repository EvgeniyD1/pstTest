package psttest.demo.service;

import org.springframework.stereotype.Service;
import psttest.demo.controller.MessageRequest;
import psttest.demo.dao.MessageRepository;
import psttest.demo.domain.Message;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll(){
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long messageId){
        return messageRepository.findById(messageId);
    }

    public Message save(Message message){
        return messageRepository.save(message);
    }

    public void delete(Message message){
        messageRepository.delete(message);
    }

    public Message updateMessage(Long messageId, MessageRequest request){
        Message messageFromDb = findById(messageId).orElseThrow();
        messageFromDb.setText(request.getText());
        return save(messageFromDb);
    }

    public String deleteMessage(Long messageId){
        Message messageFromDb = findById(messageId).orElseThrow();
        messageRepository.delete(messageFromDb);
        return "Message with ID = " + messageId + " deleted";
    }
}
