package xyz.kristo.projectx.account.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import xyz.kristo.projectx.account.util.JdbcUrlConverter;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"xyz.kristo.projectx.account.dao"})
public class DatabaseConfig {
    @Bean
    @ConfigurationProperties(prefix = "liquibase")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public org.apache.ibatis.session.Configuration mybatisProperties() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        hikariConfig.setJdbcUrl(JdbcUrlConverter.convert(hikariConfig.getJdbcUrl()));
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        springLiquibase.setContexts(liquibaseProperties.getContexts());
        springLiquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        springLiquibase.setDropFirst(liquibaseProperties.isDropFirst());
        springLiquibase.setShouldRun(liquibaseProperties.isEnabled());
        springLiquibase.setLabels(liquibaseProperties.getLabels());
        springLiquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        springLiquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        springLiquibase.setContexts(liquibaseProperties.getContexts());
        return springLiquibase;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, org.apache.ibatis.session.Configuration configuration) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }
}
