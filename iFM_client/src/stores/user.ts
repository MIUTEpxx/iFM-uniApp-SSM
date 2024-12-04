import { defineStore } from 'pinia'
 
//创建小仓库
const useUserStore = defineStore('User', {
  state: () => {
    return {
		 isLogin:false,//登录状态
		 userId:0,//用户账号
		 userName:'游客',//用户昵称
		 userEmail:'',//用户邮箱
		 userPicture:'/images/user/head/D.png',//用户头像
		 userProfile:'',//用户个性签名
		 Token:'',//Token安全令牌
    }
  },
  actions: {
	  //设置登录状态
	setLogin(isLogin:boolean){
		this.isLogin=isLogin
	},
    //设置User的各个数据
    setUser(
	userId:number,userName:string,userEmail:string,
	userPicture:string,userProfile:string) {
      this.userId=userId
	  this.userName=userName
	  this.userPicture=userPicture
	  this.userProfile=userProfile
	  this.userEmail=userEmail
	  this.isLogin=true
    },
	setUserInfo(res:any){
		this.userId=res.userId
		this.userName=res.userName
		this.userPicture=res.userPicture
		this.userProfile=res.userProfile
		this.userEmail=res.userEmail
		this.isLogin=true
	},
	//设置Token值
	setToken(newToken:string){
		this.Token=newToken
	},
	//登出账号,清除用户信息
	clearUserInfo(){
		this.isLogin=false,
		this.userName='游客',
		this.userEmail='',
		this.userPicture='/images/user/head/D.png',
		this.userProfile='',
		this.Token=''
	},
  }
})
 
//暴露小仓库
export default useUserStore