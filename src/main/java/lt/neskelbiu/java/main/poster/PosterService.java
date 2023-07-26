package lt.neskelbiu.java.main.poster;

import java.util.List;

import lt.neskelbiu.java.main.exceptions.PosterNotFoundException;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
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
		return posterRepo.findById(id)
				.orElseThrow(() -> new PosterNotFoundException("Poster not found with id:" + id)); // need to handle exception
	}

	public Poster save(Poster post) {
		return posterRepo.save(post);
	}
	
	public void deleteById(Long id) {
		posterRepo.deleteById(id);
	}

	public List<Poster> searchEngine(
			String category,
			String type,
			String status,
			String city,
			Boolean priceIsAscending,
			Boolean createdAt,	//true means
			Boolean updatedAt
	) {
		CategoryA categoryA = null;
		CategoryB categoryB = null;
		if (category != null) {
			switch (category) {
				case "a" -> categoryA = CategoryA.valueOf(type.toUpperCase());
				case "b" -> categoryB = CategoryB.valueOf(type.toUpperCase());
			}
		}

		Status statusEnum = null;
		if (status != null) {
			statusEnum = Status.valueOf(
					status.toUpperCase()
			);
		}

		City cityEnum = null;
		if (city != null) {
			cityEnum = City.valueOf(
					city.toUpperCase()
			);
		}

		List<Poster> posterList;
		if (priceIsAscending != null) {
			if (priceIsAscending) {
				posterList = posterRepo.searchByAndSortByPriceAcsending(categoryA, categoryB, statusEnum, cityEnum);
			} else {
				posterList = posterRepo.searchByAndSortByPriceDescending(categoryA, categoryB, statusEnum, cityEnum);
			}
		} else if (createdAt != null) {
			posterList = posterRepo.searchByAndSortByCreatedAt(categoryA, categoryB, statusEnum, cityEnum);
			//po to prideto atnaujintus papildomai
		} else if (updatedAt != null){
			posterList = posterRepo.searchByAndSortByUpdatedAt(categoryA, categoryB, statusEnum, cityEnum);
			//po to pridetu naujausiai sukurtus
		} else {
			posterList = posterRepo.searchBy(
					categoryA,
					categoryB,
					statusEnum,
					cityEnum
			);
		}

		return posterList;
	}
}
