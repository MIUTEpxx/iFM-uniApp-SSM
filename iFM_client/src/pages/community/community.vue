<template>
	<view class="community">
		<head-general :showBackButton="false" :showHomeButton="false" titleContent="社区中心"></head-general>
		<search-box></search-box>
		<uv-sticky class="community-head" bgColor="white" customStyle="top:0;">
			<view class="community-head-sort">
				<uv-text text="帖子板块" color="#86c7f9"  size="20px"></uv-text>
				<view class="sort-method">
					<uv-icon name="paixu" custom-prefix="custom-icon" color="#647182" size="26px"></uv-icon>
					<uv-drop-down
						ref="dropDown"
						sign="dropDown_1" 
						:defaultValue="[0,'0','all']"  
						customStyle="border-bottom:none;" 
					>
						<uv-drop-down-item 
							name="order" 
							type="2" 
							:label="sortMethodList[currentSortMethod]" 
							value="all">
						</uv-drop-down-item>
					</uv-drop-down>
					<uv-drop-down-popup sign="dropDown_1">
						<view class="drop-content">
							<sort-method-box 
							customStyle="margin-bottom: 15rpx;height:40px;font-size:60rpx;" 
							:methodList="sortMethodList" 
							:current="currentSortMethod" 
							@change="sortMethodClick"></sort-method-box>
							<view class="post-hashtag">
								<uv-text :text="'帖子主题标签 当前已选择 '+checkboxValue.length+' 个标签'" color="#767f8b" size="26rpx" align="left"></uv-text>
								<uv-checkbox-group class="post-hashtag-checkbox" v-model="checkboxValue" placement="row" iconPlacement="left">
									<uv-checkbox v-for="(item,i) in hashtagList" :key="i" 
									:name="item.hashtagId" :label="item.hashtagName" 
									size="40rpx" customStyle="minWidth: 148rpx;"
									></uv-checkbox>				
								</uv-checkbox-group>
							</view>
						</view>
					</uv-drop-down-popup>		
				</view>
			</view>
			<tags-tabs customStyle="margin:0 10px 10px 10px;" @click="sectionTabClick" :hashtag="sectionList"></tags-tabs>
		</uv-sticky>
		<view class="community-body">
			<post-item 
			v-for="(item,i) in currentPostList" :key="i"
			v-bind="item">
			</post-item>
			<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
		</view>
		<player-bar :reserveSpace="true"></player-bar>
		<tabbar></tabbar>
	</view>
</template>

