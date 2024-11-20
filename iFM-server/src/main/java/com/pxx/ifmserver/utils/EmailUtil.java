package com.pxx.ifmserver.utils;

import java.util.Date;
import java.util.Properties;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {
    private static String fromEmail = "3384328633@qq.com";  // 发件人账号
    private static String fromEmailPw = "cajcodnftvlqdbie";  // 发件人密码
    private static String myEmailSMTPHost = "smtp.qq.com";  // 发件邮箱服务器
    private static Properties props;  // 用于参数配置
    private static Session session;  // 用于创建会话对象
    private String vCode;
    public static EmailUtil instance = new EmailUtil();

    /*
     * 获取验证码
     * @return 验证码字符串
     */
    public String getVCode() {
        return vCode;
    }

    /*
     * 构造函数，配置属性
     */
    private EmailUtil() {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);  // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");  // 需要请求认证
        props.setProperty("mail.smtp.ssl.enable", "true");  // 启用 SSL
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  // 设置 SSL 连接类
        props.setProperty("mail.smtp.socketFactory.fallback", "false");  // 设置为不回退
        session = Session.getInstance(props);
        // session.setDebug(true);  // 设置为debug模式, 可以查看详细的发送 log
    }

    /*
     * 构建邮件内容
     * @param 收件人
     * @return 发送邮件内容
     */
    public MimeMessage createMailContent(String toEmail) throws Exception, MessagingException {
        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(fromEmail, "验证码发送系统", "UTF-8"));
        // 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmail));
        // 邮件主题
        message.setSubject("验证码", "UTF-8");
        // 邮件正文
        vCode = VCodeUtill.verifyCode(6);
        message.setContent("欢迎使用iFm!您的验证码是："+vCode+"。", "text/html;charset=UTF-8");
        // 设置发件时间
        message.setSentDate(new Date());
        // 保存设置
        message.saveChanges();
        return message;
    }
    /*
     * 发送邮件
     * @param 收件人
     */
    public void sendEmail(String toEmail) throws Exception {
        Transport transport = session.getTransport();
        transport.connect(fromEmail, fromEmailPw);
        MimeMessage message = createMailContent(toEmail);  // 邮件内容
        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("验证码发送成功！");
        // 关闭连接
        transport.close();
    }
}


