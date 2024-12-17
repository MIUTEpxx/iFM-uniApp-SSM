<template>
	<view class="post-association">
		<head-general titleContent="关联帖子"></head-general>
		<view class="post-association-body">
			<view v-if="postAssociation==0" class="association-content">
				<uv-text text="关联频道" color="#86c7f9" size="20px" align="left"></uv-text>
				<channel-item  v-bind="association"></channel-item>
			</view>
			<view v-else-if="postAssociation==1" class="association-content">
				<uv-text text="关联节目" color="#86c7f9" size="20px" align="left"></uv-text>
				<broadcast-item v-bind="association"></broadcast-item>
			</view>
			
			<view class="association-post">
				<uv-text text="关联帖子" color="#86c7f9" size="20px" align="left"></uv-text>
				<view class="post-item-list">
					<post-item v-for="(item,i) in postList" :key="i" v-bind="item"></post-item>
					<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app';
import { ref } from 'vue';
import { getBroadcastDetail, getChannelDetail, getPostByAssociationId } from '@/request/api';
import useBaseStore from '@/stores/base';
	
	//关联内容类型 0:频道 1:节目
	const postAssociation = ref(-1)
	//关联内容的id
	const associationId = ref(-1)
	//关联内容数据
	const association = ref<any>()
	//关联帖子
	const postList = ref<any>([])
	onLoad((options:any)=>{
		postAssociation.value = JSON.parse(options.postAssociation); 
		associationId.value = JSON.parse(options.associationId); // 字符串转对象
		//获取关联内容
		if(postAssociation.value==0) {
			//获取频道信息
			getChannelDetail(associationId.value).then((res:any)=>{
				if(res.success==true){
					association.value=res.data.channel
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('关联频道数据请求失败', err); 
			});
		} else if(postAssociation.value==1) {
			//获取节目信息
			getBroadcastDetail(associationId.value).then((res:any)=>{
				if(res.success==true){
					association.value=res.data.broadcast
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('关联节目数据请求失败', err); 
			});
		}
		//获取关联帖子
		getPostByAssociationId(postAssociation.value,associationId.value).then((res:any)=>{
			if(res.success===true){
				postList.value=res.data.postList;
				postList.value = res.data.postList.map((post:any) => {
					// 修改每个帖子的图片列表
					post.postImageList = post.postImageList.map((postImage:any) => {
						return useBaseStore().baseUrl + postImage;
					});
					return post;
				});
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err:any) => { 
				console.error('关联帖子获取请求失败', err); 
		});
	})
	
</script>

<style scoped>
	.post-association-body {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.association-content {
		width: 95%;
		margin-bottom: 35rpx;
	}
	.association-content >:first-child {
		padding-bottom: 15rpx;
		padding-left: 10rpx;
	}
	.association-post {
		width: 100%;
	}
	.association-post > :first-child {
		padding-bottom: 15rpx;
		padding-left: 25rpx;
	}
	.association-post .post-item-list {
		padding-top: 25rpx;
		background: #dce8f9;
	}
</style>