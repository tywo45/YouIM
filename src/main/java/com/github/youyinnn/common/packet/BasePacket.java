package com.github.youyinnn.common.packet;

import com.github.youyinnn.common.MsgType;
import org.tio.core.intf.Packet;
import org.tio.utils.json.Json;

import java.io.UnsupportedEncodingException;

/**
 * @author youyinnn
 */
public class BasePacket extends Packet {

    public static final int HEADER_LENGTH = 5;
    public static final String CHARSET = "utf-8";

    private byte msgType;

    private byte[] msgBody;

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }

    public BasePacket(byte msgType, byte[] msgBody) {
        this.msgType = msgType;
        this.msgBody = msgBody;
    }

    public BasePacket(byte msgType, BaseBody baseBody) {
        this.msgType = msgType;
        try {
            this.msgBody = Json.toJson(baseBody).getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static BasePacket loginRequestPacket(String userId){
        return new BasePacket(MsgType.LOGIN_REQ, new LoginRequestBody(userId));
    }

    public static BasePacket loginResponsePacket(String resultCode, String token) {
        return new BasePacket(MsgType.LOGIN_RESP, new LoginResponseBody(resultCode, token));
    }

    public static BasePacket joinGroupRequestPacket(String group) {
        return new BasePacket(MsgType.JOIN_GROUP_REQ, new JoinGroupRequestBody(group));
    }

    public static BasePacket joinGroupResponsePacket(String resultCode, String msg, String group) {
        return new BasePacket(MsgType.JOIN_GROUP_RESP, new JoinGroupResponseBody(resultCode, msg, group));
    }

    public static BasePacket p2pMsgRequestPacket(String msg, String toUserId) {
        return new BasePacket(MsgType.P2P_REQ, new P2PRequestBody(msg, toUserId));
    }

    public static BasePacket p2pMsgResponsePacket(String msg, String fromUserId) {
        return new BasePacket(MsgType.P2P_RESP, new P2PResponseBody(msg, fromUserId));
    }

    public static BasePacket groupMsgRequestPacket(String msg, String toGroup) {
        return new BasePacket(MsgType.GROUP_MSG_REQ, new GroupMsgRequestBody(msg, toGroup));
    }

    public static BasePacket groupMsgResponsePacket(String msg, String fromUserId, String toGroup) {
        return new BasePacket(MsgType.GROUP_MSG_RESP, new GroupMsgResponseBody(msg, fromUserId, toGroup));
    }

    public static BasePacket heartbeatRequestPacket() {
        return new BasePacket(MsgType.HEART_BEAT_REQ, new BaseBody());
    }
}
