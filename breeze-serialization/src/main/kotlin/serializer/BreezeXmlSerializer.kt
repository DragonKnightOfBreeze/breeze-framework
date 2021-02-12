// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import java.lang.reflect.*

/**
 * 由Breeze Framework实现的轻量的Xml的序列化器。
 */
class BreezeXmlSerializer : XmlSerializer, BreezeSerializer {
	override fun <T> serialize(target: T): String {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Type): T {
		TODO()
	}
}
