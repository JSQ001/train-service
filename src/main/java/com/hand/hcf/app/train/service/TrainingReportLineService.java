package com.hand.hcf.app.train.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.train.domain.TrainingReportHeader;
import com.hand.hcf.app.train.domain.TrainingReportLine;
import com.hand.hcf.app.train.persistence.TrainingReportLineMapper;
import com.hand.hcf.app.train.utils.RespCode;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TrainingReportLineService extends BaseService<TrainingReportLineMapper,TrainingReportLine> {

    @Autowired
    private TrainingReportHeaderService headerService;


    public TrainingReportLine createTrainingReportLine(TrainingReportLine line){

        if (line.getId() != null) {
            throw new BizException(RespCode.SYS_ID_NOT_NULL);
        }
        if (line.getHeaderId() != null && headerService.selectById(line.getHeaderId()) != null) {
            baseMapper.insert(line);
        } else {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        return line;
    }

    public TrainingReportLine updateTrainingReportLine(TrainingReportLine line) {
        if (line.getId() == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        Long dbHeaderId = baseMapper.selectById(line.getId()).getHeaderId();
        if (line.getHeaderId() != null && line.getHeaderId().equals(dbHeaderId)) {
            baseMapper.updateById(line);
        } else {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        return line;
    }

    public void deleteTrainingReportLineById(Long id) {
        TrainingReportLine line = baseMapper.selectById(id);
        if (line == null) {
            throw new BizException(RespCode.TRAIN_LINE_NOT_EXISTS);
        }
        line.setDeleted(true);
        baseMapper.updateById(line);
    }

    public TrainingReportLine getTrainingReportLineById(Long id) {
        return baseMapper.selectById(id);
    }

    public void deleteTrainingReportLineByHeaderId(Long headerId) {
        if (headerId == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        TrainingReportLine line = new TrainingReportLine();
        line.setDeleted(true);
        baseMapper.update(line, new EntityWrapper<TrainingReportLine>().eq("header_id", headerId));
    }

    public List<TrainingReportLine> pageTrainingReportLineByHeaderId(Long headerId, Page page) {
        return baseMapper.selectPage(page, new EntityWrapper<TrainingReportLine>()
                .eq("header_id", headerId)
        );
    }

    public List<TrainingReportLine> listTrainingReportLineByHeaderId(Long headerId) {
        return baseMapper.selectList(new EntityWrapper<TrainingReportLine>()
                .eq("header_id", headerId)
        );
    }

    public TrainingReportLine insertOrUpdateTrainingReportLine(TrainingReportLine line){
        if (line.getId() == null) {
            createTrainingReportLine(line);
        }else {
            updateTrainingReportLine(line);
        }
        return line;
    }

    public List<TrainingReportLine> insertOrUpdateTrainingReportLineBatch(List<TrainingReportLine> lines) {
        lines.stream().forEach(line -> insertOrUpdateTrainingReportLine(line));
        return lines;
    }

    public BigDecimal updateHeaderAmount(Long headerId) {
        TrainingReportHeader header = new TrainingReportHeader();
        BigDecimal amount = baseMapper.getAmount(headerId);
        header.setId(headerId);
        header.setTotalAmount(amount);
        headerService.updateById(header);
        return amount;
    }
}
