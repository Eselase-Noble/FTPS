package org.nobleson.financialtransactionprocessingsystem.models;






import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle the customer entity
 * This is used to manage the customer attributes and functions
 * It handles the Getters and Setters together with All and No arguments constructors
 * @author Nobleson
 * @version 1.0.0
 * @date 03/07/2024
 *
 */
@Data
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long customerId;
    private String surname;
    private String otherName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String address;
    private String city;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
}
