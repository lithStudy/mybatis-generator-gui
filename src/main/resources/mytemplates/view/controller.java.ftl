package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.alibaba.fastjson.JSON;
<#if swagger2>
import lombok.extern.slf4j.Slf4j;
</#if>


/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(value = "${table.comment!} ",tags = "${table.comment!} ")
</#if>
@Slf4j
@RestController
@RequestMapping("/${package.ModuleName}")
public class ${package.Entity}Controller {

    @Resource
    private ${entity}${cfg.processConf.fileNameSuffix} process;


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/page")
    public Result<Page<${entity}${cfg.boConf.fileNameSuffix}>> page(@RequestBody ${entity}${cfg.paramConf.fileNameSuffix} param) {
        log.info("${package.ModuleName}/page ====== param:{}",JSON.toJSONString(param));
        return Results.success(process.page(param));
    }



}
