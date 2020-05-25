/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.plugins;

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
        List<PlusGeneratorInjectionConf> confList = generatorConfig.getGeneratorPackConfigList();
//        for (GeneratorPackConfig config : configList) {
//            //跳过rpc层的代码生成
//            if (generatorConfig.getRpcGenerator() && ConfNameEnum.isRpc(config.getConfNameEnum().getName())) {
//                continue;
//            }
//            confList.add(PlusGeneratorInjectionConf.builder().templatePath(config.getConfNameEnum().getTemplatePath())
//                .fileNameSuffix(config.getConfNameEnum().getFileNameSuffix())
//                .confName(config.getConfNameEnum().getName()).fileSuffix(config.getConfNameEnum().getFileSuffix())
//                .path(config.getTargetProject()).packagePath(config.getPackageName())
//                .build());
//        }

        /**
         * 自定义生成器变量
         */
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                HashMap<String, Object> injectMap = new HashMap<>();
                for (PlusGeneratorInjectionConf conf : confList) {
                    injectMap.put(conf.getConfNameEnum().getName(), conf);
                }
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
                .append(plusGeneratorInjectionConf.getConfNameEnum().getFileSuffix());
            //文件已经存在时是否覆盖
            File file = new File(fileSb.toString());
            if (!generatorConfig.isOverrideFile() && file.exists()) {
                continue;
            }
            focList.add(new FileOutConfig(plusGeneratorInjectionConf.getConfNameEnum().getTemplatePath()) {
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
