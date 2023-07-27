package lt.neskelbiu.java.main.poster;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.exceptions.PosterNotFoundException;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.posterImg.PosterImg;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PosterService {
	

	private final PosterRepository posterRepo;
	private final UserService userService;
	
	public List<PosterResponse> findAll() {
		List<Poster> posterList = posterRepo.findAll();

		List<PosterResponse> responseList = posterList.stream()
				.map(poster -> PosterResponse.builder()
						.posterid(poster.getId())
						.userId(poster.getUser().getId())
						.postName(poster.getPostName())
						.description(poster.getDescription())
						.images(
								poster.getPosterImg().stream()
										.collect(Collectors.toMap(PosterImg::getPosition, PosterImg::getId))
						)
						.categoryA(poster.getCategoryA())
						.categoryB(poster.getCategoryB())
						.status(poster.getStatus())
						.city(poster.getCity().getName())
						.phoneNumber(poster.getPhoneNumber())
						.website(poster.getWebsite())
						.videoLink(poster.getVideoLink())
						.build())
				.toList();
		return responseList;
	}
	
	public Poster findById(Long id) {
		return posterRepo.findById(id)
				.orElseThrow(() -> new PosterNotFoundException("Poster not found with id:" + id)); // need to handle exception
	}

	public PosterResponse buildPosterResponse(Poster poster) {
		return PosterResponse.builder()
				.posterid(poster.getId())
				.userId(poster.getUser().getId())
				.postName(poster.getPostName())
				.description(poster.getDescription())
				.images(
						poster.getPosterImg().stream()
								.collect(Collectors.toMap(PosterImg::getPosition, PosterImg::getId))
				)
				.categoryA(poster.getCategoryA())
				.categoryB(poster.getCategoryB())
				.status(poster.getStatus())
				.city(poster.getCity().getName())
				.phoneNumber(poster.getPhoneNumber())
				.website(poster.getWebsite())
				.videoLink(poster.getVideoLink())
				.build();
	}

	public Poster buildPosterNoId(Long userId, PosterRequest post) {
		var user = userService.findById(userId);
		Poster poster = Poster.builder()
				.postName(post.getPostName())
				.categoryA(post.getCategoryA())
				.categoryB(post.getCategoryB())
				.description(post.getDescription())
				.status(post.getStatus())
				.user(user)
				.phoneNumber(post.getPhoneNumber())
				.city(post.getCity())
				.website(post.getWebsite())
				.videoLink(post.getVideoLink())
				.build();
		return poster;
	}

	public Poster buildPosterWithId(Long posterId, PosterRequest post) {
		var user = userService.findById(posterId);

		Poster poster = Poster.builder()
				.id(posterId)
				.postName(post.getPostName())
				.categoryA(post.getCategoryA())
				.categoryB(post.getCategoryB())
				.description(post.getDescription())
				.status(post.getStatus())
				.user(user)
				.phoneNumber(post.getPhoneNumber())
				.city(post.getCity())
				.website(post.getWebsite())
				.videoLink(post.getVideoLink())
				.build();
		return poster;
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
