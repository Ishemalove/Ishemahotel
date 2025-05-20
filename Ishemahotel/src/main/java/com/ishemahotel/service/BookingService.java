package com.ishemahotel.service;

import com.ishemahotel.entity.Booking;
import com.ishemahotel.entity.User;
import com.ishemahotel.entity.Room;
import com.ishemahotel.entity.BookingStatus;
import com.ishemahotel.entity.Hotel;
import com.ishemahotel.repository.BookingRepository;
import com.ishemahotel.repository.UserRepository;
import com.ishemahotel.repository.RoomRepository;
import com.ishemahotel.repository.HotelRepository;
import com.ishemahotel.dto.BookingDto;
import com.ishemahotel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public BookingDto createBooking(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingDto.getUserId()));
        
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + bookingDto.getRoomId()));

        if (!room.getIsAvailable()) {
            throw new IllegalStateException("Room is not available for booking");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckIn(bookingDto.getCheckIn());
        booking.setCheckOut(bookingDto.getCheckOut());
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(calculateTotalPrice(room, bookingDto.getCheckIn(), bookingDto.getCheckOut()));

        room.setIsAvailable(false);
        roomRepository.save(room);

        return convertToDto(bookingRepository.save(booking));
    }

    public BookingDto getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return bookingRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        return bookingRepository.findByRoom_Hotel(hotel).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        booking.setStatus(BookingStatus.CANCELLED);
        
        Room room = booking.getRoom();
        room.setIsAvailable(true);
        roomRepository.save(room);
        
        return convertToDto(bookingRepository.save(booking));
    }

    private Double calculateTotalPrice(Room room, LocalDateTime checkIn, LocalDateTime checkOut) {
        long days = java.time.Duration.between(checkIn, checkOut).toDays();
        return room.getPrice() * days;
    }

    private BookingDto convertToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setRoomId(booking.getRoom().getId());
        dto.setCheckIn(booking.getCheckIn());
        dto.setCheckOut(booking.getCheckOut());
        dto.setStatus(booking.getStatus());
        dto.setTotalPrice(booking.getTotalPrice());
        return dto;
    }
} 