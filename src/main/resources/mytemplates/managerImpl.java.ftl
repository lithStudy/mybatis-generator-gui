package ${cfg.managerImplConf.packagePath};

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.guahao.convention.data.domain.PageQuery;

import ${cfg.daoConf.packagePath}.${entity}${cfg.daoConf.fileNameSuffix};
import ${cfg.boConf.packagePath}.${entity}${cfg.boConf.fileNameSuffix};
import ${cfg.poConf.packagePath}.${entity}${cfg.poConf.fileNameSuffix};
import ${cfg.managerConf.packagePath}.${entity}${cfg.managerConf.fileNameSuffix};
import ${cfg.converterConf.packagePath}.${entity}${cfg.converterConf.fileNameSuffix};

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
    return ${entity}${cfg.converterConf.fileNameSuffix}.transListPO2BO(dao.list(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo)));
    }

    @Override
    public List<${entity}${cfg.boConf.fileNameSuffix}> pageList(${entity}${cfg.boConf.fileNameSuffix} bo, PageQuery pageQuery) {
        return ${entity}${cfg.converterConf.fileNameSuffix}.transListPO2BO(dao.pageList(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo), pageQuery));
    }

    @Override
    public long count(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.count(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo));
    }

    @Override
    public ${entity}${cfg.boConf.fileNameSuffix} get(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return ${entity}${cfg.converterConf.fileNameSuffix}.transPO2BO(dao.get(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo)));
    }

    @Override
    public int insert(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.insert(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo));
    }

    @Override
    public int updateByPrimaryKey(${entity}${cfg.boConf.fileNameSuffix} bo) {
        return dao.updateByPrimaryKey(${entity}${cfg.converterConf.fileNameSuffix}.transBO2PO(bo));
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

}
