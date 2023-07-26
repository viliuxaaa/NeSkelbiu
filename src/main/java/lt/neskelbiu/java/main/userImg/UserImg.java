package lt.neskelbiu.java.main.userImg;

import jakarta.persistence.*;
import lt.neskelbiu.java.main.user.User;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_img")
public class UserImg {
	
	  @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;

	  private String name;

	  private String type;

	  @Lob
	  @Column(length = 1048576)
	  private byte[] data;

	  @OneToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "user_id", referencedColumnName = "id")
	  private User user;
}
