/**
 * 时间格式 普通时间戳转换时间
 * @param value 时间戳
 */
export function changeTimeStamp(value: any) {	
	const data = new Date(value);
	const month = data.getMonth() + 1;
	const day = data.getDate();
	const year = data.getFullYear();
	const hours = data.getHours();
	const minutes = data.getMinutes();
	//const seconds = data.getSeconds();
	const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}`;
	return formattedTime;
}

/**
 * 优化时间格式 
 * 如 2024-11-22 18:57:35->2024-11-22 18:57
 * @param {any} value 
 * @return 
 */ 
export function changeTime(value: string):string {
  // 替换 'T' 为 ' '
  value = value.replace('T', ' ');
  // 分割字符串并取到分钟
  return value.split(':').slice(0, 2).join(':');
}
