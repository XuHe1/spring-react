package top.lovelily.react.springreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.lovelily.react.springreact.model.Packet;
import top.lovelily.react.springreact.model.PbData;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Desc: HelloController
 * Author: xuhe
 * Date: 2019/2/19 3:12 PM
 * Version: 1.0
 * todo: 观察者模式
 */
@RestController
@RequestMapping
public class HelloController {
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/react")
    public Mono<List<String>> change() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("begin....");
        Mono<List<String>> stringMono = Mono.just(getData());
        // event-driven, todo: asynchornize?
        stringMono.subscribe(this::send);
        stringMono.doOnNext(this::logError);
        System.out.println("finished....");
        return stringMono;
    }


    @GetMapping("/flux")
    public Flux<Integer> change1() {
        System.out.println("begin....");
        Flux<Integer> stringMono = Flux.just(1);
        System.out.println("finished....");
        return stringMono;
    }



    private List<String> getData() {
        Packet packet = new Packet();
//        packet.setMessage("hello world.");
//        packet.set_id("as1Ids23sasds");
//        packet.setRoutingKey("data-server.test.1");
//        PbData pbData = new PbData();
//        packet.setData(pbData);

      //  packet =  mongoTemplate.insert(packet);
       // Packet packet1 = mongoTemplate.findOne(Query.query(where("routingKey").is("data-server.test.1")), Packet.class);

//        Mono<Packet> packetMono = mongoTemplate.insert(packet);
        Mono<Packet> result =  mongoTemplate.findOne(Query.query(where("routingKey").is("data-server.test.1")), Packet.class).log();
        System.out.println(result.subscribe(System.out::println));
        List<String> stringList = new ArrayList<String>();
        stringList.add("Hello World.");
        stringList.add("Hello there.");
        return stringList;
    }

    private  int send(List<String> stringList) {
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // int b = 10/0;
       for (String str : stringList) {
           System.out.println(String.format("Send[%s]", str));
       }
       return 1;
    }

    private void logError(List<String> stringList) {
        for (String str : stringList) {
            System.out.println(String.format("INFO[%s]", str));
        }
    }

}
