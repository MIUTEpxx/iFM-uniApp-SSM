import { defineStore, storeToRefs } from 'pinia'
import { getPlayer } from '@/utils/initPlayer';
import { onUnmounted, ref, watch } from 'vue';
import useBaseStore from '@/stores/base'
import { getBroadcastAudio, increasePlayCount, updateHistory } from "@/request/api"; 
import useUserStore from './user';
// 定义频道数据结构
/* interface IBroadcastAudio {
   broadcastDuration:number,//广播节目音频时长
   broadcastId:number,//广播节目id
   broadcastTitle:string,//广播节目标题
   broadcastPicture:string,//广播节目封面
   broadcastAudio:string,//广播节目音频源
   authorName:string//作者用户昵称
   channelTitle:string,//频道标题
} */

//创建小仓库
export const usePlayerStore = defineStore('Player',{
  state: () => {
    return {
		loopType: 0, // 循环模式 0 列表循环 1 单曲循环 2随机播放
		isPlaying: false, // 是否播放中
		isPause: true, // 是否暂停
		sliderInput: false, // 是否正在拖动进度条
		ended: false, // 是否播放结束
		muted: false, // 是否静音
		broadcastDuration:0,//广播节目音频时长
		currentTime:0,//当前播放到的音频时长
		currentIndex:0,//当前播放的节目在播放列表中索引
		playerShow: false, // 控制播放栏显隐
		broadcastId:-1,//广播节目id
		broadcastTitle:'当前无广播音频播放',//广播节目标题
		broadcastPicture:'/images/broadcast/D.png',//广播节目封面
		broadcastAudio:'',//广播节目音频源
		authorName:'匿名',
		channelTitle:'',
		playList: [] as number[], // 播放列表(储存节目id)
		baseUrl:useBaseStore().baseUrl,
    }
  },
  getters: {
         
      },
  actions: {
	 // 播放列表里面添加音乐
		pushPlayList(replace: boolean, list: number[]) {
			let willPlay = <boolean>false;
			if(this.playList.length==0){
				//若播放列表为空,则直接播放加入列表的第一个节目
				willPlay = true;
			}
			if (replace) {
				//直接替换播放列表
				this.playList = list;
				return;
			}
			list.forEach((audio) => {    // 筛除重复节目
				if (!this.playList.includes(audio)) {
				  this.playList.push(audio);
				}
			})
			if(willPlay==true){
				//播放列表中的第一个节目
				this.play(this.playList[0]);
				willPlay = false;
			}
		},
		 // 删除播放列表中某节目
		deleteBroadcast(id: number) {
			let currenBroadcastId = this.playList[this.currentIndex]
			if( this.currentIndex>this.playList.indexOf(id) ) {
				//如果删除了播放列表中, 在当前播放的节目之前的节目, 则应修改当前播放节目的索引值
				this.currentIndex--;
			}	
		    this.playList = this.playList.filter((boadcastId: number) => boadcastId !== id);
			// console.log("currentIndex",this.currentIndex)
			// console.log("indexOf(id)",this.playList.indexOf(id))
			if(currenBroadcastId==id){
				//如果删除的是当前正在播放的节目, 则播放下一个节目
				this.currentIndex--;
				this.next();
			}
		},
		// 清空播放列表
		clearPlayList() {
			this.isPlaying= false; // 是否播放中
			this.isPause= false; // 是否暂停
			this.sliderInput= false; // 是否正在拖动进度条
			this.ended= false; // 是否播放结束
			this.muted= false; // 是否静音
			this.playerShow= false, // 控制播放栏显隐
			this.broadcastId=-1;//广播节目id
			this.broadcastTitle='当前无节目播放';//广播节目标题
			this.broadcastPicture='/images/broadcast/D.png',//广播节目封面
			this.broadcastAudio='当前无节目播放';//广播节目音频源
			this.playList = []; // 播放列表
			this.currentIndex=0;
			const audio = getPlayer();
		    audio.stop();
		    setTimeout(() => {
		        this.broadcastDuration=0;
				this.currentTime=0;
		    }, 500);
		},
		 //填入节目信息数据
		setBroadcastInfo(broadcast:any){
			this.broadcastDuration=broadcast.broadcastDuration
			this.broadcastId=broadcast.broadcastId
			this.broadcastTitle=broadcast.broadcastTitle
			this.broadcastPicture=broadcast.broadcastPicture
			this.broadcastAudio=broadcast.broadcastAudio
			this.authorName=broadcast.userName
			this.channelTitle=broadcast.channelTitle
			this.currentTime=broadcast.lastListenDuration
			//console.log("setBroadcastInfo",broadcast)
		},
		 // 播放节目(节目id)
		async play(broadcastId: number) {
			//如果待播放的节目是和当前播放的节目为同一个节目,则不执行播放
			/* if (broadcastId == this.broadcastId){
				uni.showToast({
					title: "当前正在播放该节目",
					duration: 2000
				}) 
				return;
			} */
			this.isPlaying = false;//是否播放中
			//获取节目数据
			const userStore = useUserStore()
			getBroadcastAudio(userStore.userId,broadcastId).then((res: any) => {
					if(res.success===true){
						this.setBroadcastInfo(res.data.broadcast)
						//增加节目播放量
						increasePlayCount(res.data.broadcast.broadcastId).then((res: any) => {
							if(res.success!=true){
								uni.showToast({
									title: res.message+'\n'+res.data.error,
									icon: 'error',
									duration: 3000
								})
							}
						}).catch((err: any) => {
							console.error('节目播放量增加请求失败', err);
							this.isPlaying = true;//是否播放中
							return
						});
					}else{
						uni.showToast({
							title: res.message+'\n'+res.data.error,
							icon: 'error',
							duration: 3000
						}) 
					}
				}).catch((err: any) => {
				console.error('节目数据请求失败', err);
				this.isPlaying = true;//是否播放中
				return
			  });
			//更新当前播放的节目在播放列表中的索引
			this.currentIndex= this.playList.indexOf(broadcastId);
			//若当前播放的节目不在播放列表,则添加
			if(this.currentIndex==-1){
				this.pushPlayList(false,[broadcastId]);
				//再次更新当前播放的节目在播放列表中的索引
				this.currentIndex= this.playList.indexOf(broadcastId);
			}
		    // console.log('play')
		   
		    
		    const audio = getPlayer();//获取全局播放器实例
		    this.broadcastId = broadcastId;
		    setTimeout(() => {
		        if(!!uni.getBackgroundAudioManager) {
		            // 微信小程序后台播放 数据显示
		            audio.title = this.broadcastTitle;   // 节目标题
		            audio.singer = this.authorName;    // 创作者昵称
		            audio.epname = this.channelTitle;   // 频道标题
		            audio.coverImgUrl = this.baseUrl+this.broadcastPicture;  //封面图片
		        }
				//console.log(this.baseUrl+this.broadcastAudio);
				//设置音源
				//console.log("audio",audio)
		        audio.src = this.baseUrl+this.broadcastAudio;
				
				//若有上次用户收听的历史时长记录
				if(this.broadcastDuration-this.currentTime<=15){
					this.currentTime=0;
				}
				else if(this.currentTime>0){
					// 设置音频从特定时间开始播放
					audio.seek(this.currentTime);
					console.log("从上回收听到的时长开始播放",this.currentTime)
					uni.showToast({
						title: "从上回收听到的时长开始播放",
						duration: 2000
					}) 
				}
				this.ended = false;//是否播放结束:false
				this.isPause = false;//是否暂停:false
				this.isPlaying = true;//是否播放中:true
				//播放音频
				audio.play();
		        audio.onError((err:any) => {
		            this.broadcastId = broadcastId;
		            uni.showToast({
		                icon: "error",
		                title: "该节目无法播放"
		            })
					this.isPause = true;
		        })
		    }, 500)
		},
		// 一个节目播放结束
		playEnd() {
		    this.isPause = true;
		    console.log('节目播放结束');
		    switch (this.loopType) {
		        case 0://列表顺序播放
		            this.next();
		            break;
		        case 1://单个节目循环播放
		            this.rePlay();
		            break;
		        case 2://随机播放
		            this.randomPlay();
		            break;
		    }
		},
		// 重新播放
		 rePlay() {
		     setTimeout(() => {
		         console.log('replay');
		         this.currentTime = 0;
		         this.ended = false;
		         this.isPause = false;
		         this.isPlaying = true;
		         const audio = getPlayer();
		         audio.seek(0);
		         audio.play();
		     }, 1500)
		 },
        // 下一曲
        next() {
			if(this.playList.length==1&&this.currentIndex==0){return}
            if (this.loopType === 2) {
				//随机播放
                this.randomPlay();
            } else {
				if(this.playList.length==0) {
					//播放列表为空
					//清空当前播放节目的信息
					this.clearPlayList();
				}
                else if(this.currentIndex+1 >= this.playList.length) {
					//现在已经是播放列表中最后一个节目了
					this.currentIndex=0;
					this.play(this.playList[0]);
                }else{
					//播放下一个节目
                    this.play(this.playList[this.currentIndex+1]);
                }
            }
        },
        // 上一曲
        prev() {
			if(this.playList.length==1&&this.currentIndex==0){return}
            if(this.currentIndex <= 0) {
               /* uni.showToast({
                    icon: "none",
                    title: "没有上一首"
                }) */
				uni.showToast({
				     icon: "none",
				     title: "播放列表最后一个节目"
				 })
				this.currentIndex=0;
				this.play(this.playList[this.playList.length-1]);
            }else{
                this.play(this.playList[this.currentIndex-1]);
            }
        },
		//随机播放 播放列表中的节目
		 randomPlay() {
		    console.log('randomPlay')
		    // 确保播放列表不为空
		     if (this.playList.length > 0) {
		       // 生成一个随机索引
		       const randomIndex = Math.floor(Math.random() * this.playList.length);
		       // 获取随机选中的节目 ID
		       const randomSongId = this.playList[randomIndex];
		       // 播放选中的节目
		       this.play(randomSongId);
		     } else {
		       uni.showToast({
		           icon: "none",
		           title: "播放列表为空"
		       }) 
		     }
		},
        // 播放、暂停
        togglePlay() {
			console.log("togglePlay!")
            if (!this.isPlaying) {
				uni.showToast({
				    icon: "none",
				    title: "当前无节目被播放"
				}) 
				return;
			}
            const audio = getPlayer();
            if (!this.isPause) {
                audio.pause();
                this.isPause = true;
            } else {
                audio.play();
                this.isPause = false;
            }
        },
        setPlay() {
			console.log("setPlay!")
            if (this.isPlaying) {return;}
            const audio = getPlayer();
            this.isPlaying = true;
            audio.play();
            this.isPause = false;
        },
        setPause() {
            if (!this.isPlaying) {return;}
            const audio = getPlayer();
            this.isPlaying = false;
            audio.pause();
            this.isPause = true;
        },
		// 从val秒开始播放
		playAudioInval(val: number) {
		    const audio = getPlayer();
		    audio.seek(val);
		},
        // 快进
        forward(val: number) {
            const audio = getPlayer();
            audio.seek(this.currentTime + val);
        },
        // 后退
        backup(val: number) {
            const audio = getPlayer();
            if(this.currentTime < 5) {
                audio.seek(0)
            }else{
                audio.seek(this.currentTime - val);
            }
        },
        // 修改播放时间
        onSliderChange(val: number) {
            const audio = getPlayer();
			this.currentTime=val
            audio.seek(val);
        },
        // 定时器1触发
        interval() {
			//当播放器处于播放状态且非正在被拖动进度条且并非处于暂停状态
            if (this.isPlaying && !this.sliderInput &&!this.isPause) {
                const audio = getPlayer();//获取全局播放器实例(innerAudioContext)
                this.currentTime = parseInt(audio.currentTime.toString());// 更新当前播放时间
                //console.log("更新当前播放时间",this.currentTime)
				//this.duration = parseInt(audio.duration.toString());// 更新音频总时长
                audio.onEnded(() => {
                    // console.log('end')
                    this.ended = true // 当音频播放结束时，设置 ended 为 true
                })
            }
        },	
		//定时器2触发
		interval2() {
			//console.log("收听历史!")
			//若用户未登录,则不记录收听历史
			if(!useUserStore().isLogin){return}
			//当播放器处于播放状态且非正在被拖动进度条且并非处于暂停状态并且播放未结束
		    if (this.isPlaying && !this.sliderInput &&!this.isPause&&!this.ended) {
				const userStore = useUserStore()
		       updateHistory(userStore.userId,this.broadcastId,this.currentTime).then((res:any)=>{
				   if(res.success!=true){
				   		uni.showToast({
				   			title: '1111'+res.message+'\n'+res.data.error,
				   			icon: 'error',
				   			duration: 3000
				   		}) 
				   	}
				  }).catch((err: any) => {
				console.error('保存收听历史记录请求失败', err);
				return
				});
		    }
		},	
  }
})

//暴露仓库
export const useInitPlayer = () => {
    let timer: any;
	let timer2:any;
    const { interval, interval2, playEnd } = usePlayerStore();
    const { ended } = storeToRefs(usePlayerStore());
    
    // 监听播放结束
    watch(ended, (ended) => {
        console.log('start')
        if (!ended) return
        console.log('end')
        playEnd()
    }),
    
    // 启动定时器
    console.log('启动定时器');
    //用于更新当前播放到的时长并观察是否播放结束
	timer = setInterval(interval, 1000);
	//用于保存用户收听历史记录 15s一次
    timer2 = setInterval(interval2, 15000);
    // 清除定时器
    onUnmounted(() => {
        console.log('清除定时器');
        clearInterval(timer);
    })
}
 
 

