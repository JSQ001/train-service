package com.hand.hcf.app.train.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hand.hcf.core.annotation.I18nField;
import com.hand.hcf.core.domain.DomainI18nEnable;
import lombok.Data;

/**
 *
 * @Autnor shouting.cheng
 * @date 2019/1/10
 */
@Data
@TableName("tra_message")
public class Message extends DomainI18nEnable {
    private String code;
    @I18nField
    private String name;
}
