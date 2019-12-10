package alankzh.blog.base.core.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
//        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dataSource);
//        return sqlSessionFactory;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
//        configuration.setCallSettersOnNulls(true);
//        configuration.setMapUnderscoreToCamelCase(true);
//        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.REUSE);
//    }

}
