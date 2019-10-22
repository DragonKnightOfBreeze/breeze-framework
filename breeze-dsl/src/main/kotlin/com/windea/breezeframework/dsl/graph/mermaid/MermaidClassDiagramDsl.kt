@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.interfaces.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote

//NOTE unstable raw api
//NOTE members should be declared in class

//REGION top annotations and interfaces

@DslMarker
private annotation class MermaidClassDiagramDsl

/**Mermaid类图。*/
@ReferenceApi("[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)")
@MermaidClassDiagramDsl
class MermaidClassDiagram @PublishedApi internal constructor() : Mermaid(), MermaidClassDiagramDslEntry, IndentContent {
	override val classes: MutableSet<MermaidClassDiagramClass> = mutableSetOf()
	override val relations: MutableList<MermaidClassDiagramRelation> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.where(indentContent) { it.prependIndent(indent) }
		return "classDiagram\n$contentSnippet"
	}
}

//REGION dsl interfaces

/**Mermaid类图Dsl元素的入口。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramDslEntry : MermaidDslEntry,
	WithTransition<MermaidClassDiagramClass, MermaidClassDiagramRelation> {
	val classes: MutableSet<MermaidClassDiagramClass>
	val relations: MutableList<MermaidClassDiagramRelation>
	
	fun toContentString(): String {
		return arrayOf(
			classes.joinToStringOrEmpty("\n"),
			relations.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
	}
	
	override fun String.fromTo(other: String) =
		relation(this, other, MermaidClassDiagramRelationType.Link)
	
	@MermaidClassDiagramDsl
	infix fun String.inherits(other: String) =
		relation(this, other, MermaidClassDiagramRelationType.ReversedInheritance)
	
	@MermaidClassDiagramDsl
	infix fun String.composes(other: String) =
		relation(this, other, MermaidClassDiagramRelationType.ReversedComposition)
	
	@MermaidClassDiagramDsl
	infix fun String.aggregates(other: String) =
		relation(this, other, MermaidClassDiagramRelationType.ReversedAggregation)
	
	@MermaidClassDiagramDsl
	infix fun String.associates(other: String) =
		relation(this, other, MermaidClassDiagramRelationType.ReversedAssociation)
	
	@MermaidClassDiagramDsl
	override fun MermaidClassDiagramRelation.invoke(text: String) = this.also { it.text = text }
}

/**Mermaid类图Dsl元素。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid类图类。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramClass @PublishedApi internal constructor(
	val name: String
) : MermaidClassDiagramDslElement, IndentContent, CanEqual {
	var annotation: MermaidClassDiagramAnnotation? = null
	val properties: MutableSet<MermaidClassDiagramProperty> = mutableSetOf()
	val methods: MutableSet<MermaidClassDiagramMethod> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			annotation?.toString().orEmpty(),
			properties.joinToStringOrEmpty("\n"),
			methods.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty("\n")
			.ifEmpty { return "class $name" }
			.where(indentContent) { it.prependIndent(indent) }
		return "class $name {\n$contentSnippet\n}"
	}
	
	@MermaidClassDiagramDsl
	fun `public`(member: MermaidClassDiagramMember) =
		member.also { it.visibility = MermaidClassDiagramVisibility.Public }
	
	@MermaidClassDiagramDsl
	fun `private`(member: MermaidClassDiagramMember) =
		member.also { it.visibility = MermaidClassDiagramVisibility.Private }
	
	@MermaidClassDiagramDsl
	fun `protected`(member: MermaidClassDiagramMember) =
		member.also { it.visibility = MermaidClassDiagramVisibility.Protected }
	
	@MermaidClassDiagramDsl
	fun `package`(member: MermaidClassDiagramMember) =
		member.also { it.visibility = MermaidClassDiagramVisibility.Protected }
	
	operator fun String.invoke(member: MermaidClassDiagramMember) = member.also { it.type = this }
	
	@Suppress("DEPRECATION")
	operator fun String.invoke(vararg params: String) = method(this, *params)
}

/**Mermaid类图注解。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramAnnotation(
	val name: String //NOTE could be custom
) : MermaidClassDiagramDslElement, CanEqual {
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	override fun toString(): String {
		return "<<$name>>"
	}
}

/**Mermaid类图成员。*/
@MermaidClassDiagramDsl
sealed class MermaidClassDiagramMember(
	val name: String
) : MermaidClassDiagramDslElement, CanEqual {
	var visibility: MermaidClassDiagramVisibility? = null
	var type: String? = null
}

