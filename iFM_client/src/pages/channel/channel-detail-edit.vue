<template>
	<view class="channel-edit-detail">
		<head-general titleContent="编辑频道" ></head-general>
		<view class="channel-edit-head">
			<view class="channel-title-edit">
				<uv-text :lines="3" :text="channelDetail.channelTitle" color="#1d2b36" size="24px" bold="true"></uv-text>
				<uv-icon @click="openEditWindow(0)" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="60rpx"></uv-icon>
			</view>
			
			<view class="channel-image-edit">
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
				<view class="edit-button" @click="openEditWindow(2)">
					<uv-icon @click="" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="80rpx"></uv-icon>
					<uv-text class="edit-text" text="更换封面" color="#a3b2ca" size="45rpx" ></uv-text>
				</view>

			</view>
		</view>
			
		
		<view class="channel-edit-detail-body">
			<view class="channel-edit-subscribe">
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
					
					<view class="post" @click="goPostAssociation">
						<uv-icon name="chat" color="#86c7f9" size="40px"></uv-icon>
						<p>{{numConversion(postCount)}} </p>
					</view>
				</view>
			</view>
			
			<view class="channel-edit-introduction-bg">
				<view class="channel-edit-introduction">
					<view class="introduction-head">
						<uv-text text="频道简介" color="#61b7f9" size="20px" bold="true" customStyle="margin:0 0 10px 10px;"></uv-text>
						<view class="detail-edit-button" @click="openEditWindow(1)">
							<uv-icon @click="" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="45rpx"></uv-icon>
							<uv-text class="edit-text" text="编辑简介" color="#a3b2ca" size="30rpx" ></uv-text>
						</view>
					</view>
					<uv-read-more ref="readMore" show-height="100px" :toggle="true" color="#61b7f9" shadowStyle="backgroundImage:none;">
						<uv-parse class="content" :content="channelDetail.channelDetail" @load="load"></uv-parse>
						<!-- <uv-text :text="channelDetail.channelDetail" color="#203d53" size="18px" customStyle="margin:0 5px 5px 10px;"></uv-text> -->
					</uv-read-more>
				</view>
			</view>

			
			<view class="creat-new" @click="goCreationNew">
				<view class="creat-title">
					<p>节</p>
					<p>目</p>
				</view>
				<view class="creat-body">
					<p>点击创建</p><span>+</span>
				</view>
			</view>		
			
			<view class="delete-button">
				<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
					<p>选择删除</p>
				</view>
				<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
					<p>取消选择</p>
				</view>
			</view>
			
			
			<view class="broadcast-sort">
				<uv-text :lines="3" text="单集节目" color="#61b7f9" size="24px"></uv-text>
				<uv-tabs 
				@click="sortMethodClick"
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
				<view class="broadcast-item-list"  v-for="(item,i) in broadcastList" :key="i">
					<broadcast-item  
					class="item" 
					v-bind="item"
					:enableComponentClick="false"
					@click="goBroadcastDetailEdit(item)" ></broadcast-item>
					<uv-icon v-if="inDelete" @click="open(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>
				</view>
				<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
			</scroll-view>
			<player-bar></player-bar>
		</view>
		<popup-window
		ref="popup"
		popupTitleText="确认删除该节目"
		:popupWindowText="'节目: '+ deleteTitle"
		leftButtonText="取消删除"
		rightButtonText="确定删除"
		@leftButtonClick="cancelDelete"
		@rightButtonClick="confirmDelete">
		</popup-window>
		<!-- 修改内容的弹窗 -->
		<uv-popup ref="popupEdit" mode="bottom" custom-style="background: #dce8f9; height: 1500rpx;">
			<view class="edit-window-head">
				<uv-icon @click="closeEdit" name="close-circle" color="#86c7f9" size="65rpx" ></uv-icon>
				<uv-button type="primary" @click="editButtonClick" text="点击修改" color="#6eabf6" customStyle="width: 200rpx;"></uv-button>
			</view>
			<!-- 标题修改窗口 -->
			<view class="edit-conten" v-if="editContent==0">
				<uv-text text="编辑标题" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="broadcast-input"
				v-model="newTitle" 
				placeholder="请输入新标题"
				adjustPosition="true" 
				count="true"
				maxlength=32
				autoHeight="true"
				border="none"
				customStyle="background:#ebeff5;margin:25rpx 0;"
				countStyle="background-color:rgba(0, 0, 0, 0.0);"
				textStyle="font-size:32rpx;color:#404753;"
				placeholderStyle="font-size:32rpx;color:#717d93;">
				</uv-textarea>
			</view>
			<!-- 详情修改窗口 -->
			<view class="edit-conten" v-else-if="editContent==1">
				<uv-text text="编辑详情内容" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="broadcast-input"
				v-model="newDetail" 
				placeholder="请输入新详情内容"
				adjustPosition="true" 
				count="true"
				maxlength=3000
				height="450rpx"
				border="none"
				customStyle="background:#ebeff5;margin:25rpx 0;"
				countStyle="background-color:rgba(0, 0, 0, 0.0);"
				textStyle="font-size:32rpx;color:#404753;"
				placeholderStyle="font-size:32rpx;color:#717d93;">
				</uv-textarea>
			</view>
			<!-- 封面图片修改窗口 -->
			<view class="edit-conten" v-else-if="editContent==2">
				<uv-text text="点击选择新封面" color="#767f8b" size="20px" align="left" ></uv-text>
				<view class="edit-image">
					<uv-upload
					:fileList="newImage" 
					name="6" 
					multiple 
					:maxCount="1" 
					width="420rpx" 
					height="420rpx"
					:previewFullImage="true"
					:deletable="true"
					accept="image"
					@afterRead="handleAfterReadImage"
					@delete="deletePic"
					custom-style="
					display: flex;
					justify-content: center;
					align-items: center;
					">
						<view class="upload-new-image">
							<span>+</span>
						</view>
				</uv-upload>
			</view>
				
			</view>
		</uv-popup>
		<!-- 确认修改弹窗 -->
		<popup-window
		ref="popupConfirm"
		popupTitleText="确认修改"
		popupWindowText="请检查填写的修改内容"
		leftButtonText="暂不修改"
		rightButtonText="确定修改"
		@leftButtonClick="concelEdit"
		@rightButtonClick="confirmEdit">
		</popup-window>
	</view>
