package ${cfg.managerConf.packagePath};

import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};

import com.guahao.convention.data.domain.PageQuery;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${entity}${cfg.managerConf.fileNameSuffix}{


    List<${entity}${cfg.boConf.fileNameSuffix}> list(${entity}${cfg.boConf.fileNameSuffix} bo);

    List<${entity}${cfg.boConf.fileNameSuffix}> pageList(${entity}${cfg.boConf.fileNameSuffix} bo,PageQuery pageQuery);

    long count(${entity}${cfg.boConf.fileNameSuffix} bo);

    ${entity}${cfg.boConf.fileNameSuffix} get(${entity}${cfg.boConf.fileNameSuffix} bo);

    int insert(${entity}${cfg.boConf.fileNameSuffix} bo);

    int updateByPrimaryKey(${entity}${cfg.boConf.fileNameSuffix} bo);

    int deleteByPrimaryKey(Long id);
}

