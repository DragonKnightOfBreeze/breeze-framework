// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*
import java.lang.reflect.*

/**
 * Csv数据的读取器。
 */
internal class CsvReader @PublishedApi internal constructor(
	override val config: CsvConfig = CsvConfig()
): DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
