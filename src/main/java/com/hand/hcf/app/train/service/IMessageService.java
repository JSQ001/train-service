package com.hand.hcf.app.train.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hand.hcf.app.train.domain.Message;
import com.hand.hcf.app.train.persistence.MessageMapper;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseI18nService;
import com.hand.hcf.core.service.BaseService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Autnor shouting.cheng
 * @date 2019/1/10
 */
@Service
@Transactional
public class IMessageService extends BaseService<MessageMapper, Message> {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private BaseI18nService baseI18nService;

    public Message createMessage(Message message) {
        if (messageMapper.selectList(new EntityWrapper<Message>().eq("code", message.getCode())).size() > 0) {
            throw new BizException("TRAIN_CODE_REPEAT");
        }
        this.insert(message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (messageMapper.selectList(new EntityWrapper<Message>().ne("id", message.getId()).eq("code", message.getCode()))
                .size() > 0) {
            throw new BizException("TRAIN_CODE_REPEAT");
        }
        this.updateById(message);
        return message;
    }

    public void deleteMessage(Long id) {
        Message message = messageMapper.selectById(id);
        if (message == null) {
            throw new BizException("TRAIN_MESSAGE_NOT_EXIST");
        }
        message.setDeleted(true);
        String randomNumeric = RandomStringUtils.randomNumeric(6);
        message.setCode(message.getCode() + "_DELETED_" + randomNumeric);
        this.updateById(message);
    }

    public Message getMessage(Long id) {
        Message message = this.selectById(id);
        message.setI18n(baseI18nService.getI18nMap(Message.class, message.getId()));
        return message;
    }
}
