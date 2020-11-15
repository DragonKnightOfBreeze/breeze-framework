// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.alibaba.fastjson.*
import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*

/**
 * 由FastJson实现的Json的序列化器。
 *
 * @see com.alibaba.fastjson.JSON
 */
@BreezeComponent
open class FastJsonSerializer : JsonSerializer, DelegateSerializer {
	override fun <T> serialize(target: T): String {
		return JSON.toJSONString(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return JSON.parseObject(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return JSON.parseObject(value, type)
	}
}
