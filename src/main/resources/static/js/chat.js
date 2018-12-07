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
        //action:action,chatMsg:chatMsg,extand:extand
        //接收到信息
        var dataContent=JSON.parse(e.data);
        var action=dataContent.action;
        if(action==CHAT.ISLOGIN){
            console.log("用户在其他地方登录")
            return false;
        }
        //重新拉取好友列表
        if(action==CHAT.PULL_FRIEND){
            return false;
        }
        //{senderId:senderId,receiverId:receiverId,msg:msg,msgId:msgId}
        // 获取聊天消息模型，渲染接收到的聊天记录
        var chatMsg = dataContent.chatMsg;
        //保存聊天记录 senderId
        chatmodal.saveUserChatHistory(chatMsg.receiverId,chatMsg.senderId,chatMsg.msg,2);
        var unRead=false;
        if(chatmodal.isNotNull(chatmodal.chatWindow.id)){
            if(chatmodal.chatWindow.id==chatMsg.senderId){
                var userChatHistory=chatmodal.getUserChatHistory(chatMsg.receiverId,chatMsg.senderId);
                if(chatmodal.isNotNull(userChatHistory)){
                    chatmodal.userChatHistoryList=userChatHistory;
                }
                unRead=true;
            }
        }
        //保存聊天快照
        chatmodal.saveUserChatSnapshot(chatMsg.receiverId,chatMsg.senderId,chatMsg,unRead);
        //var scroll_div = document.getElementById("office_text");
        //scroll_div.scrollTop = scroll_div.scrollHeight;

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
        
    },
    //单个聊天记录的对象 1是我发的，2是他发的
    chatHistory:function (userId,friendId,msg,flag) {
        return {userId:userId,friendId:friendId,msg:msg,flag:flag};
    },
    //快照对象 isRead用于判断是否已读
    chatSnapshot:function (userId,friendId,msg,isRead) {
        return {userId:userId,friendId:friendId,msg:isRead};
    }

}