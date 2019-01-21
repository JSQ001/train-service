package com.hand.hcf.app.train.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.train.domain.TrainingReportHeader;
import com.hand.hcf.app.train.dto.TrainingReportDTO;
import com.hand.hcf.app.train.dto.TrainingReportHeaderDTO;
import com.hand.hcf.app.train.service.TrainingReportHeaderService;
import com.hand.hcf.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training/report/header")
public class TrainingReportHeaderController {

    /**
     * @apiDefine Train 新费控培训
     */

    @Autowired
    private TrainingReportHeaderService trainingReportHeaderService;

    @PostMapping
    public TrainingReportHeader createTrainingReportHeader(@RequestBody TrainingReportHeader header){
        trainingReportHeaderService.createTrainingReportHeader(header);
        return header;
    }

    @PutMapping
    public TrainingReportHeader updateTrainingReportHeader(@RequestBody TrainingReportHeader header){
        trainingReportHeaderService.updateTrainingReportHeader(header);
        return header;
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingReportHeaderById(@PathVariable(value = "id") Long id) {
        trainingReportHeaderService.deleteTrainingReportHeaderById(id);
    }

    @GetMapping("/{id}")
    public TrainingReportHeaderDTO getTrainingReportHeaderById(@PathVariable(value = "id") Long id) {
        return trainingReportHeaderService.getTrainingReportHeaderById(id);
    }

    @PostMapping("/dto/save")
    public TrainingReportDTO saveTrainingReportDTO(@RequestBody TrainingReportDTO dto) {
        trainingReportHeaderService.insertOrUpdateTrainingReportDTO(dto);
        return dto;
    }

    @DeleteMapping("/batch/delete")
    public void deleteTrainingReportHeaderBatch(@RequestBody List<Long> list) {
        trainingReportHeaderService.deleteTrainingReportHeaderBatch(list);
    }


    /**
     * @api {GET} /api/training/report/header/pageByCondition  【培训】条件查询报销单头
     * @apiDescription 条件查询报销单头的详细描述
     * @apiGroup Train
     * @apiParam {String} [businessCode]  报销单编码
     * @apiParam {String} [reportStatus]  报销单状态
     * @apiParam {Long} reportStatus  公司ID
     * @apiParam {int} [page]  第几页
     * @apiParam {int} [size]  每页数目
     * @apiSuccessExample {json} 成功返回值:
     * [
     *     {
     *         "id": "1087256348427571201",
     *         "businessCode": "ccc",
     *         "companyId": "1083751704185716737",
     *         "companyName": null,
     *         "unitId": "1084808610477871106",
     *         "unitName": null,
     *         "totalAmount": 1,
     *         "applicationId": "1085436751928553473",
     *         "applicationName": "测试1",
     *         "remark": null,
     *         "tenantId": "1083751703623680001",
     *         "setOfBooksId": "1083762150064451585",
     *         "setOfBooksName": null,
     *         "reportDate": "2019-01-20T01:13:16.9+08:00",
     *         "reportStatus": 1001,
     *         "reportStatusDesc": "编辑中"
     *     }
     * ]
     * * @apiErrorExample {json} 错误返回值:
     *  * {
     *  *       "message": "报账单头不存在！",
     *  *       "errorCode": "VALIDATION_ERROR",
     *  *       "category": "ERROR",
     *  *       "bizErrorCode": "TRAIN_HEADER_NOT_EXISTS"
     *  * }
     */
    @GetMapping("/pageByCondition")
    public ResponseEntity<List<TrainingReportHeaderDTO>> pageTrainingReportHeaderByCond(@RequestParam(required = false) Long applicationId,
                                                                                        @RequestParam(required = false) String businessCode,
                                                                                        @RequestParam(required = false) String reportStatus,
                                                                                        @RequestParam(required = false) Long companyId,
                                                                                        @RequestParam(value = "page",defaultValue = "0") int page,
                                                                                        @RequestParam(value = "size",defaultValue = "10") int size) {
        Page mybatisPage = PageUtil.getPage(page, size);
        List<TrainingReportHeaderDTO> headerDTOS = trainingReportHeaderService.pageTrainingReportHeaderByCond(applicationId, businessCode,
                reportStatus, companyId, mybatisPage);
        HttpHeaders httpHeaders = PageUtil.getTotalHeader(mybatisPage);
        return new ResponseEntity<>(headerDTOS, httpHeaders, HttpStatus.OK);
    }

//    @GetMapping("/dto/{headerId}/page")
//    public ResponseEntity getTrainingReportHeaderDetailsById(@PathVariable Long headerId,
//                                                             @RequestParam(value = "page",defaultValue = "0") int page,
//                                                             @RequestParam(value = "size",defaultValue = "10") int size) {
//        Page mybatisPage = PageUtil.getPage(page, size);
//        TrainingReportDTO trainingReportDTO = trainingReportHeaderService.getTrainingReportHeaderDetailsById(headerId, mybatisPage);
//        HttpHeaders headers = PageUtil.getTotalHeader(mybatisPage);
//        return new ResponseEntity<>(trainingReportDTO, headers, HttpStatus.OK);
//    }

    @PutMapping("/submit/{id}")
    public TrainingReportHeader submitTrainingReportHeader(@PathVariable Long id) {
        return trainingReportHeaderService.submitTrainingReportHeader(id);
    }
}
