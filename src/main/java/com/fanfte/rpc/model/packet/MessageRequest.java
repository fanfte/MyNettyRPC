package com.fanfte.rpc.model.packet;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
@Data
public class MessageRequest implements Serializable {

    private String messageId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parametersVal;

    @Override
    public String toString() {
        return "MessageRequest{" +
                "messageId='" + messageId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parametersVal=" + Arrays.toString(parametersVal) +
                '}';
    }
}
