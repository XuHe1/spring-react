package top.lovelily.react.springreact.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc: Packet
 * Author: xuhe
 * Date: 2019/3/6 4:47 PM
 * Version: 1.0
 */
@Getter
@Setter
public class Packet {
    private String message;
    private String routingKey;
    private PbData data;
    private String _id;
}
