package ro.marc.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import ro.marc.android.activity.login.LoginVM
import ro.marc.android.activity.main.MainVM
import ro.marc.android.data.UserRepo
import ro.marc.android.data.UserService
import ro.marc.android.util.Utils

class CoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

//    fun getDatabaseModule(): Module {
//        return module {
//            fun provideDatabase(application: Application): Database {
//                return Room
//                        .databaseBuilder(application, Database::class.java, "marc_db")
//                        .fallbackToDestructiveMigration()
//                        .allowMainThreadQueries()
//                        .build()
//            }
//
//            single { provideDatabase(androidApplication()) }
//            single { get<Database>().paymentsDAO() }
//        }
//    }
//
    fun getRepoModule(): Module {
        return module {
            single<UserRepo> { UserRepo(Utils.getRetrofit("http://10.0.2.2:8080", applicationContext).create(UserService::class.java)) }
        }
    }

    private fun getViewModelModule(): Module {
        return module {
            viewModel {
                LoginVM(get())
            }
            viewModel {
                MainVM()
            }
        }
    }

    private fun startKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoreApplication)
            modules(getRepoModule(), getViewModelModule())
        }
    }

}
