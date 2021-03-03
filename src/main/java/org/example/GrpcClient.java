package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.GreetingServiceGrpc;
import org.example.grpc.GreetingServiceOuterClass;

import java.util.Iterator;

public class GrpcClient {
    public static void main(String[] args){
        //создадим канал для соединения с сервером
        ManagedChannel channel = ManagedChannelBuilder
                                    .forTarget("localhost:5454")
                                    .usePlaintext()
                                    .build();

        // stub - умеет делать удаленные вызовы на сервере

        GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub = GreetingServiceGrpc.newBlockingStub(channel);

        //создаем запрос
        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass
                                                            .HelloRequest
                                                            .newBuilder()
                                                            .setName("Georges")
                                                            .build();
        //удаленный вызов процедуры на сервере
        //GreetingServiceOuterClass.HelloResponse response = blockingStub.greeting(request);
        //System.out.println(response);

        //удаленный вызов на сервере, результат - стрим, возвращает итератор

        Iterator<GreetingServiceOuterClass.HelloResponse> responseIterator = blockingStub.greeting(request);
        while(responseIterator.hasNext()){
            System.out.println(responseIterator.next());
        }

        channel.shutdownNow();
    }
}
