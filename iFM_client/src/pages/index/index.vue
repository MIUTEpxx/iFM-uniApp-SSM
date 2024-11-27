<template>
 <view class="index">
	 <view class="index-head">
		 <view class="title"> 	
			<uv-text text="发现" color="#9dcaf9" size="40px" bold="true" ></uv-text>
		 </view>
		<search-box></search-box>
		<uv-button size="normal " type="primary" color="#58b3f9" >频道分类</uv-button>
	 </view>
	<view class="index-body">
		<p class="title">今日<br>热门</p>
		<view class="pop-broadcast">
			<uv-text text="节目推荐" color="#86c7f9" size="26px"></uv-text>
			 <scroll-view scroll-y class="broadcast-scroll">
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			</scroll-view>

		</view>
		<view class="pop-channel">
			<uv-text text="频道推荐" color="#86c7f9" size="26px"></uv-text>
			<scroll-view scroll-y class="channle-scroll">
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			  <broadcast-item></broadcast-item>
			</scroll-view>
		</view>
	</view>
	<tabbar></tabbar>
</view>

</template>

<script setup lang="ts">
import { onLaunch, onShow, onHide } from "@dcloudio/uni-app";
onLaunch(() => {
  
});
onShow(() => {
  //隐藏pages.json里配置的导航栏，使用封装的tabbar组件
  	uni.hideTabBar({
          animation:false
       })
});
onHide(() => {
  
});



</script>

<style>
.index {
	margin: 15px 20px;
	
  .index-head {
    display: flex;
    align-items: stretch;
    flex-direction: column;

		.title {
		  margin-bottom: 20px;
		  margin-left: auto;
		}

		.uv-button {
		  padding: 5px 0 8px 0;
		  margin-top: 15px;
		  margin-left: 10px;
		  width: 100px;
		  border-radius: 10px;
		  font-size: 22px;
		}
	}
	.index-body {
		 display: flex;
		 align-items: stretch;
		 flex-direction: column;
		 margin-top: 15px;
		 position: relative;
		.title {
			margin-right: 20px;
			margin-left: auto;
			position: absolute;
			right: 0;
			top:-35px;
			font-size: 28px;
			color: #58b3f9;
		}
		.pop-broadcast{
			margin-bottom: 30px;
			
			.broadcast-scroll {
				height: 450px;
				margin-top: 10px;
				overflow: hidden;
			}
		}
		.pop-channel{
			margin-bottom: 30px;
			
			.channle-scroll {
				height: 450px;
				margin-top: 10px;
				overflow: hidden;
			}
		}
	}
}
</style>
