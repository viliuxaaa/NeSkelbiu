package lt.neskelbiu.java.main.posterImg;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterRepository;
import lt.neskelbiu.java.main.poster.PosterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/poster-images")
public class PosterImgController {
	
	private final PosterImgService posterImgService;
	private final PosterService posterService;
	private final PosterRepository posterRepository;
	
	@PostMapping("/{posterid}/image-upload")
	public ResponseEntity<String> uploadImages(@RequestParam(name = "images") MultipartFile[] images,
            @PathVariable(name = "posterid") Long posterId) {
	try {
		Poster poster = posterRepository.findById(posterId).orElse(null);
		if (poster != null) {
			List<MultipartFile> fileList = Arrays.asList(images);
			String result = posterImgService.store(fileList, poster);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poster not found.");
			}
	} catch (IOException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
		}
	}

}
