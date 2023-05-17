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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"kr.or.kkwk.repository"}
,entityManagerFactoryRef =  "unitedJpaEntityManagerFactory"
,transactionManagerRef = "unitedTransactionManager")
@MapperScan(value="kr.or.zeropay.mapper.united", sqlSessionFactoryRef="unitedSqlSessionFactory", sqlSessionTemplateRef = "unitedSqlSessionTemplate")
@EnableTransactionManagement
public class UnitedDataSourceConfig {
    private boolean isJndiDataSource;
    private String jndiName;


    public UnitedDataSourceConfig(@Value("${db.datasource.using-jndi}") boolean isJndiDataSource
            ,@Value("${db.datasource.jndi-name}")String jndiName){
        this.isJndiDataSource = isJndiDataSource;
        this.jndiName = jndiName;
    }


    @Primary
    @Bean(name="unitedDataSource")
    @ConfigurationProperties(prefix="united.datasource.hikari")
    public DataSource unitedDataSource(){
        if(isJndiDataSource){
            JndiDataSourceLookup lookup = new JndiDataSourceLookup();
            return lookup.getDataSource(jndiName);
        } else {
            //application.yml에서 정의된 DB 연결 정보 빌드
            return DataSourceBuilder.create()
                    .build();
        }
    }

    @Primary
    @Bean(name="unitedSqlSessionFactory")
    public SqlSessionFactory unitedSqlSessionFactory(@Qualifier("unitedDataSource")DataSource unitedDataSource
    , ApplicationContext applicationContext) throws Exception {
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml 경로를 알려준다.
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(unitedDataSource);
        bean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/united/**.xml"));

        return bean.getObject();
    }

    @Primary
    @Bean(name="unitedSqlSessionTemplate")
    public SqlSessionTemplate unitedSqlSessionTemplate(@Qualifier("unitedSqlSessionFactory")SqlSessionFactory unitedSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(unitedSqlSessionFactory);
    }

    @Primary
    @Bean(name="unitedJpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean unitedJpaEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("unitedDataSource") DataSource unitedDataSource){
        //entity 경로
        return builder.dataSource(unitedDataSource).packages("kr.or.zeropay.model.entity.united").build();
    }

    @Primary
    @Bean(name = "unitedTransactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("unitedJpaEntityManagerFactory") LocalContainerEntityManagerFactoryBean mfBean
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( mfBean.getObject() );
        return transactionManager;
    }

}

