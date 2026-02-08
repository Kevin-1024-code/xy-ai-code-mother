<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { computed } from 'vue'

export interface MenuItem {
  key: string
  path: string
  label: string
}

const props = withDefaults(
  defineProps<{
    title?: string
    menuItems?: MenuItem[]
  }>(),
  {
    title: 'AI零代码应用生成',
    menuItems: () => [
      { key: 'home', path: '/', label: '首页' },
      { key: 'about', path: '/about', label: '关于' },
    ],
  }
)

const router = useRouter()
const route = useRoute()

const selectedKeys = computed(() => {
  const path = route.path
  const item = props.menuItems.find((m) => m.path === path || (m.path !== '/' && path.startsWith(m.path)))
  return item ? [item.key] : []
})

function handleMenuClick({ key }: { key: string }) {
  const item = props.menuItems.find((m) => m.key === key)
  if (item) router.push(item.path)
}

/** 使用运行时路径，避免 Vite 把 /logo.png 当作模块解析；可将 logo.png 放到 public 目录 */
const logoSrc = '/logo.png'

function onLogoError(e: Event) {
  const el = e.target as HTMLImageElement
  if (el) el.src = '/favicon.ico'
}
</script>

<template>
  <div class="global-header">
    <div class="header-left">
      <router-link to="/" class="logo-wrap">
        <img :src="logoSrc" alt="Logo" class="logo-img" @error="onLogoError" />
        <span class="site-title">{{ title }}</span>
      </router-link>
      <a-menu
        :selected-keys="selectedKeys"
        mode="horizontal"
        :items="menuItems.map((m) => ({ key: m.key, label: m.label }))"
        class="header-menu"
        @click="handleMenuClick"
      />
    </div>
    <div class="header-right">
      <a-button type="primary">登录</a-button>
    </div>
  </div>
</template>

<style scoped>
.global-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 24px;
  color: #1b5e20;
}

.header-left {
  display: flex;
  align-items: center;
  flex: 1;
  min-width: 0;
}

.logo-wrap {
  display: flex;
  align-items: center;
  margin-right: 24px;
  text-decoration: none;
  color: inherit;
  flex-shrink: 0;
}

.logo-img {
  width: 32px;
  height: 32px;
  object-fit: contain;
  margin-right: 8px;
}

.site-title {
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
}

.header-menu {
  flex: 1;
  min-width: 0;
  line-height: 64px;
  border-bottom: none;
  background: transparent !important;
  color: #1b5e20;
}

.header-menu :deep(.ant-menu-item) {
  min-width: 64px;
  color: inherit;
}

.header-menu :deep(.ant-menu-item:hover),
.header-menu :deep(.ant-menu-item-selected) {
  color: #2e7d32 !important;
}

.header-right {
  flex-shrink: 0;
}

@media (max-width: 768px) {
  .global-header {
    padding: 0 16px;
  }

  .site-title {
    font-size: 16px;
  }

  .header-menu {
    display: none;
  }
}
</style>
