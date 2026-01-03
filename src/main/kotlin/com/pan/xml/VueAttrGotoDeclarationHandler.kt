package com.pan.xml

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.lang.javascript.psi.JSFile
import com.intellij.lang.javascript.psi.JSReferenceExpression
import com.intellij.lang.javascript.psi.ecma6.TypeScriptInterface
import com.intellij.lang.javascript.psi.ecmal4.JSReferenceListMember
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag

val htmlElementTagNameMap = mapOf(
    "a" to "HTMLAnchorElement",
    "abbr" to "HTMLElement",
    "address" to "HTMLElement",
    "area" to "HTMLAreaElement",
    "article" to "HTMLElement",
    "aside" to "HTMLElement",
    "audio" to "HTMLAudioElement",
    "b" to "HTMLElement",
    "base" to "HTMLBaseElement",
    "bdi" to "HTMLElement",
    "bdo" to "HTMLElement",
    "blockquote" to "HTMLQuoteElement",
    "body" to "HTMLBodyElement",
    "br" to "HTMLBRElement",
    "button" to "HTMLButtonElement",
    "canvas" to "HTMLCanvasElement",
    "caption" to "HTMLTableCaptionElement",
    "cite" to "HTMLElement",
    "code" to "HTMLElement",
    "col" to "HTMLTableColElement",
    "colgroup" to "HTMLTableColElement",
    "data" to "HTMLDataElement",
    "datalist" to "HTMLDataListElement",
    "dd" to "HTMLElement",
    "del" to "HTMLModElement",
    "details" to "HTMLDetailsElement",
    "dfn" to "HTMLElement",
    "dialog" to "HTMLDialogElement",
    "div" to "HTMLDivElement",
    "dl" to "HTMLDListElement",
    "dt" to "HTMLElement",
    "em" to "HTMLElement",
    "embed" to "HTMLEmbedElement",
    "fieldset" to "HTMLFieldSetElement",
    "figcaption" to "HTMLElement",
    "figure" to "HTMLElement",
    "footer" to "HTMLElement",
    "form" to "HTMLFormElement",
    "h1" to "HTMLHeadingElement",
    "h2" to "HTMLHeadingElement",
    "h3" to "HTMLHeadingElement",
    "h4" to "HTMLHeadingElement",
    "h5" to "HTMLHeadingElement",
    "h6" to "HTMLHeadingElement",
    "head" to "HTMLHeadElement",
    "header" to "HTMLElement",
    "hgroup" to "HTMLElement",
    "hr" to "HTMLHRElement",
    "html" to "HTMLHtmlElement",
    "i" to "HTMLElement",
    "iframe" to "HTMLIFrameElement",
    "img" to "HTMLImageElement",
    "input" to "HTMLInputElement",
    "ins" to "HTMLModElement",
    "kbd" to "HTMLElement",
    "label" to "HTMLLabelElement",
    "legend" to "HTMLLegendElement",
    "li" to "HTMLLIElement",
    "link" to "HTMLLinkElement",
    "main" to "HTMLElement",
    "map" to "HTMLMapElement",
    "mark" to "HTMLElement",
    "menu" to "HTMLMenuElement",
    "meta" to "HTMLMetaElement",
    "meter" to "HTMLMeterElement",
    "nav" to "HTMLElement",
    "noscript" to "HTMLElement",
    "object" to "HTMLObjectElement",
    "ol" to "HTMLOListElement",
    "optgroup" to "HTMLOptGroupElement",
    "option" to "HTMLOptionElement",
    "output" to "HTMLOutputElement",
    "p" to "HTMLParagraphElement",
    "picture" to "HTMLPictureElement",
    "pre" to "HTMLPreElement",
    "progress" to "HTMLProgressElement",
    "q" to "HTMLQuoteElement",
    "rp" to "HTMLElement",
    "rt" to "HTMLElement",
    "ruby" to "HTMLElement",
    "s" to "HTMLElement",
    "samp" to "HTMLElement",
    "script" to "HTMLScriptElement",
    "search" to "HTMLElement",
    "section" to "HTMLElement",
    "select" to "HTMLSelectElement",
    "slot" to "HTMLSlotElement",
    "small" to "HTMLElement",
    "source" to "HTMLSourceElement",
    "span" to "HTMLSpanElement",
    "strong" to "HTMLElement",
    "style" to "HTMLStyleElement",
    "sub" to "HTMLElement",
    "summary" to "HTMLElement",
    "sup" to "HTMLElement",
    "table" to "HTMLTableElement",
    "tbody" to "HTMLTableSectionElement",
    "td" to "HTMLTableCellElement",
    "template" to "HTMLTemplateElement",
    "textarea" to "HTMLTextAreaElement",
    "tfoot" to "HTMLTableSectionElement",
    "th" to "HTMLTableCellElement",
    "thead" to "HTMLTableSectionElement",
    "time" to "HTMLTimeElement",
    "title" to "HTMLTitleElement",
    "tr" to "HTMLTableRowElement",
    "track" to "HTMLTrackElement",
    "u" to "HTMLElement",
    "ul" to "HTMLUListElement",
    "var" to "HTMLElement",
    "video" to "HTMLVideoElement",
    "wbr" to "HTMLElement"
)
val VueHtmlElementTagNameMap = mapOf(
    "a" to "AnchorHTMLAttributes",
    "abbr" to "HTMLAttributes",
    "address" to "HTMLAttributes",
    "area" to "AreaHTMLAttributes",
    "article" to "HTMLAttributes",
    "aside" to "HTMLAttributes",
    "audio" to "AudioHTMLAttributes",
    "b" to "HTMLAttributes",
    "base" to "BaseHTMLAttributes",
    "bdi" to "HTMLAttributes",
    "bdo" to "HTMLAttributes",
    "blockquote" to "QuoteHTMLAttributes",
    "body" to "BodyHTMLAttributes",
    "br" to "BRHTMLAttributes",
    "button" to "ButtonHTMLAttributes ButtonHTMLAttributes",
    "canvas" to "CanvasHTMLAttributes",
    "caption" to "TableCaptionHTMLAttributes",
    "cite" to "HTMLAttributes",
    "code" to "HTMLAttributes",
    "col" to "TableColHTMLAttributes",
    "colgroup" to "TableColHTMLAttributes",
    "data" to "DataHTMLAttributes",
    "datalist" to "DataListHTMLAttributes",
    "dd" to "HTMLAttributes",
    "del" to "ModHTMLAttributes",
    "details" to "DetailsHTMLAttributes",
    "dfn" to "HTMLAttributes",
    "dialog" to "DialogHTMLAttributes",
    "div" to "DivHTMLAttributes",
    "dl" to "DListHTMLAttributes",
    "dt" to "HTMLAttributes",
    "em" to "HTMLAttributes",
    "embed" to "EmbedHTMLAttributes",
    "fieldset" to "FieldSetHTMLAttributes",
    "figcaption" to "HTMLAttributes",
    "figure" to "HTMLAttributes",
    "footer" to "HTMLAttributes",
    "form" to "FormHTMLAttributes",
    "h1" to "HeadingHTMLAttributes",
    "h2" to "HeadingHTMLAttributes",
    "h3" to "HeadingHTMLAttributes",
    "h4" to "HeadingHTMLAttributes",
    "h5" to "HeadingHTMLAttributes",
    "h6" to "HeadingHTMLAttributes",
    "head" to "HeadHTMLAttributes",
    "header" to "HTMLAttributes",
    "hgroup" to "HTMLAttributes",
    "hr" to "HRHTMLAttributes",
    "html" to "HtmlHTMLAttributes",
    "i" to "HTMLAttributes",
    "iframe" to "IFrameHTMLAttributes",
    "img" to "ImageHTMLAttributes",
    "input" to "InputHTMLAttributes",
    "ins" to "ModHTMLAttributes",
    "kbd" to "HTMLAttributes",
    "label" to "LabelHTMLAttributes",
    "legend" to "LegendHTMLAttributes",
    "li" to "LIHTMLAttributes",
    "link" to "LinkHTMLAttributes",
    "main" to "HTMLAttributes",
    "map" to "MapHTMLAttributes",
    "mark" to "HTMLAttributes",
    "menu" to "MenuHTMLAttributes",
    "meta" to "MetaHTMLAttributes",
    "meter" to "MeterHTMLAttributes",
    "nav" to "HTMLAttributes",
    "noscript" to "HTMLAttributes",
    "object" to "ObjectHTMLAttributes",
    "ol" to "OListHTMLAttributes",
    "optgroup" to "OptGroupHTMLAttributes",
    "option" to "OptionHTMLAttributes",
    "output" to "OutputHTMLAttributes",
    "p" to "ParagraphHTMLAttributes",
    "picture" to "PictureHTMLAttributes",
    "pre" to "PreHTMLAttributes",
    "progress" to "ProgressHTMLAttributes",
    "q" to "QuoteHTMLAttributes",
    "rp" to "HTMLAttributes",
    "rt" to "HTMLAttributes",
    "ruby" to "HTMLAttributes",
    "s" to "HTMLAttributes",
    "samp" to "HTMLAttributes",
    "script" to "ScriptHTMLAttributes",
    "search" to "HTMLAttributes",
    "section" to "HTMLAttributes",
    "select" to "SelectHTMLAttributes",
    "slot" to "SlotHTMLAttributes",
    "small" to "HTMLAttributes",
    "source" to "SourceHTMLAttributes",
    "span" to "HTMLAttributes",
    "strong" to "HTMLAttributes",
    "style" to "StyleHTMLAttributes",
    "sub" to "HTMLAttributes",
    "summary" to "HTMLAttributes",
    "sup" to "HTMLAttributes",
    "table" to "TableHTMLAttributes",
    "tbody" to "TableSectionHTMLAttributes",
    "td" to "TableCellHTMLAttributes",
    "template" to "TemplateHTMLAttributes",
    "textarea" to "TextAreaHTMLAttributes",
    "tfoot" to "TableSectionHTMLAttributes",
    "th" to "TableCellHTMLAttributes",
    "thead" to "TableSectionHTMLAttributes",
    "time" to "TimeHTMLAttributes",
    "title" to "TitleHTMLAttributes",
    "tr" to "TableRowHTMLAttributes",
    "track" to "TrackHTMLAttributes",
    "u" to "HTMLAttributes",
    "ul" to "UListHTMLAttributes",
    "var" to "HTMLAttributes",
    "video" to "VideoHTMLAttributes",
    "wbr" to "HTMLAttributes"
)

