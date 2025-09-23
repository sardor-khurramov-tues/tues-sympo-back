package sympo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendee")
public class AttendeeEntity {

    @Id
    @SequenceGenerator(name = "attendee_id_generator", sequenceName = "attendee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "attendee_id_generator")
    private Long id;

    private String email;

    private String phone;

    @Column(name = "register_time")
    private LocalDateTime registerTime;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String affiliation;

    @Column(name = "job_position")
    private String jobPosition;

    private String degree;

    private String field;

    private String country;

    @Column(name = "pres_title")
    private String presTitle;

    @Column(name = "pres_abstract")
    private String presAbstract;

    private String comments;

}
