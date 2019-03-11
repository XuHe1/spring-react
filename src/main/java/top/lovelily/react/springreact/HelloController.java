package top.lovelily.react.springreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.lovelily.react.springreact.model.Packet;
import top.lovelily.react.springreact.model.PbData;
import top.lovelily.react.springreact.repository.PacketRepository;

import java.util.*;

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

    @Autowired
    private  MongoTemplate template;

    @Autowired
    private PacketRepository packetRepository;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/react")
    public Mono<Packet> change() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("begin....");

        System.out.println("finished....");
        return Mono.just(new Packet());
    }


    @GetMapping("/flux")
    public Flux<Packet> change1() {
        System.out.println("begin....");
        // async
        Flux<Packet> packetFlux = getData();
        // async
        // packetFlux.map(Packet::get_id).subscribe(System.out::println);
        System.out.println("finished....");

        return packetFlux;
    }


    @GetMapping("/add")
    public Flux<Packet> add() {
        System.out.println("begin....");
        // async
        Flux<Packet> packetFlux = save();
        // async
        packetFlux.map(Packet::get_id).subscribe(System.out::println);
        System.out.println("finished....");
        return Flux.just(new Packet());
    }

    @GetMapping("/add_sync")
    public List<Packet> addSyn() {
        System.out.println("begin....");
        saveAsyn();
        System.out.println("finished....");
        return null;
    }

    @GetMapping("/show")
    public List<Packet> show() {
        System.out.println("begin....");
        List<Packet> packetList = template.findAll(Packet.class);
        System.out.println(packetList.size());
        System.out.println("finished....");
        return packetList;
    }

    private Flux<Packet> save() {
        List<Packet> packetList = new ArrayList<>();
        for (int i = 0; i < 10000; i ++) {
            Packet packet = new Packet();
            packet.setMessage("hello world.");
            packet.set_id(UUID.randomUUID().toString());
            packet.setRoutingKey("data-server.test.1");
            PbData pbData = new PbData();
            packet.setData(pbData);
            packetList.add(packet);
        }

        return  packetRepository.saveAll(packetList);

    }

    private Collection<Packet> saveAsyn() {
        List<Packet> packetList = new ArrayList<>();
        for (int i = 0; i < 100000; i ++) {
            Packet packet = new Packet();
            packet.setMessage("hello world.");
            packet.set_id(UUID.randomUUID().toString());
            packet.setRoutingKey("data-server.test.1");
            PbData pbData = new PbData();
            packet.setData(pbData);
            packetList.add(packet);
        }

        return template.insertAll(packetList);
    }



    private Flux<Packet> getData() {
        Packet packet = new Packet();
//        packet.setMessage("hello world.");
//        packet.set_id("as1Ids23sasds");
//        packet.setRoutingKey("data-server.test.1");
//        PbData pbData = new PbData();
//        packet.setData(pbData);

      //  packet =  mongoTemplate.insert(packet);
       // Packet packet1 = mongoTemplate.findOne(Query.query(where("routingKey").is("data-server.test.1")), Packet.class);

//        Mono<Packet> packetMono = mongoTemplate.insert(packet);

        Flux<Packet> packetFlux  = packetRepository.findAll();
        // Flux<Packet> packetFlux = mongoTemplate.findAll(Packet.class).log();

        // Mono<Packet> packetMono =  mongoTemplate.findOne(Query.query(where("routingKey").is("data-server.test.1")), Packet.class).log();
        return packetFlux;

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
