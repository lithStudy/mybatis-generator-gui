package com.zzg.mybatis.generator.model;

import com.zzg.mybatis.generator.enums.ConfNameEnum;

import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import lombok.Data;

/**
 *
 * GeneratorConfig is the Config of mybatis generator config exclude database
 * config
 *
 * Created by Owen on 6/16/16.
 */
@Data
public class GeneratorConfig {

	/**
	 * 本配置的名称
	 */
	private String name;

	private String connectorJarPath;

	private String projectFolder;

	private List<PlusGeneratorInjectionConf> generatorPackConfigList;

//	private String poPackage;
//	private String poTargetProject;
//
//	private String boPackage;
//	private String boTargetProject;
//
//	private String daoPackage;
//	private String daoTargetProject;
//
//	private String mapperPackage;
//	private String mapperTargetProject;
//
//	private String managerPackage;
//	private String managerTargetProject;
//
//	private String managerImplPackage;
//	private String managerImplTargetProject;
//
//	private String transPackage;
//	private String transTargetProject;

	private Boolean rpcGenerator;

//	private String dtoPackage;
//	private String dtoTargetProject;
//
//	private String rpcPackage;
//	private String rpcTargetProject;
//
//	private String rpcImplPackage;
//	private String rpcImplTargetProject;
//
//	private String rpcConverterPackage;
//	private String rpcConverterTargetProject;



	private String tableName;

    private String entityName;

	private boolean overrideFile;

	private boolean useLombokPlugin;

	private String generateKeys;

	private String encoding;

	private boolean jsr310Support;

	public PlusGeneratorInjectionConf getPackage(ConfNameEnum confNameEnum) {
		if (CollectionUtils.isEmpty(this.generatorPackConfigList)) {
			return PlusGeneratorInjectionConf.builder().build();
		}
		for (PlusGeneratorInjectionConf config : this.generatorPackConfigList) {
			if (Objects.equals(config.getConfNameEnum(),confNameEnum)) {
				return config;
			}
		}
		return PlusGeneratorInjectionConf.builder().build();
	}

}
