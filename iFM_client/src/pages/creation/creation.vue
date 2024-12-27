<template>
	<view class="creation">
		<head-general titleContent="创作中心"></head-general>
		<view class="creation-body">
			<view class="show-content-tbs">
				<uv-tabs
				@click="showContentClick"
				:list="[{key:0,name:'我的频道'},{key:1,name:'我的帖子'}]"
				lineWidth="60" 
				lineColor="#63b1f9" 
				:activeStyle="{
					color: '#63b1f9',
					fontSize:'28px',
					transform: 'scale(1.05)',
				}"
				:inactiveStyle="{
					color: '#606266',
					fontSize:'28px',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:15px 20px; height: 35px;"
				></uv-tabs>		
			</view>
			<view class="creat-new" @click="goCreationNew">
				<view v-if="showContent==0" class="creat-title">
					<p>频</p>
					<p>道</p>
				</view>
				<view v-else class="creat-title">
					<p>帖</p>
					<p>子</p>
				</view>
				<view class="creat-body">
					<p>点击创建</p><span>+</span>
				</view>
			</view>
			<view class="creation-my">
				<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
					<p>选择删除</p>
				</view>
				<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
					<p>取消选择</p>
				</view>
				<uv-text v-if="showContent==0" text="已创建的频道" color="#93a2b5"  size="22px" align="right"></uv-text>
				<uv-text v-else text="已创建的帖子" color="#93a2b5"  size="22px" align="right"></uv-text>
			</view>
			<view class="creation-channel" v-if="showContent==0">
				<view class="channel-item-list" v-for="(item,i) in channelList" :key="i" >
					<channel-item  @click="goChannelDetailEdit(item)" class="item" v-bind="item" :showSubscribeButton="false" :enableComponentClick="false"></channel-item>
					<uv-icon  v-if="inDelete" @click="open(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
				</view>
				<uv-text text="没有更多频道了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
			<view class="creation-post" v-else>
				<view class="post-item-list" v-for="(item,i) in postList" :key="i" >
					<uv-icon  class="delete-button" v-if="inDelete" @click="open(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
					<post-item v-bind="item"></post-item>
				</view>
				<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
		</view>
		<popup-window
		ref="popup"
		popupTitleText="确认删除该频道"
		:popupWindowText="'频道: '+ deleteTitle"
		leftButtonText="取消删除"
		rightButtonText="确定删除"
		@leftButtonClick="cancelDelete"
		@rightButtonClick="confirmDelete">
		</popup-window>
		
		<player-bar></player-bar>
	</view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { deleteChannel, getChannelByUserId, getPostByUserId,deletePost } from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";
import useBaseStore from "@/stores/base";

const userStore =useUserStore()



//是否处于删除状态
let inDelete =ref(false)
//待删除的id
let deleteId=ref(0)
//待删除的索引
let deleteIndex=ref(0)
//待删除的频道标题
let deleteTitle=ref("")

//页面展示内容 0:创建的频道 1:创建的帖子
let showContent=ref(0)
//用户创建的频道
let channelList=ref<any>([])
//用户创建的帖子
let postList=ref<any>([])


//切换显示的内容
const showContentClick = (index:any) =>{
	showContent.value=index.key
}

//是否处于删除状态的切换
const inDeleteClick =() =>{
	inDelete.value=!inDelete.value
}

//弹窗索引
const popup = ref<any>(null);

//打开删除确认提醒弹窗
const open = (item:any,index:number) => {
	//console.log(item)
  if (popup.value) {
    popup.value.open();
	//更新待删除的Id和索引
	deleteIndex.value=index;
	if(showContent.value===0){
		deleteId.value=item.channelId;
		deleteTitle.value=channelList.value[index].channelTitle;
	}else{
		deleteId.value=item.postId;
		deleteTitle.value=postList.value[index].postTitle;
	}
  }
};

//取消删除
const cancelDelete =()=>{
	deleteId.value=0;
	deleteIndex.value=0;
	deleteTitle.value="";
	if (popup.value) {
	  popup.value.close();
	}
}	

