package com.programming.inventoryservice.grpc;

import com.programming.inventoryservice.GRPCIsInStockServiceGrpc;
import com.programming.inventoryservice.IsInStockRequestDto;
import com.programming.inventoryservice.IsInStockResponseDto;
import com.programming.inventoryservice.model.Inventory;
import com.programming.inventoryservice.repository.InventoryRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class GRPCIsInStockService extends GRPCIsInStockServiceGrpc.GRPCIsInStockServiceImplBase {
    private final InventoryRepository inventoryRepository;

    public GRPCIsInStockService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void isInStock(IsInStockRequestDto request, StreamObserver<IsInStockResponseDto> responseObserver) {
        List<Inventory> inventoryList = new ArrayList<>();
        List<Boolean> isInStockList = new ArrayList<>();

        for (Long bookId : request.getBookIdsList()) {
            Optional<Inventory> byBookId = inventoryRepository.findByBookId(bookId);
            if (byBookId.isPresent()) {
                Inventory inventory = byBookId.get();
                inventoryList.add(inventory);

                int quantityIndex = request.getBookIdsList().indexOf(bookId);
                int requestedQuantity = request.getQuantitiesList().get(quantityIndex);

                if (inventory.getQuantity() >= requestedQuantity) {
                    isInStockList.add(true);
                } else {
                    isInStockList.add(false);
                }
            }
        }
        IsInStockResponseDto responseDto = IsInStockResponseDto.newBuilder()
                .addAllBookIds(inventoryList.stream().map(Inventory::getBookId).collect(Collectors.toList()))
                .addAllIsInStock(isInStockList)
                .build();
        responseObserver.onNext(responseDto);
        responseObserver.onCompleted();
    }
}
