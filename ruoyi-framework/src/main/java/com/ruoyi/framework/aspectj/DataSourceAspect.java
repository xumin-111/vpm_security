package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 多数据源处理
 * 
 * @author ruoyi
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.ruoyi.common.annotation.DataSource)"
            + "|| @within(com.ruoyi.common.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.isNotNull(dataSource))
        {
            //DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
            //切换SLAVE数据源
            if(dataSource.value().name().equals(DataSourceType.SLAVE.name())){
                javax.sql.DataSource targetDataSource = (javax.sql.DataSource) SpringUtils.getBean("slaveDataSource");
                //---------------------修改mybatis的数据源-----------------------
                //修改MyBatis的数据源
                SqlSessionFactory sqlSessionFactoryBean = (SqlSessionFactory)SpringUtils.getBean(SqlSessionFactory.class);
                Environment environment = sqlSessionFactoryBean.getConfiguration().getEnvironment();
                Field dataSourceField = environment.getClass().getDeclaredField("dataSource");
                dataSourceField.setAccessible(true);//跳过检查
                dataSourceField.set(environment,targetDataSource);//修改mybatis的数据源
            }
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
            javax.sql.DataSource targetDataSource = (javax.sql.DataSource) SpringUtils.getBean("masterDataSource");
            //---------------------修改mybatis的数据源-----------------------
            //修改MyBatis的数据源
            SqlSessionFactory sqlSessionFactoryBean = (SqlSessionFactory)SpringUtils.getBean(SqlSessionFactory.class);
            Environment environment = sqlSessionFactoryBean.getConfiguration().getEnvironment();
            Field dataSourceField = environment.getClass().getDeclaredField("dataSource");
            dataSourceField.setAccessible(true);//跳过检查
            dataSourceField.set(environment,targetDataSource);//修改mybatis的数据源
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        if (StringUtils.isNotNull(targetDataSource))
        {
            return targetDataSource;
        }
        else
        {
            Method method = signature.getMethod();
            DataSource dataSource = method.getAnnotation(DataSource.class);
            return dataSource;
        }
    }
}
