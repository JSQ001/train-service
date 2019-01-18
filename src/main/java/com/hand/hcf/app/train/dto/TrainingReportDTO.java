package com.hand.hcf.app.train.dto;

import com.hand.hcf.app.train.domain.TrainingReportLine;
import lombok.Data;

import java.util.List;

@Data
public class TrainingReportDTO {
    private TrainingReportHeaderDTO header;
    private List<TrainingReportLine> lines;
}
