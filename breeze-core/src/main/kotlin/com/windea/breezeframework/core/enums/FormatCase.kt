package com.windea.breezeframework.core.enums

/**显示格式。*/
interface FormatCase {
	val regex: Regex
	val splitFunction: (String) -> List<String>
	val joinFunction: (List<String>) -> String
}
