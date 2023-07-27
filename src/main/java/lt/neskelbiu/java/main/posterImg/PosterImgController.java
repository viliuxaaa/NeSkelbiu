package lt.neskelbiu.java.main.posterImg;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lt.neskelbiu.java.main.exceptions.PosterNotFoundException;
import lt.neskelbiu.java.main.userImg.message.ResponseMessage;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/images/{posterId}")
	public ResponseEntity<List<byte[]>> getImages(@PathVariable Long posterId) {
		Poster poster = posterRepository.findById(posterId)
				.orElseThrow(() -> new PosterNotFoundException("Poster not found with id" + posterId));

		List<byte[]> imageList = poster.getPosterImg().stream()
				.map(PosterImg::getData)
				.toList();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(imageList);
	}
	
	@PostMapping("/{posterId}/image-upload")
	public ResponseEntity<String> uploadImages(
			@RequestParam(name = "images") MultipartFile[] images,
            @PathVariable Long posterId
	) {
		Poster poster = posterRepository.findById(posterId)
				.orElseThrow(() -> new PosterNotFoundException("Poster not found with id" + posterId));
		try {
			List<MultipartFile> imagesList = Arrays.asList(images);
			String result = posterImgService.store(imagesList, poster);
			return ResponseEntity.ok(result);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
		}
	}

//	@DeleteMapping("/{id}/image-delete")
//	public ResponseEntity<ResponseMessage> deleteImage(@PathVariable Long id) {
//		var user = userService.findById(id);
//		var userImg = userImgService.getUserImage(user);
//
//		String message = "Deleted the image successfully: " + userImg.getName();
//		userImgService.deleteById(userImg.getId());
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//	}
}
