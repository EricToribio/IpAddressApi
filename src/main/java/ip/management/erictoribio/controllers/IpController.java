package ip.management.erictoribio.controllers;

import java.net.UnknownHostException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ip.management.erictoribio.models.IPModel;
import ip.management.erictoribio.services.IPService;

@RestController
@CrossOrigin(origins = "*")
public class IpController {

    @Autowired IPService ipService;

     // ----------------Add all ips in CIDR Block----------------------//
    @PostMapping("/api/add/block")
    public void addBlock(@RequestBody String ip) throws UnknownHostException {
        String newIp = ip.replace("%2F", "/").replace("ip=", "");
    ipService.addBlock( newIp);
    }

    // --------------Find All Ip addresses in the system---------------------//
    @GetMapping("/api/all/ips")
    public List<IPModel> allIps(){
        return ipService.getAllIps();
    }

    // --------------Acquire a single ip ----------------------------//
    @PostMapping("/api/acquire/ip")
    public String acquireIp(@RequestBody String ip){
        String newIp = ip.replace("ip=", "");
        System.out.println(newIp);
        String potentialIp = ipService.acquireIp(newIp);
        return potentialIp;
    }

     //----------------------Release A single ip-----------------------//
    @PostMapping("/api/release/ip")
    public String releaseIp(@RequestBody String ip){
        String newIp = ip.replace("ip=", "");
        System.out.println(newIp);
        String potentialIp = ipService.releaseIp(newIp);
        return potentialIp;
    }
}
