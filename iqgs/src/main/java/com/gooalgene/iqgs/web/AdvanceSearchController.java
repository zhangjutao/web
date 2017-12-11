package com.gooalgene.iqgs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 高级搜索相关接口层
 * @author crabime
 * @since 12/11/2017
 * @version 2.0
 */
@Controller
@RequestMapping("/advance-search")
public class AdvanceSearchController {

    /**
     * @api {get} /advance-search/query-by-qtl-name 根据用户输入的qtl name进行模糊查询，返回模糊查询与之匹配的一组qtl name集合
     * @apiName queryByQTLName
     * @apiGroup Search
     *
     * @apiParam {String} qtlName 用户输入的qtl name
     *
     * @apiSuccessExample Success-Response:
     *      HTTP/1.1 200 OK
     *     {
     *       "id": "John",
     *       "qtlName": "Doe"
     *     }
     *
     */
    @RequestMapping(value = "/query-by-qtl-name", method = RequestMethod.GET)
    public List<String> queryByQTLName(String qtlName){
        return null;
    }
}
