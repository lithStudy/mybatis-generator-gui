package com.zzg.mybatis.generator.model;

import lombok.Data;

/**
 * Created by Owen on 5/13/16.
 */
@Data
public class DatabaseConfig {

	/**
	 * The primary key in the sqlite db
	 */
	private Integer id;

	private String dbType;
	/**
	 * The name of the config
	 */
	private String name;

	private String host;

	private String port;

	private String schema;

	private String username;

	private String password;

	private String encoding;

    private String lport;

    private String rport;

    private String sshPort;

    private String sshHost;

    private String sshUser;

    private String sshPassword;


}
