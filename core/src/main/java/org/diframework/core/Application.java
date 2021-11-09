package org.diframework.core;

import org.diframework.core.factory.BeanFactory;
import org.diframework.core.factory.EntityFactory;
import org.diframework.core.searcher.ApplicationSearcher;
import org.diframework.core.searcher.DiframeworkSearcher;
import org.diframework.core.crud.starter.console.ApplicationConsoleStarter;
import org.diframework.core.storage.PropertiesStorage;

public class Application {

    public static void run(Class<?> mainClass) {
        PropertiesStorage.getInstance().initPropertiesStorage(mainClass.getClassLoader());
        ApplicationSearcher applicationSearcher = new ApplicationSearcher(mainClass);
        DiframeworkSearcher diframeworkSearcher = new DiframeworkSearcher(Application.class.getPackageName());
        EntityFactory entityFactory = new EntityFactory();
        BeanFactory beanFactory = new BeanFactory(applicationSearcher, diframeworkSearcher);
        ApplicationContext context = new ApplicationContext(mainClass);
        context.setApplicationSearcher(applicationSearcher);
        context.setBeanFactory(beanFactory);
        context.initServiceInterfaces();
        context.initBeanMap();
        context.configureBeanMap();
        entityFactory.initEntityContext();
        ApplicationConsoleStarter applicationConsoleStarter = new ApplicationConsoleStarter();
//        applicationConsoleStarter.start();
    }

    private static void initStorage(Class<?> mainClass) {
        PropertiesStorage.getInstance().initPropertiesStorage(mainClass.getClassLoader());
    }
}
