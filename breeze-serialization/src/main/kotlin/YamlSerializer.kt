// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.fasterxml.jackson.dataformat.yaml.*
import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.representer.*
import org.yaml.snakeyaml.constructor.Constructor
import java.lang.reflect.*

/**
 * Yaml的序列化器。
 */
interface YamlSerializer : Serializer {
	override val dataType: DataType get() = DataType.Yaml

	/**
	 * 序列化指定的一组对象。
	 */
	fun serializeAll(value:List<Any>):String

	/**
	 * 反序列化指定的文本为一组对象。
	 */
	fun deserializeAll(value:String):List<Any>

	//region Yaml Serializers
	/**
	 * 由Jackson实现的Yaml序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
	 */
	object JacksonYamlSerializer : YamlSerializer, JacksonSerializer, DelegateSerializer {
		private val mapper by lazy { YAMLMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun <T : Any> serialize(value: T): String {
			return mapper.writeValueAsString(value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return mapper.readValue(value, type)
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return mapper.readValue(value, mapper.typeFactory.constructType(type))
		}

		override fun serializeAll(value: List<Any>): String {
			throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
		}

		override fun deserializeAll(value: String): List<Any> {
			throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
		}
	}

	/**
	 * 由SnakeYaml实现的Yaml序列化器。
	 * @see org.yaml.snakeyaml.Yaml
	 */
	object SnakeYamlSerializer : YamlSerializer, DelegateSerializer {
		internal val loaderOptions = LoaderOptions()
		internal val dumperOptions = DumperOptions()
		internal val yaml by lazy { Yaml(Constructor(), Representer(), dumperOptions, loaderOptions) }

		init {
			dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
		}

		override fun <T : Any> serialize(value: T): String {
			return yaml.dump(value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return yaml.loadAs(value, type)
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return yaml.load(value)
		}

		override fun serializeAll(value: List<Any>): String {
			return yaml.dumpAll(value.iterator())
		}

		override fun deserializeAll(value: String): List<Any> {
			return yaml.loadAll(value).toList()
		}
	}

	/**
	 * 默认的Yaml序列化器。
	 */
	object BreezeYamlSerializer:YamlSerializer{
		override fun serializeAll(value: List<Any>): String {
			TODO()
		}

		override fun deserializeAll(value: String): List<Any> {
			TODO()
		}

		override fun <T : Any> serialize(value: T): String {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			TODO()
		}

	}
	//endregion
}
