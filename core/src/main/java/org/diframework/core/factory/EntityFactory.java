package org.diframework.core.factory;

import org.apache.commons.lang3.StringUtils;
import org.diframework.core.storage.EntityStorage;
import org.diframework.core.storage.PropertiesStorage;

import static org.diframework.core.jpa.props.JpaProps.DB_PLATFORM;
import static org.diframework.core.jpa.props.JpaProps.PLATFORM_MYSQL;

public class EntityFactory {

    public void initEntityContext() {
        System.out.println("entities = " + EntityStorage.getInstance().getEntities());
        String platform = PropertiesStorage.getInstance().getProperties().get(DB_PLATFORM.getProps());
        if (StringUtils.isNotBlank(platform)) {
            if (platform.equals(PLATFORM_MYSQL.getProps())) {
                System.out.println("platform = " + platform);
            }
        }
    }
}
