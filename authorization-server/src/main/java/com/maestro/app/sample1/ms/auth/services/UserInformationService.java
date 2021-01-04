package com.maestro.app.sample1.ms.auth.services;

import com.maestro.app.sample1.ms.auth.entities.User;
import com.maestro.app.sample1.ms.auth.repositories.UserRepository;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.queue.QueueLogConnectEvt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Component
public class UserInformationService {
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private Parser parser;
     @Autowired
     private RabbitTemplate rabbitTemplate;
     @Autowired
     private Exchange eventExchange;;

     private static final String UNKNOWN = "NA";

     @Transactional
     public void verifyUserInformation(User user, HttpServletRequest request) {
         String ip = extractIp(request),
                deviceDetails = getDeviceDetails(request.getHeader("user-agent"));
         if (deviceDetails.length() > 512) deviceDetails = deviceDetails.substring(0, 508) + "...";
         ClientLocation client =new ClientLocation(UNKNOWN, UNKNOWN, UNKNOWN);

         // form LogConnects
         QueueLogConnectEvt evt = new QueueLogConnectEvt();
         evt.setIduser(user.getId());
         evt.setUsername(user.getUsername());
         evt.setIp_address(ip);
         evt.setCountryCode(client.getCountryCode());
         evt.setCountryName(client.getCountry());
         evt.setCity(client.getCity());
         evt.setDeviceDetails(deviceDetails);

         this.rabbitTemplate.convertAndSend(this.eventExchange.getName(), ("events.connects." + user.getUsername()), evt);

         userRepository.updateLastLogon(user.getId(), new Date());
         userRepository.flush();
     }

     private String extractIp(HttpServletRequest request) {
         String clientIp;
         String clientXForwardedForIp = request.getHeader("x-forwarded-for");
         if (nonNull(clientXForwardedForIp)) {
             clientIp = parseXForwardedHeader(clientXForwardedForIp);
         } else {
             clientIp = request.getRemoteAddr();
         }

         return clientIp;
     }

     private String parseXForwardedHeader(String header) {
         return header.split(" *, *")[0];
     }

     private String getDeviceDetails(String userAgent) {
         String deviceDetails = UNKNOWN;
         Client client = parser.parse(userAgent);
         if (Objects.nonNull(client)) {
             if (client.userAgent != null && client.os != null) {
                 deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
                         " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
             }
         }

         return deviceDetails;
     }

     @Data
     @AllArgsConstructor
     private static class ClientLocation {
         private String country;
         private String countryCode;
         private String city;
     }
}
