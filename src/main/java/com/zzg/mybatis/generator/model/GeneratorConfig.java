package com.zzg.mybatis.generator.model;

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
	/**
	 * 本配置的名称
	 */
	private String name;

	private String connectorJarPath;

	private String projectFolder;

	private String poPackage;
	private String poTargetProject;

	private String boPackage;
	private String boTargetProject;

	private String daoPackage;
	private String daoTargetProject;

	private String mapperPackage;
	private String mapperTargetProject;

	private String managerPackage;
	private String managerTargetProject;

	private String managerImplPackage;
	private String managerImplTargetProject;

	private String transPackage;
	private String transTargetProject;

	private String tableName;

	private boolean overrideXML;

	private boolean useLombokPlugin;

	private boolean useActualColumnNames;

	private boolean useExample;

	private String generateKeys;

	private String encoding;

	private boolean useTableNameAlias;

	private boolean useDAOExtendStyle;

	private boolean useSchemaPrefix;

	private boolean jsr310Support;

}
