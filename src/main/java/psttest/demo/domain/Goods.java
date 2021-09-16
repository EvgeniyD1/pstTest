package psttest.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

//@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
//@EqualsAndHashCode(exclude = {
//        "users"
//})
//@ToString(exclude = {
//        "users"
//})
@Entity
@Table(name = "m_goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goodName;

    @JsonIgnoreProperties("goods")
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "l_user_goods",
            joinColumns = {@JoinColumn(name = "good_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = Collections.emptySet();
}
