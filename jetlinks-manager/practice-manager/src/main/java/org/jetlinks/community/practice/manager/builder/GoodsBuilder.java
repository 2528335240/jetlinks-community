package org.jetlinks.community.practice.manager.builder;

import org.hswebframework.ezorm.core.param.Term;
import org.hswebframework.ezorm.rdb.metadata.RDBColumnMetadata;
import org.hswebframework.ezorm.rdb.operator.builder.fragments.PrepareSqlFragments;
import org.hswebframework.ezorm.rdb.operator.builder.fragments.SqlFragments;
import org.hswebframework.ezorm.rdb.operator.builder.fragments.term.AbstractTermFragmentBuilder;
import org.jetlinks.community.utils.ConverterUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Component
public class GoodsBuilder extends AbstractTermFragmentBuilder {
    public static final String termType = "test";

    public GoodsBuilder() {
        super("test", "根据自定义条件查询商品");
    }

    @Override
    public SqlFragments createFragments(String columnFullName, RDBColumnMetadata column, Term term) {

        PrepareSqlFragments sqlFragments = PrepareSqlFragments.of();
        List<Term> terms = ConverterUtils.convertTerms(term.getValue());
        if(term.getOptions().contains("not")){
            sqlFragments.addSql("not");
        }
        sqlFragments
//            .addSql("exists(select 1 from"+getTableName("goods_manage",column)+  " where goods_name = ")
//            .addSql(columnFullName)
//            .addSql(" and ")
//            .addSql("goods_name = ?")
//            .addParameter(terms.get(0).getValue())
//            .addSql(" and id not in ")
//            .addSql("( select goods_id from order_manage  ))");
            .addSql(terms.stream().map(item -> item.getColumn()+" "+ getSqlType(item.getTermType()) +" ? and ")
                        .collect(Collectors.joining()))
            .addParameter(terms.stream().map(Term::getValue).collect(Collectors.toList()))
        .addSql(" id not in ")
            .addSql("( select goods_id from order_manage  )");



//            .addSql("goods_name = ?")
//            .addParameter(terms.get(0).getValue())
//            .addSql(" and ")
//            .addSql(" goods_batch = ")
//            .addSql(" and id not in ")
//            .addSql("( select goods_id from order_manage  )");


        return sqlFragments;
    }

    private String getSqlType(String termType) {
        if(termType.equals("eq")){
            return "=";
        }else if(termType.equals("gt")){
            return ">";
        }
        return null;
    }
}
