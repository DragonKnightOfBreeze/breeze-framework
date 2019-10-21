package com.windea.breezeframework.data.domain

import java.io.*

/**泛型实体类。此类的子类应当是开放的。不提供[toString]方法的默认实现。*/
@Deprecated("使用基于顶级方法的通用方法实现。如：`equalsBySelect()`。")
abstract class TEntity<ID> : Serializable {
	open var id: ID? = null
	
	@Suppress("DEPRECATION")
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false
		
		other as TEntity<*>
		if(id != other.id) return false
		return true
	}
	
	override fun hashCode(): Int {
		return id?.hashCode() ?: 0
	}
}