//删除用户创作内容
const confirmDelete =() =>{
	if(showContent.value===0){
		//删除我的频道
		deleteChannel(userStore.userId,deleteId.value).then((res:any) => {
			//console.log("res.data",res.data)
			//频道删除成功
			if(res.success!=false){
				// 删除channelList中对应index的元素
				channelList.value.splice(deleteIndex.value, 1);
				//关闭窗口
				cancelDelete();
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 6000
				}) 
			}
		}).catch((err:any) => { 
		  console.error('频道删除请求失败', err); 
		}); 
	}
	else{
		//删除我的帖子
		deletePost(userStore.userId,deleteId.value).then((res:any) => {
			//console.log("res.data",res.data)
			//帖子删除成功
			if(res.success!=false){
				// 删除postList中对应index的元素
				postList.value.splice(deleteIndex.value, 1);
				//关闭窗口
				cancelDelete();
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 6000
				}) 
			}
		}).catch((err:any) => { 
		  console.error('帖子删除请求失败', err); 
		}); 
	}

}
onShow(() => {
	//获取用户创建的频道
	 getChannelByUserId(userStore.userId).then((res:any) => {
		channelList.value=res.data.channelList
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	}); 
	//获取用户创建的帖子
	getPostByUserId(userStore.userId).then((res:any) => {
		postList.value=res.data.postList
		postList.value = res.data.postList.map((post:any) => {
			// 修改每个帖子的图片列表
			post.postImageList = post.postImageList.map((postImage:any) => {
				return useBaseStore().baseUrl + postImage;
			});
			return post;
		});
		console.log("postList",postList.value)
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	}); 
});
	
const goCreationNew	=()=>{
	if(showContent.value==0){
			//频道创建页
		uni.navigateTo({
		  url: "/pages/creation/creation-channel",
		}); 
	} else {
		//帖子创建页
		uni.navigateTo({
		   url: '/pages/creation/creation-post?associationType='+-1+'&associationId='+-1,
		}); 
	}
 
}
//前往可编辑频道详情页
const goChannelDetailEdit=(item:any)=>{
	uni.navigateTo({
	  url: "/pages/channel/channel-detail-edit?channelId="+item.channelId,
	}); 
}
</script>

<style scoped>
	.creation-body {
		display: flex;
	    flex-direction: column;
	    align-items: center;
		margin-top: 20px;
	}
	.creation-body .creation-my {
		display:flex;
		padding: 20rpx;
		width: 90%;
	 }
	.creation-body .creat-new {
		display:flex;
		margin: 35rpx 0 25rpx 0;
		overflow: hidden;
		width: 90%;
		border: 1px solid #a9bee4;
		border-radius: 15rpx;
	}
	.creation-body .creat-new .creat-title {
		flex: 1;
		display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: space-between;	
		padding: 32rpx 60rpx;
		background: #baddfc;
		color:white;
		font-weight: 900;
		font-size: 55rpx;
	}
	.creation-body .creat-new .creat-body {
		flex: 6;
		display: flex;
	    align-items: center;
	    justify-content: space-evenly;
		background: #e8f5ff;
		font-weight: 900;
		font-size: 60rpx;	
		color:#6eb9ff;
	}
	.creation-body .creat-new .creat-body>span {
		font-size: 100rpx;	
		margin-bottom: 20rpx;
	}
	.un-delete-button {
		padding: 3px 5px;
		color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.in-delete-button {
		/* margin: 20px auto 15px 25px; */
		padding: 3px 5px;
		color: white;
		background-color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.creation-channel {
		width: 95%;
		padding-bottom: 125rpx;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.channel-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.channel-item-list>*{
		flex: 1;
	}
	.channel-item-list>.item{
		flex: 9;
	}
	.creation-post {
		width: 100%;
		min-height: 700rpx;
		padding-bottom: 125rpx;
		background: #dce8f9;
		padding-top: 25rpx;
		padding-bottom: 140rpx;
	}
	.post-item-list {
/* 		display: flex;
		flex-direction: column;
		align-items: flex-end; */
		position:relative;
	}
	.post-item-list .delete-button {
		position:absolute;
		right: 150rpx;
		top: 18rpx;
	}
</style>