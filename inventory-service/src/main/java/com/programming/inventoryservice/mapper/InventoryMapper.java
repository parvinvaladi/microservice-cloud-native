package com.programming.inventoryservice.mapper;

import com.programming.inventoryservice.dto.InventoryDto;
import com.programming.inventoryservice.model.Inventory;
import lombok.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryDto toDto(Inventory inventory);
    Inventory toEntity(InventoryDto inventoryDto);
    List<InventoryDto> toDtos(List<Inventory> inventory);
    List<Inventory> toEntities(List<InventoryDto> inventoryDtos);
}