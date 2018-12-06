//构建聊天chat
window.CHAT={
    socket:null,
    init:function () {
        if(window.WebSocket){
            //如果当前的状态已经连接，那就不需要重复初始化websocket
            if(CHAT.socket!=null&&CHAT.socket!=undefined&&CHAT.socket.readyState==WebSocket.OPEN){
                return false;
            }
            CHAT.socket=new WebSocket('ws://localhost:8088/ws');
            CHAT.socket.onopen=CHAT.wsopen;
            CHAT.socket.onclose=CHAT.wsclose;
            CHAT.socket.onerror=CHAT.wserror;
            CHAT.socket.onmessage=CHAT.wsmessage;
        }
    },
    // 和后端的枚举对应
    CONNECT: 1, 	// 第一次(或重连)初始化连接
    CHAT: 2, 		// 聊天消息
    SIGNED: 3, 		// 消息签收
    KEEPALIVE: 4, 	// 客户端保持心跳
    PULL_FRIEND:5,	// 重新拉取好友
    ISLOGIN:6,	// 重新拉取好友
    // 和后端的 ChatMsg 聊天模型对象保持一致
    getChatMsg: function(senderId, receiverId, msg, msgId){
        return {senderId:senderId,receiverId:receiverId,msg:msg,msgId:msgId};
    },
    // 构建消息 DataContent 模型对象
    getDataContent: function(action, chatMsg, extand){
        return {action:action,chatMsg:chatMsg,extand:extand};
    },
    getUserGlobal:function(){
        var user=localStorage.getItem("userInfo");
        if(user!=null&&user!=undefined){
            return JSON.parse(user);
        }
        return null;
    },
    //第一次连接时触发
    wsopen:function () {
        var user=CHAT.getUserGlobal();
        if(user==null||user==undefined){
            alert(用户没有登录);
            return;
        }
        console.log("websocket连接已建立");
        var chatMsg=CHAT.getChatMsg(user.id,null,null,null);
        var dataContent=CHAT.getDataContent(CHAT.CONNECT,chatMsg,null);
        CHAT.chat(JSON.stringify(dataContent));
        //每18秒发送心跳
        setInterval("CHAT.keeppalive()",18000);
    },
    //关闭的时候触发
    wsclose:function () {
        console.log("连接关闭。。。");
    },
    wserror:function () {
        console.log("发生错误...");
    },
    //接收到信息时触发
    wsmessage:function (e) {
        
    },
    //发送信息
    chat:function(msg){
        if(CHAT.socket!=null&&CHAT.socket!=undefined&&CHAT.socket.readyState==WebSocket.OPEN){
            CHAT.socket.send(msg);
        }else{
            //重连websocket
            CHAT.init();
            setTimeout("CHAT.reChat('" + msg + "')", "1000");
        }
    },
    //消息重新发送
    reChat:function (msg) {
        CHAT.socket.send(msg);
    },
    //发送心跳包
    keeppalive:function () {
        
    }

}