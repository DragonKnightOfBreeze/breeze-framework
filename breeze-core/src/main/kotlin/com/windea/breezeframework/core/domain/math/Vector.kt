package com.windea.breezeframework.core.domain.math

interface Vector<T : Vector<T>> {
	/**模长*/
	val length: Float
	/**单位向量。*/
	val unitVector: T
	/**是否是零向量。*/
	val isOriginVector: Boolean
	/**是否是单位向量。*/
	val isUnitVector: Boolean
}

