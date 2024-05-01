package com.nikita.frenzy.food.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikita.frenzy.food.dto.RestaurantDto;
import com.nikita.frenzy.food.models.Address;
import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.repository.AddressRepository;
import com.nikita.frenzy.food.repository.RestaurantRepository;
import com.nikita.frenzy.food.repository.UserRepository;
import com.nikita.frenzy.food.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		
		Address address = addressRepo.save(req.getAddress());
		
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setName(req.getName());
		restaurant.setDescription(req.getDescription());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setImages(req.getImages());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long resId, CreateRestaurantRequest updateReq) throws Exception {
		
		Restaurant restaurant = findRestaurantById(resId);
		
		restaurant.setCuisineType(updateReq.getCuisineType() != null ? updateReq.getCuisineType() : restaurant.getCuisineType());;
		restaurant.setDescription(updateReq.getDescription() != null ? updateReq.getDescription() : restaurant.getDescription());
		restaurant.setContactInformation(updateReq.getContactInformation() != null ? updateReq.getContactInformation() : restaurant.getContactInformation());
		restaurant.setAddress(updateReq.getAddress() != null ? updateReq.getAddress() : restaurant.getAddress());
		restaurant.setOpeningHours(updateReq.getOpeningHours() != null ? updateReq.getOpeningHours() : restaurant.getOpeningHours());
		restaurant.setImages(updateReq.getImages() != null ? updateReq.getImages() : restaurant.getImages());

		return restaurantRepo.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long resId) throws Exception {
		
		Restaurant restaurant = findRestaurantById(resId);
		
		restaurantRepo.delete(restaurant);
		
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepo.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		return restaurantRepo.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long resId) throws Exception {
		
		Optional<Restaurant> res = restaurantRepo.findById(resId);
		
		if(res.isEmpty()) {
			throw new Exception("Restaurant doesn't exist");
		}
		
		return res.get();
	}

	@Override
	public Restaurant findRestaurantByUserId(Long userId) throws Exception {
		
		Restaurant restaurant = restaurantRepo.findByOwnerId(userId);
		
		if(restaurant == null) {
			throw new Exception("Restaurant associated with this owner doesn't exist");
		}
		
		return null;
	}

	@Override
	public RestaurantDto addToFavourite(Long resId, User user) throws Exception {

		Restaurant restaurant = findRestaurantById(resId);
		
		RestaurantDto resDto = new RestaurantDto();
		resDto.setId(resId);
		resDto.setTitle(restaurant.getName());
		resDto.setImages(restaurant.getImages());
		resDto.setDescription(restaurant.getDescription());
		
		boolean isFavourite = false;
		List<RestaurantDto> favourites = user.getFavouriteRestaurants();
		for(RestaurantDto res : favourites) {
			if(res.getId().equals(resId)) {
				isFavourite = true;
				break;
			}
		}
		
		if(isFavourite) {
			favourites.removeIf(favourite -> favourite.getId().equals(resId));
		}else {
			favourites.add(resDto);
		}
			
		userRepo.save(user);
		
		return resDto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long resId) throws Exception {
		
		Restaurant restaurant = findRestaurantById(resId);
		
		restaurant.setOpen(!restaurant.isOpen());
		
		return restaurantRepo.save(restaurant);
	}

}
