package lt.neskelbiu.java.main.posterImg;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Poster Image Controller")
@RequestMapping("/api/v1/images/poster")
public class PosterImgController {
	
	private final PosterImgService posterImgService;
	private final PosterService posterService;

	@Operation(
			summary = "Used for getting all poster's image in array",
			description = "With this endpoint you get all poster's image. You need to provide poster."
	)
	@GetMapping("/get/{posterId}")
	public ResponseEntity<List<byte[]>> getImages(@PathVariable Long posterId) {
		List<byte[]> imageList = posterImgService.getImageList(posterId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(imageList);
	}

	@Operation(
			summary = "Used for getting single poster's image",
			description = "With this endpoint you get single poster's image. You need to provide poster id and position of " +
					"the image in the poster"
	)
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

	@Operation(
			summary = "Used for uploading user's poster image",
			description = "With this endpoint you delete user's image. You need to provide poster id. In request param you " +
					"need to provide image param called \"image\" and give image as a file. This is protected path, for " +
					"authenticated users only."
	)
	@PostMapping("/{userId}/{posterId}/upload")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<String> uploadImages(
			@RequestParam(name = "image") MultipartFile[] images,
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

	@Operation(
			summary = "Used for deleting user's poster image",
			description = "With this endpoint you delete user's poster image. You need to provide poster id and position of image in the poster. This is protected path, for authenticated users only."
	)
	@DeleteMapping("/{userId}/{posterId}/{posterImgPosition}")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<String> deleteImage(
			@PathVariable Long posterId,
			@PathVariable Long posterImgPosition
	){
		PosterImg posterImg = posterImgService.getImage(posterId, posterImgPosition);
		try{
			posterImgService.delete(posterImg);
			return ResponseEntity.ok("Image deleted successfully.");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image.");
		}
	}
}
