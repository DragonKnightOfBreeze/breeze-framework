@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.reflect.extensions

import com.windea.breezeframework.core.annotations.api.*
import kotlin.reflect.*

/**判断指定Class是否在className中。*/
inline fun checkClassForName(className: String): Boolean {
	return runCatching { Class.forName(className) }.onFailure { it.printStackTrace() }.isSuccess
}


/**得到指定类型的名字。*/
@TrickImplementationApi("Can never be implemented for all situations.")
inline fun <reified T> nameOf(): String? {
	return T::class.simpleName
}

/**得到指定项的名字。适用于：类型、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi("Can never be implemented for all situations.")
inline fun nameOf(target: Any?): String? {
	return when {
		target == null -> null
		target is KClass<*> -> target.simpleName
		target is KCallable<*> -> target.name
		//无法直接通过方法的引用得到参数
		target is KParameter -> target.name
		//无法得到局部变量的任何信息
		else -> target::class.simpleName
	}
}