/**Mermaid类图属性。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramProperty(
	name: String
) : MermaidClassDiagramMember(name) {
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	override fun toString(): String {
		return "${visibility?.text.orEmpty()}${type?.let { "$it " }.orEmpty()}$name"
	}
}

/**Mermaid类图方法。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramMethod(
	name: String,
	val params: Array<out String>
) : MermaidClassDiagramMember(name) {
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name, params) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name, params) }
	
	override fun toString(): String {
		return "${visibility?.text.orEmpty()}${type?.let { "$it " }.orEmpty()}$name(${params.joinToStringOrEmpty()})"
	}
}

/**Mermaid类图关系。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramRelation(
	val fromClassId: String,
	val toClassId: String,
	val type: MermaidClassDiagramRelationType,
	var text: String? = null //NOTE can wrap by "<br>"
) : MermaidClassDiagramDslElement {
	var fromCardinality: String? = null //NOTE syntax: "0", "0..1", "0..*", "many"
	var toCardinality: String? = null
	
	//NOTE syntax: $fromClassId $fromCardinality? $relationType $toCardinality? $toClassId: $text?
	override fun toString(): String {
		return arrayOf(
			fromClassId, fromCardinality?.wrapQuote(quote), type.text, toCardinality?.wrapQuote(quote), toClassId
		).filterNotNull().joinToStringOrEmpty(" ", "", text?.let { ": $it" }.orEmpty())
	}
}

//REGION enumerations and constants

/**Mermaid类图类/成员的可见性。*/
@MermaidClassDiagramDsl
enum class MermaidClassDiagramVisibility(val text: String) {
	Public("+"), Private("-"), Protected("#"), Package("~") //NOTE why no "internal" ???
}

/**Mermaid类图注解的类型。*/
@MermaidClassDiagramDsl
enum class MermaidClassDiagramAnnotationType(val text: String) {
	Interface("interface"), Abstract("abstract"), Service("service"), Enumeration("enumeration")
}

/**Mermaid类图关系的类型。*/
@MermaidClassDiagramDsl
enum class MermaidClassDiagramRelationType(val text: String) {
	//NOTE do not allow bidirectional arrows
	Link("--"),
	Inheritance("<|--"), Composition("*--"), Aggregation("o--"), Association("<--"),
	ReversedInheritance("--|>"), ReversedComposition("--*"), ReversedAggregation("--o"), ReversedAssociation("-->")
}

//REGION build extensions

@MermaidClassDiagramDsl
inline fun mermaidClassDiagram(builder: MermaidClassDiagram.() -> Unit) =
	MermaidClassDiagram().also { it.builder() }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.`class`(name: String, builder: MermaidClassDiagramClass.() -> Unit) =
	MermaidClassDiagramClass(name).also { it.builder() }.also { classes += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(fromClassId: String, toClassId: String,
	type: MermaidClassDiagramRelationType, text: String? = null) =
	MermaidClassDiagramRelation(fromClassId, toClassId, type, text = text).also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(fromClass: MermaidClassDiagramClass, toClass: MermaidClassDiagramClass,
	type: MermaidClassDiagramRelationType, text: String? = null) =
	MermaidClassDiagramRelation(fromClass.name, toClass.name, type, text = text).also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(
	fromClassId: String,
	fromCardinality: String?,
	type: MermaidClassDiagramRelationType,
	toCardinality: String?,
	toClassId: String,
	text: String? = null
) = MermaidClassDiagramRelation(fromClassId, toClassId, type, text)
	.also { it.fromCardinality = fromCardinality;it.toCardinality = toCardinality }
	.also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.annotation(name: String) =
	MermaidClassDiagramAnnotation(name).also { annotation = it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.annotation(type: MermaidClassDiagramAnnotationType) =
	MermaidClassDiagramAnnotation(type.text).also { annotation = it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.property(name: String) =
	MermaidClassDiagramProperty(name).also { properties += it }

@Deprecated("Use code-like-style build extensions.", ReplaceWith("method(name(*params))"))
@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.method(name: String, vararg params: String) =
	MermaidClassDiagramMethod(name, params).also { methods += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.method(method: MermaidClassDiagramMethod) = method.also { methods += it }

@Deprecated("Use wrapped-style build extensions.", ReplaceWith("\"type\"(this)"))
@MermaidClassDiagramDsl
inline infix fun <T : MermaidClassDiagramMember> T.type(type: String) =
	this.also { it.type = type }

@Deprecated("Use wrapped-style build extensions.", ReplaceWith("`public`(this)"))
@MermaidClassDiagramDsl
inline infix fun <T : MermaidClassDiagramMember> T.visibility(visibility: MermaidClassDiagramVisibility) =
	this.also { it.visibility = visibility }

@MermaidClassDiagramDsl
inline infix fun MermaidClassDiagramRelation.cardinality(cardinalityPair: Pair<String?, String?>) =
	this.also { it.fromCardinality = cardinalityPair.first;it.toCardinality = cardinalityPair.second }
