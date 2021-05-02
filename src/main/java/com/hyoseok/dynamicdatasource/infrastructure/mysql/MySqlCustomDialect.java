package com.hyoseok.dynamicdatasource.infrastructure.mysql;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySqlCustomDialect extends MySQL57Dialect {

    public MySqlCustomDialect() {
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
