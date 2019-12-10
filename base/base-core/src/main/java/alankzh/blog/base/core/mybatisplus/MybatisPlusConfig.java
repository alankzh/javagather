package alankzh.blog.base.core.mybatisplus;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {


    // 与mybatis不同, 需要使用MybatisSqlSessionFactoryBean 而不是 SqlSessionFactory
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory;
    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
//        configuration.setCallSettersOnNulls(true);
//        configuration.setMapUnderscoreToCamelCase(true);
//        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.REUSE);
//    }

}
