package lt.neskelbiu.java.main.user;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String firstname;
	private String lastname;
	
	@Column(nullable = false, unique = true)
	private String email;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserImg userImg;
	
	
//	@Column(name = "profile_img")
//	private Blob profileimg;
//	
//	@OneToMany(mappedBy="user")
//    private Set<Advert> advert;
}
