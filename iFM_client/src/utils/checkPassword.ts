export function checkPassword(p1:string,p2:string): boolean {
 // 检查两个密码是否相等
   if (p1 !== p2) {
     return false;
   }
 
   // 检查密码长度是否在6到30位之间
   if (p1.length < 6 || p1.length > 30) {
     return false;
   }
 
   // 检查密码是否至少包含两类字符（数字，英文字母）
   const hasNumber = /\d/.test(p1);
   const hasLetter = /[a-zA-Z]/.test(p1);
   const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(p1);
   const hasInvalidChar = /[^a-zA-Z0-9!@#$%^&*(),.?":{}|<>]/.test(p1);
 
   // 至少包含数字和字母，并且不包含非法字符
   if (!hasNumber || !hasLetter || hasInvalidChar) {
     return false;
   }
 
   // 检查是否至少包含两类字符（数字、字母、特殊符号）
  if (!(hasNumber && hasLetter) && !(hasNumber && hasSpecialChar) && !(hasLetter && hasSpecialChar)) {
    return false;
  }
 
   // 如果所有检查都通过，则返回true
   return true;
}