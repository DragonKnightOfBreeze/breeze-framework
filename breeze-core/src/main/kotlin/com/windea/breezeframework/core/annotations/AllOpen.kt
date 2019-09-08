package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationTarget.*

/**适用Kotlin的allOpen编译器插件的标准注解。让被注解的类及其属性和方法默认是开放的。需要自行配置。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(CLASS)
annotation class AllOpen
