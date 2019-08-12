//页面加载后判断是否登录 显示不同的欢迎页
$(function () {
    //发送ajax 判断是否登录
      $.ajax({
         url:"http://127.0.0.1:8081/sso/checkLogin",
          success:function (data) {
             console.log(data)
             var html="";
              if(data!=null){
               html=data.nickname+",您好，欢迎来到<b><a>ShopCZ商城</a></b><a href='http://127.0.0.1:8081/sso/logout' >注销</a>";
              }else{
               html="[<a onclick='login()'>登录</a>][<a href=\"http://127.0.0.1:8081/sso/toregister\">注册</a>]";
              }
              $("#pid").html(html);
          },
          dataType:"jsonp",
          jsonpCallback:"method"
      });
});
//登录后跳转回点击时页面
function login() {
    //获取当前的url
   var returnUrl=location.href;
    //对请求地址进行编码 这样到后台就会编码utf-8   否则会乱码
    returnUrl=encodeURIComponent(returnUrl);
    location.href="http://127.0.0.1:8081/sso/tologin?returnUrl="+returnUrl;
}