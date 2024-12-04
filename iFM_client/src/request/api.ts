import request from './index'


//获取热门节目
export const getPopularBroadcast = () =>{
	return request({
		url:'/broadcast/getPopularBroadcast',
		method:'get',
	})
}

//获取热门频道
export const getPopularChannel = () =>{
	return request({
		url:'/channel/getPopularChannel',
		method:'get',
	})
}

//获取主题标签
export const getHashtag = () =>{
	return request({
		url:'/channel/getHashtag',
		method:'get',
	})
}

//根据主题标签获取频道
export const getChannelByHashtag = (hashtagId:number) =>{
	return request({
		url:'/channel/getChannelByHashtag',
		method:'get',
		data:{"hashtagId":hashtagId},
	})
}

//获取频道所有的节目
export const getBroadcastByChannel = (channelId:number) =>{
	return request({
		url:'/broadcast/getBroadcastByChannelId',
		method:'get',
		data:{"channelId":channelId},
	})
}

//获取频道详情
export const getChannelDetail = (channelId:number) =>{
	return request({
		url:'/channel/getChannelByChannelId',
		method:'get',
		data:{"channelId":channelId},
	})
}

//根据关键词搜索频道
export const searchChannel = (keyword:string) =>{
	return request({
		url:'/channel/searchChannelByKeyWord',
		method:'get',
		data:{"keyword":keyword},
	})
}

//检查频道是否被用户订阅
export const checkSubscribe = (userId:number,channelId:number) =>{
	return request({
		url:'/channel/checkChannel',
		method:'get',
		data:{"userId":userId,"channelId":channelId},
	})
}

//获取用户订阅的频道
export const getSubscribedChannel = (userId:number) =>{
	return request({
		url:'/channel/getSubscribedChannel',
		method:'get',
		data:{"userId":userId},
	})
}

//用户订阅/取消订阅
export const changeSubscribe = (userId:number,channelId:number) =>{
	return request({
		url:'/channel/changeSubscribe',
		method:'post',
		data:{userId:userId,channelId:channelId},
	})
}

//获取节目详情
export const getBroadacastDetail = (broadcastId:number) =>{
	return request({
		url:'/broadcast/getBroadcastByBroadcastId',
		method:'get',
		data:{"broadcastId":broadcastId},
	})
}

//根据关键词搜索节目
export const searchBroadcast = (keyword:string) =>{
	return request({
		url:'/broadcast/getBroadcastByKeyword',
		method:'get',
		data:{"keyword":keyword},
	})
}

//检查节目是否被收藏
export const checkCollect = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/checkBroadcast',
		method:'get',
		data:{"userId":userId,"broadcastId":broadcastId},
	})
}
//获取用户收藏的节目
export const getFavoriteBroadcast = (userId:number) =>{
	return request({
		url:'/broadcast/getFavoriteBroadcast',
		method:'get',
		data:{"userId":userId},
	})
}
//用户收藏/取消收藏
export const changeFavorite = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/changeFavorite',
		method:'post',
		data:{userId:userId,broadcastId:broadcastId},
	})
}

//获取验证码
export const getVCode = (userEmail:string) =>{
	return request({
		url:'/user/getVCode',
		method:'get',
		data:{"userEmail":userEmail},
	})
}

//账号密码登录
export const loginPassword = (userId:string,userPassword:string) =>{
	return request({
		url:'/user/userLoginPassword',
		method:'get',
		data:{"userId":userId,"userPassword":userPassword},
	})
}

//邮箱验证登录
export const loginEmail = (userEmail:string,code:string) =>{
	return request({
		url:'/user/userLoginEmail',
		method:'get',
		data:{"userEmail":userEmail,"code":code},
	})
}
//创建账号
export const createUser = (userName:string,userPassword:string,userEmail:string,code:string) =>{
	return request({
		url:'/user/userRegister',
		method:'post',
		data:{
			userName: userName,
			userPassword: userPassword,
			userEmail: userEmail,
			code: code,
		},
	})
}

//修改用户昵称
export const changeUserName = (userId:number,userName:string) =>{
	return request({
		url:'/user/updateUserName',
		method:'post',
		data:{
			userId:userId,
			userName: userName,
		},
	})
}

//修改用户邮箱
export const changeUserEmail = (userId:number,userEmail:string,userNewEmail:string,code1:string,code2:string) =>{
	return request({
		url:'/user/updateUserEmail',
		method:'post',
		data:{
			userId:userId,
			userEmail: userEmail,
			userNewEmail:userNewEmail,
			code1:code1,
			code2:code2,
		},
	})
}

//修改用户密码
export const changeUserPassword = (userId:number,userPassword:string,userEmail:string,code:string) =>{
	return request({
		url:'/user/updateUserPassword',
		method:'post',
		data:{
			userId:userId,
			userPassword:userPassword,
			userEmail: userEmail,
			code:code,
		},
	})
}

//修改用户个性签名
export const changeUserProfile = (userId:number,userProfile:string) =>{
	return request({
		url:'/user/updateUserProfile',
		method:'post',
		data:{
			userId:userId,
			userProfile:userProfile,
		},
	})
}
