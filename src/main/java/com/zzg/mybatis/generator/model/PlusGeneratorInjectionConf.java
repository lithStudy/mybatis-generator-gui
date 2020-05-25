/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.model;

import com.zzg.mybatis.generator.enums.ConfNameEnum;

import lombok.Builder;
import lombok.Data;

/**
 * TODO PlusGeneratorInjectionConf
 *
 * @author lith
 * @version V1.0
 * @since 2020-05-15 15:18
 */
@Builder
@Data
public class PlusGeneratorInjectionConf {

    public PlusGeneratorInjectionConf() {
    }

    public PlusGeneratorInjectionConf(ConfNameEnum confNameEnum, String path, String packagePath) {
        this.confNameEnum = confNameEnum;
        this.path = path;
        this.packagePath = packagePath;
    }

    private ConfNameEnum confNameEnum;

//    private String templatePath;

    private String path;

    private String packagePath;

//    /**
//     * 文件名后缀
//     */
//    private String fileNameSuffix;

//    /**
//     * 文件后缀
//     */
//    private String fileSuffix;

    public String getFileNameSuffix() {
        return this.confNameEnum.getFileNameSuffix();
    }
}
