package org.sang.provider.Controller;

import com.netflix.discovery.shared.Application;
import org.sang.provider.Entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping(value = "/hello")
    public String index() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("hello-service");
        for (ServiceInstance instance : instances) {
            logger.info("/hello,host:" + instance.getHost() + ",service_id:" + instance.getServiceId());
            System.out.println("/hello,host:" + instance.getHost() + ",service_id:" + instance.getServiceId());
        }
        return "Hello World My First Cloud";

    }
    @GetMapping(value = "/providerSayHello")
    public String providerSayHello(){
        List<ServiceInstance> instances = discoveryClient.getInstances("HELLO-SERVICE");

        for (ServiceInstance instance : instances) {
            System.out.println(instance.getHost());

        }

        return "providerSayHello___GET方式传递参数的两种方式";
    }
    /**
     * 返回一个实体对象
     */

    @GetMapping(value = "/providerBook")
    public Book providerBook(){
        Book book = new Book("三国演义",56L,21156440889L);
        return book;
    }



    @PostMapping(value = "/providerBook2")
    public Book providerBook2(@RequestBody Book book){
        Book book1 = new Book();
        book1.setName(book.getName());
        book1.setPrice( book.getPrice());
        book1.setNum(20L);

        System.out.println("Post传参数:"+book.toString());
        return book1;
    }
}
