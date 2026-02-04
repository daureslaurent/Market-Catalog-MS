package lda.services.market.infra.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Bean("writeLiquibase")
    public SpringLiquibase writeLiquibase(
            @Qualifier("writeDataSource") DataSource writeDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(writeDataSource);
        liquibase.setChangeLog("classpath:/db/changelog/write/db.write.changelog-master.yaml");
        liquibase.setShouldRun(true);
        return liquibase;
    }

    @Bean("readLiquibase")
    public SpringLiquibase readLiquibase(
            @Qualifier("readDataSource") DataSource readDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(readDataSource);
        liquibase.setChangeLog("classpath:/db/changelog/read/db.read.changelog-master.yaml");
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
