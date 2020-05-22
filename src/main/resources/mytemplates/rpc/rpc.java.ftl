package ${cfg.rpcConf.packagePath};

<#if swagger2>
import lomdtok.extern.slf4j.Slf4j;
</#if>
import com.guahao.convention.data.domain.Page;
import com.guahao.convention.data.domain.Result;
import ${cfg.dtoConf.packagePath}.${entity}${cfg.dtoConf.fileNameSuffix};


/**
 * <p>
 * ${table.comment!} rpc
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${entity}${cfg.rpcConf.fileNameSuffix} {

    Result<Page<${entity}${cfg.dtoConf.fileNameSuffix}>> page(${entity}${cfg.dtoConf.fileNameSuffix} dto);




}
