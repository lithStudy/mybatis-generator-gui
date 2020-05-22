package ${cfg.viewConverterConf.packagePath};

import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ${cfg.paramConf.packagePath}.${entity}${cfg.paramConf.fileNameSuffix};
import ${cfg.voConf.packagePath}.${entity}${cfg.voConf.fileNameSuffix};

/**
 * <p>
 * ${table.comment!} 视图层实体转换类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}${cfg.viewConverterConf.fileNameSuffix}{


    public static ${entity}${cfg.voConf.fileNameSuffix} transBO2VO(${entity}${cfg.boConf.fileNameSuffix} bo){
        if(bo == null){
            return null;
        }
        ${entity}${cfg.voConf.fileNameSuffix} vo =new ${entity}${cfg.voConf.fileNameSuffix}();
<#list table.fields as field>
        vo.set${field.capitalName}(bo.get${field.capitalName}());
</#list>
        return vo;
    }

    public static List<${entity}${cfg.voConf.fileNameSuffix}> transListBO2VO(List<${entity}${cfg.boConf.fileNameSuffix}> boList){
        if (CollectionUtils.isEmpty(boList)) {
            return new ArrayList<>();
        }
        return boList.stream().map(${entity}${cfg.viewConverterConf.fileNameSuffix}::transBO2VO).collect(Collectors.toList());
    }


    public static ${entity}${cfg.boConf.fileNameSuffix} transParam2BO(${entity}${cfg.paramConf.fileNameSuffix} param){
        if(bo == null){
            return null;
        }
        ${entity}${cfg.boConf.fileNameSuffix} bo =new ${entity}${cfg.boConf.fileNameSuffix}();
    <#list table.fields as field>
         bo.set${field.capitalName}(param.get${field.capitalName}());
    </#list>
        return po;
    }

    public static List<${entity}${cfg.boConf.fileNameSuffix}> transListParam2BO(List<${entity}${cfg.paramConf.fileNameSuffix}> paramList){
        if (CollectionUtils.isEmpty(paramList)) {
            return new ArrayList<>();
        }
        return paramList.stream().map(${entity}${cfg.viewConverterConf.fileNameSuffix}::transParam2BO).collect(Collectors.toList());
    }

}
