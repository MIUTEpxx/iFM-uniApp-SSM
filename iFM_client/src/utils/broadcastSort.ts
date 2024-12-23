
// 定义频道数据结构
interface IBroadcast {
   broadcastDuration: number,
   broadcastPlayCount:  number,
   broadcastCollectionCount: number,
   broadcastPostCount: number,
   broadcastCreateTime: number,
}

export const sortBroadcastList = (broadcasts: IBroadcast[], sortMethod: number): any[] => {
  switch (sortMethod) {
    case 0: // 综合排序
      return broadcasts.sort((a, b) => {
        const scoreA = calculateScore(a);
        const scoreB = calculateScore(b);
        return scoreB - scoreA; // 降序
      });
    case 1: // 以创建时间 broadcastCreateTime 降序排序
      return broadcasts.sort((a, b) => b.broadcastCreateTime - a.broadcastCreateTime);
    case 2: // 以播放量 broadcastPlayCount 降序排序
      return broadcasts.sort((a, b) => b.broadcastPlayCount - a.broadcastPlayCount);
    case 3: // 以收藏量 broadcastCollectionCount 降序排序
      return broadcasts.sort((a, b) => b.broadcastCollectionCount - a.broadcastCollectionCount);
    case 4: //// 以创建时间 broadcastCreateTime 升序序排序
	  return broadcasts.sort((a, b) => a.broadcastCreateTime - b.broadcastCreateTime);
	default:
      throw new Error('Invalid sort method');
  }
};

// 计算综合得分的函数
const calculateScore = (broadcast: IBroadcast): number => {
  return broadcast.broadcastCollectionCount *0.2+ 
  broadcast.broadcastPostCount *0.8+
  broadcast.broadcastCreateTime*10 / Math.pow(10,broadcast.broadcastCreateTime.toString().length);
};

export default sortBroadcastList;
