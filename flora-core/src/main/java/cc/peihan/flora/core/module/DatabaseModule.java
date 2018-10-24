package cc.peihan.flora.core.module;

import cc.peihan.flora.core.config.ApplicationProperties;
import cc.peihan.flora.core.infra.mysql.HikariDataSourceProvider;
import com.google.inject.name.Names;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Set;


public class DatabaseModule extends MyBatisModule {

    /**
     * mapper类集合
     */
    private Set<Class<?>> mapperClasses;

    public DatabaseModule(Set<Class<?>> mapperClasses) {
        this.mapperClasses = mapperClasses;
    }

    @Override
    protected void initialize() {
        install(JdbcHelper.MySQL);
        bindDataSourceProviderType(HikariDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        if (!CollectionUtils.isEmpty(mapperClasses)) {
            mapperClasses.forEach(this::addMapperClass);
        }
        Names.bindProperties(binder(), new ApplicationProperties());
    }
}
