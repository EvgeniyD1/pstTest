package psttest.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psttest.demo.dao.UserRepository;
import psttest.demo.domain.Message;
import psttest.demo.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Find all Messages")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading messages"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @GetMapping
    public ResponseEntity<List<Message>> findAll() {
        List<Message> messages = messageService.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////
//    @ApiOperation(value = "Create Message")
//    @ApiResponses({
//            @ApiResponse(code = 201, message = "Successful creation message"),
//            @ApiResponse(code = 500, message = "Server error, something wrong")
//    })
//    /*костыль пока что*/
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "User database id", example = "1", required = true,
//                    dataType = "long", paramType = "path")
//    })
//    @PostMapping("/createMessage/{id}")
//    public ResponseEntity<Message> create(@PathVariable("id") Long userId, @RequestBody MessageRequest request) {
//        Message message = new Message();
//        message.setText(request.getText());
//        User user = userRepository.findById(userId).orElseThrow();
//        message.setUser(user);
//        return new ResponseEntity<>(messageRepository.save(message), HttpStatus.CREATED);
//    }

    @ApiOperation(value = "Update message")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful message update"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "message database id", example = "1", required = true,
                    dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") Long messageId,
                                                 @RequestBody MessageRequest request) {
        return new ResponseEntity<>(messageService.updateMessage(messageId, request), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Message")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete Message"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Message database id", example = "1", required = true,
                    dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") Long messageId) {
        return new ResponseEntity<>(messageService.deleteMessage(messageId), HttpStatus.OK);
    }
}
