package com.fanfte.rpc.model.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
@Data
public class MessageResponse implements Serializable {

    private String messageId;

    private String error;

    private Object resultDesc;

    @Override
    public String toString() {
        return "MessageResponse{" +
                "messageId='" + messageId + '\'' +
                ", error='" + error + '\'' +
                ", resultDesc=" + resultDesc +
                '}';
    }
}
