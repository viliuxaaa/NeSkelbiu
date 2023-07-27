package lt.neskelbiu.java.main.posterImg;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.neskelbiu.java.main.poster.Poster;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "poster_img")
public class PosterImg {

	  @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;

	  private String name;

	  private String type;

	  @Lob
	  @Column(length = 1048576)
	  private byte[] data;
	
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "poster_id")
	  public Poster poster;
}
