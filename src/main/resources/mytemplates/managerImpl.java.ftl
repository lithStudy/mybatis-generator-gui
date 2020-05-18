package ${cfg.managerImplConf.packagePath};

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import ${cfg.daoConf.packagePath}.${entity}${cfg.daoConf.fileNameSuffix};
import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};
import ${cfg.managerConf.packagePath}.${entity}${cfg.managerConf.fileNameSuffix};

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${entity}${cfg.managerImplConf.fileNameSuffix} implements ${entity}${cfg.managerConf.fileNameSuffix} {

    @Resource
    private ${entity}${cfg.daoConf.fileNameSuffix} dao;

    @Override
    public List<${entity}${cfg.boConf.fileNameSuffix}> list(${entity}${cfg.boConf.fileNameSuffix} bo) {
    return SysConfOrgConverter.detransform(dao.list(SysConfOrgConverter.transform(bo)));
    }

    @Override
    public List<${entity}${cfg.boConf.fileNameSuffix}> pageList(${entity}${cfg.boConf.fileNameSuffix} bo, PageQuery pageQuery) {
        return SysConfOrgConverter.detransform(dao.pageList(SysConfOrgConverter.transform(bo), pageQuery));
    }

    @Override
    public long count(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.count(SysConfOrgConverter.transform(bo));
    }

    @Override
    public ${entity}${cfg.boConf.fileNameSuffix} get(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return SysConfOrgConverter.transform(dao.get(SysConfOrgConverter.transform(bo)));
    }

    @Override
    public int insert(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.insert(SysConfOrgConverter.transform(bo));
    }

    @Override
    public int updateByPrimaryKey(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.updateByPrimaryKey(SysConfOrgConverter.transform(bo));
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

}
