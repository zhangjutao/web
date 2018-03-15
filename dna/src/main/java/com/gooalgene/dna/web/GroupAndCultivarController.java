package com.gooalgene.dna.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class GroupAndCultivarController {
    Logger logger = LoggerFactory.getLogger(SNPController.class);

    @Autowired
    DNARunService dnaRunService;

    @RequestMapping(value = "/dna/condition", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getByExample(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                 @RequestParam(value = "isPage", required = false) String isPage,
                                 DnaRunDto dnaRunDto) {
        logger.info(dnaRunDto.getGroup());
        PageInfo<DNARunSearchResult> dnaRunPageInfo = dnaRunService.getListByConditionWithTypeHandler(dnaRunDto, pageNum, pageSize, isPage);
        return ResultUtil.success(dnaRunPageInfo);

    }
}
