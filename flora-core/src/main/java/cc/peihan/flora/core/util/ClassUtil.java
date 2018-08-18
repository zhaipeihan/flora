package cc.peihan.flora.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取当前线程中的类加载器
     *
     * @return ClassLoader对象
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className    类的全限定名
     * @param isInitialied 是否初始化的标志(是否执行类的静态代码块)TODO
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialied) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialied, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类初始化错误");
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 加载类
     *
     * @param className 类的全限定名
     * @return
     */
    public static Class<?> loadClass(String className) {
        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类初始化错误");
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     *
     * @param packageName 包名
     * @return Set<Class>
     * 1.根据包名将其转换为文件路径
     * 2读取class或者jar包，获取指定的类名去加载类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {

            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));


            if (urls.hasMoreElements()) {

                URL url = urls.nextElement();

                if (url != null) {

                    String protocol = url.getProtocol();        //协议名称
                    if (protocol.equals("file")) {

                        String packagePath = url.getPath().replace("%20", " ");
                        //加载类
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {

                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();

                        if (jarURLConnection != null) {

                            JarFile jarFile = jarURLConnection.getJarFile();

                            if (jarFile != null) {

                                Enumeration<JarEntry> jarEntries = jarFile.entries();

                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {

                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {

        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file : files) {

            String fileName = file.getName();

            if (file.isFile()) {

                String className = fileName.substring(0, fileName.lastIndexOf("."));

                if (packageName != null && !packageName.equals("")) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);

            } else {

                String subPackagePath = fileName;
                if (subPackagePath != null && !subPackagePath.equals("")) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (subPackageName != null && !subPackageName.equals("")) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);

            }
        }

    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

}