package ${cfg.converterConf.packagePath};

import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix};
import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};

/**
 * <p>
 * ${table.comment!} 实体转换类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}${cfg.converterConf.fileNameSuffix}{


    public static ${entity}${cfg.boConf.fileNameSuffix} transPO2BO(${entity}${cfg.poConf.fileNameSuffix} po){
        if(po == null){
            return null;
        }
        ${entity}${cfg.boConf.fileNameSuffix} bo =new ${entity}${cfg.boConf.fileNameSuffix}();
<#list table.fields as field>
        bo.set${field.capitalName}(po.get${field.capitalName}());
</#list>
        return bo;
    }

    public static List<${entity}${cfg.boConf.fileNameSuffix}> transListPO2BO(List<${entity}${cfg.poConf.fileNameSuffix}> poList){
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return poList.stream().map(${entity}${cfg.converterConf.fileNameSuffix}::transPO2BO).collect(Collectors.toList());
    }


    public static ${entity}${cfg.poConf.fileNameSuffix} transBO2PO(${entity}${cfg.boConf.fileNameSuffix} bo){
        if(bo == null){
            return null;
        }
        ${entity}${cfg.poConf.fileNameSuffix} po =new ${entity}${cfg.poConf.fileNameSuffix}();
    <#list table.fields as field>
         po.set${field.capitalName}(bo.get${field.capitalName}());
    </#list>
        return po;
    }

    public static List<${entity}${cfg.poConf.fileNameSuffix}> transListBO2PO(List<${entity}${cfg.boConf.fileNameSuffix}> boList){
        if (CollectionUtils.isEmpty(boList)) {
            return new ArrayList<>();
        }
        return boList.stream().map(${entity}${cfg.converterConf.fileNameSuffix}::transBO2PO).collect(Collectors.toList());
    }

}
