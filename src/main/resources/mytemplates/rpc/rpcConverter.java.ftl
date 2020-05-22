package ${cfg.rpcConverterConf.packagePath};

import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};
import ${cfg.dtoConf.packagePath}.${entity}${cfg.dtoConf.fileNameSuffix};

/**
 * <p>
 * ${table.comment!} rpc层实体转换类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}${cfg.rpcConverterConf.fileNameSuffix}{


    public static ${entity}${cfg.boConf.fileNameSuffix} transDTO2BO(${entity}${cfg.dtoConf.fileNameSuffix} dto){
        if(dto == null){
            return null;
        }
        ${entity}${cfg.boConf.fileNameSuffix} bo =new ${entity}${cfg.boConf.fileNameSuffix}();
<#list table.fields as field>
        bo.set${field.capitalName}(dto.get${field.capitalName}());
</#list>
        return bo;
    }

    public static List<${entity}${cfg.boConf.fileNameSuffix}> transListDTO2BO(List<${entity}${cfg.dtoConf.fileNameSuffix}> dtoList){
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(${entity}${cfg.rpcConverterConf.fileNameSuffix}::transDTO2BO).collect(Collectors.toList());
    }


    public static ${entity}${cfg.dtoConf.fileNameSuffix} transBO2DTO(${entity}${cfg.boConf.fileNameSuffix} bo){
        if(bo == null){
            return null;
        }
        ${entity}${cfg.dtoConf.fileNameSuffix} dto =new ${entity}${cfg.dtoConf.fileNameSuffix}();
    <#list table.fields as field>
         dto.set${field.capitalName}(bo.get${field.capitalName}());
    </#list>
        return dto;
    }

    public static List<${entity}${cfg.dtoConf.fileNameSuffix}> transListBO2DTO(List<${entity}${cfg.boConf.fileNameSuffix}> boList){
        if (CollectionUtils.isEmpty(boList)) {
            return new ArrayList<>();
        }
        return boList.stream().map(${entity}${cfg.rpcConverterConf.fileNameSuffix}::transBO2DTO).collect(Collectors.toList());
    }

}
