package com.windea.breezeframework.logger

import mu.*
import kotlin.test.*

class Tests {
	@Test
	fun test() {
		loggerConfig.output = "D:\\My Documents\\My Projects\\Managed\\Breeze-Framework\\breeze-logger\\src\\test\\resources\\log.txt"
		
		logger.trace("trace")
		logger.debug("debug")
		logger.info("info")
		logger.warn("warn")
		logger.error("error")
		logger.fatal("fatal")
	}
	
	@Test
	fun test2() {
		val kLogger = KotlinLogging.logger { }
		kLogger.trace("trace")
		kLogger.debug("debug")
		kLogger.info("info")
		kLogger.warn("warn")
		kLogger.error("error")
	}
}
