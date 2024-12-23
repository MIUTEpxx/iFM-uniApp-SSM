<template>
	<view class="broadcast-detail">
		<head-general titleContent="编辑节目" ></head-general>
		<view class="broadcast-edit-head">
			<view class="broadcast-title-edit">
				<uv-text :lines="3" :text="broadcast.broadcastTitle" color="#1d2b36" size="24px" bold="true"></uv-text>
				<view class="edit-button" @click="openEditWindow(0)">
					<uv-icon @click="" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="60rpx"></uv-icon>
				</view>
			</view>
			<view class="info">
				<uv-text :lines="2" :text="'发布: '+broadcast.gmtCreate" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
				<uv-text :lines="2" :text="'节目时长: '+ broadcast.broadcastDuration" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
			</view>
			<view class="broadcast-image-edit">
				<view class="broadcast-image">
						<uv-image
						:src= "base_url+broadcast.broadcastPicture"
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
		
		<view class="play-button">
			<view class="button"  @click="playBroadcastAudio">
				<uv-icon name="play-circle-fill" color="#86c7f9" size="75"></uv-icon>
				<uv-text :lines="3" text="点击播放" color="#5cbaf9" size="18px" bold="true"></uv-text>
			</view>
			<view class="edit-button" @click="openEditWindow(3)">
				<uv-icon @click="" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="80rpx"></uv-icon>
				<uv-text class="edit-text" text="更换音频" color="#a3b2ca" size="45rpx" ></uv-text>
			</view>
		</view>
		
		
		<view class="broadcast-button">
			<view class="other-button">
				<view class="post-button" @click="goPostAssociation">
					<uv-icon name="chat" color="#343b4c" size="50px"></uv-icon>
					<p>关联帖子</p>
					<p>{{broadcast.broadcastPostCount}}</p>
				</view>
				<view v-if="!hasCollect" class="favorite button" @click="favoriteClick">
					<uv-icon name="star" color="#343b4c" size="50px"></uv-icon>
					<p>收藏节目</p>
					<p>{{broadcast.broadcastCollectionCount}}</p>
				</view>
				<view v-else class="favorite button" @click="favoriteClick">
					<uv-icon name="star-fill" color="#86c7f9" size="50px"></uv-icon>
					<p style="color:#6cb3ff;">取消收藏</p>
					<p style="color:#6cb3ff;">{{numConversion(CollectionCount)}}</p>
				</view>
				
				<view class="add-playList-button" @click="addPlayerList">
					<uv-icon name="tianjialiebiaoxiang" custom-prefix="custom-icon" color="#343b4c" size="35px" customStyle="padding:7px;"></uv-icon>
					<p>添加至</p>
					<p>播放列表</p>
				</view>
				
			</view>
		</view>
		<view class="channel">
			<uv-text :lines="3" text="节目所属频道" color="#5cbaf9" size="20px" customStyle="margin:5px 10px;" ></uv-text>
			<channel-item v-bind="channel"></channel-item>
		</view>
		<view class="broadcast-detail-content">
			<view class="detail-edit">
				<uv-text :lines="3" text="节目详情内容" color="#5cbaf9" size="20px" bold="true" customStyle="flex-direction: row-reverse;"></uv-text>
				<view class="edit-button" @click="openEditWindow(1)">
					<uv-icon @click="" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="50rpx"></uv-icon>
					<uv-text class="edit-text" text="修改详情" color="#a3b2ca" size="20px" ></uv-text>
				</view>
			</view>
			
			<uv-read-more show-height="300px" :toggle="true" color="#61b7f9" shadowStyle="backgroundImage:none;">
				<uv-text :text="broadcast.broadcastDetail" color="#203d53" size="18px" customStyle="margin:15px 5px 5px 10px;"></uv-text>
			</uv-read-more>
		</view>	
		<player-bar></player-bar>
		
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
			<!-- 音频文件修改窗口 -->
			<view class="edit-conten" v-else-if="editContent==3">
				<uv-text v-if="newAudio.length<=0" text="点击选择新音频文件" color="#767f8b" size="20px" align="left" ></uv-text>
				<uv-text v-else :text="'已选择音频文件:'+newAudio[0].name" color="#767f8b" size="20px" align="left" ></uv-text>
				<view class="audio" v-if="newAudio.length<=0">
					<uv-upload
						:fileList="newAudio" 
						name="6" 
						multiple 
						:maxCount="1" 
						width="680rpx" 
						height="200rpx"
						:previewFullImage="true"
						:deletable="true"
						accept="media "
						@afterRead="handleAfterReadAudio"
						@delete="deleteAudio"
						custom-style="
						display: flex;
						justify-content: center;
						align-items: center;
						">
						<view class="upload-audio">
							<span>+</span>
						</view>
					</uv-upload>
				</view>
				<view class="audio" v-else>
					<web-view :fullscreen="false" :src="newAudio[0].url" style="padding-bottom: 100rpx;"></web-view>
					<uv-icon @click="deleteAudio" name="close-circle" color="#767f8b" size="40" style="padding-bottom: 120rpx;padding-left: 25rpx;" ></uv-icon>
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

