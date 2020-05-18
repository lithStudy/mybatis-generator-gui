<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${cfg.daoConf.packagePath}.${entity}${cfg.daoConf.fileNameSuffix}">

    <#--用于忽略字段时进行比较-->
    <#assign tableFiledCheck=(", "+table.fieldNames +",")/>
    <#--需要忽略返回的字段-->
    <#assign ignoreRetArr=['is_deleted','id']/>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}">
        <!--固定的自增主键-->
        <id column="id" jdbcType="BIGINT" property="id" />
        <#list table.fields as field>
        <#if !field.keyFlag && !ignoreRetArr?seq_contains(field.name)><#--生成普通字段 -->
        <result column="${field.name}" jdbcType="${field.name}" property="${field.propertyName}" />
        </#if>
        </#list>
    </resultMap>

    <!--表基础字段sql-->
    <sql id="Base_Column_List">
        id,
        <#list table.fields as field>
        <#if !field.keyFlag && !ignoreRetArr?seq_contains(field.name)><#--生成普通字段 -->
        ${field.name}<#if field_has_next>,</#if>
        </#if>
        </#list>
    </sql>

    <#--需要忽略查询条件的字段-->
    <#assign ignoreQueryArr=['is_deleted','creator','modifier','gmt_created','gmt_modified']/>
    <#--公用查询条件-->
    <sql id="base_query">
        <trim prefix="WHERE" prefixOverrides="AND | OR">
            <#if tableFiledCheck?contains(", is_deleted,")>
            is_deleted=0
            </#if>
            <#--生成普通字段 -->
            <#list table.fields as field>
                <#if !field.keyFlag && !ignoreQueryArr?seq_contains(field.name)>
            <if test="null != po.${field.propertyName} <#if field.columnType.type=="String">and '' != po.${field.propertyName}</#if>">
                and ${field.name} = ${r"#{po."+field.propertyName+"}"}
            </if>
                </#if>
            </#list>
        </trim>
         <#if tableFiledCheck?contains(", gmt_modified,")>
             order by gmt_modified desc
         </#if>
    </sql>

    <!--单条记录查询-->
    <select id="get" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}" resultMap="BaseResultMap">
        select <include refid="Base_Column_List"/> from
        ${table.name}
        <include refid="base_query" />
        LIMIT 1
    </select>

    <!--列表查询-->
    <select id="list" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}" resultMap="BaseResultMap">
        select <include refid="Base_Column_List"/> from
        ${table.name}
        <include refid="base_query" />
        LIMIT 200
    </select>

    <!--分页查询-->
    <select id="pageList" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}" resultMap="BaseResultMap">
        select <include refid="Base_Column_List"/> from
        ${table.name}
        <include refid="base_query" />
        LIMIT ${r"#{pageQuery.offset}"}, ${r"#{pageQuery.pageSize}"}
    </select>

    <!--查询总数-->
    <select id="count" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}" resultType="java.lang.Long">
        select count(*) from
        ${table.name}
        <include refid="base_query" />
    </select>

    <#--忽略插入的字段-->
    <#assign ignoreInsertArr=['is_deleted','id','gmt_created','gmt_modified']/>

    <!--新增数据-->
    <insert id="insert" keyColumn="id" keyProperty="id" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}" useGeneratedKeys="true">
        insert into ${table.name}(
<#list table.fields as field>
    <#if !field.keyFlag && !ignoreInsertArr?seq_contains(field.name)><#--生成普通字段 -->
        ${field.name},
    </#if>
</#list>
        is_deleted,
        gmt_created,
        gmt_modified
        )
        values (
<#list table.fields as field>
    <#if !field.keyFlag && !ignoreInsertArr?seq_contains(field.name)><#--生成普通字段 -->
        ${field.propertyName},
    </#if>
</#list>
        0,
        now(),
        now()
        )
    </insert>

    <!--忽略更新的字段-->
    <#assign ignoreUpdateArr=['is_deleted','id','gmt_created','gmt_modified','modifier','creator']/>
    <#--更新数据-->
    <update id="updateByPrimaryKey" parameterType="${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix}">
        update ${table.name}
        <set>
            gmt_modified = now(),
            <#if tableFiledCheck?contains(", modifier,")>
            modifier = ${r"#{modifier}"},
            </#if>
    <#list table.fields as field>
        <#if !field.keyFlag && !ignoreUpdateArr?seq_contains(field.name)>
            <if test="null != ${field.propertyName}">
                ${field.name} = ${r"#{"+field.propertyName+"}"}<#if field_has_next>,</#if>
            </if>
        </#if>
    </#list>
        </set>
        where id = ${r"#{id}"}
        <#if tableFiledCheck?contains(", is_deleted,")>
        and is_deleted=0
        </#if>
    </update>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete from ${table.name}
        where id = ${r'#{id}'}
        <#if tableFiledCheck?contains(", is_deleted,")>
        and is_deleted=0
        </#if>
    </delete>

</mapper>
