// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.serialization.extensions.*

/**
 * Tsv的序列化器。
 */
@BreezeComponent
interface TsvSerializer : DataSerializer {
	override val dataType: DataType get()= DataType.Tsv

	/**
	 * 默认的Tsv的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: TsvSerializer by defaultTsvSerializer

	/**
	 * 由Breeze Framework实现的轻量的Tsv的序列化器。
	 */
	class BreezeTsvSerializer : BreezeSerializer.BreezeTsvSerializer()
}
