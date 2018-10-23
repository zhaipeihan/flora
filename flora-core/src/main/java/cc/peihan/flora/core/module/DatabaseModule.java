package cc.peihan.flora.core.module;

import cc.peihan.flora.core.config.ApplicationProperties;
import cc.peihan.flora.core.infra.mysql.HikariDataSourceProvider;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;


public class DatabaseModule extends MyBatisModule {

    @Override
    protected void initialize() {
        install(JdbcHelper.MySQL);
        bindDataSourceProviderType(HikariDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        // TODO: 2018/10/21 add mapper
        Names.bindProperties(binder(), new ApplicationProperties());
    }
}
