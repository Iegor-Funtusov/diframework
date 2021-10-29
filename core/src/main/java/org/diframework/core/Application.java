package org.diframework.core;

import org.diframework.core.factory.BeanFactory;
import org.diframework.core.factory.BeanStorage;
import org.diframework.core.searcher.ApplicationSearcher;
import org.diframework.core.searcher.DiframeworkSearcher;

public class Application {

    public static void run(Class<?> mainClass) {
        ApplicationSearcher applicationSearcher = new ApplicationSearcher(mainClass.getPackageName());
        DiframeworkSearcher diframeworkSearcher = new DiframeworkSearcher(Application.class.getPackageName());
        BeanStorage beanStorage = new BeanStorage();
        BeanFactory beanFactory = new BeanFactory(applicationSearcher, beanStorage, diframeworkSearcher);
        ApplicationContext context = new ApplicationContext(mainClass);
        context.setApplicationSearcher(applicationSearcher);
        context.setBeanStorage(beanStorage);
        context.setBeanFactory(beanFactory);
        context.initServiceInterfaces();
        context.initBeanMap();
        context.configureBeanMap();
    }
}
