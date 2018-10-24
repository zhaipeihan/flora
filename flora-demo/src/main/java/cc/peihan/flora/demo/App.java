package cc.peihan.flora.demo;


import cc.peihan.flora.core.annotation.DatabaseSettingX;
import cc.peihan.flora.core.annotation.FloraBootApplication;
import cc.peihan.flora.core.annotation.HttpEngineSettingX;
import cc.peihan.flora.core.annotation.ServiceProtocol;
import cc.peihan.flora.core.bootstrap.FloraApplication;

@HttpEngineSettingX(
        serviceProtocal = ServiceProtocol.FCP,
        port = 8888
)
@DatabaseSettingX(
        daoPackageName = "cc.peihan.flora.demo.dao"
)
@FloraBootApplication(basePackage = "cc.peihan.flora.demo")
public class App {
    public static void main(String[] args) {
        FloraApplication.run(App.class);
    }
}
