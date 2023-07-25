package lt.neskelbiu.java.main.poster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PosterService {
	
	@Autowired
	PosterRepository posterRepo;
	
	public List<Poster> findAll() {
		return posterRepo.findAll();
	}
	
	public Poster findById(Long id) {
		return posterRepo.findById(id).orElseThrow(); // need to handle exception
	}

	public Poster save(Poster post) {
		return posterRepo.save(post);
	}
	
	public void deleteById(Long id) {
		posterRepo.deleteById(id);
	}
}
