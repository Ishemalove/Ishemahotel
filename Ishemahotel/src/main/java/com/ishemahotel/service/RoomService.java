package com.ishemahotel.service;

import com.ishemahotel.entity.Room;
import com.ishemahotel.entity.Hotel;
import com.ishemahotel.repository.RoomRepository;
import com.ishemahotel.repository.HotelRepository;
import com.ishemahotel.dto.RoomDto;
import com.ishemahotel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomDto createRoom(RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + roomDto.getHotelId()));

        Room room = new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomType(roomDto.getRoomType());
        room.setPrice(roomDto.getPrice());
        room.setIsAvailable(true);
        room.setHotel(hotel);
        
        return convertToDto(roomRepository.save(room));
    }

    public RoomDto getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public List<RoomDto> getRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        
        return roomRepository.findByHotel(hotel).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomDto> getAvailableRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        
        return roomRepository.findByHotelAndIsAvailableTrue(hotel).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomDto> getAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        
        return roomRepository.findByHotelAndIsAvailableTrue(hotel).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomType(roomDto.getRoomType());
        room.setPrice(roomDto.getPrice());
        room.setIsAvailable(roomDto.getIsAvailable());
        
        return convertToDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    private RoomDto convertToDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setPrice(room.getPrice());
        dto.setIsAvailable(room.getIsAvailable());
        dto.setHotelId(room.getHotel().getId());
        return dto;
    }
} 