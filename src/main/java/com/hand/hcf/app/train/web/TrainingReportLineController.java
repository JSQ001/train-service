package com.hand.hcf.app.train.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.train.domain.TrainingReportLine;
import com.hand.hcf.app.train.service.TrainingReportLineService;
import com.hand.hcf.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training/report/line")
public class TrainingReportLineController {

    @Autowired
    private TrainingReportLineService trainingReportLineService;

    @PostMapping
    public TrainingReportLine createTrainingReportLine(@RequestBody TrainingReportLine line){
        trainingReportLineService.createTrainingReportLine(line);
        return line;
    }

    @PutMapping
    public TrainingReportLine updateTrainingReportLine(@RequestBody TrainingReportLine line){
        trainingReportLineService.updateTrainingReportLine(line);
        return line;
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingReportLineById(@PathVariable(value = "id") Long id) {
        trainingReportLineService.deleteTrainingReportLineById(id);
    }

    @GetMapping("/{id}")
    public TrainingReportLine getTrainingReportLineById(@PathVariable(value = "id") Long id) {
        return trainingReportLineService.getTrainingReportLineById(id);
    }

    @GetMapping("/pageByHeaderId")
    public ResponseEntity<List<TrainingReportLine>> getTrainingReportLineByHeaderId(@RequestParam(value = "headerId") Long headerId,
                                                                                    @RequestParam(value = "page",defaultValue = "0") int page,
                                                                                    @RequestParam(value = "size",defaultValue = "10") int size) {
        Page mybatisPage = PageUtil.getPage(page, size);

        List<TrainingReportLine> reportLineList = trainingReportLineService.pageTrainingReportLineByHeaderId(headerId, mybatisPage);
        HttpHeaders httpHeaders = PageUtil.getTotalHeader(mybatisPage);
        return new ResponseEntity<>(reportLineList, httpHeaders, HttpStatus.OK);
    }
}
