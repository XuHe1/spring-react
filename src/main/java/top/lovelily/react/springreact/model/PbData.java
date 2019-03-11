package top.lovelily.react.springreact.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Desc: PbData
 * Author: xuhe
 * Date: 2019/3/6 4:48 PM
 * Version: 1.0
 */
@Getter
@Setter
public class PbData implements Serializable {
    	private String routingKey;
        private String msgMeta;
        private Gps gps;
        private long time;
}
