package com.programming.orderservice.grpc;

import com.programming.orderservice.GRPCIsInStockServiceGrpc;
import com.programming.orderservice.IsInStockGRPCResponse;
import com.programming.orderservice.IsInStockRequestDto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GRPCIsInStockService extends GRPCIsInStockServiceGrpc.GRPCIsInStockServiceImplBase{

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

    public IsInStockGRPCResponse isInStock(List<Long> bookIds, List<Integer> quantities) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        GRPCIsInStockServiceGrpc.GRPCIsInStockServiceBlockingStub stub = GRPCIsInStockServiceGrpc.newBlockingStub(channel);
         IsInStockRequestDto request = IsInStockRequestDto.newBuilder()
                .addAllBookIds(bookIds)
                .addAllQuantities(quantities)
                .build();
        IsInStockGRPCResponse inStock = stub.isInStock(request);
        return inStock;
    }


}
