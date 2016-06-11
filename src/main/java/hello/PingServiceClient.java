package hello;

/**
 * Created by Mindaugas Vidmantas on 2016-06-06.
 * email: minvidm@gmail.com, minvidm@ktu.lt
 */

import java.text.SimpleDateFormat;

//        import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetHostResponse;
import hello.wsdl.GetHostRequest;
import hello.wsdl.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class PingServiceClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(PingServiceClient.class);

    /**
     * get Host Response By Name
     * use Web Service
     * use SOAP
     *
     *
     * **/
    public GetHostResponse getHostresponseByName(String website) {
        GetHostRequest request = new GetHostRequest();
        request.setName(website);
        log.info("Requesting for " + website);
        GetHostResponse response = (GetHostResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        "http://localhost:9192/ws/hosts.wsdl",
                        request,
                        new SoapActionCallback("http://localhost:9192/ws/GetHostRequest"));
        printResponse(response);
        return response;
    }



    /**
     * get Hostresponse By Name
     *
     *
     * **/
    public void printResponse(GetHostResponse response) {
        Host hostPingReturn = response.getHost();
        if (hostPingReturn!=null)
            log.info("Host ping info " + hostPingReturn.getName() + ", " + hostPingReturn.getPort() + ", " + hostPingReturn.getTimeout());
        //log.info("No response received");
        //if (hostPingReturn..isSuccess()) {
        //}
        //            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //log.info(String.format("%s %s %s°-%s°", format.format(forecast.getDate().toGregorianCalendar().getTime()));
        //log.info("");
    }

}