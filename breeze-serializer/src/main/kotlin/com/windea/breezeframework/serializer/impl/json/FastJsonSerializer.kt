package com.windea.breezeframework.serializer.impl.json

import com.alibaba.fastjson.*
import java.io.*
import java.lang.reflect.*

/**由FastJson实现的Json序列化器。*/
internal object FastJsonSerializer : JsonSerializer {
	override fun <T : Any> read(string: String, type: Class<T>): T {
		return JSON.parseObject(string, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return JSON.parseObject(string, type)
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return JSON.parseObject(file.readText(), type)
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return JSON.parseObject(file.readText(), type)
	}

	override fun <T : Any> write(data: T): String {
		return JSON.toJSONString(data, true)
	}

	override fun <T : Any> write(data: T, file: File) {
		return JSON.writeJSONString(file.writer(), data)
	}
}
