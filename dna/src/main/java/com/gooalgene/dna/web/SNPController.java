package com.gooalgene.dna.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.*;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.gooalgene.dna.entity.result.MinimumSNPResult;
import com.gooalgene.dna.service.*;
import com.gooalgene.utils.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/dna")
public class SNPController {
    Logger logger = LoggerFactory.getLogger(SNPController.class);

    @Autowired
    private IndexExplainService indexExplainService;

    @Autowired
    private DNAGensService dnaGensService;

    @Autowired
    private SNPService snpService;

    @Autowired
    private DNAMongoService dnaMongoService;

    @Autowired
    private DNARunService dnaRunService;
    @Autowired
    private DNAGenStructureService dnaGenStructureService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("mDNA/dna-index");
        model.addObject("dnaDetail", indexExplainService.queryByType("dna").getDetail());
        return model;
    }

    // 群体信息跳转接口
    @RequestMapping("/populationInfos")
    public String populationInfos() {
        return "population/infos";
    }

    /**
     * 按基因条件搜索
     */
    @RequestMapping("/queryByGene")
    @ResponseBody
    public Map<String, Object> QueryByGene(HttpServletRequest request, HttpServletResponse response) {
        String gene = request.getParameter("gene");
        logger.info("Gene:" + gene);
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        return dnaGensService.queryDNAGenesByGenes(gene, pageNo, pageSize);
    }

    @RequestMapping(value = "/fetch-all-chromosome", method = RequestMethod.GET)
    @ResponseBody
    public List<ChromosomeList> fetchAllChromosome() {
        return dnaGensService.fetchAllChromosome();
    }

    /**
     * 按基因条件搜索
     */
    @RequestMapping(value = "/queryByGroup", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map QueryByGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String group = request.getParameter("group");
        logger.info("QueryByGroup:" + group);
        Page<SampleInfoDto> page = new Page<>(request, response);
        return dnaRunService.queryDNARunByGroup(group, page);
    }

    /**
     * 查询SNP表格数据接口
     * @return SNP/INDEL查询页面表格内容
     */
    @RequestMapping(value = "/queryForTable", method = RequestMethod.POST)
    @ResponseBody
    public TableSearchResult queryForSNPTable(@RequestBody SearchCondition condition) throws IOException {
        TableSearchResult result = new TableSearchResult();
        String gene = condition.getGene();
        // Search in Region
        if (StringUtils.isEmpty(gene)) {
            result = snpService.searchSNPResult(condition.getType(), condition.getCtype(),
                    condition.getChromosome(), String.valueOf(condition.getStart()), String.valueOf(condition.getEnd()),
                    condition.getGroup(), condition.getPageNo(), condition.getPageSize());
        } else { // Search in Gene
            DNAGens dnaGens = dnaGensService.findByGeneId(gene);
            if (dnaGens != null) {
                String chromosome = dnaGens.getChromosome();
                // 修改geneStart/geneEnd映射
                long start = dnaGens.getStart();
                long end = dnaGens.getEnd();
                Long upstream = condition.getStart();  // start此时为upstream
                Long downstream = condition.getEnd();  // end此时为downstream
                // 判断用户是否有输入上下游区间
                if (null != upstream) {
                    start = start - upstream < 0 ? 0 : start - upstream;
                } else {
                    start = start - 2000 < 0 ? 0 : start - 2000;
                }
                if (null != downstream) {
                    end = end + downstream;
                } else {
                    end = end + 2000;
                }
                result = snpService.searchSNPResult(condition.getType(), condition.getCtype(), chromosome, String.valueOf(start),
                        String.valueOf(end), condition.getGroup(), condition.getPageNo(), condition.getPageSize());
            } else {
                logger.warn("传入基因" + gene + "不存在");
            }
        }
        return result;
    }

    /**
     * 根据图中当前位置的点，确定该SNP在查询结果表格中第几页、该页第几条
     *
     * @param condition 与侧边栏点击确定时传入相同的条件,注意:这里还需要增加额外的条件,点击位点的index值
     * @return 含有pageNo、offset、total及指定pageNo页面的数据,offset表示位点位于跳转页面的偏移量
     */
    @RequestMapping(value = "/jump-page", method = RequestMethod.POST)
    @ResponseBody
    public TableSearchResult jumpPageThroughSNPIndex(@RequestBody SearchCondition condition) throws IOException {
        TableSearchResult result = null;
        int index = condition.getIndex();
        String gene = condition.getGene();
        String chromosome = null;
        Long start = null;
        Long end = null;
        if (StringUtils.isNotEmpty(gene)) {
            DNAGens dnaGens = dnaGensService.findByGeneId(gene);
            if (dnaGens != null) {
                chromosome = dnaGens.getChromosome();
                // 修改geneStart/geneEnd映射
                start = dnaGens.getStart();
                end = dnaGens.getEnd();
                Long upstream = condition.getStart();  // start此时为upstream
                Long downstream = condition.getEnd();  // end此时为downstream
                // 判断用户是否有输入上下游区间
                if (null != upstream) {
                    start = start - upstream < 0 ? 0 : start - upstream;
                } else {
                    start = start - 2000 < 0 ? 0 : start - 2000;
                }
                if (null != downstream) {
                    end = end + downstream;
                } else {
                    end = end + 2000;
                }
            } else {
                logger.warn("传入基因" + gene + "不存在");
            }
        } else {
            start = condition.getStart();
            end = condition.getEnd();
        }
        Map<String, Integer> targetPageInfo = dnaMongoService.getStartIndex(condition.getType(), condition.getCtype(),
                chromosome, start, end, index, condition.getPageSize());
        // 获取targetIndex位置处的pageNo
        int pageNo = targetPageInfo.get("pageNo");
        int offset = targetPageInfo.get("offset");
        if (StringUtils.isEmpty(gene)) {
            result = snpService.searchSNPResult(condition.getType(), condition.getCtype(),
                    condition.getChromosome(), String.valueOf(condition.getStart()), String.valueOf(condition.getEnd()),
                    condition.getGroup(), pageNo, condition.getPageSize());
        } else {
            result = snpService.searchSNPResult(condition.getType(), condition.getCtype(), chromosome, String.valueOf(start),
                    String.valueOf(end), condition.getGroup(), pageNo, condition.getPageSize());
        }
        // Java按应用传递,offset在这里设值才能生效
        result.setOffset(offset);
        result.setPageNo(pageNo);
        return result;
    }

    /**
     * DNA数据库获取图形界面数据，如果在区间中查找，在该区间中如有基因，则显示第一个基因的SNP位点及基因结构数据，
     * 如果在该区间内无基因，只需返回该区间内的SNP位点及基因结构数据
     *
     * @param condition 搜索条件
     * @return 含有SNP/Structure数据的集合
     * @throws IOException
     */
    @RequestMapping(value = "/fetch-point", method = RequestMethod.POST)
    @ResponseBody
    public GraphSearchResult fetchAllPoint(@RequestBody SearchCondition condition) throws IOException {
        String gene = condition.getGene();
        GraphSearchResult result = new GraphSearchResult();
        // Search in Region
        if (StringUtils.isEmpty(gene)) {
            // 获取该范围内的所有gene_id
            Set<String> geneIds = dnaGensService.getByRegionNoCompare(condition.getChromosome(), condition.getStart(),
                    condition.getEnd());
            // 如果用户查询为区间查询，且该区间内存在基因，这里默认取第一个基因作为图形数据查询条件
            if (geneIds.size() > 0) {
                result = searchOnlyByGene(geneIds.iterator().next(), condition.getType(),
                        condition.getStart(), condition.getEnd(), false);
                // 将查询出来的区间内的基因返回给前台
                result.setGeneInsideRegion(geneIds);
            } else {
                // 如果该区间内不包含基因，直接搜索该区间内所有SNP位点，无需返回基因结构
                List<MinimumSNPResult> allSNP = dnaMongoService.searchSNPIdAndPos(condition.getType(), condition.getChromosome(),
                        condition.getStart(), condition.getEnd());
                // 放入所有查询到的SNP位点（包含INDEX）
                result.setSnpList(allSNP);
            }
        } else { // Search in Gene
            result = searchOnlyByGene(gene, condition.getType(), condition.getStart(), condition.getEnd(), true);
        }
        return result;
    }

    /**
     * 在根据基因条件查询下，先查询该基因在染色体上的位置，根据起始位置，根据用户输入的上下游区间，按上游减两千
     * 下游加两千的规则，查询该区间内的所有SNP位点及当前基因的结构
     *
     * @param gene 基因ID
     * @param type SNP/INDEL
     * @param upstream 上游值
     * @param downstream 下游值
     * @param originateFromGene 发送过来的请求是从区域内查找还是根据基因查找，如果从区域内查找发现该区域内存在基因，
     *      那会选取第一个基因，画该基因的基因结构，但是该基因是不需要加用户输入的上下游限制的，这里就是false，
     *      但是需要加上默认的2000上下游区间,如果请求是从基因中查找，那这里需要加上上下游限制
     * @return 最终显示在页面上的图形数据，包含基因结构和所有SNP位点
     */
    private GraphSearchResult searchOnlyByGene(String gene, String type, Long upstream, Long downstream, boolean originateFromGene) {
        if (null == gene || gene.equals("")) {
            throw new IllegalArgumentException("传入gene ID未空");
        }
        GraphSearchResult result = new GraphSearchResult();
        DNAGens dnaGens = dnaGensService.findByGeneId(gene);
        if (dnaGens != null) {
            String chromosome = dnaGens.getChromosome();
            // 修改geneStart/geneEnd映射
            long start = dnaGens.getStart();
            long end = dnaGens.getEnd();
            // 如果是按基因查找，这里需要加上下游，如果按区域查找，不需要加限制
            if (originateFromGene) {
                // 判断用户是否有输入上下游区间
                if (null != upstream) {
                    start = start - upstream < 0 ? 0 : start - upstream;
                } else {
                    start = start - 2000 < 0 ? 0 : start - 2000;
                }
                if (null != downstream) {
                    end = end + downstream;
                } else {
                    end = end + 2000;
                }
            } else {
                start = start - 2000 < 0 ? 0 : start - 2000;
                end = end + 2000;
            }
            List<MinimumSNPResult> allSNP = dnaMongoService.searchSNPIdAndPos(type, chromosome, start, end);
            result.setSnpList(allSNP);
            // 查询当前基因的基因结构
            List<DNAGenStructureDto> dnaGenStructures = dnaGenStructureService.getByGeneId(gene);
            result.setStructureList(dnaGenStructures);
            result.setGeneId(gene);
            result.setUpstream(start);
            result.setDownstream(end);
        } else {
            logger.warn("传入基因" + gene + "不存在");
        }
        return result;
    }

    /**
     * 点选SNP Id或INDEL Id时根据相应id进行样本相关信息查询
     */
    @RequestMapping("/findSampleById")
    @ResponseBody
    public ResultVO genotypePercentById(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null) {
            return ResultUtil.error(-1, "未拿到id的值");
        }
        Map result = snpService.findSampleById(id);
        if (result.size() == 0) {
            return ResultUtil.error(-1, "无对应id");
        }
        return ResultUtil.success(result);
    }

    /**
     * 跳转到变异位点详情页面
     *
     * @param snpId SNP ID值
     */
    @RequestMapping(value = "/snp/info", method = RequestMethod.GET)
    public ModelAndView getInfo(@RequestParam("snpId") String snpId) {
        ModelAndView modelAndView = new ModelAndView("snpinfo/snpinfo");
        Map result = snpService.findSampleById(snpId);
        SNP snpFormatMajorFreq;
        if (result.containsKey("snpData")) {
            snpFormatMajorFreq = (SNP) result.get("snpData");
        } else {
            snpFormatMajorFreq = (SNP) result.get("INDELData");
        }
        double major = snpFormatMajorFreq.getMajor();
        BigDecimal decimal = new BigDecimal(major);
        BigDecimal majorForBigDecimal = decimal.multiply(new BigDecimal(100));
        StringBuffer convertValue = new StringBuffer();
        StringBuffer finalResult = new DecimalFormat("###0.00").format(majorForBigDecimal, convertValue, new FieldPosition(NumberFormat.INTEGER_FIELD));
        snpFormatMajorFreq.setMajor(major); //将转换后的值反设值到SNP对象中
        modelAndView.addObject("major", finalResult);
        modelAndView.addObject("snpId", snpId);
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    /**
     * 区分minor和major
     */
    @RequestMapping(value = "/changeByProportion", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO changeByProportion(@RequestParam("snpId") String snpId, @RequestParam("changeParam") String changeParam,
                                       @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                       @RequestParam(value = "pageSize", required = false) String isPage,
                                       @RequestParam("judgeAllele")String judgeAllele,
                                       SampleInfoDto sampleInfoDto) {
        Map result = snpService.findSampleById(snpId);
        SNP snpTemp = (SNP) result.get("snpData");
        String type = "snp";
        if (snpTemp == null) {
            type = "indel";
            snpTemp = (SNP) result.get("INDELData");
        }
        Map map = (Map) snpTemp.getSamples();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        List<String> runNos = Lists.newArrayList();
        Map samples = Maps.newHashMap();
        for (Map.Entry entry : entrySet) {
            String value = (String) entry.getValue();
            if (StringUtils.isNotBlank(changeParam)) {
                if (type.equals("indel")) {
                    String majAndchangePa= snpTemp.getMajorallen() + changeParam;
                    String changePaAndMin = changeParam + snpTemp.getMinorallen();
                    if (value.equalsIgnoreCase(majAndchangePa) || value.equalsIgnoreCase(changePaAndMin)) {
                        String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                        Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                        Matcher matcher = regexp.matcher(singleRunNo);
                        if (!matcher.find()) {
                            singleRunNo = singleRunNo + ".0";
                        }
                        runNos.add(singleRunNo);
                        samples.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    if(StringUtils.equalsIgnoreCase(judgeAllele,"major")){
                        if(value.contains(snpTemp.getMajorallen())){
                            if (StringUtils.containsIgnoreCase(value, changeParam)) {
                                String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                                Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                                Matcher matcher = regexp.matcher(singleRunNo);
                                if (!matcher.find()) {
                                    singleRunNo = singleRunNo + ".0";
                                }
                                runNos.add(singleRunNo);
                                samples.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }else {
                        if(value.contains(snpTemp.getMinorallen())){
                            if (StringUtils.containsIgnoreCase(value, changeParam)) {
                                String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                                Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                                Matcher matcher = regexp.matcher(singleRunNo);
                                if (!matcher.find()) {
                                    singleRunNo = singleRunNo + ".0";
                                }
                                runNos.add(singleRunNo);
                                samples.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
            }
        }
        if (samples.size() <= 0||runNos.size()<=0) {
            Map response = Maps.newHashMap();
            PageInfo<DNARunSearchResult> dnaRuns = new PageInfo<>();
            response.put("dnaRuns", dnaRuns);
            return ResultUtil.success(response);
        }
        Map response = Maps.newHashMap();
        sampleInfoDto.setRunNos(runNos);
        PageInfo<SampleInfoDto> dnaRuns = dnaRunService.getListByConditionWithTypeHandler(sampleInfoDto, pageNum, pageSize, isPage);
        response.put("dnaRuns", dnaRuns);
        response.put("samples", samples);
        return ResultUtil.success(response);
    }

    @RequestMapping(value = "/getByCultivar",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getByCultivar(@RequestParam("names") List<String> ids,
                                  @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return ResultUtil.success(dnaRunService.getByCultivar(ids,pageNum,pageSize));
    }
}
