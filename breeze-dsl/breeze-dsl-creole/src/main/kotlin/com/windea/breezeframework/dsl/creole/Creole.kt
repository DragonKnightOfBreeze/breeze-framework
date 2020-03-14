@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**Creole。*/
@CreoleDsl
interface Creole {
	/**Creole文档。*/
	@CreoleDsl
	class Document @PublishedApi internal constructor() : CreoleDslEntry, CreoleDslInlineEntry {
		override val content:MutableList<CreoleDslTopElement> = mutableListOf()

		override fun toString() = contentString()
	}

	/**Creole富文本。*/
	@CreoleDsl
	interface RichText : CreoleDslInlineElement, HandledCharSequence

	/**CreoleUnicode文本。*/
	@CreoleDsl
	inline class UnicodeText @PublishedApi internal constructor(override val text:String) : RichText {
		override fun toString() = "<U+$text>"
	}

	/**Creole加粗文本。*/
	@CreoleDsl
	inline class BoldText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "**$text**"
	}

	/**Creole斜体文本。*/
	@CreoleDsl
	inline class ItalicText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "//$text//"
	}

	/**Creole代码文本。*/
	@CreoleDsl
	inline class MonospacedText @PublishedApi internal constructor(override val text:String) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole删除线文本。*/
	@CreoleDsl
	inline class StrokedText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole下划线文本。*/
	@CreoleDsl
	inline class UnderlineText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "__${text}__"
	}

	/**Creole波浪线文本。*/
	@CreoleDsl
	inline class WavedText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "~~$text~~"
	}

	/**Creole转义文本。*/
	@CreoleDsl
	inline class EscapedText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "~$text"
	}

	/**
	 * Creole的图标。
	 * 参见：[OpenIconic](https://useiconic.com/open/)
	 * */
	@CreoleDsl
	inline class Icon @PublishedApi internal constructor(override val text:String) : RichText {
		val name:String get() = text
		override fun toString() = "<&$text>"
	}

	/**Creole文本块。*/
	@CreoleDsl
	class TextBlock @PublishedApi internal constructor(
		val text:String
	) : CreoleDslTopElement {
		override fun toString() = text
	}

	//DELAY HtmlBlock

	/**Creole标题。*/
	@CreoleDsl
	interface Heading : CreoleDslTopElement {
		val text:String
	}

	/**Creole一级标题。*/
	@CreoleDsl
	inline class Heading1 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 1)
	}

	/**Creole二级标题。*/
	@CreoleDsl
	inline class Heading2 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 2)
	}

	/**Creole三级标题。*/
	@CreoleDsl
	inline class Heading3 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 3)
	}

	/**Creole四级标题。*/
	@CreoleDsl
	inline class Heading4 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 4)
	}

	/**Creole水平分割线。*/
	@CreoleDsl
	class HorizontalLine @PublishedApi internal constructor(
		val type:HorizontalLineType = HorizontalLineType.Normal
	) : CreoleDslTopElement {
		override fun toString() = type.marker.repeat(config.markerCount)
	}

	/**Creole水平标题。*/
	@CreoleDsl
	class HorizontalTitle @PublishedApi internal constructor(
		val text:String, val type:HorizontalLineType = HorizontalLineType.Normal
	) : CreoleDslTopElement {
		override fun toString() = type.marker.repeat(config.markerCount).let { "$it$text$it" }
	}

	/**Creole列表。*/
	@CreoleDsl
	class List @PublishedApi internal constructor() : CreoleDslTopElement {
		val nodes:MutableList<ListNode> = mutableListOf()

		override fun toString() = nodes.joinToString("\n")
	}

	/**Creole列表节点。*/
	@CreoleDsl
	abstract class ListNode(
		internal val prefixMarkers:String, val text:String
	) : CreoleDslElement {
		val nodes:MutableList<ListNode> = mutableListOf()

		override fun toString() = "$prefixMarkers $text${nodes.typingAll("\n", "\n")}"
	}

	/**Creole有序列表节点。*/
	@CreoleDsl
	class OrderedListNode @PublishedApi internal constructor(text:String) : ListNode("#", text)

	/**Creole无序列表节点。*/
	@CreoleDsl
	class UnorderedListNode @PublishedApi internal constructor(text:String) : ListNode("*", text)

	/**Creole树。*/
	@CreoleDsl
	class Tree @PublishedApi internal constructor(
		val title:String
	) : CreoleDslTopElement {
		val nodes:MutableList<TreeNode> = mutableListOf()

		override fun toString() = "$title${nodes.typingAll("\n", "\n")}"
	}

	/**Creole树节点。*/
	@CreoleDsl
	class TreeNode @PublishedApi internal constructor(
		val text:String
	) : CreoleDslElement {
		val nodes:MutableList<TreeNode> = mutableListOf()

		override fun toString() = "|_ $text${nodes.typingAll("\n", "\n").prependIndent(config.indent)}"
	}

	//DELAY pretty format
	/**Creole表格。*/
	@CreoleDsl
	class Table @PublishedApi internal constructor() : CreoleDslTopElement {
		var header:TableHeader = TableHeader()
		val rows:MutableList<TableRow> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(rows.isNotEmpty()) { "Table row size must be positive." }

			//actual column size may not equal to columns.size, and can be user defined
			val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
			header.columnSize = actualColumnSize
			rows.forEach { it.columnSize = actualColumnSize }
			val headerRowSnippet = header.toString()
			val rowsSnippet = rows.joinToString("\n")
			return "$headerRowSnippet\n$rowsSnippet"
		}
	}

	/**Creole表格头部。*/
	@CreoleDsl
	class TableHeader @PublishedApi internal constructor() : CreoleDslElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
				else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, config.emptyColumnText)
			}.joinToString("|", "|", "|")
		}

		override fun String.unaryPlus() = column(this)

		@DslFunction
		@CreoleDsl
		infix fun TableColumn.align(alignment:TableAlignment) = apply { this.alignment = alignment }
	}

	/**Creole表格行。*/
	@CreoleDsl
	open class TableRow @PublishedApi internal constructor() : CreoleDslElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, config.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		override fun String.unaryPlus() = column(this)
	}

	/**Creole表格列。*/
	@CreoleDsl
	open class TableColumn @PublishedApi internal constructor(
		val text:String = config.emptyColumnText
	) : CreoleDslElement {
		var color:String? = null
		var alignment:TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString():String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			return "$colorSnippet$text"
		}

		fun toStringInHeader():String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			val (l, r) = alignment.textPair
			return "$l $colorSnippet$text $r"
		}
	}

	/**Creole水平线的类型。*/
	@CreoleDsl
	enum class HorizontalLineType(
		internal val marker:Char
	) {
		Normal('-'), Double('='), Strong('_'), Dotted('.')
	}

	/**Creole表格的对齐方式。*/
	@CreoleDsl
	enum class TableAlignment(
		internal val textPair:Pair<String, String>
	) {
		None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
	}

	/**
	 * Creole的配置。
	 * @property indent 文本的缩进。
	 * @property emptyColumnText 表格的空单元格的文本。
	 * @property truncated 省略字符串。
	 * @property doubleQuoted 是否偏向使用双引号。
	 * @property markerCount 可重复标记的个数。
	 */
	@CreoleDsl
	data class Config(
		var indent:String = "  ",
		var emptyColumnText:String = "  ",
		var truncated:String = "...",
		var doubleQuoted:Boolean = true,
		var markerCount:Int = 3
	) {
		@PublishedApi internal val quote get() = if(doubleQuoted) '\"' else '\''

		@PublishedApi
		internal fun repeat(marker:Char) = marker.repeat(markerCount)
	}

	companion object {
		@PublishedApi internal val config = Config()

		@PublishedApi
		internal fun heading(text:String, headingLevel:Int) = "${"#".repeat(headingLevel)} $text"
	}
}