</template>

<script setup lang="ts">
	import { onLoad } from "@dcloudio/uni-app";
	import { getBroadcastByChannel,getChannelDetail,checkSubscribe, changeSubscribe, deleteBroadcast, updateChannelTitle, updateChannelDetail, updateChannelPicture } from "@/request/api"; 
	import { nextTick, ref,watch } from 'vue';
	import { changeTime } from "@/utils/timeChange"
	import { numConversion} from '@/utils/numConversion';
	import useBaseStores from '@/stores/base';
	import useUserStores from '@/stores/user';
	import { logOut } from "@/utils/logOut";
	import sortBroadcastList from "@/utils/broadcastSort";
	import useBaseStore from "@/stores/base";
	
	//后端请求路径
	const base_url = useBaseStores().baseUrl;
	//获取用户信息
	const userStore = useUserStores();
	//修改窗口索引
	const popupEdit = ref<any>(null);
	//修改确认弹窗
	const popupConfirm = ref<any>(null);
	//
	const readMore = ref<any>(null)
	
	//是否处于删除状态
	let inDelete =ref(false)
	//待删除的id
	let deleteId=ref(0)
	//待删除的索引
	let deleteIndex=ref(0)
	//待删除的节目标题
	let deleteTitle=ref("")
	
	//排序方法
	const list=[{name:'最新发布'},{name:'最早发布'},{name:'最多收藏'}];
	let channelId = ref(0);
	//储存频道详情信息
	let channelDetail = ref<any>([]);
	//储存节目项数据的数组
	let broadcastList= ref<any>([]);
	//用户是否已订阅该频道
	let hasSubscribe= ref(false);
	//频道订阅数
	let subscribeCount= ref(0)
	//频道关联帖子数
	let postCount= ref(0)
	//当前排序方式
	let currentSortMethod=ref(0)
	
	
	//当前修改的内容
	let editContent = ref(0);//0:标题 1:详情内容 2:封面图片 3:音频文件
	//新标题
	let newTitle = ref("");
	//新详情内容
	let newDetail = ref("");
	//新封面图片
	let newImage = ref<any>([]);

	
	// 删除图片
	function deletePic(event:any) {
		newImage.value.splice(event.index, 1);
	}
	// 处理图片文件读取后的逻辑
	function handleAfterReadImage(event:any) {
		// 直接将文件赋值给imageList
		newImage.value = event.file;
	}

	
	//打开修改弹窗
	const openEditWindow =(i:number)=>{
		 if (popupEdit.value) {
		    popupEdit.value.open();
		}
		//指定修改内容
		editContent.value=i;
	}
	
	//关闭编辑窗口 清空已输入信息
	const closeEdit = () =>{
		if (popupEdit.value) {
		    popupEdit.value.close();
		}
		newTitle .value =  channelDetail.value.channelTitle;
		newDetail.value =  channelDetail.value.channelDetail;
		newImage .value =  []
	}
	
	//点击修改按钮 检查内容 弹出确认弹窗
	const editButtonClick = () =>{
		if(editContent.value == 0) {
			if(newTitle.value == "") {
				uni.showToast({
					title: '标题内容不能为空',
					icon: 'error',
					duration: 1000
				}) 
				return;
			}else if(newTitle.value == channelDetail.value.channelTitle) {
				uni.showToast({
					title: '新标题不能和原标题相同',
					icon: 'error',
					duration: 1000
				}) 
				return;
			}
		}else if(editContent.value == 1) {
			if(newDetail.value == "") {
				uni.showToast({
					title: '详情内容不能为空',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}else if(newDetail.value == channelDetail.value.channelDetail) {
				uni.showToast({
					title: '新详情内容不能和原详情内容相同',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}
		}else if(editContent.value == 2) {
			if(newImage.value.length == 0) {
				uni.showToast({
					title: '请选择图片作为新封面',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}
		}
		//打开确认弹窗
		if (popupConfirm.value) {
		    popupConfirm.value.open();
		}
		
	}
	
	//关闭确认弹窗
	const concelEdit = () => {
		if (popupConfirm.value) {
		    popupConfirm.value.close();
		}
		
	}
	
	//确认修改
	const confirmEdit = () => {
		//关闭确认弹窗
		concelEdit();
		if(editContent.value == 0) {
			//修改标题
			updateChannelTitle(userStore.userId,channelId.value,newTitle.value).then((res:any)=>{
				if(res.success===true){
					//关闭编辑弹窗
					closeEdit();
					uni.showToast({
						title: '修改成功!',
						icon: 'success',
						duration: 1000
					}) 
					// 设置1秒后刷新页面
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/channel/channel-detail-edit?channelId='+channelId.value,
						});
					}, 1000); // 1000毫秒后执行
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err: any) => {
				console.error('标题修改请求失败', err);
			});
		}else if(editContent.value == 1) {
			//修改详情
			updateChannelDetail(userStore.userId,channelId.value,newDetail.value).then((res:any)=>{
				if(res.success===true){
					//关闭编辑弹窗
					closeEdit();
					uni.showToast({
						title: '修改成功!',
						icon: 'success',
						duration: 1000
					}) 
					// 设置1秒后刷新页面
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/channel/channel-detail-edit?channelId='+channelId.value,
						});
					}, 1000); // 1000毫秒后执行
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err: any) => {
				console.error('标题修改请求失败', err);
			});
		}else if(editContent.value == 2) {
			//修改封面图片
			updateChannelPicture(userStore.userId,channelId.value,newImage.value[0]).then((res:any)=>{
				if(res.success===true){
					//关闭编辑弹窗
					closeEdit();
					uni.showToast({
						title: '修改成功!',
						icon: 'success',
						duration: 1000
					}) 
					// 设置1秒后刷新页面
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/channel/channel-detail-edit?channelId='+channelId.value,
						});
					}, 1000); // 1000毫秒后执行
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err: any) => {
				console.error('标题修改请求失败', err);
			});
		}
	}
	
	const sortMethodClick =(e:any)=>{
		currentSortMethod.value=e.index+1
		if(currentSortMethod.value===2){
			currentSortMethod.value=4;//最早发布排序的序号为4,而页面显示对应的序号为2,所以需要修改
		}
		console.log("currentSortMethod",currentSortMethod.value)
		broadcastList.value=sortBroadcastList(broadcastList.value,currentSortMethod.value)
	}
	
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
		deleteId.value=item.broadcastId;
		deleteTitle.value=broadcastList.value[index].broadcastTitle;
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
	
	//删除用户创作的节目
	const confirmDelete =() =>{
		//确认删除提示框
		deleteBroadcast(userStore.userId,deleteId.value).then((res:any)=>{
			if(res.success!=false){
				// 删除channelList中对应index的元素
				console.log("deleteIndex",deleteIndex.value);
				broadcastList.value.splice(deleteIndex.value, 1);
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
			console.error('节目删除请求失败', err); 
		})
	}

	
	  onLoad((options: any) => {
	      channelId.value = JSON.parse(options.channelId); // 字符串转对象
	  
	      // 定义一个数组来存储所有的 Promise
	      const promises = [
				//获取频道详情信息
				getChannelDetail (channelId.value).then((res:any) => {
					channelDetail.value=res.data.channel
					//console.log("channelDetail.value",channelDetail.value)
					subscribeCount.value=channelDetail.value.channelSubscribe
					postCount.value=channelDetail.value.channelPostCount
					//转化时间格式
					channelDetail.value.channelCreateTime=changeTime(channelDetail.value.channelCreateTime);
					channelDetail.value.channelUpdateTime=changeTime(channelDetail.value.channelUpdateTime);
					//console.log("channelDetail",channelDetail.value)
					
					newTitle .value =  channelDetail.value.channelTitle;
					newDetail.value =  channelDetail.value.channelDetail;
				}).catch((err:any) => { 
					console.error('频道数据请求失败', err); 
				}),
				
				//获取频道下的节目数据
				getBroadcastByChannel (channelId.value).then((res:any) => {
					broadcastList.value=res.data.broadcastList
				}).catch((err:any) => { 
					console.error('节目数据请求失败', err); 
				})
		
			];
	  
	      // 如果用户已登录，添加检查订阅状态的 Promise
	      if (userStore.isLogin === true) {
	          promises.push(checkSubscribe(userStore.userId, channelId.value).then((res: any) => {
	              if (res.success === true) {
	                  hasSubscribe.value = res.data.subscribe;
	              } else {
	                  uni.showToast({
	                      title: res.message + '\n' + res.data.error,
	                      icon: 'error',
	                      duration: 2000
	                  });
	              }
	          }));
	      }
	  
	      // 等待所有 Promise 完成
	      Promise.all(promises).then(() => {
	          // 所有异步操作完成后，检查用户权限
	          if (userStore.isLogin === false || userStore.userId !== channelDetail.value.userId) {
	              uni.showToast({
	                  title: "非作者,无权访问",
	                  icon: 'error',
	                  duration: 2000
	              });
	  
	              // 使用 setTimeout 来等待提示框显示完毕
	              setTimeout(() => {
	                  useBaseStore().activeTab = 0;
	                  uni.switchTab({
	                      url: '/pages/index/index'
	                  });
	              }, 2000);
	          }
	      }).catch((err: any) => {
	          console.error('请求失败', err);
	      });
		  
		  //
		  nextTick(() => {
		    if (readMore.value) {
		      readMore.value.init();
		  	/* console.log("readMore.value.init();") */
		    }
		  });
	  });

	const goCreationNew=()=>{
		uni.navigateTo({
		  url: "/pages/creation/creation-broadcast?channelId="+channelId.value,
		}); 
	}
	
	//前往可编辑节目详情页
	const goBroadcastDetailEdit =(item:any)=>{
		uni.navigateTo({
		  url: "/pages/broadcast/broadcast-detail-edit?broadcastId="+item.broadcastId,
		}); 
	}
	const load =()=>{
		setTimeout(()=>{
			readMore.value.init();
		},1000)
	}
	//前往关联帖子页
	const goPostAssociation =()=>{
		uni.navigateTo({
		  url: "/pages/community/post-association?postAssociation=0&associationId="+channelId.value,
		});   
	}
</script>

<style scoped>
	.edit-button {
		display: flex;
	}
	.channel-edit-head {
		display: flex;
		flex-direction: column;
		padding: 0 20px 0 20px;
		height: 250rpx;	
		padding-bottom: 80rpx;
	}
	.channel-edit-head .channel-title-edit {
		display: flex;
		justify-content: space-between;
	}
	.channel-image-edit {
		display: flex;
	}
	
	.channel-image-edit .channel-image {
		position: relative;
		width: 250rpx;
		margin: 30rpx 30rpx 0 0;
		padding: 0;
		padding-bottom: 250rpx; 
	}
	.channel-image-edit .edit-text {
		padding-left: 10rpx;
		padding-bottom: 13rpx;
	}
	.channel-edit-detail-body {
		margin-top:20px;
		/* margin: 0 10px 0 10px; */
	}
	.channel-edit-detail-body  .channel-edit-subscribe {
		display: flex;
	    justify-content: space-between;
	    align-items: center;
		margin: 25rpx ;
		margin-top: 60rpx;
		margin-bottom: 10rpx;
	}
	 .channel-edit-subscribe .subscribe-number{
		display: flex;
		align-items: center;
	}
	 .channel-edit-subscribe .button {
		display: flex;
	    align-items: center;
	    justify-content: space-around;	
	}
	 .channel-edit-subscribe .button .post {
		flex: 0 0 30%;
		display: flex;
	    flex-direction: column;
	    align-items: center;
		margin-left: 10px;
		color: #407aa7;
	}
	 .channel-edit-subscribe .button  .post p{
		font-size: 16px;
	}
	.channel-edit-introduction-bg {
		background: #dce8f9;
		padding: 25rpx 0;
	}
	.channel-edit-introduction {
		/* margin: 20px 0 20px 0; */
		padding: 10px;
		min-height: 100px;
		background-color: #fff;
	}
	.introduction-head {
		display: flex;
	}
	.channel-edit-introduction .detail-edit-button {
		display: flex;
		align-items: center;
	} 
	.channel-edit-introduction .detail-edit-button .edit-text {
		padding-bottom: 6rpx;
		padding-left: 5rpx;
	}
	.creat-new {
		display:flex;
		margin: 25rpx;
		margin-top: 35rpx;
		overflow: hidden;
		/* width: 90%; */
		border: 1px solid #a9bee4;
		border-radius: 15rpx;
	}
	.creat-new .creat-title {
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
	.creat-new .creat-body {
		flex: 6;
		display: flex;
	    align-items: center;
	    justify-content: space-evenly;
		background: #e8f5ff;
		font-weight: 900;
		font-size: 60rpx;	
		color:#6eb9ff;
	}
	.creat-new .creat-body>span {
		font-size: 100rpx;	
		margin-bottom: 20rpx;
	}
	.broadcast-sort {
		display: flex;
		margin: 15rpx 25rpx;
	}
	.broadcast-scroll {
		max-height: 500px;
		/* margin-top: 10px; */
		padding: 10rpx 0rpx;
		padding-bottom: 110px;
		overflow: hidden;
		/* border-top: 3px dashed #9dcaf9; */
		background: #dce8f9;
	}
	.broadcast-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
		
	}
	.broadcast-item-list>*{
		flex: 1;
	}
	.broadcast-item-list>.item{
		flex: 9;
		margin:10rpx;
		
	}
	.delete-button {
		display:flex;
		/* padding:0 20rpx; */
		margin-bottom: 15rpx;
		width: 90%;
	}
	.un-delete-button {
		padding: 3px 5px;
		margin-left: 25rpx;
		color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.in-delete-button {
		padding: 3px 5px;
		margin-left: 25rpx;
		color: white;
		background-color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.edit-window-head {
		display: flex;
		margin-bottom: 15rpx;
		padding: 15rpx;
		border-bottom: 1px solid #d2d8e8;
		background-color: white;
		justify-content: space-between;
		align-items: center;
	}
	.audio-edit {
		width: 90%;
		border-top: 3px dashed #9dcaf9;
	}
	
	.audio-edit .edit-image {
		width: 400rpx;
		height: 400rpx;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.upload-new-image {
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 35rpx 0;
		width: 400rpx;
		height: 400rpx;
		background: #d7e3ee;
		border-radius: 15rpx;
	}
	.upload-new-image >span {
		margin-bottom: 25rpx;
		color: white;
		font-size: 150rpx;
		font-weight: 900;
	}
	
	.audio-edit>:first-child {
		padding-bottom: 15rpx;
	}
	.edit-conten {
		padding: 15rpx;
		background: white;
	}
</style>