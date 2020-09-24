package com.auth0.samples.authapi.springbootauthupdated.user;

import com.auth0.samples.authapi.springbootauthupdated.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESIDENT_IDFK")
    private Residence residence;
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
