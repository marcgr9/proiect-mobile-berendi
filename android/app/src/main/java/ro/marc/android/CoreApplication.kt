package ro.marc.android

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import ro.marc.android.activity.login.LoginVM
import ro.marc.android.activity.main.MainVM
import ro.marc.android.data.db.Database
import ro.marc.android.data.repo.LocalRepo
import ro.marc.android.data.repo.UserRepo
import ro.marc.android.data.service.UserService
import ro.marc.android.util.Utils

class CoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun getDatabaseModule(): Module {
        return module {
            fun provideDatabase(application: Application): Database {
                return Room
                        .databaseBuilder(application, Database::class.java, "marc_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }

            single { provideDatabase(androidApplication()) }
            single { get<Database>().dao() }
        }
    }

    private fun getRepoModule(): Module {
        return module {
            single { UserRepo(Utils.getRetrofit("http://10.0.2.2:8080", applicationContext).create(UserService::class.java)) }
            single { LocalRepo(get()) }
        }
    }

    private fun getViewModelModule(): Module {
        return module {
            viewModel {
                LoginVM(get())
            }
            viewModel {
                MainVM(get())
            }
        }
    }

    private fun startKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoreApplication)
            modules(getDatabaseModule(), getRepoModule(), getViewModelModule())
        }
    }

}
