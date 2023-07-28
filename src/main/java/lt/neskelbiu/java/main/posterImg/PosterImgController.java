package lt.neskelbiu.java.main.posterImg;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lt.neskelbiu.java.main.exceptions.PosterImgNotFoundException;
import lt.neskelbiu.java.main.exceptions.PosterNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterRepository;
import lt.neskelbiu.java.main.poster.PosterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/poster/images")
public class PosterImgController {
	
	private final PosterImgService posterImgService;
	private final PosterService posterService;

	@GetMapping("/get/{posterId}")
	public ResponseEntity<List<byte[]>> getImages(@PathVariable Long posterId) {
		List<byte[]> imageList = posterImgService.getImageList(posterId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(imageList);
	}

	@GetMapping("/get/{posterId}/{posterImgPosition}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long posterId, @PathVariable Long posterImgPosition) {
		PosterImg posterImg = posterImgService.getImage(posterId, posterImgPosition);

		byte[] imageData = posterImg.getData();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(posterImg.getType()));

		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(imageData);
	}
	
	@PostMapping("/images/{posterId}/upload")
	public ResponseEntity<String> uploadImages(
			@RequestParam(name = "images") MultipartFile[] images,
            @PathVariable Long posterId
	) {
		Poster poster = posterService.findById(posterId);
		try {
			List<MultipartFile> imagesList = Arrays.asList(images);
			String result = posterImgService.store(imagesList, poster);
			return ResponseEntity.ok(result);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
		}
	}

	@DeleteMapping("/images/{posterId}/{posterImgPosition}")
	public ResponseEntity<String> deleteImage(@PathVariable Long posterId, @PathVariable Long posterImgPosition){
		PosterImg posterImg = posterImgService.getImage(posterId, posterImgPosition);
		try{
			posterImgService.delete(posterImg);
			return ResponseEntity.ok("Image deleted successfully.");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image.");
		}
	}
}
