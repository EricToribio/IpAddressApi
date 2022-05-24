package ip.management.erictoribio.controllers;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ip.management.erictoribio.services.IPService;

@RestController
@CrossOrigin(origins = "*")
public class IpController {

    @Autowired IPService ipService;

    @PostMapping("/api/add/block")
    public void addBlock(@RequestBody String ip) throws UnknownHostException {
        String newIp = ip.replace("%2F", "/").replace("ip=", "");
    ipService.addBlock( newIp);
    }
}
