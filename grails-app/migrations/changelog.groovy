databaseChangeLog = {

    changeSet(author: "vsmir (generated)", id: "1509702925282-1") {
        createTable(tableName: "person") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "personPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)")

            column(name: "enabled", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "user_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-2") {
        createTable(tableName: "person_person") {
            column(name: "person_followed_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "person_id", type: "BIGINT")
        }
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-3") {
        createTable(tableName: "person_role") {
            column(name: "person_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "role_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-4") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-5") {
        createTable(tableName: "status") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "statusPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "author_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "message", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-6") {
        addPrimaryKey(columnNames: "person_id, role_id", constraintName: "person_rolePK", tableName: "person_role")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-7") {
        addUniqueConstraint(columnNames: "user_name", constraintName: "UC_PERSONUSER_NAME_COL", tableName: "person")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-8") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_ROLEAUTHORITY_COL", tableName: "role")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-9") {
        addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_person", constraintName: "FKb0usqkpsjgv42fmsu4e0h2gyl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-10") {
        addForeignKeyConstraint(baseColumnNames: "person_followed_id", baseTableName: "person_person", constraintName: "FKhrhp1dnrkl9sppvuawccawads", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-11") {
        addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_role", constraintName: "FKhyx1efsls0f00lxs6xd4w2b3j", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-12") {
        addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "status", constraintName: "FKisrwmscc3ijnhm0lyy1jg3m08", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "vsmir (generated)", id: "1509702925282-13") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "person_role", constraintName: "FKs7asxi8amiwjjq1sonlc4rihn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role")
    }
}
