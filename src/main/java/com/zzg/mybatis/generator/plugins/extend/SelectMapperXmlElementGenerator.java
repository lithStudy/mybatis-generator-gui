/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.zzg.mybatis.generator.plugins.extend;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * TODO
 *
 * @author lith
 * @version V1.0
 * @since 2020-05-07 20:41
 */
public class SelectMapperXmlElementGenerator extends AbstractXmlElementGenerator {
    private static final List<String> EXCLUDE_FIELDS =new ArrayList<String>(){{
        add("is_deleted");
        add("creator");
        add("modifier");
        add("gmt_created");
        add("gmt_modified");
    }};

    @Override
    public void addElements(XmlElement parentElement) {
        // 增加base_query
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "base_query"));
        //在这里添加where条件
        XmlElement selectTrimElement = new XmlElement("trim"); //设置trim标签
        selectTrimElement.addAttribute(new Attribute("prefix", "WHERE"));
        selectTrimElement.addAttribute(new Attribute("prefixOverrides", "AND | OR")); //添加where和and
        StringBuilder sb = new StringBuilder();

        //是否删除
        sb.append(" is_deleted=0 ");
        selectTrimElement.addElement(new TextElement(sb.toString()));

        for(IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            //排除查询条件不关心的字段
            if (EXCLUDE_FIELDS.contains(introspectedColumn.getActualColumnName())) {
                continue;
            }
            XmlElement selectNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append("null != ");
            sb.append(introspectedColumn.getJavaProperty());
            selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            // 添加and
            sb.append(" and ");
            // 添加别名t
//            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn,"po."));
            selectNotNullElement.addElement(new TextElement(sb.toString()));
            selectTrimElement.addElement(selectNotNullElement);
        }
        sql.addElement(selectTrimElement);
        parentElement.addElement(sql);

        // 公用select
        sb.setLength(0);
        sb.append("select ");
        sb.append("* ");
        sb.append("from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
//        sb.append(" t");
        TextElement selectText = new TextElement(sb.toString());

        // 公用include
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "base_query"));

        // 增加list
        XmlElement list = new XmlElement("select");
        list.addAttribute(new Attribute("id", "list"));
        list.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        list.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        list.addElement(selectText);
        list.addElement(include);
        //限制最大查询数量
        sb.setLength(0);
        sb.append("LIMIT 200");
        TextElement listLimitText = new TextElement(sb.toString());
        list.addElement(listLimitText);

        parentElement.addElement(list);

        // 增加pageList
        XmlElement pageList = new XmlElement("select");
        pageList.addAttribute(new Attribute("id", "pageList"));
        pageList.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        pageList.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        pageList.addElement(selectText);
        pageList.addElement(include);

        sb.setLength(0);
        sb.append("LIMIT #{pageQuery.offset}, #{pageQuery.pageSize}");
        TextElement limitText = new TextElement(sb.toString());
        pageList.addElement(limitText);

        parentElement.addElement(pageList);

        //增加count
        XmlElement countElement = new XmlElement("select");
        countElement.addAttribute(new Attribute("id", "count"));
        countElement.addAttribute(new Attribute("resultType", "java.lang.Long"));
        countElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        sb.setLength(0);
        sb.append("select count(*) from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        TextElement countText = new TextElement(sb.toString());
        countElement.addElement(countText);
        countElement.addElement(include);

        parentElement.addElement(countElement);

        //增加get
        XmlElement getElement = new XmlElement("select");
        getElement.addAttribute(new Attribute("id", "get"));
        getElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        getElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        getElement.addElement(selectText);
        getElement.addElement(include);

        sb.setLength(0);
        sb.append("LIMIT 1");
        TextElement getLimitText = new TextElement(sb.toString());
        getElement.addElement(getLimitText);

        parentElement.addElement(getElement);
    }
}
