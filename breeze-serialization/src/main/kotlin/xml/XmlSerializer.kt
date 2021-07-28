// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.xml

import icu.windea.breezeframework.serialization.*

/**
 * Xml数据的序列化器。
 *
 * @see BreezeXmlSerializer
 * @see JacksonXmlSerializer
 */
interface XmlSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormats.Xml

	/**
	 * 默认的Xml的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default : XmlSerializer by defaultXmlSerializer
}