class VueAttrGotoDeclarationHandler : GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(
        sourceElement: PsiElement?,
        offset: Int,
        editor: Editor?
    ): Array<out PsiElement?>? {
        if (sourceElement === null) {
            return null;
        }

        // 1. 找到当前点击的 XmlAttribute（:title/title）
        val xmlAttr = PsiTreeUtil.getParentOfType(sourceElement, XmlAttribute::class.java);
        if (xmlAttr === null) {
            val xmlTag = PsiTreeUtil.getParentOfType(sourceElement, XmlTag::class.java) ?: return null;
            val targetElement = handleTagJump(xmlTag, xmlTag.localName)
            return targetElement?.let { arrayOf(it) }
        }
        val attrName = xmlAttr.name

        val tagName = xmlAttr.parent?.name ?: return null
        val domType = htmlElementTagNameMap[tagName] ?: return null


        // 2. 规范化属性名（和之前逻辑一致）
        val coreName = when {
            attrName.startsWith(":") -> attrName.substring(1).split('.').first()
            attrName.startsWith("@") -> "on${attrName.substring(1).split('.').first()}"
            attrName.startsWith("v-bind:") -> attrName.substring(7).split('.').first()
            else -> attrName.split('.').first()
        }.let { if (it == "class") "className" else it }

        val project = sourceElement.project
        val target = findLibDomElementForAttribute(project, domType, coreName) ?: return null

        // 4. 强制返回唯一跳转目标（绕过IDE所有内置逻辑）
        return arrayOf(target)
    }

    // ---------------------- 业务逻辑：标签名跳转（如 span → lib.dom.d.ts 的 HTMLSpanElement） ----------------------
    private fun handleTagJump(xmlTag: XmlTag, tagName: String): PsiElement? {
        // 1. 从标签名映射到 DOM 接口名（如 span → HTMLSpanElement）
        val domInterfaceName = htmlElementTagNameMap[tagName] ?: return null

        // 2. 查找 lib.dom.d.ts 中对应的 DOM 接口（如 HTMLSpanElement）
        return findLibDomInterface(xmlTag.project, domInterfaceName)
    }

    fun findVueRuntimeCore(project: Project): JSFile? {
        //runtime-core.d.ts lib.dom.d.ts
        val virtualFiles = FilenameIndex.getVirtualFilesByName(
            //"runtime-dom.d.ts",
            "lib.dom.d.ts",
            GlobalSearchScope.allScope(project)
        )

        val domFile = virtualFiles.firstOrNull()?.let {
            PsiManager.getInstance(project).findFile(it) as? JSFile
        } ?: return null
        return domFile
    }

    // ---------------------- 工具方法：查找 DOM 接口（新增：标签名跳转用） ----------------------
    private fun findLibDomInterface(project: Project, domInterfaceName: String): TypeScriptInterface? {
        val domFile = findVueRuntimeCore(project) ?: return null;
        return PsiTreeUtil.collectElementsOfType(domFile, TypeScriptInterface::class.java)
            .firstOrNull { it.name == domInterfaceName }
    }

    /** 查找 lib.dom.d.ts 对应 DOM 属性 */
    private fun findLibDomElementForAttribute(project: Project, domType: String, normalizedName: String): PsiElement? {
        val domFile = findVueRuntimeCore(project) ?: return null;

        val tsInterface = findInterface(domFile, domType) ?: return null;

        // 查自己和继承的属性
        val target = findPropertyInInterface(tsInterface, normalizedName)
        if (target === null) {
            return null;
        }
        return target
    }

    fun findInterface(domFile: JSFile, domType: String): TypeScriptInterface? {
        val tsInterfaces = PsiTreeUtil.getChildrenOfTypeAsList(domFile, TypeScriptInterface::class.java)
        val tsInterface = tsInterfaces.firstOrNull { it.name == domType } ?: return null
        return tsInterface
    }

    private fun findPropertyInInterface(
        startInterface: TypeScriptInterface?,
        propName: String,
    ): PsiElement? {
        if (startInterface == null) return null

        val visited = mutableSetOf<TypeScriptInterface>()

        fun search(current: TypeScriptInterface?): PsiElement? {
            if (current == null || !visited.add(current)) return null  // 防循环

            // 1. 检查当前接口自身成员（属性或方法）
            current.members.forEach { member ->
                if (member.name == propName) {
                    return member
                    /* return when (member) {
                         is TypeScriptPropertySignature -> member.nameIdentifier ?: member
                         is TypeScriptFunctionSignature -> member.nameIdentifier ?: member
                         else -> member
                     }*/
                }
            }

            // 2. 遍历所有 extends 的引用（安全处理，只取 JSReferenceExpression）
            val extendsList = current.extendsList ?: return null
            for (child in extendsList.children) {

                val referenceExpr = when (child) {
                    is JSReferenceExpression -> child
                    is JSReferenceListMember -> child.expression  // 关键！取里面的表达式
                    else -> null
                } ?: continue
                if (referenceExpr is JSReferenceExpression) {
                    val resolved = referenceExpr.resolve() as? TypeScriptInterface ?: continue
                    search(resolved)?.let { return it }
                }
            }

            return null
        }

        return search(startInterface)
    }

}