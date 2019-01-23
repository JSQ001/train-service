package com.hand.hcf.app.train.enums;

import com.hand.hcf.core.enums.SysEnum;

public enum TrainingReportStatus implements SysEnum {
    NEW(1001), //编辑中
    SUBMIT(1002), //审批中
    WITHDRAW(1003), //撤回
    APPROVED(1004), //审批通过
    REJECTED(1005), //审批驳回
    ;

    private Integer id;

    TrainingReportStatus(Integer id){
        this.id = id;
    }

    public static TrainingReportStatus parse(String name) {
        for (TrainingReportStatus status : TrainingReportStatus.values()){
            if (status.name().equals(name)){
                return status;
            }
        }
        return null;
    }

    public static TrainingReportStatus parse(Integer id) {
        for (TrainingReportStatus status : TrainingReportStatus.values()){
            if (status.getId().equals(id)){
                return status;
            }
        }
        return null;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
