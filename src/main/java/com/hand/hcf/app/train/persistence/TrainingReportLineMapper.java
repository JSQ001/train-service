package com.hand.hcf.app.train.persistence;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hand.hcf.app.train.domain.TrainingReportLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface TrainingReportLineMapper extends BaseMapper<TrainingReportLine> {
    BigDecimal getAmount(@Param("headerId") Long headerId);
}
