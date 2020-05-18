/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO
 *
 * @author lith
 * @version V1.0
 * @since 2020-05-15 15:18
 */
@Builder
@Data
public class PlusGeneratorInjectionConf {

    private String templatePath;

    private String path;

    private String packagePath;

    /**
     * 文件名后缀
     */
    private String fileNameSuffix;

    /**
     * 文件后缀
     */
    private String fileSuffix;


}
