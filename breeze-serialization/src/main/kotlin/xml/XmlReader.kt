// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.xml

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * Xml数据的读取器。
 */
internal class XmlReader @PublishedApi internal constructor(
	override val config: XmlConfig = XmlConfig()
) : DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
