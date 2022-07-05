//import com.aveo.db.AveoDatabase
//import com.squareup.sqldelight.db.SqlDriver

//expect fun createDb() : AveoDatabase
//
//fun createDatabase(driverFactory): AveoDatabase {
//    val driver = driverFactory.createDriver()
//    val database = AveoDatabase(driver)
//
//}
//
//// in src/jvmMain/kotlin
//actual class DriverFactory {
//    actual fun createDriver(): SqlDriver {
//        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
//        Database.Schema.create(driver)
//        return driver
//    }
//}