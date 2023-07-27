package lt.neskelbiu.java.main.posterImg;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.Poster;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosterImgService {
	
	private final PosterImgRepository posterImgRepository;

	public String store(List<MultipartFile> images, Poster poster) throws IOException {
	    List<PosterImg> posterImages = posterImgRepository.findAllByPosterId(poster.getId());
		int posterImagesListSize = posterImages.size(); //4
		int imagesListSize = images.size();			//3

		if (posterImagesListSize + imagesListSize > 6)
			throw (new IOException());

		for (MultipartFile image : images) {
			PosterImg posterImg = posterImgBuilder(image, poster);
			posterImgRepository.save(posterImg);
		}
		return String.format("Ikeltos %d nuotraukos", imagesListSize);
	}
	public PosterImg posterImgBuilder(MultipartFile file, Poster poster) throws IOException {
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		 PosterImg posterImg = PosterImg.builder()
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
