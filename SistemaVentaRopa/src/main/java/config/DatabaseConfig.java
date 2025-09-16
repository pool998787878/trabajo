package config;

import java.util.Properties;

/**
 * Configuración para futuras implementaciones de base de datos
 */
public class DatabaseConfig {
    private static final Properties config = new Properties();

    static {
        // Configuración para SQLite (futuro)
        config.setProperty("db.driver", "org.sqlite.JDBC");
        config.setProperty("db.url", "jdbc:sqlite:ropa_juvenil.db");
        config.setProperty("db.user", "");
        config.setProperty("db.password", "");
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
    }
}
