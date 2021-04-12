package com.example.notebookscatalog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notebookscatalog.db.converters.DeviceTypeConverter
import com.example.notebookscatalog.db.dao.BrandDao
import com.example.notebookscatalog.db.dao.DeviceDao
import com.example.notebookscatalog.db.entities.*
import com.example.notebookscatalog.db.entities.relations.ModelSpecCrossRef
import com.example.notebookscatalog.db.enums.DeviceType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        Brand::class,
        Device::class,
        Screen::class
//        Model::class,
//        Spec::class,
//        ModelSpecCrossRef::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(DeviceTypeConverter::class)
abstract class DevicesDatabase : RoomDatabase() {

    abstract fun brandDao(): BrandDao
    abstract fun deviceDao(): DeviceDao

    private class DeviceDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
//                    populateBrands(database.brandDao())
//                    prePopulateDevices(database.deviceDao())
                }
            }
        }

        suspend fun populateBrands(brandDao: BrandDao) {
            brandDao.deleteAll()

            val brandsList = listOf(
                Brand(null, "Acer"),
                Brand(null, "Asus"),
                Brand(null, "Apple"),
                Brand(null, "HP"),
                Brand(null, "Dell"),
                Brand(null, "Lenovo"),
                Brand(null, "Huawei"),
                Brand(null, "Xiaomi"),
                Brand(null, "HONOR")
            )

            brandsList.forEach { brand ->
                brandDao.insert(brand)
            }
        }

//        suspend fun prePopulateDevices(deviceDao: DeviceDao) {
//            val devicesList = listOf(
//                Device(null, "Asus", "TUF Gaming F15",
//                "https://content2.onliner.by/catalog/device/header/5d591589287bdfe724068e346033c6cc.jpeg",
//                "15.6, 1920 x 1080", "Intel Core I5, 16Gb RAM, 512Gb SSD, 4Gb Video", DeviceType.Notebook),
//                Device(null, "HONOR", "Magic 14 2020 53010 VTY",
//                    "https://content2.onliner.by/catalog/device/header/3ca32c20c4104d66bc0bf2155d9b85b6.jpeg",
//                    "14.0, 1920 x 1080", "AMD Ryzen 5, 8Gb RAM, 512Gb SSD", DeviceType.Notebook),
//                Device(null, "Apple", "Macbook Air 13 M1 2020",
//                    "https://content2.onliner.by/catalog/device/header/9a08e5f5400e508fb975bf697669c2b5.jpeg",
//                    "13.3, 2560 x 1600", "Apple M1, 8Gb RAM, 256Gb SSD, M1 GPU Video", DeviceType.Notebook)
//            )
//
//            devicesList.forEach { device ->
//                deviceDao.insert(device)
//            }
//        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DevicesDatabase? = null

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE device RENAME TO devices")
                database.execSQL("ALTER TABLE device ADD COLUMN brandId INTEGER")
                database.execSQL("UPDATE device SET brandId = (SELECT id FROM brand WHERE name = device.brand_name)")
            }
        }

        private val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE note_screen(id INTEGER PRIMARY KEY AUTOINCREMENT, size TEXT, resolution TEXT )")
                database.execSQL("INSERT INTO note_screen(size, resolution) SELECT SUBSTR(screen, 0, INSTR(screen, ',')), SUBSTR(screen, INSTR(screen, ',')+2) FROM device")
                database.execSQL( "CREATE TABLE new_device(id INTEGER PRIMARY KEY AUTOINCREMENT, brand_name TEXT NOT NULL, model TEXT NOT NULL, img_uri TEXT, hardware TEXT NOT NULL, device_type INTEGER NOT NULL, brandId INTEGER)")
                database.execSQL("INSERT INTO new_device(brand_name, model, img_uri, hardware, device_type, brandId) SELECT brand_name, model, img_uri, hardware, device_type, brandId FROM device")
                database.execSQL("DROP TABLE device")
                database.execSQL("ALTER TABLE new_device RENAME TO device")
            }
        }

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): DevicesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DevicesDatabase::class.java,
                    "devices_database"
                )
                    .addMigrations(migration_1_2, migration_2_3)
                    .addCallback(DeviceDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}