package com.zzg.mybatis.generator.plugins;

/**
 * Created by zouzhigang on 2016/6/14.
 */

import com.zzg.mybatis.generator.plugins.extend.SelectMapperXmlElementGenerator;
import com.zzg.mybatis.generator.plugins.extend.SelectJavaMapperMethodGenerator;
import com.zzg.mybatis.generator.plugins.extend.InsertMapperXmlElementGenerator;
import com.zzg.mybatis.generator.plugins.extend.UpdateMapperXmlElementGenerator;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.InsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeySelectiveMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeyWithBLOBsMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class WeDoctorExtendsPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        AbstractXmlElementGenerator elementGenerator = new SelectMapperXmlElementGenerator();
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.addElements(document.getRootElement());

        AbstractXmlElementGenerator insertMapperXmlElementGenerator = new InsertMapperXmlElementGenerator(true);
        insertMapperXmlElementGenerator.setContext(context);
        insertMapperXmlElementGenerator.setIntrospectedTable(introspectedTable);
        insertMapperXmlElementGenerator.addElements(document.getRootElement());


        AbstractXmlElementGenerator updateMapperXmlElementGenerator = new UpdateMapperXmlElementGenerator();
        updateMapperXmlElementGenerator.setContext(context);
        updateMapperXmlElementGenerator.setIntrospectedTable(introspectedTable);
        updateMapperXmlElementGenerator.addElements(document.getRootElement());

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        AbstractJavaMapperMethodGenerator methodGenerator = new SelectJavaMapperMethodGenerator();
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.addInterfaceElements(interfaze);

        AbstractJavaMapperMethodGenerator insertMethodGenerator = new InsertMethodGenerator(true);
        insertMethodGenerator.setContext(context);
        insertMethodGenerator.setIntrospectedTable(introspectedTable);
        insertMethodGenerator.addInterfaceElements(interfaze);

        AbstractJavaMapperMethodGenerator updateMethodGenerator = new UpdateByPrimaryKeySelectiveMethodGenerator();
        updateMethodGenerator.setContext(context);
        updateMethodGenerator.setIntrospectedTable(introspectedTable);
        updateMethodGenerator.addInterfaceElements(interfaze);

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }
}
