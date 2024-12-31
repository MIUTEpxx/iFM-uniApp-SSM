<template>
	<view class="my-history">
		<head-general titleContent="收听历史"></head-general>
		<view class="my-history-body">
			<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
				<p>选择删除</p>
			</view>
			<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
				<p>取消选择</p>
			</view>
			<view class="history-broadcast">
				<scroll-view scroll-y class="broadcast-scroll">
					<view class="broadcast-item-list" v-for="(item,i) in broadcastList" :key="i" >
						<broadcast-item  class="item" v-bind="item"></broadcast-item>
						<uv-icon  v-if="inDelete" @click="deleteBroacast(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
					</view>
				</scroll-view>
			</view>
		</view>
		<player-bar></player-bar>
	</view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { getHistory, deleteHistory} from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";

//是否处于删除状态
let inDelete =ref(false)
const userStore =useUserStore()
//页面展示内容 0:收藏的节目 1:收藏的帖子
let showContent=ref(0)
//收藏的节目
let broadcastList=ref([])
//切换显示的内容
const showContentClick = (index:any) =>{
	showContent.value=index.key
}
//
const inDeleteClick =() =>{
	inDelete.value=!inDelete.value
}
const deleteBroacast =(item:any,index:number) =>{
	
	deleteHistory(userStore.userId,item.broadcastId).then((res:any) => {
		//删除历史记录成功
		if(res.success!=false){
			// 删除broadcastList中对应index的元素
			broadcastList.value.splice(index, 1);
		}else{
			uni.showToast({
				title: res.message+'\n'+res.data.error,
				icon: 'error',
				duration: 6000
			}) 
		}
	}).catch((err:any) => { 
	  console.error('取消收藏失败', err); 
	}); 
}
onShow(() => {
	//获取用户收藏的节目
	 getHistory(userStore.userId).then((res:any) => {
		broadcastList.value=res.data.broadcastList
		console.log("broadcastList",res.data)
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	});
});
	
</script>

<style>
	.my-history-body {
		display: flex;
	    flex-direction: column;
	    align-items: center;
		margin-top: 20px;
	}
	.un-delete-button {
		margin: 20px auto 15px 25px;
		padding: 3px 5px;
		color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.in-delete-button {
		margin: 20px auto 15px 25px;
		padding: 3px 5px;
		color: white;
		background-color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.history-broadcast {
		width: 90%;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.history-broadcast .broadcast-scroll {
		height: 100%;
	}
	.broadcast-scroll .broadcast-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.broadcast-item-list>*{
		flex: 1;
	}
	.broadcast-item-list>.item{
		flex: 6;
	}

</style>