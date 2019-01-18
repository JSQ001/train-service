package com.hand.hcf.app.train.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.client.com.CompanyCO;
import com.hand.hcf.app.client.com.CompanyClient;
import com.hand.hcf.app.client.department.DepartmentCO;
import com.hand.hcf.app.client.department.DepartmentClient;
import com.hand.hcf.app.client.org.OrganizationClient;
import com.hand.hcf.app.client.org.SysCodeValueCO;
import com.hand.hcf.app.client.sob.SetOfBooksInfoCO;
import com.hand.hcf.app.client.sob.SobClient;
import com.hand.hcf.app.client.user.UserCO;
import com.hand.hcf.app.client.user.UserClient;
import com.hand.hcf.app.train.domain.TrainingReportHeader;
import com.hand.hcf.app.train.dto.TrainingReportDTO;
import com.hand.hcf.app.train.dto.TrainingReportHeaderDTO;
import com.hand.hcf.app.train.enums.TrainingReportStatus;
import com.hand.hcf.app.train.persistence.TrainingReportHeaderMapper;
import com.hand.hcf.app.train.utils.RespCode;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class TrainingReportHeaderService  extends BaseService<TrainingReportHeaderMapper,TrainingReportHeader> {

    @Autowired
    private TrainingReportHeaderMapper trainingReportHeaderMapper;
    @Autowired
    private TrainingReportLineService trainingReportLineService;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private SobClient sobClient;
    @Autowired
    private DepartmentClient departmentClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private OrganizationClient organizationClient;

    public TrainingReportHeader createTrainingReportHeader(TrainingReportHeader header) {

        if (header.getId() != null) {
            throw new BizException(RespCode.SYS_ID_NOT_NULL);
        }
        checkData(header);
        header.setReportStatus(TrainingReportStatus.NEW);
        trainingReportHeaderMapper.insert(header);
        return header;
    }

    public TrainingReportHeader updateTrainingReportHeader(TrainingReportHeader header) {

        if (header.getId() == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        checkData(header);
        trainingReportHeaderMapper.updateById(header);
        return header;
    }

    private void checkData(TrainingReportHeader header) {
        if (trainingReportHeaderMapper.selectList(new EntityWrapper<TrainingReportHeader>()
                .ne(header.getId() != null, "id", header.getId())
                .eq(header.getBusinessCode() != null, "business_code", header.getBusinessCode())
        ).size() > 0) {
            throw new BizException(RespCode.TRAIN_CODE_REPEAT);
        }
    }

    public TrainingReportHeader saveTrainingReportHeader(TrainingReportHeader header) {

        if (header.getId() == null) {
            createTrainingReportHeader(header);
        }else {
            updateTrainingReportHeader(header);
        }
        return header;
    }

    public void deleteTrainingReportHeaderById(Long id) {

        TrainingReportHeader header = trainingReportHeaderMapper.selectById(id);
        if (header == null) {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        header.setDeleted(true);
        header.setBusinessCode(header.getBusinessCode() + "_DELETED_" + RandomStringUtils.randomNumeric(6));
        trainingReportHeaderMapper.updateById(header);
        trainingReportLineService.deleteTrainingReportLineByHeaderId(header.getId());
    }

    public void deleteTrainingReportHeaderBatch(List<Long> list) {
        list.stream().forEach(this::deleteTrainingReportHeaderById);
    }

    public TrainingReportDTO saveTrainingReportDTO(TrainingReportDTO dto){
        saveTrainingReportHeader(toDomain(dto.getHeader()));
        dto.getLines().stream().forEach(line -> line.setHeaderId(dto.getHeader().getId()));
        trainingReportLineService.saveTrainingReportLineBatch(dto.getLines());
        return dto;
    }

    public TrainingReportHeaderDTO getTrainingReportHeaderById(Long id) {
        TrainingReportHeader header = trainingReportHeaderMapper.selectById(id);
        return toDTO(header);
    }

    public List<TrainingReportHeaderDTO> pageTrainingReportHeaderByCond(Long applicationId,
                                                                       String businessCode,
                                                                       String reportStatus,
                                                                       Long companyId,
                                                                       Page page) {
        List<TrainingReportHeader> headerList = trainingReportHeaderMapper.selectPage(page, new EntityWrapper<TrainingReportHeader>()
                .eq(applicationId != null, "application_id", applicationId)
                .like(businessCode != null, "business_code", businessCode)
                .eq(reportStatus!= null, "report_status", reportStatus)
                .eq(companyId != null, "company_id", companyId)
        );
        return toDTO(headerList);
    }

//    public TrainingReportDTO getTrainingReportHeaderDetailsById(Long id, Page page) {
//        TrainingReportDTO trainingReportDTO = new TrainingReportDTO();
//        trainingReportDTO.setHeader(toDTO(trainingReportHeaderMapper.selectById(id)));
//        trainingReportDTO.setLines(trainingReportLineService.listTrainingReportLineByHeaderId(id));
//        return trainingReportDTO;
//    }

    public TrainingReportHeader submitTrainingReportHeader(Long id) {
        TrainingReportHeader header = trainingReportHeaderMapper.selectById(id);
        if (header == null) {
            throw new BizException(RespCode.TRAIN_HEADER_NOT_EXISTS);
        }
        header.setReportStatus(TrainingReportStatus.SUBMIT);
        trainingReportHeaderMapper.updateById(header);
        return header;
    }

    public TrainingReportHeaderDTO toDTO(TrainingReportHeader domain){
        //设置相同属性
        TrainingReportHeaderDTO dto = mapperFacade.map(domain, TrainingReportHeaderDTO.class);

        //转化其他属性
        CompanyCO companyCO = companyClient.getById(domain.getCompanyId());
        if (companyCO != null) {
            dto.setCompanyName(companyCO.getName());
        }

        SetOfBooksInfoCO setOfBooksInfoCO = sobClient.getSetOfBooksById(domain.getSetOfBooksId());
        if (setOfBooksInfoCO != null) {
            dto.setSetOfBooksName(setOfBooksInfoCO.getSetOfBooksName());
        }

        DepartmentCO departmentCO = departmentClient.getDepartmentById(domain.getUnitId());
        if (departmentCO != null) {
            dto.setUnitName(departmentCO.getName());
        }

        UserCO userCO = userClient.getById(domain.getApplicationId());
        if (userCO != null) {
            dto.setApplicationName(userCO.getFullName());
        }

        dto.setReportStatusId(domain.getReportStatus().getId());
        SysCodeValueCO sysCodeValueCO = organizationClient.getSysCodeValueByCodeAndValue("2028", domain.getReportStatus().getId().toString());
        if (sysCodeValueCO != null) {
            dto.setReportStatusDesc(sysCodeValueCO.getName());
        }

        return dto;
    }

    public List<TrainingReportHeaderDTO> toDTO(List<TrainingReportHeader> domains){
        List<TrainingReportHeaderDTO> dtos = new ArrayList<>();
        Map<Long,String> userMap = new HashMap<>();

        Map<String,String> statusMap;
        List<SysCodeValueCO> codeValueCOS = organizationClient.listAllSysCodeValueByCode("2028");
        statusMap = codeValueCOS.stream().collect(Collectors.toMap(SysCodeValueCO::getValue, SysCodeValueCO::getName, (k1, k2) ->k1));

        for (TrainingReportHeader domain : domains){
            //设置相同属性
            TrainingReportHeaderDTO dto = mapperFacade.map(domain, TrainingReportHeaderDTO.class);

            //转化其他属性
            dto.setReportStatusId(domain.getReportStatus().getId());
            //先判断userMap存在数据不，不存在就去查
            if (!userMap.containsKey(domain.getApplicationId())) {
                // 获取用户名称
                UserCO userCO = userClient.getById(domain.getApplicationId());
                if (userCO != null) {
                    userMap.put(domain.getApplicationId(), userCO.getFullName());
                }
            }
            dto.setApplicationName(userMap.get(domain.getApplicationId()));

            dto.setReportStatusDesc(statusMap.get(domain.getReportStatus().getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public TrainingReportHeader toDomain(TrainingReportHeaderDTO dto){
        if (dto != null){
            TrainingReportHeader domain = mapperFacade.map(dto, TrainingReportHeader.class);

            domain.setReportStatus(TrainingReportStatus.parse(dto.getReportStatusId()));
            domain.setTotalAmount(BigDecimal.ZERO);
            return domain;
        }
        return null;
    }

    public List<TrainingReportHeader> toDomain(List<TrainingReportHeaderDTO> dtos){
        if (dtos != null){
            List domains = new ArrayList<TrainingReportHeader>();
            for (TrainingReportHeaderDTO dto : dtos){
                domains.add(toDomain(dto));
            }
            return domains;
        }
        return null;
    }
}
