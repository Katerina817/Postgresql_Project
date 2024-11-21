package org.example.postgresql_project;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    @Setter
    @Getter
    private static Connection connection;
}
