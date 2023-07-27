package lt.neskelbiu.java.main.poster;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.posterImg.PosterImg;
import lt.neskelbiu.java.main.user.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "poster")
public class Poster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String postName;
	
	@Enumerated(EnumType.STRING)
	private CategoryA categoryA;

    @Enumerated(EnumType.STRING)
    private CategoryB categoryB;

//    @Enumerated(EnumType.STRING)
//    private CategoryC categoryC;
//
//    @Enumerated(EnumType.STRING)
//    private CategoryModel categoryModel;
	
	@Column(length = 1024)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Status status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    
    private Long phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private City city;
    
    @Column(nullable = true)
    private String website;
    
    @Column(nullable = true)
    private String videoLink;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NonNull
    private Long price;
    
    @JsonIgnore
    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL)
    private List<PosterImg> posterImg;
}
