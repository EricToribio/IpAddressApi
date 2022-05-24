package ip.management.erictoribio.services;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.StringUtils;
import edazdarevic.commons.net.CIDRUtils;
import ip.management.erictoribio.models.IPModel;
import ip.management.erictoribio.repositories.IPRepository;

@Service
public class IPService {

    @Autowired IPRepository ipRepo;

    //--------------Find All Ip addresses in the system---------------------//
    public List<IPModel> getAllIps(){
        return ipRepo.findAll();
    }

    //----------------Add all ips in CIDR Block----------------------//

    public void addBlock(String Ip) throws UnknownHostException{
        CIDRUtils cidrUtils = new CIDRUtils(Ip);
        String netAddress =cidrUtils.getNetworkAddress();
        String brodAddress = cidrUtils.getBroadcastAddress();
        for (int i = 1 ; i < 255 ; i++){
            String[] ip = netAddress.split("\\.",-1);
            Integer checkIp = Integer.parseInt(ip[ip.length-1]) + i;
            ip[ip.length-1] =  checkIp.toString();
            String newIp = String.join(".", ip);
            if (cidrUtils.isInRange(newIp)){
                
                System.out.println(newIp);
            }
        }
    }

}
