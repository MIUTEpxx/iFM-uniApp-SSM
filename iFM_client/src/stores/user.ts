import { defineStore } from 'pinia'
 
//创建小仓库
const useUserStore = defineStore('User', {
  state: () => {
    return {
      activeTab: 0 // 底部导航栏选中的索引 默认选中为1 首页
    }
  },
  actions: {
    //设置active的值
    setActive(active: number) {
      this.activeTab = active
    }
  }
})
 
//暴露小仓库
export default useUserStore