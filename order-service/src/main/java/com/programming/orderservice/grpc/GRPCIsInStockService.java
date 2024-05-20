package com.programming.orderservice.grpc;

import com.programming.orderservice.GRPCIsInStockServiceGrpc;
import com.programming.orderservice.IsInStockGRPCResponse;
import com.programming.orderservice.IsInStockRequestDto;

import com.programming.orderservice.domain.OrderItem;
import com.programming.orderservice.dto.ResponseMessageDto;
import com.programming.orderservice.dto.response.InventoryResponseDto;
import com.programming.orderservice.repository.OrderItemRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GRPCIsInStockService extends GRPCIsInStockServiceGrpc.GRPCIsInStockServiceImplBase{

    private final OrderItemRepository orderItemRepository;

//    @GrpcClient("myService")
//    private GRPCIsInStockServiceGrpc.GRPCIsInStockServiceImplBase myServiceStub;
//
//    public CompletableFuture<IsInStockResponseDto> isInStock(List<Long> bookIds, List<Integer> quantities) {
//        IsInStockRequestDto request = IsInStockRequestDto.newBuilder()
//                .addAllBookIds(bookIds)
//                .addAllQuantities(quantities)
//                .build();
//
//        CompletableFuture<IsInStockResponseDto> future = new CompletableFuture<>();
//
//        myServiceStub.isInStock(request, new StreamObserver<IsInStockResponseDto>() {
//            @Override
//            public void onNext(IsInStockResponseDto response) {
//                future.complete(response); // Complete the future with the response
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                future.completeExceptionally(t); // Complete the future exceptionally if an error occurs
//            }
//
//            @Override
//            public void onCompleted() {
//                // Do nothing for onCompleted as we're using onNext to complete the future
//            }
//        });
//
//        return future;
//    }

    ///////////////////////////////////////////////////////////////////

    @GrpcClient("myService")
    private GRPCIsInStockServiceGrpc.GRPCIsInStockServiceBlockingStub myServiceStub;

    public GRPCIsInStockService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public ResponseEntity<ResponseMessageDto> isInStock(List<Long> bookIds, List<Integer> quantities) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        GRPCIsInStockServiceGrpc.GRPCIsInStockServiceBlockingStub stub = GRPCIsInStockServiceGrpc.newBlockingStub(channel);
         IsInStockRequestDto request = IsInStockRequestDto.newBuilder()
                .addAllBookIds(bookIds)
                .addAllQuantities(quantities)
                .build();
        IsInStockGRPCResponse grpcResponse = stub.isInStock(request);
        boolean allBooksInStock = !grpcResponse.getIsInStockList().contains(false);

        OrderItem orderItem = null;
        for (int i = 0; i < bookIds.size(); i++) {
            orderItem = OrderItem.builder()
                    .bookId(bookIds.get(i))
                    .quantity(quantities.get(i))
                    .build();
        }
        if (allBooksInStock){
            orderItemRepository.save(orderItem);
            channel.shutdown();
            InventoryResponseDto inventoryResponseDto = InventoryResponseDto.builder()
                    .bookIds(grpcResponse.getBookIdsList())
                    .isInStock(grpcResponse.getIsInStockList())
                    .build();
            return ResponseEntity.ok(ResponseMessageDto.builder()
                            .message(HttpStatus.OK.toString())
                    .data(inventoryResponseDto)
                            .status(HttpStatus.OK)
                    .build());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessageDto.builder().message("Not all books in stock").build());
        }

    }

}
