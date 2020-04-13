package com.ivanapp.ivanapp.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/2/21 14:46
 * Druid数据源配置
 */
@Configuration
public class DruidConfiguration {
    //定义日志信息对象
    private static final Logger logger= LoggerFactory.getLogger(DruidConfiguration.class);

    @Bean //声明为bean实例
    @Primary //在同样的Source中，首先使用被标注的DataSource
    @ConfigurationProperties(prefix = "spring.datasource") //加载配置文件的前缀application.yml
    public DataSource dataSource(){
        logger.info("init Druid____正在初始化Druid......");
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    /**
     * 配置servlet管理后台
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("正在配置管理servlet后台......");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        // servletRegistrationBean.addInitParameter("deny", "192.168.0.000");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "ivanadmin");
        servletRegistrationBean.addInitParameter("loginPassword", "ivan123");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 配置web监控filter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
