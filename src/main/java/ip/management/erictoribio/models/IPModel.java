package ip.management.erictoribio.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "ips")
public class IPModel {

    @Id
    @NotNull
    public String ip;

    @NotNull
    public String status;

    
}
