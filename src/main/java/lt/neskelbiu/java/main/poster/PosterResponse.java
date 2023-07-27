package lt.neskelbiu.java.main.poster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PosterResponse {
    private Long posterid;
    private Long userId;
    private String postName;
    private String description;
    private Map<Long, String> images;
    private Long price;
    private CategoryA categoryA;
    private CategoryB categoryB;
    private Status status;
    private Long phoneNumber;
    private String city;
    private String website;
    private String videoLink;
    private String createdAt;
    private String updatedAt;
}
