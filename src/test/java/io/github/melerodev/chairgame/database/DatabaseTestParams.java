package io.github.melerodev.chairgame.database;

import io.github.melerodev.chairgame.database.handler.DatabaseType;

record DatabaseTestParams(String jdbcPrefix, DatabaseType requiredDatabaseType, String tablePrefix) {
}
