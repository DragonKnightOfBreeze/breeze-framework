@file:JvmName("ClassExtensions")

package com.windea.breezeframework.reflect.extensions

import java.io.*
import java.lang.reflect.*

/**判断当前类型是否是基本类型的包装类型。*/
val <T> Class<T>.isBoxed: Boolean
	get() = java.lang.Byte::class.java == this || java.lang.Short::class.java == this ||
	        java.lang.Integer::class.java == this || java.lang.Long::class.java == this ||
	        java.lang.Float::class.java == this || java.lang.Double::class.java == this ||
	        java.lang.Character::class.java == this || java.lang.Boolean::class.java == this

/**判断当前类型是否是字符序列类型。*/
val <T> Class<T>.isCharSequence: Boolean get() = CharSequence::class.java.isAssignableFrom(this)

/**判断当前类型是否是可迭代类型。*/
val <T> Class<T>.isIterable: Boolean get() = Iterable::class.java.isAssignableFrom(this)

/**判断当前类型是否是列表类型。*/
val <T> Class<T>.isList: Boolean get() = List::class.java.isAssignableFrom(this)

/**判断当前类型是否是集类型。*/
val <T> Class<T>.isSet: Boolean get() = Set::class.java.isAssignableFrom(this)

/**判断当前类型是否是映射类型。*/
val <T> Class<T>.isMap: Boolean get() = Map::class.java.isAssignableFrom(this)

/**判断当前类型是否是可序列化类型。*/
val <T> Class<T>.isSerializable: Boolean get() = Serializable::class.java.isAssignableFrom(this)


/**得到当前类型的默认值。*/
val <T> Class<T>.defaultValue: Any?
	get() = when(this) {
		Byte::class.java -> 0.toByte()
		Short::class.java -> 0.toShort()
		Int::class.java -> 0
		Long::class.java -> 0L
		Float::class.java -> 0F
		Double::class.java -> 0.0
		Boolean::class.java -> false
		Char::class.java -> '\u0000'
		else -> null
	}

/**得到当前类型的属性名-取值方法映射。基于取值方法，而非私有字段，并且忽略class属性。*/
val <T> Class<T>.getters: List<Method>
	get() = this.methods.filter { it.name.startsWith("get") && it.name != "getClass" }

/**得到当前类型的属性名-赋值方法映射。基于取值方法。*/
val <T> Class<T>.setters: List<Method>
	get() = this.methods.filter { it.name.startsWith("set") }
