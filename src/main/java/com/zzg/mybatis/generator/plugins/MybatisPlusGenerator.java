/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.plugins;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zzg.mybatis.generator.model.DatabaseConfig;
import com.zzg.mybatis.generator.model.GeneratorConfig;
import com.zzg.mybatis.generator.model.PlusGeneratorInjectionConf;
import com.zzg.mybatis.generator.util.MyStringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO
 *
 * @author lith
 * @version V1.0
 * @since 2020-05-18 15:17
 */
public class MybatisPlusGenerator {


    public static void generator(DatabaseConfig selectedDatabaseConfig, GeneratorConfig generatorConfig) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        mpg.setDataSource(dsc);
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        mpg.setStrategy(strategy);
        //包配置
        PackageConfig packageInfo = new PackageConfig();
        mpg.setPackageInfo(packageInfo);

        //实体名
        String entityName = generatorConfig.getEntityName();
        /**
         * 全局配置
         */
        gc.setAuthor("lith");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setEntityName(entityName);
        //是否使用localDate
        if (!generatorConfig.isJsr310Support()) {
            gc.setDateType(DateType.ONLY_DATE);
        }

        /**
         * 策略配置
         */
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(generatorConfig.isUseLombokPlugin());
        //指定表名
        strategy.setInclude(generatorConfig.getTableName());

        /**
         * 数据源配置
         */
        dsc.setDriverName("com.mysql.jdbc.Driver");
        StringBuilder mysqlUri = new StringBuilder("jdbc:mysql://").append(selectedDatabaseConfig.getHost()).append(":")
            .append(selectedDatabaseConfig.getPort()).append("/").append(selectedDatabaseConfig.getSchema())
            .append("?useUnicode=true&characterEncoding=").append(selectedDatabaseConfig.getEncoding())
            .append("&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
        dsc.setUrl(mysqlUri.toString());
        dsc.setUsername(selectedDatabaseConfig.getUsername());
        dsc.setPassword(selectedDatabaseConfig.getPassword());

        /**
         * 自定义配置
         */
        PlusGeneratorInjectionConf mapperConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/mapper.xml.ftl")
            .path(generatorConfig.getMapperTargetProject())
            .packagePath(generatorConfig.getMapperPackage())
            .fileNameSuffix("Mapper")
            .fileSuffix(StringPool.DOT_XML)
            .build();

        PlusGeneratorInjectionConf poConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/entityPO.java.ftl")
            .path(generatorConfig.getPoTargetProject())
            .packagePath(generatorConfig.getPoPackage())
            .fileNameSuffix("PO")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf boConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/entityBO.java.ftl")
            .path(generatorConfig.getBoTargetProject())
            .packagePath(generatorConfig.getBoPackage())
            .fileNameSuffix("BO")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf daoConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/mapper.java.ftl")
            .path(generatorConfig.getDaoTargetProject())
            .packagePath(generatorConfig.getDaoPackage())
            .fileNameSuffix("DAO")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf managerConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/manager.java.ftl")
            .path(generatorConfig.getManagerTargetProject())
            .packagePath(generatorConfig.getManagerPackage())
            .fileNameSuffix("Manager")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf managerImplConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/managerImpl.java.ftl")
            .path(generatorConfig.getManagerImplTargetProject())
            .packagePath(generatorConfig.getManagerImplPackage())
            .fileNameSuffix("ManagerImpl")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf converterConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/converter.java.ftl")
            .path(generatorConfig.getTransTargetProject())
            .packagePath(generatorConfig.getTransPackage())
            .fileNameSuffix("Converter")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();



        //RPC
        PlusGeneratorInjectionConf dtoConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/rpc/entityDTO.java.ftl")
            .path(generatorConfig.getTransTargetProject())
            .packagePath("dto")
//            .packagePath(generatorConfig.getTransPackage())
            .fileNameSuffix("DTO")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf rpcConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/rpc/rpc.java.ftl")
            .path(generatorConfig.getTransTargetProject())
            .packagePath("rpc")
            //            .packagePath(generatorConfig.getTransPackage())
            .fileNameSuffix("Service")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf rpcImplConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/rpc/rpcImpl.java.ftl")
            .path(generatorConfig.getTransTargetProject())
            .packagePath("rpc.impl")
            //            .packagePath(generatorConfig.getTransPackage())
            .fileNameSuffix("ServiceImpl")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();

        PlusGeneratorInjectionConf rpcConverterConf = PlusGeneratorInjectionConf.builder()
            .templatePath("/mytemplates/rpc/rpcConverter.java.ftl")
            .path(generatorConfig.getTransTargetProject())
            .packagePath("converter")
            //            .packagePath(generatorConfig.getTransPackage())
            .fileNameSuffix("RpcConverter")
            .fileSuffix(StringPool.DOT_JAVA)
            .build();


        List<PlusGeneratorInjectionConf> confList = new ArrayList<>();
        confList.add(boConf);
        confList.add(poConf);
        confList.add(daoConf);
        confList.add(mapperConf);
        confList.add(managerConf);
        confList.add(managerImplConf);
        confList.add(converterConf);

        confList.add(rpcConf);
        confList.add(rpcImplConf);
        confList.add(dtoConf);
        confList.add(rpcConverterConf);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                HashMap<String, Object> injectMap = new HashMap<>();
                injectMap.put("mapperConf", mapperConf);
                injectMap.put("poConf", poConf);
                injectMap.put("boConf", boConf);
                injectMap.put("daoConf", daoConf);
                injectMap.put("managerConf", managerConf);
                injectMap.put("managerImplConf", managerImplConf);
                injectMap.put("converterConf", converterConf);

                injectMap.put("dtoConf", dtoConf);
                injectMap.put("rpcConf", rpcConf);
                injectMap.put("rpcImplConf", rpcImplConf);
                injectMap.put("rpcConverterConf", rpcConverterConf);

                this.setMap(injectMap);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();

        //自定义对象
        for (PlusGeneratorInjectionConf plusGeneratorInjectionConf : confList) {
            // 自定义输入文件名称
            StringBuilder fileSb = new StringBuilder(generatorConfig.getProjectFolder())
                .append(MyStringUtils.joinPath(plusGeneratorInjectionConf.getPath(), plusGeneratorInjectionConf.getPackagePath()))
                .append(File.separator)
                .append(entityName).append(plusGeneratorInjectionConf.getFileNameSuffix())
                .append(plusGeneratorInjectionConf.getFileSuffix());
            //文件已经存在时是否覆盖
            File file = new File(fileSb.toString());
            if (!generatorConfig.isOverrideFile() && file.exists()) {
                continue;
            }
            focList.add(new FileOutConfig(plusGeneratorInjectionConf.getTemplatePath()) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return fileSb.toString();
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        /**
         * 模板配置
         */
        //屏蔽原有的生成器
        TemplateConfig tc = new TemplateConfig();
        tc.setMapper(null);
        tc.setXml(null);
        tc.setEntity(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setController(null);
        mpg.setTemplate(tc);

        /**
         * 包配置
         */
        packageInfo.setModuleName(entityName.toLowerCase());

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }




}
