package com.minorproject.eventgaze.model.service.module

import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.model.service.impl.AccountServiceImpl
import com.minorproject.eventgaze.model.service.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl:AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

}
