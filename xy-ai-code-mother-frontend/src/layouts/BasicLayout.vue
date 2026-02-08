<script setup lang="ts">
import { RouterView } from 'vue-router'
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>

<template>
  <a-layout class="basic-layout">
    <a-layout-header class="layout-header">
      <GlobalHeader />
    </a-layout-header>
    <a-layout-content class="layout-content">
      <div class="content-inner">
        <RouterView />
      </div>
    </a-layout-content>
    <a-layout-footer class="layout-footer">
      <GlobalFooter />
    </a-layout-footer>
  </a-layout>
</template>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.layout-header {
  height: 64px;
  padding: 0;
  line-height: 64px;
  flex-shrink: 0;
  background: #e8f5e9 !important;
}

.layout-content {
  flex: 1;
  padding: 24px;
  min-height: 0;
}

.content-inner {
  max-width: 1280px;
  margin: 0 auto;
  min-height: 100%;
}

.layout-footer {
  flex-shrink: 0;
  padding: 0;
  margin-top: auto;
}
</style>
