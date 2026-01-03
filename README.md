# Vue DOM 属性跳转插件文档

## 🎯 功能概述

**Vue DOM 属性跳转插件** 为 Vue 单文件组件（`.vue`）的 `<template>` 中的 HTML 属性和标签名提供 **Ctrl+点击跳转到 lib.dom.d.ts 定义** 的功能。

### 支持的功能

| 功能 | 示例 | 跳转目标 |
|------|------|----------|
| **普通属性** | `<div title="xxx">` | `HTMLElement.title: string` |
| **绑定属性** | `<div :title="xxx">` | `HTMLElement.title: string` |
| **class 特殊映射** | `<div class="">`<br>`<div :class="">` | `Element.className: string` |
| **事件绑定** | `<div @click="">` | `GlobalEventHandlers.onclick` |
| **标签跳转** | Ctrl+点击 `<div>` 标签名 | `HTMLDivElement` 接口定义 |
| **深度继承链** | 支持完整 DOM 继承链（`HTMLDivElement` → `HTMLElement` → `Element` → `GlobalEventHandlers` 等） |

## ✨ 特性亮点

✅ **精准跳转**：直接跳转到 lib.dom.d.ts 中属性定义行，显示完整类型签名  
✅ **Vue 语法全支持**：`:`（v-bind）、`@`（v-on）、`v-bind:`、`v-on:`、修饰符 `.stop` 等自动规范化