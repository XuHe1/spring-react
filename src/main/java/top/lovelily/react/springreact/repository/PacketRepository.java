package top.lovelily.react.springreact.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import top.lovelily.react.springreact.model.Packet;

/**
 * Desc: repository
 * Author: xuhe
 * Date: 2019/3/11 2:19 PM
 * Version: 1.0
 */
public interface PacketRepository extends ReactiveCrudRepository<Packet, String> {
}
