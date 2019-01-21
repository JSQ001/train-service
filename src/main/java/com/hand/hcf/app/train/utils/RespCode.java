package com.hand.hcf.app.train.utils;

public interface RespCode {
    String SYS_ID_NOT_NULL = "SYS_ID_NOT_NULL"; // id应该为空!
    String SYS_ID_NULL = "SYS_ID_NULL"; //id不应该为空!
    String TRAIN_HEADER_NOT_EXISTS = "TRAIN_HEADER_NOT_EXISTS"; //报销单头不存在！
    String TRAIN_LINE_NOT_EXISTS = "TRAIN_LINE_NOT_EXISTS"; //报销单行不存在！
    String TRAIN_CODE_REPEAT = "TRAIN_CODE_REPEAT"; //代码重复！
    String TRAIN_SUBMIT_ERROR = "TRAIN_SUBMIT_ERROR"; //提交失败！
}
