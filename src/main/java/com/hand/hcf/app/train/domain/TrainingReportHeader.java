package com.hand.hcf.app.train.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hand.hcf.core.domain.DomainLogic;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@TableName("tra_report_header")
public class TrainingReportHeader extends DomainLogic {

    private String businessCode;//对公报销单头编码

    private Long companyId;

    private Long unitId;

    private BigDecimal totalAmount;//行总金额

    private Long applicationId;//申请人ID

    private String remark;//说明

    private Long tenantId;//租户ID

    private Long setOfBooksId;//账套ID

    private ZonedDateTime reportDate;//报账日期

    private Integer reportStatus;//报账单状态

}
