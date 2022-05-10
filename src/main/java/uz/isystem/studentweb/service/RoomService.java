package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.isystem.studentweb.dto.RoomDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.Room;
import uz.isystem.studentweb.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    //Main function

    public RoomDto createRoom(RoomDto roomDto) {
        Room entity = new Room();
        entity.setName(roomDto.getName());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        roomRepository.save(entity);
        roomDto.setId(entity.getId());
        roomDto.setCreatedAt(entity.getCreatedAt());
        roomDto.setStatus(entity.getStatus());
        return roomDto;
    }

    public RoomDto getRoom(Integer id) {
        Room entity = getEntity(id);
        RoomDto roomDto = new RoomDto();
        roomDto.setId(entity.getId());
        roomDto.setName(entity.getName());
        roomDto.setStatus(entity.getStatus());
        roomDto.setCreatedAt(entity.getCreatedAt());
        roomDto.setUpdatedAt(entity.getUpdatedAt());
        return roomDto;
    }

    public List<RoomDto> getAll() {
        List<Room> entityList = roomRepository.findAllByDeletedAtIsNull();
        if (entityList.isEmpty()) {
            throw new ServerBadRequestException("Room not found");
        }

        List<RoomDto> dtoList = new LinkedList<>();
        for (Room room : entityList) {
            RoomDto roomDto = new RoomDto();
            roomDto.setId(room.getId());
            roomDto.setName(room.getName());
            roomDto.setStatus(room.getStatus());
            roomDto.setCreatedAt(room.getCreatedAt());
            roomDto.setUpdatedAt(room.getUpdatedAt());
            dtoList.add(roomDto);
        }
        return dtoList;
    }

    public RoomDto update (Integer id, RoomDto roomDto) {
        Room entity = getEntity(id);
        entity.setName(roomDto.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(entity);
        roomDto.setId(entity.getId());
        roomDto.setStatus(entity.getStatus());
        roomDto.setCreatedAt(entity.getCreatedAt());
        roomDto.setUpdatedAt(entity.getUpdatedAt());
        return roomDto;
    }

    public Boolean delete(Integer id) {
        Room room = getEntity(id);
        room.setDeletedAt(LocalDateTime.now());
        roomRepository.save(room);
        return true;
    }


    //Secondary function

    public Room getEntity(Integer id) {
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Room not found");
        }
        return optional.get();
    }


}
