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

//根据用户id获取用户创建的频道
export const getChannelByUserId = (userId:number) =>{
	return request({
		url:'/channel/getChannelByUserId',
		method:'get',
		data:{"userId":userId},
	})
}

//根据频道id删除频道
export const deleteChannel = (userId:number, channelId:number) =>{
	return request({
		url:'/channel/deleteChannel',
		method:'delete',
		data:{userId:userId,channelId:channelId},
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

//创建频道
/* export const createChannel = (userId:number,channelTitle:string,channelDetail:string,channelPicture:File,hashtagIdList:Array<number>) =>{
 	const formData = new FormData();
	formData.append('userId', userId.toString());
	formData.append('channelTitle', channelTitle);
	formData.append('channelDetail', channelDetail);
	hashtagIdList.forEach((id) => formData.append('hashtagIdList', id.toString())); 
	//console.log("formData", formData);
	return request({
		url:'/channel/createChannel',
		method:'post',
		haveFile:true,//表明请求参数中有文件数据, 则头部体有'Content-Type': 'multipart/form-data' 
		data:{
		userId:userId,
		channelTitle:channelTitle,
		channelDetail:channelDetail,
		channelPicture:channelPicture,
		hashtagIdList:hashtagIdList,
		},
	})
} */

//创建频道
export const createChannel = (userId:number,channelTitle:string,channelDetail:string,channelPicture:any,hashtagIdList:Array<number>) =>{

	return request({
		url:'/channel/createChannel',
		method:'post',
		filePath:channelPicture.url,
		name:"channelPicture",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			channelTitle:channelTitle,
			channelDetail:channelDetail,
			hashtagIdList:hashtagIdList,
		},
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

//修改节目标题
export const updateChannelTitle = (userId:number,channelId:number,channelTitle:string) =>{
	return request({
		url:'/channel/updateChannelTitle',
		method:'post',
		data:{
			userId:userId,
			channelId:channelId,
			channelTitle:channelTitle
			},
	})
}
//修改节目详情
export const updateChannelDetail = (userId:number,channelId:number,channelDetail:string) =>{
	return request({
		url:'/channel/updateChannelDetail',
		method:'post',
		data:{
			userId:userId,
			channelId:channelId,
			channelDetail:channelDetail
			},
	})
}
//修改节目封面图片
export const updateChannelPicture = (userId:number,channelId:number,channelPicture:any) =>{
	return request({
		url:'/channel/updateChannelPicture',
		method:'post',
		filePath:channelPicture.url,
		name:"channelPicture",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			channelId:channelId,
		},
	})
}


//获取节目详情
export const getBroadcastDetail = (broadcastId:number) =>{
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
//检查节目是否在用户收听历史中,以决定起始播放时长
export const checkHistory = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/checkHistory',
		method:'get',
		data:{userId:userId,broadcastId:broadcastId},
	})
}
//获取节目音频信息(包括作者名,若为登录状态,则可获得上次播放到的时长数据)
export const getBroadcastAudio = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/getBroadcastAudio',
		method:'get',
		data:{"userId":userId,"broadcastId":broadcastId},
	})
}
//增加节目播放量
export const increasePlayCount = (broadcastId:number) =>{
	return request({
		url:'/broadcast/increasePlayCount',
		method:'post',
		data:{broadcastId:broadcastId},
	})
}
//更新节目收听历史记录
export const updateHistory = (userId:number,broadcastId:number,lastListenDuraction:number) =>{
	return request({
		url:'/broadcast/updateHistory',
		method:'post',
		data:{userId:userId,broadcastId:broadcastId,lastListenDuraction:lastListenDuraction},
	})
}
//根据节目id列表,一次性获取复数节目信息
export const getBroadcastByIdList = (broadcastIdList:number[]) =>{
	return request({
		url:'/broadcast/getBroadcastByIdList',
		method:'get',
		data:{"broadcastIdList":broadcastIdList},
	})
}
//获取用户收听历史表中的节目
export const getHistory = (userId:number) =>{
	return request({
		url:'/broadcast/getHistory',
		method:'get',
		data:{"userId":userId},
	})
}

//删除节目收听历史记录
export const deleteHistory = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/deleteHistory',
		method:'delete',
		data:{userId:userId,broadcastId:broadcastId},
	})
}

//删除节目
export const deleteBroadcast = (userId:number,broadcastId:number) =>{
	return request({
		url:'/broadcast/deleteBroadcast',
		method:'delete',
		data:{userId:userId,broadcastId:broadcastId},
	})
}

