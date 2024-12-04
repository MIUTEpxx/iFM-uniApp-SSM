<template>

  <view class="tabbar">
    <uv-tabbar 
	:active="useStore.activeTab" 
	:fixed="true" 
	:safeAreaInsetBottom="true"
	customStyle="height:70px"
	>
	  <uv-tabbar-item
	  v-for="(item, index) in tabbarItems" 
	  :key="index" 
	  :icon="getTabbarIcon(item, index)" 
	  :iconSize=50
	  :text="item.text" 
	  customStyle="margin-top:15px;"
	  @click="handleTabbarItemClick(item, index)">
	  </uv-tabbar-item>
    </uv-tabbar>
  </view>
</template>
 
<script setup lang="ts">
//引入pinia仓库
import useBaseStore from '@/stores/base'
let useStore = useBaseStore()
 
const tabbarItems = [
  {
    pagePath: '/pages/index/index',
    text: '首页',
    iconPath: '/static/tab_icons/home.png',
    selectedIconPath: '/static/tab_icons/home-active.png'
  },
  {
    pagePath: '/pages/community/community',
    text: '社区',
    iconPath: '/static/tab_icons/community.png',
    selectedIconPath: '/static/tab_icons/community-active.png'
  },
  {
    pagePath: '/pages/my/my',
    text: '个人',
    iconPath: '/static/tab_icons/my.png',
    selectedIconPath: '/static/tab_icons/my-active.png'
  }
]
//点击tabbar按钮
const handleTabbarItemClick = (item: { pagePath: any; }, index: number) => {
  if (useStore.activeTab !== index) {
      useStore.setActive(index)
      const path = item.pagePath
      uni.switchTab({
        url: path
      })
  }
}
//图标的切换
const getTabbarIcon = (item: { selectedIconPath: any; iconPath: any; }, index: number) => {
  return useStore.activeTab === index ? item.selectedIconPath : item.iconPath
}
</script>


<style>
</style>

