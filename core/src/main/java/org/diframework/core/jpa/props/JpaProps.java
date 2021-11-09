package org.diframework.core.jpa.props;

public enum JpaProps {

    DB_PLATFORM("db.platform"),
    DB_URL("db.url"),
    DB_USER("db.user"),
    DB_PASSWORD("db.password"),
    DB_DRIVER("db.driver"),

    PLATFORM_MYSQL("mysql"),
    PLATFORM_POSTGRESQL("postgresql");


    JpaProps(String s) {
        props = s;
    }

    private String props;

    public String getProps() {
        return props;
    }
}
