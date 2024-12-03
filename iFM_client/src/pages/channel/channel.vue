<template>
	<view class="channel-detail">
		<head-general titleContent="频道详情" ></head-general>
		<view class="channel-detail-head">
			<view class="channel-info">
				<uv-text :lines="3" :text="channelDetail.channelTitle" color="#1d2b36" size="24px" bold="true"></uv-text>
				<view class="other-info">
					<uv-avatar :src="base_url+channelDetail.userPicture" size="65px"></uv-avatar>
					<view class="more-info">
						<uv-text :lines="2" :text="'作者: '+channelDetail.userName" color="#5d5d5d"  size="18px"></uv-text>
						<uv-text :lines="2" :text="'发布: '+channelDetail.channelCreateTime" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
						<uv-text :lines="2" :text="'更新: '+channelDetail.channelUpdateTime" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
					</view>
				</view>
			</view>
			
			<view class="channel-image">
				<uv-image
				:src= "base_url+channelDetail.channelPicture"
				:lazy-load="true"
				mode="aspectFill"
				width="100%" 
				height="100%"
				customStyle=" position: absolute; top: 0px; right: 0; bottom: 0; left: 0;" >
				</uv-image>
			</view>
		</view>
		
		<view class="channel-detail-body">
			<view class="channel-subscribe">
				<view class="subscribe-number">
					<uv-text :text="numConversion(subscribeCount)" color="#1d2b36" size="22px" bold="true" customStyle="margin-left:5px; width:100px;"></uv-text>
					<uv-text text="订阅" color="#4f7694" size="16px" customStyle="margin-left:5px; width:50px"></uv-text>
				</view>
				<view class="button">
					<uv-button
					@click="subscribeClick"
					v-if="!hasSubscribe"
					customStyle=" width: 110px; height:auto; padding:10px;fontSize:18px; border-radius: 10px;" 
					color="#61b7f9">
					 +订阅频道
					</uv-button>
					
					<uv-button
					@click="subscribeClick"
					v-if="hasSubscribe"
					:plain="true"
					customStyle=" width: 110px; height:auto; padding:10px 5px;fontSize:18px;border-radius: 10px;" 
					color="#61b7f9">
					 X 取消订阅
					</uv-button>
					
					<view class="post">
						<uv-icon name="chat" color="#86c7f9" size="40px"></uv-icon>
						<p>{{numConversion(postCount)}} </p>
					</view>
				</view>
			</view>
			<view class="channel-introduction">
					<uv-text text="频道简介" color="#61b7f9" size="20px" bold="true" customStyle="margin:0 0 10px 10px;"></uv-text>
				<uv-read-more show-height="100px" :toggle="true" color="#61b7f9" shadowStyle="backgroundImage:none;">
					<uv-text text="丙辰年的中秋节，高兴地喝酒直到第二天早晨，喝到大醉，写了这首词，同时思念弟弟苏辙。明月从什么时候才开始出现的？我端起酒杯遥问苍天。不知道在天上的宫殿，何年何月。我想要乘御清风回到天上，又恐怕在美玉砌成的楼宇，受不住高耸九天的寒冷。翩翩起舞玩赏着月下清影，哪像是在人间。月儿转过朱红色的楼阁，低低地挂在雕花的窗户上，照着没有睡意的自己。明月不该对人们有什么怨恨吧，为什么偏在人们离别时才圆呢？人有悲欢离合的变迁，月有阴晴圆缺的转换，这种事自古来难以周全。只希望这世上所有人的亲人能平安健康，即便相隔千里，也能共享这美好的月光" color="#203d53" size="18px" customStyle="margin:0 5px 5px 10px;"></uv-text>
				</uv-read-more>
			</view>
			<view class="broadcast-sort">
				<uv-text :lines="3" text="单集节目" color="#61b7f9" size="24px"></uv-text>
				<uv-tabs 
				:list="list"
				lineWidth="45" 
				lineColor="#61b7f9" 
				:activeStyle="{
					color: '#303133',
					fontWeight: 'bold',
					transform: 'scale(1.05)'
				}"
				:inactiveStyle="{
					color: '#606266',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:0 5px 5px 5px; height: 34px;"
				></uv-tabs>		
			</view>
			
			<scroll-view scroll-y class="broadcast-scroll">
				<broadcast-item  v-for="(item,i) in broadcastList" :key="i" v-bind="item"></broadcast-item>
				<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
			</scroll-view>
		</view>
		
	</view>
</template>

