package lt.neskelbiu.java.main.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.token.RefreshToken;
import lt.neskelbiu.java.main.token.Token;
import lt.neskelbiu.java.main.userImg.UserImg;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_user")
public class User implements UserDetails {
	
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

	private boolean isNotLocked;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE) //cascade = CascadeType.REMOVE)
	private UserImg userImg;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Token> tokenList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<RefreshToken> refreshTokenList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Poster> posterList;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isNotLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
