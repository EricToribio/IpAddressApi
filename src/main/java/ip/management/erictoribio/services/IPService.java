package ip.management.erictoribio.services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String addBlock(String Ip) throws UnknownHostException {
        CIDRUtils cidrUtils = new CIDRUtils(Ip);
        String netAddress = cidrUtils.getNetworkAddress();
        IPModel networkIp = new IPModel();
        networkIp.setIp(netAddress);
        networkIp.setStatus("Network");
        ipRepo.save(networkIp);
        String brodcastAddress = cidrUtils.getBroadcastAddress();
        IPModel brodIp = new IPModel();
        brodIp.setIp(brodcastAddress);
        brodIp.setStatus("Brodcast");
        ipRepo.save(brodIp);
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
                Optional<IPModel> possibleIp = ipRepo.findByip(newIp);
                if (possibleIp.isPresent()){
                    return"Ip is already in network" ;
                } 
                IPModel activeIp = new IPModel();
                activeIp.setIp(newIp);
                activeIp.setStatus("Active");
                ipRepo.save(activeIp);
            } else {
                ip[ip.length - 1] = "0";
                Integer checkIp = Integer.parseInt(ip[ip.length - 2]) + 1;
                ip[ip.length - 2] = checkIp.toString();
                newIp = String.join(".", ip);
                if (newIp.equals(brodcastAddress)) {
                    System.out.println("here");
                    break;
                }
                Optional<IPModel> possibleIp = ipRepo.findByip(newIp);
                if (possibleIp.isPresent()){
                    return"Ip is already in network" ;
                } 
                IPModel activeIp = new IPModel();
                activeIp.setIp(newIp);
                activeIp.setStatus("Active");
                ipRepo.save(activeIp);
            }
            System.out.println(newIp);
        }
        return "Success";
    }

    // --------------Acquire a single ip ----------------------------//
    public String acquireIp(String ip){
        Optional<IPModel> possibleIp = ipRepo.findByip(ip);
        if (!possibleIp.isPresent()){
            return"Ip is not in network" ;
        } 
        
        IPModel foundIp = possibleIp.get();
        if ( foundIp.getStatus().equals("Network")){
            return foundIp.getIp() + " is the Network Address and can't be Acquired";
        }
        if ( foundIp.getStatus().equals("Brodcast")){
            return foundIp.getIp() + " is the Brodcast Address and can't be Acquired";
        }
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
        if ( foundIp.getStatus().equals("Network")){
            return foundIp.getIp() + " is the Network Address and can't be set to Active";
        }
        if ( foundIp.getStatus().equals("Brodcast")){
            return foundIp.getIp() + " is the Brodcast Address and can't be set to Active";
        }
        if (foundIp.getStatus().equals("Active")){
            return foundIp.getIp() + " is not Acquired yet";
        }
        foundIp.setStatus("Active");
        ipRepo.save(foundIp);
        return (foundIp.getIp() + " " + foundIp.getStatus());
    }

}
