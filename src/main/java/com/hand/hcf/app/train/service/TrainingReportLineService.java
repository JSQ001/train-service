package com.hand.hcf.app.train.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.train.domain.TrainingReportLine;
import com.hand.hcf.app.train.persistence.TrainingReportLineMapper;
import com.hand.hcf.app.train.utils.RespCode;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainingReportLineService extends BaseService<TrainingReportLineMapper,TrainingReportLine> {

    @Autowired
    private TrainingReportLineMapper lineMapper;
    @Autowired
    private TrainingReportHeaderService headerService;


    public TrainingReportLine createTrainingReportLine(TrainingReportLine line){

        if (line.getId() != null) {
            throw new BizException(RespCode.SYS_ID_NOT_NULL);
        }
        checkData(line);
        if (line.getHeaderId() != null && headerService.selectById(line.getHeaderId()) != null) {
            lineMapper.insert(line);
        } else {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        return line;
    }

    public TrainingReportLine updateTrainingReportLine(TrainingReportLine line) {
        if (line.getId() == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        checkData(line);
        Long dbHeaderId = lineMapper.selectById(line.getId()).getHeaderId();
        if (line.getHeaderId() != null && line.getHeaderId().equals(dbHeaderId)) {
            lineMapper.updateById(line);
        } else {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        return line;
    }

    private void checkData(TrainingReportLine line) {
        if (lineMapper.selectList(new EntityWrapper<TrainingReportLine>()
                .ne(line.getId() != null, "id", line.getId())
                .eq(line.getInvoiceCode() != null, "invoice_code", line.getInvoiceCode())
        ).size() > 0) {
            throw new BizException(RespCode.TRAIN_CODE_REPEAT);
        }
    }

    public void deleteTrainingReportLineById(Long id) {
        TrainingReportLine line = lineMapper.selectById(id);
        if (line == null) {
            throw new BizException(RespCode.TRAIN_LINE_NOT_EXISTS);
        }
        line.setDeleted(true);
        line.setInvoiceCode(line.getInvoiceCode() + "_DELETED_" + RandomStringUtils.randomNumeric(6));
        lineMapper.updateById(line);
    }

    public TrainingReportLine getTrainingReportLineById(Long id) {
        return lineMapper.selectById(id);
    }

    public void deleteTrainingReportLineByHeaderId(Long headerId) {
        if (headerId == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        TrainingReportLine line = new TrainingReportLine();
        line.setDeleted(true);
        line.setInvoiceCode(line.getInvoiceCode() + "_DELETED_" + RandomStringUtils.randomNumeric(6));
        lineMapper.update(line, new EntityWrapper<TrainingReportLine>().eq("header_id", headerId));
    }

    public List<TrainingReportLine> pageTrainingReportLineByHeaderId(Long headerId, Page page) {
        return lineMapper.selectPage(page, new EntityWrapper<TrainingReportLine>()
                .eq("header_id", headerId)
        );
    }

    public List<TrainingReportLine> listTrainingReportLineByHeaderId(Long headerId) {
        return lineMapper.selectList(new EntityWrapper<TrainingReportLine>()
                .eq("header_id", headerId)
        );
    }

    public TrainingReportLine saveTrainingReportLine(TrainingReportLine line){
        if (line.getId() == null) {
            createTrainingReportLine(line);
        }else {
            updateTrainingReportLine(line);
        }
        return line;
    }

    public List<TrainingReportLine> saveTrainingReportLineBatch(List<TrainingReportLine> lines) {
        lines.stream().forEach(line -> saveTrainingReportLine(line));
        return lines;
    }
}
