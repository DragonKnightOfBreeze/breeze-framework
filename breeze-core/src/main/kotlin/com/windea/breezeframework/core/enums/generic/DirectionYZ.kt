package com.windea.breezeframework.core.enums.generic

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.enums.generic.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向")
enum class DirectionYZ(
	val dimension: Dimension
) {
	@Name("原点")
	Origin(Point),
	@Name("上")
	Up(Height),
	@Name("下")
	Down(Height),
	@Name("前")
	Forward(Length),
	@Name("后")
	Backward(Length)
}
