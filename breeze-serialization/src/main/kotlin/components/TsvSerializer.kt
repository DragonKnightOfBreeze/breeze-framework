// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.serialization.extensions.*
import com.windea.breezeframework.serialization.extensions.defaultTsvSerializer
import java.lang.reflect.*

/**
 * Tsv序列化器。
 */
interface TsvSerializer : Serializer {
	override val dataType: DataType get()= DataType.Tsv

	//region Tsv Serializers
	/**
	 * 默认的Tsv序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: TsvSerializer by defaultTsvSerializer

	/**
	 * 框架本身实现的Tsv序列化器。
	 */
	class BreezeTsvSerializer: TsvSerializer {
		override fun <T : Any> serialize(value: T): String {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			TODO()
		}

		private fun <T:Any> doSerialize(value:T):String{
			return when(value){
				is Array<*> -> doSerializeArray(value)
				is Map<*,*> -> doSerializeMap(value)
				else -> TODO()
			}
		}

		private fun doSerializeArray(value:Array<*>):String{
			TODO()
		}

		private fun doSerializeIterable(value:Iterable<*>):String{
			TODO()
		}

		private fun doSerializeMap(value:Map<*,*>):String{
			TODO()
		}

		private fun doSerializeSequence(value:Iterable<*>):String{
			TODO()
		}
	}
	//endregion
}
