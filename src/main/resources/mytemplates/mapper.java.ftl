package ${cfg.daoConf.packagePath};

import ${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix};

import com.guahao.convention.data.domain.PageQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${entity}${cfg.daoConf.fileNameSuffix} {

 ${entity}${cfg.poConf.fileNameSuffix} get(@Param("po") ${entity}${cfg.poConf.fileNameSuffix} po);

 List<${entity}${cfg.poConf.fileNameSuffix}> list(@Param("po") ${entity}${cfg.poConf.fileNameSuffix} po);

 List<${entity}${cfg.poConf.fileNameSuffix}> pageList(@Param("po") ${entity}${cfg.poConf.fileNameSuffix} po , @Param("pageQuery") PageQuery pageQuery);

 Long count(@Param("po") ${entity}${cfg.poConf.fileNameSuffix} po);

 int insert(${entity}${cfg.poConf.fileNameSuffix} po);

 int updateByPrimaryKey(${entity}${cfg.poConf.fileNameSuffix} po);

 int deleteByPrimaryKey(Long id);

}

