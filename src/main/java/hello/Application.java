package hello;

/**
 * Created by Mindaugas Vidmantas on 2016-06-06.
 * email: minvidm@gmail.com, minvidm@ktu.lt
 */

import hello.wsdl.GetHostResponse;
import hello.wsdl.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
@PropertySource(value="classpath:config.properties")
public class Application {


    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Value("#{'${server.name}'.split(',')}")
    private List<String> servers;
    private List<Host> hostList = new ArrayList<Host>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * Bean for Host Response Result Collection handling/exposing
     *
     * **/
    @Bean
    CommandLineRunner lookup(PingServiceClient pingServiceClient) {


        return args -> {


            pingHostListByResponseTime(pingServiceClient, this.hostList, this.servers);
            sortHostListByResponseTime(this.hostList);

            for (Host temp : hostList)
                log.info("HOST: " + temp.getName() + ", \t PING " + temp.getTimeout() + ", \t PORT:" +  temp.getPort());


            //sortHostListByResponseTime(webSite);
        };
    }


    /**
     * Case of Functional Decomposition
     * sort Host List by ResponseTime
     * */

    List<Host> sortHostListByResponseTime(List<Host> hostList){
        Collections.sort(hostList, new Comparator<Host>() {
            public int compare(Host o1, Host o2) {
                return Long.compare(o1.getTimeout(), o2.getTimeout());
            }
        });

        return hostList;
    }

    /**
     * Case of Functional Decomposition
     * get Host Response Time
     * */

    List<Host> pingHostListByResponseTime(PingServiceClient pingServiceClient, List<Host> hostList, List<String> servers){
        for (int i=0; i < servers.size(); i++){
            GetHostResponse response = pingServiceClient.getHostresponseByName(servers.get(i));
            hostList.add(response.getHost());
        }
        return hostList;
    }


}
