/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.enums;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @author lith
 * @version V1.0
 * @since 2020-05-25 17:05
 */
public enum ConfNameEnum {
    MAPPER("mapperConf", "Mapper", StringPool.DOT_XML,"/mytemplates/mapper.xml.ftl","mapper"),
    PO("poConf","PO",StringPool.DOT_JAVA,"/mytemplates/entityPO.java.ftl","po"),
    BO("boConf","BO",StringPool.DOT_JAVA,"/mytemplates/entityBO.java.ftl", "bo"),
    DAO("daoConf","DAO",StringPool.DOT_JAVA,"/mytemplates/mapper.java.ftl", "dao"),
    MANAGER("managerConf","Manager",StringPool.DOT_JAVA,"/mytemplates/manager.java.ftl", "manager"),
    MANAGER_IMPL("managerImplConf","ManagerImpl",StringPool.DOT_JAVA,"/mytemplates/managerImpl.java.ftl","managerImpl"),
    CONVERTER("converterConf","Converter",StringPool.DOT_JAVA,"/mytemplates/converter.java.ftl", "converter"),

    DTO("dtoConf", "DTO",StringPool.DOT_JAVA,"/mytemplates/rpc/entityDTO.java.ftl","dto"),
    RPC("rpcConf", "Service",StringPool.DOT_JAVA,"/mytemplates/rpc/rpc.java.ftl","rpc"),
    RPC_IMPL("rpcImplConf", "ServiceImpl",StringPool.DOT_JAVA,"/mytemplates/rpc/rpcImpl.java.ftl","rpcImpl"),
    RPC_CONVERTER("rpcConverterConf", "RpcConverter",StringPool.DOT_JAVA,"/mytemplates/rpc/rpcConverter.java.ftl","rpcConverter"),
    ;

    ConfNameEnum(String name, String fileNameSuffix,String fileSuffix,String templatePath, String desc) {
        this.name = name;
        this.desc = desc;
        this.fileNameSuffix = fileNameSuffix;
        this.fileSuffix = fileSuffix;
        this.templatePath = templatePath;
    }

    private String name;

    /**
     * 文件名后缀
     */
    private String fileNameSuffix;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 模板地址
     */
    private String templatePath;

    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public static Boolean isRpc(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("名称不能为空");
        }
        if (name.equals(RPC.getName()) || name.equals(RPC_IMPL.getName()) || name.equals(DTO.getName()) || name
            .equals(RPC_CONVERTER.getName())) {
            return true;
        }
        return false;
    }
}
