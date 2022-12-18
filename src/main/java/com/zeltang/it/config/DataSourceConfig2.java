package com.zeltang.it.config;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源2配置
 *
 *@ClassName DataSourceConfig2
 *@Author tangzelong
 *@Date 2019/11/18 14:46
 *@Version 1.0
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
@MapperScan(basePackages = "com.zeltang.it.mapper.mapper2", sqlSessionFactoryRef = "mapper2SqlSessionFactory")
public class DataSourceConfig2 {
    // 将这个对象放入Spring容器中
    @Bean(name = "mapper2DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "mapper2SqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为mapper2DataSource的对象
    public SqlSessionFactory mapper2SqlSessionFactory(@Qualifier("mapper2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        VFS.addImplClass(SpringBootVFS.class);//mybatis的facroty需要加载SpringBoot独特的虚拟文件系统，才能识别类路
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        bean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath:com/zeltang/it/mapper/mapper2/*.xml"));
        return bean.getObject();
    }
    @Bean("mapper2SqlSessionTemplate")
    public SqlSessionTemplate mapper2sqlsessiontemplate(
            @Qualifier("mapper2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
