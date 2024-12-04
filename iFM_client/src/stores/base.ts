import { defineStore } from 'pinia'

//创建小仓库
const useBaseStore = defineStore('Base', {
  state: () => {
    return {
      activeTab: 0 ,// 底部导航栏选中的索引 默认选中为1 首页
	  baseUrl: 'http://localhost:9090',//后端数据请求基础路径
    }
  },
  actions: {
    //设置active的值
    setActive(active: number) {
      this.activeTab = active
    }
  }
})
 
//暴露仓库
export default useBaseStore