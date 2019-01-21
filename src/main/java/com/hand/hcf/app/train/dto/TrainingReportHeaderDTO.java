package com.hand.hcf.app.train.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class TrainingReportHeaderDTO {

    private Long id;
    private String businessCode;//对公报销单头编码
    private Long companyId;
    private String companyName;//公司
    private Long unitId;
    private String unitName;//部门
    private BigDecimal totalAmount;//行总金额
    private Long applicationId;//申请人ID
    private String applicationName;//申请人
    private String remark;//说明
    private Long tenantId;//租户ID
    private Long setOfBooksId;//账套ID
    private String setOfBooksName;//账套名称
    private ZonedDateTime reportDate;//报账日期
    private Integer reportStatus;//报账单状态
    private String reportStatusDesc;//报账单状态描述
}
