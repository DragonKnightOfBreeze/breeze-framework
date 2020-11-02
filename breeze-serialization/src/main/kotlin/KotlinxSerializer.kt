// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.core.domain.*
import kotlinx.serialization.*

/**
 * 由Kotlinx Serialization委托实现的序列化器。
 *
 * @see kotlinx.serialization.StringFormat
 */
interface KotlinxSerializer : Serializer,DelegateSerializer
