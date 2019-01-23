package com.hand.hcf.app.train.web;

import com.hand.hcf.app.train.domain.Message;
import com.hand.hcf.app.train.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Autnor shouting.cheng
 * @date 2019/1/10
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService service;

    @PostMapping
    public Message createMessage(@RequestBody Message message){
        return service.createMessage(message);
    }

    @PutMapping
    public Message updateMessage(@RequestBody Message message){
        return service.updateMessage(message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") Long id){
        service.deleteMessage(id);
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable("id") Long id){
        return service.getMessage(id);
    }
}