<script setup lang="ts">
	import { onLoad } from "@dcloudio/uni-app";
	import { getBroadcastByChannel,getChannelDetail,checkSubscribe, changeSubscribe } from "@/request/api"; 
	import { ref,watch } from 'vue';
	import { changeTime } from "@/utils/timeChange"
	import { numConversion} from '@/utils/numConversion';
	import useBaseStores from '@/stores/base';
	import useUserStores from '@/stores/user';
	import { logOut } from "@/utils/logOut";
	
	//后端请求路径
	const base_url = useBaseStores().baseUrl;
	//获取用户信息
	const userStore = useUserStores();
	//排序方法
	const list=[{name:'最新发布'},{name:'最早发布'},{name:'最多收藏'}];
	let channelId = ref(0);
	//储存频道详情信息
	let channelDetail = ref<any>([]);
	//储存节目项数据的数组
	let broadcastList= ref([]);
	//用户是否已订阅该频道
	let hasSubscribe= ref(false);
	//频道订阅数
	let subscribeCount= ref(0)
	//频道关联帖子数
	let postCount= ref(0)
	
	const subscribeClick =()=>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		hasSubscribe.value=!hasSubscribe.value
		if(hasSubscribe.value===true){subscribeCount.value++}
		else {subscribeCount.value--}
		//用户订阅/取消订阅
		changeSubscribe(userStore.userId,channelId.value).then((res: any) => {
			if(res.code==20005){
				//安全令牌失效,重新登录
				logOut()
				return
			}
			//更新Token安全令牌
			userStore.setToken(res.data.token)
			if(res.success===false){
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err: any) => {
			console.error('频道订阅请求失败', err);
		});
		
	}
	
	 onLoad((options:any) => {
		channelId.value = JSON.parse(options.channelId); // 字符串转对象
		//获取频道详情信息
		getChannelDetail (channelId.value).then((res:any) => {
			channelDetail.value=res.data.channel
			//console.log("channelDetail.value",channelDetail.value)
			subscribeCount.value=channelDetail.value.channelSubscribe
			postCount.value=channelDetail.value.channelPostCount
			//转化时间格式
			channelDetail.value.channelCreateTime=changeTime(channelDetail.value.channelCreateTime);
			channelDetail.value.channelUpdateTime=changeTime(channelDetail.value.channelUpdateTime);
		}).catch((err:any) => { 
		  console.error('频道数据请求失败', err); 
		});
		
		//获取频道下的节目数据
		getBroadcastByChannel (channelId.value).then((res:any) => {
			broadcastList.value=res.data.broadcastList
		}).catch((err:any) => { 
		  console.error('节目数据请求失败', err); 
		});
		
		if(userStore.isLogin===true){
			//若用户处于登录状态,检查用户是否已订阅该频道
			checkSubscribe (userStore.userId,channelId.value).then((res:any) => {
				 console.log("res",res)
				if(res.success===true){
					hasSubscribe.value=res.data.subscribe
					console.log("hasSubscribe",hasSubscribe.value)
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('订阅数据请求失败', err); 
			});
		}
	  });
	
</script>

<style>
	.channel-detail-head {
		display: flex;
		align-items: center;
		padding: 0 20px 0 20px;
		height: 150px;
		
		.channel-info {
			flex:0 0 65%;
			
			.other-info {				
				display: flex;
				justify-content: flex-start;
				margin-top: 15px;
				
				.more-info {
					padding:0 15px 5px 15px;
				}
			}
		}
		.channel-image {
			position: relative;
			flex:0 0 35%;
			height: 0;
			padding: 0;
			padding-bottom: 35%; 
		}
		
	}
	
	.channel-detail-body {
		margin-top:20px;
		margin: 0 10px 0 10px;
		
		.channel-subscribe {
			display: flex;
	        justify-content: space-between;
	        align-items: center;		
			
			margin: 20px 10px 20px 10px;
			
			.subscribe-number{
				display: flex;
				align-items: center;
		
			}
			.button {
				display: flex;
	            align-items: center;
	            justify-content: space-around;	
						
				.post {
					flex: 0 0 30%;
					display: flex;
	                flex-direction: column;
	                align-items: center;
					margin-left: 10px;
					color: #407aa7;
					
					p{
						font-size: 16px;
					}
					
				}
			}
		}
		
		.channel-introduction {
			margin: 20px 0 20px 0;
			padding: 10px;
			min-height: 100px;
			box-shadow: 2px 2px 5px 2px #aebfd6;
			border-radius: 10px;
		}
		
		.broadcast-sort {
			display: flex;
		}
		.broadcast-scroll {
			height: 375px;
			margin-top: 10px;
			overflow: hidden;
			border-top: 3px dashed #9dcaf9;
		}
	}
	
	
</style>