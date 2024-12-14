
// 定义频道数据结构
interface IPost {
   postLikeCount: number,
   postCommentCount:  number,
   postCollectionCount: number,
   postCreateTime: number,
}


export const sortpostList = (postList: IPost[], sortMethod: number): any[] => {
  switch (sortMethod) {
    case 0: // 综合排序
      return postList.sort((a, b) => {
        const scoreA = calculateScore(a);
        const scoreB = calculateScore(b);
        return scoreB - scoreA; // 降序
      });
    case 1: // 以创建时间 postCreateTime 降序排序
      return postList.sort((a, b) => b.postCreateTime - a.postCreateTime);
    case 2: // 以创建时间 postCreateTime 升序排序
      return postList.sort((a, b) =>  a.postCreateTime - b.postCreateTime );
    case 3: // 以收藏量 postCollectionCount 降序排序
      return postList.sort((a, b) => b.postCollectionCount - a.postCollectionCount);
	default:
      throw new Error('Invalid sort method');
  }
};

// 计算综合得分的函数
const calculateScore = (post: IPost): number => {
  return post.postLikeCount * 2 + 
  post.postCollectionCount * 8 +
  post.postCommentCount * 10 +
  post.postCreateTime * 10 / Math.pow(10,post.postCreateTime.toString().length);
};

export default sortpostList;
