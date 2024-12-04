import { defineStore } from 'pinia'

//创建小仓库
const usePlayStore = defineStore('Play', {
  state: () => {
    return {
		inPlay:false,
		pausePlayback:true,
		broadcastId:'',
		broadcastTitle:'',
		broadcastPicture:'/images/broadcast/D.png',
		broadcastAudio:'',
		broadcastDuration:0,
		currentDuration:0,
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
export default usePlayStore