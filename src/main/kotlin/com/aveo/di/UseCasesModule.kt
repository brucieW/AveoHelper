package com.aveo.di

import com.aveo.domain.use_cases.AveoUseCases
import com.aveo.domain.use_cases.GetUsers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

//val useCasesModule = DI.Module("Use Cases") {
//    bind<AveoUseCases> { singleton { AveoUseCases(instance()) }}
//    bind<GetUsers> { singleton { GetUsers(instance()) }}
//}