<script  setup lang="ts">
	import { onLoad, onShow } from "@dcloudio/uni-app";
	import { getChannelDetail, getBroadcastDetail, checkCollect, changeFavorite, updateBroadcastTitle, updateBroadcastDetail, updateBroadcastPicture, updateBroadcastAudio } from "@/request/api"; 
	import { ref,watch } from 'vue';
	import { changeTime } from "@/utils/timeChange"
	import { numConversion} from '@/utils/numConversion';
	import { formatDuration} from '@/utils/durationFormatter';
	import useBaseStores from '@/stores/base';
	import useUserStores from '@/stores/user';
	import { logOut } from "@/utils/logOut";
	import { usePlayerStore } from "@/stores/player";
	
	//后端请求路径
	const base_url = useBaseStores().baseUrl;
	//获取用户信息
	const userStore = useUserStores();
	//全局音频播放器
	const playerStore= usePlayerStore();
	//修改窗口索引
	const popupEdit = ref<any>(null);
	//修改确认弹窗
	const popupConfirm = ref<any>(null);
	
	let broadcastId = ref(0);
	//储存频道信息
	let channel = ref<any>([]);
	//储存节目项数据的数组
	let broadcast= ref<any>([]);
	//用户是否已订阅该频道
	let hasCollect= ref<boolean>(false);
	//节目收藏量
	let CollectionCount= ref<number>(0);
	
	//当前修改的内容
	let editContent = ref(0);//0:标题 1:详情内容 2:封面图片 3:音频文件
	//新标题
	let newTitle = ref("");
	//新详情内容
	let newDetail = ref("");
	//新封面图片
	let newImage = ref<any>([]);
	//新音频文件
	let newAudio = ref<any>([]);
	
	// 删除图片
	function deletePic(event:any) {
		newImage.value.splice(event.index, 1);
	}
	// 删除音频
	function deleteAudio(event:any) {
		newAudio.value = [];
	}
	
	// 处理图片文件读取后的逻辑
	function handleAfterReadImage(event:any) {
		// 直接将文件赋值给imageList
		newImage.value = event.file;
	}
	// 处理音频文件读取后的逻辑
	function handleAfterReadAudio(event:any) {
		// 直接将文件赋值给audioList
		newAudio.value = event.file;
		//console.log(newAudio.value);
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
		newTitle .value =  broadcast.value.broadcastTitle;
		newDetail.value =  broadcast.value.broadcastDetail;
		newImage .value =  []
		newAudio .value =  []
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
			}else if(newTitle.value == broadcast.value.broadcastTitle) {
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
			}else if(newDetail.value == broadcast.value.broadcastDetail) {
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
		}else {
			if(newAudio.value.length == 0) {
				uni.showToast({
					title: '请选择音频文件替换原音频',
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
			updateBroadcastTitle(userStore.userId,broadcastId.value,newTitle.value).then((res:any)=>{
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
							url: '/pages/broadcast/broadcast-detail-edit?broadcastId='+broadcastId.value,
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
			updateBroadcastDetail(userStore.userId,broadcastId.value,newDetail.value).then((res:any)=>{
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
							url: '/pages/broadcast/broadcast-detail-edit?broadcastId='+broadcastId.value,
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
			updateBroadcastPicture(userStore.userId,broadcastId.value,newImage.value[0]).then((res:any)=>{
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
							url: '/pages/broadcast/broadcast-detail-edit?broadcastId='+broadcastId.value,
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
		}else {
			//修改音频
			updateBroadcastAudio(userStore.userId,broadcastId.value,newAudio.value[0]).then((res:any)=>{
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
							url: '/pages/broadcast/broadcast-detail-edit?broadcastId='+broadcastId.value,
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
	
	//收藏按钮点击
	const favoriteClick =() =>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		hasCollect.value=!hasCollect.value
		if(hasCollect.value===true){CollectionCount.value+=1}
		else {CollectionCount.value-=1}
		//用户收藏/取消收藏
		changeFavorite(userStore.userId,broadcastId.value).then((res: any) => {
			if(res.success===false){
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err: any) => {
			console.error('节目收藏请求失败', err);
		});
	}
	
	 onLoad((options:any) => {
		broadcastId.value = JSON.parse(options.broadcastId); // 字符串转对象
		//获取节目信息
		getBroadcastDetail (broadcastId.value).then((res:any) => {
			broadcast.value=res.data.broadcast
			newTitle.value = broadcast.value.broadcastTitle;
			newDetail.value= broadcast.value.broadcastDetail;
			//处理节目的数据
			broadcast.value.gmtCreate=changeTime(broadcast.value.gmtCreate)
			broadcast.value.broadcastDuration=formatDuration(broadcast.value.broadcastDuration)
			broadcast.value.dcastPostCount=numConversion(broadcast.value.broadcastPostCount)
			CollectionCount.value = broadcast.value.broadcastCollectionCount
			 // 在获取到节目信息后，再获取频道信息
			    if (broadcast.value.channelId) {
				/* console.log("broadcast.value.channelId",broadcast.value.channelId) */
			      // 获取频道信息
			      getChannelDetail(broadcast.value.channelId).then((res: any) => {
			        channel.value = res.data.channel;
			        //console.log("channel.value", channel.value);
			      }).catch((err: any) => {
			        console.error('频道数据请求失败', err);
			      });
			    }
		}).catch((err:any) => {
		  console.error('节目数据请求失败', err); 
		});
		if(userStore.isLogin===true){
			//若用户处于登录状态,检查用户是否已收藏该节目
			checkCollect(userStore.userId,broadcastId.value).then((res: any) => {
					if(res.success===true){
						hasCollect.value=res.data.favorite
					}else{
						uni.showToast({
							title: res.message+'\n'+res.data.error,
							icon: 'error',
							duration: 3000
						}) 
					}
				  }).catch((err: any) => {
				console.error('用户收藏信息失败', err);
			  });
		}        
		
		
	});
	//将音频添加至播放列表
	const addPlayerList =()=>{
		playerStore.pushPlayList(false, [broadcastId.value]);
		uni.showToast({
			title: "音频已添加至列表",
			icon: 'none',
			duration: 3000
		}) 
	}
	//播放节目音频
	const playBroadcastAudio =()=>{
		playerStore.play(broadcastId.value);
	}
	//跳转关联帖子页
	const goPostAssociation =()=>{
		uni.navigateTo({
		  url: "/pages/community/post-association?postAssociation=1&associationId="+broadcastId.value,
		});   
	}
</script>

<style scoped>
	.edit-button {
		display: flex;
	}
	.broadcast-edit-head {
		display: flex;
		flex-direction: column;
		padding: 0 20px 0 20px;
		height: 250rpx;	
		padding-bottom: 200rpx;
	}
	.broadcast-edit-head .broadcast-title-edit {
		display: flex;
		justify-content: space-between;
	}
	.broadcast-image-edit {
		display: flex;
	}
	
	.broadcast-image-edit .broadcast-image {
		position: relative;
		width: 250rpx;
		margin: 30rpx 30rpx 0 0;
		padding: 0;
		padding-bottom: 250rpx; 
	}
	.broadcast-image-edit .edit-text {
		padding-left: 10rpx;
		padding-bottom: 13rpx;
	}
	
	
	.channel{
		margin: 20px 10px;
	}
	
	.play-button {
		display:flex;
		margin: 25rpx 30rpx;
	}
	.play-button .button {
		margin-right: 35rpx;
	}
	.broadcast-button {
		display:flex;
	    justify-content: space-between;
	    align-items: center;	
		margin: 10rpx 20rpx;
	}
	
	.other-button {
		display: flex;
		align-items: center;	
	}
	.other-button >* {
		flex: 1;
		display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: space-between;
	}

	.other-button p{
		margin: 3px 5px;
		font-size: 16px;
	}
	.broadcast-detail-content {
		margin: 10px 20px;
		padding-bottom: 110px;
	}
	.broadcast-detail-content .detail-edit {
		display: flex;
	}

	.broadcast-detail-content .uv-read-more {
		margin-top: 10px;
		padding-bottom: 30px;
		border-top: 3px dashed #9dcaf9;
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
	.edit-conten .audio {
		margin-top: 15rpx;
		width: 100%;
		height: 200rpx;
		display: flex;
		justify-content: center;
		align-items: flex-end;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.edit-conten .audio .upload-audio {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 1000rpx;
		height: 200rpx;
		background: #d7e3ee;
	}
	.upload-audio >span {
		margin-bottom: 15rpx;
		color: white;
		font-size: 100rpx;
		font-weight: 900;
	}
</style>