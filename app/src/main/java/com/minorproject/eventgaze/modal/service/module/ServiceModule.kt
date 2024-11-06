package com.minorproject.eventgaze.modal.service.module

import com.minorproject.eventgaze.modal.service.AccountService
import com.minorproject.eventgaze.modal.service.LogService
import com.minorproject.eventgaze.modal.service.impl.AccountServiceImpl
import com.minorproject.eventgaze.modal.service.impl.LogServiceImpl
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
