package hello;

/**
 * Created by Mindaugas Vidmantas on 2016-06-06.
 * email: minvidm@gmail.com, minvidm@ktu.lt
 */


        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PingServiceConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("hello.wsdl");
        return marshaller;
    }

    @Bean
    public PingServiceClient pingServiceClient(Jaxb2Marshaller marshaller) {
        PingServiceClient client = new PingServiceClient();
        client.setDefaultUri("http://localhost:9192/ws");//
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
