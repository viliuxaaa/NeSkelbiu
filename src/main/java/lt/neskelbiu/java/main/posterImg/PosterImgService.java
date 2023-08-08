package lt.neskelbiu.java.main.posterImg;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.exceptions.PosterImgNotFoundException;
import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PosterImgService {
	
	private final PosterImgRepository posterImgRepository;
	private final PosterService posterService;

	public List<byte[]> getImageList(Long posterId) {
		Poster poster = posterService.findById(posterId);
		return poster.getPosterImg().stream()
				.map(PosterImg::getData)
				.toList();
	}

	public PosterImg getImage(Long posterId, Long posterImgPosition) {
		Poster poster = posterService.findById(posterId);
		return poster.getPosterImg().stream()
				.filter(img -> Objects.equals(img.getPosition(), posterImgPosition))
				.findFirst()
				.orElseThrow(() -> new PosterImgNotFoundException("Image not found with id " + posterImgPosition));
	}

	public String store(List<MultipartFile> images, Poster poster) throws IOException {
	    List<PosterImg> posterImages = posterImgRepository.findAllByPosterId(poster.getId());

		int posterImagesListSize = posterImages.size();
		int imagesListSize = images.size();

		if (posterImagesListSize + imagesListSize > 6)
			throw (new IOException());

		List<Long> positionsThatExists = new ArrayList<>();
		posterImages.stream().forEach(image ->
				positionsThatExists.add(image.getPosition())
		);

		long position = 0;
		int count = 0;
		do {
			if (!positionsThatExists.contains(position)) {
				PosterImg posterImg = posterImgBuilder(images.get(count), poster, position);
				posterImgRepository.save(posterImg);
				count++;
			}
			position++;
		} while (count < imagesListSize);

		return String.format("Ikeltos %d nuotraukos", imagesListSize);
	}
	public PosterImg posterImgBuilder(MultipartFile file, Poster poster, Long position) throws IOException {
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		 PosterImg posterImg = PosterImg.builder()
				 .position(position)
				 .name(fileName)
				 .type(file.getContentType())
				 .data(file.getBytes())
				 .poster(poster)
				 .build();
		 return posterImg;
	}

	public void delete(PosterImg posterImg) {
		posterImgRepository.deleteById(posterImg.getId());
	}
}
