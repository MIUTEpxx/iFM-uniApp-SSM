<template>
	<view class="my-favorite">
		<head-general titleContent="播放列表"></head-general>
		<view class="my-favorite-body">
			<view class="show-content-tbs">
			</view>
			<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
				<p>选择删除</p>
			</view>
			<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
				<p>取消选择</p>
			</view>
			<view class="favorite-broadcast">
				<scroll-view scroll-y class="broadcast-scroll">
					<view class="broadcast-item-list" v-for="(item,i) in broadcastList" :key="i" >
						<broadcast-item class="item" v-bind="item" :showPlayButton="false"></broadcast-item>
						<view class="current-play"  v-if="playerStore.playList[playerStore.currentIndex]==item.broadcastId&&!inDelete">
							<uv-text   text="当前正在播放" lineHeight="60rpx" color="#86c7f9"  size="28rpx" ></uv-text>
							<uv-icon   name="play-right-fill" color="#89ccff" size="40rpx"></uv-icon>
						</view>
						<view class="current-play-inDelete"  v-if="playerStore.playList[playerStore.currentIndex]==item.broadcastId&&inDelete">
							<uv-text   text="当前正在播放" lineHeight="60rpx" color="#86c7f9"  size="28rpx" ></uv-text>
							<uv-icon   name="play-right-fill" color="#89ccff" size="40rpx"></uv-icon>
						</view>
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
import { getBroadcastByIdList} from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";
import { usePlayerStore } from "@/stores/player";

const userStore = useUserStore()
const playerStore = usePlayerStore()

//是否处于删除状态
let inDelete =ref(false)

//播放列表中的节目
let broadcastList=ref<any>([])

const inDeleteClick =() =>{
	inDelete.value=!inDelete.value
}
const deleteBroacast =(item:any,index:number) =>{
	// console.log("item",item)
	playerStore.deleteBroadcast(item.broadcastId)
	// 删除broadcastList中对应index的元素
	broadcastList.value.splice(index, 1);
	/* console.log("broadcastList",broadcastList.value) */
}
onShow(() => {
	//获取用户播放列表中的节目信息
	 getBroadcastByIdList(playerStore.playList).then((res:any) => {
		broadcastList.value=res.data.broadcastList
		//console.log("broadcastList",res.data)
	}).catch((err:any) => { 
	  console.error('节目数据请求失败', err); 
	});
});
	
</script>

<style>
	.my-favorite-body {
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
	.favorite-broadcast {
		width: 90%;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.favorite-broadcast .broadcast-scroll {
		height: 100%;
	}
	.broadcast-scroll .broadcast-item-list {
		position: relative;
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.broadcast-item-list>*{
		flex: 1;
	}
	.broadcast-item-list>.item{
		flex: 9;
	}
	.broadcast-item-list .current-play {
		position: absolute;
		top:5rpx ;
		right: 10rpx;
		display: flex;
		align-items: center;
	}
	.broadcast-item-list .current-play-inDelete {
		position: absolute;
		top:5rpx ;
		right: 80rpx;
		display: flex;
		align-items: center;
	}
</style>