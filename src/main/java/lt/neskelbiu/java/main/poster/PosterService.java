package lt.neskelbiu.java.main.poster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.exceptions.PosterNotFoundException;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.posterImg.PosterImg;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PosterService {
	

	private final PosterRepository posterRepo;
	private final UserService userService;

	public List<Poster> findAllUsersPosters(Long userId) {
		User user = userService.findById(userId);
		return posterRepo.findByUser(user);
	}

	public List<Poster> findAll() {
		return posterRepo.findAll();
	}
	public List<PosterResponse> posterListResponse(List<Poster> posterList) {
		List<PosterResponse> responseList = posterList.stream()
				.map(poster -> PosterResponse.builder()
						.posterId(poster.getId())
						.userId(poster.getUser().getId())
						.username(poster.getUser().getUsername())
						.postName(poster.getPostName())
						.description(poster.getDescription())
						.images(
								poster.getPosterImg().stream()
										.collect(Collectors.toMap(PosterImg::getPosition, PosterImg::getId))
						)
						.price(poster.getPrice())
						.categoryA(poster.getCategoryA())
						.categoryB(poster.getCategoryB())
						.status(poster.getStatus())
						.city(poster.getCity().getName())
						.phoneNumber(poster.getPhoneNumber())
						.website(poster.getWebsite())
						.videoLink(poster.getVideoLink())
						.createdAt(
								poster.getCreatedAt() != null
										? poster.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
										: null
								)
						.updatedAt(
								poster.getUpdatedAt() != null
										? poster.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
										: null
						)
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
				.posterId(poster.getId())
				.userId(poster.getUser().getId())
				.username(poster.getUser().getUsername())
				.postName(poster.getPostName())
				.description(poster.getDescription())
				.images( poster.getPosterImg() != null
						? poster.getPosterImg().stream()
								.collect(Collectors.toMap(PosterImg::getPosition, PosterImg::getId))
						: new HashMap<>()
				)
				.price(poster.getPrice())
				.categoryA(poster.getCategoryA())
				.categoryB(poster.getCategoryB())
				.status(poster.getStatus())
				.city(poster.getCity().getName())
				.phoneNumber(poster.getPhoneNumber())
				.website(poster.getWebsite())
				.videoLink(poster.getVideoLink())
				.createdAt(
						poster.getCreatedAt() != null
								? poster.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
								: null
				)
				.updatedAt(
						poster.getUpdatedAt() != null
								? poster.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
								: null
				)
				.build();
	}

	public Poster buildPosterNoId(Long userId, PosterRequest post) {
		var user = userService.findById(userId);
		Poster poster = Poster.builder()
				.postName(post.getPostName())
				.categoryA(post.getCategoryA())
				.categoryB(post.getCategoryB())
				.description(post.getDescription())
				.price(post.getPrice())
				.status(post.getStatus())
				.user(user)
				.phoneNumber(post.getPhoneNumber())
				.city(post.getCity())
				.website(post.getWebsite())
				.videoLink(post.getVideoLink())
				.createdAt(LocalDateTime.now())
				.build();
		return poster;
	}

	public Poster buildPosterWithId(Long userId, Long posterId, PosterRequest post) {
		var user = userService.findById(userId);
		Poster poster = findById(posterId);

		Poster updatedPoster = Poster.builder()
				.id(posterId)
				.postName(post.getPostName())
				.categoryA(post.getCategoryA())
				.categoryB(post.getCategoryB())
				.description(post.getDescription())
				.price(post.getPrice())
				.status(post.getStatus())
				.user(user)
				.phoneNumber(post.getPhoneNumber())
				.city(post.getCity())
				.website(post.getWebsite())
				.videoLink(post.getVideoLink())
				.createdAt(poster.getCreatedAt())
				.updatedAt(LocalDateTime.now())
				.build();
		return updatedPoster;
	}


	public Poster save(Poster post) {
		return posterRepo.save(post);
	}
	
	public void deleteById(Long id) {
		posterRepo.deleteById(id);
	}

	public List<PosterResponse> getLatest() {
		List<City> cityEnum = Arrays.asList(City.values());
		List<Poster> posterList = posterRepo.searchByAndSortByCreatedAt(null, null, cityEnum, null).stream()
				.limit(10)
				.toList();
		return posterListResponse(posterList);

	}

	public List<PosterResponse> searchEngine(
			String category,
			String type,
			List<String> city,
			Boolean priceIsAscending,
			Boolean createdAt,
			Boolean updatedAt,
			String string
	) {
		CategoryA categoryA = null;
		CategoryB categoryB = null;
		if (category != null) {
			switch (category) {
				case "a" -> categoryA = CategoryA.valueOf(type.toUpperCase());
				case "b" -> categoryB = CategoryB.valueOf(type.toUpperCase());
			}
		}

		List<City> cityEnum = Arrays.asList(City.values());
		if (city != null) {
			cityEnum = city.stream()
					.map((item) -> City.valueOf(item.toUpperCase()))
					.collect(Collectors.toList());
		}

		List<Poster> posterList;
		if (priceIsAscending != null) {
			if (priceIsAscending) {
				posterList = posterRepo.searchByAndSortByPriceAcsending(categoryA, categoryB, cityEnum, string);
			} else {
				posterList = posterRepo.searchByAndSortByPriceDescending(categoryA, categoryB, cityEnum, string);
			}
		} else if (createdAt != null && createdAt) {
			posterList = posterRepo.searchByAndSortByCreatedAt(categoryA, categoryB, cityEnum, string);
			List<Poster> otherList = posterRepo.searchByAndSortByUpdatedAt(categoryA, categoryB, cityEnum, string);
			otherList.removeAll(posterList);
			posterList.addAll(otherList);
		} else if (updatedAt != null && updatedAt){
			posterList = posterRepo.searchByAndSortByUpdatedAt(categoryA, categoryB, cityEnum, string);
			List<Poster> otherList = posterRepo.searchByAndSortByCreatedAt(categoryA, categoryB, cityEnum, string);
			otherList.removeAll(posterList);
			posterList.addAll(otherList);
		} else {
			posterList = posterRepo.searchBy(categoryA, categoryB, cityEnum, string);
		}
		return posterListResponse(posterList);
	}
}