<script setup lang="ts">
	import { onLoad, onShow } from '@dcloudio/uni-app';
	import { ref, watch } from 'vue';
	import { getHashtag, getPopularPost, getPostBySectionId } from '@/request/api';
	import { sortpostList } from '@/utils/postSort';
	import useBaseStore from '@/stores/base';

	//排序方法列表
	let sortMethodList =ref(["综合排序","最新发布","最早发布","最多收藏"])
	//当前选择的排序方法
	let currentSortMethod=ref(0)
	//帖子主题标签
	const hashtagList = ref<any>([])
	//选中的帖子主题标签
	let checkboxValue = ref([])
	
	//帖子列表
	let postList=ref<any>([])
	//当前显示的帖子的列表
	let currentPostList=ref<any>([])
	
	
	//帖子板块分类列表
	const sectionList=[{hashtagId:0,hashtagName:"综合热门"},{hashtagId:1,hashtagName:"话题讨论"},{hashtagId:2,hashtagName:"分享推荐"},{hashtagId:3,hashtagName:"求助问答"}]
	//当前选择的社区板块 0:话题讨论 1:分享推荐 2:求助问答
	let currentSection = ref(0)
	
	onLoad(async()=> {
		//隐藏pages.json里配置的导航栏，使用封装的tabbar组件
			uni.hideTabBar({
				animation:false
		})
	
		//获取主题标签数据
		getHashtag ().then((res:any) => {
			hashtagList.value=res.data.hashtagList
		}).catch((err:any) => { 
		  console.error('主题标签数据请求失败', err); 
		});
		
/* 		await getPost();
		currentPostList.value = sortpostList(postList.value,currentSortMethod.value); */
		//获取当前应显示的帖子数据
		getCurrentPostList(true);
	})

	//监听主题标签选择器组件返回的数据(即当前选择的标签数据)
	const sectionTabClick = (hashtag:any) => {
		currentSection.value=hashtag.key;
	};	
	//监听排序方式选择组件返回的数据(即当前选择的是第几个排序方式)
	const sortMethodClick = (sortMethod: number) => {
		currentSortMethod.value=sortMethod;
	}
	
	// 监听currentSection的变化
	watch(currentSection, async (newValue, oldValue) => {
		//console.log(newValue, oldValue)
/* 		//根据板块获取帖子
		await getPost();
		//排序
		currentPostList.value=sortpostList(postList.value,currentSortMethod.value); */
		//获取当前应显示的帖子数据
		getCurrentPostList(true);
	});
	
	// 监听currentSortMethod的变化
	watch(currentSortMethod, (newValue, oldValue) => {
		//排序帖子
		//console.log(newValue, oldValue)
		//currentPostList.value=sortpostList(postList.value,currentSortMethod.value);
		//获取当前应显示的帖子数据
		getCurrentPostList(false);
	});
	// 监听checkboxValue的变换
	watch(checkboxValue, (newValue, oldValue) => {
		/* console.log("checkboxValue",checkboxValue.value);
		console.log("postList",postList.value) */
/* 		if(checkboxValue.value.length==0){
			currentPostList.value =sortpostList(postList.value,currentSortMethod.value);
		}else{
			currentPostList.value = filterPostsByHashtags(postList.value,checkboxValue.value)
		} */
		//获取当前应显示的帖子数据
		getCurrentPostList(false);
	});
	
	//整理帖子,获取当前应显示的帖子
	const getCurrentPostList =  async (isSectionChange:boolean) =>{
		if(isSectionChange==true) {
			//如果当前社区板块有所变化
			//获取新的帖子数据
			await getPost();
			currentPostList.value = postList.value
		}
		if(checkboxValue.value.length!=0) {
			//如果有主题标签筛选
			currentPostList.value = await filterPostsByHashtags(postList.value,checkboxValue.value)
			//排序
			currentPostList.value = sortpostList(currentPostList.value,currentSortMethod.value);
		} else {
			//排序
			currentPostList.value = sortpostList(postList.value,currentSortMethod.value);
		}
		
	}
	
	//获取帖子数据
	const getPost = () => {
	return new Promise<void>((resolve, reject) => {
		if (currentSection.value == 0) {
		// 获取热门帖子
		getPopularPost().then((res: any) => {
			if (res.success === true) {
			postList.value = res.data.postList.map((post: any) => {
				// 修改每个帖子的图片列表
				post.postImageList = post.postImageList.map((postImage: any) => {
				return useBaseStore().baseUrl + postImage;
				});
				return post;
			});
			resolve(); // 当成功获取数据后，调用resolve
			} else {
			uni.showToast({
				title: res.message + '\n' + res.data.error,
				icon: 'error',
				duration: 6000
			});
			reject(new Error(res.message)); // 如果获取数据失败，调用reject
			}
		}).catch((err: any) => {
			console.error('热门帖子请求失败', err);
			reject(err); // 如果请求过程中出现错误，调用reject
		});
		} else {
		// 根据板块获取帖子
		getPostBySectionId(currentSection.value - 1).then((res: any) => {
			if (res.success === true) {
			postList.value = res.data.postList.map((post: any) => {
				// 修改每个帖子的图片列表
				post.postImageList = post.postImageList.map((postImage: any) => {
				return useBaseStore().baseUrl + postImage;
				});
				return post;
			});
			resolve(); // 当成功获取数据后，调用resolve
			} else {
			uni.showToast({
				title: res.message + '\n' + res.data.error,
				icon: 'error',
				duration: 6000
			});
			reject(new Error(res.message)); // 如果获取数据失败，调用reject
			}
		}).catch((err: any) => {
			console.error('根据板块获取帖子请求失败', err);
			reject(err); // 如果请求过程中出现错误，调用reject
		});
		}
	});
	};
	//根据主题标签筛选帖子
	function filterPostsByHashtags(postList:any, checkboxValue:any) {
	  return new Promise((resolve, reject) => {
	    try {
	      // 过滤帖子数组
	      const filteredPosts = postList.filter((post:any) => {
	        // 检查帖子中的每个标签是否存在于checkboxValue数组中
	        return post.postHashtagList.some((hashtag:any) => {
	          return checkboxValue.includes(hashtag.hashtagId);
	        });
	      });
	      // 解析Promise并返回过滤后的帖子数组
	      resolve(filteredPosts);
	    } catch (error) {
	      // 如果发生错误，则拒绝Promise
	      reject(error);
	    }
	  });
	}
</script>

<style scoped>
	.community-head {
		border-bottom: 0.5rpx solid #98a0ab;
	}
	.community-head-sort {
		display:flex;
		margin: 0  0 0 25rpx;
	}
	.community-head-sort .sort-method {
		display:flex;
	}
	.community-body {
		background: #dce8f9;
		padding-top: 25rpx;
		padding-bottom: 140rpx;
	}
	.drop-content {
		height: auto;
	}
	.drop-content .post-hashtag {
		padding: 0 35rpx;
	}
	.drop-content .post-hashtag>:first-child {
		padding-bottom: 20rpx;
	}
	.drop-content .post-hashtag .post-hashtag-checkbox>* {
		margin-right: 20rpx;
		margin-bottom: 15rpx;
	}
</style>