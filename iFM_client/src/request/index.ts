//引入pinia仓库
import useBaseStore from '@/stores/base'
import useUserStore from '@/stores/user'
import { logOut } from '@/utils/logOut';


//let Token = useUserStore().Token
// 全局请求封装
//const base_url = useBaseStore().baseUrl
// 请求超出时间
const timeout = 5000
 
// 需要修改token，和根据实际修改请求头
export default async (params: any) => {
	let url = params.url;
	let method = params.method || "get";
	let data = params.data || {};
	let haveFile = params.haveFile || false;
	let header = {
		'Content-Type': 'application/json;charset=UTF-8',
		'Token': useUserStore().Token || '',//Token安全令牌验证
		...params.header
	}
	if (method != "get") {
		header = {
			'Content-Type': 'application/x-www-form-urlencoded' ,
			'Token': useUserStore().Token || '',//Token安全令牌验证
		};
	}
	if (haveFile==true) {
		//请求附带文件
		header = {
			//'Content-Type': 'multipart/form-data' ,
			'Token': useUserStore().Token || '',//Token安全令牌验证
		};
		let filePath = params.filePath;
		let name = params.name;
		//console.log("formData", data);
		try {
			return await new Promise((resolve, reject) => {
				uni.uploadFile({
					url: useBaseStore().baseUrl + url,
					filePath:filePath,
					name:name,
					method: method,
					header: header,
					formData:data,
					timeout,
					success: (uploadRes:any) => {
						const res = uploadRes
						// 尝试解析data字段 ,因为uploadRes中的data字段是一个JSON字符串
						let parsedData;
						try {
						  parsedData = JSON.parse(res.data);
						} catch (error) {
						  console.error("Error parsing response data:", error);
						  reject(error); // 处理解析错误
						  return;
						}
						res.data=parsedData;
						//console.log("uploadFile321",res)
						// 根据返回的状态码做出对应的操作
						if(res.data.code==20005){
							//token安全令牌失效,用户重新登录
							logOut();
							resolve(res.data)
						}
						else if (res.statusCode == 200) {
							//获取成功
							//若有token, 则更新
							//console.log("uploadFile123",res);
							if(res.data && res.data.data && res.data.data.token){
								useUserStore().setToken(res.data.data.token);
							}
							resolve(res.data)
						} else {
							uni.clearStorageSync()
						}
				},
				fail: (err) => {
					console.error(err);
					console.error('图片上传失败', 'none');
				},
				complete() {
					// 不管成功还是失败都会执行
					uni.hideLoading()
					uni.hideToast()
				}
			});		
				})
		} catch { }
		 
	}
	else{
	//其他普通请求
		try {
			return await new Promise((resolve, reject) => {
				uni.request({
					url: useBaseStore().baseUrl + url,
					method: method,
					header: header,
					data: data,
					timeout,
					success(response:any) {
						const res = response
						//console.log("request",res);
						// 根据返回的状态码做出对应的操作
						if(res.data.code==20005){
							//token安全令牌失效,用户重新登录
							//console.log("token安全令牌失效,用户重新登录")
							logOut();
							resolve(res.data)
						}						
						else if (res.statusCode == 200) {
							//若有token, 则更新
							if(res.data.data.token){
								useUserStore().setToken(res.data.data.token);
							}
							//获取成功
							resolve(res.data)
						} else {
							uni.clearStorageSync()
						}
					},
					fail(err) {
						console.log(err)
					},
					complete() {
						// 不管成功还是失败都会执行
						uni.hideLoading()
						uni.hideToast()
					}
				})
			})
		} catch { }
	}
	
};