//创建节目
export const createBroadcast = (userId:number,channelId:number,broadcastTitle:string,broadcastDetail:string,broadcastPicture:any) =>{

	return request({
		url:'/broadcast/createBroadcast',
		method:'post',
		filePath:broadcastPicture.url,
		name:"broadcastPicture",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			channelId:channelId,
			broadcastTitle:broadcastTitle,
			broadcastDetail:broadcastDetail,
		},
	})
}
//创建节目(添加图片数据)
export const addAudioForBroadcast = (userId:number,broadcastId:number,audioFile:any) =>{

	return request({
		url:'/broadcast/addAudio',
		method:'post',
		filePath:audioFile.url,
		name:"audioFile",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			broadcastId:broadcastId,
		},
	})
}

//修改节目标题
export const updateBroadcastTitle = (userId:number,broadcastId:number,broadcastTitle:string) =>{
	return request({
		url:'/broadcast/updateBroadcastTitle',
		method:'post',
		data:{
			userId:userId,
			broadcastId:broadcastId,
			broadcastTitle:broadcastTitle
			},
	})
}
//修改节目详情
export const updateBroadcastDetail = (userId:number,broadcastId:number,broadcastDetail:string) =>{
	return request({
		url:'/broadcast/updateBroadcastDetail',
		method:'post',
		data:{
			userId:userId,
			broadcastId:broadcastId,
			broadcastDetail:broadcastDetail
			},
	})
}
//修改节目封面图片
export const updateBroadcastPicture = (userId:number,broadcastId:number,broadcastPicture:any) =>{
	return request({
		url:'/broadcast/updateBroadcastPicture',
		method:'post',
		filePath:broadcastPicture.url,
		name:"broadcastPicture",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			broadcastId:broadcastId,
		},
	})
}
//修改节目音频
export const updateBroadcastAudio = (userId:number,broadcastId:number,broadcastAudio:any) =>{
	return request({
		url:'/broadcast/updateBroadcastAudio',
		method:'post',
		filePath:broadcastAudio.url,
		name:"broadcastAudio",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			userId:userId,
			broadcastId:broadcastId,
		},
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

//根据用户id获取用户信息
export const getUserById = (userId:number) =>{
	return request({
		url:'/user/getUserById',
		method:'get',
		data:{"userId":userId},
	})
}

//根据关键词搜索用户
export const getUserByKeyword = (keyword:string) =>{
	return request({
		url:'/user/getUserByKeyword',
		method:'get',
		data:{"keyword":keyword},
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

//检查邮箱是否已被注册
export const checkEmail = (email:string) =>{
	return request({
		url:'/user/checkEmail',
		method:'get',
		data:{"email":email},
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
//修改用户头像
export const changeUserPicture = (userId:number,file:any) =>{
	return request({
		url:'/user/updateUserPicture',
		method:'post',
		filePath:file.url,
		name:"file",
		haveFile:true,//表明请求参数中有文件数据
		data:{
			userId:userId,
		},
	})
}




// 帖子 //
//根据用户id获取用户创建的帖子
export const getPostByUserId = (userId:number) =>{
	return request({
		url:'/post/getPostByUserId',
		method:'get',
		data:{"userId":userId},
	})
}

//创建帖子(非图片部分)
export const createPost = 
	(userId:number,//用户Id
	postTitle:string,//帖子标题
	postDetail:string,//帖子详情内容
	postSection:number,//帖子所属板块id
	postAssociation:number,//帖子关联内容类型
	associationId:number,//帖子关联内容id
	postHashtagList:Array<number>) =>{
	return request({
		url:'/post/createPost',
		method:'post',
		data: {
			userId:userId,
			postTitle:postTitle,
			postDetail:postDetail,
			postSection:postSection,
			postAssociation:postAssociation,
			associationId:associationId,
			postHashtagList:postHashtagList,
		},
	})
}

//创建帖子(图片部分,即为帖子添加图片)
export const addImageForPost = (postId:number,postImage:any) =>{
	return request({
		url:'/post/addImageForPost',
		method:'post',
		filePath:postImage.url,
		name:"postImage",
		haveFile:true,//表明请求参数中有文件数据
		data: {
			postId:postId,
			postImage:postImage,
		},
	})
}
//删除帖子
export const deletePost = (userId:number,postId:number) =>{
	return request({
		url:'/post/deletePost',
		method:'delete',
		data: {
			userId:userId,
			postId:postId,
		},
	})
}
//获取热门帖子
export const getPopularPost = () =>{
	return request({
		url:'/post/getPopularPost',
		method:'get',
	})
}
//根据帖子id获取对应帖子数据
export const getPostByPostId = (postId:number) =>{
	return request({
		url:'/post/getPostByPostId',
		method:'get',
		data:{
			"postId":postId,
		}
	})
}

//根据板块id获取帖子
export const getPostBySectionId = (sectionId:number) =>{
	return request({
		url:'/post/getPostBySectionId',
		method:'get',
		data:{
			"sectionId":sectionId,
		}
	})
}

//根据关键词获取帖子
export const getPostByKeyword = (keyword:string) =>{
	return request({
		url:'/post/getPostByKeyword',
		method:'get',
		data:{
			"keyword":keyword,
		}
	})
}

//获取用户收藏的帖子
export const getFavoritePost = (userId:number) =>{
	return request({
		url:'/post/getFavoritePost',
		method:'get',
		data:{
			"userId":userId,
		}
	})
}

//检查帖子是否已经被用户收藏
export const checkPostCollection = (userId:number,postId:number) =>{
	return request({
		url:'/post/checkCollection',
		method:'get',
		data:{
			"userId":userId,
			"postId":postId,
		}
	})
}
//根据频道或节目ID获取对应关联的帖子
export const getPostByAssociationId = (postAssociation:number,associationId:number) =>{
	return request({
		url:'/post/getPostByAssociationId',
		method:'get',
		data:{
			"postAssociation":postAssociation,
			"associationId":associationId,
		}
	})
}
//用户收藏/取消收藏 帖子 (具体取决于帖子是否处于用户收藏表)
export const changePostCollection = (userId:number,postId:number) =>{
	return request({
		url:'/post/changeCollection',
		method:'post',
		data:{
			"userId":userId,
			"postId":postId,
		}
	})
}

//点赞/取消点赞帖子,更改帖子点赞量
export const changePostLike = (postId:number,value:number) =>{
	return request({
		url:'/post/changeLike',
		method:'post',
		data:{
			"postId":postId,
			"value":value,
		}
	})
}

//获取帖子下的评论
export const getCommentByPostId = (postId:number) =>{
	return request({
		url:'/comment/getCommentByPostId',
		method:'get',
		data:{
			"postId":postId,
		}
	})
}

//添加评论(创建评论,非图片部分)
export const addComment = (userId:number,postId:number,commentDetail:string) =>{
	return request({
		url:'/comment/addComment',
		method:'post',
		data:{
			"userId":userId,
			"postId":postId,
			"commentDetail":commentDetail,
		}
	})
}

//添加评论(图片部分)
export const addImageForComment = (userId:number,commentId:number,commentImage:any) =>{
	return request({
		url:'/comment/addImageForComment',
		method:'post',
		filePath:commentImage.url,
		name:"commentImage",
		haveFile:true,//表明请求参数中有文件数据
		data:{
			"userId":userId,
			"commentId":commentId,
		}
	})
}

//点赞评论
export const changeCommentLike = (commentId:number,value:number) =>{
	return request({
		url:'/comment/changeLike',
		method:'post',
		data:{
			commentId:commentId,
			value:value,
		}
	})
}

//根据评论id获取评论下的所有回复
export const getReplyByCommentId = (commentId:number) =>{
	return request({
		url:'/reply/getReplyByCommentId',
		method:'get',
		data:{
			"commentId":commentId,
		}
	})
}

//添加回复(创建评论,非图片部分)
export const addReply = (userId:number,commentId:number,replyDetail:string) =>{
	return request({
		url:'/reply/addReply',
		method:'post',
		data:{
			userId:userId,
			commentId:commentId,
			replyDetail:replyDetail,
		}
	})
}

//点赞/取消点赞回复,更改回复点赞量
export const changeReplyLike = (replyId:number,value:number) =>{
	return request({
		url:'/reply/changeLike',
		method:'post',
		data:{
			replyId:replyId,
			value:value,
		}
	})
}

//获取用户搜索历史记录
export const getSearchHistory = (userId:number) =>{
	return request({
		url:'/searchHistory/getSearchHistory',
		method:'get',
		data:{
			"userId":userId,
		}
	})
}

//保存用户搜索记录
export const saveSearchHistory = (userId:number,keyword:string) =>{
	return request({
		url:'/searchHistory/saveSearchHistory',
		method:'post',
		data:{
			userId:userId,
			keyword:keyword,
		}
	})
}

//删除用户所有历史记录
export const deleteAllSearchHistory = (userId:number) =>{
	return request({
		url:'/searchHistory/deleteAll',
		method:'delete',
		data:{
			userId:userId,
		}
	})
}

//删除某项用户搜索记录
export const deleteSearchHistory = (userId:number,searchHistoryId:number) =>{
	return request({
		url:'/searchHistory/deleteSearchHistory',
		method:'delete',
		data:{
			userId:userId,
			searchHistoryId:searchHistoryId,
		}
	})
}

