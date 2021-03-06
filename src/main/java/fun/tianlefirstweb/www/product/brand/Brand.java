package fun.tianlefirstweb.www.product.brand;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String websiteUrl;
    private String logoImage;

    @OneToMany(
            mappedBy = "brand",
            orphanRemoval = true,
            fetch = LAZY
    )
    @JsonIgnore
    private List<Lipstick> lipsticks;
}
