package ${package.Controller};

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
<#if swagger2>
import lomdtok.extern.slf4j.Slf4j;
</#if>


/**
 * <p>
 * ${table.comment!} process
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(value = "${table.comment!} ",tags = "${table.comment!} ")
</#if>
@Slf4j
@Component
public class ${package.Entity}Process {

    @Autowired
    private ${entity}${cfg.serviceConf.fileNameSuffix} ${entity?uncap_first}${cfg.serviceConf.fileNameSuffix};


    public Page<${entity}${cfg.voConf.fileNameSuffix}> page(@RequestBody ${entity}${cfg.paramConf.fileNameSuffix} param) {
        log.info("${package.Entity}Process#page ====== param:{}",JSON.toJSONString(param));

        ${entity}${cfg.dtoConf.fileNameSuffix} queryDTO = ${entity}${cfg.viewConverterConf.fileNameSuffix}.transParam2DTO(param);

        Long totalNum = ${entity?uncap_first}${cfg.serviceConf.fileNameSuffix}.count(queryDTO);
        List<${entity}${cfg.voConf.fileNameSuffix}> pageVOList = null;
        if (totalNum > 0) {
          PageQuery pageQuery = new PageQuery(param.getPageNumber(), param.getPageSize());
          List<${entity}${cfg.dtoConf.fileNameSuffix}> retList = manager.pageList(queryDTO,pageQuery);
          pageVOList = ${entity}${cfg.viewConverterConf.fileNameSuffix}.transListDTO2VO(retList);
        }
        return Pages.page(param, pageVOList, totalNum);
    }



}
