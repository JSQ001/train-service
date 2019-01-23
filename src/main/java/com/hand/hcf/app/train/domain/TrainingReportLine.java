package com.hand.hcf.app.train.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hand.hcf.core.domain.DomainLogic;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@TableName("tra_report_line_14306_jsq")
public class TrainingReportLine extends DomainLogic {

    private Integer lineNumber;//行号

    private Long headerId;//报销单头表ID

    private String invoiceCode;//发票代码

    private String invoiceNumber;//发票号码

    private BigDecimal invoiceAmount;//发票金额

    private ZonedDateTime invoiceDate;//发票日期

    private String remark;//说明

}
