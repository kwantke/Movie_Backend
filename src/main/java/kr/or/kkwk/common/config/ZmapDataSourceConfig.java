package kr.or.kkwk.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"kr.or.kkwk.repository"}// repository 경로
        ,entityManagerFactoryRef =  "zmapJpaEntityManagerFactory"
        ,transactionManagerRef = "zmapTransactionManager")
@MapperScan(value="kr.or.zeropay.mapper.zmap", sqlSessionFactoryRef="zmapSqlSessionFactory")
@EnableTransactionManagement
public class ZmapDataSourceConfig {
    private boolean isJndiDataSource;
    private String jndiName;


    public ZmapDataSourceConfig(@Value("${db.datasource.using-jndi}") boolean isJndiDataSource
            ,@Value("${db.datasource.jndi-name}")String jndiName){
        this.isJndiDataSource = isJndiDataSource;
        this.jndiName = jndiName;
    }

    @Bean(name="zmapDataSource")
    @ConfigurationProperties(prefix="zmap.datasource.hikari")
    public DataSource zmapDataSource(){
        if(isJndiDataSource){
            JndiDataSourceLookup lookup = new JndiDataSourceLookup();
            return lookup.getDataSource(jndiName);
        } else {
            //application.yml에서 정의된 DB 연결 정보 빌드
            return DataSourceBuilder.create()
                    .build();
        }
    }

    @Bean(name="zmapSqlSessionFactory")
    public SqlSessionFactory zmapSqlSessionFactory(@Qualifier("zmapDataSource")DataSource zmapDataSource
            , ApplicationContext applicationContext) throws Exception {
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml 경로를 알려준다.
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(zmapDataSource);
        bean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/zmap/**.xml"));

        return bean.getObject();
    }

    @Bean(name="zmapSqlSessionTemplate")
    public SqlSessionTemplate zmapSqlSessionTemplate(SqlSessionFactory zmapSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(zmapSqlSessionFactory);
    }

    @Bean(name="zmapJpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean zmapJpaEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("zmapDataSource") DataSource zmapDataSource){

        return builder.dataSource(zmapDataSource).packages("kr.or.zeropay.model.entity.zmap").build();
    }

    @Bean(name = "zmapTransactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("zmapJpaEntityManagerFactory") LocalContainerEntityManagerFactoryBean mfBean
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( mfBean.getObject() );
        return transactionManager;
    }
}
