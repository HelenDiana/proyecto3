package com.api.mstransaction.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

    @Id
    private String id;
    @Field("type")
    private String type;
    @Field("amount")
    private Double amount;   
    //@Field("date")
    //private Date date = new Date();
    @Field("sourceAccount")
    private String sourceAccount;
    @Field("destinationAccount")
    private String destinationAccount;
}