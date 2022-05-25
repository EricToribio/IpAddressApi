package ip.management.erictoribio.controllers;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ip.management.erictoribio.models.IPModel;
import ip.management.erictoribio.services.IPService;

@RestController
public class IpController {

    @Autowired IPService ipService;

     // ----------------Add all ips in CIDR Block----------------------//
    @PostMapping("/api/add/block")
    public String addBlock(@RequestBody IPModel ip) throws UnknownHostException {
        System.out.println(ip.getIp());
    return ipService.addBlock( ip.getIp());
    }

    // --------------Find All Ip addresses in the system---------------------//
    @GetMapping("/api/all/ips")
    public List<IPModel> allIps(){
        return ipService.getAllIps();
    }

    // --------------Acquire a single ip ----------------------------//
    @PutMapping("/api/acquire/ip")
    public String acquireIp(@RequestBody IPModel ip){
        String potentialIp = ipService.acquireIp(ip.getIp());
        return potentialIp;
    }

     //----------------------Release A single ip-----------------------//
    @PutMapping("/api/release/ip")
    public String releaseIp(@RequestBody IPModel ip){
        String potentialIp = ipService.releaseIp(ip.getIp());
        return potentialIp;
    }
}
