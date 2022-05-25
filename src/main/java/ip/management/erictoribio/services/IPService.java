package ip.management.erictoribio.services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import edazdarevic.commons.net.CIDRUtils;
import ip.management.erictoribio.models.IPModel;
import ip.management.erictoribio.repositories.IPRepository;

@Service
public class IPService {

    @Autowired
    IPRepository ipRepo;

    // --------------Find All Ip addresses in the system---------------------//
    public List<IPModel> getAllIps() {
        return ipRepo.findAll();
    }

    // ----------------Add all ips in CIDR Block----------------------//

    public void addBlock(String Ip) throws UnknownHostException {
        CIDRUtils cidrUtils = new CIDRUtils(Ip);
        String netAddress = cidrUtils.getNetworkAddress();
        String brodcastAddress = cidrUtils.getBroadcastAddress();
        System.out.println(netAddress);
        System.out.println(brodcastAddress);
        String newIp = netAddress;
        while (cidrUtils.isInRange(newIp)) {
            String[] ip = newIp.split("\\.", -1);
            Integer last = Integer.parseInt(ip[ip.length - 1]);

            if (last < 255) {
                Integer checkIp = last + 1;
                ip[ip.length - 1] = checkIp.toString();
                newIp = String.join(".", ip);
                if (newIp.equals(brodcastAddress)) {
                    System.out.println("here");
                    break;
                }
                IPModel activeIp = new IPModel();
                activeIp.setIp(newIp);
                activeIp.setStatus("Active");
                ipRepo.save(activeIp);
                System.out.println(activeIp);

            } else {
                ip[ip.length - 1] = "0";
                Integer checkIp = Integer.parseInt(ip[ip.length - 2]) + 1;
                ip[ip.length - 2] = checkIp.toString();
                newIp = String.join(".", ip);
                if (newIp.equals(brodcastAddress)) {
                    System.out.println("here");
                    break;
                }
                System.out.println(newIp);

            }
        }
    }

    // --------------Acquire a single ip ----------------------------//
    public String acquireIp(String ip){
        Optional<IPModel> possibleIp = ipRepo.findByip(ip);
        if (!possibleIp.isPresent()){
            return"Ip is not in network" ;
        } 
        
        IPModel foundIp = possibleIp.get();
        if (foundIp.getStatus().equals("Acquired")){
        return foundIp.getIp() + " is already Acquired";
        }
        foundIp.setStatus("Acquired");
        ipRepo.save(foundIp);
        return (foundIp.getIp() + " " + foundIp.getStatus());
    }

    //----------------------Release A single ip-----------------------//
    public String releaseIp(String ip){
        Optional<IPModel> possibleIp = ipRepo.findByip(ip);
        if (!possibleIp.isPresent()){
            return"Ip is not in network" ;
        } 
        IPModel foundIp = possibleIp.get();
        if (foundIp.getStatus().equals("Active")){
            return foundIp.getIp() + " is not Acquired yet";
        }
        foundIp.setStatus("Active");
        ipRepo.save(foundIp);
        return (foundIp.getIp() + " " + foundIp.getStatus());
    }

}
