package com.fanfte.rpc.model.packet;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by tianen on 2018/10/10
 *
 * @author fanfte
 * @date 2018/10/10
 **/
@Data
public class MessageCache {

    private Map<String, Object> messageMap;

}
