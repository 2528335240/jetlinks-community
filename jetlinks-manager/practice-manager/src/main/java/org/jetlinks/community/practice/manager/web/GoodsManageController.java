package org.jetlinks.community.practice.manager.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.io.excel.ExcelUtils;
import org.jetlinks.community.io.excel.ImportHelper;
import org.jetlinks.community.io.file.FileInfo;
import org.jetlinks.community.io.file.FileManager;
import org.jetlinks.community.io.file.FileOption;
import org.jetlinks.community.io.utils.FileUtils;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.service.GoodsManageService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@RestController
@RequestMapping("goods/crud")
@AllArgsConstructor
@Getter
@Resource(id="goodsManage",name = "商品控制器")
@Tag(name ="商品增删改查")
public class GoodsManageController implements ReactiveServiceCrudController<GoodsManageEntity,String> {
    private final GoodsManageService service;
    private final FileManager fileManager;

    @GetMapping("/getNoOrderGoodsList")
    @Operation(summary = "根据商品名称为XX且不存在订单的商品列表")
    public Mono<List<GoodsManageEntity>> getList(QueryParamEntity queryParam){

        return service.getNoOrderGoodsList(queryParam);

    }
    //导出数据
    @GetMapping("/_export/{name}.{format}")
    public Mono<Void> export(QueryParamEntity param,
                             @PathVariable String name,
                             //文件格式: 支持csv,xlsx
                             @PathVariable String format,
                             ServerWebExchange exchange) {

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //文件名
        exchange.getResponse().getHeaders().setContentDisposition(
            ContentDisposition
                .attachment()
                .filename(name + "." + format, StandardCharsets.UTF_8)
                .build()
        );
        return exchange
            .getResponse()
            .writeWith(
                ExcelUtils.write(GoodsManageEntity.class, service.query(param.noPaging()), format)
            );
    }

    //根据上传的文件来导入数据并将导入结果保存到文件中返回结果文件地址，
    //客户端可以引导用户下载结果文件
    @PostMapping("/_import.{format}")
    public Mono<String> importByFileUpload(@PathVariable String format,
                                           @RequestPart("file") Mono<FilePart> file) {


        return FileUtils
            .dataBufferToInputStream(file.flatMapMany(FilePart::content))
            .flatMap(inputstream -> new ImportHelper<>(
                GoodsManageEntity::new,
                //数据处理逻辑
                flux -> service.save(flux).then())
                //批量处理数量
                .bufferSize(200)
                //当批量处理失败时,是否回退到单条数据处理
                .fallbackSingle(true)
                .doImport(inputstream,
                          format,
                          //忽略导入结果
                          info -> null,
                          //将导入的结果保存为临时文件
                          result -> fileManager
                              .saveFile("import." + format, result, FileOption.tempFile)
                              .map(FileInfo::getAccessUrl))
                .last()
            );
    }
}
