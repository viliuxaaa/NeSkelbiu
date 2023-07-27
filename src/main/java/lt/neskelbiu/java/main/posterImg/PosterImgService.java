package lt.neskelbiu.java.main.posterImg;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.Poster;

@Service
@RequiredArgsConstructor
public class PosterImgService {
	
	private final PosterImgRepository posterImgRepository;

	public String store(List<MultipartFile> files, Poster poster) throws IOException {
	    List<PosterImg> posterImages = posterImgRepository.findAllByPosterId(poster.getId());

	    if (posterImages.size() + files.size() <= 3) {
	        for (MultipartFile file : files) {
	            PosterImg posterImg = posterBuilder(file, poster);
	            posterImgRepository.save(posterImg);
	        }
	        return "success";
	    } else {
	        return "Nuotrauku limitas pasiektas. Istrinkite nuotraukas pries dedamas naujas";
	    }
	}
	public PosterImg posterBuilder(MultipartFile file, Poster poster) throws IOException {
		
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		 PosterImg posterImg = PosterImg.builder()
	                .name(fileName)
	                .type(file.getContentType())
	                .data(file.getBytes())
	                .poster(poster)
	                .build();
		 
		 return posterImg;
	}
}
