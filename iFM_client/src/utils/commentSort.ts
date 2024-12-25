
// 定义频道数据结构
interface IComment {
  commentLikeCount: number;  //点赞数
  commentReplyCount: number; //回复数
  commentCreateTime: number; // 创建时间戳
}

export const sortCommentClick = (channels: IComment[], sortMethod: number): any[] => {
  switch (sortMethod) {
    case 0: // 综合排序
      return channels.sort((a, b) => {
        const scoreA = calculateScore(a);
        const scoreB = calculateScore(b);
        return scoreB - scoreA; // 降序
      });
    case 1: // 以创建时间 channelCreateTime 降序排序
      return channels.sort((a, b) => b.commentCreateTime - a.commentCreateTime);
    case 2: // 以上次更新时间 channelUpdateTime 降序排序
      return channels.sort((a, b) => b.commentCreateTime - a.commentCreateTime);
    default:
      throw new Error('Invalid sort method');
  }
};

// 计算综合得分的函数
const calculateScore = (channel: IComment): number => {
  return channel.commentLikeCount *2+ 
  channel.commentReplyCount *5+ 
  channel.commentCreateTime*10 / Math.pow(10,channel.commentCreateTime.toString().length);
};

export default sortCommentClick;
