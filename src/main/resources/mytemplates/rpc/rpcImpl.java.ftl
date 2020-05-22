package ${cfg.rpcImplConf.packagePath};

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.guahao.convention.data.domain.Page;
import com.guahao.convention.data.domain.PageQuery;
import com.guahao.convention.data.domain.Pages;
import com.guahao.convention.data.domain.Result;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import ${cfg.rpcConf.packagePath}.${entity}${cfg.rpcConf.fileNameSuffix};
import ${cfg.managerConf.packagePath}.${entity}${cfg.managerConf.fileNameSuffix};
import ${cfg.dtoConf.packagePath}.${entity}${cfg.dtoConf.fileNameSuffix};
import ${cfg.rpcConverterConf.packagePath}.${entity}${cfg.rpcConverterConf.fileNameSuffix};
import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};



/**
 * <p>
 * ${table.comment!} process
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

@Slf4j
@Component
@Service(interfaceClass = ${entity}${cfg.rpcConf.fileNameSuffix}.class)
public class ${entity}${cfg.rpcImplConf.fileNameSuffix} implements ${entity}${cfg.rpcConf.fileNameSuffix}{

    @Autowired
    private ${entity}${cfg.managerConf.fileNameSuffix} ${entity?uncap_first}${cfg.managerConf.fileNameSuffix};


    public Result<Page<${entity}${cfg.dtoConf.fileNameSuffix}>> page(${entity}${cfg.dtoConf.fileNameSuffix} dto) {
        log.info("${entity}${cfg.rpcImplConf.fileNameSuffix}#page ====== dto:{}",JSON.toJSONString(dto));

        ${entity}${cfg.boConf.fileNameSuffix} queryBO = ${entity}${cfg.rpcConverterConf.fileNameSuffix}.transDTO2BO(dto);

        Long totalNum = ${entity?uncap_first}${cfg.managerConf.fileNameSuffix}.count(queryBO);
        List<${entity}${cfg.dtoConf.fileNameSuffix}> pageDTOList = null;
        if (totalNum > 0) {
          PageQuery pageQuery = new PageQuery(param.getPageNumber(), param.getPageSize());
          List<${entity}${cfg.boConf.fileNameSuffix}> retList = ${entity?uncap_first}${cfg.managerConf.fileNameSuffix}.pageList(queryBO,pageQuery);
          pageDTOList = ${entity}${cfg.rpcConverterConf.fileNameSuffix}.transListBO2DTO(retList);
        }
        return Pages.page(param, pageDTOList, totalNum);
    }



}
