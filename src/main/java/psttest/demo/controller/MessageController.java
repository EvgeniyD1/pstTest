package psttest.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psttest.demo.controller.requests.MessageRequest;
import psttest.demo.domain.Message;
import psttest.demo.domain.User;
import psttest.demo.service.MessageService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
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

    @ApiOperation(value = "Create Message")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful creation message"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/createMessage")
    public ResponseEntity<Message> create(@ApiIgnore @AuthenticationPrincipal User user,
                                          @RequestBody MessageRequest request) {
        return new ResponseEntity<>(messageService.create(user, request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update message")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful message update"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "message database id", example = "1", required = true,
                    dataType = "long", paramType = "path")
    })
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") Long messageId,
                                                 @ApiIgnore @AuthenticationPrincipal User user,
                                                 @RequestBody MessageRequest request) {
        Message messageFromDb = messageService.findById(messageId).orElseThrow();
        if (messageFromDb.getUser().getId().equals(user.getId())){
            messageFromDb.setText(request.getText());
            return new ResponseEntity<>(messageService.save(messageFromDb), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageFromDb, HttpStatus.FORBIDDEN);
        }
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
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") Long messageId) {
        return new ResponseEntity<>(messageService.deleteMessage(messageId), HttpStatus.OK);
    }
}
