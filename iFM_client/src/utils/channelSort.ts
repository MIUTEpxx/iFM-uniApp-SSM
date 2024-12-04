
// 定义频道数据结构
interface IChannel {
  channelSubscribe: number;
  channelPostCount: number;
  channelUpdateTime: number; // 更新时间戳
  channelCreateTime: number; // 创建时间戳
}

// src/utils/channelSort.ts
export const sortChannelList = (channels: IChannel[], sortMethod: number): any[] => {
  switch (sortMethod) {
    case 0: // 综合排序
      return channels.sort((a, b) => {
        const scoreA = calculateScore(a);
        const scoreB = calculateScore(b);
        return scoreB - scoreA; // 降序
      });
    case 1: // 以创建时间 channelCreateTime 降序排序
      return channels.sort((a, b) => b.channelCreateTime - a.channelCreateTime);
    case 2: // 以上次更新时间 channelUpdateTime 降序排序
      return channels.sort((a, b) => b.channelUpdateTime - a.channelUpdateTime);
    case 3: // 以订阅数 channelSubscribe 降序排序
      return channels.sort((a, b) => b.channelSubscribe - a.channelSubscribe);
    default:
      throw new Error('Invalid sort method');
  }
};

// 计算综合得分的函数
const calculateScore = (channel: IChannel): number => {
  return channel.channelSubscribe *0.5+ 
  channel.channelPostCount *0.5+ 
  channel.channelUpdateTime*10 / Math.pow(10,channel.channelUpdateTime.toString().length) + 
  channel.channelCreateTime*10 / Math.pow(10,channel.channelCreateTime.toString().length);
};

export default sortChannelList;
